import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private static MenuScene menuScene;
    private static WelcomeScene welcomeScene;
    private static CreditsScene creditsScene;
    private static FightScene fightScene;
    private static PreviewEnemyScene previewEnemyScene;

    @Override
    public void start(Stage stage) throws Exception {
        HeroHandler.initHero();
        EnemyHandler.initEnemies();

        menuScene = new MenuScene();
        welcomeScene = new WelcomeScene();
        creditsScene = new CreditsScene();

        menuScene.setCloseListener(stage::close);
        menuScene.setPlayListener(()->{
            stage.setScene(welcomeScene);
        });
        menuScene.setCreditsListener(()->{
            stage.setScene(creditsScene);
        });

        welcomeScene.addPreviewEnemyListener(()->{
            if (HeroHandler.getHero().isEscapeSuccess())
                EnemyHandler.pickEnemy();
            previewEnemyScene = new PreviewEnemyScene();
            stage.setScene(previewEnemyScene);

            previewEnemyScene.addEscapeListener(()->escapeAction(stage));

            previewEnemyScene.addFightListener(()->{
                fightScene = new FightScene();
                fightScene.addEscapeListener(()->escapeAction(stage));
                stage.setScene(fightScene);
            });
        });

        creditsScene.setCloseListener(()->stage.setScene(menuScene));

        stage.setTitle("Projekt");
        stage.setHeight(720);
        stage.setWidth(1080);
        stage.setScene(menuScene);
        stage.show();
    }

    public static void escapeAction(Stage stage) {
        HeroHandler.getHero().tryToEscape(EnemyHandler.getCurrentEnemy());
        if (HeroHandler.getHero().isEscapeSuccess()) {
            stage.setScene(welcomeScene);
        } else {
            stage.setScene(welcomeScene);
            System.out.println("nie udało się uciec");
        }
    }
}