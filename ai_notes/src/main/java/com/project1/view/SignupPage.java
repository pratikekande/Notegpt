package com.project1.view;

import com.project1.controller.UserController;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SignupPage {
    
    private UserController ai_userController = new UserController();

    /*
    Creates and return signup scene.

    @param ai_backHandler A Runnable to handle the action of going back to the previous scene.
    @return the signup scene
    */

    public Parent createSignupScene(Runnable ai_backHandler){

        ImageView ai_logo = new ImageView("Assets//Images//Notegpt.png");
        ai_logo.setFitWidth(120);
        ai_logo.setPreserveRatio(true);
        Label ai_title = new Label("Sign up");
        ai_title.setStyle("-fx-font-size:25; -fx-font-weight: bold; -fx-pref-width: 600; -fx-pref-height: 30; -fx-alignment: CENTER; -fx-text-fill: #FFFFFFF");

        VBox ai_header = new VBox(20, ai_logo, ai_title);
        ai_header.setAlignment(Pos.CENTER);

        // Uusername and Password Labels
        Label ai_userLabel = new Label("Username:");
        TextField ai_userTextField = new TextField();
        ai_userTextField.setPromptText("Enter Username");
        ai_userTextField.setStyle("-fx-max-width: 270; -fx-min-height: 30; -fx-background-radius: 15");
        ai_userTextField.setFocusTraversable(false);

        Label ai_passLabel = new Label("Password:");
        TextField ai_passTextField = new TextField();
        ai_passTextField.setPromptText("Enter Password");
        ai_passTextField.setStyle("-fx-max-width: 270; -fx-min-height: 30; -fx-background-radius: 15");
        ai_passTextField.setFocusTraversable(false);

        // Button to trigger sigup action
        Button ai_signupButton = new Button("Signup");
        ai_signupButton.setStyle("-fx-pref-width: 70; -fx-min-height: 30; -fx-background-radius: 15; -fx-background-color : #2196F3; -fx-text-fill: #FFFFFF");

        //Label to navigate to login scene
        Label ai_loginButton = new Label("Login");
        ai_loginButton.setStyle("-fx-background-radius: 15; -fx-text-fill: white");

        // Label for output messages
        Label ai_output = new Label();
        ai_output.setStyle("-fx-text-fill: white");

        // Style the labels
        ai_userLabel.setStyle("-fx-text-fill: white");
        ai_passLabel.setStyle("-fx-text-fill: white");

        // VBox layouts for the fields and buttons
        VBox ai_fieldBox1 = new VBox(10, ai_userLabel, ai_userTextField); // VBox for username
        ai_fieldBox1.setMaxSize(300, 30);
        VBox ai_fieldBox2 = new VBox(10, ai_passLabel, ai_passTextField); // VBox for password
        ai_fieldBox2.setMaxSize(300, 30);

        // Set action for the signup button
        ai_signupButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent ai_Event) {
                if(!ai_userTextField.getText().isEmpty() && !ai_passTextField.getText().isEmpty()){
                    if(ai_userController.handelSignup(ai_userTextField.getText(), ai_passTextField.getText())){
                        
                        LoginPage ai_loginPage = new LoginPage();
                        ai_loginPage.getLogiScene();
                    } else{

                        ai_output.setText("User not Registred");
                    }
                } else{
                    ai_output.setText("Please Enter Username and Password");
                }
            }
            
        });

        // Set action for the login button
        ai_loginButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent ai_event) {
                ai_backHandler.run();
            }
            
        });


        // Main VBox layout for the signup page
        VBox ai_logiBox = new VBox(20, ai_header, ai_fieldBox1, ai_fieldBox2, ai_signupButton, ai_loginButton, ai_output);
        ai_logiBox.setStyle("-fx-pref-height : 200; -fx-alignment : CENTER; -fx-padding: 30; -fx-background-color: rgba(0, 0, 0);");
        ai_logiBox.setMaxSize(300, 200);
        ai_logiBox.setAlignment(Pos.CENTER);

        Rectangle clip = new Rectangle(300, 650);
        clip.setArcWidth(40);
        clip.setArcHeight(40);
        ai_logiBox.setClip(clip);
        return ai_logiBox;
    }
}
