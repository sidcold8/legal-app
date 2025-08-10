package com.btl.View;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import org.json.JSONObject;

import com.btl.View.FirebaseInit; // Assuming FirebaseInit exists and correctly initializes Firebase

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class loginPage extends Application {

    private static final String SCALES_ICON_URL = "Assets/Images/logo.png";
    // IMPORTANT: Replace with your actual Firebase Web API Key for production.
    // This key is exposed in client-side code and should be restricted in Firebase console.
    private static final String FIREBASE_WEB_API_KEY = "AIzaSyBnDyBuWkOo9b03FLqWhoHXxZAeTWp1Yqs"; 

    @Override
    public void start(Stage stage) {
        FirebaseInit.initialize(); // Initialize Firebase

        stage.setTitle("Bridge To Law - Login");

        // --- Left Section: Logo and Tagline ---
        VBox left = new VBox(25);
        left.setAlignment(Pos.CENTER);
        left.setPrefWidth(450);
        left.setStyle("-fx-background-color: #001f4d;"); // Dark blue background

        ImageView logo = new ImageView();
        try {
            Image logoImage = new Image(SCALES_ICON_URL);
            logo.setImage(logoImage);
            logo.setFitWidth(150);
            logo.setFitHeight(150);
            logo.setPreserveRatio(true);
        } catch (Exception e) {
            // Fallback image if the local asset is not found
            logo.setImage(new Image("https://placehold.co/150x150/CCCCCC/000000?text=Logo+Error"));
            System.err.println("Error loading logo image: " + e.getMessage());
        }

        Label appName = new Label("Bridge To Law");
        appName.setFont(Font.font("Verdana", FontWeight.BOLD, 36));
        appName.setTextFill(Color.WHITE);

        Label tag = new Label("Connecting clients with legal professionals\nfor better justice outcomes");
        tag.setFont(Font.font("Segoe UI", 16));
        tag.setTextFill(Color.web("#E0E0E0")); // Light grey text
        tag.setTextAlignment(TextAlignment.CENTER);
        tag.setWrapText(true);
        tag.setMaxWidth(350);

        left.getChildren().addAll(logo, appName, tag);

        // --- Right Section: Login Form ---
        VBox right = new VBox(20);
        right.setAlignment(Pos.CENTER);
        right.setPadding(new Insets(50));
        right.setPrefWidth(450);
        right.setStyle("-fx-background-color: #FFFFFF;"); // White background

        Label loginHeading = new Label("Welcome Back!");
        loginHeading.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        loginHeading.setTextFill(Color.web("#001f4d")); // Dark blue text

        // Toggle buttons for Client/Lawyer login
        ToggleButton clientToggle = new ToggleButton("Client Login");
        ToggleButton lawyerToggle = new ToggleButton("Lawyer Login");
        ToggleGroup loginTypeGroup = new ToggleGroup();
        clientToggle.setToggleGroup(loginTypeGroup);
        lawyerToggle.setToggleGroup(loginTypeGroup);

        // Styling for toggle buttons
        String toggleButtonStyle = "-fx-background-color: #f0f0f0; -fx-text-fill: #001f4d; -fx-font-weight: bold; -fx-padding: 8px 15px; -fx-background-radius: 5;";
        String toggleButtonSelectedStyle = "-fx-background-color: #001f4d; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8px 15px; -fx-background-radius: 5;";

        clientToggle.setStyle(toggleButtonStyle);
        lawyerToggle.setStyle(toggleButtonStyle);

        // Set default selection to Client
        clientToggle.setSelected(true);
        clientToggle.setStyle(toggleButtonSelectedStyle);

        HBox toggleButtons = new HBox(10, clientToggle, lawyerToggle);
        toggleButtons.setAlignment(Pos.CENTER);
        toggleButtons.setPrefWidth(300); // Match width of other inputs
        toggleButtons.setMaxWidth(300);

        Label emailLabel = new Label("Email Address");
        emailLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        TextField email = new TextField();
        email.setPromptText("Enter your email");
        email.setPrefWidth(300);
        email.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-radius: 5;");

        Label passLabel = new Label("Password");
        passLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        PasswordField pass = new PasswordField();
        pass.setPromptText("Enter your password");
        pass.setPrefWidth(300);
        pass.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-radius: 5;");

        // Bar ID field (initially hidden)
        Label barIdLabel = new Label("Bar ID");
        barIdLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        TextField barIdField = new TextField();
        barIdField.setPromptText("Enter your Bar ID");
        barIdField.setPrefWidth(300);
        barIdField.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-radius: 5;");

        // Hide Bar ID field by default (for Client login)
        barIdLabel.setVisible(false);
        barIdField.setVisible(false);
        barIdLabel.setManaged(false); // Do not take up space when hidden
        barIdField.setManaged(false); // Do not take up space when hidden

        // Listener for toggle buttons to show/hide Bar ID field
        loginTypeGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if (newToggle == lawyerToggle) {
                barIdLabel.setVisible(true);
                barIdField.setVisible(true);
                barIdLabel.setManaged(true);
                barIdField.setManaged(true);
                lawyerToggle.setStyle(toggleButtonSelectedStyle);
                clientToggle.setStyle(toggleButtonStyle);
            } else { // Client toggle or no toggle selected
                barIdLabel.setVisible(false);
                barIdField.setVisible(false);
                barIdLabel.setManaged(false);
                barIdField.setManaged(false);
                clientToggle.setStyle(toggleButtonSelectedStyle);
                lawyerToggle.setStyle(toggleButtonStyle);
            }
        });

        Button login = new Button("Login");
        login.setPrefWidth(300);
        login.setStyle("-fx-background-color: #001f4d; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 12px 25px; -fx-background-radius: 8;");

        // Hover effects for login button
        login.setOnMouseEntered(e -> login.setStyle("-fx-background-color: #003366; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 12px 25px; -fx-background-radius: 8;"));
        login.setOnMouseExited(e -> login.setStyle("-fx-background-color: #001f4d; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 12px 25px; -fx-background-radius: 8;"));

        // --- Firebase Login Logic ---
        login.setOnAction(e -> {
            String emailInput = email.getText().trim();
            String passwordInput = pass.getText().trim();
            String barIdInput = barIdField.getText().trim();

            if (emailInput.isEmpty() || passwordInput.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Email and Password cannot be empty.");
                return;
            }

            // If Lawyer login is selected, ensure Bar ID is not empty
            if (lawyerToggle.isSelected() && barIdInput.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Bar ID cannot be empty for Lawyer login.");
                return;
            }

            // Run Firebase login on a separate thread to keep UI responsive
            new Thread(() -> {
                try {
                    String idToken = loginWithFirebase(emailInput, passwordInput);

                    if (idToken != null) {
                        javafx.application.Platform.runLater(() -> {
                            try {
                                if (lawyerToggle.isSelected()) {
                                    // Navigate to lawyerlandingpage for Lawyer Login
                                    lawyerlandingpage lawyerLandingPage = new lawyerlandingpage();
                                    lawyerLandingPage.start(stage);
                                } else {
                                    // Navigate to landingPage for Client Login (default)
                                    landingPage clientLandingPage = new landingPage();
                                    clientLandingPage.start(stage);
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to load the next page.");
                            }
                        });
                    } else {
                        javafx.application.Platform.runLater(() ->
                            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid email or password.")
                        );
                    }
                } catch (Exception ex) {
                    javafx.application.Platform.runLater(() ->
                        showAlert(Alert.AlertType.ERROR, "Login Error", "An error occurred during login: " + ex.getMessage())
                    );
                    ex.printStackTrace();
                }
            }).start();
        });

        // SignUp link
        Label signUpPrompt = new Label("Don't have an account?");
        signUpPrompt.setFont(Font.font("Segoe UI", 12));
        signUpPrompt.setTextFill(Color.web("#666666")); // Dark grey text
        Button signUpButton = new Button("Sign Up");
        signUpButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #001f4d; -fx-font-weight: bold;"); // Transparent button with dark blue text
        signUpButton.setOnAction(e -> {
            try {
                lawyerClientSignup signup = new lawyerClientSignup();
                signup.start(stage); // Navigate to signup page
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to load Sign Up page.");
            }
        });

        HBox signUpBox = new HBox(5, signUpPrompt, signUpButton);
        signUpBox.setAlignment(Pos.CENTER);

        right.getChildren().addAll(loginHeading, toggleButtons, emailLabel, email, passLabel, pass, barIdLabel, barIdField, login, signUpBox);

        // --- Root Layout ---
        // Using StackPane to center the HBox content within the scene
        StackPane rootContainer = new StackPane();
        HBox root = new HBox(left, right);
        root.setAlignment(Pos.CENTER); // Center the HBox within the StackPane
        rootContainer.getChildren().add(root);

        Scene scene = new Scene(rootContainer, 1550, 890); // Initial scene size
        stage.setScene(scene);
        stage.show();

        // Make the stage resizable and ensure content remains centered
        stage.setMinWidth(900);
        stage.setMinHeight(600);
    }

    /**
     * Helper method to display alerts to the user.
     * @param type The type of alert (ERROR, INFORMATION, WARNING, etc.)
     * @param title The title of the alert window.
     * @param message The content message of the alert.
     */
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null); // No header text
        alert.setContentText(message);
        alert.showAndWait(); // Show and wait for user to dismiss
    }

    /**
     * Authenticates a user with Firebase using email and password.
     * @param email The user's email address.
     * @param password The user's password.
     * @return The Firebase ID Token if login is successful, otherwise null.
     * @throws Exception If an error occurs during the API call.
     */
    private String loginWithFirebase(String email, String password) throws Exception {
        String urlStr = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + FIREBASE_WEB_API_KEY;
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true); // Allow sending a request body

        // Create JSON payload for the Firebase API
        JSONObject payload = new JSONObject();
        payload.put("email", email);
        payload.put("password", password);
        payload.put("returnSecureToken", true); // Request an ID token

        // Write the JSON payload to the connection's output stream
        try (OutputStream os = conn.getOutputStream()) {
            os.write(payload.toString().getBytes());
            os.flush();
        }

        int responseCode = conn.getResponseCode(); // Get HTTP response code
        if (responseCode == HttpURLConnection.HTTP_OK) { // 200 OK
            // Read the successful response
            try (Scanner scanner = new Scanner(conn.getInputStream())) {
                StringBuilder responseStr = new StringBuilder();
                while (scanner.hasNext()) {
                    responseStr.append(scanner.nextLine());
                }
                JSONObject json = new JSONObject(responseStr.toString());
                return json.getString("idToken"); // Return the ID token
            }
        } else {
            // Read error response if status is not 200
            try (Scanner scanner = new Scanner(conn.getErrorStream())) {
                StringBuilder errorResponseStr = new StringBuilder();
                while (scanner.hasNext()) {
                    errorResponseStr.append(scanner.nextLine());
                }
                JSONObject errorJson = new JSONObject(errorResponseStr.toString());
                String errorMessage = errorJson.getJSONObject("error").getString("message");
                System.err.println("Firebase Login Error: " + errorMessage);
                // You might want to throw a more specific exception or return null based on error message
            }
            return null; // Login failed
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
