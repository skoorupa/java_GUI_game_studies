package GameMechanisms;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class GameCharacter {
    private IntegerProperty healthPoints, attackPoints, defendPoints;
    private IntegerProperty maxHealthPoints;
    private int basicAttackPoints, basicDefendPoints;
    private DoubleProperty critChance;
    private double basicCritChance;
    private String assetPath;

    public GameCharacter(int healthPoints, int attackPoints, int defendPoints, double critChance, String assetPath) {
        this.maxHealthPoints = new SimpleIntegerProperty(healthPoints);
        this.healthPoints = new SimpleIntegerProperty(healthPoints);
        this.basicAttackPoints = attackPoints;
        this.attackPoints = new SimpleIntegerProperty(attackPoints);
        this.basicDefendPoints = defendPoints;
        this.defendPoints = new SimpleIntegerProperty(defendPoints);
        this.basicCritChance = critChance;
        this.critChance = new SimpleDoubleProperty(critChance);
        this.assetPath = assetPath;
    }

//    public void setHealthPoints(int healthPoints) {
//        this.healthPoints.set(healthPoints);
//    }

    // EFFECT METHODS

    public void acceptHealthEffect(Effect effect) {
        healthPointsProperty().set(
                Math.max(getHealthPoints() + effect.getValue(),0)
        );
    }

    public void acceptHitEffect(Effect effect) {
        healthPointsProperty().set(
                Math.max(getHealthPoints() - effect.getValue(),0)
        );
    }

    public void acceptAttackEffect(Effect effect) {
        attackPointsProperty().set(
                Math.max(getAttackPoints() + effect.getValue(),0)
        );
    }

    public void acceptDefendEffect(Effect effect) {
        defendPointsProperty().set(
                Math.max(getDefendPoints() + effect.getValue(),0)
        );
    }

    public void acceptCritChanceEffect(Effect effect) {
        critChanceProperty().set(
                Math.max(getCritChance() + (double) effect.getValue() / 100,0)
        );
    }

    // GETTERS AND SETTERS

    public boolean isAlive() {
        return getHealthPoints()>0;
    }
    public boolean isDead() {
        return getHealthPoints()<=0;
    }

    public String getAssetPath() {
        return assetPath;
    }

    public int getBasicAttackPoints() {
        return basicAttackPoints;
    }

    public int getBasicDefendPoints() {
        return basicDefendPoints;
    }

    public double getBasicCritChance() {
        return basicCritChance;
    }

    public int getHealthPoints() {
        return healthPoints.get();
    }

    public IntegerProperty healthPointsProperty() {
        return healthPoints;
    }

    public int getAttackPoints() {
        return attackPoints.get();
    }

    public IntegerProperty attackPointsProperty() {
        return attackPoints;
    }

    public int getDefendPoints() {
        return defendPoints.get();
    }

    public IntegerProperty defendPointsProperty() {
        return defendPoints;
    }

    public double getCritChance() {
        return critChance.get();
    }

    public DoubleProperty critChanceProperty() {
        return critChance;
    }
}
