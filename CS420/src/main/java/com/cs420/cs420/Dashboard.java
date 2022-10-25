package com.cs420.cs420;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


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
 class Main {
    public static void main(String[] args) {
        Application.launch(DashboardVis.class, args);
    }
    public static class DashboardVis extends Application {
        @Override
        public void start(Stage stage) {
            Dashboard dashboard = Dashboard.getInstance();
            Scene scene = dashboard.scene;
            stage.setTitle("Farm Dashboard");
            stage.setScene(scene);
            stage.show();
        }
    }
}
