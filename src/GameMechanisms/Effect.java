package GameMechanisms;

public class Effect {
    private TargetCharacter targetCharacter;
    private TargetField targetField;
    private int value;
}

enum TargetCharacter {
    HERO, ENEMY
}

enum TargetField {
    HEALTH, MANA, CASH, ATTACK, DEFEND, EXHAUSTED, FAMILIAR, CRITCHANCE
}


