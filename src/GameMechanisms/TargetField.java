package GameMechanisms;

public enum TargetField {
    HEALTH("Zdrowie"),
    MANA("Magia"),
    CASH("Gotówka"),
    ATTACK("Atak"),
    DEFEND("Obrona"),
    POWEREDUP("Turbonaładowanie"),
    GUARDED("Tarcza"),
    HIT("Uderzenie"),
    CRITCHANCE("Szansa na obrażenia krytyczne"),
    ESCAPECHANCE("Szansa na ucieczkę");

    String text;

    TargetField(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
