package com.btl.Controller;

public class FirebaseManager {

    // --- YOUR ACTUAL VALUES FROM FIREBASE CONSOLE ---
    public static final String FIREBASE_PROJECT_ID = "bridge-to-law";
    public static final String FIREBASE_WEB_API_KEY = "AIzaSyBnDyBuWkOo9b03FLqWhoHXxZAeTWp1Yqs";

    // Base URL for Firestore REST API
    public static final String FIRESTORE_BASE_URL = "gs://bridge-to-law.firebasestorage.app" +
                                                     FIREBASE_PROJECT_ID + "bridge-to-law";

    private static String currentUserId = null; // The UID of the logged-in user
    private static String currentAuthToken = null; // The Firebase ID Token of the logged-in user

    /**
     * Call this method after a successful login to set the authenticated user's details.
     * Your existing login/backend should provide these values.
     * @param userId The Firebase User ID (UID) of the logged-in user.
     * @param idToken The Firebase ID Token received after authentication.
     */
    public static void setAuthenticatedUser(String userId, String idToken) {
        currentUserId = userId;
        currentAuthToken = idToken;
        System.out.println("FirebaseManager: Authenticated user set. User ID: " + currentUserId);
    }

    /**
     * Clears the authenticated user's details, typically on logout.
     */
    public static void clearAuthenticatedUser() {
        currentUserId = null;
        currentAuthToken = null;
        System.out.println("FirebaseManager: Authenticated user cleared.");
    }

    public static String getCurrentUserId() {
        return currentUserId;
    }

    public static String getCurrentAuthToken() {
        return currentAuthToken;
    }

    /**
     * Checks if a user is currently authenticated.
     * @return true if both userId and authToken are non-null, false otherwise.
     */
    public static boolean isAuthenticated() {
        return currentUserId != null && currentAuthToken != null;
    }
}


