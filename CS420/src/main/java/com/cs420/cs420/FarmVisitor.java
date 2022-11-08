package com.cs420.cs420;

public interface FarmVisitor {

    float visitPrice(Item item);
    float visitPrice(ItemContainer itemContainer);
    float visitValue(Item item);
    float visitValue(ItemContainer itemContainer);

}
