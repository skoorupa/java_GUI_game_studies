import GameMechanisms.Enemy;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;

public class ScoreScene extends Scene {
    private BorderPane root;
    private ArrayList<BackListener> backListeners;
    private Button backButton;

    public ScoreScene(Boolean win, Enemy enemy) {
        super(new BorderPane());
        root = (BorderPane) getRoot();
        backListeners = new ArrayList<>();

        // BACKGROUND
        // Image by upklyak on Freepik
        root.setBackground(new Background(new BackgroundImage(
                new Image("assets/ui/forest-background.jpg"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1080,720,true,true,true,true)
        )));

        // RESULT
        Label resultLabel = new Label(
                win?"WYGRAŁEŚ!":"PRZEGRAŁEŚ"
        );
        if (EnemyHandler.getAvailableEnemies().size()==0)
            resultLabel.setText("WYGRAŁEŚ CAŁĄ GRĘ!");
        resultLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        resultLabel.setTextFill(Color.WHITE);

        // BUTTON
        backButton = new Button();
        ImageView backImage = new ImageView(new Image("assets/ui/back.png"));
        backButton.setGraphic(backImage);
        backButton.setPrefHeight(50);
        backButton.setBackground(Background.EMPTY);
        backImage.setPreserveRatio(true);
        backImage.setFitHeight(50);

        backButton.setOnAction(e->{
            backListeners.forEach(BackListener::goBack);
        });

        root.setCenter(resultLabel);
        if (win && EnemyHandler.getAvailableEnemies().size()>0) root.setBottom(backButton);
    }

    public void addBackListener(BackListener backListener) {
        backListeners.add(backListener);
    }
}
