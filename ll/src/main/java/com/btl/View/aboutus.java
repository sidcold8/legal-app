/*package com.btl.View;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;
import javafx.stage.Modality; // Import Modality for popup window
import com.btl.View.landingPage; // Ensure landingPage is imported
import com.btl.View.AdvocateBiography; // Assuming this is the Find a Lawyer target

public class aboutus extends Application {

    // --- YOUR ACTUAL IMAGE URLs ---
    private static final String BACKGROUND_IMAGE_URL = "Assets/Images/background.jpg";
    private static final String LAWYER_ICON_URL = "https://placehold.co/60x60/001f4d/ffffff?text=Lawyer"; // Placeholder, if you have a local path for this, replace it
    private static final String EXPERIENCE_ICON_URL = "https://placehold.co/60x60/001f4d/ffffff?text=Exp"; // Placeholder
    private static final String CITIES_ICON_URL = "https://placehold.co/60x60/001f4d/ffffff?text=Cities"; // Placeholder
    private static final String CLIENTS_ICON_URL = "https://placehold.co/60x60/001f4d/ffffff?text=Clients"; // Placeholder
    private static final String ROHIT_IMAGE_URL = "Assets/Images/rohit.jpg";
    private static final String SWANAND_IMAGE_URL = "Assets/Images/swanand.jpg";
    private static final String SIDDHANT_IMAGE_URL = "Assets/Images/siddhant.jpg";
    private static final String YASH_IMAGE_URL = "Assets/Images/yash.jpg";
    // New image URLs for Shashi sir and Gandharv (placeholders)
    private static final String SHASHI_IMAGE_URL = "Assets/Images/Shashi Sir.jpg"; // Placeholder for Shashi sir
    private static final String Core2Web_IMAGE_URL = "Assets/Images/Core2Web.jpg"; // Placeholder for Gandharv

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("About Bridge To Law");

        // --- Navigation Bar ---
        HBox navBar = new HBox(0);
        navBar.setAlignment(Pos.CENTER_LEFT);
        navBar.setPadding(new Insets(15, 30, 15, 30));
        navBar.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-width: 0 0 1px 0;");

        // Left section: "Bridge To Law." label (now clickable)
        VBox logoBox = new VBox(-10);
        Label bridgeLabel = new Label("Bridge");
        bridgeLabel.setStyle("-fx-text-fill: black; -fx-font-size: 32px; -fx-font-weight: bold;");
        Label toLawLabel = new Label("To Law.");
        toLawLabel.setStyle("-fx-text-fill: black; -fx-font-size: 32px; -fx-font-weight: bold;");
        logoBox.getChildren().addAll(bridgeLabel, toLawLabel);
        logoBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(logoBox, new Insets(0, 60, 0, 0));

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
        //Button findLawyerButton = new Button("Find a Lawyer");
        Button aboutUsButton = new Button("About Us");
        Button legalNewsButton = new Button("Legal News"); // New Legal News button
        Button contactUsButton = new Button("Contact Us");

        homeButton.setStyle(mainNavLinkStyle);
        //findLawyerButton.setStyle(mainNavLinkStyle);
        aboutUsButton.setStyle(mainNavLinkHoverStyle );
        legalNewsButton.setStyle(mainNavLinkStyle); // Style the new button
        contactUsButton.setStyle(mainNavLinkStyle);

        homeButton.setOnMouseEntered(e -> homeButton.setStyle(mainNavLinkHoverStyle));
        homeButton.setOnMouseExited(e -> homeButton.setStyle(mainNavLinkStyle));
        //findLawyerButton.setOnMouseEntered(e -> findLawyerButton.setStyle(mainNavLinkHoverStyle));
        //findLawyerButton.setOnMouseExited(e -> findLawyerButton.setStyle(mainNavLinkStyle));
        legalNewsButton.setOnMouseEntered(e -> legalNewsButton.setStyle(mainNavLinkHoverStyle)); // Hover for Legal News
        legalNewsButton.setOnMouseExited(e -> legalNewsButton.setStyle(mainNavLinkStyle)); // Exit hover for Legal News
        contactUsButton.setOnMouseEntered(e -> contactUsButton.setStyle(mainNavLinkHoverStyle));
        contactUsButton.setOnMouseExited(e -> contactUsButton.setStyle(mainNavLinkStyle));

        // Add the new Legal News button to mainNavLinks
        mainNavLinks.getChildren().addAll(homeButton , aboutUsButton, legalNewsButton, contactUsButton);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        navBar.getChildren().addAll(logoBox, mainNavLinks, spacer);

        // --- Main Content Area ---
        VBox content = new VBox(50);
        content.setPadding(new Insets(30, 0, 30, 0));
        content.setAlignment(Pos.TOP_CENTER);
        content.setStyle("-fx-background-color: transparent;");

        // The target for the "Contact Us" button
        VBox contactSection = getFooterSection();
        VBox finalSection = getFinalSection();
        
        content.getChildren().addAll(
                getTitleSection(),
                getImpactSection(),
                getTeamSection(),
                getValuesSection(),
                getInSupportWithSection(), // Add the new "In Support With" section here
                contactSection,
                finalSection
        );

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-hbar-policy: never;");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        // --- Navigation Actions ---
        /*findLawyerButton.setOnAction(e -> {
            System.out.println("Navigating to Find a Lawyer (Advocate Biography) Page...");
            primaryStage.close();
            try {
                new AdvocateBiography().start(primaryStage); // Launch AdvocateBiography
            } catch (Exception ex) {
                ex.printStackTrace();
                showErrorPopup(primaryStage, "Navigation Error", "Could not load the Find a Lawyer page. Please ensure 'AdvocateBiography.java' exists and is correctly compiled.");
            }
        });*/
        
    /*     // Action for Home Button and Logo
        Runnable navigateToLandingPage = () -> {
            System.out.println("Navigating to Landing Page...");
            primaryStage.close();
            try {
                new landingPage().start(new Stage()); // Launch landingPage
            } catch (Exception ex) {
                ex.printStackTrace();
                showErrorPopup(primaryStage, "Navigation Error", "Could not load the Home page. Please ensure 'landingPage.java' exists and is correctly compiled.");
            }
        };

        homeButton.setOnAction(e -> navigateToLandingPage.run());
        logoBox.setOnMouseClicked(e -> navigateToLandingPage.run());

        // Action for Legal News button to navigate to landingPage (with a note about scrolling)
        legalNewsButton.setOnAction(e -> {
            System.out.println("Navigating to Legal News (landingPage)...");
            primaryStage.close();
            try {
                // To scroll directly to the "Latest Legal News" section on landingPage,
                // the landingPage class itself would need to be modified to accept a parameter
                // or have a public method to trigger that scroll after it's displayed.
                // Example (conceptual, requires modification in landingPage):
                // landingPage lp = new landingPage();
                // lp.start(new Stage());
                // lp.scrollToSection("Latest Legal News"); // This method would need to exist in landingPage
                new landingPage().start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
                showErrorPopup(primaryStage, "Navigation Error", "Could not load the Legal News page. Please ensure 'landingPage.java' exists and is correctly compiled.");
            }
        });

        // Action for Contact Us button to scroll to the bottom of the *current* page
        contactUsButton.setOnAction(e -> {
            // Scroll to the end of the ScrollPane's content
            scrollPane.setVvalue(1.0);
        });

        // --- Background Image for the Content Area ---
        ImageView backgroundImage = new ImageView(new Image(BACKGROUND_IMAGE_URL));
        backgroundImage.setFitWidth(1000);
        backgroundImage.setPreserveRatio(true);
        backgroundImage.setOpacity(0.15);
        backgroundImage.setManaged(false);

        // Create a StackPane to layer the background image and the scrollable content
        StackPane contentStack = new StackPane();
        contentStack.getChildren().addAll(backgroundImage, scrollPane);
        StackPane.setAlignment(backgroundImage, Pos.TOP_CENTER);
        backgroundImage.fitWidthProperty().bind(contentStack.widthProperty());

        VBox root = new VBox();
        root.getChildren().addAll(navBar, contentStack);
        root.setStyle("-fx-background-color: #001f4d;");

        Scene scene = new Scene(root, 1550, 890);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox getTitleSection() {
        Label title = new Label("About Bridge To Law");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 36));
        title.setTextFill(Color.WHITE);

        Label sub = new Label("Bridge To Law was founded on a singular vision: to democratize legal access across India. We believe that ethical, reliable, and accessible legal assistance is a fundamental right, not a privilege. Our dedicated team, 'Legal Linkers,' has meticulously crafted this platform to dismantle social barriers and stigma surrounding legal aid. Bridge To Law is more than just an application; it's a transformative movement striving for inclusive justice and a more legally aware society.");
        sub.setWrapText(true);
        sub.setTextAlignment(TextAlignment.CENTER);
        sub.setFont(Font.font("Segoe UI", 16));
        sub.setTextFill(Color.web("#E0E0E0"));
        sub.setMaxWidth(850);

        VBox box = new VBox(title, sub);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(20);
        box.setPadding(new Insets(60, 40, 60, 40));
        box.setStyle("-fx-background-color: #001f4d; -fx-background-radius: 12;");

        return box;
    }

    private HBox getImpactSection() {
        HBox box = new HBox(40);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20, 0, 20, 0));
        box.getChildren().addAll(
                impactBox("Strong Lawyer Database", "We are connected to thousands of verified legal professionals across multiple domains, ensuring diverse expertise.", LAWYER_ICON_URL),
                impactBox("Extensive Case Experience", "A robust history of successfully handled cases across varied legal fields, demonstrating proven success.", EXPERIENCE_ICON_URL),
                impactBox("Pan-India Presence", "Expanding legal access across major metropolitan and growing cities, making justice reachable nationwide.", CITIES_ICON_URL),
                impactBox("Thousands of Satisfied Clients", "Countless individuals have successfully received expert legal help and positive outcomes through our platform.", CLIENTS_ICON_URL)
        );
        return box;
    }

    private VBox impactBox(String title, String desc, String imgPath) {
        ImageView image = new ImageView(new Image(imgPath));
        image.setFitWidth(60);
        image.setPreserveRatio(true);

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        titleLabel.setTextFill(Color.web("#001f4d"));

        Label descLabel = new Label(desc);
        descLabel.setFont(Font.font("Segoe UI", 14));
        descLabel.setWrapText(true);
        descLabel.setTextAlignment(TextAlignment.CENTER);
        descLabel.setTextFill(Color.web("#444444"));

        VBox card = new VBox(image, titleLabel, descLabel);
        card.setAlignment(Pos.TOP_CENTER);
        card.setSpacing(12);
        card.setPadding(new Insets(25));
        card.setPrefWidth(250);
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.15), 10, 0, 0, 6);");
        return card;
    }

    private VBox getTeamSection() {
        Label heading = new Label("Meet Our Visionary Team");
        heading.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        heading.setTextFill(Color.web("#001f4d"));

        HBox members = new HBox(35);
        members.setAlignment(Pos.CENTER);
        members.setPadding(new Insets(20, 0, 20, 0));
        members.getChildren().addAll(
                teamCard("Rohit Vaidya", "", ROHIT_IMAGE_URL),
                teamCard("Swanand Konke", "", SWANAND_IMAGE_URL),
                teamCard("Siddhant Selkar", "", SIDDHANT_IMAGE_URL),
                teamCard("Yash Katkar", "", YASH_IMAGE_URL)
        );

        VBox box = new VBox(heading, members);
        box.setSpacing(30);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    // Modified teamCard method to accept a role
    private VBox teamCard(String name, String role, String imgPath) {
        ImageView image = new ImageView(new Image(imgPath));
        image.setFitWidth(90);
        image.setFitHeight(90);
        image.setPreserveRatio(true);

        Label nameLabel = new Label(name);
        nameLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        nameLabel.setTextFill(Color.web("#001f4d"));

        Label roleLabel = new Label(role); // New label for role
        roleLabel.setFont(Font.font("Segoe UI", 13));
        roleLabel.setTextFill(Color.web("#555555"));

        VBox box = new VBox(image, nameLabel, roleLabel); // Add roleLabel to VBox
        box.setAlignment(Pos.CENTER);
        box.setSpacing(5); // Reduced spacing between name and role
        box.setPadding(new Insets(15));
        box.setPrefWidth(180);
        box.setStyle("-fx-background-color: #F8F8F8; -fx-background-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 8, 0, 0, 4);");
        return box;
    }

    private VBox getValuesSection() {
        Label heading = new Label("Our Core Values");
        heading.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        heading.setTextFill(Color.web("#001f4d"));
        HBox.setMargin(heading, new Insets(0, 0, 20, 0));

        HBox box = new HBox(40);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20, 0, 20, 0));
        box.getChildren().addAll(
                valueCard("⚖", "Justice & Equity", "Committed to upholding fairness, promoting equity, and ensuring ethical service delivery for all."),
                valueCard("🤝", "Trust & Transparency", "Building strong relationships founded on unwavering reliability, clear communication, and complete transparency."),
                valueCard("💡", "Innovation & Accessibility", "Leveraging smart technological solutions to simplify legal processes and make expert legal access effortless for everyone.")
        );
        VBox container = new VBox(heading, box);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(20);
        return container;
    }

    private VBox valueCard(String icon, String title, String desc) {
        Label iconLabel = new Label(icon);
        iconLabel.setFont(Font.font(40));

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        titleLabel.setTextFill(Color.web("#001f4d"));

        Label descLabel = new Label(desc);
        descLabel.setFont(Font.font("Segoe UI", 14));
        descLabel.setWrapText(true);
        descLabel.setTextAlignment(TextAlignment.CENTER);
        descLabel.setTextFill(Color.web("#444444"));

        VBox box = new VBox(iconLabel, titleLabel, descLabel);
        box.setSpacing(12);
        box.setAlignment(Pos.CENTER);
        box.setPrefWidth(280);
        box.setPadding(new Insets(25));
        box.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.15), 10, 0, 0, 6);");
        return box;
    }

    private VBox getInSupportWithSection() {
        Label heading = new Label("In Support With");
        heading.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        heading.setTextFill(Color.web("#001f4d"));

        HBox supportMembers = new HBox(35);
        supportMembers.setAlignment(Pos.CENTER);
        supportMembers.setPadding(new Insets(20, 0, 20, 0));
        supportMembers.getChildren().addAll(
                teamCard("Shashi Sir", "", SHASHI_IMAGE_URL),
                teamCard("Core2Web", "", Core2Web_IMAGE_URL)
        );

        // Miniature "Bridge To Law." logo for embedding in text
        HBox miniLogo = new HBox(-5); // Adjust spacing for mini logo
        Label miniBridgeLabel = new Label("Bridge");
        miniBridgeLabel.setStyle("-fx-text-fill: #001f4d; -fx-font-size: 14px; -fx-font-weight: bold;");
        Label miniToLawLabel = new Label("   To Law.");
        miniToLawLabel.setStyle("-fx-text-fill: #001f4d; -fx-font-size: 14px; -fx-font-weight: bold;");
        //miniLogo.getChildren().addAll(miniBridgeLabel, miniToLawLabel);

        // TextFlow for mixed text and nodes (logo, bold text)
        TextFlow descriptionTextFlow = new TextFlow(
            new Text("Our project "),
            new Text("𝗕𝗿𝗶𝗱𝗴𝗲 𝗧𝗼 𝗟𝗮𝘄. "),
            new Text(" it wouldn't have been possible without our esteemed Guru, "),
            createBoldText("Shashi Sir"),
            new Text(" and "),
            createBoldText("Sachin Sir"),
            new Text(", their guidance helped us make this project into reality, also our mentor, "),
            createBoldText("Gandharv Adsare"),
            new Text(" helped us to solve every difficulties in our path, THANK YOU!")
        );
        descriptionTextFlow.setTextAlignment(TextAlignment.CENTER);
        descriptionTextFlow.setMaxWidth(850);
        descriptionTextFlow.setLineSpacing(5); // Add some line spacing
        descriptionTextFlow.setPadding(new Insets(0, 40, 0, 40)); // Padding to match other sections

        // Set default font for non-bold text in TextFlow
        descriptionTextFlow.getChildren().forEach(node -> {
            if (node instanceof Text) {
                ((Text) node).setFont(Font.font("Segoe UI", 16));
                ((Text) node).setFill(Color.web("#333333"));
            }
        });


        VBox box = new VBox(heading, supportMembers, descriptionTextFlow);
        box.setSpacing(30);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20, 0, 20, 0)); // Padding for the whole section
        return box;
    }

    // Helper method to create bold Text nodes
    private Text createBoldText(String text) {
        Text boldText = new Text(text);
        boldText.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        boldText.setFill(Color.web("#001f4d")); // Dark blue for bold text
        return boldText;
    }


    private VBox getFooterSection() {
        Label heading = new Label("Ready to Transform Your Legal Journey?");
        heading.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        heading.setTextFill(Color.web("#FFFFFF"));

        Label sub = new Label("Join thousands of satisfied clients who have successfully navigated their legal challenges and found their perfect legal match through Bridge To Law.");
        sub.setFont(Font.font("Segoe UI", 15));
        sub.setTextFill(Color.web("#E0E0E0"));
        sub.setWrapText(true);
        sub.setTextAlignment(TextAlignment.CENTER);
        sub.setMaxWidth(700);

        Label contact = new Label("Contact Us: info@bridgetolaw.com | +91 98765 43210");
        contact.setFont(Font.font("Segoe UI", 13));
        contact.setTextFill(Color.web("#CCCCCC"));

        VBox box = new VBox(heading, sub, contact);
        box.setSpacing(15);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(50, 40, 50, 40));
        box.setStyle("-fx-background-color: #001f4d; -fx-background-radius: 12;");
        return box;
    }

    private VBox getFinalSection() {
        Label rights = new Label("\u00A9 2025 Bridge To Law. All rights reserved.");
        rights.setFont(Font.font("Segoe UI", 12));
        rights.setTextFill(Color.web("#BBBBBB"));

        VBox box = new VBox(rights);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20));
        box.setStyle("-fx-background-color: #001f4d;");
        return box;
    }

    /**
     * Displays a custom error popup.
     * @param ownerStage The primary stage, used for modality.
     * @param title The title of the popup.
     * @param message The message to display in the popup.
     */
   /*  private void showErrorPopup(Stage ownerStage, String title, String message) {
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

    public static void main(String[] args) {
        launch(args);
    }
}
*/


