import GameMechanisms.Enemy;
import GameMechanisms.GameCharacter;
import GameMechanisms.Hero;
import GameMechanisms.TargetField;
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
    private GameCharacter gameCharacter;
    private HBox mainStatsPane, attackStatsPane;

    public PlayerStatsPane(GameCharacter gC) {
        super();
        gameCharacter = gC;
        mainStatsPane = new HBox();
        attackStatsPane = new HBox();

        // MAINSTATSPANE
            // HP
        addLabel("assets/ui/health-points.png", mainStatsPane, gameCharacter.healthPointsProperty(),"Punkty zdrowia",TargetField.HEALTH);

        if (gameCharacter instanceof Hero) {
            Hero hero = (Hero) gameCharacter;
            // MP
            addLabel("assets/ui/mana-points.png", mainStatsPane, hero.manaPointsProperty(), "Punkty magii",TargetField.MANA);

            // CASH
            addLabel("assets/ui/cash.png", mainStatsPane, hero.cashProperty(), "Gotówka",TargetField.CASH);

            // GUARDED
            addLabel("assets/ui/guarded.png", mainStatsPane, hero.guardedProperty(), "Tymczasowa tarcza",null);

            // POWEREDUP
            addLabel("assets/ui/poweredup.png", mainStatsPane, hero.poweredupProperty(), "Podwójne wzmocnienie",null);
        } else if (gameCharacter instanceof Enemy) {
            Enemy enemy = (Enemy) gameCharacter;
            // PRIZE
            addLabel("assets/ui/prize.png",mainStatsPane, enemy.getPrize(), "Nagroda", null);
        }

        // ATTACKSSTATSPANE
            // ATTACKPOINTS
        addLabel("assets/ui/attack-points.png", attackStatsPane, gameCharacter.attackPointsProperty(), "Punkty ataku",TargetField.ATTACK);

            // DEFENDPOINTS
        addLabel("assets/ui/defend-points.png", attackStatsPane, gameCharacter.defendPointsProperty(), "Punkty obrony",TargetField.DEFEND);

            // CRITCHANCE
        addLabel("assets/ui/crit-chance.png", attackStatsPane, gameCharacter.critChanceProperty(), "Szansa na obrażenia krytyczne", TargetField.CRITCHANCE);

            // ESCAPECHANCE
        if (gameCharacter instanceof Hero) {
            addLabel("assets/ui/escape-chance.png", attackStatsPane, ((Hero) gameCharacter).escapeChanceProperty(), "Szansa na ucieczkę", TargetField.ESCAPECHANCE);
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

    private void addLabel(String imagePath, HBox pane, ObservableValue ov, String tooltipText, TargetField targetField) {
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
        else if (ov instanceof SimpleBooleanProperty) {
            image.visibleProperty().bind(ov);
        }
        else
            label.textProperty().bind(ov);

        Tooltip tooltip = new Tooltip(tooltipText);

        if (targetField != null)
            tooltip.setText(tooltipText + tooltipFilter(targetField));

        tooltip.setFont(Font.font("Arial", FontPosture.REGULAR,15));
        tooltip.setShowDelay(new Duration(1));
        tooltip.setHideDelay(new Duration(1));

        ov.addListener(change->{
            if (targetField != null)
                tooltip.setText(tooltipText + tooltipFilter(targetField));
        });

        Tooltip.install(image, tooltip);
        Tooltip.install(label, tooltip);

        pane.getChildren().add(image);
        if (!(ov instanceof SimpleBooleanProperty))
            pane.getChildren().add(label);
    }
    private void addLabel(String imagePath, HBox pane, int value, String tooltipText, TargetField targetField) {
        addLabel(imagePath,pane,new SimpleStringProperty(Integer.toString(value)), tooltipText, targetField);
    }

    private String tooltipFilter(TargetField targetField) {
        if (gameCharacter instanceof Enemy) return "";

        StringBuilder result = new StringBuilder();
        Hero hero = (Hero) gameCharacter;
        hero.getInventory().getPassiveItemsWithField(targetField).forEach((item, integer) -> {
            result.append("\n  +"+integer+": "+item.getName());
        });
        return result.toString();
    }
}
