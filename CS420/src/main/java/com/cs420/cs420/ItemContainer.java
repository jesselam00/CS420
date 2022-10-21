package com.cs420.cs420;

import java.util.ArrayList;

public class ItemContainer extends Item{
    private ArrayList<Item> items = new ArrayList<Item>();

    public ItemContainer(String name, int location_x, int location_y, float length, float width, float height){
        super(name, location_x, location_y, length, width, height);
        super.name=name;
    }
    //Helps to input name on Tree Branch instead of scripture :https://softwareengineering.stackexchange.com/questions/309199/treeitem-containing-non-string-object-displaying-strange-text
    @Override
    public String toString(){
        return super.name;
    }

    public ArrayList<Item> getItems(){
        return items;
    }
    public void setItems(ArrayList<Item> items){
        this.items = items;
    }
    public void addItem(Item item){
        this.items.add(item);
    }
    public void removeItem(Item item){
        for(int i = 0;i < this.items.size(); i++){
            if(this.items.get(i).name.equals(item.getName())){
                items.remove(i);
                break;
            }
        }
    }
    public void moveItem(ItemContainer container, Item item){
        container.addItem(item);
        this.removeItem(item);
    }
}