/*package com.btl.View;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;
import javafx.stage.Modality;
import com.btl.View.landingPage;
import com.btl.View.AdvocateBiography;

public class aboutus extends Application {

    // --- YOUR ACTUAL IMAGE URLs ---
    private static final String BACKGROUND_IMAGE_URL = "Assets/Images/background.jpg";
    private static final String LAWYER_ICON_URL = "https://placehold.co/60x60/001f4d/ffffff?text=Lawyer";
    private static final String EXPERIENCE_ICON_URL = "https://placehold.co/60x60/001f4d/ffffff?text=Exp";
    private static final String CITIES_ICON_URL = "https://placehold.co/60x60/001f4d/ffffff?text=Cities";
    private static final String CLIENTS_ICON_URL = "https://placehold.co/60x60/001f4d/ffffff?text=Clients";
    private static final String ROHIT_IMAGE_URL = "Assets/Images/rohit.jpg";
    private static final String SWANAND_IMAGE_URL = "Assets/Images/swanand.jpg";
    private static final String SIDDHANT_IMAGE_URL = "Assets/Images/siddhant.jpg";
    private static final String YASH_IMAGE_URL = "Assets/Images/yash.jpg";
    private static final String SHASHI_IMAGE_URL = "Assets/Images/Shashi Sir.jpg";
    private static final String Core2Web_IMAGE_URL = "Assets/Images/Core2Web.jpg";

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("About Bridge To Law");

        // --- Navigation Bar ---
        HBox navBar = new HBox(0);
        navBar.setAlignment(Pos.CENTER_LEFT);
        navBar.setPadding(new Insets(15, 30, 15, 30));
        navBar.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-width: 0 0 1px 0;");

        // Left section: "Bridge To Law." label (now clickable)
        VBox logoBox = new VBox(-10);
        Label bridgeLabel = new Label("Bridge");
        bridgeLabel.setStyle("-fx-text-fill: black; -fx-font-size: 32px; -fx-font-weight: bold;");
        Label toLawLabel = new Label("To Law.");
        toLawLabel.setStyle("-fx-text-fill: black; -fx-font-size: 32px; -fx-font-weight: bold;");
        logoBox.getChildren().addAll(bridgeLabel, toLawLabel);
        logoBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(logoBox, new Insets(0, 60, 0, 0));

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
        Button aboutUsButton = new Button("About Us");
        Button legalNewsButton = new Button("Legal News");
        Button contactUsButton = new Button("Contact Us");

        homeButton.setStyle(mainNavLinkStyle);
        aboutUsButton.setStyle(mainNavLinkHoverStyle);
        legalNewsButton.setStyle(mainNavLinkStyle);
        contactUsButton.setStyle(mainNavLinkStyle);

        homeButton.setOnMouseEntered(e -> homeButton.setStyle(mainNavLinkHoverStyle));
        homeButton.setOnMouseExited(e -> homeButton.setStyle(mainNavLinkStyle));
        legalNewsButton.setOnMouseEntered(e -> legalNewsButton.setStyle(mainNavLinkHoverStyle));
        legalNewsButton.setOnMouseExited(e -> legalNewsButton.setStyle(mainNavLinkStyle));
        contactUsButton.setOnMouseEntered(e -> contactUsButton.setStyle(mainNavLinkHoverStyle));
        contactUsButton.setOnMouseExited(e -> contactUsButton.setStyle(mainNavLinkStyle));

        mainNavLinks.getChildren().addAll(homeButton, aboutUsButton, legalNewsButton, contactUsButton);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        navBar.getChildren().addAll(logoBox, mainNavLinks, spacer);

        // --- Main Content Area ---
        VBox content = new VBox(50);
        content.setPadding(new Insets(30, 0, 30, 0));
        content.setAlignment(Pos.TOP_CENTER);
        content.setStyle("-fx-background-color: transparent;");

        VBox contactSection = getFooterSection();
        VBox finalSection = getFinalSection();
        
        // REORDERED SECTIONS
        content.getChildren().addAll(
                getTitleSection(),
                getImpactSection(),
                getInSupportWithSection(), // Moved this section up
                getTeamSection(),          // Moved this section down
                getValuesSection(),
                contactSection,
                finalSection
        );

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-hbar-policy: never;");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        // --- Navigation Actions ---
        Runnable navigateToLandingPage = () -> {
            System.out.println("Navigating to Landing Page...");
            primaryStage.close();
            try {
                new landingPage().start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
                showErrorPopup(primaryStage, "Navigation Error", "Could not load the Home page. Please ensure 'landingPage.java' exists and is correctly compiled.");
            }
        };

        homeButton.setOnAction(e -> navigateToLandingPage.run());
        logoBox.setOnMouseClicked(e -> navigateToLandingPage.run());

        legalNewsButton.setOnAction(e -> {
            System.out.println("Navigating to Legal News (landingPage)...");
            primaryStage.close();
            try {
                new landingPage().start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
                showErrorPopup(primaryStage, "Navigation Error", "Could not load the Legal News page. Please ensure 'landingPage.java' exists and is correctly compiled.");
            }
        });

        contactUsButton.setOnAction(e -> {
            scrollPane.setVvalue(1.0);
        });

        // --- Background Image for the Content Area ---
        ImageView backgroundImage = new ImageView(new Image(BACKGROUND_IMAGE_URL));
        backgroundImage.setFitWidth(1000);
        backgroundImage.setPreserveRatio(true);
        backgroundImage.setOpacity(0.15);
        backgroundImage.setManaged(false);

        // Create a StackPane to layer the background image and the scrollable content
        StackPane contentStack = new StackPane();
        contentStack.getChildren().addAll(backgroundImage, scrollPane);
        StackPane.setAlignment(backgroundImage, Pos.TOP_CENTER);
        backgroundImage.fitWidthProperty().bind(contentStack.widthProperty());

        VBox root = new VBox();
        root.getChildren().addAll(navBar, contentStack);
        root.setStyle("-fx-background-color: #001f4d;");

        Scene scene = new Scene(root, 1550, 890);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox getTitleSection() {
        Label title = new Label("About Bridge To Law");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 36));
        title.setTextFill(Color.WHITE);

        Label sub = new Label("Bridge To Law was founded on a singular vision: to democratize legal access across India. We believe that ethical, reliable, and accessible legal assistance is a fundamental right, not a privilege. Our dedicated team, 'Legal Linkers,' has meticulously crafted this platform to dismantle social barriers and stigma surrounding legal aid. Bridge To Law is more than just an application; it's a transformative movement striving for inclusive justice and a more legally aware society.");
        sub.setWrapText(true);
        sub.setTextAlignment(TextAlignment.CENTER);
        sub.setFont(Font.font("Segoe UI", 16));
        sub.setTextFill(Color.web("#E0E0E0"));
        sub.setMaxWidth(850);

        VBox box = new VBox(title, sub);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(20);
        box.setPadding(new Insets(60, 40, 60, 40));
        box.setStyle("-fx-background-color: #001f4d; -fx-background-radius: 12;");

        return box;
    }

    private HBox getImpactSection() {
        HBox box = new HBox(40);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20, 0, 20, 0));
        box.getChildren().addAll(
                impactBox("Strong Lawyer Database", "We are connected to thousands of verified legal professionals across multiple domains, ensuring diverse expertise.", LAWYER_ICON_URL),
                impactBox("Extensive Case Experience", "A robust history of successfully handled cases across varied legal fields, demonstrating proven success.", EXPERIENCE_ICON_URL),
                impactBox("Pan-India Presence", "Expanding legal access across major metropolitan and growing cities, making justice reachable nationwide.", CITIES_ICON_URL),
                impactBox("Thousands of Satisfied Clients", "Countless individuals have successfully received expert legal help and positive outcomes through our platform.", CLIENTS_ICON_URL)
        );
        return box;
    }

    private VBox impactBox(String title, String desc, String imgPath) {
        ImageView image = new ImageView(new Image(imgPath));
        image.setFitWidth(60);
        image.setPreserveRatio(true);

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        titleLabel.setTextFill(Color.web("#001f4d"));

        Label descLabel = new Label(desc);
        descLabel.setFont(Font.font("Segoe UI", 14));
        descLabel.setWrapText(true);
        descLabel.setTextAlignment(TextAlignment.CENTER);
        descLabel.setTextFill(Color.web("#444444"));

        VBox card = new VBox(image, titleLabel, descLabel);
        card.setAlignment(Pos.TOP_CENTER);
        card.setSpacing(12);
        card.setPadding(new Insets(25));
        card.setPrefWidth(250);
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.15), 10, 0, 0, 6);");
        return card;
    }

    private VBox getTeamSection() {
        Label heading = new Label("Meet Our Visionary Team");
        heading.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        heading.setTextFill(Color.web("#001f4d"));

        HBox members = new HBox(35);
        members.setAlignment(Pos.CENTER);
        members.setPadding(new Insets(20, 0, 20, 0));
        members.getChildren().addAll(
                teamCard("Rohit Vaidya", "", ROHIT_IMAGE_URL, 90),
                teamCard("Swanand Konke", "", SWANAND_IMAGE_URL, 90),
                teamCard("Siddhant Selkar", "", SIDDHANT_IMAGE_URL, 90),
                teamCard("Yash Katkar", "", YASH_IMAGE_URL, 90)
        );

        VBox box = new VBox(heading, members);
        box.setSpacing(30);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private VBox teamCard(String name, String role, String imgPath, double imageSize) {
        ImageView image = new ImageView(new Image(imgPath));
        image.setFitWidth(imageSize);
        image.setFitHeight(imageSize);
        image.setPreserveRatio(true);

        Label nameLabel = new Label(name);
        nameLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        nameLabel.setTextFill(Color.web("#001f4d"));

        Label roleLabel = new Label(role);
        roleLabel.setFont(Font.font("Segoe UI", 13));
        roleLabel.setTextFill(Color.web("#555555"));

        VBox box = new VBox(image, nameLabel, roleLabel);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(5);
        box.setPadding(new Insets(15));
        box.setPrefWidth(180);
        box.setStyle("-fx-background-color: #F8F8F8; -fx-background-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 8, 0, 0, 4);");
        return box;
    }

    private VBox getValuesSection() {
        Label heading = new Label("Our Core Values");
        heading.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        heading.setTextFill(Color.web("#001f4d"));
        HBox.setMargin(heading, new Insets(0, 0, 20, 0));

        HBox box = new HBox(40);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20, 0, 20, 0));
        box.getChildren().addAll(
                valueCard("⚖", "Justice & Equity", "Committed to upholding fairness, promoting equity, and ensuring ethical service delivery for all."),
                valueCard("🤝", "Trust & Transparency", "Building strong relationships founded on unwavering reliability, clear communication, and complete transparency."),
                valueCard("💡", "Innovation & Accessibility", "Leveraging smart technological solutions to simplify legal processes and make expert legal access effortless for everyone.")
        );
        VBox container = new VBox(heading, box);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(20);
        return container;
    }

    private VBox valueCard(String icon, String title, String desc) {
        Label iconLabel = new Label(icon);
        iconLabel.setFont(Font.font(40));

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        titleLabel.setTextFill(Color.web("#001f4d"));

        Label descLabel = new Label(desc);
        descLabel.setFont(Font.font("Segoe UI", 14));
        descLabel.setWrapText(true);
        descLabel.setTextAlignment(TextAlignment.CENTER);
        descLabel.setTextFill(Color.web("#444444"));

        VBox box = new VBox(iconLabel, titleLabel, descLabel);
        box.setSpacing(12);
        box.setAlignment(Pos.CENTER);
        box.setPrefWidth(280);
        box.setPadding(new Insets(25));
        box.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.15), 10, 0, 0, 6);");
        return box;
    }

    private VBox getInSupportWithSection() {
        Label heading = new Label("In Support With");
        heading.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        heading.setTextFill(Color.web("#001f4d"));

        HBox supportMembers = new HBox(35);
        supportMembers.setAlignment(Pos.CENTER);
        supportMembers.setPadding(new Insets(20, 0, 20, 0));
        // Using a larger image size for the support members
        supportMembers.getChildren().addAll(
                teamCard("Shashi Sir", "", SHASHI_IMAGE_URL, 150),
                teamCard("Core2Web", "", Core2Web_IMAGE_URL, 150)
        );

        TextFlow descriptionTextFlow = new TextFlow(
                new Text("Our project "),
                new Text("𝗕𝗿𝗶𝗱𝗴𝗲 𝗧𝗼 𝗟𝗮𝘄. "),
                new Text(" it wouldn't have been possible without our esteemed Guru, "),
                createBoldText("Shashi Sir"),
                new Text(" and "),
                createBoldText("Sachin Sir"),
                new Text(", their guidance helped us make this project into reality, also our mentor, "),
                createBoldText("Gandharv Adsare"),
                new Text(" helped us to solve every difficulties in our path, THANK YOU!")
        );
        descriptionTextFlow.setTextAlignment(TextAlignment.CENTER);
        descriptionTextFlow.setMaxWidth(850);
        descriptionTextFlow.setLineSpacing(5);
        descriptionTextFlow.setPadding(new Insets(0, 40, 0, 40));

        descriptionTextFlow.getChildren().forEach(node -> {
            if (node instanceof Text) {
                ((Text) node).setFont(Font.font("Segoe UI", 16));
                ((Text) node).setFill(Color.web("#333333"));
            }
        });

        VBox box = new VBox(heading, supportMembers, descriptionTextFlow);
        box.setSpacing(30);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20, 0, 20, 0));
        return box;
    }

    private Text createBoldText(String text) {
        Text boldText = new Text(text);
        boldText.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        boldText.setFill(Color.web("#001f4d"));
        return boldText;
    }


    private VBox getFooterSection() {
        Label heading = new Label("Ready to Transform Your Legal Journey?");
        heading.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        heading.setTextFill(Color.web("#FFFFFF"));

        Label sub = new Label("Join thousands of satisfied clients who have successfully navigated their legal challenges and found their perfect legal match through Bridge To Law.");
        sub.setFont(Font.font("Segoe UI", 15));
        sub.setTextFill(Color.web("#E0E0E0"));
        sub.setWrapText(true);
        sub.setTextAlignment(TextAlignment.CENTER);
        sub.setMaxWidth(700);

        Label contact = new Label("Contact Us: info@bridgetolaw.com | +91 98765 43210");
        contact.setFont(Font.font("Segoe UI", 13));
        contact.setTextFill(Color.web("#CCCCCC"));

        VBox box = new VBox(heading, sub, contact);
        box.setSpacing(15);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(50, 40, 50, 40));
        box.setStyle("-fx-background-color: #001f4d; -fx-background-radius: 12;");
        return box;
    }

    private VBox getFinalSection() {
        Label rights = new Label("\u00A9 2025 Bridge To Law. All rights reserved.");
        rights.setFont(Font.font("Segoe UI", 12));
        rights.setTextFill(Color.web("#BBBBBB"));

        VBox box = new VBox(rights);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20));
        box.setStyle("-fx-background-color: #001f4d;");
        return box;
    }

    private void showErrorPopup(Stage ownerStage, String title, String message) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initOwner(ownerStage);
        popupStage.setTitle(title);

        VBox popupContent = new VBox(20);
        popupContent.setAlignment(Pos.CENTER);
        popupContent.setPadding(new Insets(30));
        popupContent.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 0);");

        Label errorLabel = new Label("✖");
        errorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 60));
        errorLabel.setTextFill(Color.web("#D32F2F"));

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
    
    public static void main(String[] args) {
        launch(args);
    }
}
*/

