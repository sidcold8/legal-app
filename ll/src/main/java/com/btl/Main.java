package com.btl;


import com.btl.Controller.Firebaseinitialize;
import com.btl.View.AdvocateBiography;
import com.btl.View.GetStarted;
/*import com.btl.View.AdvocateBiography;
import com.btl.View.LD;
import com.btl.View.aboutus;
import com.btl.View.lawyerClientSignup;*/
//import com.btl.Controller.SceneManager;
//import com.btl.View.landingPage;
//import com.btl.View.loginPage;
//import com.btl.View.AdvocateGrid;
//import com.btl.View.notificationPage;
import com.btl.View.loginPage;
//import com.btl.Controller.LD;

import javafx.application.Application;

//import com.btl.View.lawyer1;
//import com.btl.View.lawyer2;
//import com.btl.View.lawyer3;
//import com.btl.View.lawyer4;
//import com.btl.View.lawyer5;
//import com.btl.View.lawyer6;


public class Main {
    public static void main(String[] args) {
        Firebaseinitialize.initialize();
        System.out.println("Hello world!");
        //Application.launch(lawyerClientSignup.class,args);
      Application.launch(GetStarted.class,args);
       //Application.launch(AdvocateGrid.class,args);
       //Application.launch(AdvocateBiography.class,args);

       //Application.launch(lawyer1.class,args);
       //Application.launch(lawyer2.class,args);
       //Application.launch(lawyer3.class,args);
       //Application.launch(lawyer4.class,args);
       //Application.launch(lawyer5.class,args);
       //Application.launch(lawyer6.class,args);
       //Application.launch(landingPage.class,args);
       //Application.launch(aboutus.class,args);
       //Application.launch(LD.class,args);

    }

    //FirebaseManager.setAuthenticatedUser();


}


