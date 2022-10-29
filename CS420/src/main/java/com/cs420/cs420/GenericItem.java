package com.cs420.cs420;

import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;

public interface GenericItem {
    public String getName();

    public void setName(String newName);

    public Label getLabel();

    public void setLabel(String s);

    public float getPrice();

    public void setPrice(float newPrice);

    public int getLocationX();

    public void setLocationX(int newLocationX);

    public int getLocationY();

    public void setLocationY(int newLocationY);

    public float getLength();

    public void setLength(float newLength);

    public float getWidth();

    public void setWidth(float newWidth);

    public float getHeight();

    public void setHeight(float newHeight);

    public Rectangle getRectangle();

    public void setRectangle(float length, float width);
}
