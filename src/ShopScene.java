import GameMechanisms.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class ShopScene extends Scene {
    private BorderPane root;
    private PlayerStatsPane playerStatsPane;
    private FlowPane itemsPane;
    private Button backButton;
    private ArrayList<BackListener> backListeners;

    public ShopScene() {
        super(new BorderPane());
        root = (BorderPane) getRoot();

        playerStatsPane = new PlayerStatsPane(HeroHandler.getHero());
        itemsPane = new FlowPane();
        backListeners = new ArrayList<>();

        // BACKGROUND
        root.setBackground(new Background(new BackgroundImage(
                new Image("assets/ui/shop-background.jpg"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1080,720,true,true,true,true)
        )));

        // ITEMS
        for (Item item :
                Item.values()) {
            Button button = new Button();
            ImageView imageView = new ImageView(new Image(item.getImagePath()));
            imageView.setPreserveRatio(true);
            button.setBackground(Background.EMPTY);
            button.setPrefHeight(75);
            imageView.setFitHeight(75);
            button.setGraphic(imageView);

            itemsPane.getChildren().add(button);
        }

        // BACKBUTTON
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

        // LAYOUT
        root.setTop(playerStatsPane);
        root.setCenter(itemsPane);
        root.setBottom(backButton);
    }

    public void addBackListener(BackListener backListener) {
        backListeners.add(backListener);
    }
}
