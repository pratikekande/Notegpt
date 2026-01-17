package com.project1.controller;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

import com.google.firebase.auth.FirebaseAuth; // Keep this for the object type
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.project1.configuration.FirebaseInitialization; // Import your helper

import javafx.scene.control.Alert;

public class UserController {
    
    public boolean authenticateUser(String userName, String password){
        try{
            // Firebase Web API key
            String apiKey = "AIzaSyAGT4xdEwl_sVy-l1tm5EzDaZY7DfVykl8";

            // FIX: Removed the extra space after 'key='
            URL url = new URL("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + apiKey);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);

            // Build the JSON request body
            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("email", userName);
            jsonRequest.put("password", password);
            jsonRequest.put("returnSecureToken", true);
            // System.out.println(jsonRequest.toString()); // Good for debugging, remove for production

            // Send the JSON request to the server
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonRequest.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Check the response code to determine if login was successful
            if(conn.getResponseCode() == 200){
                return true;
            } else{
                // Login failed
                showAlert("Error", "Invalid email or password.");
                return false;
            }
        } catch(Exception e){
            e.printStackTrace();
            showAlert("Error", "Login connection failed: " + e.getMessage());
            return false;
        }
    }

    public boolean handelSignup(String userName, String password){

        if(userName.isEmpty() || password.isEmpty()){
            showAlert("Error", "Username and password cannot be empty.");
            return false;
        }

        try {
            // Create a new user request
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(userName)
                .setPassword(password)
                .setDisabled(false);

            // FIX: Use your helper class to get the Auth instance
            // This prevents the "Default app doesn't exist" error
            // (Assumes you called FirebaseInitialization.init() in your Main class)
            FirebaseAuth auth = FirebaseInitialization.getFirebaseAuth();
            
            UserRecord userRecord = auth.createUser(request);
            
            System.out.println("Successfully created user: " + userRecord.getUid());
            showAlert("Success", "User created successfully.");
            return true; 

        } catch(FirebaseAuthException e){
            e.printStackTrace();
            showAlert("Error", "Failed to create user: " + e.getMessage());
            return false;
        } catch(IllegalStateException e) {
             // Catch the specific initialization error to give a better hint
             e.printStackTrace();
             showAlert("System Error", "Database not connected. Please restart the app.");
             return false;
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