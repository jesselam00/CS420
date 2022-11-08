package com.cs420.cs420;

public class ConcreteFarmVisitor implements FarmVisitor {

    @Override
    public float visitPrice(Item item){
        float price = item.getPrice();
        return price;
    };

    @Override
    public float visitPrice(ItemContainer itemContainer){
        // sum prices of items in container
        float sum=0;
        sum = sum + itemContainer.getPrice();
        int length=itemContainer.getItems().size();
        for (int i = 0; i < length; i++){
            sum = sum + itemContainer.getItems().get(i).getPrice();
        }

        // sum prices of item containers in container
        length=itemContainer.getItemContainers().size();
        if (length == 0){
            return sum;
        } else{
            for (int i = 0; i < length; i++) {
                sum = sum + visitPrice(itemContainer.getItemContainers().get(i));
            }
        }
        return sum;
    }

    @Override
    public float visitValue(Item item){
        float value = item.getValue();
        return value;
    };

    @Override
    public float visitValue(ItemContainer itemContainer){
        // sum prices of items in container
        float sum=0;
        int length=itemContainer.getItems().size();
        for (int i = 0; i < length; i++){
            sum = sum + itemContainer.getItems().get(i).getValue();
        }

        // sum prices of item containers in container
        length=itemContainer.getItemContainers().size();
        if (length == 0){
            return sum;
        } else{
            for (int i = 0; i < length; i++) {
                sum = sum + visitValue(itemContainer.getItemContainers().get(i));
            }
        }
        return sum;
    }

//    @Override
//    public float visit(ItemContainer itemContainer){
//        float sum=0;
//
//        // sum prices of items in container
//        int length=itemContainer.getItems().size();
//        for (int i = 0; i < length; i++){
//            sum = sum + itemContainer.getItems().get(i).getPrice();
//        }
//
//        // sum prices of item containers in container
//        length=itemContainer.getItemContainers().size();
//        for (int i = 0; i < length; i++){
//            sum = sum + itemContainer.getItemContainers().get(i).getPrice();
//        }
//        return sum;
//    }
}
