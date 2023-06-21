import GameMechanisms.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.ArrayList;

public class ShopScene extends Scene {
    private BorderPane root;
    private PlayerStatsPane playerStatsPane;
    private ScrollPane scrollPane;
    private FlowPane itemsPane;
    private Button backButton;
    private InventoryPane inventoryPane;
    private HBox bottomPane;
    private ArrayList<BackListener> backListeners;

    public ShopScene() {
        super(new BorderPane());
        root = (BorderPane) getRoot();

        playerStatsPane = new PlayerStatsPane(HeroHandler.getHero());
        scrollPane = new TransparentScrollPane();
        itemsPane = new FlowPane();
        backListeners = new ArrayList<>();
        bottomPane = new HBox();
        backButton = new Button();
        inventoryPane = new InventoryPane(root.widthProperty().subtract(backButton.getWidth()),true);

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
            button.setPrefHeight(75);
            button.setBackground(Background.EMPTY);

            ImageView imageView = new ImageView(new Image(item.getImagePath()));
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(75);
            button.setGraphic(imageView);

            button.setOnAction(event->{
                Hero hero = HeroHandler.getHero();
                if (hero.getCash() >= item.getCost()) {
                    hero.getInventory().addItem(item);
                    hero.setCash(
                            hero.getCash() - item.getCost()
                    );
                }
            });

            Tooltip tooltip = new Tooltip(item.getName()+":");
            for(Effect effect : item.getEffects()) {
                tooltip.setText(
                        tooltip.getText()+
                                "\n  - "+
                                effect.getTargetField().getText()+
                                ": "+
                                (effect.getValue()>0?"+":"")+
                                effect.getValue()
                );
            }
            tooltip.setFont(Font.font(15));
            tooltip.setText(tooltip.getText()+"\n\n$"+item.getCost()+" dukatów");
            tooltip.setHideDelay(Duration.ZERO);
            tooltip.setShowDelay(Duration.ZERO);
            Tooltip.install(button,tooltip);

            itemsPane.getChildren().add(button);
        }
        scrollPane.setContent(itemsPane);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.viewportBoundsProperty().addListener((ov, oldBounds, bounds) -> {
            itemsPane.setPrefWidth(bounds.getWidth());
            itemsPane.setPrefHeight(bounds.getHeight());
        });
        scrollPane.setBackground(Background.EMPTY);
        scrollPane.getContent().setStyle("-fx-background-color: transparent;");

        // BACKBUTTON
        ImageView backImage = new ImageView(new Image("assets/ui/back.png"));
        backButton.setGraphic(backImage);
        backButton.setPrefHeight(50);
        backButton.setBackground(Background.EMPTY);
        backImage.setPreserveRatio(true);
        backImage.setFitHeight(50);

        backButton.setOnAction(e->{
            backListeners.forEach(BackListener::goBack);
        });
        bottomPane.getChildren().addAll(backButton, inventoryPane);
        bottomPane.setAlignment(Pos.CENTER);

        // LAYOUT

        root.setTop(playerStatsPane);
        root.setCenter(scrollPane);
        root.setBottom(bottomPane);
    }

    public void addBackListener(BackListener backListener) {
        backListeners.add(backListener);
    }
}

class TransparentScrollPane extends ScrollPane {
    @Override
    protected void layoutChildren() {
        for (int i = 0; i < getChildren().size(); i++) {
            if (getChildren().get(i) instanceof StackPane) {
                StackPane stackPane = (StackPane) getChildren().get(i);
                stackPane.setBackground(Background.EMPTY);
            }
        }
        super.layoutChildren();
    }
}