package com.btl.View;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;
import javafx.stage.Modality;
import com.btl.View.landingPage;
import com.btl.View.AdvocateBiography;

public class aboutus extends Application {

    // --- YOUR ACTUAL IMAGE URLs ---
    private static final String BACKGROUND_IMAGE_URL = "Assets/Images/background.jpg";
    private static final String LAWYER_ICON_URL = "https://placehold.co/60x60/001f4d/ffffff?text=Lawyer";
    private static final String EXPERIENCE_ICON_URL = "https://placehold.co/60x60/001f4d/ffffff?text=Exp";
    private static final String CITIES_ICON_URL = "https://placehold.co/60x60/001f4d/ffffff?text=Cities";
    private static final String CLIENTS_ICON_URL = "https://placehold.co/60x60/001f4d/ffffff?text=Clients";
    private static final String ROHIT_IMAGE_URL = "Assets/Images/rohitvaidya1.jpg";
    private static final String SWANAND_IMAGE_URL = "Assets/Images/swanand.jpg";
    private static final String SIDDHANT_IMAGE_URL = "Assets/Images/siddhant1.jpg";
    private static final String YASH_IMAGE_URL = "Assets/Images/yash.jpg";
    private static final String SHASHI_IMAGE_URL = "Assets/Images/Shashi Sir.jpg";
    private static final String Core2Web_IMAGE_URL = "Assets/Images/Core2Web.jpg";

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("About Bridge To Law");

