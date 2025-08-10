package com.btl.View;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.FileChooser; // Import FileChooser for document upload
import javafx.stage.Modality; // Import Modality for popup window
import javafx.stage.Stage;
import com.btl.View.aboutus; // Import the aboutus class for navigation
import com.btl.View.landingPage; // Import the landingPage class for navigation
import com.btl.View.loginPage; // Import the loginPage class for navigation if needed

import java.io.File; // Import File
import java.time.LocalDate; // Import LocalDate for DatePicker
import java.util.HashMap;
import java.util.Map;

import com.btl.Dao.AdvocateDao;
import com.btl.Dao.AppointmentDao;
import com.btl.model.Adovocate;
import com.btl.model.Client; // Ensure this Client class is updated as well

public class AdvocateBiography extends Application {

    // Placeholder for the lawyer's profile image
    private static final String LAWYER_PROFILE_IMAGE_URL = "Assets/Images/male icon.png";
    Client c = new Client();

     private Map<String, Object> lawyerData;

     public AdvocateBiography(){
        
     }

    public AdvocateBiography(Map<String, Object> data) {
        this.lawyerData = data;
    }

    String lawyername="";
    String Bio = "";


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Lawyer Profile - Bridge To Law");
    
        System.out.println();

        lawyername = lawyerData.get("Fullname").toString();
        Bio = lawyerData.get("Bio").toString();



        // --- Navigation Bar ---
        HBox navBar = new HBox(0); // Original spacing
        navBar.setAlignment(Pos.CENTER_LEFT);
        navBar.setPadding(new Insets(15, 30, 15, 30));
        navBar.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-width: 0 0 1px 0;");

        // Left section: "Bridge To Law." label (reintroduced)
        VBox logoBox = new VBox(-10);
        Label bridgeLabel = new Label("Bridge");
        bridgeLabel.setStyle("-fx-text-fill: black; -fx-font-size: 32px; -fx-font-weight: bold;");
        Label toLawLabel = new Label("To Law.");
        toLawLabel.setStyle("-fx-text-fill: black; -fx-font-size: 32px; -fx-font-weight: bold;");
        logoBox.getChildren().addAll(bridgeLabel, toLawLabel);
        logoBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(logoBox, new Insets(0, 60, 0, 0)); // Original margin

        // Center section: Main navigation links
        HBox mainNavLinks = new HBox(25);
        mainNavLinks.setAlignment(Pos.CENTER_LEFT);

        String mainNavLinkStyle = "-fx-background-color: transparent; " +
                                  "-fx-text-fill: black; " +
                                  "-fx-font-size: 14px; " +
                                  "-fx-font-weight: normal; " +
                                  "-fx-padding: 5px 0px;";

        String mainNavLinkHoverStyle = "-fx-background-color: transparent; " +
                                       "-fx-text-fill: #001f4d; " +
                                       "-fx-font-size: 14px; " +
                                       "-fx-font-weight: normal;";

        Button homeButton = new Button("Home");
        Button findLawyerButton = new Button("Find a Lawyer");
        Button aboutUsButton = new Button("About Us");
        Button contactUsButton = new Button("Contact Us"); // New Contact Us button

        homeButton.setStyle(mainNavLinkStyle);
        findLawyerButton.setStyle(mainNavLinkHoverStyle ); // Highlight Find a Lawyer as current context
        aboutUsButton.setStyle(mainNavLinkStyle);
        contactUsButton.setStyle(mainNavLinkStyle); // Apply style to new button

        // Hover effects for navigation buttons
        homeButton.setOnMouseEntered(e -> homeButton.setStyle(mainNavLinkHoverStyle));
        homeButton.setOnMouseExited(e -> homeButton.setStyle(mainNavLinkStyle));
        findLawyerButton.setOnMouseEntered(e -> findLawyerButton.setStyle(mainNavLinkHoverStyle));
        findLawyerButton.setOnMouseExited(e -> findLawyerButton.setStyle(mainNavLinkHoverStyle + "-fx-underline: true;")); // Keep underline on exit
        aboutUsButton.setOnMouseEntered(e -> aboutUsButton.setStyle(mainNavLinkHoverStyle));
        aboutUsButton.setOnMouseExited(e -> aboutUsButton.setStyle(mainNavLinkStyle));
        contactUsButton.setOnMouseEntered(e -> contactUsButton.setStyle(mainNavLinkHoverStyle)); // Hover for Contact Us
        contactUsButton.setOnMouseExited(e -> contactUsButton.setStyle(mainNavLinkStyle)); // Exit hover for Contact Us

