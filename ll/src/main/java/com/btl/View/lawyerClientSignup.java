package com.btl.View; // Changed package to com.example for consistency

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.btl.Dao.AdvocateDao;
import com.btl.model.Adovocate;
// No need for com.google.cloud.location.Location unless explicitly used

public class lawyerClientSignup extends Application {

    private Stage primaryStage;
    // Client fields
    private TextField clientEmailField;
    private PasswordField clientPasswordField, clientConfirmPasswordField;
    private TextField clientFullNameField; // New field for client's full name
    private TextField clientPhoneNumberField; // New field for client's phone number
    private TextField clientLocationField; // New field for client's location
    private ComboBox<String> clientLegalIssueComboBox; // New field for client's legal issue
    private TextArea clientLegalIssueDetails; // New field for client's legal issue details
    private CheckBox clientAgreeTerms; // New field for client's terms agreement


    // Lawyer fields
    private TextField lawyerNameField;
    private TextField lawyerEmailField;
    private PasswordField lawyerPasswordField, lawyerConfirmPasswordField;
    private TextField lawyerExperienceField; // Corrected field name
    private TextField lawyerPhoneField; // Corrected field name
    private TextField lawyerLocationField; // New field for lawyer's location

    private ComboBox<String> specializationComboBox;
    private TextArea bioTextArea;
    
    Adovocate a = new Adovocate(); // Instantiated Adovocate model

    // Replace with your actual Firebase API Key
    // WARNING: Hardcoding API key in client-side code like this is NOT secure for production.
    // For a real application, use Firebase Admin SDK on a backend or Firebase client SDKs.
    private static final String FIREBASE_API_KEY = "AIzaSyBnDyBuWkOo9b03FLqWhoHXxZAeTWp1Yqs"; // Keep as is for this exercise

    // --- Custom Alert/Message Box (Replaces Alert) ---
    private void showCustomMessage(String title, String message, Color textColor) {
        Stage dialogStage = new Stage();
        dialogStage.initOwner(primaryStage);
        dialogStage.setTitle(title);
        dialogStage.setResizable(false); // Make dialog not resizable

        Label messageLabel = new Label(message);
        messageLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        messageLabel.setTextFill(textColor);
        messageLabel.setWrapText(true);
        messageLabel.setTextAlignment(TextAlignment.CENTER);
        messageLabel.setMaxWidth(300); // Constrain width for wrapping

        Button okButton = new Button("OK");
        okButton.setStyle(
            "-fx-background-color: #001f4d; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 14px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 8px 15px; " +
            "-fx-background-radius: 5;"
        );
        okButton.setOnAction(e -> dialogStage.close());

        VBox dialogLayout = new VBox(20, messageLabel, okButton);
        dialogLayout.setAlignment(Pos.CENTER);
        dialogLayout.setPadding(new Insets(20));
        dialogLayout.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 0);");

