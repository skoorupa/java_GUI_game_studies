import GameMechanisms.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.util.ArrayList;

public class FightScene extends Scene {
    private BorderPane root;
    private GridPane heroAndEnemyStatsPane, heroAndEnemyPane;
    private PlayerStatsPane heroStatsPane, enemyStatsPane;
    private VBox heroPane, enemyPane;
    private HBox actionPane;
    private Button shopButton, escapeButton, attackButton;
    private InventoryPane inventoryPane;

    private ArrayList<EscapeListener> escapeListeners;
    private ArrayList<ScoreListener> scoreListeners;
    private ArrayList<ShopListener> shopListeners;

    public FightScene() {
        super(new BorderPane());
        root = (BorderPane) getRoot();

        // INIT
        heroAndEnemyStatsPane = new GridPane();
        heroStatsPane = new PlayerStatsPane(getHero());
        enemyStatsPane = new PlayerStatsPane(getEnemy());
        heroAndEnemyPane = new GridPane();
        heroPane = new VBox();
        enemyPane = new VBox();
        actionPane = new HBox();
        shopButton = new Button();
        escapeButton = new Button();
        attackButton = new Button();
        inventoryPane = new InventoryPane(
                root.widthProperty().subtract(escapeButton.widthProperty()).subtract(attackButton.widthProperty()),
                false

        );

        escapeListeners = new ArrayList<>();
        scoreListeners = new ArrayList<>();
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

        // HEROANDENEMYSTATSPANE
        ColumnConstraints topColumn1 = new ColumnConstraints();
        ColumnConstraints topColumn2 = new ColumnConstraints();
        topColumn1.setPercentWidth(50);
        topColumn2.setPercentWidth(50);
        topColumn1.setHgrow(Priority.ALWAYS);
        topColumn2.setHgrow(Priority.ALWAYS);
        heroAndEnemyStatsPane.getColumnConstraints().addAll(topColumn1,topColumn2);
        heroAndEnemyStatsPane.add(heroStatsPane,0,0);
        heroAndEnemyStatsPane.add(enemyStatsPane,1,0);

        // HEROANDENEMYPANE
        ColumnConstraints centerColumn1 = new ColumnConstraints();
        ColumnConstraints centerColumn2 = new ColumnConstraints();
        centerColumn1.setPercentWidth(50);
        centerColumn2.setPercentWidth(50);
        centerColumn1.setHgrow(Priority.ALWAYS);
        centerColumn2.setHgrow(Priority.ALWAYS);
        heroAndEnemyPane.getColumnConstraints().addAll(centerColumn1,centerColumn2);

        // HEROPANE
        Image player_image = new Image(getHero().getAssetPath());
        ImageView player_imageView = new ImageView(player_image);
        player_imageView.setPreserveRatio(true);
        player_imageView.fitHeightProperty().bind(
                root.heightProperty().subtract(
                        heroAndEnemyStatsPane.heightProperty()
                ).subtract(
                        actionPane.heightProperty()
                )
        );
        heroPane.getChildren().addAll(player_imageView);
        heroPane.setAlignment(Pos.CENTER);

        // ENEMYPANE
        Label enemyNameLabel = new Label(getEnemy().getName());
        enemyNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        enemyNameLabel.setTextFill(Color.WHITE);

        Image enemy_image = new Image(getEnemy().getAssetPath());
        ImageView enemy_imageView = new ImageView(enemy_image);
        enemy_imageView.setPreserveRatio(true);
        enemy_imageView.fitHeightProperty().bind(
                root.heightProperty().subtract(
                        heroAndEnemyStatsPane.heightProperty()
                ).subtract(
                        actionPane.heightProperty()
                ).subtract(
                        enemyNameLabel.heightProperty()
                )
        );

        enemyPane.getChildren().addAll(enemyNameLabel,enemy_imageView);
        enemyPane.setAlignment(Pos.CENTER);

        heroAndEnemyPane.add(heroPane,0,0);
        heroAndEnemyPane.add(enemyPane,1,0);

        // ACTIONPANE
            // ESCAPEBUTTON
        Image escapeButton_image = new Image("assets/ui/escape.png");
        ImageView escapeButton_imageView = new ImageView(escapeButton_image);
        escapeButton_imageView.setPreserveRatio(true);
        escapeButton.setGraphic(escapeButton_imageView);
            // SHOPBUTTON
        Image shopButton_image = new Image("assets/ui/shop.png");
        ImageView shopButton_imageView = new ImageView(shopButton_image);
        shopButton_imageView.setPreserveRatio(true);
        shopButton.setGraphic(shopButton_imageView);
            // ATTACKBUTTON
        Image attackButton_image = new Image("assets/ui/attack.png");
        ImageView attackButton_imageView = new ImageView(attackButton_image);
        attackButton_imageView.setPreserveRatio(true);
        attackButton.setGraphic(attackButton_imageView);

        actionPane.getChildren().addAll(escapeButton,shopButton,attackButton);
        actionPane.getChildren().forEach(node->{
            Button button = (Button) node;
            ImageView imageView = (ImageView) button.getGraphic();

            button.setBackground(Background.EMPTY);
            button.setPrefHeight(75);
            imageView.setFitHeight(75);
        });

        escapeButton.setOnAction(event->escapeListeners.forEach(EscapeListener::onEscapeTry));
        shopButton.setOnAction(event->shopListeners.forEach(ShopListener::onShop));
        attackButton.setOnAction(event->{
            attackEnemy(true, player_imageView, enemy_imageView);
        });

        // INVENTORYPANE
        inventoryPane.addClickListener(item->{
            HeroHandler.useItem(item);
            if (item.getItemUsage() == ItemUsage.ACTIVE)
                attackEnemy(false, player_imageView, enemy_imageView);

        });
        actionPane.getChildren().add(inventoryPane);

        // LAYOUT
        root.setTop(heroAndEnemyStatsPane);
        root.setCenter(heroAndEnemyPane);
        root.setBottom(actionPane);
    }
    public void attackEnemy(boolean enemyAttack, ImageView heroImage, ImageView enemyImage) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, (evt)-> {
                    actionPane.setVisible(false);
                    if (enemyAttack) {
                        HeroHandler.attackEnemy();
                        animateImageDamage(enemyImage);
                    }
                }),
                new KeyFrame(Duration.seconds(1), (evt)-> {
                    if (EnemyHandler.attackHero())
                        animateImageDamage(heroImage);
                }),
                new KeyFrame(Duration.seconds(2),(evt)->{
                    actionPane.setVisible(true);
                    if (getHero().isDead())
                        scoreListeners.forEach((scoreListener)->scoreListener.onScore(false, getEnemy()));
                    else if (getEnemy().isDead())
                        scoreListeners.forEach((scoreListener)->scoreListener.onScore(true, getEnemy()));
                })
        );
        timeline.play();
    }
    public void animateImageDamage(ImageView imageView) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(100));
        transition.setAutoReverse(true);
        transition.setCycleCount(2);
        transition.setByY(50);
        transition.setNode(imageView);
        transition.playFromStart();
    }
    public void addEscapeListener(EscapeListener escapeListener) {
        this.escapeListeners.add(escapeListener);
    }
    public void addScoreListener(ScoreListener scoreListener) {this.scoreListeners.add(scoreListener);}
    public void addShopListener(ShopListener shopListener) {
        this.shopListeners.add(shopListener);
    }

    public Hero getHero() {
        return HeroHandler.getHero();
    }
    public Enemy getEnemy() {
        return EnemyHandler.getCurrentEnemy();
    }
}
