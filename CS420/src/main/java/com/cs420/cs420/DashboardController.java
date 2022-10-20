package com.cs420.cs420;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private TreeView Farm;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Created the Different item like Example
        //Root File
        TreeItem<String> rootItem = new TreeItem<>("Root");
        //Main branches
        TreeItem<String>  branch1= new TreeItem<>("Barn");
        TreeItem<String> branch2 = new TreeItem<>("Live-Stock-Area");
        TreeItem<String> branch3= new TreeItem<>("Milk-Storage");
        TreeItem<String> branch4 = new TreeItem<>("Command-Center");
        //Leafs of the branches
        TreeItem<String> leaf1 = new TreeItem<>("Cow");
        TreeItem<String> leaf2 = new TreeItem<>("Drone");
        TreeItem<String> leaf3 = new TreeItem<>("Crop");
        //Combining the branch with the leaf
        branch2.getChildren().addAll(leaf1);
        branch4.getChildren().addAll(leaf2);
        //Combining the root with the branches
        rootItem.getChildren().addAll(branch1,branch2,branch3,branch4,leaf3);
        //Allows the Tree to be expanded (not needed)
        rootItem.setExpanded(true);
        //Setting the Javafx id to the Tree root
        Farm.setRoot(rootItem);

    }
}