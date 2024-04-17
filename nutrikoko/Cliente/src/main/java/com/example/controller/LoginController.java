package com.example.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.SceneLoader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    @FXML
    private Button botonCitas;

    @FXML
    private Button botonConfiguracion;

    @FXML
    private Button botonDietas;

    @FXML
    private Button botonEntrarLogin;

    @FXML
    private Button botonHome;

    @FXML
    private Button botonPacientes;

    @FXML
    private Button botonPagos;

    @FXML
    private Button botonSignOut;

    @FXML
    private PasswordField contraseniaLogin;

    @FXML
    private TextField correoLogin;

    @FXML
    private AnchorPane panelInicio;

    @FXML
    private BorderPane panelLogin;

    @FXML
    private Pane panelErrorLogin;

    @FXML
    private Text textoError;

    @FXML
    private CheckBox mostrarContraseniaCheck;

    private DataInputStream entrada;
    private DataOutputStream salida; 
    private static final int PUERTO = 8888; 
    private Socket socket; 

    @FXML //Entra en la aplicación si el usuario y contraseña existen en la BBDD y/o sin son correctos
    void entrarEnAplicacion(ActionEvent event) {     
        String correo = correoLogin.getText();
        String contrasenia = contraseniaLogin.getText();   

        //Verificar si el correo o contraseña están vacíos
        boolean camposVacios = correo.isEmpty() || contrasenia.isEmpty(); 

        if (camposVacios) {
            correoLogin.setStyle("-fx-border-color: red;");
            contraseniaLogin.setStyle("-fx-border-color: red;");
            mostrarMensajeError("Por favor, complete todos los campos");
            return; // Salir del método si hay campos vacíos
        } else {
            correoLogin.setStyle(null);
            contraseniaLogin.setStyle(null);
            panelErrorLogin.setVisible(false);
        }

        try {
            // Cerrar la conexión existente antes de intentar iniciar sesión con un nuevo usuario
            if (socket != null && !socket.isClosed()) {
                socket.close();
                entrada.close();
                salida.close();
            }

            socket = new Socket("localhost", PUERTO); 
            entrada = new DataInputStream(socket.getInputStream()); 
            salida = new DataOutputStream(socket.getOutputStream());

            salida.writeUTF("Login"); 
            salida.flush();

            String respuestaProtocolo = entrada.readUTF(); 

            salida.writeUTF(correo + ";" + contrasenia); // Enviar los datos de inicio de sesión al servidor
            salida.flush();

            respuestaProtocolo = entrada.readUTF();
            if (respuestaProtocolo.equals("logueado")) { // Inicio de sesión exitoso, ocultar el panel de inicio de sesión
                if (correo.equals("admin@admin.com")) { //Si inicia sesión desde la interfaz del administrador
                    SceneLoader sceneLoader = new SceneLoader((Stage) botonEntrarLogin.getScene().getWindow());
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/view/MainAdmin.fxml"));
                    Parent root = loader.load();
                    sceneLoader.loadScene(root);
                } else { //Se inicia sesión desde la interfaz de los nutricionistas
                    ocultarTodosLosPaneles();
                    panelInicio.setVisible(true);
                }
            } else {
                mostrarMensajeError("Credenciales inválidas. Por favor, inténtalo de nuevo.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        limpiarCamposLogin();
    }

    
    @FXML
    void mostrarContrasenia(ActionEvent event) {
        // Verificar si el CheckBox está marcado
        if (mostrarContraseniaCheck.isSelected()) {
            // Mostrar la contraseña
            contraseniaLogin.setPromptText(contraseniaLogin.getText());
            contraseniaLogin.setText("");
            contraseniaLogin.setDisable(false);
        } else {
            // Ocultar la contraseña
            contraseniaLogin.setText(contraseniaLogin.getPromptText());
            contraseniaLogin.setPromptText("");
            contraseniaLogin.setDisable(false);
        }
    }

    @FXML //Navega a la pantalla de Log In (sale de la aplicación)
    void salir(ActionEvent event) { 
        ocultarTodosLosPaneles();
        panelLogin.setVisible(true);
    }

    private void limpiarCamposLogin() {
        correoLogin.clear();
        contraseniaLogin.clear();
    }

    private void mostrarMensajeError(String mensaje) {
        textoError.setText(mensaje);
        panelErrorLogin.setVisible(true);
    }

    //Oculta todas las pantallas de la aplicación
    private void ocultarTodosLosPaneles() {
        panelInicio.setVisible(false);
        panelLogin.setVisible(false);
        panelErrorLogin.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }    
}