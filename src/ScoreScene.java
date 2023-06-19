import GameMechanisms.Enemy;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;

public class ScoreScene extends Scene {
    private BorderPane root;
    private ArrayList<BackToWelcomeListener> backToWelcomeListeners;

    public ScoreScene(Boolean win, Enemy enemy) {
        super(new BorderPane());
        root = (BorderPane) getRoot();
        backToWelcomeListeners = new ArrayList<>();

        // BACKGROUND
        // Image by upklyak on Freepik
        root.setBackground(new Background(new BackgroundImage(
                new Image("assets/ui/forest.jpg"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1080,720,true,true,true,true)
        )));

        // RESULT
        Label resultLabel = new Label(
                win?"WYGRAŁEŚ!":"PRZEGRAŁEŚ"
        );
        resultLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        resultLabel.setTextFill(Color.WHITE);

        // BUTTON
        Button goBackButton = new Button("Wróć");
        goBackButton.setPrefHeight(40);
        goBackButton.setPrefWidth(200);
        goBackButton.setFont(Font.font("Arial", FontPosture.REGULAR, 25));
        goBackButton.setOnAction(event->backToWelcomeListeners.forEach(BackToWelcomeListener::goBack));
        goBackButton.setAlignment(Pos.CENTER);

        root.setCenter(resultLabel);
        root.setBottom(goBackButton);
    }

    public void addBackToWelcomeListener(BackToWelcomeListener backToWelcomeListener) {
        backToWelcomeListeners.add(backToWelcomeListener);
    }
}

interface BackToWelcomeListener {
    void goBack();
}
