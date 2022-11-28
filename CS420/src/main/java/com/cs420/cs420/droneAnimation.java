package com.cs420.cs420;

import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.util.ArrayList;

public class droneAnimation implements Target {
    private ArrayList transitions = new ArrayList();
    private SequentialTransition st;
    private ImageView drone = null;
    private double x_offset = 0;
    private double y_offset = 0;


    // Constructor
    public droneAnimation(ImageView Drone){
        this.drone = Drone;
        if(drone != null){
            this.x_offset = drone.getX();
            this.y_offset = drone.getY();
            this.st = new SequentialTransition(drone);
        }
    }

    // Add transition
    public void addTransition(double x, double y){
        //  TODO: Set speed based on real world speed
        TranslateTransition t_temp = new TranslateTransition(Duration.seconds(1));
        System.out.println(x-this.x_offset);
        System.out.println(y-this.y_offset);
        t_temp.setToX(x-this.x_offset);
        t_temp.setToY(y-this.y_offset);
        t_temp.setAutoReverse(false);
        transitions.add(t_temp);
    }

    // Rotation Transitions
    public void addRotationTransition(double degree){
        //  TODO: Set speed based on real world speed
        RotateTransition t_temp = new RotateTransition(Duration.seconds(1), drone);
        System.out.println("Rotate "+degree+" Degrees.");
        t_temp.setAxis(Rotate.Z_AXIS);
        t_temp.setByAngle(degree);
        t_temp.setAutoReverse(false);
        transitions.add(t_temp);
    }

   public void clearTransitions(){
        this.transitions = new ArrayList();
        this.st = new SequentialTransition(drone);
   }

    // return Home
    @Override
    public void returnHome(){
        this.st.stop();
        this.clearTransitions();
        this.addTransition(200,60);
        this.runSequence();
    }
    @Override
    public void scanFarm(){
        this.addTransition(0,0);
        this.addTransition(0,720);
        this.addRotationTransition(-90);
        for (int i = 1; i < 7; i++){
            if (i % 2 == 0) {
                this.addTransition(i * 80, 0);
                this.addRotationTransition(90);
                this.addTransition(i * 80, 720);
                this.addRotationTransition(-90);
            } else{
                this.addTransition(i * 80, 720);
                this.addRotationTransition(-90);
                this.addTransition(i * 80, 0);
                this.addRotationTransition(90);
            }
        }
        this.addTransition(520,720);
        this.addRotationTransition(-90);
        this.addTransition(520, 0);
        this.addRotationTransition(180);

        // return home
        this.addTransition(200,60);
        this.runSequence();
    }

    // Visit Item
    @Override
    public void visitItem(TreeView farm){
        TreeItem item = (TreeItem) farm.getSelectionModel().getSelectedItem();
        if(item.getValue() instanceof ItemContainer){
            this.st.stop();
            this.clearTransitions();
            this.addTransition(((ItemContainer) item.getValue()).getLocationX() + (((ItemContainer) item.getValue()).getWidth() / 2) - 40, ((ItemContainer) item.getValue()).getLocationY() + (((ItemContainer) item.getValue()).getLength() / 2) - 40);
            this.addRotationTransition(360);
            this.addTransition(200,60);
            this.runSequence();
        }
        else{
            this.st.stop();
            this.clearTransitions();
            this.addTransition(((Item) item.getValue()).getLocationX() + (((Item) item.getValue()).getWidth() / 2) - 40, ((Item) item.getValue()).getLocationY() + (((Item) item.getValue()).getLength() / 2) - 40);
            this.addRotationTransition(360);
            this.addTransition(200,60);
            this.runSequence();
        }

    }
    
    public void runSequence(){
        this.st.getChildren().addAll(transitions);
        this.st.play();
        st.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                clearTransitions();
            }
        });

    }
}
