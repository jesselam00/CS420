package com.cs420.cs420;

import javafx.scene.control.TreeView;

import java.io.IOException;

public interface Target {

    public void scanFarm() throws InterruptedException, IOException;
    public void returnHome() throws InterruptedException, IOException;
    public void visitItem(TreeView farm) throws InterruptedException, IOException;

}
