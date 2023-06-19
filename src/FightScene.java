import GameMechanisms.*;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;

public class FightScene extends Scene {
    private BorderPane root;
    private GridPane heroAndEnemyStatsPane, heroAndEnemyPane;
    private PlayerStatsPane heroStatsPane, enemyStatsPane;
    private VBox heroPane, enemyPane;
    private HBox actionPane;
    private Button escapeButton, attackButton;
    private InventoryPane inventoryPane;

    private ArrayList<EscapeListener> escapeListeners;

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
        escapeButton = new Button("Ucieczka");
        attackButton = new Button("Atak");
        inventoryPane = new InventoryPane(
                root.widthProperty().subtract(escapeButton.widthProperty()).subtract(attackButton.widthProperty()),
                false
        );

        escapeListeners = new ArrayList<>();

        // BACKGROUND
        // Image by upklyak on Freepik
        root.setBackground(new Background(new BackgroundImage(
                new Image("assets/ui/forest.jpg"),
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
        actionPane.getChildren().addAll(escapeButton,attackButton);
        actionPane.getChildren().forEach(node->{
            Button button = (Button) node;
            button.prefHeightProperty().bind(inventoryPane.heightProperty());
            button.setPrefWidth(200);
            button.setFont(Font.font("Arial", FontPosture.REGULAR, 25));
        });

        escapeButton.setOnAction(event->escapeListeners.forEach(EscapeListener::onEscapeTry));

        actionPane.getChildren().add(inventoryPane);

        // LAYOUT
        root.setTop(heroAndEnemyStatsPane);
        root.setCenter(heroAndEnemyPane);
        root.setBottom(actionPane);
    }

    public void addEscapeListener(EscapeListener escapeListener) {
        this.escapeListeners.add(escapeListener);
    }
    public Hero getHero() {
        return HeroHandler.getHero();
    }
    public Enemy getEnemy() {
        return EnemyHandler.getCurrentEnemy();
    }
}
