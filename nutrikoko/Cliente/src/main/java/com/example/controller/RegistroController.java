package com.example.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class RegistroController implements Initializable { 

    @FXML
    private TextField apellidosRegistro;

    @FXML
    private Button botonCancelarRegistro;

    @FXML
    private Button botonRegistrar;

    @FXML
    private TextField calleRegistro;

    @FXML
    private TextField especialidadRegistro;

    @FXML
    private TextField ciudadRegistro;

    @FXML
    private PasswordField contraseniaRegistro;

    @FXML
    private TextField correoRegistro;

    @FXML
    private TextField cpRegistro;

    @FXML
    private TextField dniRegistro;

    @FXML
    private DatePicker fechaNacimientoRegistro;

    @FXML
    private TextField nombreRegistro;

    @FXML
    private TextField numeroRegistro;

    @FXML
    private TextField provinciaRegistro;

    @FXML
    private TextField telefonoRegistro;

    @FXML
    private Pane panelErrorRegistro;

    @FXML
    private CheckBox mostrarContraseniaCheck;

    @FXML
    private Text textoMensajeError;

    @FXML
    private AnchorPane registroPane;

    private DataInputStream entrada;
    private DataOutputStream salida; 
    private static final int PUERTO = 8888; 
    private Socket socket; 

    @FXML
    void altaNutricionista(ActionEvent event) {
        String nombre = nombreRegistro.getText(); 
        String apellidos = apellidosRegistro.getText(); 
        String dni = dniRegistro.getText(); 
        LocalDate fecha = fechaNacimientoRegistro.getValue();
        String fechaNacimiento = "";
        if (fecha != null)
            fechaNacimiento = getFechaFormateada(fechaNacimientoRegistro); 
        else
            fechaNacimiento = null; 
        String telefono = telefonoRegistro.getText(); 
        String correo = correoRegistro.getText(); 
        String contrasenia = contraseniaRegistro.getText(); 
        String calle = calleRegistro.getText(); 
        String numero = numeroRegistro.getText();
        String cp = cpRegistro.getText();
        String ciudad = ciudadRegistro.getText();
        String provincia = provinciaRegistro.getText(); 
        String especialidad = especialidadRegistro.getText();

        boolean camposVacios = nombre.isEmpty() || apellidos.isEmpty() || dni.isEmpty() || telefono.isEmpty() || correo.isEmpty() 
        || contrasenia.isEmpty() || calle.isEmpty() || numero.isEmpty() || cp.isEmpty() || ciudad.isEmpty() || provincia.isEmpty();  

        if (camposVacios) {
            mostrarMensajeError("Por favor, complete todos los campos obligatorios");
            return; 
        }

        try {
            socket = new Socket("localhost", PUERTO); 
            entrada = new DataInputStream(socket.getInputStream()); 
            salida = new DataOutputStream(socket.getOutputStream());

            salida.writeUTF("Registro"); 
            String respuestaProtocolo = "";

            // Enviar los datos de registro al servidor
            salida.writeUTF(nombre + ";" + apellidos + ";" + dni + ";" + fechaNacimiento + ";" + telefono + ";" + correo + ";" 
                            + contrasenia + ";" + calle + ";" + numero + ";" + cp + ";" + ciudad + ";" + provincia + ";" + especialidad); 
            salida.flush();
            
            respuestaProtocolo = entrada.readUTF(); 

            if (respuestaProtocolo.equals("registrado")) { // Registro exitoso
                mostrarMensajeExito("Registro realizado.");
                limpiarCampos();
            } else 
                mostrarMensajeError("Se ha producido un error al registrar Profesional");
        } catch (IOException e) {
           e.printStackTrace();
        }
    }

    //Muestra un mensaje de alerta. Si se pulsa sí, se borran los datos de los campos del formulario
    @FXML
    void cancelarRegistro(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancelar Registro");
        alert.setHeaderText("¿Está seguro de que desea cancelar el registro?");
        alert.setContentText("Si cancela, se perderán todos los datos ingresados.");
    
        ButtonType buttonTypeSi = new ButtonType("Sí");
        ButtonType buttonTypeNo = new ButtonType("No");
    
        alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);
    
        // Mostrar el diálogo de alerta y esperar a que el usuario elija una opción
        alert.showAndWait().ifPresent(response -> {
            if (response == buttonTypeSi) {
                limpiarCampos();
            }
        });
    }

    @FXML
    void mostrarContrasenia(ActionEvent event) {
        // Verificar si el CheckBox está marcado
        if (mostrarContraseniaCheck.isSelected()) {
            // Mostrar la contraseña
            contraseniaRegistro.setPromptText(contraseniaRegistro.getText());
            contraseniaRegistro.setText("");
            contraseniaRegistro.setDisable(true);
        } else {
            // Ocultar la contraseña
            contraseniaRegistro.setText(contraseniaRegistro.getPromptText());
            contraseniaRegistro.setPromptText("");
            contraseniaRegistro.setDisable(false);
        }
    }

    private void mostrarMensajeExito(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void limpiarCampos() {
        nombreRegistro.clear(); 
        apellidosRegistro.clear(); 
        dniRegistro.clear(); 
        fechaNacimientoRegistro.setValue(null);
        telefonoRegistro.clear(); 
        correoRegistro.clear(); 
        contraseniaRegistro.clear(); 
        calleRegistro.clear(); 
        numeroRegistro.clear();
        cpRegistro.clear();
        ciudadRegistro.clear();
        provinciaRegistro.clear(); 
        especialidadRegistro.clear();
    }

    private String getFechaFormateada(DatePicker fecha) {
        LocalDate fechaSeleccionada = fecha.getValue();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaComoCadena = fechaSeleccionada.format(formatoFecha);
        return fechaComoCadena; 
    }

    private void mostrarMensajeError(String mensaje) {
        textoMensajeError.setText(mensaje);
        panelErrorRegistro.setVisible(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}