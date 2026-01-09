package com.project1.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.FirestoreClient;

public class FirebaseInitialization {

    private static final Logger LOGGER = Logger.getLogger(FirebaseInitialization.class.getName());

    private static FirebaseApp firebaseApp;

    static {
        init();
    }

    public static void init() {
    if (firebaseApp == null) {
        try {
            // CORRECT WAY: Load from classpath (works in IDE and JAR)
            // ensure the json file is actually in src/main/resources/
            var serviceAccount = FirebaseInitialization.class.getClassLoader().getResourceAsStream("firebase_key.json");

            if (serviceAccount == null) {
                throw new IOException("File not found: firebase_key.json");
            }

            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setProjectId("notegpt-a3bac")
                .build();

            firebaseApp = FirebaseApp.initializeApp(options);
            LOGGER.info("Firebase Initialized Successfully");

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error Initializing Firebase: " + e.getMessage(), e);
            // Optionally: Exit the app here because it cannot function without DB
            // System.exit(1); 
        }
    }
}

    public static FirebaseAuth getFirebaseAuth() {
        if(firebaseApp == null){
            throw new IllegalStateException("FirebaseApp has not been initialized. Call init() first.");
        }
        return FirebaseAuth.getInstance(firebaseApp);
    }

    public static Firestore getFireStore() {
    if (firebaseApp == null) {
        throw new IllegalStateException("FirebaseApp has not been initialized. Call init() first.");
    }
    return FirestoreClient.getFirestore(firebaseApp);
}

}
