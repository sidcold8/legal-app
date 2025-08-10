package com.btl.View;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class lawyer4 extends Application {
    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F7F9FC;");

        VBox profileBox = new VBox(10);
        profileBox.setPadding(new Insets(20));
        profileBox.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0;");
        profileBox.setPrefWidth(600);

        ImageView imageView = new ImageView(new Image("Assets/Images/lawyer4.jpg"));
        imageView.setFitWidth(90);
        imageView.setFitHeight(80);

        Text name = new Text("Adv. Rajesh Rai");
        name.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Text rating = new Text("★ 4.8 (Criminal Law Specialist)");
        rating.setFill(Color.web("#FFD700"));
        rating.setFont(Font.font("Arial", 14));

        VBox bioBox = new VBox(
                new Text("Biography"),
                new Text("Adv. Rajesh Rai is a highly experienced criminal lawyer with a track record of high-profile acquittals in Mumbai.")
        );

        VBox specBox = new VBox(
                new Text("Specialisations"),
                new Text("✓ Criminal Trials, ✓ Narcotics, ✓ Economic Offenses")
        );

        VBox courtBox = new VBox(
                new Text("Courts of Practice"),
                new Text("✓ Bombay High Court, ✓ Sessions Court")
        );

        VBox eduBox = new VBox(
                new Text("Education & Bar Admission"),
                new Text("✔️ LL.B from National Law School, Bengaluru (2012)")
        );

        profileBox.getChildren().addAll(imageView, name, rating, new Separator(), bioBox, specBox, courtBox, eduBox);

        VBox bookingBox = new VBox(10);
        bookingBox.setPadding(new Insets(20));
        bookingBox.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0;");
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
        caseType.getItems().addAll("Criminal", "Property", "Civil");

        TextArea desc = new TextArea();
        desc.setPromptText("Briefly describe your case");

        Label cost = new Label("Consultation Fee: ₹3,000");

        Button bookBtn = new Button("Book Consultation");
        bookBtn.setStyle("-fx-background-color: #000000ff; -fx-text-fill: white; -fx-font-weight: bold;");

        bookingBox.getChildren().addAll(bookTitle, fullName, email, phone, datePicker, timeDropdown, caseType, desc, cost, bookBtn);

        HBox mainContent = new HBox(20, profileBox, bookingBox);
        mainContent.setPadding(new Insets(40));
        mainContent.setAlignment(Pos.TOP_CENTER);

        root.setCenter(mainContent);
        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        stage.setTitle("Adv. Rajesh Rai - Profile");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
