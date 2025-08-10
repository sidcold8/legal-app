package com.btl.View;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image; // Required for Image in top panel
import javafx.scene.image.ImageView; // Required for ImageView in top panel
import javafx.scene.layout.*;
import javafx.scene.paint.Color; // Required for Color in custom messages
import javafx.scene.text.*; // Required for Text and Font in custom messages and top panel
import javafx.stage.Stage;

// Assuming these classes exist in the com.btl.View package or are accessible.
// If not, you'll need to create them or adjust the navigation logic.
import com.btl.View.lawyerlandingpage; // Import for lawyerlandingpage

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.btl.Dao.getnotificationDao;
import com.btl.View.aboutus;
// import com.btl.View.ContactUsPage; // Uncomment if you have a separate ContactUsPage class


public class notificationPage extends Application {


    String fullName;
    String email;
    String datePicker ;
    String desc ;
    List<Map<String, Object>> lawyerdata;

    VBox pageContent = new VBox(20);

    // This VBox will specifically hold the appointment request cards,
    // allowing the title to be placed before it.
    VBox appointmentCardsContainer = new VBox(15);


    notificationPage(){

        try {
            lawyerdata = getnotificationDao.getLawyer("Appointmentinfo");

            for (int i = 0; i < lawyerdata.size(); i++) {
                Map<String, Object> item = lawyerdata.get(i);

                String name = item.get("Fullname") != null ? item.get("Fullname").toString() : "N/A";
                String email = item.get("Email") != null ? item.get("Email").toString() : "N/A";
                //String phone = item.get("phone") != null ? item.get("phone").toString() : "N/A";
                String message = item.get("Description") != null ? item.get("Description").toString() : "No message provided";
                String date = item.get("Date") != null ? item.get("Date").toString() : "Date not available";

                // Add card dynamically to the new container
                appointmentCardsContainer.getChildren().add(makeRequestCard(name, email, message, "Requested on " + date, appointmentCardsContainer));
            }

        } catch (InterruptedException | ExecutionException e1) {
            e1.printStackTrace();
        }

    }




    private Stage primaryStage; // Store primary stage for dialogs and navigation

    // Placeholder for the logo image (from previous LD project)
    // Using a generic placeholder URL for portability. Replace with your actual logo if needed.
    private static final String LOGO_IMAGE_URL = "Assets/Images/logo.png"; 

