package com.example.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;

public class MainController implements Initializable{
    @FXML
    private Button botonRegistarProfesional;

    @FXML
    private Button botonVerProfesionales;

    @FXML
    private VBox contentPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //loadView("/com/example/view/MainAdmin.fxml"); 
    }

    // Redirige al fxml que permite dar de alta un nutricionista
    @FXML
    public void registrarNutricionista(ActionEvent event) {
        try {
            // Cargar el archivo FXML de la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/view/Registro.fxml"));
            AnchorPane view = loader.load();

            // Establecer las restricciones de anclaje para expandirse dentro del VBox
            VBox.setVgrow(view, Priority.ALWAYS);

            // Agregar la vista al contentPane
            contentPane.getChildren().setAll(view); 

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void verProfesionales(ActionEvent event) {
        try {
            // Cargar el archivo FXML de la vista de profesionales
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/view/ProfesionalesAdmin.fxml"));
            VBox view = loader.load();
    
            // Establecer las restricciones de anclaje para expandirse dentro del VBox
            VBox.setVgrow(view, Priority.ALWAYS);
    
            // Agregar la vista al contentPane
            contentPane.getChildren().setAll(view); 
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}