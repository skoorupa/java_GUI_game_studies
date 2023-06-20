import GameMechanisms.Enemy;
import GameMechanisms.GameCharacter;
import GameMechanisms.Hero;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.util.Duration;

public class PlayerStatsPane extends VBox {
    HBox mainStatsPane, attackStatsPane;

    public PlayerStatsPane(GameCharacter gameCharacter) {
        super();
        mainStatsPane = new HBox();
        attackStatsPane = new HBox();

        // MAINSTATSPANE
            // HP
        addLabel("assets/ui/health-points.png", mainStatsPane, gameCharacter.healthPointsProperty(),"Punkty zdrowia");

        if (gameCharacter instanceof Hero) {
            Hero hero = (Hero) gameCharacter;
            // MP
            addLabel("assets/ui/mana-points.png", mainStatsPane, hero.manaPointsProperty(), "Punkty magii");

            // CASH
            addLabel("assets/ui/cash.png", mainStatsPane, hero.cashProperty(), "Gotówka");
        } else if (gameCharacter instanceof Enemy) {
            Enemy enemy = (Enemy) gameCharacter;
            // PRIZE
            addLabel("assets/ui/prize.png",mainStatsPane, enemy.getPrize(), "Nagroda");
        }

        // ATTACKSSTATSPANE
            // ATTACKPOINTS
        addLabel("assets/ui/attack-points.png", attackStatsPane, gameCharacter.attackPointsProperty(), "Punkty ataku");

            // DEFENDPOINTS
        addLabel("assets/ui/defend-points.png", attackStatsPane, gameCharacter.defendPointsProperty(), "Punkty obrony");

            // CRITCHANCE
        addLabel("assets/ui/crit-chance.png", attackStatsPane, gameCharacter.critChanceProperty(), "Szansa na obrażenia krytyczne");

            // ESCAPECHANCE
        if (gameCharacter instanceof Hero) {
            addLabel("assets/ui/escape-chance.png", attackStatsPane, ((Hero) gameCharacter).escapeChanceProperty(), "Szansa na ucieczkę");
        }

        // LAYOUT
        mainStatsPane.setAlignment(Pos.CENTER_LEFT);
        attackStatsPane.setAlignment(Pos.CENTER_LEFT);
        mainStatsPane.setPadding(new Insets(10,10,0,10));
        attackStatsPane.setPadding(new Insets(10));
        mainStatsPane.setSpacing(5);
        attackStatsPane.setSpacing(5);

        getChildren().addAll(mainStatsPane,attackStatsPane);
    }

    private void addLabel(String imagePath, HBox pane, ObservableValue ov, String tooltipText) {
        Image imageraw = new Image(imagePath);
        ImageView image = new ImageView(imageraw);
        image.setPreserveRatio(true);
        image.setFitHeight(30);

        Label label = new Label();
        label.setBackground(new Background(new BackgroundFill(Color.WHITE,new CornerRadii(10), null)));
        label.setFont(Font.font("Arial",15));
        label.setPadding(new Insets(5));
        if (ov instanceof SimpleIntegerProperty)
            label.textProperty().bind(((SimpleIntegerProperty)ov).asString());
        else if (ov instanceof SimpleDoubleProperty)
            label.textProperty().bind(((SimpleDoubleProperty)ov).asString().concat("%"));
        else
            label.textProperty().bind(ov);

        Tooltip tooltip = new Tooltip(tooltipText);
        tooltip.setFont(Font.font("Arial", FontPosture.REGULAR,10));
        tooltip.setShowDelay(new Duration(1));
        tooltip.setHideDelay(new Duration(1));

        Tooltip.install(image, tooltip);
        Tooltip.install(label, tooltip);

        pane.getChildren().addAll(image,label);
    }
    private void addLabel(String imagePath, HBox pane, int value, String tooltipText) {
        addLabel(imagePath,pane,new SimpleStringProperty(Integer.toString(value)), tooltipText);
    }
}
