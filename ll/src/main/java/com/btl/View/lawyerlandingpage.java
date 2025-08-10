package com.btl.View;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class lawyerlandingpage extends Application {

    Stage myStage;
    private List<List<NewsArticle>> paginatedNews = new ArrayList<>();
    private AtomicInteger currentPageIndex = new AtomicInteger(0);
    private StackPane newsCardsContainer;
    private ProgressBar newsLoadingIndicator;
    private Timeline newsCycleTimeline; // For controlling the news cycling animation

    // References for scrolling
    private ScrollPane mainScrollPane;
    private VBox legalNewsSection; // Now a class member for scrolling

    // --- NewsArticle Data Model (Simple Record) ---
    private record NewsArticle(String title, String description, String imageUrl, String articleUrl) {}

    // Background image URL for the main content area
    private static final String BACKGROUND_IMAGE_URL = "Assets/Images/background img.jpg"; // Using a generic background image

    @Override
    public void start(Stage primaryStage) {
        myStage = primaryStage;
        primaryStage.setTitle("Bridge To Law - Lawyer Portal"); // Updated Title

        // --- NavBar ---
        HBox navBar = new HBox(30);
        navBar.setPadding(new Insets(20, 50, 20, 50)); // Original padding, not 40
        // Changed navBar background to dark blue
        navBar.setStyle("-fx-background-color: #001f4d; -fx-border-color: #e5e5e5; -fx-border-width: 0 0 1 0;");
        navBar.setAlignment(Pos.CENTER_LEFT);

        VBox logoBox = new VBox(-8); // Adjusted negative spacing for tighter logo
        Label bridgeLabel = new Label("Bridge");
        bridgeLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 28)); // Larger font
        bridgeLabel.setTextFill(Color.WHITE);
        Label toLawLabel = new Label("To Law.");
        toLawLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 28)); // Larger font
        toLawLabel.setTextFill(Color.WHITE);
        logoBox.getChildren().addAll(bridgeLabel, toLawLabel);
        logoBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(logoBox, new Insets(0, 80, 0, 0)); // Increased margin to the right of the logo
        logoBox.setOnMouseClicked(e -> {
            System.out.println("Navigating to Home (lawyerlandingpage) via logo...");
            myStage.close();
            try {
                new lawyerlandingpage().start(primaryStage); // Reload this page as home
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert("Navigation Error", "Could not load the Home page.", ex.getMessage(), Alert.AlertType.ERROR);
            }
        });

        String navLinkStyle = "-fx-background-color: transparent; " +
                                "-fx-text-fill: white; " + // Changed text color to white
                                "-fx-font-size: 14px; " +
                                "-fx-font-weight: normal;";
        String navLinkHoverStyle = "-fx-background-color: rgba(255,255,255,0.2); " + // Light overlay on hover
                                   "-fx-text-fill: white; " +
                                   "-fx-font-size: 14px; " +
                                   "-fx-font-weight: bold; -fx-background-radius: 5;"; // Added bold and radius on hover

        Button homeButton = new Button("Home");
        homeButton.setStyle(navLinkStyle);
        homeButton.setOnMouseEntered(e -> homeButton.setStyle(navLinkHoverStyle));
        homeButton.setOnMouseExited(e -> homeButton.setStyle(navLinkStyle));
        homeButton.setOnAction(e -> {
            System.out.println("Navigating to Home (lawyerlandingpage)...");
            myStage.close();
            try {
                new lawyerlandingpage().start(primaryStage); // Reload this page as home
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert("Navigation Error", "Could not load the Home page.", ex.getMessage(), Alert.AlertType.ERROR);
            }
        });

        Button aboutUsButton = new Button("About Us");
        aboutUsButton.setStyle(navLinkStyle);
        aboutUsButton.setOnMouseEntered(e -> aboutUsButton.setStyle(navLinkHoverStyle));
        aboutUsButton.setOnMouseExited(e -> aboutUsButton.setStyle(navLinkStyle));
        aboutUsButton.setOnAction(e -> {
            System.out.println("Navigating to About Us Page...");
            myStage.close();
            try {
                new aboutus().start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert("Navigation Error", "Could not load the About Us page.", ex.getMessage(), Alert.AlertType.ERROR);
            }
        });

        // Added Contact Us button
        Button contactUsButton = new Button("Contact Us");
        contactUsButton.setStyle(navLinkStyle);
        contactUsButton.setOnMouseEntered(e -> contactUsButton.setStyle(navLinkHoverStyle));
        contactUsButton.setOnMouseExited(e -> contactUsButton.setStyle(navLinkStyle));
        contactUsButton.setOnAction(e -> {
            System.out.println("Navigating to Contact Us (About Us Page)...");
            myStage.close();
            try {
                new aboutus().start(primaryStage); // Assuming Contact Us is part of About Us or a similar page
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert("Navigation Error", "Could not load the Contact Us page.", ex.getMessage(), Alert.AlertType.ERROR);
            }
        });

        // New "News" button for scrolling to news section
        Button newsSectionButton = new Button("📰 News");
        newsSectionButton.setStyle(navLinkStyle); // Use consistent navLinkStyle
        newsSectionButton.setOnMouseEntered(e -> newsSectionButton.setStyle(navLinkHoverStyle));
        newsSectionButton.setOnMouseExited(e -> newsSectionButton.setStyle(navLinkStyle));
        newsSectionButton.setOnAction(e -> {
            // Scroll to the legalNewsSection
            if (mainScrollPane != null && legalNewsSection != null) {
                double targetY = legalNewsSection.getBoundsInParent().getMinY();
                double scrollHeight = mainScrollPane.getContent().getBoundsInLocal().getHeight();
                double viewportHeight = mainScrollPane.getViewportBounds().getHeight();
                
                if (scrollHeight > viewportHeight) {
                    double vValue = targetY / (scrollHeight - viewportHeight);
                    mainScrollPane.setVvalue(vValue);
                } else {
                    mainScrollPane.setVvalue(0); // If content is smaller than viewport, just go to top
                }
            }
        });

        // Notification Button
        Button notificationButton = new Button("🔔 Notifications");
        notificationButton.setStyle(navLinkStyle); // Use consistent navLinkStyle
        notificationButton.setOnMouseEntered(e -> notificationButton.setStyle(navLinkHoverStyle));
        notificationButton.setOnMouseExited(e -> notificationButton.setStyle(navLinkStyle));
        // --- ADDED NAVIGATION FOR NOTIFICATION BUTTON ---
        notificationButton.setOnAction(e -> {
            System.out.println("Navigating to Notifications Page...");
            myStage.close();
            try {
                new notificationPage().start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert("Navigation Error", "Could not load the Notifications page.", ex.getMessage(), Alert.AlertType.ERROR);
            }
        });

        // NEW: Logout Button
        Button logoutButton = new Button("Logout");
        logoutButton.setStyle(navLinkStyle); // Apply consistent style
        logoutButton.setOnMouseEntered(e -> logoutButton.setStyle(navLinkHoverStyle));
        logoutButton.setOnMouseExited(e -> logoutButton.setStyle(navLinkStyle));
        logoutButton.setOnAction(e -> {
            System.out.println("Logging out and navigating to Login Page...");
            myStage.close();
            try {
                new loginPage().start(primaryStage); // Navigate to the loginPage
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert("Navigation Error", "Could not load the Login page after logout.", ex.getMessage(), Alert.AlertType.ERROR);
            }
        });

        Region spacerLeft = new Region(); // Spacer to push logo to left and nav links to right
        HBox.setHgrow(spacerLeft, Priority.ALWAYS);

        navBar.getChildren().addAll(logoBox, spacerLeft, homeButton, aboutUsButton, contactUsButton, newsSectionButton, notificationButton, logoutButton); // Added logoutButton


        // --- Hero Section ---
        GridPane hero = new GridPane();
        hero.setPadding(new Insets(50, 80, 50, 80));
        hero.setHgap(50);
        hero.setVgap(20);
        hero.setAlignment(Pos.CENTER);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.ALWAYS);
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        col2.setPercentWidth(50);
        hero.getColumnConstraints().addAll(col1, col2);

        VBox heroTextContent = new VBox(15);
        heroTextContent.setAlignment(Pos.CENTER_LEFT);

        Label heading = new Label("Empowering Legal Professionals with Insights & Tools"); // Adjusted heading
        heading.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        heading.setTextFill(Color.web("#ffffffff")); // Kept original text color for contrast with background
        heading.setWrapText(true);

        Label subheading = new Label("Access case history, legal news, and manage your profile efficiently."); // Adjusted subheading
        subheading.setFont(Font.font("Arial", 16));
        subheading.setTextFill(Color.web("#ffffffff")); // Kept original text color
        subheading.setWrapText(true);

        // Replaced "Find a Lawyer" and "Sign Up" buttons
        Button caseHistoryButton = new Button("📂 Case History");
        caseHistoryButton.setStyle("-fx-background-color: #001f4d; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12 25; -fx-background-radius: 8;");
        caseHistoryButton.setOnMouseEntered(e -> caseHistoryButton.setStyle("-fx-background-color: #003366; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12 25; -fx-background-radius: 8;"));
        caseHistoryButton.setOnMouseExited(e -> caseHistoryButton.setStyle("-fx-background-color: #001f4d; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12 25; -fx-background-radius: 8;"));
        // --- ADDED NAVIGATION FOR CASE HISTORY BUTTON ---
        caseHistoryButton.setOnAction(e -> {
            System.out.println("Navigating to Case History (notificationPage)...");
            myStage.close();
            try {
                new notificationPage().start(primaryStage); // Navigating to notificationPage as requested
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert("Navigation Error", "Could not load the Case History page.", ex.getMessage(), Alert.AlertType.ERROR);
            }
        });

        Button editProfileButton = new Button("✎ Edit Profile");
        editProfileButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12 25; -fx-background-radius: 8;");
        editProfileButton.setOnMouseEntered(e -> editProfileButton.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12 25; -fx-background-radius: 8;"));
        editProfileButton.setOnMouseExited(e -> editProfileButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12 25; -fx-background-radius: 8;"));
        editProfileButton.setOnAction(e -> {
            System.out.println("Navigating to Edit Profile (LD Page)..."); // Updated console message
            myStage.close();
            try {
                new LD().start(primaryStage); // Navigating to LD for profile editing
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert("Navigation Error", "Could not load the Edit Profile page.", ex.getMessage(), Alert.AlertType.ERROR);
            }
        });


        HBox heroButtons = new HBox(15, caseHistoryButton, editProfileButton); // Added Edit Profile button
        heroTextContent.getChildren().addAll(heading, subheading, heroButtons);

        StackPane illustrationPlaceholder = new StackPane();
        illustrationPlaceholder.setPrefSize(400, 250);

        hero.add(heroTextContent, 0, 0);
        hero.add(illustrationPlaceholder, 1, 0);

        VBox whatWeOfferSection = new VBox(30);
        whatWeOfferSection.setPadding(new Insets(50, 80, 50, 80));
        whatWeOfferSection.setAlignment(Pos.CENTER);
        whatWeOfferSection.setStyle("-fx-background-color: #f8f8f8;");

        Label offerTitle = new Label("What We Offer");
        offerTitle.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        offerTitle.setTextFill(Color.web("#333333"));

        HBox offerCards = new HBox(30);
        offerCards.setAlignment(Pos.CENTER);
        offerCards.getChildren().addAll(
                createOfferCard("🔍", "Client Matching", "Connect with clients seeking your specific legal expertise."),
                createOfferCard("🧑‍⚖", "Professional Profile Management", "Maintain and update your detailed lawyer profile for visibility."),
                createOfferCard("📰", "Latest Legal News", "Stay informed with curated news from legal journals and Bar & Bench."),
                createOfferCard("🏛", "Case Management Tools", "Organize and track your case history and client interactions.")
        );
        whatWeOfferSection.getChildren().addAll(offerTitle, offerCards);

        legalNewsSection = new VBox(30); // Assign to class member
        legalNewsSection.setPadding(new Insets(50, 80, 50, 80));
        legalNewsSection.setAlignment(Pos.CENTER);
        legalNewsSection.setStyle("-fx-background-color: #f8f8f8;");

        Label newsTitle = new Label("Latest Legal News");
        newsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        newsTitle.setTextFill(Color.web("#333333"));

        newsLoadingIndicator = new ProgressBar();
        newsLoadingIndicator.setPrefWidth(300);
        newsLoadingIndicator.setVisible(false);

        newsCardsContainer = new StackPane();
        newsCardsContainer.setAlignment(Pos.CENTER);
        newsCardsContainer.setPrefHeight(250);
        newsCardsContainer.setPrefWidth(900);

        legalNewsSection.getChildren().addAll(newsTitle, newsLoadingIndicator, newsCardsContainer);

        VBox footer = new VBox(10);
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(40, 30, 40, 30)); // Increased padding for larger footer
        footer.setStyle("-fx-background-color: #001f4d;"); // Dark blue background, same as navbar

        Label footerText = new Label("© 2025 Bridge To Law. All rights reserved.");
        footerText.setFont(Font.font("Arial", 12));
        footerText.setTextFill(Color.WHITE);

        Label contactInfo = new Label("Contact: info@bridgetolaw.com | Phone: +91 12345 67890");
        contactInfo.setFont(Font.font("Arial", 12));
        contactInfo.setTextFill(Color.WHITE.deriveColor(0, 1, 1, 0.7)); // Slightly transparent white

        footer.getChildren().addAll(footerText, contactInfo);

        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(navBar);

        VBox content = new VBox();
        content.getChildren().addAll(hero, whatWeOfferSection, legalNewsSection, footer); // Footer added to scrollable content

        ImageView backgroundImage = new ImageView(new Image(BACKGROUND_IMAGE_URL));
        backgroundImage.setFitWidth(1200); // Fit to scene width
        backgroundImage.setPreserveRatio(true);
        backgroundImage.setOpacity(10); // Adjusted opacity for subtle visibility (changed from 10 which is too high)
        backgroundImage.setManaged(false); // Do not let layout manager manage its size/position

        StackPane mainContentStack = new StackPane();
        mainContentStack.getChildren().addAll(backgroundImage, content);
        StackPane.setAlignment(backgroundImage, Pos.TOP_CENTER); // Align background image to top
        backgroundImage.fitWidthProperty().bind(mainContentStack.widthProperty()); // Make background image responsive

        mainScrollPane = new ScrollPane(mainContentStack); // ScrollPane now wraps the StackPane
        mainScrollPane.setFitToWidth(true);
        mainScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        mainScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mainScrollPane.setStyle("-fx-background-color: transparent;"); // Ensure scroll pane background is transparent

        mainLayout.setCenter(mainScrollPane);

        primaryStage.setScene(new Scene(mainLayout, 1550, 890));
        primaryStage.show();

        fetchAndDisplayNews();
    }

    private Button createNavLink(String text) {
        Button link = new Button(text);
        link.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: normal;");
        link.setOnMouseEntered(e -> link.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 5;"));
        link.setOnMouseExited(e -> link.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: normal;"));
        // Add action for navigation links if needed (already handled in start for specific buttons)
        // For generic links, you might add a System.out.println or a placeholder action
        return link;
    }

    private VBox createOfferCard(String icon, String title, String description) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.TOP_CENTER);
        card.setPadding(new Insets(20));
        card.setPrefWidth(220);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 0);");

        Label iconLabel = new Label(icon);
        iconLabel.setFont(Font.font("Segoe UI Emoji", FontWeight.BOLD, 36));
        iconLabel.setTextFill(Color.web("#001f4d"));

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        titleLabel.setTextFill(Color.web("#333333"));
        titleLabel.setTextAlignment(TextAlignment.CENTER);
        titleLabel.setWrapText(true);

        Label descLabel = new Label(description);
        descLabel.setFont(Font.font("Arial", 12));
        descLabel.setTextFill(Color.web("#666666"));
        descLabel.setTextAlignment(TextAlignment.CENTER);
        descLabel.setWrapText(true);

        card.getChildren().addAll(iconLabel, titleLabel, descLabel);
        return card;
    }

    // Helper method to create news cards from NewsArticle object
    private VBox createNewsCard(NewsArticle article) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setPrefWidth(280);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 0);");

        Rectangle thumbnailRect = new Rectangle(250, 150);
        thumbnailRect.setFill(Color.web("#e0e0e0"));
        
        StackPane thumbContainer = new StackPane();
        thumbContainer.setPrefSize(250, 150);
        thumbContainer.setAlignment(Pos.CENTER);

        if (article.imageUrl() != null && !article.imageUrl().isEmpty()) {
            try {
                ImageView newsImage = new ImageView(new Image(article.imageUrl(), true));
                newsImage.setFitWidth(250);
                newsImage.setFitHeight(150);
                newsImage.setPreserveRatio(false);
                thumbContainer.getChildren().add(newsImage);
            } catch (Exception e) {
                System.err.println("Failed to load news image: " + article.imageUrl() + " - " + e.getMessage());
                Label thumbText = new Label("Image Load Error");
                thumbText.setFont(Font.font("Arial", 12));
                thumbText.setTextFill(Color.web("#666666"));
                thumbContainer.getChildren().addAll(thumbnailRect, thumbText);
            }
        } else {
            Label thumbText = new Label("No Image Available");
            thumbText.setFont(Font.font("Arial", 12));
            thumbText.setTextFill(Color.web("#666666"));
            thumbContainer.getChildren().addAll(thumbnailRect, thumbText);
        }
        
        card.getChildren().add(thumbContainer);

        Label titleLabel = new Label(article.title());
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        titleLabel.setTextFill(Color.web("#333333"));
        titleLabel.setWrapText(true);

        Label descLabel = new Label(article.description());
        descLabel.setFont(Font.font("Arial", 12));
        descLabel.setTextFill(Color.web("#666666"));
        descLabel.setWrapText(true);

        Hyperlink readMoreLink = new Hyperlink("Read More");
        readMoreLink.setFont(Font.font("Arial", 12));
        readMoreLink.setTextFill(Color.web("#001f4d"));
        readMoreLink.setOnAction(e -> {
            try {
                if (article.articleUrl() != null && !article.articleUrl().isEmpty()) {
                    java.awt.Desktop.getDesktop().browse(new java.net.URI(article.articleUrl()));
                } else {
                    System.out.println("No article URL available for: " + article.title());
                }
            } catch (Exception ex) {
                System.err.println("Failed to open URL: " + ex.getMessage());
            }
        });

        card.getChildren().addAll(titleLabel, descLabel, readMoreLink);
        return card;
    }

    private void fetchAndDisplayNews() {
        newsLoadingIndicator.setVisible(true);
        newsCardsContainer.getChildren().clear();

        new Thread(() -> {
            List<NewsArticle> allNews = fetchNewsArticles();
            
            paginatedNews.clear();
            for (int i = 0; i < allNews.size(); i += 3) {
                int endIndex = Math.min(i + 3, allNews.size());
                paginatedNews.add(allNews.subList(i, endIndex));
            }

            javafx.application.Platform.runLater(() -> {
                newsLoadingIndicator.setVisible(false);
                if (paginatedNews.isEmpty()) {
                    Label noNewsLabel = new Label("No news available at the moment.");
                    noNewsLabel.setFont(Font.font("Arial", 16));
                    noNewsLabel.setTextFill(Color.GRAY);
                    newsCardsContainer.getChildren().add(noNewsLabel);
                } else {
                    currentPageIndex.set(0);
                    displayCurrentNewsPage();
                    startNewsCycleTimer();
                }
            });
        }).start();
    }

    // --- Method to Fetch News Articles (Uses comprehensive mock data) ---
    private List<NewsArticle> fetchNewsArticles() {
        // Simulate network delay for demonstration
        try {
            Thread.sleep(1000); // Simulate 1.5 seconds loading time
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        List<NewsArticle> allNews = new ArrayList<>();
        // --- EXPANDED MOCK DATA ---
        allNews.add(new NewsArticle(
            "The Supreme Court of India delivered a significant verdict today",
            "The Supreme Court of India delivered a significant verdict today, reinforcing the fundamental right to digital privacy for citizens, setting new precedents for data protection.",
            "Assets/Images/news1.jpg",
            "https://www.brennancenter.org/our-work/research-reports/landmark-supreme-court-cases"
        ));
        allNews.add(new NewsArticle(
            "Elephant Madhuri to return from Vantara to Kolhapur if court allows: Maharashtra CM Devendra Fadnavis shares ‘good news’",
            "Anant Ambani’s Vantara has agreed to support Madhuri’s return to Nandani Math. The animal rehab will work with the Maharashtra government to return the elephant, also called Mahadevi.",
            "Assets/Images/Madhuri-ele.png",
            "https://www.livemint.com/news/india/elephant-madhuri-mahadevi-to-return-from-vantara-to-kolhapur-if-court-allows-maharashtra-cm-devendra-fadnavis-good-news-11754474285251.html"
        ));
        allNews.add(new NewsArticle(
            "A recent High Court judgment provides clearer guidelines for equitable distribution of marital property, impacting future divorce settlements nationwide.",
            "A recent High Court judgment provides clearer guidelines for equitable distribution of marital property, impacting future divorce settlements nationwide.",
            "Assets/Images/news3.jpg",
            "https://www.indialaw.in/blog/family-law/8-critical-guidelines-to-ensure-fair-settlements-in-divorce-cases/#:~:text=This%20case%20serves%20as%20a,legal%20framework%20for%20family%20disputes."
        ));
        allNews.add(new NewsArticle(
            "Kanwar Yatra: Supreme Court says customers have right to know if restaurant was earlier serving non-veg",
            "Kanwar Yatra: Supreme Court says customers have right to know if restaurant was earlier serving non-veg",
            "Assets/Images/yatra img.png",
            "https://www.barandbench.com/news/kanwar-yatra-supreme-court-says-customers-have-right-to-know-if-restaurant-was-earlier-serving-non-veg"
        ));
        allNews.add(new NewsArticle(
            "Aadhaar, Voter ID & Ration Cards Not Reliable Documents' : ECI Tells Supreme Court In Bihar Electoral Roll Revision Matter",
            "Aadhaar, Voter ID & Ration Cards Not Reliable Documents' : ECI Tells Supreme Court In Bihar Electoral Roll Revision Matter",
            "Assets/Images/Aadhar.png",
            "https://www.livelaw.in/top-stories/aadhaar-voter-id-ration-cards-not-reliable-documents-eci-tells-supreme-court-in-bihar-electoral-roll-revision-matter-298392"
        ));
        allNews.add(new NewsArticle(
            "Jagdeep Dhankhar collapsed at Delhi event days before shock resignation: Sources",
            "Jagdeep Dhankhar collapsed at Delhi event days before shock resignation: Sources",
            "Assets/Images/minister.png",
            "https://www.indiatoday.in/india/story/jagdeep-dhankhar-collapsed-during-an-event-in-delhi-rajya-sabha-parliament-vice-president-resignation-lok-sabha-monsoon-2759496-2025-07-22"
        ));
        allNews.add(new NewsArticle(
            "Andhra ex-CM Jagan got kickbacks in Rs 3,500-crore liquor ‘scam’: CID probe",
            "Andhra ex-CM Jagan got kickbacks in Rs 3,500-crore liquor ‘scam’: CID probe",
            "Assets/Images/Jaganath.png",
            "https://indianexpress.com/article/india/ys-jagan-mohan-reddy-kickback-beneficiary-3500-crore-liquor-scam-chargesheet-10138557/"
        ));
        allNews.add(new NewsArticle(
            "Air India says completed inspection of fuel switch locking system on Boeing fleet, no issues found",
            "Air India says completed inspection of fuel switch locking system on Boeing fleet, no issues found",
            "Assets/Images/air india.png",
            "https://www.thehindu.com/news/national/air-india-says-completed-inspection-of-fuel-switch-locking-system-on-boeing-fleet-no-issues-found/article69841914.ece"
        ));
        allNews.add(new NewsArticle(
            "Bangladesh Air Force jet crash: What to know about the crash into a Dhaka school.",
            "Bangladesh Air Force jet crash: What to know about the crash into a Dhaka school.",
            "Assets/Images/Bangladesh.png",
            "https://www.thehindu.com/news/international/bangladesh-air-force-jet-crash-what-to-know-about-the-crash-into-a-dhaka-school/article69841265.ece"
        ));

        return allNews;
    }

    // --- Method to display the current news page ---
    private void displayCurrentNewsPage() {
        newsCardsContainer.getChildren().clear();
        if (paginatedNews.isEmpty()) {
            return;
        }

        List<NewsArticle> currentPageArticles = paginatedNews.get(currentPageIndex.get());
        HBox currentNewsHBox = new HBox(30);
        currentNewsHBox.setAlignment(Pos.CENTER);

        for (NewsArticle article : currentPageArticles) {
            currentNewsHBox.getChildren().add(createNewsCard(article));
        }
        newsCardsContainer.getChildren().add(currentNewsHBox);
    }
    private void startNewsCycleTimer() {
        if (newsCycleTimeline != null) {
            newsCycleTimeline.stop();
        }

        newsCycleTimeline = new Timeline(
            new KeyFrame(Duration.seconds(4), event -> {
                int nextIndex = (currentPageIndex.get() + 1) % paginatedNews.size();
                currentPageIndex.set(nextIndex);
                displayCurrentNewsPage();
            })
        );
        newsCycleTimeline.setCycleCount(Timeline.INDEFINITE);
        newsCycleTimeline.play();
    }
    private void showAlert(String title, String header, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}