        // Add the new Contact Us button to mainNavLinks
        mainNavLinks.getChildren().addAll(homeButton, findLawyerButton, aboutUsButton, contactUsButton);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Add logoBox back to navBar
        navBar.getChildren().addAll(logoBox, mainNavLinks, spacer);

        // --- Navigation Actions ---
        homeButton.setOnAction(e -> {
            System.out.println("Navigating to Home (landingPage)...");
            primaryStage.close(); // Close current stage
            try {
                new landingPage().start(primaryStage); // Launch landingPage
            } catch (Exception ex) {
                ex.printStackTrace();
                // Handle error, e.g., show an alert
                showErrorPopup(primaryStage, "Navigation Error", "Could not load the Home page. Please ensure 'landingPage.java' exists and is correctly compiled.");
            }
        });

        aboutUsButton.setOnAction(e -> {
            System.out.println("Navigating to About Us Page...");
            primaryStage.close(); // Close current stage
            try {
                new aboutus().start(primaryStage); // Launch About Us page
            } catch (Exception ex) {
                ex.printStackTrace();
                // Handle error, e.g., show an alert
                showErrorPopup(primaryStage, "Navigation Error", "Could not load the About Us page. Please ensure 'aboutus.java' exists and is correctly compiled.");
            }
        });

        contactUsButton.setOnAction(e -> {
            System.out.println("Navigating to Contact Us (About Us Page)...");
            primaryStage.close(); // Close current stage
            try {
                // Note: Directly navigating to a "bottom part" of another Stage
                // is not a standard JavaFX feature without modifying the target class
                // (aboutus.java) to include a scroll-to-element mechanism.
                // For now, this will simply open the About Us page.
                new aboutus().start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
                // Handle error, e.g., show an alert
                showErrorPopup(primaryStage, "Navigation Error", "Could not load the Contact Us page. Please ensure 'aboutus.java' exists and is correctly compiled.");
            }
        });

        // --- Lawyer Profile Section ---
        VBox profileBox = new VBox(15); // Increased spacing
        profileBox.setPadding(new Insets(30)); // Increased padding
        profileBox.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 0);");
        profileBox.setPrefWidth(650); // Adjusted width

        // Lawyer Image
        ImageView imageView = new ImageView();
        try {
            Image profileImage = new Image(LAWYER_PROFILE_IMAGE_URL);
            imageView.setImage(profileImage);
            imageView.setFitWidth(120); // Larger image
            imageView.setFitHeight(120);
            imageView.setPreserveRatio(false); // Allow non-proportional scaling for square
            imageView.setClip(new javafx.scene.shape.Rectangle(120, 120)); // Clip to square
        } catch (Exception e) {
            System.err.println("Failed to load lawyer profile image: " + e.getMessage());
            // Fallback for image loading error
            imageView.setImage(new Image("https://placehold.co/120x120/CCCCCC/000000?text=No+Image"));
            imageView.setFitWidth(120);
            imageView.setFitHeight(120);
        }
        imageView.setStyle("-fx-border-color: #001f4d; -fx-border-width: 3; -fx-border-radius: 5;"); // Border around image

       Text name = new Text(lawyername); // 👈 Set text from variable
