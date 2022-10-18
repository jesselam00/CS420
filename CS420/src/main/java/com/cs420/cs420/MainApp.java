package com.cs420.cs420;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class MainApp extends Application {
    Dashboard dashboard = Dashboard.getInstance();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = dashboard.scene;
        stage.setTitle("Farm Dashboard");
        stage.setScene(scene);
        stage.show();
    }
}
