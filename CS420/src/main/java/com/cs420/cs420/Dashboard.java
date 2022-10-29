package com.cs420.cs420;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;


//Uses Singleton pattern
public class Dashboard {
    private static Dashboard dashboard;
    public Scene scene;

    private Dashboard(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Dashboard.class.getResource("dash-scene.fxml"));
            scene = new Scene(fxmlLoader.load(), 1100, 900);
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