name.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28)); 
name.setFill(Color.web("#001f4d")); 


        HBox ratingBox = new HBox(5); // Spacing for stars and text
        ratingBox.setAlignment(Pos.CENTER_LEFT);
        Label starIcon = new Label("★"); // Unicode star
        starIcon.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        starIcon.setTextFill(Color.web("#FFD700")); // Gold color for star
        Text ratingText = new Text("4.9 (200+ Reviews)"); // More specific text
        ratingText.setFill(Color.web("#555555"));
        ratingText.setFont(Font.font("Segoe UI", 14));
        ratingBox.getChildren().addAll(starIcon, ratingText);

        Text exp = new Text("10 Years Experience");
        exp.setFont(Font.font("Segoe UI", 14));
        exp.setFill(Color.web("#666666"));

        Text loc = new Text("📍 Mumbai, Maharashtra | Property Law");
        loc.setFont(Font.font("Segoe UI", 14));
        loc.setFill(Color.web("#666666"));

        // Helper function for styled VBoxes
        VBox bioBox = createInfoBox("Biography",  Bio);
        VBox specBox = createInfoBox("Specialisations", "✓ Criminal Defense, ✓ Cyber Crime, ✓ Bail Application, ✓ White-Collar Crime, ✓ Narcotics Cases ,✓ Property Issues");
        VBox courtBox = createInfoBox("Courts of Practice", "✓ Bombay High Court, ✓ Magistrate Court, ✓ ✓ Sessions Court, ✓ Supreme Court of India");
        VBox langBox = createInfoBox("Languages", "✓ English ✓ Hindi ✓ Marathi ✓ Gujarati ✓ Punjabi");
        VBox eduBox = createInfoBox("Education & Bar Admission", "✔ LL.B from Government Law College, Mumbai (2015)\n✔ Enrolled with Bar Council of Maharashtra and Goa (2016)");

        profileBox.getChildren().addAll(
            imageView, name, ratingBox, exp, loc,
            new Separator(), // Styled separator
            bioBox, specBox, courtBox, langBox, eduBox
        );
        profileBox.setAlignment(Pos.TOP_LEFT); // Align content to top-left within profileBox

        // --- Booking Consultation Section ---
        VBox bookingBox = new VBox(15); // Increased spacing
        bookingBox.setPadding(new Insets(30)); // Increased padding
        bookingBox.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 0);");
        bookingBox.setPrefWidth(350); // Adjusted width
        bookingBox.setAlignment(Pos.TOP_CENTER);

        Label bookTitle = new Label("Book Consultation");
        bookTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        bookTitle.setTextFill(Color.web("#001f4d"));

        TextField fullName = new TextField();
        fullName.setPromptText("Enter your full name");
        fullName.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 8px;");

        TextField email = new TextField();
        email.setPromptText("Enter your email");
        email.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 8px;");

        TextField phone = new TextField();
        phone.setPromptText("Phone number");
        phone.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 8px;");

        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Select Date");
        datePicker.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 8px;");

        ComboBox<String> timeDropdown = new ComboBox<>();
        timeDropdown.setPromptText("Select Time Slot");
        timeDropdown.getItems().addAll("10:00 AM - 11:00 AM", "11:00 AM - 12:00 PM", "02:00 PM - 03:00 PM", "03:00 PM - 04:00 PM");
        timeDropdown.setPrefWidth(Double.MAX_VALUE); // Make it fill width
        timeDropdown.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 8px;");

        ComboBox<String> caseType = new ComboBox<>();
        caseType.setPromptText("Select Case Type");
        caseType.getItems().addAll("Civil Law", "Criminal Law", "Property Law", "Cyber Law", "Family Law", "Corporate Law");
        caseType.setPrefWidth(Double.MAX_VALUE); // Make it fill width
        caseType.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 8px;");

        TextArea desc = new TextArea();
        desc.setPromptText("Briefly describe your case (optional)");
        desc.setPrefRowCount(4); // Increased rows
        desc.setWrapText(true);
        desc.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 8px;");

        Button bookBtn = new Button("Book Consultation");
        bookBtn.setStyle(
            "-fx-background-color: #001f4d; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 16px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 10px 20px; " +
            "-fx-background-radius: 8; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 0);"
        );

        bookBtn.setOnMouseEntered(e -> bookBtn.setStyle(
            "-fx-background-color: #003366; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 16px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 10px 20px; " +
            "-fx-background-radius: 8; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 8, 0, 0, 0);"
        ));
        bookBtn.setOnMouseExited(e -> bookBtn.setStyle(
            "-fx-background-color: #001f4d; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 16px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 10px 20px; " +
            "-fx-background-radius: 8; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 0);"
        ));
        bookBtn.setPrefWidth(Double.MAX_VALUE); // Make button fill width

        // Action for Book Consultation button
        bookBtn.setOnAction(e -> {
            // Set data to Client model
            c.setFullname(fullName.getText());
            c.setEmail(email.getText());
            // Convert LocalDate to String for storage if your model expects String
            c.setDate(datePicker.getValue() != null ? datePicker.getValue().toString() : ""); 
            c.setDescription(desc.getText());
            // Add other fields like phone, time, caseType if you extend the Client model
            // c.setPhoneNumber(phone.getText()); 
            // c.setTimeSlot(timeDropdown.getValue());
            // c.setCaseType(caseType.getValue());

            // Handle the booking logic
            Handlebooking(c.getFullname(), c.getEmail(), c.getDate(), c.getDescription());

            // Show success popup after successful data handling
            showSuccessPopup(primaryStage, "Consultation Booked!", "Your consultation request has been successfully submitted. Lawyer will contact you.");
        });

        bookingBox.getChildren().addAll(bookTitle, fullName, email, phone, datePicker,
                timeDropdown, caseType, desc, bookBtn);

        // --- Document Verification Section ---
        VBox documentVerificationBox = new VBox(15); // Spacing
        documentVerificationBox.setPadding(new Insets(30));
        documentVerificationBox.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 0);");
        documentVerificationBox.setPrefWidth(350); // Match booking box width
        documentVerificationBox.setAlignment(Pos.TOP_CENTER);
        VBox.setMargin(documentVerificationBox, new Insets(20, 0, 0, 0)); // Top margin for separation

        Label docTitle = new Label("Document Verification");
        docTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        docTitle.setTextFill(Color.web("#001f4d"));

        Label docInstruction = new Label("Upload relevant documents for verification.");
        docInstruction.setFont(Font.font("Segoe UI", 14));
        docInstruction.setTextFill(Color.web("#666666"));
        docInstruction.setWrapText(true);
        docInstruction.setTextAlignment(TextAlignment.CENTER);

        // Consultation Fee moved here
        Label cost = new Label("Doc Verification Fee: ₹1,500");
        cost.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        cost.setTextFill(Color.web("#001f4d"));
        VBox.setMargin(cost, new Insets(10, 0, 0, 0)); // Margin above fee

        Button uploadDocButton = new Button("Upload Document");
        uploadDocButton.setStyle(
            "-fx-background-color: #4CAF50; " + // Green color for upload
            "-fx-text-fill: white; " +
            "-fx-font-size: 16px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 10px 20px; " +
            "-fx-background-radius: 8; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 0);"
        );
        uploadDocButton.setOnMouseEntered(e -> uploadDocButton.setStyle(
            "-fx-background-color: #45a049; " + // Darker green on hover
            "-fx-text-fill: white; " +
            "-fx-font-size: 16px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 10px 20px; " +
            "-fx-background-radius: 8; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 8, 0, 0, 0);"
        ));
        uploadDocButton.setOnMouseExited(e -> uploadDocButton.setStyle(
            "-fx-background-color: #4CAF50; " + // Original green
            "-fx-text-fill: white; " +
            "-fx-font-size: 16px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 10px 20px; " +
            "-fx-background-radius: 8; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 0);"
        ));
        uploadDocButton.setPrefWidth(Double.MAX_VALUE); // Make button fill width

        // Label to show selected file name
        Label selectedFileLabel = new Label("No file selected.");
        selectedFileLabel.setFont(Font.font("Segoe UI", 12));
        selectedFileLabel.setTextFill(Color.web("#888888"));
        selectedFileLabel.setWrapText(true);
        selectedFileLabel.setMaxWidth(documentVerificationBox.getPrefWidth() - 60);

        uploadDocButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Upload Document");
            // Set extension filters, if desired
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"),
                    new FileChooser.ExtensionFilter("All Files", "*.*")
            );
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                selectedFileLabel.setText("Selected: " + selectedFile.getName());
                System.out.println("File selected: " + selectedFile.getAbsolutePath());
                // Show success popup after successful file selection
                showSuccessPopup(primaryStage, "Document Uploaded!", "Your document has been successfully uploaded for verification.");
            } else {
                selectedFileLabel.setText("No file selected.");
            }
        });

        documentVerificationBox.getChildren().addAll(docTitle, docInstruction, cost, uploadDocButton, selectedFileLabel);

        // Combine booking and document verification sections into a single VBox for the right side
        VBox rightPanel = new VBox(20); // Spacing between booking and document sections
        rightPanel.getChildren().addAll(bookingBox, documentVerificationBox);

        HBox mainContent = new HBox(30); // Increased spacing between left and right panels
        mainContent.setPadding(new Insets(40));
        mainContent.setAlignment(Pos.TOP_CENTER);
        mainContent.getChildren().addAll(profileBox, rightPanel);

        // Wrap mainContent in a ScrollPane to make the page scrollable
        ScrollPane mainScrollPane = new ScrollPane(mainContent);
        mainScrollPane.setFitToWidth(true); // Allow content to expand to width
        mainScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Disable horizontal scrollbar
        mainScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Show vertical scrollbar only when needed
        mainScrollPane.setStyle("-fx-background-color: transparent; -fx-padding: 0;"); // Transparent background, no extra padding

        // Main root container
        VBox root = new VBox();
        root.getChildren().addAll(navBar, mainScrollPane); // navBar at top, mainScrollPane below
        root.setStyle("-fx-background-color: #001f4d;"); // Dark blue background for the entire window

        Scene scene = new Scene(root, 1550, 890); // Adjusted scene size for better layout
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void Handlebooking(String Fullname, String email, String date, String description){
        Map<String,Object> data = new HashMap<>();
        data.put("Fullname",Fullname);
        data.put("Email",email);
        data.put("Date",date); // Changed key to 'Date'
        data.put("Description",description); // Changed key to 'Description'

        try {
            AppointmentDao.adddata("Appointmentinfo", Fullname , data);
            System.out.println("Appointment data saved successfully for: " + Fullname);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle database error
            showErrorPopup(null, "Database Error", "Failed to save appointment data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Helper function for styled VBoxes
    private VBox createInfoBox(String title, String content) {
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        titleLabel.setTextFill(Color.web("#001f4d"));

        Label contentLabel = new Label(content);
        contentLabel.setFont(Font.font("Segoe UI", 14));
        contentLabel.setTextFill(Color.web("#333333"));
        contentLabel.setWrapText(true);
        contentLabel.setMaxWidth(590); // 650 - 60, since profileBox.getPrefWidth() is 650

        VBox box = new VBox(5, titleLabel, contentLabel);
        box.setPadding(new Insets(10, 0, 0, 0)); // Top padding for separation
        return box;
    }

    /**
     * Displays a custom success popup.
     * @param ownerStage The primary stage, used for modality.
     * @param title The title of the popup.
     * @param message The message to display in the popup.
     */
    private void showSuccessPopup(Stage ownerStage, String title, String message) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL); // Block interaction with other windows
        popupStage.initOwner(ownerStage);
        popupStage.setTitle(title);

        VBox popupContent = new VBox(20);
        popupContent.setAlignment(Pos.CENTER);
        popupContent.setPadding(new Insets(30));
        popupContent.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 0);");

        // Green checkmark icon
        Label checkmarkLabel = new Label("✔"); // Unicode checkmark
        checkmarkLabel.setFont(Font.font("Arial", FontWeight.BOLD, 60));
        checkmarkLabel.setTextFill(Color.web("#4CAF50")); // Green color

        Label messageLabel = new Label(message);
        messageLabel.setFont(Font.font("Segoe UI", 16));
        messageLabel.setTextFill(Color.web("#333333"));
        messageLabel.setWrapText(true);
        messageLabel.setTextAlignment(TextAlignment.CENTER);

        Button okButton = new Button("OK");
        okButton.setStyle(
            "-fx-background-color: #001f4d; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 14px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 8px 15px; " +
            "-fx-background-radius: 5;"
        );
        okButton.setOnMouseEntered(e -> okButton.setStyle(
            "-fx-background-color: #003366; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 14px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 8px 15px; " +
            "-fx-background-radius: 5;"
        ));
        okButton.setOnMouseExited(e -> okButton.setStyle(
            "-fx-background-color: #001f4d; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 14px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 8px 15px; " +
            "-fx-background-radius: 5;"
        ));
        okButton.setOnAction(e -> popupStage.close());

        popupContent.getChildren().addAll(checkmarkLabel, messageLabel, okButton);

        Scene popupScene = new Scene(popupContent);
        popupStage.setScene(popupScene);
        popupStage.showAndWait(); // Show and wait for it to be closed
    }

    /**
     * Displays a custom error popup.
     * @param ownerStage The primary stage, used for modality.
     * @param title The title of the popup.
     * @param message The message to display in the popup.
     */
    private void showErrorPopup(Stage ownerStage, String title, String message) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initOwner(ownerStage);
        popupStage.setTitle(title);

        VBox popupContent = new VBox(20);
        popupContent.setAlignment(Pos.CENTER);
        popupContent.setPadding(new Insets(30));
        popupContent.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 0);");

        // Red 'X' icon for error
        Label errorLabel = new Label("✖"); // Unicode multiplication sign for error
        errorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 60));
        errorLabel.setTextFill(Color.web("#D32F2F")); // Red color

        Label messageLabel = new Label(message);
        messageLabel.setFont(Font.font("Segoe UI", 16));
        messageLabel.setTextFill(Color.web("#333333"));
        messageLabel.setWrapText(true);
        messageLabel.setTextAlignment(TextAlignment.CENTER);

        Button okButton = new Button("OK");
        okButton.setStyle(
            "-fx-background-color: #001f4d; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 14px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 8px 15px; " +
            "-fx-background-radius: 5;"
        );
        okButton.setOnAction(e -> popupStage.close());

        popupContent.getChildren().addAll(errorLabel, messageLabel, okButton);

        Scene popupScene = new Scene(popupContent);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }
}