        // --- Navigation Bar ---
        HBox navBar = new HBox(0);
        navBar.setAlignment(Pos.CENTER_LEFT);
        navBar.setPadding(new Insets(15, 30, 15, 30));
        navBar.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-width: 0 0 1px 0;");

        // Left section: "Bridge To Law." label (now clickable)
        VBox logoBox = new VBox(-10);
        Label bridgeLabel = new Label("Bridge");
        bridgeLabel.setStyle("-fx-text-fill: black; -fx-font-size: 32px; -fx-font-weight: bold;");
        Label toLawLabel = new Label("To Law.");
        toLawLabel.setStyle("-fx-text-fill: black; -fx-font-size: 32px; -fx-font-weight: bold;");
        logoBox.getChildren().addAll(bridgeLabel, toLawLabel);
        logoBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(logoBox, new Insets(0, 60, 0, 0));

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
        Button aboutUsButton = new Button("About Us");
        Button legalNewsButton = new Button("Legal News");
        Button contactUsButton = new Button("Contact Us");

        homeButton.setStyle(mainNavLinkStyle);
        aboutUsButton.setStyle(mainNavLinkHoverStyle);
        legalNewsButton.setStyle(mainNavLinkStyle);
        contactUsButton.setStyle(mainNavLinkStyle);