    @Override
    public void start(Stage primaryStage) {


        this.primaryStage = primaryStage;
        primaryStage.setTitle("Notifications - Bridge To Law");

        // --- Top Navigation/Header Panel (Copied from LD class) ---
        HBox topPanel = new HBox(10); // Spacing between elements
        topPanel.setAlignment(Pos.CENTER_LEFT);
        topPanel.setPadding(new Insets(15, 30, 15, 30));
        topPanel.setStyle("-fx-background-color: #001f4d;"); // Dark blue background

        // Logo (Bridge To Law)
        VBox logoBox = new VBox(-10); // Negative spacing for "Bridge To Law." stacked effect
        Label bridgeLabel = new Label("Bridge");
        bridgeLabel.setStyle("-fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: bold;");
        Label toLawLabel = new Label("To Law.");
        toLawLabel.setStyle("-fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: bold;");
        logoBox.getChildren().addAll(bridgeLabel, toLawLabel);
        logoBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(logoBox, new Insets(0, 60, 0, 0)); // Margin to the right of the logo

        // Navigation Buttons Styling
        String navButtonStyle = "-fx-background-color: transparent; " +
                                "-fx-text-fill: white; " +
                                "-fx-font-size: 14px; " +
                                "-fx-font-weight: bold; " +
                                "-fx-padding: 5px 10px;";
        String navButtonHoverStyle = "-fx-background-color: rgba(255,255,255,0.2); " + // Light overlay on hover
                                        "-fx-text-fill: white; " +
                                        "-fx-font-size: 14px; " +
                                        "-fx-font-weight: bold; " +
                                        "-fx-padding: 5px 10px;";

        Button homeButton = new Button("Home");
        homeButton.setStyle(navButtonStyle);
        homeButton.setOnMouseEntered(e -> homeButton.setStyle(navButtonHoverStyle));
        homeButton.setOnMouseExited(e -> homeButton.setStyle(navButtonStyle));
        homeButton.setOnAction(e -> {
            System.out.println("Navigating to Home (LawyerLandingPage)...");
            try {
                primaryStage.close(); // Close current stage
                new lawyerlandingpage().start(primaryStage); // Launch LawyerLandingPage
            } catch (Exception ex) {
                ex.printStackTrace();
                showCustomMessage("Navigation Error", "Could not navigate to Home Page. Ensure lawyerlandingpage class exists and is correctly compiled.", Color.RED);
            }
        });
        // Make logo clickable for Home as well
        logoBox.setOnMouseClicked(e -> {
            System.out.println("Navigating to Home (LawyerLandingPage) via logo...");
            primaryStage.close();
            try {
                new lawyerlandingpage().start(primaryStage);
            }  catch (Exception ex) {
                ex.printStackTrace();
                showCustomMessage("Navigation Error", "Could not load the Home page. Please ensure 'lawyerlandingpage.java' exists and is correctly compiled.", Color.RED);
            }
        });


        Button aboutUsButton = new Button("About Us");
        aboutUsButton.setStyle(navButtonStyle);
        aboutUsButton.setOnMouseEntered(e -> aboutUsButton.setStyle(navButtonHoverStyle));
        aboutUsButton.setOnMouseExited(e -> aboutUsButton.setStyle(navButtonStyle));
        aboutUsButton.setOnAction(e -> {
            System.out.println("Navigating to About Us Page...");
            try {
                primaryStage.close(); // Close current stage
                new aboutus().start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
                showCustomMessage("Error", "Could not navigate to About Us Page. Ensure aboutus class exists.", Color.RED);
            }
        });

        Button contactUsButton = new Button("Contact Us");
        contactUsButton.setStyle(navButtonStyle);
        contactUsButton.setOnMouseEntered(e -> contactUsButton.setStyle(navButtonHoverStyle));
        contactUsButton.setOnMouseExited(e -> contactUsButton.setStyle(navButtonStyle));
        contactUsButton.setOnAction(e -> {
            System.out.println("Navigating to Contact Us Page...");
            try {
                primaryStage.close(); // Close current stage
                // If you have a separate ContactUsPage, uncomment the line below:
                // new ContactUsPage().start(new Stage());
                // Otherwise, it will navigate to About Us as per previous logic
                new aboutus().start(primaryStage); // Assuming Contact Us is part of About Us or a similar page
                showCustomMessage("Navigation", "Navigating to Contact Us Page.", Color.BLACK);
            } catch (Exception ex) {
                ex.printStackTrace();
                showCustomMessage("Error", "Could not navigate to Contact Us Page. Ensure ContactUsPage or aboutus class exists.", Color.RED);
            }
        });

        Region spacerRight = new Region(); // This spacer will push Notifications to the far right
        HBox.setHgrow(spacerRight, Priority.ALWAYS); 

        // Notification Button (already on this page, so just a message)
        Button notificationButton = new Button("🔔 Notifications"); // Bell emoji or icon
        notificationButton.setStyle(
            "-fx-background-color: #003366; " + // Slightly lighter blue
            "-fx-text-fill: white; " +
            "-fx-font-size: 14px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 8px 15px; " +
            "-fx-background-radius: 8;"
        );
        notificationButton.setOnMouseEntered(e -> notificationButton.setStyle(
            "-fx-background-color: #004d99; " + // Darker on hover
            "-fx-text-fill: white; " +
            "-fx-font-size: 14px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 8px 15px; " +
            "-fx-background-radius: 8;"
        ));
        notificationButton.setOnMouseExited(e -> notificationButton.setStyle(
            "-fx-background-color: #003366; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 14px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 8px 15px; " +
            "-fx-background-radius: 8;"
        ));
        notificationButton.setOnAction(e -> showCustomMessage("Notifications", "You are currently on the Notifications page.", Color.BLACK));

        // Add all elements to the top panel
        topPanel.getChildren().addAll(logoBox, homeButton, aboutUsButton, contactUsButton, spacerRight, notificationButton);


    
        pageContent.setPadding(new Insets(20));
        pageContent.setStyle("-fx-background-color: #f5f6fa;");

        // Appointment Requests Section Title
        Label title = new Label("Appointment Requests");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        HBox titleBox = new HBox(title);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(10));
        titleBox.setStyle("-fx-background-color: white; -fx-border-color: #ccc; -fx-border-width: 1 0 1 0;");
        
