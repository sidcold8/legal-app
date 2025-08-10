/*package com.btl.View;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.btl.Dao.getAdvocateDao;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AdvocateGrid extends Application {

     String fullname;
        String specialization;
        String exp ;


    AdvocateGrid(){

        try {
                List<Map<String, Object>> lawyerdata = getAdvocateDao.getLawyer("Lawyerinfo");

            for (int i = 0; i < lawyerdata.size(); i++) {
                Map<String, Object> item = lawyerdata.get(i);
                System.out.println(item);
                System.out.println();

                // fullname = item.get("FOODTYPE") != null ? item.get("FOODTYPE").toString() : "N/A";
                // specialization = item.get("Quantity") != null ? item.get("Quantity").toString() : "N/A";
                // exp = item.get("ValidFor") != null ? item.get("ValidFor").toString() : "N/A";
                

                // System.out.println("FoodType: " + FoodType);
                // System.out.println("ValidFor : "+ ValidFor);
                // System.out.println("Quantity: " + Quantity);
            }

        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (ExecutionException e1) {
            e1.printStackTrace();
        }
    
}

    // --- Image URLs ---
    private static final String BACKGROUND_IMAGE_URL = "Assets/Images/normal.jpg"; // Placeholder background image
    private static final String FEMALE_ICON_PATH = "file:ll/src/main/resources/Assets/Images/female icon.png";
    private static final String MALE_ICON_PATH = "file:ll/src/main/resources/Assets/Images/male icon.png";

    // Standard start method as required by JavaFX, now delegating to the
    // parameterized one.
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Example usage if you were to launch directly without parameters
        start(primaryStage, "Mumbai", "Criminal", "English");
    }

    // Overloaded start method to allow launching with specific parameters
    public void start(Stage primaryStage, String selectedCity, String selectedArea, String selectedLang) {
        primaryStage.setTitle("Bridge To Law - Lawyer Directory");

        // --- Navigation Bar ---
        HBox navBar = getNavBar(primaryStage); // getNavBar now handles navigation actions

        // --- Main Content Area ---
        VBox mainContentLayout = new VBox(10);
        mainContentLayout.setPadding(new Insets(20));
        mainContentLayout.setAlignment(Pos.TOP_CENTER); // Align content to top-center

        // Info
        String infoText = String.format("🔍 Showing results for %s Lawyers in %s", selectedArea, selectedCity);
        Label searchInfo = new Label(infoText);
        searchInfo.setFont(Font.font("Segoe UI", 16)); // Consistent font, added FontWeight.BOLD for emphasis
        searchInfo.setTextFill(Color.web("#001f4d")); // Changed to dark blue for better readability
        searchInfo.setPadding(new Insets(10, 0, 20, 0));

        // Responsive Card Container using FlowPane
        FlowPane cardFlow = new FlowPane();
        cardFlow.setHgap(25); // Increased horizontal gap
        cardFlow.setVgap(25); // Increased vertical gap
        cardFlow.setPadding(new Insets(10));
        cardFlow.setPrefWrapLength(950); // Adjusted for better wrapping on 1000px width
        cardFlow.setAlignment(Pos.CENTER); // Center the cards within the flow pane

        // Add cards with navigation
       try {
    List<Map<String, Object>> lawyerdata = getAdvocateDao.getLawyer("Lawyerinfo");

    for (Map<String, Object> item : lawyerdata) {
        String name = item.get("Fullname") != null ? item.get("Fullname").toString() : "Unknown";
        String specialization = item.get("Specialization") != null ? item.get("Specialization").toString() : "Not specified";
        String experience = item.get("Experience") != null ? item.get("Experience").toString() + " Years Experience" : "N/A";
        String rating = item.get("rating") != null ? item.get("rating").toString() : "4.2/5";
        String city = item.get("city") != null ? item.get("city").toString() : selectedCity;
        String gender = item.get("gender") != null ? item.get("gender").toString().toLowerCase() : "male";

        // Choose icon path based on gender
        String iconPath = gender.equals("female") ? FEMALE_ICON_PATH : MALE_ICON_PATH;

        // Add dynamic card
        addLawyerCard(primaryStage, cardFlow, name, specialization, experience, rating, city, iconPath,item);
    }

} catch (InterruptedException | ExecutionException e) {
    e.printStackTrace();
}


        ScrollPane scrollPane = new ScrollPane(cardFlow);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);
        scrollPane.setStyle("-fx-background-color: transparent;"); // Transparent background for scroll pane

        mainContentLayout.getChildren().addAll(searchInfo, scrollPane);

        // --- Background Image for the Content Area ---
        ImageView backgroundImage = new ImageView(new Image(BACKGROUND_IMAGE_URL));
        backgroundImage.setFitWidth(1000); // Will be bound to StackPane width
        backgroundImage.setPreserveRatio(true);
        backgroundImage.setOpacity(0.10); // Adjusted opacity to 0.10 (10%) for subtle visibility
        backgroundImage.setManaged(false); // Do not let layout manager manage its size/position

        // Create a StackPane to layer the background image and the scrollable content
        StackPane contentStack = new StackPane();
        contentStack.getChildren().addAll(backgroundImage, mainContentLayout);
        StackPane.setAlignment(backgroundImage, Pos.TOP_CENTER); // Align background image
        backgroundImage.fitWidthProperty().bind(contentStack.widthProperty()); // Make background image responsive

        VBox root = new VBox();
        root.getChildren().addAll(navBar, contentStack);
        root.setStyle("-fx-background-color: #f0f2f5;"); // Light grey background for the entire window

        Scene scene = new Scene(root, 1550, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox getNavBar(Stage primaryStage) {
        HBox navBar = new HBox(0);
        navBar.setAlignment(Pos.CENTER_LEFT);
        navBar.setPadding(new Insets(15, 30, 15, 30));
        navBar.setStyle("-fx-background-color: #001f4d; -fx-border-color: #e0e0e0; -fx-border-width: 0 0 1px 0;");

        // Left section: "Bridge To Law." logo
        VBox logoBox = new VBox(-10);
        Label bridgeLabel = new Label("Bridge");
        bridgeLabel.setStyle("-fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: bold;");
        Label toLawLabel = new Label("To Law.");
        toLawLabel.setStyle("-fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: bold;");
        logoBox.getChildren().addAll(bridgeLabel, toLawLabel);
        logoBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(logoBox, new Insets(0, 60, 0, 0));

        // Center section: Main navigation links
        HBox mainNavLinks = new HBox(25);
        mainNavLinks.setAlignment(Pos.CENTER_LEFT);

        String mainNavLinkStyle = "-fx-background-color: transparent; " +
                                  "-fx-text-fill: white; " +
                                  "-fx-font-size: 14px; " +
                                  "-fx-font-weight: normal; " +
                                  "-fx-padding: 5px 0px;";

        String mainNavLinkHoverStyle = "-fx-background-color: transparent; " +
                                       "-fx-text-fill: #A0D2EB; " +
                                       "-fx-font-size: 14px; " +
                                       "-fx-font-weight: normal;";

        Button homeButton = new Button("Home");
        Button findLawyerButton = new Button("Find a Lawyer");
        Button aboutUsButton = new Button("About Us");
        Button legalNewsButton = new Button("Legal News");
        Button contactUsButton = new Button("Contact Us");

        homeButton.setStyle(mainNavLinkStyle);
        // Highlight "Find a Lawyer" as the current page
        findLawyerButton.setStyle(mainNavLinkHoverStyle + "-fx-underline: true;");
        aboutUsButton.setStyle(mainNavLinkStyle);
        legalNewsButton.setStyle(mainNavLinkStyle);
        contactUsButton.setStyle(mainNavLinkStyle);

        // Hover effects for navigation buttons
        homeButton.setOnMouseEntered(e -> homeButton.setStyle(mainNavLinkHoverStyle));
        homeButton.setOnMouseExited(e -> homeButton.setStyle(mainNavLinkStyle));
        findLawyerButton.setOnMouseEntered(e -> findLawyerButton.setStyle(mainNavLinkHoverStyle));
        findLawyerButton.setOnMouseExited(e -> findLawyerButton.setStyle(mainNavLinkHoverStyle + "-fx-underline: true;")); // Keep underline on exit
        aboutUsButton.setOnMouseEntered(e -> aboutUsButton.setStyle(mainNavLinkHoverStyle));
        aboutUsButton.setOnMouseExited(e -> aboutUsButton.setStyle(mainNavLinkStyle));
        legalNewsButton.setOnMouseEntered(e -> legalNewsButton.setStyle(mainNavLinkHoverStyle));
        legalNewsButton.setOnMouseExited(e -> legalNewsButton.setStyle(mainNavLinkStyle));
        contactUsButton.setOnMouseEntered(e -> contactUsButton.setStyle(mainNavLinkHoverStyle));
        contactUsButton.setOnMouseExited(e -> contactUsButton.setStyle(mainNavLinkStyle));

        mainNavLinks.getChildren().addAll(homeButton, findLawyerButton, aboutUsButton, legalNewsButton, contactUsButton);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        navBar.getChildren().addAll(logoBox, mainNavLinks, spacer);

        // --- Navigation Actions ---
        // RESTORED: Action for Home Button and Logo
        homeButton.setOnAction(e -> {
            System.out.println("Navigating to Home (landingPage)...");
            primaryStage.close();
            try {
                new landingPage().start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
                showErrorPopup(primaryStage, "Navigation Error", "Could not load the Home page. Please ensure 'landingPage.java' exists and is correctly compiled.");
            }
        });
        logoBox.setOnMouseClicked(e -> { // Make logo clickable for Home
            System.out.println("Navigating to Home (landingPage) via logo...");
            primaryStage.close();
            try {
                new landingPage().start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
                showErrorPopup(primaryStage, "Navigation Error", "Could not load the Home page. Please ensure 'landingPage.java' exists and is correctly compiled.");
            }
        });


        // RESTORED: Action for About Us button
        aboutUsButton.setOnAction(e -> {
            System.out.println("Navigating to About Us Page...");
            primaryStage.close();
            try {
                new aboutus().start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
                showErrorPopup(primaryStage, "Navigation Error", "Could not load the About Us page. Please ensure 'aboutus.java' exists and is correctly compiled.");
            }
        });

        // RESTORED: Action for Legal News button
        legalNewsButton.setOnAction(e -> {
            System.out.println("Navigating to Legal News (landingPage)...");
            primaryStage.close();
            try {
                // To scroll directly to the "Latest Legal News" section on landingPage,
                // the landingPage class itself would need to be modified to accept a parameter
                // or have a public method to trigger that scroll after it's displayed.
                new landingPage().start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
                showErrorPopup(primaryStage, "Navigation Error", "Could not load the Legal News page. Please ensure 'landingPage.java' exists and is correctly compiled.");
            }
        });

        // RESTORED: Action for Contact Us button
        contactUsButton.setOnAction(e -> {
            System.out.println("Navigating to Contact Us (About Us Page bottom)...");
            primaryStage.close();
            try {
                // To scroll directly to the bottom of the About Us page,
                // the aboutus class itself would be modified to accept a parameter
                // or have a public method to trigger that scroll after it's displayed.
                new aboutus().start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
                showErrorPopup(primaryStage, "Navigation Error", "Could not load the Contact Us page. Please ensure 'aboutus.java' exists and is correctly compiled.");
            }
        });

        return navBar;
    }

    private void addLawyerCard(Stage primaryStage, Pane parent, String name, String type, String exp, String rating, String city, String imgPath,Map<String,Object> lawyerdata) {
        VBox card = new VBox(8); // Reduced spacing for a tighter look
        card.setPadding(new Insets(15));
        card.setPrefWidth(280); // Adjusted width for better fit in FlowPane
        card.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #e0e0e0; -fx-border-width: 1px; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0, 0, 4);");

        card.setOnMouseEntered(e -> card.setStyle(
                "-fx-background-color: #F0F8FF; -fx-border-color: #001f4d; -fx-border-width: 2px; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 15, 0, 0, 6);"
        ));
        card.setOnMouseExited(e -> card.setStyle(
                "-fx-background-color: #FFFFFF; -fx-border-color: #e0e0e0; -fx-border-width: 1px; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0, 0, 4);"
        ));

        HBox imageBox = new HBox();
        imageBox.setAlignment(Pos.CENTER);
        ImageView image = new ImageView(new Image(imgPath));
        image.setFitWidth(80); // Larger image
        image.setFitHeight(80);
        image.setPreserveRatio(false); // Allow non-proportional scaling for square
        image.setClip(new javafx.scene.shape.Rectangle(80, 80)); // Clip to square
        image.setStyle("-fx-border-color: #001f4d; -fx-border-width: 2; -fx-border-radius: 5;"); // Border around image
        imageBox.getChildren().add(image);

        Label nameLabel = new Label("Adv. " + name);
        nameLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20)); // Bolder, larger font
        nameLabel.setTextFill(Color.web("#001f4d"));

        Label ratingLabel = new Label("⭐ " + rating);
        ratingLabel.setFont(Font.font("Segoe UI", 14));
        ratingLabel.setTextFill(Color.web("#555555"));

        Label expLabel = new Label("💼 " + exp); // Changed icon
        expLabel.setFont(Font.font("Segoe UI", 14));
        expLabel.setTextFill(Color.web("#555555"));

        Label cityLabel = new Label("📍 " + city);
        cityLabel.setFont(Font.font("Segoe UI", 14));
        cityLabel.setTextFill(Color.web("#555555"));

        Label tagLabel = new Label(type);
        tagLabel.setStyle("-fx-background-color: #607D8B; -fx-text-fill: white; -fx-padding: 4 10 4 10; -fx-background-radius: 15px; -fx-font-size: 12px;"); // More rounded, slightly larger padding

        Button viewButton = new Button("View Profile");
        viewButton.setStyle(
            "-fx-background-color: #001f4d; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 14px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 8px 15px; " +
            "-fx-background-radius: 5; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 0);"
        );
        viewButton.setOnMouseEntered(e -> viewButton.setStyle(
            "-fx-background-color: #003366; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 14px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 8px 15px; " +
            "-fx-background-radius: 5; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 0);"
        ));
        viewButton.setOnMouseExited(e -> viewButton.setStyle(
            "-fx-background-color: #001f4d; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 14px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 8px 15px; " +
            "-fx-background-radius: 5; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 0);"
        ));
        viewButton.setPrefWidth(Double.MAX_VALUE); // Make button fill card width

        viewButton.setOnAction(e -> {
            System.out.println("Opening profile for: " + name);
            primaryStage.close(); // Close the current AdvocateGrid stage
            try {
                // Pass all relevant lawyer details to the AdvocateBiography class
                new AdvocateBiography(lawyerdata).start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
                showErrorPopup(primaryStage, "Navigation Error", "Could not load the Advocate Biography page. Please ensure 'AdvocateBiography.java' exists and is correctly compiled with the overloaded start method.");
            }
        });

        card.getChildren().addAll(imageBox, nameLabel, ratingLabel, expLabel, cityLabel, tagLabel, viewButton);
        parent.getChildren().add(card);
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
        messageLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

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
}*/

