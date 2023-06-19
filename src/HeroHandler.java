import GameMechanisms.*;

public class HeroHandler {
    private static Hero hero;
    public static void initHero() {
        hero = new Hero(100,0,0,0.05, "assets/ui/knight.png",0,0, 0.5, false,false);
        hero.getInventory().addItem(Item.Weapon_Sword1);
        hero.getInventory().addItem(Item.Food_Apple);
    }

    public static boolean attackEnemy() {
        Enemy enemy = EnemyHandler.getCurrentEnemy();
        if (enemy.getHealthPoints()<=0) return true;

        boolean isCrit = getHero().getCritChance()>Math.random();
        int defendValue = enemy.getDefendPoints();
        int damageValue = Math.max(getHero().getAttackPoints()-defendValue,5);
        if (isCrit) damageValue *= 0.5;

        enemy.getHit(damageValue);
        if (enemy.getHealthPoints()<=0) {
            getHero().setCash(getHero().getCash() + enemy.getPrize());
            return true;
        } else return false;
    }

    public static Hero getHero() {
        return hero;
    }
}