        homeButton.setOnMouseEntered(e -> homeButton.setStyle(mainNavLinkHoverStyle));
        homeButton.setOnMouseExited(e -> homeButton.setStyle(mainNavLinkStyle));
        legalNewsButton.setOnMouseEntered(e -> legalNewsButton.setStyle(mainNavLinkHoverStyle));
        legalNewsButton.setOnMouseExited(e -> legalNewsButton.setStyle(mainNavLinkStyle));
        contactUsButton.setOnMouseEntered(e -> contactUsButton.setStyle(mainNavLinkHoverStyle));
        contactUsButton.setOnMouseExited(e -> contactUsButton.setStyle(mainNavLinkStyle));

        mainNavLinks.getChildren().addAll(homeButton, aboutUsButton, legalNewsButton, contactUsButton);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        navBar.getChildren().addAll(logoBox, mainNavLinks, spacer);

        // --- Main Content Area ---
        VBox content = new VBox(50);
        content.setPadding(new Insets(30, 0, 30, 0));
        content.setAlignment(Pos.TOP_CENTER);
        content.setStyle("-fx-background-color: transparent;");

        VBox contactSection = getFooterSection();
        VBox finalSection = getFinalSection();
        
        // REORDERED SECTIONS
        content.getChildren().addAll(
                getTitleSection(),
                getImpactSection(),
                getInSupportWithSection(), 
                getTeamSection(),          
                getValuesSection(),
                contactSection,
                finalSection
        );

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-hbar-policy: never;");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        // --- Navigation Actions ---
        Runnable navigateToLandingPage = () -> {
            System.out.println("Navigating to Landing Page...");
            primaryStage.close();
            try {
                new landingPage().start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
                showErrorPopup(primaryStage, "Navigation Error", "Could not load the Home page. Please ensure 'landingPage.java' exists and is correctly compiled.");
            }
        };

