import GameMechanisms.Hero;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class WelcomeScene extends Scene {
    private BorderPane root;
    private InventoryPane inventoryPane;
    private VBox playerStatsPane, shopPane, fightPane;
    private FlowPane playerPane;
    private Button shopButton, fightButton;
    private ArrayList<PreviewEnemyListener> previewEnemyListeners;
    private ArrayList<ShopListener> shopListeners;

    public WelcomeScene() {
        super(new BorderPane());
        root = (BorderPane) getRoot();

        // INIT
        playerStatsPane = new PlayerStatsPane(getHero());
        shopPane = new VBox();
        fightPane = new VBox();
        playerPane = new FlowPane();
        inventoryPane = new InventoryPane(root.widthProperty(), true);

        shopButton = new Button();
        fightButton = new Button();
        previewEnemyListeners = new ArrayList<>();
        shopListeners = new ArrayList<>();

        // BACKGROUND
            // Image by upklyak on Freepik
        root.setBackground(new Background(new BackgroundImage(
                new Image("assets/ui/forest-background.jpg"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1080,720,true,true,true,true)
        )));

        // PANES
            // SHOPPANE
        shopButton.setPrefSize(80,80);
        Image shopButton_image = new Image("assets/ui/shop.png");
        ImageView shopButton_imageView = new ImageView(shopButton_image);
        shopButton_imageView.setPreserveRatio(true);
        shopButton_imageView.setFitWidth(80);
        shopButton_imageView.setFitHeight(80);
        shopButton.setGraphic(shopButton_imageView);
        shopButton.setBackground(Background.EMPTY);
        shopButton.setOnAction(e->{
            shopListeners.forEach(ShopListener::onShop);
        });

        shopPane.getChildren().addAll(shopButton);
        shopPane.setAlignment(Pos.CENTER);

            // FIGHTPANE
        fightButton.setPrefSize(80,80);
        Image fightButton_image = new Image("assets/ui/swords-emblem.png");
        ImageView fightButton_imageView = new ImageView(fightButton_image);
        fightButton_imageView.setPreserveRatio(true);
        fightButton_imageView.setFitWidth(80);
        fightButton_imageView.setFitHeight(80);
        fightButton.setGraphic(fightButton_imageView);
        fightButton.setOnAction(e->{
//            HeroHandler.getHero().getInventory().addItem(
//                    Item.Weapon_Sword2
//            );
            previewEnemyListeners.forEach(PreviewEnemyListener::onPreview);
        });
        fightButton.setBackground(Background.EMPTY);

        fightPane.getChildren().addAll(fightButton);
        fightPane.setAlignment(Pos.CENTER);

            // PLAYERPANE
        // Image by rawpixel.com on Freepik
        Image player_image = new Image(getHero().getAssetPath());
        ImageView player_imageView = new ImageView(player_image);
        player_imageView.setPreserveRatio(true);
        player_imageView.setFitHeight(500);

        playerPane.getChildren().addAll(player_imageView);
        playerPane.setAlignment(Pos.CENTER);

            // INVENTORYPANE
        inventoryPane.addClickListener(System.out::println);

        // LAYOUT
        root.setTop(playerStatsPane);
        root.setLeft(shopPane);
        root.setRight(fightPane);
        root.setCenter(playerPane);
        root.setBottom(inventoryPane);
    }

    public void addPreviewEnemyListener(PreviewEnemyListener previewEnemyListener) {
        previewEnemyListeners.add(previewEnemyListener);
    }

    public void addShopListener(ShopListener shopListener) {
        shopListeners.add(shopListener);
    }

    private Hero getHero() {
        return HeroHandler.getHero();
    }
}
