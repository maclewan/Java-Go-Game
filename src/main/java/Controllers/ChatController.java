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

    ChatObserver co;
    Stage stage;


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
        addLabelChatText("Wznawiam gre");

        backToGame();


    }

    @FXML
    void endGameOnAction(ActionEvent event) {
        co.endGame();
    }

    @FXML
    void sendOnAction(ActionEvent event) {
        labelChatText.setText(labelChatText.getText()+"\n"+"Ja:\t"+textFldType.getText());
        co.setMesOut(textFldType.getText());
    }

    public void setCo(ChatObserver co) {
        this.co = co;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void addLabelChatText(String text) {
        if(text.equals("Wznawiam gre!")){
            backToGame();
        }
        labelChatText.setText(labelChatText.getText()+"\n"+"Oponent:\t"+text);

    }

    public void backToGame(){

        co.continueGame();
        stage.close();

    }



}

