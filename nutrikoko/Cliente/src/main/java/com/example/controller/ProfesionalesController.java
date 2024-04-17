package com.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProfesionalesController implements Initializable {

    @FXML
    private TextField barraBusqueda;

    @FXML
    private TableColumn<Profesional, String> campoApellidos;

    @FXML
    private TableColumn<Profesional, String> campoCorreo;

    @FXML
    private TableColumn<Profesional, String> campoDNI;

    @FXML
    private TableColumn<Profesional, Integer> campoId;

    @FXML
    private TableColumn<Profesional, String> campoNombre;

    @FXML
    private TableColumn<Profesional, String> campoTelefono;

    @FXML
    private TableColumn<Profesional, Void> campoOpciones;

    @FXML
    private TableView<Profesional> tablaRegistroNutricionistas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarColumnas();
        cargarDatosProfesionales();
    }

    private void configurarColumnas() {
        campoApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        campoCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        campoDNI.setCellValueFactory(new PropertyValueFactory<>("dni"));
        campoId.setCellValueFactory(new PropertyValueFactory<>("id"));
        campoNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        campoTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        // Configuración de edición en línea para las columnas de texto
        campoApellidos.setCellFactory(TextFieldTableCell.forTableColumn());
        campoCorreo.setCellFactory(TextFieldTableCell.forTableColumn());
        campoDNI.setCellFactory(TextFieldTableCell.forTableColumn());
        campoNombre.setCellFactory(TextFieldTableCell.forTableColumn());
        campoTelefono.setCellFactory(TextFieldTableCell.forTableColumn());

        // Configuración de la columna de opciones
        campoOpciones.setCellFactory(param -> new TableCell<>() {
            private final Button botonEditar = new Button("Editar");
            private final Button botonEliminar = new Button("Eliminar");

            {
                botonEditar.setOnAction(event -> handleEditar(getIndex()));
                botonEliminar.setOnAction(event -> handleEliminar(getIndex()));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(botonEditar);
                    setGraphic(botonEliminar);
                }
            }
        });
    }

    private void cargarDatosProfesionales() {
        List<Profesional> profesionales = obtenerProfesionalesDesdeServidor();
        tablaRegistroNutricionistas.getItems().addAll(profesionales);
    }

    private List<Profesional> obtenerProfesionalesDesdeServidor() {
        // Aquí deberías llamar a tu método en el servidor para obtener la lista de profesionales
        return List.of(); // Retorna una lista vacía temporalmente
    }

    private void handleEditar(int index) {
        Profesional profesional = tablaRegistroNutricionistas.getItems().get(index);
        // Aquí puedes abrir una ventana para editar el profesional
        System.out.println("Editar: " + profesional.getNombre());
    }

    private void handleEliminar(int index) {
        Profesional profesional = tablaRegistroNutricionistas.getItems().get(index);
        // Aquí puedes mostrar un diálogo de confirmación antes de eliminar el profesional
        System.out.println("Eliminar: " + profesional.getNombre());
    }
}