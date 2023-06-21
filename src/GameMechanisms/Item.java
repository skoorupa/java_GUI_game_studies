package GameMechanisms;

import java.util.ArrayList;
import java.util.Collections;

public enum Item {
    Weapon_Sword1("Brązowy miecz",ItemType.HAND,2, new Effect[]{new Effect(TargetCharacter.ENEMY,TargetField.ATTACK,15)},ItemUsage.PASSIVE, "assets/items/hand/sword-1.png"),
    Weapon_Sword2("Srebrny miecz",ItemType.HAND,10, new Effect[]{new Effect(TargetCharacter.ENEMY,TargetField.ATTACK,25)},ItemUsage.PASSIVE, "assets/items/hand/sword-2.png"),
    Weapon_Sword3("Ognisty miecz",ItemType.HAND,25, new Effect[]{new Effect(TargetCharacter.ENEMY,TargetField.ATTACK,35)},ItemUsage.PASSIVE, "assets/items/hand/sword-3.png"),
    Weapon_Sword4("Złoty miecz",ItemType.HAND,50, new Effect[]{new Effect(TargetCharacter.ENEMY,TargetField.ATTACK,45)},ItemUsage.PASSIVE, "assets/items/hand/sword-4.png"),
    Weapon_Sword5("Diamentowy miecz",ItemType.HAND,100, new Effect[]{new Effect(TargetCharacter.ENEMY,TargetField.ATTACK,55)},ItemUsage.PASSIVE, "assets/items/hand/sword-5.png"),
    Weapon_Gun1("Brązowa strzelba",ItemType.HAND,10, new Effect[]{new Effect(TargetCharacter.ENEMY,TargetField.ATTACK,10)},ItemUsage.PASSIVE, "assets/items/hand/gun-1.png"),
    Weapon_Gun2("Dobry pistolet",ItemType.HAND,20, new Effect[]{new Effect(TargetCharacter.ENEMY,TargetField.ATTACK,20)},ItemUsage.PASSIVE, "assets/items/hand/gun-2.png"),
    Weapon_Gun3("Ogniste P90",ItemType.HAND,30, new Effect[]{new Effect(TargetCharacter.ENEMY,TargetField.ATTACK,30)},ItemUsage.PASSIVE, "assets/items/hand/gun-3.png"),
    Weapon_Gun4("Złote MP7",ItemType.HAND,40, new Effect[]{new Effect(TargetCharacter.ENEMY,TargetField.ATTACK,40)},ItemUsage.PASSIVE, "assets/items/hand/gun-4.png"),
    Weapon_Gun5("Diamentowy karabin",ItemType.HAND,50, new Effect[]{new Effect(TargetCharacter.ENEMY,TargetField.ATTACK,50)},ItemUsage.PASSIVE, "assets/items/hand/gun-5.png"),
    Defence_Shield1("Srebrna tarcza",ItemType.HAND,15, new Effect[]{new Effect(TargetCharacter.HERO,TargetField.DEFEND,5)},ItemUsage.PASSIVE, "assets/items/hand/shield-1.png"),
    Defence_Shield2("Dobra tarcza",ItemType.HAND,30, new Effect[]{new Effect(TargetCharacter.HERO,TargetField.DEFEND,10)},ItemUsage.PASSIVE, "assets/items/hand/shield-2.png"),
    Defence_Shield3("Ognista tarcza",ItemType.HAND,45, new Effect[]{new Effect(TargetCharacter.HERO,TargetField.DEFEND,15)},ItemUsage.PASSIVE, "assets/items/hand/shield-3.png"),
    Defence_Shield4("Złota tarcza",ItemType.HAND,60, new Effect[]{new Effect(TargetCharacter.HERO,TargetField.DEFEND,30)},ItemUsage.PASSIVE, "assets/items/hand/shield-4.png"),
    Defence_Shield5("Diamentowa tarcza",ItemType.HAND,90, new Effect[]{new Effect(TargetCharacter.HERO,TargetField.DEFEND,60)},ItemUsage.PASSIVE, "assets/items/hand/shield-5.png"),
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

