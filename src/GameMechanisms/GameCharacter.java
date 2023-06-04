package GameMechanisms;

public abstract class GameCharacter {
    private int healthPoints;
    private int attackPoints;
    private int defendPoints;
    private double critChance;

    public GameCharacter(int healthPoints, int attackPoints, int defendPoints, double critChance) {
        this.healthPoints = healthPoints;
        this.attackPoints = attackPoints;
        this.defendPoints = defendPoints;
        this.critChance = critChance;
    }
}
