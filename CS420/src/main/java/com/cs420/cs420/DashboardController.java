package com.cs420.cs420;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class DashboardController {
    @FXML
    private TreeView FarmObjects;


    @FXML
    public void initialize(){
        FarmObjects.setRoot(new TreeItem<>("Test Root"));

    }

}