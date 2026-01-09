package com.project1;

import com.project1.configuration.FirebaseInitialization;
import com.project1.view.LoginPage;

import javafx.application.Application;

public class Main {

    public static void main(String[] args) {
         FirebaseInitialization.init();
        Application.launch(LoginPage.class);
    }
}