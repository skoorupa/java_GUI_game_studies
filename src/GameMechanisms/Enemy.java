package GameMechanisms;

public class Enemy extends GameCharacter {
    private String name;
    private int prize;

    public Enemy(int healthPoints, int attackPoints, int defendPoints, double critChance, String name, int prize, String assetPath) {
        super(healthPoints, attackPoints, defendPoints, critChance, assetPath);
        this.name = name;
        this.prize = prize;
    }

    public String getName() {
        return name;
    }

    public int getPrize() {
        return prize;
    }
}
