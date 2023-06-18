import GameMechanisms.*;

public class HeroHandler {
    private static Hero hero;
    public static void initHero() {
        hero = new Hero(100,0,0,0.05, "assets/ui/knight.png",0,0, 0.5, false,false);
        hero.getInventory().addItem(Item.Weapon_Sword1);
        hero.getInventory().addItem(Item.Food_Apple);
//        System.out.println(hero.getInventory().values());
    }

    public static Hero getHero() {
        return hero;
    }
}
