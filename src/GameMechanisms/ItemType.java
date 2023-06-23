package GameMechanisms;

public enum ItemType {
    ARMOR(1,"ZBROJA"), HAND(2,"BROŃ"), BACKPACK(30,"DO PLECAKA"), SPECIAL(1,"SPECJALNY");

    private final int slots;
    private final String text;

    ItemType(int s, String text) {
        this.slots = s;
        this.text = text;
    }

    public int getSlots() {
        return slots;
    }

    public String getText() {
        return text;
    }
}