        // Add the title box FIRST
        pageContent.getChildren().add(titleBox);
        // Then add the container holding the dynamically loaded appointment cards
        pageContent.getChildren().add(appointmentCardsContainer);



        // Document Verification Section
        pageContent.getChildren().add(makeDocumentGrid(pageContent)); // Pass pageContent to makeDocumentGrid

        ScrollPane scrollPane = new ScrollPane(pageContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #f5f6fa;");

        // Main root layout combining top panel and scrollable content
        VBox root = new VBox();
        root.getChildren().addAll(topPanel, scrollPane);
        root.setAlignment(Pos.TOP_CENTER); // Center the content horizontally

        Scene scene = new Scene(root, 1550, 890);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Creates a VBox card for an appointment request.
     * Includes functionality for Accept and Reject buttons, and removes the card upon action.
     * @param name The name of the person requesting the appointment.
     * @param email The email of the person.
     * @param caseDetails Details of the case.
     * @param date The date of the request.
     * @param parentContainer The VBox that contains this card, used for removal.
     * @return A VBox representing the appointment request card.
     */
    private VBox makeRequestCard(String name, String email, String caseDetails, String date, VBox parentContainer) {
        Label nameLabel = new Label(name);
        nameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

     

        Label dateLabel = new Label(date);
        dateLabel.setStyle("-fx-text-fill: #777;");

        Label caseLabel = new Label("Case: " + caseDetails);
        caseLabel.setWrapText(true);
        caseLabel.setStyle("-fx-text-fill: #444;");

        Button acceptBtn = new Button("✓ Accept");
        acceptBtn.setStyle("-fx-background-color: #1a1e3f; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 4;");

        Button rejectBtn = new Button("✕ Reject");
        rejectBtn.setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: black; -fx-font-weight: bold; -fx-border-color: #ccc; -fx-background-radius: 4;");

        HBox buttonBox = new HBox(10, acceptBtn, rejectBtn);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        // Declare 'card' before its usage in lambda to make it effectively final
        final VBox card = new VBox(8, nameLabel,dateLabel, caseLabel, buttonBox);
        card.setPadding(new Insets(15));
        card.setSpacing(5);
        card.setStyle("-fx-background-color: white; -fx-border-color: #ccc; -fx-border-radius: 6; -fx-background-radius: 6;");

        // Add action for Accept button and remove the card
        acceptBtn.setOnAction(e -> {
            showCustomMessage("Appointment Status", "Appointment with " + name + " accepted!", Color.GREEN);
            parentContainer.getChildren().remove(card); // Remove the card from its parent
        });

        // Add action for Reject button and remove the card
        rejectBtn.setOnAction(e -> {
            showCustomMessage("Appointment Status", "Appointment with " + name + " rejected.", Color.RED);
            parentContainer.getChildren().remove(card); // Remove the card from its parent
        });

        return card;
    }

    /**
     * Creates a VBox containing the document verification requests.
     * Includes functionality for View, Accept, and Reject buttons, and removes the row upon action.
     * @param mainPageContent The VBox that contains the document grid, used for removal.
     * @return A VBox representing the document verification section.
     */
    private VBox makeDocumentGrid(VBox mainPageContent) {
        Label sectionTitle = new Label("Document Verification Request");
        sectionTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        HBox titleBox = new HBox(sectionTitle);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(10));
        titleBox.setStyle("-fx-background-color: white; -fx-border-color: #ccc; -fx-border-width: 1 0 1 0;");

        VBox container = new VBox(10); // This VBox will hold the individual document request rows
        container.setPadding(new Insets(15));
        container.setStyle("-fx-background-color: white; -fx-border-color: #ccc; -fx-border-radius: 6; -fx-background-radius: 6;");

        String[][] docRequests = {
                {"Property Papers Verification", "Amit Patel"},
                {"Divorce Papers Verification", "Priya Sharma"},
                {"Rental Agreement Verification", "Rohit Mehta"},
                {"Will Document Verification", "Sneha Kulkarni"}
        };

        for (String[] req : docRequests) {
            String docName = req[0];
            String sender = req[1];

            Label docLabel = new Label(docName);
            docLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

            Label senderLabel = new Label("Send By: " + sender);
            senderLabel.setStyle("-fx-text-fill: #666;");

            VBox leftBox = new VBox(5, docLabel, senderLabel);
            leftBox.setAlignment(Pos.CENTER_LEFT);

            Button viewBtn = new Button("📄 View");
            viewBtn.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 4;");

            Button acceptBtn = new Button("✓ Accept");
            acceptBtn.setStyle("-fx-background-color: #1a1e3f; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 4;");

            Button rejectBtn = new Button("✕ Reject");
            rejectBtn.setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: black; -fx-font-weight: bold; -fx-border-color: #ccc; -fx-background-radius: 4;");

            HBox rightBox = new HBox(10, viewBtn, acceptBtn, rejectBtn);
            rightBox.setAlignment(Pos.CENTER_RIGHT);

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            // Declare 'row' before its usage in lambda to make it effectively final
            final HBox row = new HBox(15, leftBox, spacer, rightBox);
            row.setPadding(new Insets(10));
            row.setStyle("-fx-background-color: #fafafa; -fx-border-color: #ddd; -fx-border-width: 1; -fx-background-radius: 6;");
            container.getChildren().add(row);

            // Add action for View button and remove the row
            viewBtn.setOnAction(e -> {
                showCustomMessage("Document View", "Viewing document: " + docName + " from " + sender, Color.BLACK);
                container.getChildren().remove(row); // Remove the HBox row
            });

            // Add action for Accept button and remove the row
            acceptBtn.setOnAction(e -> {
                showCustomMessage("Document Status", "Document '" + docName + "' from " + sender + " accepted!", Color.GREEN);
                container.getChildren().remove(row); // Remove the HBox row
            });

            // Add action for Reject button and remove the row
            rejectBtn.setOnAction(e -> {
                showCustomMessage("Document Status", "Document '" + docName + "' from " + sender + " rejected.", Color.RED);
                container.getChildren().remove(row); // Remove the HBox row
            });
        }

        // Return a VBox that combines the section title and the container of document rows
        return new VBox(10, titleBox, container);
    }

