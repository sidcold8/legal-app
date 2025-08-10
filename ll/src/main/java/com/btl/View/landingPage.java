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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration; // For Timeline

import java.awt.Desktop; // Import for opening URLs
import java.net.URI;     // Import for URI

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.btl.Controller.Firebaseinitialize;
import com.google.cloud.firestore.Firestore;



public class landingPage extends Application {

    Stage myStage;
    private List<List<NewsArticle>> paginatedNews = new ArrayList<>();
    private AtomicInteger currentPageIndex = new AtomicInteger(0);
    private StackPane newsCardsContainer;
    private ProgressBar newsLoadingIndicator;
    private Timeline newsCycleTimeline;

    // References for scrolling to sections
    private ScrollPane mainScrollPane;
    private VBox searchSectionRef; // Reference to the "Find a Lawyer Instantly" section
    private VBox newsSectionRef; // Reference to the "Latest Legal News" section
    private VBox footerSectionRef; // Reference to the "Contact Us" (footer) section

    // --- NewsArticle Data Model (Simple Record) ---
    private record NewsArticle(String title, String description, String imageUrl, String articleUrl) {}

    // Background image path for Hero Section
    private static final String BACKGROUND_IMAGE_PATH = "Assets/Images/background img.jpg"; // Relative path to resources