        homeButton.setOnAction(e -> navigateToLandingPage.run());
        logoBox.setOnMouseClicked(e -> navigateToLandingPage.run());

        legalNewsButton.setOnAction(e -> {
            System.out.println("Navigating to Legal News (landingPage)...");
            primaryStage.close();
            try {
                new landingPage().start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
                showErrorPopup(primaryStage, "Navigation Error", "Could not load the Legal News page. Please ensure 'landingPage.java' exists and is correctly compiled.");
            }
        });

        contactUsButton.setOnAction(e -> {
            scrollPane.setVvalue(1.0);
        });

        // --- Background Image for the Content Area ---
        ImageView backgroundImage = new ImageView(new Image(BACKGROUND_IMAGE_URL));
        backgroundImage.setFitWidth(1000);
        backgroundImage.setPreserveRatio(true);
        backgroundImage.setOpacity(0.15);
        backgroundImage.setManaged(false);

        // Create a StackPane to layer the background image and the scrollable content
        StackPane contentStack = new StackPane();
        contentStack.getChildren().addAll(backgroundImage, scrollPane);
        StackPane.setAlignment(backgroundImage, Pos.TOP_CENTER);
        backgroundImage.fitWidthProperty().bind(contentStack.widthProperty());

        VBox root = new VBox();
        root.getChildren().addAll(navBar, contentStack);
        root.setStyle("-fx-background-color: #001f4d;");

