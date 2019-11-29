package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LobbyFrameController {
    Stage stage;

    @FXML
    private Button btnNewGame;

    @FXML
    private Button btnExistingGame;

    @FXML
    void btnExistingGameOnAction(ActionEvent event) {
        //wyślij cos do serwera
        openWaitingFrame();
    }

    @FXML
    void btnNewGameOnAction(ActionEvent event) {
        //wyślij cos do serwera
        openWaitingFrame();

    }


    void openWaitingFrame() {
        try {
            stage.close();
            FXMLLoader loaderG = new FXMLLoader(getClass().getClassLoader().getResource("WaitingFrame.fxml"));
            WaitingFrameController wfc = new WaitingFrameController();
            loaderG.setController(wfc);

            Scene sceneG = new Scene(loaderG.load());
            Stage stageG = new Stage();
            stageG.setTitle("Waiting");
            stageG.setScene(sceneG);
            stageG.show();
            wfc.setStage(stageG);
        }
        catch(IOException e){}
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
