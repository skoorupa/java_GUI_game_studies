import GameMechanisms.Effect;
import GameMechanisms.Hero;
import GameMechanisms.Item;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class InventoryPane extends HBox {
    private ListView<Item> inventoryListView;
    private ArrayList<Consumer<Item>> clickListeners;

    public InventoryPane(BorderPane root) {
        super();
        inventoryListView = new ListView<>();
        clickListeners = new ArrayList<>();

        inventoryListView.setOrientation(Orientation.HORIZONTAL);
        inventoryListView.setBackground(Background.EMPTY);
        inventoryListView.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        inventoryListView.setMaxHeight(75);
        inventoryListView.backgroundProperty().set(Background.EMPTY);
        inventoryListView.setCellFactory(new Callback<ListView<Item>, ListCell<Item>>() {
            @Override
            public ListCell<Item> call(ListView<Item> itemListView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Item item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                            setStyle("");
                        } else {
                            ImageView imageView = new ImageView(item.getImagePath());
                            imageView.setPreserveRatio(true);
                            imageView.setFitHeight(50);
                            imageView.setFitWidth(50);

                            Tooltip tooltip = new Tooltip(item.getName());
                            for(Effect effect : item.getEffects()) {
                                tooltip.setText(
                                        tooltip.getText()+"\n  - "+effect.getTargetField().getText()+": "+effect.getValue()
                                );
                            }
                            tooltip.setShowDelay(new Duration(1));
                            tooltip.setHideDelay(new Duration(1));

                            setTooltip(tooltip);
                            setGraphic(imageView);
                            setBackground(Background.EMPTY);

                            setOnMouseClicked((e)->{
                                clickListeners.forEach(a->a.accept(item));
                            });
                        }
                    }
                };
            }
        });

        inventoryListView.setItems(getHero().getInventory().getItemList());
        inventoryListView.prefWidthProperty().bind(root.widthProperty());
        System.out.println(getHero().getInventory().getItemList());
//        getHero().getInventory().addChangeListener(() -> {
//            inventoryListView.setItems(getHero().getInventory().getItemList());
//        });

        getChildren().add(inventoryListView);
        setAlignment(Pos.BOTTOM_CENTER);
    }

    public void addClickListener(Consumer<Item> consumer) {
        clickListeners.add(consumer);
    }

    private Hero getHero() {
        return HeroHandler.getHero();
    }
}
