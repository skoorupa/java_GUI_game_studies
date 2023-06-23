package GameMechanisms;

public enum ItemUsage {
    PASSIVE("PASYWNY"),
    ACTIVE("AKTYWNY"),
    USEABLE("ZUŻYWALNY");

    private final String text;

    ItemUsage(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
