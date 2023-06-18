import GameMechanisms.Enemy;

import java.util.ArrayList;
import java.util.Random;

public class EnemyHandler {
    private static Enemy currentEnemy;
    private static ArrayList<Enemy> allEnemies;
    public static void initEnemies() {
        allEnemies = new ArrayList<>();
        allEnemies.add(new Enemy(50,10,10,0.10,"Wściekły lis",15,"assets/enemies/angry_fox.png"));
    }

    public static Enemy pickEnemy() {
        currentEnemy = allEnemies.get(new Random().nextInt(0, allEnemies.size()));
        return getCurrentEnemy();
    }

    public static Enemy getCurrentEnemy() {
        return currentEnemy;
    }
}