package com.btl.View;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.btl.Dao.getAdvocateDao;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AdvocateGrid extends Application {

    String fullname;
    String specialization;
    String exp;

    // Make the no-argument constructor PUBLIC and ideally, keep it simple.
    // Data fetching should happen in start() or a method called from start().
    public AdvocateGrid() {
        // You can leave this empty or put very minimal, non-UI-related initialization here.
        // The data fetching logic will be moved to the start() method.
        System.out.println("AdvocateGrid no-argument constructor called.");
    }

    // --- Image URLs ---
    private static final String BACKGROUND_IMAGE_URL = "Assets/Images/normal.jpg";
    private static final String FEMALE_ICON_PATH = "Assets/Images/female icon.png";
    private static final String MALE_ICON_PATH = "Assets/Images/male icon.png";

    // Standard start method as required by JavaFX, now delegating to the
    // parameterized one.
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Example usage if you were to launch directly without parameters
        // These are default values for demonstration.
        start(primaryStage, "Mumbai", "Criminal", "English");
    }

    // Overloaded start method to allow launching with specific parameters
    public void start(Stage primaryStage, String selectedCity, String selectedArea, String selectedLang) {
        primaryStage.setTitle("Bridge To Law - Lawyer Directory");

        // --- Navigation Bar ---
        HBox navBar = getNavBar(primaryStage); // getNavBar now handles navigation actions

        // --- Main Content Area ---
        VBox mainContentLayout = new VBox(10);
        mainContentLayout.setPadding(new Insets(20));
        mainContentLayout.setAlignment(Pos.TOP_CENTER); // Align content to top-center

        // Info
        String infoText = String.format("🔍 Showing results for %s Lawyers in %s", selectedArea, selectedCity);
        Label searchInfo = new Label(infoText);
        searchInfo.setFont(Font.font("Segoe UI", 16));
        searchInfo.setTextFill(Color.web("#001f4d"));
        searchInfo.setPadding(new Insets(10, 0, 20, 0));

        // Responsive Card Container using FlowPane
        FlowPane cardFlow = new FlowPane();
        cardFlow.setHgap(25);
        cardFlow.setVgap(25);
        cardFlow.setPadding(new Insets(10));
        cardFlow.setPrefWrapLength(950);
        cardFlow.setAlignment(Pos.CENTER);

        // --- Data Fetching and Card Population (MOVED HERE) ---
        try {
            List<Map<String, Object>> lawyerdata = getAdvocateDao.getLawyer("Lawyerinfo");

            if (lawyerdata != null && !lawyerdata.isEmpty()) {
                for (Map<String, Object> item : lawyerdata) {
                    String name = item.get("Fullname") != null ? item.get("Fullname").toString() : "Unknown";
                    String specialization = item.get("Specialization") != null ? item.get("Specialization").toString() : "Not specified";
                    String experience = item.get("Experience") != null ? item.get("Experience").toString() + " Years Experience" : "N/A";
                    String rating = item.get("rating") != null ? item.get("rating").toString() : "4.2/5";
                    String city = item.get("city") != null ? item.get("city").toString() : selectedCity;
                    String gender = item.get("gender") != null ? item.get("gender").toString().toLowerCase() : "male";

                    // Choose icon path based on gender
                    String iconPath = gender.equals("female") ? FEMALE_ICON_PATH : MALE_ICON_PATH;

                    // Add dynamic card
                    addLawyerCard(primaryStage, cardFlow, name, specialization, experience, rating, city, iconPath, item);
                }
            } else {
                Label noLawyersLabel = new Label("No lawyers found matching your criteria.");
                noLawyersLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 16));
                noLawyersLabel.setTextFill(Color.web("#555555"));
                cardFlow.getChildren().add(noLawyersLabel);
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            showErrorPopup(primaryStage, "Data Load Error", "Failed to load lawyer data. Please check your database connection or data source.");
        }
        // --- End Data Fetching ---

        ScrollPane scrollPane = new ScrollPane(cardFlow);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);
        scrollPane.setStyle("-fx-background-color: transparent;");

        mainContentLayout.getChildren().addAll(searchInfo, scrollPane);

        // --- Background Image for the Content Area ---
        ImageView backgroundImage = new ImageView(new Image(BACKGROUND_IMAGE_URL));
        backgroundImage.setFitWidth(1000);
        backgroundImage.setPreserveRatio(true);
        backgroundImage.setOpacity(0.10);
        backgroundImage.setManaged(false);

        StackPane contentStack = new StackPane();
        contentStack.getChildren().addAll(backgroundImage, mainContentLayout);
        StackPane.setAlignment(backgroundImage, Pos.TOP_CENTER);
        backgroundImage.fitWidthProperty().bind(contentStack.widthProperty());

        VBox root = new VBox();
        root.getChildren().addAll(navBar, contentStack);
        root.setStyle("-fx-background-color: #f0f2f5;");

        Scene scene = new Scene(root, 1550, 890);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox getNavBar(Stage primaryStage) {
        HBox navBar = new HBox(0);
        navBar.setAlignment(Pos.CENTER_LEFT);
        navBar.setPadding(new Insets(15, 30, 15, 30));
        navBar.setStyle("-fx-background-color: #001f4d; -fx-border-color: #e0e0e0; -fx-border-width: 0 0 1px 0;");

        VBox logoBox = new VBox(-10);
        Label bridgeLabel = new Label("Bridge");
        bridgeLabel.setStyle("-fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: bold;");
        Label toLawLabel = new Label("To Law.");
        toLawLabel.setStyle("-fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: bold;");
        logoBox.getChildren().addAll(bridgeLabel, toLawLabel);
        logoBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(logoBox, new Insets(0, 60, 0, 0));

        HBox mainNavLinks = new HBox(25);
        mainNavLinks.setAlignment(Pos.CENTER_LEFT);

        String mainNavLinkStyle = "-fx-background-color: transparent; " +
                                  "-fx-text-fill: white; " +
                                  "-fx-font-size: 14px; " +
                                  "-fx-font-weight: normal; " +
                                  "-fx-padding: 5px 0px;";

        String mainNavLinkHoverStyle = "-fx-background-color: transparent; " +
                                       "-fx-text-fill: #A0D2EB; " +
                                       "-fx-font-size: 14px; " +
                                       "-fx-font-weight: normal;";

        Button homeButton = new Button("Home");
        Button findLawyerButton = new Button("Find a Lawyer");
        Button aboutUsButton = new Button("About Us");
        Button legalNewsButton = new Button("Legal News");
        Button contactUsButton = new Button("Contact Us");

        homeButton.setStyle(mainNavLinkStyle);
        findLawyerButton.setStyle(mainNavLinkHoverStyle);
        aboutUsButton.setStyle(mainNavLinkStyle);
        legalNewsButton.setStyle(mainNavLinkStyle);
        contactUsButton.setStyle(mainNavLinkStyle);

        homeButton.setOnMouseEntered(e -> homeButton.setStyle(mainNavLinkHoverStyle));
        homeButton.setOnMouseExited(e -> homeButton.setStyle(mainNavLinkStyle));
        findLawyerButton.setOnMouseEntered(e -> findLawyerButton.setStyle(mainNavLinkHoverStyle));
        findLawyerButton.setOnMouseExited(e -> findLawyerButton.setStyle(mainNavLinkHoverStyle + "-fx-underline: true;"));
        aboutUsButton.setOnMouseEntered(e -> aboutUsButton.setStyle(mainNavLinkHoverStyle));
        aboutUsButton.setOnMouseExited(e -> aboutUsButton.setStyle(mainNavLinkStyle));
        legalNewsButton.setOnMouseEntered(e -> legalNewsButton.setStyle(mainNavLinkHoverStyle));
        legalNewsButton.setOnMouseExited(e -> legalNewsButton.setStyle(mainNavLinkStyle));
        contactUsButton.setOnMouseEntered(e -> contactUsButton.setStyle(mainNavLinkHoverStyle));
        contactUsButton.setOnMouseExited(e -> contactUsButton.setStyle(mainNavLinkStyle));

        mainNavLinks.getChildren().addAll(homeButton, findLawyerButton, aboutUsButton, legalNewsButton, contactUsButton);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        navBar.getChildren().addAll(logoBox, mainNavLinks, spacer);

        homeButton.setOnAction(e -> {
            System.out.println("Navigating to Home (landingPage)...");
            primaryStage.close();
            try {
                new landingPage().start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
                showErrorPopup(primaryStage, "Navigation Error", "Could not load the Home page. Please ensure 'landingPage.java' exists and is correctly compiled.");
            }
        });
        logoBox.setOnMouseClicked(e -> {
            System.out.println("Navigating to Home (landingPage) via logo...");
            primaryStage.close();
            try {
                new landingPage().start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
                showErrorPopup(primaryStage, "Navigation Error", "Could not load the Home page. Please ensure 'landingPage.java' exists and is correctly compiled.");
            }
        });

        // Assuming aboutus and landingPage are in the same package or imported
        aboutUsButton.setOnAction(e -> {
            System.out.println("Navigating to About Us Page...");
            primaryStage.close();
            try {
                new aboutus().start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
                showErrorPopup(primaryStage, "Navigation Error", "Could not load the About Us page. Please ensure 'aboutus.java' exists and is correctly compiled.");
            }
        });

        legalNewsButton.setOnAction(e -> {
            System.out.println("Navigating to Legal News (landingPage)...");
            primaryStage.close();
            try {
                new landingPage().start(new Stage()); // Assuming legal news is on landing page
            } catch (Exception ex) {
                ex.printStackTrace();
                showErrorPopup(primaryStage, "Navigation Error", "Could not load the Legal News page. Please ensure 'landingPage.java' exists and is correctly compiled.");
            }
        });

        contactUsButton.setOnAction(e -> {
            System.out.println("Navigating to Contact Us (About Us Page bottom)...");
            primaryStage.close();
            try {
                new aboutus().start(new Stage()); // Assuming contact us is on about us page
            } catch (Exception ex) {
                ex.printStackTrace();
                showErrorPopup(primaryStage, "Navigation Error", "Could not load the Contact Us page. Please ensure 'aboutus.java' exists and is correctly compiled.");
            }
        });

        return navBar;
    }

    private void addLawyerCard(Stage primaryStage, Pane parent, String name, String type, String exp, String rating, String city, String imgPath, Map<String, Object> lawyerdata) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(15));
        card.setPrefWidth(280);
        card.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #e0e0e0; -fx-border-width: 1px; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0, 0, 4);");

        card.setOnMouseEntered(e -> card.setStyle(
                "-fx-background-color: #F0F8FF; -fx-border-color: #001f4d; -fx-border-width: 2px; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 15, 0, 0, 6);"
        ));
        card.setOnMouseExited(e -> card.setStyle(
                "-fx-background-color: #FFFFFF; -fx-border-color: #e0e0e0; -fx-border-width: 1px; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0, 0, 4);"
        ));

        HBox imageBox = new HBox();
        imageBox.setAlignment(Pos.CENTER);
        ImageView image = new ImageView(new Image(imgPath));
        image.setFitWidth(80);
        image.setFitHeight(80);
        image.setPreserveRatio(false);
        image.setClip(new javafx.scene.shape.Rectangle(80, 80));
        image.setStyle("-fx-border-color: #001f4d; -fx-border-width: 2; -fx-border-radius: 5;");
        imageBox.getChildren().add(image);

        Label nameLabel = new Label(" " + name);
        nameLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        nameLabel.setTextFill(Color.web("#001f4d"));

        Label ratingLabel = new Label("⭐ " + rating);
        ratingLabel.setFont(Font.font("Segoe UI", 14));
        ratingLabel.setTextFill(Color.web("#555555"));

        Label expLabel = new Label("💼 " + exp);
        expLabel.setFont(Font.font("Segoe UI", 14));
        expLabel.setTextFill(Color.web("#555555"));

        Label cityLabel = new Label("📍 " + city);
        cityLabel.setFont(Font.font("Segoe UI", 14));
        cityLabel.setTextFill(Color.web("#555555"));

        Label tagLabel = new Label(type);
        tagLabel.setStyle("-fx-background-color: #607D8B; -fx-text-fill: white; -fx-padding: 4 10 4 10; -fx-background-radius: 15px; -fx-font-size: 12px;");

        Button viewButton = new Button("View Profile");
        viewButton.setStyle(
                "-fx-background-color: #001f4d; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 8px 15px; " +
                        "-fx-background-radius: 5; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 0);"
        );
        viewButton.setOnMouseEntered(e -> viewButton.setStyle(
                "-fx-background-color: #003366; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 8px 15px; " +
                        "-fx-background-radius: 5; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 0);"
        ));
        viewButton.setOnMouseExited(e -> viewButton.setStyle(
                "-fx-background-color: #001f4d; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 8px 15px; " +
                        "-fx-background-radius: 5; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 0);"
        ));
        viewButton.setPrefWidth(Double.MAX_VALUE);

        viewButton.setOnAction(e -> {
            System.out.println("Opening profile for: " + name);
            primaryStage.close();
            try {
                new AdvocateBiography(lawyerdata).start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
                showErrorPopup(primaryStage, "Navigation Error", "Could not load the Advocate Biography page. Please ensure 'AdvocateBiography.java' exists and is correctly compiled with the overloaded start method.");
            }
        });

        card.getChildren().addAll(imageBox, nameLabel, ratingLabel, expLabel, cityLabel, tagLabel, viewButton);
        parent.getChildren().add(card);
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
        messageLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

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

