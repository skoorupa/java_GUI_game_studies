import GameMechanisms.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class EnemyHandler {
    private static Enemy currentEnemy;
    private static ArrayList<Enemy> allEnemies;
    public static void initEnemies() {
        allEnemies = new ArrayList<>();
        allEnemies.add(new Enemy(40,10,5,10,"Wściekły lis",15,"assets/enemies/angry_fox.png"));
        allEnemies.add(new Enemy(50,20,5,30,"Świąteczne zombie",30,"assets/enemies/christmas_zombie.png"));
        allEnemies.add(new Enemy(60,20,10,25,"Szalony muppet",50,"assets/enemies/crazy_muppet.png"));
        allEnemies.add(new Enemy(100,30,20,40,"Wampir",75,"assets/enemies/vampir.png"));
    }

    public static Enemy pickEnemy() {
//        ArrayList<Enemy> allEnemies = new ArrayList<>();
//        allEnemies.add(new Enemy(50,10,10,0.10,"Wściekły lis",15,"assets/enemies/angry_fox.png"));
        if (!HeroHandler.getHero().isEscapeSuccess())
            return getCurrentEnemy();

        ArrayList<Enemy> availableEnemies = getAvailableEnemies();
        currentEnemy = availableEnemies.get(new Random().nextInt(0, availableEnemies.size()));
        return getCurrentEnemy();
    }

    public static ArrayList<Enemy> getAvailableEnemies() {
        return (ArrayList<Enemy>) allEnemies.stream().filter(enemy->!enemy.isDefeated()).collect(Collectors.toList());
    }

    public static boolean attackHero() {
        Hero hero = HeroHandler.getHero();
        boolean isCrit = getCurrentEnemy().getCritChance()/100>Math.random();
        int defendValue = hero.getDefendPoints();
        int damageValue = Math.max(getCurrentEnemy().getAttackPoints()-defendValue,5);
        if (isCrit) damageValue *= 0.5;

        if (hero.isGuarded()) {
            damageValue = 0;
            hero.setGuarded(false);
        }

        if (getCurrentEnemy().getHealthPoints()>0) {
            hero.getHit(damageValue);
            return true;
        } else return false;
    }

    public static Enemy getCurrentEnemy() {
        return currentEnemy;
    }
}
