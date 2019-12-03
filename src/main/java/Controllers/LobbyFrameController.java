package Controllers;

import clients.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LobbyFrameController {

    /*
    Stage stage;
    @FXML
    private Button btnNewGame;

    @FXML
    private Button btnExistingGame;

    @FXML
    void btnExistingGameOnAction(ActionEvent event) throws IOException, ClassNotFoundException, InterruptedException {




        Client client2=new Client();
        client2.main();
        startGame();

    }

    @FXML
    void btnNewGameOnAction(ActionEvent event) throws IOException, ClassNotFoundException, InterruptedException {

    }


    void openWaitingFrame(boolean makeServer) {
        try {

            FXMLLoader loaderG = new FXMLLoader(getClass().getClassLoader().getResource("WaitingFrame.fxml"));
            WaitingFrameController wfc = new WaitingFrameController();
            loaderG.setController(wfc);

            Scene sceneG = new Scene(loaderG.load());
            Stage stageG = new Stage();
            stageG.setTitle("Waiting");
            stageG.setScene(sceneG);
            stageG.show();
            wfc.setStage(stageG);
            //wfc.setCreateServer(makeServer);
            stage.close();
        }
        catch(IOException | ClassNotFoundException e){}
    }
    /*
    public void startGame() {

            try {


                FXMLLoader loaderG = new FXMLLoader(getClass().getClassLoader().getResource("GUI.fxml"));
                GuiController gc = new GuiController();
                gc.isBlack=true;
                loaderG.setController(gc);

                Scene sceneG = new Scene(loaderG.load());
                Stage stageG = new Stage();
                stageG.setTitle("Go");
                stageG.setScene(sceneG);
                stageG.show();

            } catch (IOException ioe) {
            }

    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }
*/
}
