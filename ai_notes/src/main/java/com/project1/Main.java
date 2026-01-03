package com.project1;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
    public static void main(String[] args){
        System.out.println("Hello World");
        launch(args);
    }

    @Override
    public void start(Stage myStage) {
        myStage.show(); 
    }
}