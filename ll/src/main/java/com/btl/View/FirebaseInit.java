package com.btl.View;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.InputStream;

public class FirebaseInit {
    public static void initialize() {
        try {
            InputStream serviceAccount = FirebaseInit.class.getClassLoader()
                    .getResourceAsStream("firebase-adminsdk.json");

            if (serviceAccount == null) {
                System.out.println("❌ JSON file not found.");
                return;
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println("✅ Firebase Initialized");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}