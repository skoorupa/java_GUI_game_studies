package GameMechanisms;

public class Effect {
    private TargetCharacter targetCharacter;
    private TargetField targetField;
    private int value;

    public Effect(TargetCharacter targetCharacter, TargetField targetField, int value) {
        this.targetCharacter = targetCharacter;
        this.targetField = targetField;
        this.value = value;
    }

    public TargetCharacter getTargetCharacter() {
        return targetCharacter;
    }

    public TargetField getTargetField() {
        return targetField;
    }

    public int getValue() {
        return value;
    }
}

enum TargetCharacter {
    HERO, ENEMY
}


