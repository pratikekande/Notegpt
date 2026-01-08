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
        if(firebaseApp == null){
            try{
                FileInputStream serviceAccount = new FileInputStream("src/main/resources/firebase_key.json");

                FirebaseOptions options = FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(serviceAccount)).setProjectId("notegpt-a3bac").build();

                firebaseApp = FirebaseApp.initializeApp(options);
            } catch(IOException e){
                LOGGER.log(Level.SEVERE, "Error Initializing Firebase: "+ e.getMessage(), e);
            }
        } else{
            LOGGER.warning("FirebaseApp already initialized. Skipping initialization");
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
