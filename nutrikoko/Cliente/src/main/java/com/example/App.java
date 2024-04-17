package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Login.fxml"));
        Parent root = loader.load();
        
        SceneLoader sceneLoader = new SceneLoader(stage);
        sceneLoader.loadScene(root);
    }

    public static void main(String[] args) {
        launch();
    }
}