        Scene scene = new Scene(root, 1550, 890);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox getTitleSection() {
        Label title = new Label("About Bridge To Law");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 36));
        title.setTextFill(Color.WHITE);

        Label sub = new Label("Bridge To Law was founded on a singular vision: to democratize legal access across India. We believe that ethical, reliable, and accessible legal assistance is a fundamental right, not a privilege. Our dedicated team, 'Legal Linkers,' has meticulously crafted this platform to dismantle social barriers and stigma surrounding legal aid. Bridge To Law is more than just an application; it's a transformative movement striving for inclusive justice and a more legally aware society.");
        sub.setWrapText(true);
        sub.setTextAlignment(TextAlignment.CENTER);
        sub.setFont(Font.font("Segoe UI", 16));
        sub.setTextFill(Color.web("#E0E0E0"));
        sub.setMaxWidth(850);

        VBox box = new VBox(title, sub);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(20);
        box.setPadding(new Insets(60, 40, 60, 40));
        box.setStyle("-fx-background-color: #001f4d; -fx-background-radius: 12;");

        return box;
    }

    private HBox getImpactSection() {
        HBox box = new HBox(40);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20, 0, 20, 0));
        box.getChildren().addAll(
                impactBox("Strong Lawyer Database", "We are connected to thousands of verified legal professionals across multiple domains, ensuring diverse expertise.", LAWYER_ICON_URL),
                impactBox("Extensive Case Experience", "A robust history of successfully handled cases across varied legal fields, demonstrating proven success.", EXPERIENCE_ICON_URL),
                impactBox("Pan-India Presence", "Expanding legal access across major metropolitan and growing cities, making justice reachable nationwide.", CITIES_ICON_URL),
                impactBox("Thousands of Satisfied Clients", "Countless individuals have successfully received expert legal help and positive outcomes through our platform.", CLIENTS_ICON_URL)
        );
        return box;
    }

    private VBox impactBox(String title, String desc, String imgPath) {
        ImageView image = new ImageView(new Image(imgPath));
        image.setFitWidth(60);
        image.setPreserveRatio(true);

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        titleLabel.setTextFill(Color.web("#001f4d"));

        Label descLabel = new Label(desc);
        descLabel.setFont(Font.font("Segoe UI", 14));
        descLabel.setWrapText(true);
        descLabel.setTextAlignment(TextAlignment.CENTER);
        descLabel.setTextFill(Color.web("#444444"));

        VBox card = new VBox(image, titleLabel, descLabel);
        card.setAlignment(Pos.TOP_CENTER);
        card.setSpacing(12);
        card.setPadding(new Insets(25));
        card.setPrefWidth(250);
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.15), 10, 0, 0, 6);");
        return card;
    }

    private VBox getTeamSection() {
        Label heading = new Label("Meet Our Visionary Team");
        heading.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        heading.setTextFill(Color.web("#001f4d"));

        HBox members = new HBox(35);
        members.setAlignment(Pos.CENTER);
        members.setPadding(new Insets(20, 0, 20, 0));
        members.getChildren().addAll(
                teamCard("Rohit Vaidya", "", ROHIT_IMAGE_URL, 120),
                teamCard("Swanand Konke", "", SWANAND_IMAGE_URL, 120),
                teamCard("Siddhant Selkar", "", SIDDHANT_IMAGE_URL, 120),
                teamCard("Yash Katkar", "", YASH_IMAGE_URL, 120)
        );

        VBox box = new VBox(heading, members);
        box.setSpacing(30);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private VBox teamCard(String name, String role, String imgPath, double imageSize) {
        ImageView image = new ImageView(new Image(imgPath));
        image.setFitWidth(imageSize);
        image.setFitHeight(imageSize);
        image.setPreserveRatio(true);

        Label nameLabel = new Label(name);
        nameLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        nameLabel.setTextFill(Color.web("#001f4d"));

        Label roleLabel = new Label(role);
        roleLabel.setFont(Font.font("Segoe UI", 13));
        roleLabel.setTextFill(Color.web("#555555"));

        VBox box = new VBox(image, nameLabel, roleLabel);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(5);
        box.setPadding(new Insets(15));
        box.setPrefWidth(180);
        box.setStyle("-fx-background-color: #F8F8F8; -fx-background-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 8, 0, 0, 4);");
        return box;
    }

    private VBox getValuesSection() {
        Label heading = new Label("Our Core Values");
        heading.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        heading.setTextFill(Color.web("#001f4d"));
        HBox.setMargin(heading, new Insets(0, 0, 20, 0));

        HBox box = new HBox(40);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20, 0, 20, 0));
        box.getChildren().addAll(
                valueCard("⚖", "Justice & Equity", "Committed to upholding fairness, promoting equity, and ensuring ethical service delivery for all."),
                valueCard("🤝", "Trust & Transparency", "Building strong relationships founded on unwavering reliability, clear communication, and complete transparency."),
                valueCard("💡", "Innovation & Accessibility", "Leveraging smart technological solutions to simplify legal processes and make expert legal access effortless for everyone.")
        );
        VBox container = new VBox(heading, box);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(20);
        return container;
    }

    private VBox valueCard(String icon, String title, String desc) {
        Label iconLabel = new Label(icon);
        iconLabel.setFont(Font.font(40));

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        titleLabel.setTextFill(Color.web("#001f4d"));

        Label descLabel = new Label(desc);
        descLabel.setFont(Font.font("Segoe UI", 14));
        descLabel.setWrapText(true);
        descLabel.setTextAlignment(TextAlignment.CENTER);
        descLabel.setTextFill(Color.web("#444444"));

        VBox box = new VBox(iconLabel, titleLabel, descLabel);
        box.setSpacing(12);
        box.setAlignment(Pos.CENTER);
        box.setPrefWidth(280);
        box.setPadding(new Insets(25));
        box.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.15), 10, 0, 0, 6);");
        return box;
    }

    private VBox getInSupportWithSection() {
        Label heading = new Label("In Support With");
        heading.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        heading.setTextFill(Color.web("#001f4d"));

        HBox supportMembers = new HBox(35);
        supportMembers.setAlignment(Pos.CENTER);
        supportMembers.setPadding(new Insets(20, 0, 20, 0));
        supportMembers.getChildren().addAll(
                teamCard("Shashi Sir", "", SHASHI_IMAGE_URL, 200),
                teamCard("Core2Web", "", Core2Web_IMAGE_URL, 200)
        );

        TextFlow descriptionTextFlow = new TextFlow(
                new Text("Our project "),
                new Text("𝗕𝗿𝗶𝗱𝗴𝗲 𝗧𝗼 𝗟𝗮𝘄. "),
                new Text(" it wouldn't have been possible without our esteemed Guru, "),
                createBoldText("Shashi Sir"),
                new Text(" and "),
                createBoldText("Sachin Sir"),
                new Text(", their guidance helped us make this project into reality, also our mentor, "),
                createBoldText("Gandharv Adsare"),
                new Text(" helped us to solve every difficulties in our path, THANK YOU!")
        );
        descriptionTextFlow.setTextAlignment(TextAlignment.CENTER);
        descriptionTextFlow.setMaxWidth(850);
        descriptionTextFlow.setLineSpacing(5);
        descriptionTextFlow.setPadding(new Insets(0, 40, 0, 40));

        descriptionTextFlow.getChildren().forEach(node -> {
            if (node instanceof Text) {
                ((Text) node).setFont(Font.font("Segoe UI", 16));
                ((Text) node).setFill(Color.web("#333333"));
            }
        });

        VBox box = new VBox(heading, supportMembers, descriptionTextFlow);
        box.setSpacing(30);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20, 0, 20, 0));
        return box;
    }

    private Text createBoldText(String text) {
        Text boldText = new Text(text);
        boldText.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        boldText.setFill(Color.web("#001f4d"));
        return boldText;
    }


    private VBox getFooterSection() {
        Label heading = new Label("Ready to Transform Your Legal Journey?");
        heading.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        heading.setTextFill(Color.web("#FFFFFF"));

        Label sub = new Label("Join thousands of satisfied clients who have successfully navigated their legal challenges and found their perfect legal match through Bridge To Law.");
        sub.setFont(Font.font("Segoe UI", 15));
        sub.setTextFill(Color.web("#E0E0E0"));
        sub.setWrapText(true);
        sub.setTextAlignment(TextAlignment.CENTER);
        sub.setMaxWidth(700);

        Label contact = new Label("Contact Us: info@bridgetolaw.com | +91 98765 43210");
        contact.setFont(Font.font("Segoe UI", 13));
        contact.setTextFill(Color.web("#CCCCCC"));

        VBox box = new VBox(heading, sub, contact);
        box.setSpacing(15);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(50, 40, 50, 40));
        box.setStyle("-fx-background-color: #001f4d; -fx-background-radius: 12;");
        return box;
    }

    private VBox getFinalSection() {
        Label rights = new Label("\u00A9 2025 Bridge To Law. All rights reserved.");
        rights.setFont(Font.font("Segoe UI", 12));
        rights.setTextFill(Color.web("#BBBBBB"));

        VBox box = new VBox(rights);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20));
        box.setStyle("-fx-background-color: #001f4d;");
        return box;
    }

    private void showErrorPopup(Stage ownerStage, String title, String message) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initOwner(ownerStage);
        popupStage.setTitle(title);

        VBox popupContent = new VBox(20);
        popupContent.setAlignment(Pos.CENTER);
        popupContent.setPadding(new Insets(30));
        popupContent.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 0);");

        Label errorLabel = new Label("✖");
        errorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 60));
        errorLabel.setTextFill(Color.web("#D32F2F"));

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
    
    public static void main(String[] args) {
        launch(args);
    }
}