        Scene dialogScene = new Scene(dialogLayout, 350, 200);
        dialogStage.setScene(dialogScene);
        dialogStage.showAndWait();
    }


    // Helper for styled TextFields/PasswordFields
    private TextField createStyledTextField(String promptText) {
        TextField field = new TextField(); // Create a new instance each time
        field.setPromptText(promptText);
        field.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 10px;");
        field.setPrefWidth(300); // Consistent width
        field.setMaxWidth(350);
        return field;
    }

    private PasswordField createStyledPasswordField(String promptText) {
        PasswordField field = new PasswordField(); // Create a new instance each time
        field.setPromptText(promptText);
        field.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 10px;");
        field.setPrefWidth(300); // Consistent width
        field.setMaxWidth(350);
        return field;
    }
    
    // Helper for styled Labels for form fields
    private Label createFormLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        label.setTextFill(Color.web("#333333"));
        return label;
    }

    // Helper for styled ComboBoxes
    private ComboBox<String> createStyledComboBox(String promptText, String... items) {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setPromptText(promptText);
        comboBox.getItems().addAll(items);
        comboBox.setPrefWidth(300); // Consistent width
        comboBox.setMaxWidth(350);
        comboBox.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 8px;");
        return comboBox;
    }

    // Helper for styled TextArea
    private TextArea createStyledTextArea(String promptText, int prefRowCount) {
        TextArea textArea = new TextArea();
        textArea.setPromptText(promptText);
        textArea.setPrefRowCount(prefRowCount);
        textArea.setWrapText(true);
        textArea.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 10px;");
        textArea.setPrefWidth(300); // Consistent width
        textArea.setMaxWidth(350);
        return textArea;
    }

    private VBox createClientForm() {
        VBox form = new VBox(15); // Increased spacing
        form.setPadding(new Insets(30)); // Increased padding
        form.setAlignment(Pos.CENTER); // Center content within form
        form.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 0);");

        Label heading = new Label("Client Registration");
        heading.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        heading.setTextFill(Color.web("#001f4d"));
        VBox.setMargin(heading, new Insets(0, 0, 10, 0)); // Margin below heading
        
        // Initialize client fields here
        clientFullNameField = createStyledTextField("Enter your full name");
        clientEmailField = createStyledTextField("Enter your email");
        clientPasswordField = createStyledPasswordField("Create password");
        clientConfirmPasswordField = createStyledPasswordField("Confirm password");
        clientPhoneNumberField = createStyledTextField("Enter your phone number");
        clientLocationField = createStyledTextField("Enter your location");

        clientLegalIssueComboBox = createStyledComboBox(
            "Select Legal Issue",
            "Civil Law", "Criminal Law", "Family Law", "Property Law",
            "Cyber Law", "Corporate Law", "Consumer Law", "Environmental Law",
            "Intellectual Property Law", "Tax Law", "Labour Law", "Constitutional Law",
            "Arbitration & Mediation", "Immigration Law", "Human Rights Law", "Banking & Finance Law"
        );

        clientLegalIssueDetails = createStyledTextArea("Briefly describe your legal issue (optional)", 3);

        clientAgreeTerms = new CheckBox("I agree to the Terms & Conditions");
        clientAgreeTerms.setFont(Font.font("Segoe UI", 12));
        clientAgreeTerms.setTextFill(Color.web("#555555"));
        VBox.setMargin(clientAgreeTerms, new Insets(10, 0, 0, 0)); // Margin above checkbox

        Button clientSubmitButton = createSubmitButton("Create Client Account", true);
        clientSubmitButton.setOnAction(e -> {
            handleClientSignup();
        });


        form.getChildren().addAll(
            heading,
            createFormLabel("Full Name"), clientFullNameField,
            createFormLabel("Email"), clientEmailField,
            createFormLabel("Password"), clientPasswordField,
            createFormLabel("Confirm Password"), clientConfirmPasswordField,
            createFormLabel("Phone Number"), clientPhoneNumberField,
            createFormLabel("Location"), clientLocationField,
            createFormLabel("Legal Issue Area"), clientLegalIssueComboBox,
            createFormLabel("Legal Issue Details"), clientLegalIssueDetails,
            clientAgreeTerms,
            clientSubmitButton
        );

        return form;
    }

    private VBox createLawyerForm() {
        VBox form = new VBox(15); // Increased spacing
        form.setPadding(new Insets(30)); // Increased padding
        form.setAlignment(Pos.CENTER); // Center content within form
        form.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 0);");

        Label heading = new Label("Lawyer Registration");
        heading.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        heading.setTextFill(Color.web("#001f4d"));
        VBox.setMargin(heading, new Insets(0, 0, 10, 0)); // Margin below heading
        
        // Initialize lawyer fields here
        lawyerNameField = createStyledTextField("Enter your name");
        lawyerEmailField = createStyledTextField("Enter your email");
        lawyerPasswordField = createStyledPasswordField("Create password");
        lawyerConfirmPasswordField = createStyledPasswordField("Confirm password");
        lawyerPhoneField = createStyledTextField("Enter Your Phone Number");
        lawyerLocationField = createStyledTextField("Enter your location");
        lawyerExperienceField = createStyledTextField("Enter Your Experience"); // Corrected variable name

        specializationComboBox = createStyledComboBox(
            "Select Specialization",
            "Criminal Law", "Civil Law", "Family Law", "Property Law",
            "Cyber Law", "Corporate Law", "Consumer Law", "Environmental Law",
            "Intellectual Property Law", "Tax Law", "Labour Law", "Constitutional Law",
            "Arbitration & Mediation", "Immigration Law", "Human Rights Law", "Banking & Finance Law"
        );

        bioTextArea = createStyledTextArea("Briefly describe your professional background and expertise", 4);

        CheckBox agreeTerms = new CheckBox("I agree to the Terms & Conditions");
        agreeTerms.setFont(Font.font("Segoe UI", 12));
        agreeTerms.setTextFill(Color.web("#555555"));
        VBox.setMargin(agreeTerms, new Insets(10, 0, 0, 0)); // Margin above checkbox

        Button lawyerSubmitButton = createSubmitButton("Create Lawyer Account", false);
        lawyerSubmitButton.setOnAction(e -> {
            handleLawyerSignup();
        });

        form.getChildren().addAll(
            heading,
            createFormLabel("Full Name"), lawyerNameField,
            createFormLabel("Email"), lawyerEmailField,
            createFormLabel("Password"), lawyerPasswordField,
            createFormLabel("Confirm Password"), lawyerConfirmPasswordField,
            createFormLabel("Phone Number"), lawyerPhoneField,
            createFormLabel("Location"), lawyerLocationField,
            createFormLabel("Years of Experience"), lawyerExperienceField, // Corrected variable name
            createFormLabel("Specialization"), specializationComboBox,
            createFormLabel("Bio"), bioTextArea,
            agreeTerms,
            lawyerSubmitButton
        );

        return form;
    }

    private Button createSubmitButton(String text, boolean isClient) {
        Button button = new Button(text);
        button.setPrefWidth(300); // Consistent width
        button.setMaxWidth(350);
        button.setStyle(
            "-fx-background-color: #001f4d; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 16px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 12px 25px; " +
            "-fx-background-radius: 8; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 0);"
        );
        button.setOnMouseEntered(e -> button.setStyle(
            "-fx-background-color: #003366; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 16px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 12px 25px; " +
            "-fx-background-radius: 8; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 8, 0, 0, 0);"
        ));
        button.setOnMouseExited(e -> button.setStyle(
            "-fx-background-color: #001f4d; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 16px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 12px 25px; " +
            "-fx-background-radius: 8; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 0);"
        ));
        VBox.setMargin(button, new Insets(20, 0, 0, 0)); // Margin above button
        
        return button;
    }

    private void handleClientSignup() {
        String email = clientEmailField.getText();
        String password = clientPasswordField.getText();
        String confirmPassword = clientConfirmPasswordField.getText();
        String fullName = clientFullNameField.getText();
        String phoneNumber = clientPhoneNumberField.getText();
        String location = clientLocationField.getText();
        String legalIssue = clientLegalIssueComboBox.getValue();
        String legalIssueDetailsText = clientLegalIssueDetails.getText();
        boolean agreedToTerms = clientAgreeTerms.isSelected();


        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || fullName.isEmpty() || phoneNumber.isEmpty() || location.isEmpty() || legalIssue == null || !agreedToTerms) {
            showCustomMessage("Error", "All client fields are required and you must agree to terms.", Color.RED);
            return;
        }

        if (!password.equals(confirmPassword)) {
            showCustomMessage("Error", "Passwords do not match.", Color.RED);
            return;
        }

        // Additional validation (e.g., email format, password strength) can be added here
        
        // Register with Firebase
        registerWithFirebase(email, password);

        // Save client data to your database (AdvocateDao in this case)
        Map<String, Object> clientData = new HashMap<>();
        clientData.put("fullName", fullName);
        clientData.put("email", email);
        clientData.put("phoneNumber", phoneNumber);
        clientData.put("location", location);
        clientData.put("legalIssue", legalIssue);
        clientData.put("legalIssueDetails", legalIssueDetailsText);
        // Do not store password directly in your database in plain text.
        // If you are using Firebase Authentication, the password is handled there securely.

        AdvocateDao.adddata("Clientinfo", email, clientData); // Assuming AdvocateDao can handle client data
        showCustomMessage("Client Signup", "Client data saved locally (assuming AdvocateDao handles it).", Color.BLUE);
    }

    private void handleLawyerSignup() {
        String fullName = lawyerNameField.getText();
        String email = lawyerEmailField.getText();
        String password = lawyerPasswordField.getText();
        String confirmPassword = lawyerConfirmPasswordField.getText();
        String specialization = specializationComboBox.getValue();
        String bio = bioTextArea.getText();
        String phoneNo = lawyerPhoneField.getText();
        String experience = lawyerExperienceField.getText(); // Corrected variable name
        boolean agreedToTerms = true; // Placeholder, assuming a checkbox named 'agreeTerms' exists in the form

        // Basic validation
        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() ||
            specialization == null || bio.isEmpty() || phoneNo.isEmpty() || experience.isEmpty()) {
            showCustomMessage("Error", "All lawyer fields are required.", Color.RED);
            return;
        }

        if (!password.equals(confirmPassword)) {
            showCustomMessage("Error", "Passwords do not match.", Color.RED);
            return;
        }

        // Additional validation (e.g., email format, password strength) can be added here

        // Register with Firebase
        registerWithFirebase(email, password);

        // Set data to Adovocate model
        a.setFullname(fullName);
        a.setEmail(email);
        a.setPassword(password); // Only for Firebase, not for direct database storage usually
        a.setSpecialization(specialization);
        a.setBio(bio);
        a.setPhoneNo(phoneNo);
        a.setExp(experience);

        // Save lawyer data to your database via AdvocateDao
        Handlelawyersignup(a.getFullname(), a.getEmail(), a.getSpecialization(), a.getBio(), a.getPhoneNo(), a.getExp());
    }

    private void registerWithFirebase(String email, String password) {
        new Thread(() -> { // Run network operation on a separate thread
            try {
                URL url = new URL("https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=" + FIREBASE_API_KEY);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                String jsonInput = String.format(
                    "{\"email\":\"%s\",\"password\":\"%s\",\"returnSecureToken\":true}",
                    email, password
                );

                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                int responseCode = conn.getResponseCode();
                BufferedReader in;
                StringBuilder response = new StringBuilder();
                if (responseCode >= 200 && responseCode < 300) {
                    in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                } else {
                    in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                }
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                String responseString = response.toString();
                System.out.println("Firebase Response: " + responseString); // Log full response

                if (responseCode == 200) {
                    javafx.application.Platform.runLater(() -> { // Update UI on FX Application Thread
                        showCustomMessage("Success", "✅ Registration successful!", Color.GREEN);
                        backToLogin(); // Navigate to Loginpage after successful registration
                    });
                } else {
                    // Parse error message from Firebase response if available
                    String errorMessage = "Registration failed. Please try again.";
                    if (responseString.contains("EMAIL_EXISTS")) {
                        errorMessage = "Registration failed. Email already exists.";
                    } else if (responseString.contains("WEAK_PASSWORD")) {
                        errorMessage = "Registration failed. Password is too weak (min 6 characters).";
                    }

                    String finalErrorMessage = errorMessage;
                    javafx.application.Platform.runLater(() -> { // Update UI on FX Application Thread
                        showCustomMessage("Error", "❌ " + finalErrorMessage, Color.RED);
                    });
                }

                conn.disconnect();
            } catch (Exception ex) {
                ex.printStackTrace();
                javafx.application.Platform.runLater(() -> { // Update UI on FX Application Thread
                    showCustomMessage("Network Error", "⚠️ Could not connect to Firebase: " + ex.getMessage(), Color.RED);
                });
            }
        }).start(); // Start the new thread
    }

    private void backToLogin() {
        // Ensure Loginpage is in the same package (com.example) or imported correctly
        loginPage loginPage = new loginPage(); // Assuming loginPage exists in the same package
        try {
            primaryStage.close(); // Close the current signup stage
            loginPage.start(new Stage()); // Launch a new Loginpage stage
        } catch (Exception ex) {
            ex.printStackTrace();
            showCustomMessage("Navigation Error", "Failed to go back to Login page.", Color.RED);
        }
    }

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage; // Store the primary stage

        // --- Navigation Bar (Re-integrated and simplified) ---
        HBox navBar = new HBox(0);
        navBar.setAlignment(Pos.CENTER_LEFT);
        navBar.setPadding(new Insets(15, 30, 15, 30));
        navBar.setStyle("-fx-background-color: #001f4d; -fx-border-color: #001f4d; -fx-border-width: 0 0 1px 0;"); // Dark blue background

        // Left section: "Bridge To Law." label
        VBox logoBox = new VBox(-10);
        Label bridgeLabel = new Label("Bridge");
        bridgeLabel.setStyle("-fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: bold;"); // White text
        Label toLawLabel = new Label("To Law.");
        toLawLabel.setStyle("-fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: bold;"); // White text
        logoBox.getChildren().addAll(bridgeLabel, toLawLabel);
        logoBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(logoBox, new Insets(0, 60, 0, 0));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        /*Button homeButton = new Button("Home");
        homeButton.setStyle(
            "-fx-background-color: transparent; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 14px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 5px 10px;"
        );
        homeButton.setOnMouseEntered(e -> homeButton.setStyle(
            "-fx-background-color: rgba(255,255,255,0.2); " + // Light overlay on hover
            "-fx-text-fill: white; " +
            "-fx-font-size: 14px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 5px 10px;"
        ));
        homeButton.setOnMouseExited(e -> homeButton.setStyle(
            "-fx-background-color: transparent; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 14px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 5px 10px;"
        ));
        
        // Navigation Action for Home button
        homeButton.setOnAction(e -> {
            System.out.println("Navigating to Home (LandingPage)...");
            primaryStage.close();
            try {
                new landingPage().start(new Stage()); // Launch LandingPage
            } catch (Exception ex) {
                ex.printStackTrace();
                showCustomMessage("Navigation Error", "Failed to go to Home page.", Color.RED);
            }
        }); */

        navBar.getChildren().addAll(logoBox, spacer);


        // --- Toggle Buttons for Client/Lawyer ---
        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton lawyerToggle = new RadioButton("Create Lawyer Account");
        RadioButton clientToggle = new RadioButton("Create Client Account");
        
        // Apply consistent styling to RadioButtons
        String radioStyle = "-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #001f4d;";
        String radioSelectedStyle = "-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #003366;"; // Darker blue when selected

        lawyerToggle.setStyle(radioStyle);
        clientToggle.setStyle(radioStyle);

        lawyerToggle.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                lawyerToggle.setStyle(radioSelectedStyle);
            } else {
                lawyerToggle.setStyle(radioStyle);
            }
        });
        clientToggle.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                clientToggle.setStyle(radioSelectedStyle);
            } else {
                clientToggle.setStyle(radioStyle);
            }
        });


        lawyerToggle.setToggleGroup(toggleGroup);
        clientToggle.setToggleGroup(toggleGroup);
        lawyerToggle.setSelected(true); // Default to lawyer form

        HBox toggleBox = new HBox(30, lawyerToggle, clientToggle); // Increased spacing
        toggleBox.setAlignment(Pos.CENTER);
        toggleBox.setPadding(new Insets(20, 0, 20, 0)); // Vertical padding only

        // --- Form Panes ---
        StackPane formPane = new StackPane();
        VBox lawyerForm = createLawyerForm();
        VBox clientForm = createClientForm();
        formPane.getChildren().addAll(lawyerForm, clientForm);
        clientForm.setVisible(false); // Client form hidden by default

        toggleGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == lawyerToggle) {
                lawyerForm.setVisible(true);
                clientForm.setVisible(false);
            } else {
                lawyerForm.setVisible(false);
                clientForm.setVisible(true);
            }
        });

        // --- Main Layout ---
        VBox mainContent = new VBox(toggleBox, formPane); // No spacing between toggle and formPane, formPane has its own padding
        mainContent.setAlignment(Pos.TOP_CENTER);
        mainContent.setPadding(new Insets(30, 50, 30, 50)); // Adjusted top padding below toggle box

        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-padding: 0;"); // Transparent background

        VBox root = new VBox();
        root.getChildren().addAll(navBar, scrollPane); // Nav bar added back
        root.setStyle("-fx-background-color: #F4F6F9;"); // Light grey background for the overall window

        Scene scene = new Scene(root, 1550, 890); // Adjusted scene size for better fit
        stage.setTitle("Register on Bridge To Law");
        stage.setScene(scene);
        stage.show();
    }


    public void Handlelawyersignup(String fullname, String email, String specialization, String bio, String PhoneNo, String Exp){
        Map<String,Object> data = new HashMap<>();
        data.put("Fullname",fullname);
        data.put("email", email);
        data.put("Specialization", specialization); // Changed key to match combobox prompt
        data.put("Bio", bio); // Changed key to match textarea prompt
        data.put("PhoneNo", PhoneNo); // Changed key to match textfield prompt
        data.put("Experience", Exp); // Changed key to match textfield prompt

        try {
            AdvocateDao.adddata("Lawyerinfo", email, data);
            javafx.application.Platform.runLater(() -> {
                showCustomMessage("Lawyer Data Save", "Lawyer information saved successfully!", Color.GREEN);
            });
        } catch (Exception e) {
            e.printStackTrace();
            javafx.application.Platform.runLater(() -> {
                showCustomMessage("Database Error", "Failed to save lawyer information: " + e.getMessage(), Color.RED);
            });
        }
    }
        
    public static void main(String[] args) {
        launch(args);
    }
}