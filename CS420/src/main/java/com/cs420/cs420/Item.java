package com.cs420.cs420;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.Random;

public class Item implements GenericItem{
    //Helps to input name on Tree leaf instead of scripture :https://softwareengineering.stackexchange.com/questions/309199/treeitem-containing-non-string-object-displaying-strange-text
    @Override
    public String toString(){
        return this.name;
    }
    private String name = "New";
    private float price = 0;
    //Location X is the
    private int location_x = 0;
    private int location_y = 0;
    // Length is the x-axis
    private float length = 0;
    // Width is the y-axis
    private float width = 0;
    // Height is the Z axis
    private float height = 0;
    private Rectangle rectangle = new Rectangle(0, 0);

    private Label label = new Label("");

    public Item(String name, int location_x, int location_y, float length, float width, float height){
        this.name = name;
        this.location_x = location_x;
        this.location_y = location_y;
        this.length = length;
        this.width = width;
        this.height = height;
        this.rectangle.setHeight(this.length);
        this.rectangle.setWidth(this.width);
        this.label.setText(this.name);
        this.rectangle.setFill(Color.color(new Random().nextDouble(0,1.0),new Random().nextDouble(0,1.0),new Random().nextDouble(0,1.0),0.2));
        this.rectangle.setStroke(Color.color(0,0,0,1.0));
    }


    // To add text to the rectangle formed
    @Override
    public Label getLabel() {
        return this.label;
    }
    @Override
    public void setLabel(String s) {
        this.label.setText(s);
    }
    //The name of the item
    @Override
    public String getName(){
        return this.name;
    }
    @Override
    public void setName(String newName){
        this.name=newName;
    }
    //Price of Item
    @Override
    public float getPrice(){
        return this.price;
    }
    @Override
    public void setPrice(float newPrice){
        this.price=newPrice;
    }
    //X coordinate position  of Item
    @Override
    public int getLocationX(){
        return this.location_x;
    }
    @Override
    public void setLocationX(int newLocationX){
        this.location_x=newLocationX;
    }
    //Y coordinate position  of Item
    @Override
    public int getLocationY(){
        return this.location_y;
    }
    @Override
    public void setLocationY(int newLocationY){
        this.location_y=newLocationY;
    }
    //Length of Item
    @Override
    public float getLength(){
        return this.length;
    }
    @Override
    public void setLength(float newLength){
        this.length=newLength;
    }
    //Width of Item
    @Override
    public float getWidth(){
        return this.width;
    }
    @Override
    public void setWidth(float newWidth){
        this.width=newWidth;
    }
    //Height of Item
    @Override
    public float getHeight(){
        return this.height;
    }
    @Override
    public void setHeight(float newHeight){
        this.height=newHeight;
    }

    // JavaFX rectangle associated with item
    @Override
    public Rectangle getRectangle(){
        return this.rectangle;
    }
    @Override
    public void setRectangle(float length, float width){
        this.rectangle.setWidth(width);
        this.rectangle.setHeight(length);
    }
}
