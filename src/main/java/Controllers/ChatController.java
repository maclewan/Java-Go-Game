package Controllers;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
        import javafx.scene.control.Button;
        import javafx.scene.control.TextArea;
        import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChatController {

    private ChatObserver co;
    private Stage stage;
    private String lastMessage;


    @FXML
    public void initialize(){
        System.out.println("Inicjalizuje sie");
        labelChatText.textProperty().addListener(new ChangeListener<Object>() {  /**Action Listener zrzucający na sam dół tektu w chacie po dodaniu wiadomości*/
       @Override
        public void changed(ObservableValue<?> observable, Object oldValue,
                            Object newValue) {
            Platform.runLater(() ->  {
                labelChatText.selectEnd();
                labelChatText.deselect();
            });

        }
        });

    }

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
        co.setMesOut("Wznawiam gre!");


    }

    @FXML
    void endGameOnAction(ActionEvent event) {
        co.endGame();
    }


    @FXML
    void sendOnAction(ActionEvent event) {
        if(textFldType.getText().equals(lastMessage)||textFldType.getText().equals(""))
            return;
        co.setMesOut(textFldType.getText());
        lastMessage=textFldType.getText();
        textFldType.setText("");
    }

    public void setCo(ChatObserver co) {
        this.co = co;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void addLabelChatText(String text) {
        labelChatText.setText(labelChatText.getText()+"\n"+text);

    }


    public void closeChat(){
        Platform.runLater(() ->  {
            stage.close();
        });

    }



}

