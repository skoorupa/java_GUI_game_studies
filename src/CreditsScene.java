import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

public class CreditsScene extends Scene {
    private BorderPane root;
    private TextArea creditsTextArea;
    private Button closeButton;
    private CloseListener closeListener;

    public CreditsScene() {
        super(new BorderPane());
        root = (BorderPane) getRoot();

        // INIT
        creditsTextArea = new TextArea();
        creditsTextArea.setEditable(false);
        creditsTextArea.setText(creditsText());
        closeButton = new Button("← Wróć do menu");

        // CLOSEBUTTON

        closeButton.setPrefHeight(40);
        closeButton.setFont(Font.font("Arial", FontPosture.REGULAR, 25));
        closeButton.setOnAction((e)->closeListener.onClose());

        // LAYOUT

        root.setCenter(creditsTextArea);
        root.setBottom(closeButton);
    }

    public void setCloseListener(CloseListener closeListener) {
        this.closeListener = closeListener;
    }

    public String creditsText() {
        StringBuilder sb = new StringBuilder();

        sb.append("Icons: game-icons.txt\n");
        sb.append("Forest background: Image by upklyak on Freepik\n");
        sb.append("Knight: Image by rawpixel.com on Freepik\n");

        return sb.toString();
    }
}
