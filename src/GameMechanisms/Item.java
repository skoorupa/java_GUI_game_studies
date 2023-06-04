package GameMechanisms;

public class Item {
    private String name;
    private ItemType type;
    private int cost;
    private Effect[] effects;
    private boolean isReusable;
}

enum ItemType {
    ARMOUR, HAND, BACKPACK, SPECIAL
}