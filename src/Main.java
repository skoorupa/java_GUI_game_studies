import GameMechanisms.*;
import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    MenuScene MenuScene;

    @Override
    public void start(Stage stage) throws Exception {
        MenuScene menuScene = new MenuScene(new BorderPane());
        menuScene.setCloseListener(stage::close);

        stage.setScene(menuScene);
        stage.setTitle("Projekt");
        stage.show();
    }
}