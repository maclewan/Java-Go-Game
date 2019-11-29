package Controllers;

import clients.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import server.Server;

import java.io.IOException;

public class LobbyFrameController {
    Stage stage;

    @FXML
    private Button btnNewGame;

    @FXML
    private Button btnExistingGame;

    @FXML
    void btnExistingGameOnAction(ActionEvent event) throws IOException, ClassNotFoundException, InterruptedException {
        openWaitingFrame();
        Client client2=new Client();
        client2.main();
    }

    @FXML
    void btnNewGameOnAction(ActionEvent event) throws IOException, ClassNotFoundException, InterruptedException {
        openWaitingFrame();
        String ar[]=null;
        Server myServer= new Server();
        Client client1=new Client();
        myServer.main();
        client1.main();
    }


    void openWaitingFrame() {
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
            stage.close();
        }
        catch(IOException e){}
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
