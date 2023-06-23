import GameMechanisms.Enemy;
import GameMechanisms.Hero;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;

public class PreviewEnemyScene extends Scene {
    private BorderPane root;
    private GridPane heroAndEnemyStatsPane, heroAndEnemyPane;
    private PlayerStatsPane heroStatsPane, enemyStatsPane;
    private VBox heroPane, enemyPane;
    private FlowPane optionsPane;

    private ArrayList<EscapeListener> escapeListeners;
    private ArrayList<FightListener> fightListeners;

    public PreviewEnemyScene() {
        super(new BorderPane());
        root = (BorderPane) getRoot();

        // INIT
        heroAndEnemyStatsPane = new GridPane();
        heroStatsPane = new PlayerStatsPane(getHero());
        enemyStatsPane = new PlayerStatsPane(getEnemy());
        heroAndEnemyPane = new GridPane();
        heroPane = new VBox();
        enemyPane = new VBox();
        optionsPane = new FlowPane();

        escapeListeners = new ArrayList<>();
        fightListeners = new ArrayList<>();

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
                        optionsPane.heightProperty()
                )
        );
        heroPane.getChildren().addAll(player_imageView);
        heroPane.setAlignment(Pos.CENTER);

            // ENEMYPANE
        Label enemyNameLabel = new Label(getEnemy().getName());
        enemyNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        enemyNameLabel.setTextFill(Color.WHITE);

        Image enemy_image = new Image(getEnemy().getAssetPath());
        ImageView enemy_imageView = new ImageView(enemy_image);
        enemy_imageView.setPreserveRatio(true);
        enemy_imageView.fitHeightProperty().bind(
                root.heightProperty().subtract(
                        heroAndEnemyStatsPane.heightProperty()
                ).subtract(
                        optionsPane.heightProperty()
                ).subtract(
                        enemyNameLabel.heightProperty()
                )
        );

        enemyPane.getChildren().addAll(enemyNameLabel,enemy_imageView);
        enemyPane.setAlignment(Pos.CENTER);

        heroAndEnemyPane.add(heroPane,0,0);
        heroAndEnemyPane.add(enemyPane,1,0);

        // OPTIONSPANE
        Button escapeButton = new Button();
        Button fightButton = new Button();

        Image escapeButton_image = new Image("assets/ui/escape.png");
        ImageView escapeButton_imageView = new ImageView(escapeButton_image);
        escapeButton_imageView.setPreserveRatio(true);
        escapeButton_imageView.setFitWidth(80);
        escapeButton_imageView.setFitHeight(80);
        escapeButton.setGraphic(escapeButton_imageView);
        escapeButton.setOnAction(event->{
            escapeListeners.forEach(EscapeListener::onEscapeTry);
        });
        Tooltip.install(escapeButton,new Tooltip("Spróbuj uciec"));

        Image fightButton_image = new Image("assets/ui/swords-emblem.png");
        ImageView fightButton_imageView = new ImageView(fightButton_image);
        fightButton_imageView.setPreserveRatio(true);
        fightButton_imageView.setFitWidth(80);
        fightButton_imageView.setFitHeight(80);
        fightButton.setGraphic(fightButton_imageView);
        fightButton.setOnAction(event->{
            fightListeners.forEach(FightListener::onFight);
        });
        Tooltip.install(fightButton,new Tooltip("Przystąp do walki"));

        optionsPane.getChildren().addAll(escapeButton,fightButton);
        optionsPane.getChildren().forEach(node-> {
            Button button = (Button) node;
            button.setPrefSize(80,80);
            button.setBackground(Background.EMPTY);
        });
        optionsPane.setAlignment(Pos.BOTTOM_CENTER);

        // LAYOUT
        root.setTop(heroAndEnemyStatsPane);
        root.setCenter(heroAndEnemyPane);
        root.setBottom(optionsPane);
    }

    public void addEscapeListener(EscapeListener escapeListener) {
        this.escapeListeners.add(escapeListener);
    }

    public void addFightListener(FightListener fightListener) {
        this.fightListeners.add(fightListener);
    }

    public Hero getHero() {
        return HeroHandler.getHero();
    }
    public Enemy getEnemy() {
        return EnemyHandler.getCurrentEnemy();
    }
}
