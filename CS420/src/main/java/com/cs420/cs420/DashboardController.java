package com.cs420.cs420;
import com.cs420.cs420.jdrone.control.physical.tello.TelloDrone;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

//Uses Composite pattern
public class DashboardController implements Initializable {


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
    private Button marketValueButton;
    @FXML
    private Label priceLabel;
    @FXML
    private Label valueLabel;
    @FXML
    private ImageView Drone_Image;
    @FXML
    private Label item_error;
    @FXML
    private Label none_selected;
    @FXML
    private RadioButton new_scan;
    @FXML
    private RadioButton new_vist;
    @FXML
    private RadioButton new_home;
    @FXML
    private Button realDrone ;
    @FXML
    private Button Virtual_drone;
    @FXML
    private Button panic_button;

    private droneAnimation drone;

    private droneIRL drone_real;

    @FXML
    private AnchorPane FarmVis;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        drone_real = droneIRL.getInstance();
        commandLabel.setVisible(false);
        renameButton.setVisible(false);
        addItemButton.setVisible(false);
        addItemContainerButton.setVisible(false);
        deleteButton.setVisible(false);
        dimensionsButton.setVisible(false);
        locationButton.setVisible(false);
        priceButton.setVisible(false);
        item_error.setVisible(false);
        none_selected.setVisible(false);
        marketValueButton.setVisible(false);
        priceLabel.setVisible(false);
        valueLabel.setVisible(false);
        ToggleGroup group = new ToggleGroup();
        new_vist.setToggleGroup(group);
        new_scan.setToggleGroup(group);
        new_home.setToggleGroup(group);

        //Created the Different item like Example
        //Root File
        ItemContainer root = new ItemContainer("Root", 0, 0, 600, 800, 0);
        ItemContainer barn = new ItemContainer("Barn", 50, 220, 200, 450, 10000);
        ItemContainer livestock = new ItemContainer("Live-Stock-Area", 80, 250, 150, 150, 20);
        barn.addItemContainer(livestock);
        ItemContainer milkstorage = new ItemContainer("Milk-Storage", 240, 250, 80, 140, 20);
        barn.addItemContainer(milkstorage);
        ItemContainer commandcenter = new ItemContainer("Command-Center", 120, 30, 120, 200, 20);
        ItemContainer crop = new ItemContainer("Crop", 20, 440, 300, 560, 0);
        root.addItemContainer(barn);
        root.addItemContainer(commandcenter);
        root.addItemContainer(crop);
        //Root File
        TreeItem rootItem = new TreeItem(root);
        //Main branches
        TreeItem branch1 = new TreeItem(barn);
        TreeItem branch2 = new TreeItem(livestock);
        TreeItem branch3 = new TreeItem(milkstorage);
        TreeItem branch4 = new TreeItem(commandcenter);
        TreeItem branch5 = new TreeItem(crop);

        ItemContainer barn2 = new ItemContainer("Barn 2", 400, 250, 100, 50, 20);
        barn.addItemContainer(barn2);
        TreeItem branch1_2 = new TreeItem(barn2);
        branch1.getChildren().addAll(branch1_2, branch2, branch3);
        Item cow = new Item("Cow", 500, 105, 50, 30, 5);
        Item drone_item = new Item("Drone", 200, 60, 25, 10, 5);
        livestock.addItem(cow);
        commandcenter.addItem(drone_item);
        TreeItem leaf1 = new TreeItem(cow);
        TreeItem leaf2 = new TreeItem(drone_item);
        //Combining the branch with the leaf
        branch2.getChildren().add(leaf1);
        branch4.getChildren().add(leaf2);
        //Combining the root with the branches
        rootItem.getChildren().addAll(branch1, branch4, branch5);
        //Allows the Tree to be expanded (not needed)
        rootItem.setExpanded(true);
        Farm.setRoot(rootItem);

        //Hard codes the necessary item and item containers
        addItemToVis(commandcenter);
        addItemToVis(barn);
        addItemToVis(barn2);
        addItemToVis(livestock);
        addItemToVis(milkstorage);
        addItemToVis(crop);
        addItemToVis(cow);

