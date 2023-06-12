import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MenuScene extends Scene {
    private BorderPane root;
    private FlowPane titlePane;
    private VBox buttonPane;
    private Label title;
    private Button start,exit;
    private Region spacer;

    private CloseListener closeListener;

    public MenuScene(BorderPane root) {
        super(root);
        this.root = root;

        // INIT
        titlePane = new FlowPane();
        buttonPane = new VBox();

        // TITLE
        title = new Label("PROJEKT GUI");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        title.setTextFill(Color.BLACK);

        titlePane.setAlignment(Pos.TOP_CENTER);
        titlePane.getChildren().addAll(title);

        // BUTTONS
        start = new Button("Zacznij");
        exit = new Button("Wyjdź");

        exit.setOnAction(event -> {
            if (closeListener != null) {
                closeListener.onClose();
            }
        });

        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.getChildren().addAll(start,exit);
        buttonPane.getChildren().forEach(node->{
            Button button = (Button) node;
            button.setPrefHeight(40);
            button.setPrefWidth(200);
            button.setFont(Font.font("Arial", FontPosture.REGULAR, 25));
        });

        // LAYOUT

        spacer = new Region();
        spacer.setPrefHeight(100);

        root.setPadding(new Insets(50));
        root.setTop(titlePane);
        root.setCenter(spacer);
        root.setBottom(buttonPane);
    }

    public void setCloseListener(CloseListener closeListener) {
        this.closeListener = closeListener;
    }
}

interface CloseListener {
    void onClose();
}
