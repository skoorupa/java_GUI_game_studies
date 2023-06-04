package GameMechanisms;

public class Enemy extends GameCharacter {
    private String name;
    private int prize;

    public Enemy(int healthPoints, int attackPoints, int defendPoints, double critChance, String name, int prize) {
        super(healthPoints, attackPoints, defendPoints, critChance);
        this.name = name;
        this.prize = prize;
    }
}
