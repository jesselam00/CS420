package com.cs420.cs420;

import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.css.Size;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
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

    @FXML
    private AnchorPane FarmVis;

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
        ItemContainer commandcenter = new ItemContainer("Command-Center", 150, 50, 100, 200, 20);
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
        Item drone = new Item("Drone",200,60,25,10,5);
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
        //rootItem.setExpanded(true);
        //Setting the Javafx id to the Tree root
        Farm.setRoot(rootItem);

//        FarmVis.getChildren().addAll(barn.getRectangle());
        addItemToVis(commandcenter);
//        addItemToVis(barn);
//        FarmVis.getChildren().addAll(barn.getRectangle(), livestock.getRectangle(), milkstorage.getRectangle(), commandcenter.getRectangle(), crop.getRectangle(), cow.getRectangle(), drone.getRectangle());

        Drone.setX(drone.getLocationX());
        Drone.setY(drone.getLocationY());
    }

    public void addItemToVis(Item item){
        Rectangle rect = item.getRectangle();
        Label name = item.getLabel();
        name.relocate(item.getLocationX(),item.getLocationY());
        rect.relocate(item.getLocationX(), item.getLocationY());
        rect.setHeight(item.getLength());
        rect.setWidth(item.getWidth());
        FarmVis.getChildren().addAll(rect, name);

    }
    public void addItemToVis(ItemContainer item){
        Rectangle rect = item.getRectangle();
        Label name = item.getLabel();
        name.relocate(item.getLocationX(),item.getLocationY());
        rect.relocate(item.getLocationX(), item.getLocationY());
        rect.setHeight(item.getLength());
        rect.setWidth(item.getWidth());
        FarmVis.getChildren().addAll(rect, name);
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
            if(name.getResult()!=null) {
                FarmVis.getChildren().remove(item.getValue().getLabel());
                item.getValue().setName(name.getResult());
                item.getValue().setLabel(name.getResult());
                FarmVis.getChildren().add(item.getValue().getLabel());
            }

            Farm.refresh();
        }
    }
    public void onChangeLocation() {
        TreeItem<Item> item = (TreeItem<Item>) Farm.getSelectionModel().getSelectedItem();
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Change Location");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField input1 = new TextField();
        TextField input2 = new TextField();

        gridPane.add(new Label("X:"), 0, 0);
        gridPane.add(input1, 1, 0);
        gridPane.add(new Label("Y:"), 0, 1);
        gridPane.add(input2, 1, 1);

        dialog.getDialogPane().setContent(gridPane);

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(input1.getText(), input2.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(pair -> {
            FarmVis.getChildren().remove(item.getValue().getRectangle());
            FarmVis.getChildren().remove(item.getValue().getLabel());
            item.getValue().setLocationX(Integer.parseInt(pair.getKey()));
            item.getValue().setLocationY(Integer.parseInt(pair.getValue()));
            addItemToVis(item.getValue());
            Farm.refresh();
        });

        // System.out.println("X: " + item.getValue().getLocationX());
        // System.out.println("Y: " + item.getValue().getLocationY());
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
        Dialog<ArrayList<String>> dialog = new Dialog<>();
        dialog.setTitle("Change Dimensions");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField input1 = new TextField();
        TextField input2 = new TextField();
        TextField input3 = new TextField();

        gridPane.add(new Label("Length:"), 0, 0);
        gridPane.add(input1, 1, 0);
        gridPane.add(new Label("Width:"), 0, 1);
        gridPane.add(input2, 1, 1);
        gridPane.add(new Label("Height:"), 0, 2);
        gridPane.add(input3, 1, 2);

        dialog.getDialogPane().setContent(gridPane);

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                ArrayList<String> strings = new ArrayList<>();
                strings.add(input1.getText());
                strings.add(input2.getText());
                strings.add(input3.getText());
                return strings;
            }
            return null;
        });

        Optional<ArrayList<String>> result = dialog.showAndWait();

        result.ifPresent(pair -> {
            FarmVis.getChildren().remove(item.getValue().getRectangle());
            FarmVis.getChildren().remove(item.getValue().getLabel());
            item.getValue().setLength(Float.valueOf(pair.get(0)));
            item.getValue().setWidth(Float.valueOf(pair.get(1)));
            item.getValue().setHeight(Float.valueOf(pair.get(2)));
            addItemToVis(item.getValue());
        });
        System.out.println("L: " + item.getValue().getLength());
        System.out.println("W: " + item.getValue().getWidth());
        System.out.println("H: " + item.getValue().getHeight());
    }
    public void onDelete() {
        TreeItem item = (TreeItem) Farm.getSelectionModel().getSelectedItem();
        if (item.getValue().toString() == "Root") {
            return;
        }
        if(item != null) {
            TreeItem<ItemContainer> itemContainerTreeItem = (TreeItem<ItemContainer>) item.getParent();
            itemContainerTreeItem.getValue().removeItem((Item) item.getValue());
            FarmVis.getChildren().remove(((Item) item.getValue()).getRectangle());
            FarmVis.getChildren().remove(((Item) item.getValue()).getLabel());
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
            addItemToVis(newitem);
        }
//        System.out.println("onAddItem");
    }
    public void onAddItemContainer() {
        TreeItem<ItemContainer> item = (TreeItem<ItemContainer>) Farm.getSelectionModel().getSelectedItem();
        if(item != null){
            ItemContainer newitem = new ItemContainer("Item",item.getValue().getLocationX()+10,item.getValue().getLocationY()+10,100,50,20);
            item.getValue().addItem(newitem);
            ItemContainer newitemcontainer = new ItemContainer("Item Container",item.getValue().getLocationX()+10,item.getValue().getLocationY()+10,100,50,20);
            TreeItem newTreeItem = new TreeItem(newitemcontainer);
            item.getChildren().add(newTreeItem);
            addItemToVis(newitem);
        }
        // System.out.println("onAddItemContainer");
    }

    public void visitItem() {
        TreeItem<Item> item = (TreeItem<Item>) Farm.getSelectionModel().getSelectedItem();
        int transition_time = 1;

        TranslateTransition tend = new TranslateTransition(Duration.seconds(transition_time));
        tend.setToX(item.getValue().getLocationX()-Drone.getX());
        tend.setToY(item.getValue().getLocationY()-Drone.getY());
        tend.setAutoReverse(false);
        SequentialTransition st = new SequentialTransition(Drone, tend);
        st.play();


        System.out.println("visitItem");
    }

    public void ReturnHome(){
        int transition_time = 1;
        TranslateTransition tend = new TranslateTransition(Duration.seconds(transition_time));
        tend.setToX(0);
        tend.setToY(0);
        tend.setAutoReverse(false);
        SequentialTransition st = new SequentialTransition(Drone, tend);
        st.play();

    }

    public void transition(int x_cord,int  y_cord,Node Node){

    }
    public void scanFarm() {
        System.out.println("scanFarm");
        int transition_time = 1;
        TranslateTransition t1 = new TranslateTransition(Duration.seconds(transition_time));
        t1.setToX(-220);
        t1.setToY(-60);
        t1.setAutoReverse(false);
        // Drone should now be at (0,0)
        // Window is 568x682
        TranslateTransition t2 = new TranslateTransition(Duration.seconds(transition_time));
        t2.setToX(-220);
        t2.setToY(540);
        t2.setAutoReverse(false);
        TranslateTransition t3 = new TranslateTransition(Duration.seconds(transition_time));
        t3.setToX(-140);
        t3.setToY(540);
        t3.setAutoReverse(false);
        TranslateTransition t4 = new TranslateTransition(Duration.seconds(transition_time));
        t4.setToX(-140);
        t4.setToY(-60);
        t4.setAutoReverse(false);
        TranslateTransition t5 = new TranslateTransition(Duration.seconds(transition_time));
        t5.setToX(-60);
        t5.setToY(-60);
        t5.setAutoReverse(false);
        TranslateTransition t6 = new TranslateTransition(Duration.seconds(transition_time));
        t6.setToX(-60);
        t6.setToY(540);
        t6.setAutoReverse(false);
        TranslateTransition t7 = new TranslateTransition(Duration.seconds(transition_time));
        t7.setToX(20);
        t7.setToY(540);
        t7.setAutoReverse(false);
        TranslateTransition t8 = new TranslateTransition(Duration.seconds(transition_time));
        t8.setToX(20);
        t8.setToY(-60);
        t8.setAutoReverse(false);
        TranslateTransition t9 = new TranslateTransition(Duration.seconds(transition_time));
        t9.setToX(100);
        t9.setToY(-60);
        t9.setAutoReverse(false);
        TranslateTransition t10 = new TranslateTransition(Duration.seconds(transition_time));
        t10.setToX(100);
        t10.setToY(540);
        t10.setAutoReverse(false);
        TranslateTransition t11 = new TranslateTransition(Duration.seconds(transition_time));
        t11.setToX(180);
        t11.setToY(540);
        t11.setAutoReverse(false);
        TranslateTransition t12 = new TranslateTransition(Duration.seconds(transition_time));
        t12.setToX(180);
        t12.setToY(-60);
        t12.setAutoReverse(false);
        TranslateTransition t13 = new TranslateTransition(Duration.seconds(transition_time));
        t13.setToX(220);
        t13.setToY(-60);
        t13.setAutoReverse(false);
        TranslateTransition t14 = new TranslateTransition(Duration.seconds(transition_time));
        t14.setToX(220);
        t14.setToY(540);
        t14.setAutoReverse(false);
        TranslateTransition tend = new TranslateTransition(Duration.seconds(transition_time));
        tend.setToX(0);
        tend.setToY(0);
        tend.setAutoReverse(false);

        SequentialTransition st = new SequentialTransition(Drone, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, tend);
        st.play();

    }
}