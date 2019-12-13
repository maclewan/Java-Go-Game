package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
        import javafx.scene.control.Button;
        import javafx.scene.control.TextArea;
        import javafx.scene.control.TextField;

public class ChatController {

    ChatObserver co;

    @FXML
    private TextArea labelChatText;

    @FXML
    private TextField textFldType;

    @FXML
    private Button btnSend;

    @FXML
    private Button btnContinue;

    @FXML
    private Button btnEndGame;

    @FXML
    void continueOnAction(ActionEvent event) {
        //todo: maciej zmykanie okna
        co.continueGame();
    }

    @FXML
    void endGameOnAction(ActionEvent event) {
        co.endGame();

    }

    @FXML
    void sendOnAction(ActionEvent event) {
        co.setMesOut(textFldType.getText());
    }

    public void setCo(ChatObserver co) {
        this.co = co;
    }
}

