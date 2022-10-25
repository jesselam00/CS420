package com.cs420.cs420;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class Dashboard {
    private static Dashboard dashboard;
    public Scene scene;

    private Dashboard(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Dashboard.class.getResource("dash-scene.fxml"));
            scene = new Scene(fxmlLoader.load(), 1024, 768);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static Dashboard getInstance(){
        if (dashboard == null){
            dashboard = new Dashboard();
        }
        return dashboard;
    }

}