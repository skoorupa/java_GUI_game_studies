import GameMechanisms.*;

public class HeroHandler {
    private static Hero hero;
    public static void initHero() {
        hero = new Hero(100,5,0,5, "assets/ui/knight.png",0,0, 50, false,false);
        hero.getInventory().addItem(Item.Weapon_Sword1);
        hero.getInventory().addItem(Item.Food_Apple);
    }

    public static boolean attackEnemy() {
        Enemy enemy = EnemyHandler.getCurrentEnemy();
        if (enemy.getHealthPoints()<=0) return true;

        boolean isCrit = getHero().getCritChance()/100>Math.random();
        int defendValue = enemy.getDefendPoints();
        int damageValue = Math.max(getHero().getAttackPoints()-defendValue,5);
        if (isCrit) damageValue *= 1.5;

        enemy.getHit(damageValue);
        if (enemy.getHealthPoints()<=0) {
            enemy.setDefeated();
            getHero().setCash(getHero().getCash() + enemy.getPrize());
            return true;
        } else return false;
    }

    public static void useItem(Item item) {
        for (Effect effect : item.getEffects()) {
            GameCharacter targetCharacter;

            if (effect.getTargetCharacter() == TargetCharacter.HERO)
                targetCharacter = getHero();
            else
                targetCharacter = EnemyHandler.getCurrentEnemy();

            switch (effect.getTargetField()) {
                case HEALTH ->
                        targetCharacter.acceptHealthEffect(effect);
                case MANA -> // tylko HERO
                        getHero().acceptManaEffect(effect);
                case CASH -> // tylko HERO
                        getHero().acceptCashEffect(effect);
                case ATTACK ->
                        targetCharacter.acceptAttackEffect(effect);
                case DEFEND ->
                        targetCharacter.acceptDefendEffect(effect);
                case EXHAUSTED -> // tylko HERO
                        getHero().acceptExhaustedEffect(effect);
                case GUARDED -> // tylko HERO
                        getHero().acceptGuardedEffect(effect);
                case CRITCHANCE ->
                        targetCharacter.acceptCritChanceEffect(effect);
                case ESCAPECHANCE -> // tylko HERO
                        getHero().acceptEscapeChanceEffect(effect);
            }
        }

        if (item.getItemUsage() == ItemUsage.USEABLE)
            getHero().getInventory().removeItem(item);
    }

    public static boolean tryToEscape(Enemy enemy) {
        double random = Math.random();
        getHero().setEscapeSuccess(getHero().getEscapeChance()/100 >= random);
        return getHero().isEscapeSuccess();
    }

    public static Hero getHero() {
        return hero;
    }
}
