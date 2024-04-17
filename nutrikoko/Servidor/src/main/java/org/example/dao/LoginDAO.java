package org.example.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.example.model.Direccion;
import org.example.model.Profesional;

public class LoginDAO {
    private static String url = "jdbc:mysql://localhost:3306/nutrikoko"; 
    private static String user = "root";
    private static String contraseña = "fRANCO98";

    public boolean registroProfesional(Direccion direccion, Profesional profesional) {
        boolean insercion = false; 
        
        try (Connection connection = DriverManager.getConnection(url, user, contraseña)) {    
            int direccionId = insertarDireccion(connection, direccion); //Obtener el id de la dirección insertada
            
            //Insertar datos en la tabla Usuario
            String insert = "INSERT INTO USUARIO (NOMBRE, APELLIDOS, CORREO, CONTRASENIA, FECHA_NAC, TELEFONO, DNI, DIRECCION_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
                preparedStatement.setString(1, profesional.getNombre());
                preparedStatement.setString(2, profesional.getApellidos());
                preparedStatement.setString(3, profesional.getCorreo());
                preparedStatement.setString(4, profesional.getContrasenia());
                preparedStatement.setString(5, convertirFecha(profesional.getFechaNacimiento()));
                preparedStatement.setString(6, profesional.getTelefono());
                preparedStatement.setString(7, profesional.getDni());
                preparedStatement.setInt(8, direccionId);
                preparedStatement.executeUpdate(); 
            }

            //Insertar datos en la tabla Profesional       
            String insertProfesional = "INSERT INTO PROFESIONAL (ID, ESPECIALIDAD) VALUES (?, ?)";         
            try (PreparedStatement preparedStatement2 = connection.prepareStatement(insertProfesional)) {
                preparedStatement2.setInt(1, getUltimoId(connection)); //Obtener el último ID insertado en la tabla Persona
                preparedStatement2.setString(2, profesional.getEspecialidad());
                preparedStatement2.executeUpdate(); 
            }

            insercion = true; 
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return insercion; 
    }

    //Método para insertar dirección
    private int insertarDireccion(Connection connection, Direccion direccion) throws SQLException {
        String insertDireccion = "INSERT INTO DIRECCION (CALLE, NUMERO, CP, PROVINCIA, CIUDAD) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatementDireccion = connection.prepareStatement(insertDireccion, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatementDireccion.setString(1, direccion.getCalle());
            preparedStatementDireccion.setInt(2, direccion.getNumero());
            preparedStatementDireccion.setInt(3, direccion.getCp());
            preparedStatementDireccion.setString(4, direccion.getProvincia());
            preparedStatementDireccion.setString(5, direccion.getCiudad());
            preparedStatementDireccion.executeUpdate();
            ResultSet generatedKeys = preparedStatementDireccion.getGeneratedKeys();
            int direccionId = -1;
            if (generatedKeys.next()) {
                direccionId = generatedKeys.getInt(1);
            }
            return direccionId;
        }
    }

    // Método para obtener el último ID insertado en la tabla PERSONA
    private int getUltimoId(Connection connection) {
        String query = "SELECT LAST_INSERT_ID()";
        int lastId = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.execute();            
            try (java.sql.ResultSet resultSet = preparedStatement.getResultSet()) {
                if (resultSet.next()) 
                    lastId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error SQL al buscar el último ID en la tabla Persona");
        }
        return lastId;
    }   
    
    // Método que verifica si las credenciales son correctas o si se encuentran almacenadas en la base de datos
    public boolean verificarCredenciales(String correo, String contrasenia) {
        boolean credencialesCorrectas = false;
        String query = "SELECT CONTRASENIA FROM USUARIO WHERE CORREO = ?";
    
        try (Connection connection = DriverManager.getConnection(url, user, contraseña)) {
            // Verificar si el correo existe en la base de datos
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, correo);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String contraseniaAlmacenada = resultSet.getString("CONTRASENIA");
                        // Si el correo existe, verificar si la contraseña coincide
                        if (contraseniaAlmacenada.equals(contrasenia)) 
                            credencialesCorrectas = true;                        
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return credencialesCorrectas;
    }  
    
    private String convertirFecha(String fecha) {
        // Formato "DD/MM/YYYY"
        String[] partes = fecha.split("/");
        return partes[2] + "-" + partes[1] + "-" + partes[0];
    }

    // Método para obtener una lista de profesionales registrados
    public List<Profesional> obtenerProfesionalesRegistrados() {
        List<Profesional> profesionales = new ArrayList<>();

        String query = "SELECT * FROM PROFESIONAL " +
                       "JOIN USUARIO ON PROFESIONAL.ID = USUARIO.ID " +
                       "JOIN DIRECCION ON USUARIO.DIRECCION_ID = DIRECCION.ID";
        try (Connection connection = DriverManager.getConnection(url, user, contraseña);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Iterar sobre el resultado de la consulta y crear objetos Profesional con su dirección
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String nombre = resultSet.getString("NOMBRE");
                String apellidos = resultSet.getString("APELLIDOS");
                String correo = resultSet.getString("CORREO");
                String contrasenia = resultSet.getString("CONTRASENIA");
                String fechaNacimiento = resultSet.getString("FECHA_NAC");
                String telefono = resultSet.getString("TELEFONO");
                String dni = resultSet.getString("DNI");
                String calle = resultSet.getString("CALLE");
                int numero = resultSet.getInt("NUMERO");
                int cp = resultSet.getInt("CP");
                String provincia = resultSet.getString("PROVINCIA");
                String ciudad = resultSet.getString("CIUDAD");
                String especialidad = resultSet.getString("ESPECIALIDAD");
                Blob imagenBlob = resultSet.getBlob("IMAGEN");

                // Convertir la imagen Blob a byte[]
                byte[] imagenBytes = null;
                if (imagenBlob != null) {
                    imagenBytes = imagenBlob.getBytes(1, (int) imagenBlob.length());
                }

                // Crear objeto Direccion para el profesional
                Direccion direccion = new Direccion(calle, numero, cp, provincia, ciudad);

                // Crear objeto Profesional y agregarlo a la lista
                Profesional profesional = new Profesional(nombre, apellidos, correo, contrasenia, fechaNacimiento, telefono, dni, direccion, especialidad);
                profesional.setId(id);
                profesional.setImagen(imagenBytes); // Asignar la imagen al profesional

                profesionales.add(profesional);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return profesionales;
    }
}