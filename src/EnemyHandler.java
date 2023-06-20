import GameMechanisms.*;

import java.util.ArrayList;
import java.util.Random;

public class EnemyHandler {
    private static Enemy currentEnemy;
    private static ArrayList<Enemy> allEnemies;
    public static void initEnemies() {
        allEnemies = new ArrayList<>();
        allEnemies.add(new Enemy(50,10,5,10,"Wściekły lis",15,"assets/enemies/angry_fox.png"));
    }

    public static Enemy pickEnemy() {
//        ArrayList<Enemy> allEnemies = new ArrayList<>();
//        allEnemies.add(new Enemy(50,10,10,0.10,"Wściekły lis",15,"assets/enemies/angry_fox.png"));

        currentEnemy = allEnemies.get(new Random().nextInt(0, allEnemies.size()));
        return getCurrentEnemy();
    }

    public static boolean attackHero() {
        Hero hero = HeroHandler.getHero();
        boolean isCrit = getCurrentEnemy().getCritChance()/100>Math.random();
        int defendValue = hero.getDefendPoints();
        int damageValue = Math.max(getCurrentEnemy().getAttackPoints()-defendValue,5);
        if (isCrit) damageValue *= 0.5;

        if (getCurrentEnemy().getHealthPoints()>0) {
            hero.getHit(damageValue);
            return true;
        } else return false;
    }

    public static Enemy getCurrentEnemy() {
        return currentEnemy;
    }
}
