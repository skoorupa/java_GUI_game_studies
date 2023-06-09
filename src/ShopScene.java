import GameMechanisms.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
                    if (hero.getInventory().addItem(item))
                        hero.setCash(
                                hero.getCash() - item.getCost()
                        );
                    else {
                        Dialog dialog = new Dialog<>();
                        dialog.setTitle("Sklep");
                        dialog.setHeaderText("Brak miejsca w ekwipunku na typ "+item.getType().getText());
                        dialog.setContentText("Ilość slotów dla typu "+item.getType().getText()+": "+item.getType().getSlots());
                        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                        dialog.show();
                    }
                } else {
                    Dialog dialog = new Dialog<>();
                    dialog.setTitle("Sklep");
                    dialog.setHeaderText("Niewystarczająca ilość pieniędzy");
                    dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                    dialog.show();
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
            tooltip.setText(
                    tooltip.getText()+
                            "\n"+
                            "\n("+item.getType().getText()+","+item.getItemUsage().getText()+")"+
                            "\n$"+item.getCost()+" dukatów"
            );
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
        backImage.setPreserveRatio(true);
        backImage.setFitHeight(50);
        backButton.setGraphic(backImage);
        Tooltip.install(backButton,new Tooltip("Powrót"));
        backButton.setPrefHeight(50);
        backButton.setBackground(Background.EMPTY);

        inventoryPane.addClickListener(InventoryPane.DROP_ITEM);

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
