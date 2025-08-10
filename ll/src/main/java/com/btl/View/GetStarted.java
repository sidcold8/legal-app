package com.btl.View;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GetStarted extends Application {

    // IMPORTANT: Replace this with the actual URL of your image
    private static final String IMAGE_URL = "Assets/Images/Screenshot 2025-07-25 172639.png"; // <-- REPLACE THIS URL
    private static final double IMAGE_PREF_WIDTH = 1500; // Example width
    private static final double IMAGE_PREF_HEIGHT = 600; // Example height (adjust as


    @Override
    public void start(Stage primaryStage) {
        // Root VBox for vertical arrangement
        VBox root = new VBox(20); // Spacing of 20px between children
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(50)); // Padding around the edges
        // The background color from the image is a dark blue/indigo
        root.setStyle("-fx-background-color: #0b1d47ff;");

        // 1. Logo (Scales of Justice with Laurel Wreath)
        Image logoImage = null;
        try {
            // Attempt to load the image from the provided URL
            logoImage = new Image(IMAGE_URL);
            
        } catch (Exception e) {
            System.err.println("Error loading image from URL: " + IMAGE_URL + " Assets/Images/Screenshot 2025-07-25 172639.png " + e.getMessage());
            // Fallback: If the URL fails, you might want to load a local placeholder
            // or just leave it blank. For now, it will be null if loading fails.
        }

        ImageView logoView = new ImageView(logoImage);
        logoView.setFitWidth(IMAGE_PREF_WIDTH);       
        logoView.setFitHeight(IMAGE_PREF_HEIGHT); // Adjust size as needed
        logoView.setPreserveRatio(true);

        // 2. "Bridge To Law" Title Label
        //Label titleLabel = new Label("Bridge To Law");
        // Using a common serif font that closely resembles the image
        //titleLabel.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: white;");

        // 3. "Your Legal Path, Simplified" Subtitle Label
        //Label subtitleLabel = new Label("Your Legal Path, Simplified");
        // Using a common sans-serif font
        //subtitleLabel.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 20px; -fx-text-fill: white;");

        // 4. "Team Legal Linkers" Label
        //Label teamLabel = new Label("Team Legal Linkers");
        // Gold color for the text
        //teamLabel.setStyle("-fx-font-family: 'Verdana'; -fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #FFD700;"); // Gold color

        // 5. "Get Started" Button
        Button getStartedButton = new Button("𝗚𝗘𝗧 𝗦𝗧𝗔𝗥𝗧𝗘𝗗 ▶");
        getStartedButton.setPrefWidth(220); // Match width of combo boxes
        getStartedButton.setStyle(" -fx-background-color: #3e5b87;-fx-text-fill: white; -fx-font-size: 18px; -fx-padding: 12 25; -fx-background-radius: 8;");
        getStartedButton.setOnMouseEntered(e -> getStartedButton.setStyle("-fx-background-color: #001f4d; -fx-text-fill: white; -fx-font-size: 18px; -fx-padding: 12 25; -fx-background-radius: 8;"));
        getStartedButton.setOnMouseExited(e -> getStartedButton.setStyle("-fx-background-color: #3e5b87ff; -fx-text-fill: white; -fx-font-size: 18px; -fx-padding: 12 25; -fx-background-radius: 8;"));
        getStartedButton.setAlignment(Pos.CENTER);
        

        // Add an action handler for the button
        getStartedButton.setOnAction(e -> {
            System.out.println("Get Started button clicked!");
            // You can add navigation logic here, e.g.,
            // Stage currentStage = (Stage) getStartedButton.getScene().getWindow();
            // currentStage.close();
            // Open new scene/window
                        try {
                new loginPage().start(primaryStage);
            } catch (Exception ex) {
                showAlert("Navigation Error", "Failed to load About Us Page.", "The 'aboutus' class could not be loaded or started. Check console for details.", Alert.AlertType.ERROR);
                ex.printStackTrace();
            }
        });

        // Add all components to the root VBox in the correct order
        root.getChildren().addAll(logoView, getStartedButton);

        // Create the scene
        Scene scene = new Scene(root, 1550, 890); // Set initial window size

        primaryStage.setTitle("Bridge To Law");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(String string, String string2, String string3, AlertType error) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showAlert'");
    }

    public static void main(String[] args) {
        launch(args);
    }
}