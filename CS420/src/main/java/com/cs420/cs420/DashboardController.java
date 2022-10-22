package com.cs420.cs420;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private TreeView Farm;

    @FXML
    private ImageView Drone;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int i=0;
        int j=0;
        //Created the Different item like Example
        //Root File
        TreeItem rootItem = new TreeItem(new ItemContainer("Root",0,0,600, 800, 0));
        //Main branches
        //TreeItem branch1= new TreeItem(new ItemContainer("Barn",100,100,100,50,20));
        //TreeItem branch2 = new TreeItem(new ItemContainer("Live-Stock-Area",120,100,50,50,20));
        //TreeItem branch3= new TreeItem(new ItemContainer("Milk-Storage",100,125, 50,25,20));
        //TreeItem branch4 = new TreeItem(new ItemContainer("Command-Center", 100, 300, 100, 100, 20));
        //TreeItem branch5 = new TreeItem(new ItemContainer("Crop",500,300,200, 400, 0));
        for (i=0;i<4;i++){
            TreeItem branch =new TreeItem((new Item("Branch",0,0,0,0,0)));
            rootItem.getChildren().add(branch);
        }

        //Leafs of the branches
        for (j=0;j<4;j++){
            TreeItem leaf =new TreeItem((new Item("leaf",0,0,0,0,0)));
            rootItem.getChildren().add(leaf);
        }
        //TreeItem leaf1 = new TreeItem(new Item("Cow", 125, 105, 25, 10, 5));
        //TreeItem leaf2 = new TreeItem(new Item("Drone",400,300,25,10,5));
        //Combining the branch with the leaf
        //branch2.getChildren().add(leaf1);
        //branch4.getChildren().add(leaf2);
        //Combining the root with the branches
        //rootItem.getChildren().addAll(branch1,branch2,branch3,branch4,branch5);
        //Allows the Tree to be expanded (not needed)
        rootItem.setExpanded(true);
        //Setting the Javafx id to the Tree root
        Farm.setRoot(rootItem);

    }
}