    @Override
    public void start(Stage primaryStage) {
        myStage = primaryStage;
        primaryStage.setTitle("Bridge To Law");

        // --- TOP BLUE NAVIGATION PANEL (Re-integrated and styled) ---
        HBox navBar = new HBox(30); // Increased spacing
        navBar.setAlignment(Pos.CENTER_LEFT);
        navBar.setPadding(new Insets(20, 50, 20, 50)); // Increased padding
        navBar.setStyle("-fx-background-color: #0d1b3f;"); // Deep blue bar

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

        HBox menu = new HBox(40); // Increased spacing
        menu.setAlignment(Pos.CENTER_RIGHT);
        menu.setPadding(new Insets(0, 0, 0, 100));

        // Navigation Buttons
        Button homeBtn = createNavButton("Home");
        homeBtn.setOnAction(e -> mainScrollPane.setVvalue(0)); // Scroll to top

        Button findLawyerNavBtn = createNavButton("Find a Lawyer");
        findLawyerNavBtn.setOnAction(e -> {
            if (mainScrollPane != null && searchSectionRef != null) {
                double targetY = searchSectionRef.getBoundsInParent().getMinY();
                mainScrollPane.setVvalue(targetY / (mainScrollPane.getContent().getBoundsInLocal().getHeight() - mainScrollPane.getViewportBounds().getHeight()));
            }
        });

        Button legalNewsNavBtn = createNavButton("Legal News");
        legalNewsNavBtn.setOnAction(e -> {
            if (mainScrollPane != null && newsSectionRef != null) {
                double targetY = newsSectionRef.getBoundsInParent().getMinY();
                mainScrollPane.setVvalue(targetY / (mainScrollPane.getContent().getBoundsInLocal().getHeight() - mainScrollPane.getViewportBounds().getHeight()));
            }
        });

        Button aboutUsNavBtn = createNavButton("About Us");
        aboutUsNavBtn.setOnAction(e -> {
            try {
                new aboutus().start(primaryStage);
            } catch (Exception ex) {
                showAlert("Navigation Error", "Failed to load About Us Page.", "The 'aboutus' class could not be loaded or started. Check console for details.", Alert.AlertType.ERROR);
                ex.printStackTrace();
            }
        });

        Button contactUsNavBtn = createNavButton("Contact Us");
        contactUsNavBtn.setOnAction(e -> {
            if (mainScrollPane != null && footerSectionRef != null) {
                double targetY = footerSectionRef.getBoundsInParent().getMinY();
                mainScrollPane.setVvalue(targetY / (mainScrollPane.getContent().getBoundsInLocal().getHeight() - mainScrollPane.getViewportBounds().getHeight()));
            }
        });

        // --- NEW: Logout Button ---
        Button logoutBtn = createNavButton("Logout");
        logoutBtn.setOnAction(e -> {
            try {
                // Assuming your loginPage class is named 'loginPage' and has a start method
                // You might want to close the current stage before opening the new one
                primaryStage.close();
                new loginPage().start(primaryStage); 
            } catch (Exception ex) {
                showAlert("Navigation Error", "Failed to load Login Page.", "The 'loginPage' class could not be loaded or started. Check console for details.", Alert.AlertType.ERROR);
                ex.printStackTrace();
            }
        });
        // --- END NEW ---

        menu.getChildren().addAll(
                homeBtn,
                findLawyerNavBtn,
                legalNewsNavBtn,
                aboutUsNavBtn,
                contactUsNavBtn,
                logoutBtn // --- ADDED LOGOUT BUTTON ---
        );

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        navBar.getChildren().addAll(logoBox, spacer, menu);

        // --- Hero Section with background ---
        HBox heroSection = new HBox(60); // Increased spacing
        heroSection.setPadding(new Insets(80)); // Increased padding
        heroSection.setAlignment(Pos.CENTER); // Center content within hero section

        VBox heroLeft = new VBox(25); // Increased spacing
        heroLeft.setAlignment(Pos.CENTER_LEFT);

        Label heading = new Label("Connecting Clients to the\nRight Legal Experts Instantly");
        heading.setFont(Font.font("Arial", FontWeight.BOLD, 40)); // Larger font
        heading.setTextFill(Color.WHITE);
        heading.setWrapText(true); // Ensure text wraps

        Label subheading = new Label("Browse verified lawyers by practice area, experience, or location.");
        subheading.setFont(Font.font("Arial", 18)); // Larger font
        subheading.setTextFill(Color.LIGHTGRAY);
        subheading.setWrapText(true); // Ensure text wraps

        HBox heroBtns = new HBox(25); // Increased spacing
        Button findBtn = new Button("🔍 Find a Lawyer");
        findBtn.setStyle("-fx-background-color: #0d1b3f; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12 25; -fx-background-radius: 8;"); // Blue button
        findBtn.setOnMouseEntered(e -> findBtn.setStyle("-fx-background-color: #0d1b3f; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12 25; -fx-background-radius: 8;"));
        findBtn.setOnMouseExited(e -> findBtn.setStyle("-fx-background-color: #0d1b3f; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12 25; -fx-background-radius: 8;"));
        findBtn.setOnAction(e -> {
            if (mainScrollPane != null && searchSectionRef != null) {
                double targetY = searchSectionRef.getBoundsInParent().getMinY();
                mainScrollPane.setVvalue(targetY / (mainScrollPane.getContent().getBoundsInLocal().getHeight() - mainScrollPane.getViewportBounds().getHeight()));
            }
        });
        heroBtns.getChildren().addAll(findBtn);

        heroLeft.getChildren().addAll(heading, subheading, heroBtns);

        // Illustration Placeholder
        StackPane illustration = new StackPane();
        illustration.setPrefSize(450, 300); // Larger size
        illustration.setStyle("-fx-background-color: rgba(255,255,255,0.8); -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 0);"); // Semi-transparent white with shadow

        heroSection.getChildren().addAll(heroLeft); // Add illustration back

        StackPane heroContainer = new StackPane();
        ImageView bgImageView = new ImageView();
        try {
            Image bgImage = new Image(BACKGROUND_IMAGE_PATH); // Load image from resources
            bgImageView.setImage(bgImage);
            bgImageView.setPreserveRatio(false); // Stretch to fill
            bgImageView.fitWidthProperty().bind(heroContainer.widthProperty());
            bgImageView.fitHeightProperty().bind(heroContainer.heightProperty());
            bgImageView.setOpacity(10); // Adjust opacity for better text readability
            heroContainer.getChildren().add(bgImageView); // Layer image behind content
        } catch (Exception e) {
            System.err.println("Failed to load background image: " + BACKGROUND_IMAGE_PATH + " - " + e.getMessage());
            // Fallback to a solid color if image fails to load
            heroContainer.setStyle("-fx-background-color: #001f4d;"); // Dark blue fallback
            Label imageErrorLabel = new Label("Background Image Missing/Error");
            imageErrorLabel.setTextFill(Color.RED);
            imageErrorLabel.setFont(Font.font("Arial", 16));
            heroContainer.getChildren().add(imageErrorLabel);
            StackPane.setAlignment(imageErrorLabel, Pos.CENTER);
        }
        heroContainer.getChildren().add(heroSection); // Layer hero content on top

        VBox offers = new VBox(40); // Increased spacing
        offers.setPadding(new Insets(60)); // Increased padding
        offers.setAlignment(Pos.CENTER);
        offers.setStyle("-fx-background-color: #f8f8f8;"); // Light background for contrast

        Label offersHeading = new Label("What We Offer");
        offersHeading.setFont(Font.font("Arial", FontWeight.BOLD, 32)); // Larger font
        offersHeading.setTextFill(Color.web("#333333"));
        HBox.setMargin(offersHeading, new Insets(0,0,20,0)); // Margin below heading

        HBox cards = new HBox(30); // Increased spacing
        cards.setAlignment(Pos.CENTER);
        cards.getChildren().addAll(
                createOfferCard("🔎", "Find a Lawyer", "Search and filter by city, specialization, rating, and more."),
                createOfferCard("👨‍⚖", "Lawyer & Client Profiles", "Create and browse detailed professional or client accounts."),
                createOfferCard("📰", "Latest Legal News", "Stay informed with curated news from legal journals and Bar & Bench."),
                createOfferCard("🏛", "Area of Practice", "Explore by legal domains - Civil, Criminal, Corporate, and more.")
        );
        offers.getChildren().addAll(offersHeading, cards);

        // --- Search Section ---
        searchSectionRef = new VBox(30); // Reference for scrolling, increased spacing
        searchSectionRef.setAlignment(Pos.CENTER);
        searchSectionRef.setPadding(new Insets(60)); // Increased padding
        searchSectionRef.setStyle("-fx-background-color: #ffffff;"); // White background

        Label searchLabel = new Label("Find a Lawyer Instantly");
        searchLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32)); // Larger font
        searchLabel.setTextFill(Color.web("#333333"));

        GridPane filters = new GridPane(); // Using GridPane for better alignment of filters
        filters.setHgap(20); // Increased gap
        filters.setVgap(20); // Increased gap
        filters.setAlignment(Pos.CENTER);

        ComboBox<String> cityBox = new ComboBox<>();
        cityBox.setPrefWidth(220); // Wider combo boxes
        cityBox.getItems().addAll("Mumbai", "Delhi", "Bengaluru", "Hyderabad", "Chennai", "Kolkata", "Pune", "Ahmedabad", "Jaipur", "Lucknow", "Chandigarh", "Kochi", "Indore", "Bhopal", "Nagpur", "Surat", "Vadodara", "Coimbatore", "Visakhapatnam", "Patna");
        cityBox.setPromptText("Select City");
        cityBox.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 10px;");

        ComboBox<String> areaBox = new ComboBox<>();
        areaBox.setPrefWidth(220);
        areaBox.getItems().addAll(
            "Criminal Law", "Civil Law", "Family Law", "Property Law",
            "Corporate Law", "Intellectual Property Law", "Cyber Law",
            "Consumer Law", "Labour Law", "Constitutional Law",
            "Tax Law", "Environmental Law", "Arbitration & Mediation",
            "Immigration Law", "Human Rights Law", "Banking & Finance Law",
            "Real Estate Law", "Media & Entertainment Law", "Aviation Law",
            "Competition Law", "Education Law", "Energy Law", "Healthcare Law",
            "Insolvency & Bankruptcy Law", "Insurance Law", "Sports Law",
            "Technology Law", "Telecommunications Law", "Trust & Succession Law"
        );
        areaBox.setPromptText("Practice Area");
        areaBox.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 10px;");

        ComboBox<String> languageBox = new ComboBox<>();
        languageBox.setPrefWidth(220);
        languageBox.getItems().addAll("English", "Hindi", "Marathi", "Telugu", "Tamil", "Bengali", "Gujarati", "Kannada", "Malayalam", "Punjabi", "Oriya", "Assamese", "Urdu", "Sanskrit");
        languageBox.setPromptText("Language");
        languageBox.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 10px;");
            
        Button searchNowBtn = new Button("Search Now");
        searchNowBtn.setPrefWidth(220); // Match width of combo boxes
        searchNowBtn.setStyle("-fx-background-color: #001f4d; -fx-text-fill: white; -fx-font-size: 18px; -fx-padding: 12 25; -fx-background-radius: 8;");
        searchNowBtn.setOnMouseEntered(e -> searchNowBtn.setStyle("-fx-background-color: #003366; -fx-text-fill: white; -fx-font-size: 18px; -fx-padding: 12 25; -fx-background-radius: 8;"));
        searchNowBtn.setOnMouseExited(e -> searchNowBtn.setStyle("-fx-background-color: #001f4d; -fx-text-fill: white; -fx-font-size: 18px; -fx-padding: 12 25; -fx-background-radius: 8;"));
        searchNowBtn.setAlignment(Pos.CENTER);

        searchNowBtn.setOnAction(e -> {
            String selectedCity = cityBox.getValue();
            String selectedArea = areaBox.getValue();
            String selectedLang = languageBox.getValue();
                        Firestore db = Firebaseinitialize.db;
                        

            if (selectedCity != null && selectedArea != null && selectedLang != null ) {
                try {
                    new com.btl.View.AdvocateGrid().start(primaryStage, selectedCity, selectedArea, selectedLang);
                } catch (Exception ex) {
                    showAlert("Navigation Error", "Failed to load Advocate Grid.", "The 'AdvocateGrid' class was not found. Please ensure it exists and is correctly named and located.", Alert.AlertType.ERROR);
                    ex.printStackTrace();
                }
            } else {
                showAlert("Missing Selection", "Please select all search fields.", "", Alert.AlertType.WARNING);
            }
        });

        filters.add(cityBox, 0, 0);
        filters.add(areaBox, 1, 0);
        filters.add(languageBox, 2, 0);
        filters.add(searchNowBtn, 1, 1); // Centered under filters

        searchSectionRef.getChildren().addAll(searchLabel, filters);

        newsSectionRef = new VBox(30); // Reference for scrolling, increased spacing
        newsSectionRef.setPadding(new Insets(60)); // Increased padding
        newsSectionRef.setAlignment(Pos.CENTER);
        newsSectionRef.setStyle("-fx-background-color: #f8f8f8;");

        Label newsHeading = new Label("Latest Legal News");
        newsHeading.setFont(Font.font("Arial", FontWeight.BOLD, 32)); // Larger font
        newsHeading.setTextFill(Color.web("#333333"));

        newsLoadingIndicator = new ProgressBar();
        newsLoadingIndicator.setPrefWidth(300);
        newsLoadingIndicator.setVisible(false); // Hide initially

        newsCardsContainer = new StackPane(); // Container for dynamic news cards
        newsCardsContainer.setAlignment(Pos.CENTER);
        newsCardsContainer.setPrefHeight(300); // Increased height for news cards
        newsCardsContainer.setPrefWidth(1000); // Wider container

        newsSectionRef.getChildren().addAll(newsHeading, newsLoadingIndicator, newsCardsContainer);

        footerSectionRef = new VBox(20); // Reference for scrolling, increased spacing
        footerSectionRef.setPadding(new Insets(50)); // Increased padding
        footerSectionRef.setAlignment(Pos.CENTER);
        footerSectionRef.setStyle("-fx-background-color: #0d1b3f;");
        Label contactHeading = new Label("Get In Touch With Us");
        contactHeading.setFont(Font.font("Arial", FontWeight.BOLD, 28)); // Larger font
        contactHeading.setTextFill(Color.WHITE);

        Label contactInfo = new Label(
                "📧 Email: support@bridgetolaw.com\n📞 Phone: +91 98765 43210");
        contactInfo.setFont(Font.font("Arial", 16)); // Larger font
        contactInfo.setTextFill(Color.LIGHTGRAY);
        contactInfo.setTextAlignment(TextAlignment.CENTER); // Center align text

        footerSectionRef.getChildren().addAll(contactHeading, contactInfo);

        VBox content = new VBox(60); // Increased spacing between major sections
        content.getChildren().addAll(heroContainer, offers, searchSectionRef, newsSectionRef, footerSectionRef);

        mainScrollPane = new ScrollPane(content);
        mainScrollPane.setFitToWidth(true);
        mainScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        mainScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // No horizontal scrollbar

        VBox root = new VBox();
        root.getChildren().addAll(navBar, mainScrollPane); // navBar at top, mainScrollPane below

        Scene scene = new Scene(root, 1550, 890); // Adjusted scene width
        primaryStage.setScene(scene);
        primaryStage.show();

        fetchAndDisplayNews();
    }

    private Button createNavButton(String title) {
        Button btn = new Button(title);
        btn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold; -fx-padding: 8 15;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold; -fx-padding: 8 15; -fx-background-radius: 5;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold; -fx-padding: 8 15;"));
        return btn;
    }

    private VBox createOfferCard(String icon, String title, String desc) {
        VBox box = new VBox(10); // Increased spacing
        box.setAlignment(Pos.TOP_CENTER); // Align icon and text to top center
        box.setPrefWidth(250); // Wider cards
        box.setPrefHeight(200); // Taller cards
        box.setPadding(new Insets(25)); // Increased padding
        box.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 15, 0, 0, 0);"); // Softer, larger shadow

        Label iconLabel = new Label(icon);
        iconLabel.setFont(Font.font("Segoe UI Emoji", FontWeight.BOLD, 48)); // Larger icon
        iconLabel.setTextFill(Color.web("#0d1b3f")); // Dark blue icon

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18)); // Larger, bolder title
        titleLabel.setTextFill(Color.web("#333333"));
        titleLabel.setTextAlignment(TextAlignment.CENTER); // Center align title
        titleLabel.setWrapText(true);

        Label descLabel = new Label(desc);
        descLabel.setFont(Font.font("Arial", 14)); // Larger description
        descLabel.setTextAlignment(TextAlignment.CENTER);
        descLabel.setWrapText(true);
        descLabel.setTextFill(Color.web("#666666"));

        box.getChildren().addAll(iconLabel, titleLabel, descLabel);
        return box;
    }

    private VBox createNewsCard(NewsArticle article) {
        VBox card = new VBox(10); // Spacing
        card.setAlignment(Pos.TOP_LEFT);
        card.setPadding(new Insets(20)); // Increased padding
        card.setPrefWidth(300); // Wider cards
        card.setPrefHeight(250); // Taller cards
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 0);");

        ImageView newsImage = new ImageView();
        if (article.imageUrl() != null && !article.imageUrl().isEmpty()) {
            try {
                Image img = new Image(article.imageUrl(), true); // Load in background
                newsImage.setImage(img);
                newsImage.setFitWidth(280); // Fit within card padding
                newsImage.setFitHeight(150);
                newsImage.setPreserveRatio(false);
            } catch (Exception e) {
                System.err.println("Failed to load news image: " + article.imageUrl() + " - " + e.getMessage());
                // Fallback to a placeholder if image fails
                newsImage.setImage(new Image("Assets/Images/supreme-court-4.jpg"));
                newsImage.setFitWidth(280);
                newsImage.setFitHeight(150);
            }
        } else {
            // Fallback for no image URL provided
            newsImage.setImage(new Image("https://placehold.co/280x150/e0e0e0/666666?text=No+Image"));
            newsImage.setFitWidth(280);
            newsImage.setFitHeight(150);
        }

        Label titleLabel = new Label(article.title());
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16)); // Larger, bolder title
        titleLabel.setTextFill(Color.web("#333333"));
        titleLabel.setWrapText(true);

        Label descLabel = new Label(article.description());
        descLabel.setFont(Font.font("Arial", 13)); // Larger description
        descLabel.setWrapText(true);
        descLabel.setTextFill(Color.web("#666666"));

        Hyperlink readMore = new Hyperlink("Read More");
        readMore.setFont(Font.font("Arial", 13));
        readMore.setTextFill(Color.web("#001f4d"));
        readMore.setOnAction(e -> {
            try {
                if (article.articleUrl() != null && !article.articleUrl().isEmpty()) {
                    Desktop.getDesktop().browse(new URI(article.articleUrl()));
                } else {
                    System.out.println("No article URL available for: " + article.title());
                }
            } catch (Exception ex) {
                System.err.println("Failed to open URL: " + ex.getMessage());
            }
        });

        card.getChildren().addAll(newsImage, titleLabel, descLabel, readMore);
        return card;
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
            "France's top diplomat calls for foreign press access to Gaza",
            "France's top diplomat calls for foreign press access to Gaza",
            "Assets/Images/france.png",
            "https://www.thehindu.com/news/international/frances-top-diplomat-calls-for-foreign-press-access-to-gaza/article69842346.ece"
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

        try {
            Thread.sleep(500); // Simulate 1.5 seconds loading time (reduced from 1000ms in previous response)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return allNews;
    }

    // --- Method to fetch and display news ---
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

    // --- Method to start the news cycling timer ---
    private void startNewsCycleTimer() {
        if (newsCycleTimeline != null) {
            newsCycleTimeline.stop();
        }

        newsCycleTimeline = new Timeline(
            new KeyFrame(Duration.seconds(4), event -> { // Cycle every 4 seconds
                int nextIndex = (currentPageIndex.get() + 1) % paginatedNews.size();
                currentPageIndex.set(nextIndex);
                displayCurrentNewsPage();
            })
        );
        newsCycleTimeline.setCycleCount(Timeline.INDEFINITE);
        newsCycleTimeline.play();
    }

    // Helper method to show alerts
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