package GameMechanisms;

public class Hero extends GameCharacter {
    private int manaPoints;
    private int cash;
    private boolean exhausted;
    private boolean familiar;

    public Hero(int healthPoints, int attackPoints, int defendPoints, double critChance, int manaPoints, int cash, boolean exhausted, boolean familiar) {
        super(healthPoints, attackPoints, defendPoints, critChance);
        this.manaPoints = manaPoints;
        this.cash = cash;
        this.exhausted = exhausted;
        this.familiar = familiar;
    }
}
