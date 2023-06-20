package GameMechanisms;

import java.util.ArrayList;
import java.util.Collections;

public enum Item {
    Weapon_Sword1("Brązowy miecz",ItemType.HAND,2, new Effect[]{new Effect(TargetCharacter.ENEMY,TargetField.ATTACK,15)},ItemUsage.PASSIVE, "assets/items/hand/sword-1.png"),
    Weapon_Sword2("Srebrny miecz",ItemType.HAND,4, new Effect[]{new Effect(TargetCharacter.ENEMY,TargetField.ATTACK,25)},ItemUsage.PASSIVE, "assets/items/hand/sword-2.png"),
    Food_Apple("Jabłko",ItemType.BACKPACK,2, new Effect[]{new Effect(TargetCharacter.HERO,TargetField.HEALTH,10)},ItemUsage.USEABLE, "assets/items/backpack/apple.png"),
    ;

    private String name, imagePath;
    private ItemType type;
    private int cost;
    private Effect[] effects;
    private ItemUsage itemUsage;

    Item(String name, ItemType type, int cost, Effect[] effects, ItemUsage itemUsage, String imagePath) {
        this.name = name;
        this.type = type;
        this.cost = cost;
        this.effects = effects;
        this.itemUsage = itemUsage;
        this.imagePath = imagePath;
    }

    public static ArrayList<Item> getListOfItems() {
        ArrayList<Item> result = new ArrayList<>();
        Collections.addAll(result, values());
        return result;
    }

    public String getName() {
        return name;
    }

    public ItemType getType() {
        return type;
    }

    public int getCost() {
        return cost;
    }

    public Effect[] getEffects() {
        return effects;
    }

    public String getImagePath() {
        return imagePath;
    }

    public ItemUsage getItemUsage() {
        return itemUsage;
    }

    @Override
    public String toString() {
        return getName();
    }
}

