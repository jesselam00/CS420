package com.cs420.cs420;

import javafx.scene.control.TreeView;

import java.io.IOException;

public class Adapter_drone implements Target  {
    private droneIRL droneI;
    private droneAnimation droneA;

    public Adapter_drone(droneIRL droneI, droneAnimation droneA){
        this.droneA=droneA;
        this.droneI=droneI;
    }


    public void scanFarm() {
        droneA.scanFarm();
        try{
            droneI.scanFarm();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void returnHome(){
        droneA.returnHome();
        try {
            droneI.returnHome();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void visitItem(TreeView farm){

        droneA.visitItem(farm);
        try {
            droneI.visitItem(farm);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }





}
