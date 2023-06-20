import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class MenuScene extends Scene {
    private BorderPane root;
    private FlowPane titlePane;
    private VBox buttonPane;
    private Label title;
    private Button start,exit,credits;
    private Region spacer;

    private CloseListener closeListener;
    private PlayListener playListener;
    private CreditsListener creditsListener;

    public MenuScene() {
        super(new BorderPane());
        root = (BorderPane) getRoot();

        // INIT
        titlePane = new FlowPane();
        buttonPane = new VBox();

        // BACKGROUND
            // Image by upklyak on Freepik
        root.setBackground(new Background(new BackgroundImage(
                new Image("assets/ui/forest-background.jpg"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1080,720,true,true,true,true)
        )));

        // TITLE
        title = new Label("PROJEKT GUI");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        title.setTextFill(Color.WHITE);

        titlePane.setAlignment(Pos.TOP_CENTER);
        titlePane.getChildren().addAll(title);

        // BUTTONS
        start = new Button("Zacznij");
        start.setOnAction(event -> {
            if (playListener != null) {
                playListener.onPlay();
            }
        });

        credits = new Button("Credits");
        credits.setOnAction(event -> {
            if (creditsListener != null) {
                creditsListener.onCredits();
            }
        });

        exit = new Button("Wyjdź");
        exit.setOnAction(event -> {
            if (closeListener != null) {
                closeListener.onClose();
            }
        });

        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.getChildren().addAll(start,credits,exit);
        buttonPane.getChildren().forEach(node->{
            Button button = (Button) node;
            button.setPrefHeight(40);
            button.setPrefWidth(200);
            button.setFont(Font.font("Arial", FontPosture.REGULAR, 25));
        });

        // LAYOUT

        spacer = new Region();
        spacer.setPrefHeight(100);

        root.setPadding(new Insets(50));
        root.setTop(titlePane);
        root.setCenter(spacer);
        root.setBottom(buttonPane);
    }

    public void setCloseListener(CloseListener closeListener) {
        this.closeListener = closeListener;
    }
    public void setPlayListener(PlayListener playListener) {
        this.playListener = playListener;
    }
    public void setCreditsListener(CreditsListener creditsListener) {
        this.creditsListener = creditsListener;
    }
}

interface PlayListener {
    void onPlay();
}
interface CreditsListener {
    void onCredits();
}