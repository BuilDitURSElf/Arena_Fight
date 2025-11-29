package view;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen; // Import for screen size
import javafx.stage.Stage;
import javafx.util.Duration;

public class MenuScene {

    private Stage stage;

    public MenuScene(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        // 1. Fullscreen Setup (Get monitor size)
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double width = screenBounds.getWidth();
        double height = screenBounds.getHeight();

        
        Image bgImage = new Image(getClass().getResourceAsStream("/imagesandstyles/backgroundmenu.jpg"));
        ImageView bgView = new ImageView(bgImage);
        
        
        bgView.fitWidthProperty().bind(stage.widthProperty());
        bgView.fitHeightProperty().bind(stage.heightProperty());

        // 3. Content Layout
        VBox contentBox = new VBox(20);
        contentBox.setAlignment(Pos.CENTER);

        // --- ELEMENTS ---
        // Note: We removed setStyle(...) because the CSS file handles it now!
        
        Button enterButton = new Button("⚔ ENTER ARENA ⚔");
        // We can add specific classes if needed, but CSS targets all .button by default

        // Player 1
        Label p1Label = new Label("Player 1 Character");
        p1Label.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, black, 2, 1, 0, 0);");
        
        ComboBox<String> p1Select = new ComboBox<>();
        p1Select.getItems().addAll("Warrior", "Mage", "Sniper", "Commando");
        p1Select.getSelectionModel().selectFirst();

        // Player 2
        Label p2Label = new Label("Player 2 Character");
        p2Label.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, black, 2, 1, 0, 0);");

        ComboBox<String> p2Select = new ComboBox<>();
        p2Select.getItems().addAll("Warrior", "Mage", "Sniper", "Commando");
        p2Select.getSelectionModel().selectFirst();

        Button fightButton = new Button("FIGHT!");

        // 4. Overlay & Animation (Same as before)
        Rectangle overlay = new Rectangle(width, height, Color.BLACK);
        overlay.setOpacity(0);
        overlay.setMouseTransparent(true);
        // Bind overlay size to stage too, just in case
        overlay.widthProperty().bind(stage.widthProperty());
        overlay.heightProperty().bind(stage.heightProperty());

        // Animation Logic
        enterButton.setOnAction(e -> {
            overlay.setMouseTransparent(false);
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.8), overlay);
            fadeOut.setFromValue(0); 
            fadeOut.setToValue(1);

            fadeOut.setOnFinished(event -> {
                contentBox.getChildren().clear();
                contentBox.getChildren().addAll(p1Label, p1Select, p2Label, p2Select, fightButton);
                
                FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.8), overlay);
                fadeIn.setFromValue(1); 
                fadeIn.setToValue(0);
                fadeIn.setOnFinished(ev -> overlay.setMouseTransparent(true));
                fadeIn.play();
            });
            fadeOut.play();
        });

        fightButton.setOnAction(e -> { 
            System.out.println("Battle: " + p1Select.getValue() + " vs " + p2Select.getValue()); 
        });

        contentBox.getChildren().add(enterButton);

        StackPane root = new StackPane();
        root.getChildren().addAll(bgView, contentBox, overlay);

        // 5. Create Scene and Link CSS
        Scene scene = new Scene(root, width, height);
        
        // This is the Magic Line that loads your "Exquisite" styles
        scene.getStylesheets().add(getClass().getResource("/imagesandstyles/uistyle.css").toExternalForm());
        return scene;
    }
}