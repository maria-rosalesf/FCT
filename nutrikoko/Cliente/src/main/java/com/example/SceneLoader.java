package com.example;

import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.StageStyle;

public class SceneLoader {
    private Stage stage; 

    public SceneLoader(Stage stage) {
        this.stage = stage; 
    }

    public void loadScene(Parent root) {
        if (stage.getScene() == null) {
            stage.initStyle(StageStyle.DECORATED);
        }
        stage.setScene(new Scene(root));
        stage.setFullScreen(true);
        stage.show();
    }
}
