package GameMechanisms;

public enum Item {
    Food_Apple("Jabłko",ItemType.BACKPACK,2, new Effect[]{new Effect(TargetCharacter.HERO,TargetField.HEALTH,10)},false, "assets/items/backpack/apple.png"),
    Weapon_Sword1("Brązowy miecz",ItemType.HAND,2, new Effect[]{new Effect(TargetCharacter.ENEMY,TargetField.ATTACK,15)},true, "assets/items/hand/sword-1.png"),
    Weapon_Sword2("Srebrny miecz",ItemType.HAND,4, new Effect[]{new Effect(TargetCharacter.ENEMY,TargetField.ATTACK,25)},true, "assets/items/hand/sword-2.png");

    private String name, imagePath;
    private ItemType type;
    private int cost;
    private Effect[] effects;
    private boolean isReusable;

    Item(String name, ItemType type, int cost, Effect[] effects, boolean isReusable, String imagePath) {
        this.name = name;
        this.type = type;
        this.cost = cost;
        this.effects = effects;
        this.isReusable = isReusable;
        this.imagePath = imagePath;
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

    public boolean isReusable() {
        return isReusable;
    }

    @Override
    public String toString() {
        return getName();
    }
}

