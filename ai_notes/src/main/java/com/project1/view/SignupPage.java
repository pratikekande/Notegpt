package com.project1.view;

import com.project1.controller.UserController;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class SignupPage {
    
    // Ensure this controller class is the one we fixed in the previous step
    private UserController userController = new UserController();

    public Parent createSignupScene(Runnable backHandler){

        // --- UI Setup (Same as before) ---
        ImageView logo = new ImageView("Assets//Images//Notegpt.png");
        logo.setFitWidth(120);
        logo.setPreserveRatio(true);
        Label title = new Label("Sign up");
        title.setStyle("-fx-font-size:25; -fx-font-weight: bold; -fx-pref-width: 600; -fx-pref-height: 30; -fx-alignment: CENTER; -fx-text-fill: #FFFFFF");

        VBox header = new VBox(20, logo, title);
        header.setAlignment(Pos.CENTER);

        Label userLabel = new Label("Username:");
        TextField userTextField = new TextField();
        userTextField.setPromptText("Enter Username");
        userTextField.setStyle("-fx-max-width: 270; -fx-min-height: 30; -fx-background-radius: 15");
        userTextField.setFocusTraversable(false);

        Label passLabel = new Label("Password:");
        PasswordField passTextField = new PasswordField();
        passTextField.setPromptText("Enter Password");
        passTextField.setStyle("-fx-max-width: 270; -fx-min-height: 30; -fx-background-radius: 15");
        passTextField.setFocusTraversable(false);

        Button signupButton = new Button("Signup");
        signupButton.setStyle("-fx-pref-width: 70; -fx-min-height: 30; -fx-background-radius: 15; -fx-background-color : #2196F3; -fx-text-fill: #FFFFFF");

        Label loginButton = new Label("Login");
        loginButton.setStyle("-fx-background-radius: 15; -fx-text-fill: white");

        Label output = new Label();
        output.setStyle("-fx-text-fill: white");

        userLabel.setStyle("-fx-text-fill: white");
        passLabel.setStyle("-fx-text-fill: white");

        VBox fieldBox1 = new VBox(10, userLabel, userTextField);
        fieldBox1.setMaxSize(300, 30);
        VBox fieldBox2 = new VBox(10, passLabel, passTextField);
        fieldBox2.setMaxSize(300, 30);

        // --- FIX IS HERE ---
        signupButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent Event) {
                if(!userTextField.getText().isEmpty() && !passTextField.getText().isEmpty()){
                    // Call the controller
                    boolean success = userController.handelSignup(userTextField.getText(), passTextField.getText());
                    
                    if(success){
                        // SUCCESS: Use the backHandler to return to the Login Screen
                        // This switches the view effectively
                        backHandler.run();
                    } else {
                        // FAILURE: Stay on page and show error
                        output.setText("User Registration Failed");
                    }
                } else {
                    output.setText("Please Enter Username and Password");
                }
            }
        });

        // Login Button (Navigation)
        loginButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                backHandler.run();
            }
        });

        VBox logiBox = new VBox(20, header, fieldBox1, fieldBox2, signupButton, loginButton, output);
        logiBox.setStyle("-fx-pref-height : 200; -fx-alignment : CENTER; -fx-padding: 30; -fx-background-color: rgba(0, 0, 0);");
        logiBox.setMaxSize(300, 200);
        logiBox.setAlignment(Pos.CENTER);

        Rectangle clip = new Rectangle(300, 650);
        clip.setArcWidth(40);
        clip.setArcHeight(40);
        logiBox.setClip(clip);
        return logiBox;
    }
}