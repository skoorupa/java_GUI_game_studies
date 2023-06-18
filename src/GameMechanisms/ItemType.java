package GameMechanisms;

public enum ItemType {
    ARMOR(1), HAND(2), BACKPACK(30), SPECIAL(1);

    private final int slots;

    ItemType(int s) {
        this.slots = s;
    }

    public int getSlots() {
        return slots;
    }
}
