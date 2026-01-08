package com.project1.controller;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import javafx.scene.control.Alert;

public class UserController {
    
    public boolean authenticateUser(String userName, String password){
        try{
            // Firebase Web API key
            String apiKey = "AIzaSyAGT4xdEwl_sVy-l1tm5EzDaZY7DfVykl8";

            URL url = new URL("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key= " + apiKey);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);

            // Build the JSON request body
            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("email", userName);
            jsonRequest.put("password", password);
            jsonRequest.put("returnSecureToken", true);
            System.out.println(jsonRequest.toString());

            // Send the JSON request to the server
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonRequest.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Check the response code to determine if login was successful
            if(conn.getResponseCode() == 200){

                // Login successful
                return true;
            } else{
                // Login failed, show error.alert
                showAlert("Error", "Failed to log in user.");
                return false;
            }
        } catch(Exception e){
            e.printStackTrace();
            // Show alert if an exception occurs
            showAlert("Error", "Failed to log in user: " + e.getMessage());
            return false;
        }
    }

    public boolean handelSignup(String userName, String password){

        if(userName.isEmpty() || password.isEmpty()){
            showAlert("Error", "UserName and password cannot be empty.");
            return false; // Return false if email or password is empty
        }

        try {
            // Create a new user request with provided email and password
            UserRecord.CreateRequest request = new UserRecord.CreateRequest().setEmail(userName).setPassword(password).setDisabled(false); // Enable the user after creation

            // Create the user in Firebase Authentication
            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
            System.out.println("Successfully created user: " + userRecord.getUid());
            showAlert("Success", "User created successfully.");
            return true; // Return true if signup was successful 
        } catch(FirebaseAuthException e){
            e.printStackTrace();
            showAlert("Error", "Failed to create user :" + e.getMessage());
            return false; // Return false if signup failed
        }
    }

    private void showAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
