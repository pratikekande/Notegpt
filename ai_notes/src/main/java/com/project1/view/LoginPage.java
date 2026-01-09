package com.project1.view;

import com.project1.configuration.FirebaseInitialization; // Import the init class
import com.project1.controller.UserController;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginPage extends Application {

    private Stage primaryStage;
    private Scene loginScene, homeScene;
    private UserController userController = new UserController();
    
    // Variables for window dragging (since style is TRANSPARENT)
    private double xOffset = 0;
    private double yOffset = 0;

    // Method to initialize the login page
    public void getLoginPage(Stage primaryStage){
        this.primaryStage = primaryStage;
        initLoginScene();
    }

    // Method to initialize the login scene
    private void initLoginScene() {
        
        ImageView logo = new ImageView("Assets/Images/Notegpt.png"); // Fixed slash direction for cross-platform compatibility
        logo.setFitWidth(120);
        logo.setPreserveRatio(true);

        Label title = new Label("Login");
        title.setStyle("-fx-font-size:25; -fx-font-weight: bold; -fx-pref-width: 300; -fx-pref-height: 30; -fx-alignment: CENTER; -fx-text-fill: #FFFFFF");

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

        Label output = new Label();
        output.setStyle("-fx-text-fill: white;");
        
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-pref-width: 70; -fx-min-height: 30; -fx-background-radius: 15; -fx-background-color: #2196F3; -fx-text-fill: #FFFFFF");
        
        Label signupButton = new Label("Signup");
        signupButton.setStyle("-fx-background-radius: 15; -fx-text-fill: white");

        // Set action for the login button
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                if(!userTextField.getText().isEmpty() && !passTextField.getText().isEmpty()){
                    if(userController.authenticateUser(userTextField.getText(), passTextField.getText())){
                        
                        initUserScene(userTextField.getText());
                        primaryStage.setScene(homeScene);

                        userTextField.clear();
                        passTextField.clear();
                    } else{
                        output.setText("Invalid Username or Password");
                    }
                } else{
                    output.setText("Please Enter Username and Password");
                }
            }
        });

        // Set action for the signup button
        signupButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent Event){
                showSignupScene();
                userTextField.clear();
                passTextField.clear();
            }
        });

        // Style the labels
        userLabel.setStyle("-fx-text-fill: white");
        passLabel.setStyle("-fx-text-fill: white");

        // Create VBox layouts for the fields and buttons
        VBox fieldBox1 = new VBox(10, userLabel, userTextField); 
        fieldBox1.setMaxSize(300, 30);
        VBox fieldBox2 = new VBox(10, passLabel, passTextField); 
        fieldBox2.setMaxSize(300, 30);

        // Main VBox layout for the login page
        VBox logiBox = new VBox(20, header, fieldBox1, fieldBox2, loginButton, signupButton, output);
        logiBox.setStyle("-fx-pref-height : 200; -fx-alignment : CENTER; -fx-padding: 30; -fx-background-color: rgba(0, 0, 0);");
        logiBox.setMaxSize(300, 200);

        // Enable Window Dragging
        logiBox.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        logiBox.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        Rectangle clip = new Rectangle(300, 650);
        clip.setArcWidth(40);
        clip.setArcHeight(40);
        logiBox.setClip(clip);

        loginScene = new Scene(logiBox, 300, 650);
        loginScene.setFill(Color.TRANSPARENT);
    }

    // Method to initialize the user scene
    private void initUserScene(String userName){
        // Assuming HomePage is correctly implemented in your project
        HomePage homePage = new HomePage();
        homePage.setStage(primaryStage);
        homePage.setUserName(userName);
        
        // This relies on HomePage being implemented correctly
        homeScene = new Scene(homePage.getView(this::handeleLogout), 300, 650);
        homeScene.setFill(Color.TRANSPARENT);
        homePage.setScene(homeScene);
    }

    // Method to get the login scene
    public Scene getLogiScene() {
        return loginScene;
    }

    // Method to show the login scene
    public void showLoginScene() {
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    // Method to show the signup scene
    private void showSignupScene() {
        SignupPage signupPage = new SignupPage();
        // pass "this::handleBack" so the Signup page knows how to return here
        Scene signupScene = new Scene(signupPage.createSignupScene(this::handleBack), 300, 650);
        signupScene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(signupScene);
        primaryStage.show();
    }

    //Method to handle logout action
    private void handeleLogout() {
        primaryStage.setScene(loginScene);
    }

    // Method to handle back action from signup
    private void handleBack() {
        loginScene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(loginScene);
    }

    @Override
    public void start(Stage priStage) throws Exception {
        
        // --- CRITICAL FIX: Initialize Firebase at startup ---
        FirebaseInitialization.init();
        
        getLoginPage(priStage);
        primaryStage.setScene(getLogiScene());
        primaryStage.setTitle("ChatApp");
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}