/*package com.btl; // Your project's base package

import com.btl.Controller.Firebaseinitialize; // Your Firebase SDK initialization
import com.btl.View.LD; // Your Lawyer Profile Page (now a VBox)
import com.btl.View.aboutus; // Your About Us page
import com.btl.View.loginPage; // Your Login Page (assuming it's a VBox now)

// Assuming these View components also exist and are VBox/Pane based
import com.btl.View.notificationPage;
import com.btl.View.AdvocateBiography;
import com.btl.View.AdvocateBiography;
import com.btl.View.lawyerlandingpage; // Assuming this is also a VBox/Pane based component

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.paint.Color; // For custom messages
import javafx.scene.text.Font; // For custom messages
import javafx.scene.text.FontWeight; // For custom messages
import javafx.scene.text.TextAlignment; // For custom messages
import javafx.stage.Modality; // For custom messages

// The Main class now extends Application to be the primary entry point
public class Main extends Application {

    private Stage primaryStage;
    private StackPane contentArea; // This will hold the different "pages" (VBox components)

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Bridge To Law Application");

        // Initialize Firebase SDK (if Firebaseinitialize.initialize() does this)
        // This should happen once at app startup
        Firebaseinitialize.initialize();

        // --- IMPORTANT: Simulate Login for Testing ---
        // In your actual project, this should be called AFTER a successful login
        // from your existing login page, using the real UID and ID Token.
        // For testing, make sure 'lawyerId123' (or whatever you use) exists in your Firestore 'users' collection
        // and has corresponding 'appointments'/'documents' entries.
        // Replace "lawyerId123" and "dummyAuthTokenForTesting" with actual values after a successful login.
        // If FirebaseManager is in com.btl package, change com.example.FirebaseManager to com.btl.FirebaseManager
        com.btl.Controller.FirebaseManager.setAuthenticatedUser("lawyerId123", "dummyAuthTokenForTesting");


        // --- Main Layout ---
        BorderPane root = new BorderPane();

        // --- Navigation Bar (Example) ---
        HBox navBar = new HBox(10);
        navBar.setPadding(new Insets(10));
        navBar.setAlignment(Pos.CENTER);
        navBar.setStyle("-fx-background-color: #001f4d;"); // Dark blue background for consistency

        Button btnLawyerProfile = new Button("Lawyer Profile");
        btnLawyerProfile.setOnAction(e -> showLawyerProfilePage());
        btnLawyerProfile.setStyle("-fx-background-color: #003366; -fx-text-fill: white;");

        Button btnLawyerNotifications = new Button("Lawyer Notifications");
        btnLawyerNotifications.setOnAction(e -> showLawyerNotificationsPage());
        btnLawyerNotifications.setStyle("-fx-background-color: #003366; -fx-text-fill: white;");

        Button btnClientAppointment = new Button("Client Appointment Request");
        btnClientAppointment.setOnAction(e -> showClientAppointmentRequestPage());
        btnClientAppointment.setStyle("-fx-background-color: #003366; -fx-text-fill: white;");

        Button btnClientDocumentUpload = new Button("Client Document Upload");
        btnClientDocumentUpload.setOnAction(e -> showClientDocumentUploadPage());
        btnClientDocumentUpload.setStyle("-fx-background-color: #003366; -fx-text-fill: white;");

        Button btnAboutUs = new Button("About Us");
        btnAboutUs.setOnAction(e -> showAboutUsPage());
        btnAboutUs.setStyle("-fx-background-color: #003366; -fx-text-fill: white;");

        Button btnLawyerLanding = new Button("Lawyer Landing");
        btnLawyerLanding.setOnAction(e -> showLawyerLandingPage());
        btnLawyerLanding.setStyle("-fx-background-color: #003366; -fx-text-fill: white;");

        Button btnLogout = new Button("Logout");
        btnLogout.setOnAction(e -> handleLogout());
        btnLogout.setStyle("-fx-background-color: #a00; -fx-text-fill: white;");


        navBar.getChildren().addAll(
            btnLawyerProfile, btnLawyerNotifications, btnClientAppointment,
            btnClientDocumentUpload, btnAboutUs, btnLawyerLanding, btnLogout
        );
        root.setTop(navBar);

        // --- Content Area ---
        contentArea = new StackPane(); // Use StackPane to easily swap views
        root.setCenter(contentArea);

        // --- Initial View ---
        // Start with the login page. Your login page should handle setting FirebaseManager.setAuthenticatedUser()
        // upon successful login and then navigate to the appropriate dashboard (e.g., Lawyer Profile or Notifications).
        showLoginPage();

        Scene scene = new Scene(root, 900, 700); // Adjust size as needed
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // --- Methods to switch between different pages/components ---

    private void showLoginPage() {
        // Assuming loginPage is a VBox/Pane and has a constructor taking Stage
        loginPage page = new loginPage(Stage);
        contentArea.getChildren().clear();
        contentArea.getChildren().add(page);
        primaryStage.setTitle("Login - Bridge To Law");
    }

    private void showLawyerProfilePage() {
        // If FirebaseManager is in com.btl package, change com.example.FirebaseManager to com.btl.FirebaseManager
        if (!com.btl.Controller.FirebaseManager.isAuthenticated()) {
            showCustomMessage("Access Denied", "Please log in to view/edit your profile.", Color.RED);
            return;
        }
        // Create an instance of your LD class (Lawyer Profile Page)
        LD lawyerProfilePage = new LD(primaryStage); // Pass the primary stage
        contentArea.getChildren().clear();
        contentArea.getChildren().add(lawyerProfilePage);
        primaryStage.setTitle("Lawyer Profile - Bridge To Law");
    }

    private void showLawyerNotificationsPage() {
        // If FirebaseManager is in com.btl package, change com.example.FirebaseManager to com.btl.FirebaseManager
        if (!com.btl.Controller.FirebaseManager.isAuthenticated()) {
            showCustomMessage("Access Denied", "Please log in as a lawyer to view notifications.", Color.RED);
            return;
        }
        notificationPage page = new notificationPage(primaryStage);
        contentArea.getChildren().clear();
        contentArea.getChildren().add(page);
        primaryStage.setTitle("Lawyer Notifications - Bridge To Law");
    }

    private void showClientAppointmentRequestPage() {
        // If FirebaseManager is in com.btl package, change com.example.FirebaseManager to com.btl.FirebaseManager
        if (!com.btl.Controller.FirebaseManager.isAuthenticated()) {
            showCustomMessage("Access Denied", "Please log in as a client to request appointments.", Color.RED);
            return;
        }
        AdvocateBiography page = new AdvocateBiography(primaryStage);
        contentArea.getChildren().clear();
        contentArea.getChildren().add(page);
        primaryStage.setTitle("Request Appointment - Bridge To Law");
    }

    private void showClientDocumentUploadPage() {
        // If FirebaseManager is in com.btl package, change com.example.FirebaseManager to com.btl.FirebaseManager
        if (!com.btl.Controller.FirebaseManager.isAuthenticated()) {
            showCustomMessage("Access Denied", "Please log in as a client to upload documents.", Color.RED);
            return;
        }
        AdvocateBiography page = new AdvocateBiography(primaryStage);
        contentArea.getChildren().clear();
        contentArea.getChildren().add(page);
        primaryStage.setTitle("Upload Document - Bridge To Law");
    }

    private void showAboutUsPage() {
        // Assuming aboutus is a VBox/Pane and has a constructor taking Stage
        aboutus page = new aboutus(primaryStage);
        contentArea.getChildren().clear();
        contentArea.getChildren().add(page);
        primaryStage.setTitle("About Us - Bridge To Law");
    }

    private void showLawyerLandingPage() {
        // Assuming lawyerlandingpage is a VBox/Pane and has a constructor taking Stage
        lawyerlandingpage page = new lawyerlandingpage(primaryStage);
        contentArea.getChildren().clear();
        contentArea.getChildren().add(page);
        primaryStage.setTitle("Lawyer Landing Page - Bridge To Law");
    }

    private void handleLogout() {
        // If FirebaseManager is in com.btl package, change com.example.FirebaseManager to com.btl.FirebaseManager
        com.btl.Controller.FirebaseManager.clearAuthenticatedUser();
        showCustomMessage("Logout", "You have been logged out.", Color.BLACK);
        showLoginPage(); // Go back to login page after logout
    }

    // Custom Alert/Message Box (copied for consistency across pages)
    private void showCustomMessage(String title, String message, Color textColor) {
        Stage dialogStage = new Stage();
        dialogStage.initOwner(primaryStage);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setTitle(title);
        dialogStage.setResizable(false);

        Label messageLabel = new Label(message);
        messageLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        messageLabel.setTextFill(textColor);
        messageLabel.setWrapText(true);
        messageLabel.setTextAlignment(TextAlignment.CENTER);
        messageLabel.setMaxWidth(300);

        Button okButton = new Button("OK");
        okButton.setStyle(
            "-fx-background-color: #001f4d; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 8px 15px; -fx-background-radius: 5;"
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

    public static void main(String[] args) {
        launch(args); // This is the standard way to launch a JavaFX Application
    }
}
*/


