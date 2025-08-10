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
import javafx.stage.Stage;

public class lawyer2 extends Application {
    @Override
    public void start(Stage primaryStage){

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F7F9FC;");

        // Lawyer Profile Section
        VBox profileBox = new VBox(10);
        profileBox.setPadding(new Insets(20));
        profileBox.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-width: 1;");
        profileBox.setPrefWidth(600);

        // Load and show image properly
        ImageView imageView = new ImageView();
        try {
            Image profileImage = new Image("Assets/Images/lawyer2.png");
            imageView.setImage(profileImage);    
            imageView.setFitWidth(80);                
            imageView.setFitHeight(90);     
        } catch (Exception e) {
            System.out.println("Failed to load image: " + e.getMessage());
        }

        Text name = new Text("Adv. Sunita Bafna");
        name.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Text rating = new Text("★ 4.7 (Top Family Lawyer) | 150+ users ratings");
        rating.setFill(Color.web("#FFD700"));
        rating.setFont(Font.font("Arial", 14));

        Text exp = new Text("12 Years Experience");
        Text loc = new Text("📍 Mumbai Central, Mumbai| Criminal Law, Cyber Crime, Family");

        VBox bioBox = new VBox(
            new Text("Biography"),
            new Text("Advocate Sunita Bafna has been practicing and handling cases independently with a result oriented approach,\r\nboth professionally and ethically and has now acquired many years of professional experience in providing\r\nlegal consultancy and advisory services.\r\n")
        );
        bioBox.setSpacing(5);

        VBox specBox = new VBox(
            new Text("Specialisations"),
            new Text("✓ Criminal Defense, ✓ Cyber Crime, ✓ Family")
        );
        specBox.setSpacing(5);

        VBox courtBox = new VBox(
            new Text("Courts of Practice"),
            new Text("✓ Bombay High Court, ✓ Magistrate Court, ✓ Sessions Court")
        );
        courtBox.setSpacing(5);

        VBox langBox = new VBox(
            new Text("Languages"),
            new Text("✓ English ✓ Hindi ✓ Marathi ✓ Kannada ✓ Gujarati ")
        );

        VBox eduBox = new VBox(
            new Text("Education & Bar Admission"),
            new Text("✔️ LL.B from Government Law College, Mumbai (2012)")
        );

        profileBox.getChildren().addAll(imageView, name, rating, exp, loc, new Separator(),
            bioBox, specBox, courtBox, langBox, eduBox);

        // Right VBox for Booking (currently not removed)
        VBox bookingBox = new VBox(10);
        bookingBox.setPadding(new Insets(20));
        bookingBox.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-width: 1;");
        bookingBox.setAlignment(Pos.TOP_CENTER);

        Label bookTitle = new Label("Book Consultation");
        bookTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        TextField fullName = new TextField();
        fullName.setPromptText("Enter your full name");

        TextField email = new TextField();
        email.setPromptText("Enter your email");

        TextField phone = new TextField();
        phone.setPromptText("Phone number");

        DatePicker datePicker = new DatePicker();

        ComboBox<String> timeDropdown = new ComboBox<>();
        timeDropdown.getItems().addAll("10 AM", "12 PM", "3 PM");

        ComboBox<String> caseType = new ComboBox<>();
        caseType.getItems().addAll("Civil", "Criminal", "Property", "Cyber", "Divorce", "Dowry");

        TextArea desc = new TextArea();
        desc.setPromptText("Briefly describe your case");
        desc.setPrefRowCount(3);

        Label cost = new Label("Consultation Fee: ₹1,100");
        Button bookBtn = new Button("Book Consultation");

        bookBtn.setStyle("-fx-background-color: #000000ff; -fx-text-fill: white; -fx-font-weight: bold;");

        bookingBox.getChildren().addAll(bookTitle, fullName, email, phone, datePicker,
                timeDropdown, caseType, desc, cost, bookBtn);

        HBox mainContent = new HBox(20, profileBox, bookingBox);
        mainContent.setPadding(new Insets(40));
        mainContent.setAlignment(Pos.TOP_CENTER);

        root.setCenter(mainContent);

        HBox profileHeaderBox = new HBox(10);
        profileHeaderBox.setPadding(new Insets(10));
        profileHeaderBox.setAlignment(Pos.CENTER_LEFT);
        root.setTop(profileHeaderBox);

        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Lawyer Profile - Bridge To Law");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}






