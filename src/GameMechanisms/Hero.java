package GameMechanisms;

import javafx.beans.property.*;

public class Hero extends GameCharacter {
    private IntegerProperty manaPoints, cash;
    private DoubleProperty escapeChance;
    private BooleanProperty poweredup, alive, guarded;
    private Inventory inventory;
    private boolean escapeSuccess;

    public Hero(int healthPoints, int attackPoints, int defendPoints, double critChance, String assetPath, int manaPoints, int cash, double escapeChance, boolean poweredup, boolean guarded) {
        super(healthPoints, attackPoints, defendPoints, critChance, assetPath);
        this.manaPoints = new SimpleIntegerProperty(manaPoints);
        this.cash = new SimpleIntegerProperty(cash);
        this.escapeChance = new SimpleDoubleProperty(escapeChance);
        this.poweredup = new SimpleBooleanProperty(poweredup);
        this.guarded = new SimpleBooleanProperty(guarded);
        this.alive = new SimpleBooleanProperty(true);

        this.escapeSuccess = true;

        this.inventory = new Inventory();
        this.inventory.addChangeListener(() -> {
            recalculateAttackStat(attackPointsProperty(),inventory,TargetField.ATTACK,getBasicAttackPoints());
            recalculateAttackStat(defendPointsProperty(),inventory,TargetField.DEFEND,getBasicDefendPoints());
            recalculateAttackStat(critChanceProperty(),inventory,TargetField.CRITCHANCE,getBasicCritChance());
        });
    }

    private void recalculateAttackStat(IntegerProperty attackPoints, Inventory inventory, TargetField targetField, int basicPoints) {
        int sum = basicPoints;
        for (Item item : inventory.getPassiveItemList()) {
            for (Effect effect : item.getEffects()) {
                if (effect.getTargetField()==targetField)
                    sum+=effect.getValue();
            }
        }
        attackPoints.set(sum);
    }
    private void recalculateAttackStat(DoubleProperty attackPoints, Inventory inventory, TargetField targetField, double basicPoints) {
        double sum = basicPoints;
        for (Item item : inventory.getPassiveItemList()) {
            for (Effect effect : item.getEffects()) {
                if (effect.getTargetField()==targetField)
                    sum+=effect.getValue();
            }
        }
        attackPoints.set(sum);
    }

    public void getHit(int damage) {
        healthPointsProperty().set(
                Math.max(getHealthPoints()-damage, 0)
        );
    }

    // EFFECT METHODS

    public void acceptManaEffect(Effect effect) {
        manaPointsProperty().set(
                getManaPoints() + effect.getValue()
        );
    }
    public boolean tryManaEffect(Effect effect) {
        if (getManaPoints() + effect.getValue() < 0) return false;
        else return true;
    }

    public void acceptCashEffect(Effect effect) {
        cashProperty().set(
                getCash() + effect.getValue()
        );
    }

    public void acceptExhaustedEffect(Effect effect) {
        poweredupProperty().set(
                effect.getValue() > 0
        );
    }

    public void acceptGuardedEffect(Effect effect) {
        guardedProperty().set(
                effect.getValue() > 0
        );
    }

    public void acceptEscapeChanceEffect(Effect effect) {
        escapeChanceProperty().set(
                getEscapeChance() + (double) effect.getValue() / 100
        );
    }

    // GETTERS AND SETTERS

    public boolean isEscapeSuccess() {
        return escapeSuccess;
    }

    public double getEscapeChance() {
        return escapeChance.get();
    }

    public DoubleProperty escapeChanceProperty() {
        return escapeChance;
    }

    public void setEscapeSuccess(boolean escapeSuccess) {
        this.escapeSuccess = escapeSuccess;
    }

    public int getManaPoints() {
        return manaPoints.get();
    }

    public IntegerProperty manaPointsProperty() {
        return manaPoints;
    }

    public int getCash() {
        return cash.get();
    }

    public void setCash(int cash) {
        this.cash.set(cash);
    }

    public IntegerProperty cashProperty() {
        return cash;
    }

    public boolean getPoweredup() {
        return poweredup.get();
    }

    public void setPoweredup(boolean poweredup) {
        this.poweredup.set(poweredup);
    }

    public BooleanProperty poweredupProperty() {
        return poweredup;
    }

    public boolean isAlive() {
        return alive.get();
    }

    public BooleanProperty aliveProperty() {
        return alive;
    }

    public boolean isGuarded() {
        return guarded.get();
    }

    public void setManaPoints(int manaPoints) {
        this.manaPoints.set(manaPoints);
    }

    public void setEscapeChance(double escapeChance) {
        this.escapeChance.set(escapeChance);
    }

    public void setGuarded(boolean guarded) {
        this.guarded.set(guarded);
    }

    public BooleanProperty guardedProperty() {
        return guarded;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
