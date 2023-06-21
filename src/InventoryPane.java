import GameMechanisms.Effect;
import GameMechanisms.Hero;
import GameMechanisms.Item;
import GameMechanisms.ItemUsage;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Callback;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.function.Consumer;

public class InventoryPane extends HBox {
    private ListView<Item> inventoryListView;
    private ArrayList<Consumer<Item>> clickListeners;

    public InventoryPane(
            ObservableValue<? extends Number> widthProperty,
            boolean showPassive
    ) {
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
                                        tooltip.getText()+
                                                "\n  - "+
                                                effect.getTargetField().getText()+
                                                ": "+
                                                (effect.getValue()>0?"+":"")+
                                                effect.getValue()
                                );
                            }
                            tooltip.setShowDelay(new Duration(1));
                            tooltip.setHideDelay(new Duration(1));
                            tooltip.setFont(Font.font(15));

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

        ObservableList<Item> itemList;

        if (!showPassive)
            itemList = getHero().getInventory().getItemListWithoutPassives();
        else
            itemList = getHero().getInventory().getItemList();

        inventoryListView.setItems(itemList);
        itemList.addListener((ListChangeListener<? super Item>) change -> refresh());

        inventoryListView.prefWidthProperty().bind(widthProperty);

        getChildren().add(inventoryListView);
        setAlignment(Pos.BOTTOM_CENTER);
    }

    public static final Consumer<Item> DROP_ITEM = (item)->{
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Usuwanie przedmiotu");
        dialog.setHeaderText("Czy na pewno chcesz usunąć przedmiot "+item.getName()+"?");
        ButtonType yesButton = new ButtonType("Tak", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("Nie", ButtonBar.ButtonData.NO);
        dialog.getDialogPane().getButtonTypes().addAll(yesButton,noButton);

        dialog.showAndWait().ifPresent(response->{
            if (response == yesButton) {
                HeroHandler.getHero().getInventory().removeItem(item);
            } else {
                dialog.close();
            }
        });
    };

    public void addClickListener(Consumer<Item> consumer) {
        clickListeners.add(consumer);
    }

    public void refresh() {
        inventoryListView.refresh();
    }

    private Hero getHero() {
        return HeroHandler.getHero();
    }
}
