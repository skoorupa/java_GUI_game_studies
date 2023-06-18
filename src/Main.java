import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private MenuScene menuScene;
    private WelcomeScene welcomeScene;
    private CreditsScene creditsScene;
    private PreviewEnemyScene previewEnemyScene;

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
            EnemyHandler.pickEnemy();
            previewEnemyScene = new PreviewEnemyScene();
            stage.setScene(previewEnemyScene);
        });

        creditsScene.setCloseListener(()->showMenuScene(stage));

        stage.setTitle("Projekt");
        stage.setHeight(720);
        stage.setWidth(1080);
        showMenuScene(stage);
        stage.show();
    }

    public void showMenuScene(Stage stage) {
        stage.setScene(menuScene);
    }
}