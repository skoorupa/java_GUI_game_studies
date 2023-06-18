package GameMechanisms;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.ArrayList;

public class Inventory {
    private ObservableMap<ItemType, ObservableList<Item>> map;
    private ObservableList<Item> list;
    private ArrayList<InventoryChangeListener> inventoryChangeListeners;
    public Inventory() {
        this.map = FXCollections.observableHashMap();
        this.list = FXCollections.observableArrayList();
        this.inventoryChangeListeners = new ArrayList<>();
    }

    public void addItem(Item item) {
        if (!map.containsKey(item.getType()))
            map.put(item.getType(), FXCollections.observableArrayList());

        if (map.get(item.getType()).size() < item.getType().getSlots()) {
            map.get(item.getType()).add(item);
            list.add(item);
            inventoryChangeListeners.forEach(InventoryChangeListener::onChange);
        }
        else
            System.out.println("brak miejsca");
    }

    public void removeItem(Item item) {
        map.get(item.getType()).remove(item);
        list.remove(item);
        inventoryChangeListeners.forEach(InventoryChangeListener::onChange);
    }

    public ObservableList<Item> getItemList() {
        return list;
    }

    public int size() {
        return list.size();
    }

    public void addChangeListener(InventoryChangeListener inventoryChangeListener) {
        inventoryChangeListeners.add(inventoryChangeListener);
    }

    public void removeChangeListener(InventoryChangeListener inventoryChangeListener) {
        inventoryChangeListeners.remove(inventoryChangeListener);
    }
}
