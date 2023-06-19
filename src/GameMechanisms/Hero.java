package GameMechanisms;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Hero extends GameCharacter {
    private IntegerProperty manaPoints, cash;
    private DoubleProperty escapeChance;
    private BooleanProperty exhausted, alive, guarded;
    private Inventory inventory;
    private boolean escapeSuccess;

    public Hero(int healthPoints, int attackPoints, int defendPoints, double critChance, String assetPath, int manaPoints, int cash, double escapeChance, boolean exhausted, boolean guarded) {
        super(healthPoints, attackPoints, defendPoints, critChance, assetPath);
        this.manaPoints = new SimpleIntegerProperty(manaPoints);
        this.cash = new SimpleIntegerProperty(cash);
        this.escapeChance = new SimpleDoubleProperty(escapeChance);
        this.exhausted = new SimpleBooleanProperty(exhausted);
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
        for (Item item : inventory.getItemList()) {
            for (Effect effect : item.getEffects()) {
                if (effect.getTargetField()==targetField)
                    sum+=effect.getValue();
            }
        }
        attackPoints.set(sum);
    }
    private void recalculateAttackStat(DoubleProperty attackPoints, Inventory inventory, TargetField targetField, double basicPoints) {
        double sum = basicPoints;
        for (Item item : inventory.getItemList()) {
            for (Effect effect : item.getEffects()) {
                if (effect.getTargetField()==targetField)
                    sum+=effect.getValue();
            }
        }
        attackPoints.set(sum);
    }

    public void getHit(int damage) {
        if (getHealthPoints() > damage)
            healthPointsProperty().set(
                    getHealthPoints()-damage
            );
        else
            healthPointsProperty().set(0);
    }

    public boolean tryToEscape(Enemy enemy) {
        double random = Math.random();
        escapeSuccess = getEscapeChance() >= random;
        return escapeSuccess;
    }

    public boolean isEscapeSuccess() {
        return escapeSuccess;
    }

    public double getEscapeChance() {
        return escapeChance.get();
    }

    public DoubleProperty escapeChanceProperty() {
        return escapeChance;
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

    public boolean isExhausted() {
        return exhausted.get();
    }

    public BooleanProperty exhaustedProperty() {
        return exhausted;
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

    public BooleanProperty guardedProperty() {
        return guarded;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