    /**
     * Displays a custom message dialog.
     * @param title The title of the dialog.
     * @param message The message to display.
     * @param textColor The color of the message text.
     */
    private void showCustomMessage(String title, String message, Color textColor) {
        Stage dialogStage = new Stage();
        dialogStage.initOwner(primaryStage); // Set owner to primary stage
        dialogStage.setTitle(title);
        dialogStage.setResizable(false); // Prevent resizing of the dialog

        Label messageLabel = new Label(message);
        messageLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        messageLabel.setTextFill(textColor);
        messageLabel.setWrapText(true); // Allow text to wrap
        messageLabel.setTextAlignment(TextAlignment.CENTER);
        messageLabel.setMaxWidth(300); // Max width for the message label

        Button okButton = new Button("OK");
        okButton.setStyle(
            "-fx-background-color: #001f4d; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 14px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 8px 15px; " +
            "-fx-background-radius: 5;"
        );
        okButton.setOnAction(e -> dialogStage.close()); // Close dialog on OK button click

        VBox dialogLayout = new VBox(20, messageLabel, okButton); // Spacing between message and button
        dialogLayout.setAlignment(Pos.CENTER); // Center content in dialog
        dialogLayout.setPadding(new Insets(20)); // Padding around dialog content
        dialogLayout.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 0);");

        Scene dialogScene = new Scene(dialogLayout, 350, 200); // Fixed size for dialog scene
        dialogStage.setScene(dialogScene);
        dialogStage.showAndWait(); // Show dialog and wait for it to be closed
    }

    public static void main(String[] args) {
        launch(args);
    }
}