        Drone_Image.setX(drone_item.getLocationX());
        Drone_Image.setY(drone_item.getLocationY());
        drone = new droneAnimation(Drone_Image);
    }

    // Given an Item, access its rectangle, set its coordinates and dimensions, and add to visualization
    public void addItemToVis(Item item) {
        Rectangle rect = item.getRectangle();
        Label name = item.getLabel();
        name.relocate(item.getLocationX(), item.getLocationY());
        rect.relocate(item.getLocationX(), item.getLocationY());
        rect.setHeight(item.getLength());
        rect.setWidth(item.getWidth());
        FarmVis.getChildren().addAll(rect, name);

    }

    // Given an ItemContainer, access its rectangle, set its coordinates and dimensions, and add to visualization
    public void addItemToVis(ItemContainer item) {
        Rectangle rect = item.getRectangle();
        Label name = item.getLabel();
        name.relocate(item.getLocationX(), item.getLocationY());
        rect.relocate(item.getLocationX(), item.getLocationY());
        rect.setHeight(item.getLength());
        rect.setWidth(item.getWidth());
        FarmVis.getChildren().addAll(rect, name);
    }

    public void onLeftClick() {
        TreeItem treeItem = (TreeItem) Farm.getSelectionModel().getSelectedItem();
        if (treeItem == null) {
            return;
        }
        commandLabel.setVisible(true);
        renameButton.setVisible(true);
        deleteButton.setVisible(true);
        dimensionsButton.setVisible(true);
        locationButton.setVisible(true);
        priceButton.setVisible(true);
        marketValueButton.setVisible(true);
        priceLabel.setVisible(true);
        valueLabel.setVisible(true);
        if (treeItem.getValue() instanceof ItemContainer) {      // ItemContainer
            commandLabel.setText("Item Container Commands");
            ItemContainer myItemContainer = ((ItemContainer) treeItem.getValue());
            FarmVisitor visitor = new ConcreteFarmVisitor();
            float totalPrice = myItemContainer.acceptPrice(visitor);
            float totalValue = myItemContainer.acceptValue(visitor);
            priceLabel.setText("Purchase Price: " + ((int) totalPrice));
            valueLabel.setText("Current Market Value: " + ((int) totalValue));
            addItemButton.setVisible(true);
            addItemContainerButton.setVisible(true);
        } else {     // Item
            commandLabel.setText("Item Commands");
            Item myItem = ((Item) treeItem.getValue());
            FarmVisitor visitor = new ConcreteFarmVisitor();
            float totalPrice = myItem.acceptPrice(visitor);
            float totalValue = myItem.acceptValue(visitor);
            priceLabel.setText("Purchase Price: " + ((int) totalPrice));
            valueLabel.setText("Current Market Value: " + ((int) totalValue));
            addItemButton.setVisible(false);
            addItemContainerButton.setVisible(false);
        }
    }

    //Right now has no functionality just prints "You right-clicked"
    public void onRightClick() {
        TreeItem item = (TreeItem) Farm.getSelectionModel().getSelectedItem();
        if (item != null) {
            if (item.getValue() instanceof ItemContainer) {
                ItemContainer temp = (ItemContainer) item.getValue();
                System.out.println(" Name of Item Container: " + temp.getName());
                System.out.println(" Price of Item Container: " + temp.getPrice());
                System.out.println(" Value of Item Container: " + temp.getValue());
                System.out.println(" Items in Container: " + temp.getItems());
                System.out.println(" Item Containers in Container: " + temp.getItemContainers());
            } else {
                Item temp = (Item) item.getValue();
                System.out.println(" Name of Item: " + temp.getName());
                System.out.println(" Price of Item: " + temp.getPrice());
                System.out.println(" Value of Item: " + temp.getValue());
            }

        }
    }

    //Allows the user to rename the item or item container
    public void onRename() {
        TreeItem item = (TreeItem) Farm.getSelectionModel().getSelectedItem();
        TextInputDialog name = new TextInputDialog("");
        name.setHeaderText("Enter new Name:");
        name.showAndWait();


        if (item != null) {
            if (item.getValue() instanceof ItemContainer) {
                // Type Cast to Container
                ;
                String oldname = ((ItemContainer) item.getValue()).getName();
                FarmVis.getChildren().remove(((ItemContainer) item.getValue()).getLabel());
                ((ItemContainer) item.getValue()).setName(name.getResult());
                ((ItemContainer) item.getValue()).setLabel(name.getResult());
                FarmVis.getChildren().add(((ItemContainer) item.getValue()).getLabel());
                Farm.refresh();
            } else {
                // Type Cast to Item
                Item temp = (Item) item.getValue();
                String oldname = temp.getName();
                FarmVis.getChildren().remove(temp.getLabel());
                temp.setName(name.getResult());
                temp.setLabel(name.getResult());
                FarmVis.getChildren().add(temp.getLabel());
                Farm.refresh();
            }

        }
    }

    //Source code for onChangeDimension:https://code.makery.ch/blog/javafx-dialogs-official/
    //Allows user to change the position of the item or item container using x and y location
    public void onChangeLocation() {
        TreeItem item = (TreeItem) Farm.getSelectionModel().getSelectedItem();
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
            if (item.getValue() instanceof ItemContainer) {
                ItemContainer temp = (ItemContainer) item.getValue();
                FarmVis.getChildren().remove(temp.getRectangle());
                FarmVis.getChildren().remove(temp.getLabel());
                temp.setLocationX(Integer.parseInt(pair.getKey()));
                temp.setLocationY(Integer.parseInt(pair.getValue()));
                addItemToVis(temp);
            } else {
                Item temp = (Item) item.getValue();
                FarmVis.getChildren().remove(temp.getRectangle());
                FarmVis.getChildren().remove(temp.getLabel());
                temp.setLocationX(Integer.parseInt(pair.getKey()));
                temp.setLocationY(Integer.parseInt(pair.getValue()));
                addItemToVis(temp);
            }

            Farm.refresh();
        });
    }

    public void onChangePrice() {
        TreeItem item = (TreeItem) Farm.getSelectionModel().getSelectedItem();
        TextInputDialog price = new TextInputDialog(("$") + "Enter new Price");
        price.setHeaderText("Enter new Price ($):");
        price.showAndWait();
        if (item != null) {
            if (price.getResult() != null) {
                if (item.getValue() instanceof ItemContainer) {
                    ItemContainer temp = (ItemContainer) item.getValue();
                    temp.setPrice(Float.valueOf(price.getResult()));
                } else {
                    Item temp = (Item) item.getValue();
                    temp.setPrice(Float.valueOf(price.getResult()));
                }
                Farm.refresh();
            }

        }
        if (item.getValue() instanceof ItemContainer) {      // ItemContainer
            commandLabel.setText("Item Container Commands");
            ItemContainer myItemContainer = ((ItemContainer) item.getValue());
            FarmVisitor visitor = new ConcreteFarmVisitor();
            float totalPrice = myItemContainer.acceptPrice(visitor);
            priceLabel.setText("Purchase Price: " + ((int) totalPrice));
        } else {     // Item
            commandLabel.setText("Item Commands");
            Item myItem = ((Item) item.getValue());
            FarmVisitor visitor = new ConcreteFarmVisitor();
            float totalPrice = myItem.acceptPrice(visitor);
            priceLabel.setText("Purchase Price: " + ((int) totalPrice));
        }
    }

    //Source code for onChangeDimension:https://code.makery.ch/blog/javafx-dialogs-official/
    //Allows user to change the size of the item or item container using length and width
    public void onChangeDimensions() {
        TreeItem item = (TreeItem) Farm.getSelectionModel().getSelectedItem();
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
            if (item.getValue() instanceof ItemContainer) {
                FarmVis.getChildren().remove(((ItemContainer) item.getValue()).getRectangle());
                FarmVis.getChildren().remove(((ItemContainer) item.getValue()).getLabel());
                ((ItemContainer) item.getValue()).setLength(Float.valueOf(pair.get(0)));
                ((ItemContainer) item.getValue()).setWidth(Float.valueOf(pair.get(1)));
                ((ItemContainer) item.getValue()).setHeight(Float.valueOf(pair.get(2)));
                addItemToVis(((ItemContainer) item.getValue()));
            } else {
                FarmVis.getChildren().remove(((Item) item.getValue()).getRectangle());
                FarmVis.getChildren().remove(((Item) item.getValue()).getLabel());
                ((Item) item.getValue()).setLength(Float.valueOf(pair.get(0)));
                ((Item) item.getValue()).setWidth(Float.valueOf(pair.get(1)));
                ((Item) item.getValue()).setHeight(Float.valueOf(pair.get(2)));
                addItemToVis(((Item) item.getValue()));
            }
        });
    }

    //When user click delete will allow user to remove the item or item container to the tree
    public void onDelete() {
        TreeItem item = (TreeItem) Farm.getSelectionModel().getSelectedItem();
        if (item.getValue().toString() == "Root") {
            return;
        }
        if (item != null) {
            if (item.getValue() instanceof ItemContainer) {
                if (((ItemContainer) item.getValue()).getItemContainers().size() > 0) {
                    for (int i = ((ItemContainer) item.getValue()).getItemContainers().size() - 1; i >= 0; i--) {
                        FarmVis.getChildren().remove(((ItemContainer) item.getValue()).getItemContainers().get(i).getRectangle());
                        FarmVis.getChildren().remove(((ItemContainer) item.getValue()).getItemContainers().get(i).getLabel());
                        ((ItemContainer) item.getValue()).getItemContainers().remove(i);
                    }
                }
                if (((ItemContainer) item.getValue()).getItems().size() > 0) {
                    for (int i = ((ItemContainer) item.getValue()).getItems().size() - 1; i >= 0; i--) {
                        FarmVis.getChildren().remove(((ItemContainer) item.getValue()).getItems().get(i).getRectangle());
                        FarmVis.getChildren().remove(((ItemContainer) item.getValue()).getItems().get(i).getLabel());
                        ((ItemContainer) item.getValue()).getItems().remove(i);
                    }
                }
                FarmVis.getChildren().remove(((ItemContainer) item.getValue()).getRectangle());
                FarmVis.getChildren().remove(((ItemContainer) item.getValue()).getLabel());
                ArrayList<ItemContainer> temp = ((ItemContainer) item.getParent().getValue()).getItemContainers();
                temp.remove(((ItemContainer) item.getValue()));
                ((ItemContainer) item.getParent().getValue()).setItemContainers(temp);
                item.getParent().getChildren().remove(item);
            } else {
                FarmVis.getChildren().remove(((Item) item.getValue()).getRectangle());
                FarmVis.getChildren().remove(((Item) item.getValue()).getLabel());
                ((ItemContainer) item.getParent().getValue()).removeItem(((Item) item.getValue()));
                item.getParent().getChildren().remove(item);
            }


        }
        System.out.println("onDelete");
    }

    //When user click add Item will allow user to add new Item to the tree
    public void onAddItem() {
        TreeItem<ItemContainer> item = (TreeItem<ItemContainer>) Farm.getSelectionModel().getSelectedItem();
        if (item != null) {
            Item newitem = new Item("Item", item.getValue().getLocationX() + 10, item.getValue().getLocationY() + 10, 100, 50, 20);
            item.getValue().addItem(newitem);
            TreeItem newTreeItem = new TreeItem(newitem);
            item.getChildren().add(newTreeItem);
            addItemToVis(newitem);
        }
    }

    //When user click add ItemContainer will allow user to add new ItemContainer to the tree
    public void onAddItemContainer() {
        TreeItem<ItemContainer> item = (TreeItem<ItemContainer>) Farm.getSelectionModel().getSelectedItem();
        if (item != null) {
            ItemContainer newitemcontainer = new ItemContainer("Item Container", item.getValue().getLocationX() + 10, item.getValue().getLocationY() + 10, 100, 50, 20);
            item.getValue().addItemContainer(newitemcontainer);
            TreeItem newTreeItem = new TreeItem(newitemcontainer);
            item.getChildren().add(newTreeItem);
            addItemToVis(newitemcontainer);
        }
    }

    public void onChangeMarketValue() {
        TreeItem item = (TreeItem) Farm.getSelectionModel().getSelectedItem();
        TextInputDialog value = new TextInputDialog(("$") + "Enter new Value");
        value.setHeaderText("Enter new Value ($):");
        value.showAndWait();
        if (item != null) {
            if (value.getResult() != null) {
                if (item.getValue() instanceof ItemContainer) {
                    ItemContainer temp = (ItemContainer) item.getValue();
                    temp.setValue(Float.valueOf(value.getResult()));
                } else {
                    Item temp = (Item) item.getValue();
                    temp.setValue(Float.valueOf(value.getResult()));
                }
                Farm.refresh();
            }

        }
        if (item.getValue() instanceof ItemContainer) {      // ItemContainer
            commandLabel.setText("Item Container Commands");
            ItemContainer myItemContainer = ((ItemContainer) item.getValue());
            FarmVisitor visitor = new ConcreteFarmVisitor();
            float totalValue = myItemContainer.acceptValue(visitor);
            valueLabel.setText("Current Market Value: " + ((int) totalValue));
        } else {     // Item
            commandLabel.setText("Item Commands");
            Item myItem = ((Item) item.getValue());
            FarmVisitor visitor = new ConcreteFarmVisitor();
            float totalValue = myItem.acceptValue(visitor);
            valueLabel.setText("Current Market Value: " + ((int) totalValue));
        }
    }

    public void onPanic(){
        System.out.println("Panic Button pressed. Stopping Drone.");
        try {
            drone_real.panic();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //Functionality to run Virtual Drone
    public void runVirtual() {
        if(new_scan.isSelected()==false && !new_vist.isSelected() && new_home.isSelected()==false) {
            none_selected.setVisible(true);
        }
        else {

            if (new_scan.isSelected()) {
                none_selected.setVisible(false);
                item_error.setVisible(false);
                drone.scanFarm();
                new_scan.setSelected(false);
            }
            if (new_vist.isSelected()) {
                none_selected.setVisible(false);

                if (Farm.getSelectionModel().getSelectedItem() == null) {
                    item_error.setVisible(true);
                } else {
                    drone.visitItem(Farm);
                    item_error.setVisible(false);
                    new_vist.setSelected(false);
                }
            }
            if (new_home.isSelected()) {
                none_selected.setVisible(false);
                item_error.setVisible(false);
                drone.returnHome();
                new_home.setSelected(false);
            }
        }

    }
    //Functionally for new Drone IRL
    public void runReal(){
        Adapter_drone adapterDrone = new Adapter_drone(drone_real, drone);
        if(new_scan.isSelected()==false && new_vist.isSelected()==false && new_home.isSelected()==false) {
            none_selected.setVisible(true);
        } else {
            if (new_scan.isSelected()) {
                none_selected.setVisible(false);
                item_error.setVisible(false);
                adapterDrone.scanFarm();
                new_scan.setSelected(false);
            }
            if (new_vist.isSelected()) {
                none_selected.setVisible(false);

                if (Farm.getSelectionModel().getSelectedItem() == null) {
                    item_error.setVisible(true);
                } else {
                    adapterDrone.visitItem(Farm);
                    item_error.setVisible(false);
                    new_vist.setSelected(false);
                }
            }
            if (new_home.isSelected()) {
                none_selected.setVisible(false);
                item_error.setVisible(false);
                adapterDrone.returnHome();
                new_home.setSelected(false);
            }
        }

    }

}