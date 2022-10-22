package com.cs420.cs420;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

        //ArrayList<ItemContainer> containerArr = new ArrayList<>();
        //ArrayList<Item> itemArr = new ArrayList<>();
    @FXML
    private TreeView Farm;

    @FXML
    private Label commandLabel;

    @FXML
    private Button renameButton;

    @FXML
    private Button locationButton;

    @FXML
    private Button priceButton;

    @FXML
    private Button dimensionsButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button addItemButton;

    @FXML
    private Button addItemContainerButton;

    @FXML
    private ImageView Drone;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        commandLabel.setVisible(false);
        renameButton.setVisible(false);
        addItemButton.setVisible(false);
        addItemContainerButton.setVisible(false);
        deleteButton.setVisible(false);
        dimensionsButton.setVisible(false);
        locationButton.setVisible(false);
        priceButton.setVisible(false);
        int i=0;
        int j=0;
        //Created the Different item like Example
        //Root File
        ItemContainer root = new ItemContainer("Root",0,0,600, 800, 0);
        ItemContainer barn = new ItemContainer("Barn",100,100,100,50,20);
        ItemContainer livestock = new ItemContainer("Live-Stock-Area",120,100,50,50,20);
        barn.addItem(livestock);
        ItemContainer milkstorage = new ItemContainer("Milk-Storage",100,125, 50,25,20);
        barn.addItem(milkstorage);
        ItemContainer commandcenter = new ItemContainer("Command-Center", 100, 300, 100, 100, 20);
        ItemContainer crop = new ItemContainer("Crop",500,300,200, 400, 0);
        root.addItem(barn);
        root.addItem(commandcenter);
        root.addItem(crop);
        //Root File
        TreeItem rootItem = new TreeItem(root);
        //Main branches
        TreeItem branch1 = new TreeItem(barn);
        TreeItem branch2 = new TreeItem(livestock);
        TreeItem branch3 = new TreeItem(milkstorage);
        TreeItem branch4 = new TreeItem(commandcenter);
        TreeItem branch5 = new TreeItem(crop);

        ItemContainer barn2 = new ItemContainer("Barn 2",100,100,100,50,20);
        barn.addItem(barn2);
        TreeItem branch1_2 = new TreeItem(barn2);
        branch1.getChildren().addAll(branch1_2,branch2,branch3);
        //containerArr.addAll(root,barn,livestock,milkstorage,commandcenter,crop);

        Item cow = new Item("Cow", 125, 105, 25, 10, 5);
        Item drone = new Item("Drone",400,300,25,10,5);
        livestock.addItem(cow);
        commandcenter.addItem(drone);
        TreeItem leaf1 = new TreeItem(cow);
        TreeItem leaf2 = new TreeItem(drone);
        //itemArr.addAll(cow,drone);
        //Combining the branch with the leaf
        branch2.getChildren().add(leaf1);
        branch4.getChildren().add(leaf2);
        //Combining the root with the branches
        rootItem.getChildren().addAll(branch1,branch4,branch5);
        //Allows the Tree to be expanded (not needed)
        rootItem.setExpanded(true);
        //Setting the Javafx id to the Tree root
        Farm.setRoot(rootItem);

    }

    public void onLeftClick() {
        TreeItem<Item> treeItem = (TreeItem<Item>) Farm.getSelectionModel().getSelectedItem();
        if(treeItem == null) {
            return;
        }
        commandLabel.setVisible(true);
        renameButton.setVisible(true);
        deleteButton.setVisible(true);
        dimensionsButton.setVisible(true);
        locationButton.setVisible(true);
        priceButton.setVisible(true);
        if (treeItem.getValue() instanceof ItemContainer) {           // // item
            commandLabel.setText("Item Container Commands");
            addItemButton.setVisible(true);
            addItemContainerButton.setVisible(true);
        } else {                                // item container
            commandLabel.setText("Item Commands");
            addItemButton.setVisible(false);
            addItemContainerButton.setVisible(false);
        }
    }
    public void onRightClick() {
        TreeItem<Item> item = (TreeItem<Item>) Farm.getSelectionModel().getSelectedItem();
        if(item!=null) {
            System.out.println("You right clicked " + item.getValue());
        }
    }

    public void onRename() {
        TreeItem<Item> item = (TreeItem<Item>) Farm.getSelectionModel().getSelectedItem();
        TextInputDialog name = new TextInputDialog("");
        name.setHeaderText("Enter new Name:");
        name.showAndWait();
        if (item != null) {
            String oldname = item.getValue().getName();
            if(name.getResult()!=null){
                item.getValue().setName(name.getResult());
            }

            Farm.refresh();
        }
    }
    public void onChangeLocation() {
        TreeItem<Item> item = (TreeItem<Item>) Farm.getSelectionModel().getSelectedItem();
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Change Location");
        ButtonType change = new ButtonType("Change", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(change, ButtonType.CANCEL);

        if (item != null) {
            item.getValue().setLocationX(0);
            item.getValue().setLocationY(0);
            Farm.refresh();
        }

        System.out.println("onChangeLocation");
    }
    public void onChangePrice() {
        TreeItem<Item> item = (TreeItem<Item>) Farm.getSelectionModel().getSelectedItem();
        TextInputDialog price = new TextInputDialog(("$")+"Enter new Price");
        price.setHeaderText("Enter new Price ($):");
        price.showAndWait();
        if(item !=null){
            if(price.getResult()!=null){
                item.getValue().setPrice(Float.valueOf(price.getResult()));
            }
            Farm.refresh();
        }
    }
    public void onChangeDimensions() {
        TreeItem<Item> item = (TreeItem<Item>) Farm.getSelectionModel().getSelectedItem();
        
        if (item != null) {
            item.getValue().setLength(10);
            item.getValue().setHeight(10);
            Farm.refresh();
            // add area to type length/height?
        }

        System.out.println("onChangeDimensions");
    }
    public void onDelete() {
        TreeItem item = (TreeItem) Farm.getSelectionModel().getSelectedItem();
        if (item.getValue().toString() == "Root") {
            return;
        }
        if(item != null) {
            TreeItem<ItemContainer> itemContainerTreeItem = (TreeItem<ItemContainer>) item.getParent();
            itemContainerTreeItem.getValue().removeItem((Item) item.getValue());
            item.getParent().getChildren().remove(item);
            System.out.println(itemContainerTreeItem.getValue().getItems());
        }
        System.out.println("onDelete");
    }
    public void onAddItem() {
        TreeItem<ItemContainer> item = (TreeItem<ItemContainer>) Farm.getSelectionModel().getSelectedItem();
        if(item != null){
            Item newitem = new Item("Item",item.getValue().getLocationX()+10,item.getValue().getLocationY()+10,100,50,20);
            item.getValue().addItem(newitem);
            TreeItem newTreeItem = new TreeItem(newitem);
            item.getChildren().add(newTreeItem);
        }
//        System.out.println("onAddItem");
    }
    public void onAddItemContainer() {
        TreeItem<ItemContainer> item = (TreeItem) Farm.getSelectionModel().getSelectedItem();
        if(item != null){
            ItemContainer newitem = new ItemContainer("Item",item.getValue().getLocationX()+10,item.getValue().getLocationY()+10,100,50,20);
            item.getValue().addItem(newitem);
            ItemContainer newitemcontainer = new ItemContainer("Item Container",item.getValue().getLocationX()+10,item.getValue().getLocationY()+10,100,50,20);
            TreeItem newTreeItem = new TreeItem(newitemcontainer);
            item.getChildren().add(newTreeItem);
        }
        // System.out.println("onAddItemContainer");
    }

    public void visitItem() {
        System.out.println("visitItem");
    }

    public void scanFarm() {
        System.out.println("scanFarm");
    }
}