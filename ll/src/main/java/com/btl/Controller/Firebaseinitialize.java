package com.btl.Controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.google.api.client.util.store.DataStore;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.v1.FirestoreClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class Firebaseinitialize {

    public static Firestore db;
    // Constants needed for the image upload method
   

    /**
     * Initializes Firebase using the correct classpath resource stream method.
     * This version also includes the Storage Bucket for file uploads.
     * 
     * 
     * 
     */

       public static void initialize() {
        try {
            FileInputStream serviceAccount = new FileInputStream("ll/src/main/resources/Assets/Images/final.json");

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://bridge-to-law.firebaseio.com") // optional for Realtime DB
                    .build();

            FirebaseApp.initializeApp(options);
            System.out.println("✅ Firebase initialized successfully.");
            db = com.google.firebase.cloud.FirestoreClient.getFirestore();
        } catch (IOException e) {
            System.err.println("❌ Failed to initialize Firebase: " + e.getMessage());
        }
    }
    

}
    
