package com.project1.view;

import com.project1.controller.UserController;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginPage extends Application {

    private Stage ai_primaryStage;
    private Scene ai_loginScene, ai_homeScene;
    private UserController ai_userController = new UserController();
    public static String ai_key;

    // Method to initialize the login page
    public void getLoginPage(Stage ai_primaryStage){
        this.ai_primaryStage = ai_primaryStage;
        initLoginScene();
    }

    //Method to initialize the login scene
    private void initLoginScene() {
        
        ImageView ai_logo = new ImageView("Assets//Images//Notegpt.png"); // add image path
        ai_logo.setFitWidth(120);
        ai_logo.setPreserveRatio(true);

        Label ai_title = new Label("Login");
        ai_title.setStyle("-fx-font-size:25; -fx-font-weight: bold; -fx-pref-width: 300; -fx-pref-height: 30; -fx-alignment: CENTER; -fx-text-fill:#FFFFFFF");

        VBox ai_header = new VBox(20, ai_logo, ai_title);
        ai_header.setAlignment(Pos.CENTER);

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

        Label ai_output = new Label();
        ai_output.setStyle("-fx-text-fill: white;");
        Button ai_loginButton = new Button("Login");
        ai_loginButton.setStyle("-fx-prefwidth: 70; -fx-min-height: 30; -fx-background-radius: 15; -fx-background-color: #2196F3; -fx-text-fill: #FFFFFFF");
        Label ai_signupButton = new Label("Signup");
        ai_signupButton.setStyle("-fx-background-radius: 15; -fx-text-fill: white");

        // Set action for the login button
        // ai_loginButton.setOnAction(new EventHandler<ActionEvent>() {
        //     @Override
        //     public void handle(ActionEvent ai_event){
        //         if(!ai_userTextField.getText().isEmpty() && !ai_passTextField.getText().isEmpty()){
        //             if(ai_userController.authenticateUser(ai_userTextField.getText(), ai_passTextField.getText())){
                        
        //                 initUserScene(ai_userTextField.getText());
        //                 ai_primaryStage.setScene(ai_homeScene);

        //                 ai_userTextField.clear();
        //                 ai_passTextField.clear();
        //             } else{

        //                 ai_output.setText("Invalid Username or Password");
        //             }
        //         } else{
        //             ai_output.setText("Please Enter Username and Password");
        //         }
        //     }
        // });

        // Set action for the signup button
        ai_signupButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent ai_Event){
                showSignupScene();
                ai_userTextField.clear();
                ai_passTextField.clear();
            }
        });

        // Style the labels
        ai_userLabel.setStyle("-fx-text-fill: white");
        ai_passLabel.setStyle("-fx-text-fill: white");

        // Create VBox layouts for the fields and buttons
        VBox ai_fieldBox1 = new VBox(10, ai_userLabel, ai_userTextField); // VBox for username
        ai_fieldBox1.setMaxSize(300, 30);
        VBox ai_fieldBox2 = new VBox(10, ai_passLabel, ai_passTextField); // VBox for password
        ai_fieldBox2.setMaxSize(300, 30);

        // Main VBox layout for the loginn page
        VBox ai_logiBox = new VBox(20, ai_header, ai_fieldBox1, ai_fieldBox2, ai_loginButton, ai_signupButton, ai_output);
        ai_logiBox.setStyle("-fx-pref-height : 200; -fx-alignment : CENTER; -fx-padding: 30; -fx-background-color: rgba(0, 0, 0);");
        ai_logiBox.setMaxSize(300, 200);

        Rectangle clip = new Rectangle(300, 650);
        clip.setArcWidth(40);
        clip.setArcHeight(40);
        ai_logiBox.setClip(clip);

        ai_loginScene = new Scene(ai_logiBox, 300, 650);
        ai_loginScene.setFill(Color.TRANSPARENT);

    }

    // Method to initialize the user scene
    private void initUserScene(String userName){
        HomePage ai_homePage = new HomePage();
        ai_homePage.setStage(ai_primaryStage);
        ai_homePage.setUserName(userName);
        ai_homeScene = new Scene(ai_homePage.getView(this::handeleLogout), 300, 650);
        ai_homeScene.setFill(Color.TRANSPARENT);
        ai_homePage.setScene(ai_homeScene);

    }

    // Method to get the login scene
    public Scene getLogiScene() {
        return ai_loginScene;
    }

    // Method to show the login scene
    public void showLoginScene() {
        ai_primaryStage.setScene(ai_loginScene);
        ai_primaryStage.show();
    }

    // Method to show the signup scene
    private void showSignupScene() {
        SignupPage signupPage = new SignupPage();
        Scene signupScene = new Scene(signupPage.createSignupScene(this::handleBack), 300, 650);
        signupScene.setFill(Color.TRANSPARENT);
        ai_primaryStage.setScene(signupScene);
        ai_primaryStage.show();
    }

    //Method to handle logout action
    private void handeleLogout() {
        ai_primaryStage.setScene(ai_loginScene);
    }

    // Method to handle back action from signup
    private void handleBack() {
        ai_loginScene.setFill(Color.TRANSPARENT);
        ai_primaryStage.setScene(ai_loginScene);
    }

    @Override
    public void start(Stage ai_priStage) throws Exception {
        
        getLoginPage(ai_priStage);
        ai_primaryStage.setScene(getLogiScene());
        ai_primaryStage.setTitle("ChatApp");
        ai_primaryStage.initStyle(StageStyle.TRANSPARENT);
        ai_primaryStage.show();
    }

    
    
}
