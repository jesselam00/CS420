package com.cs420.cs420;

import com.cs420.cs420.jdrone.control.physical.tello.TelloDrone;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class droneIRL implements Target {
    private static droneIRL droneirl;
    private TelloDrone tello = new TelloDrone();

    private droneIRL() throws InterruptedException, IOException{
    }

    public static droneIRL getInstance() {
        if (droneirl == null) {
            try {
                droneirl = new droneIRL();
            } catch (SocketException e) {
                throw new RuntimeException(e);
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return droneirl;
    }

    public TelloDrone getTello(){
        return this.tello;
    }


    public void panic() throws InterruptedException, IOException{
        this.tello.emergencyStop();
        this.tello = new TelloDrone();
    }

    private void L_Move() throws InterruptedException, IOException{
        this.tello.gotoXY(370,0,75);
        this.tello.turnCCW(90);
        this.tello.gotoXY(30,0,75);
        this.tello.turnCCW(90);
    }

    private void RL_Move() throws InterruptedException, IOException{
        this.tello.gotoXY(370,0,75);
        this.tello.turnCW(90);
        this.tello.gotoXY(30,0,75);
        this.tello.turnCW(90);
    }

    @Override
    public void scanFarm() throws InterruptedException, IOException{

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // -X is backwards Tilt, +x is forwards Tilt
                    // -Y is left Tilt, +Y is Right Tilt
                    tello.activateSDK();
                    tello.takeoff();
                    tello.increaseAltitude(75);
                    tello.gotoXY(-30,100, 75);
                    for(int i = 0; i < 4; i++){
                        try {
                            L_Move();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            RL_Move();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    tello.gotoXY(370,0,75);
                    tello.gotoXY(-340,200,75);
                    tello.land();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

    }
    @Override
    public void visitItem(TreeView farm) throws InterruptedException, IOException{
        int mid_x, mid_y;
        TreeItem item = (TreeItem) farm.getSelectionModel().getSelectedItem();
        if(item.getValue() instanceof ItemContainer){
            ItemContainer temp = (ItemContainer) item.getValue();
            mid_x = (int) (temp.getLocationY() + temp.getWidth() / 2)/2;
            mid_y = - (int) (temp.getLocationX() + temp.getLength() / 2)/2;
        }
        else{
            Item temp = (Item) item.getValue();
            mid_x = (int) (temp.getLocationY() + temp.getWidth() / 2)/2;
            mid_y = - (int) (temp.getLocationX() + temp.getLength() / 2)/2;
        }
        // Assuming that drone starts at command center
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    tello.activateSDK();
                    tello.takeoff();
                    tello.increaseAltitude(100);
                    tello.gotoXY(mid_x - 30, mid_y + 100, 75);
                    tello.turnCW(360);
                    tello.gotoXY(-mid_x + 30, -mid_y - 100, 75);
                    tello.land();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }).start();

    }
    @Override
    public void returnHome() throws InterruptedException, IOException{
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    tello.flip("f");
                    tello.flip("b");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

    }



    public void demo_flight() throws InterruptedException, IOException {
        //Drone go BRRRR
        tello.activateSDK();
        tello.takeoff();
        tello.increaseAltitude(75);
        tello.flyForward(100);
        tello.flip("f");
        tello.flyBackward(100);
        tello.land();
    }

}
