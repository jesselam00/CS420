package com.cs420.cs420;

public class Item {
    protected String name = "";
    protected float price = 0;
    //Location X is the
    protected int location_x = 0;
    protected int location_y = 0;
    // Length is the x axis
    protected float length = 0;
    // Width is the y axis
    protected float width = 0;
    // Height is the Z axis
    protected float height = 0;

    public Item(String name, int location_x, int location_y, float length, float width, float height){
        this.name = name;
        this.location_x = location_x;
        this.location_y = location_y;
        this.length = length;
        this.width = width;
        this.height = height;
    }

    //Cant implement Delete Item (Object can't commit suicide)

    //Name of Item
    public String getName(){
        return this.name;
    }
    public void setName(String newName){
        this.name=newName;
    }
    //Price of Item
    public float getPrice(){
        return this.price;
    }
    public void setPrice(float newPrice){
        this.price=newPrice;
    }
    //X coordinate position  of Item

    public int getLocationX(){
        return this.location_x;
    }
    public void setLocationX(int newLocationX){
        this.location_x=newLocationX;
    }
    //Y coordinate position  of Item
    public int getLocationY(){
        return this.location_y;
    }
    public void setLocationY(int newLocationY){
        this.location_x=newLocationY;
    }
    //Length of Item
    public float getLength(){
        return this.length;
    }
    public void setLength(float newLength){
        this.length=newLength;
    }
    //Width of Item
    public float getWidth(){
        return this.width;
    }
    public void setWidth(float newWidth){
        this.width=newWidth;
    }
    //Height of Item
    public float getHeight(){
        return this.height;
    }
    public void setHeight(float newHeight){
        this.width=newHeight;
    }


}
