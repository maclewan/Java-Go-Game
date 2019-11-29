package Controllers;

import clients.ClientPlayer;
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

        openWaitingFrame(false);
        //startGame();
        ClientPlayer client=new ClientPlayer();
        client.main();

    }

    @FXML
    void btnNewGameOnAction(ActionEvent event) throws IOException, ClassNotFoundException, InterruptedException {
        openWaitingFrame(true);
        Server myServer= new Server();
        myServer.main();
       // Client client1=new Client();

        //client1.main();
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
            wfc.setCreateServer(makeServer);
            stage.close();
        }
        catch(IOException | ClassNotFoundException e){}
    }
    public void startGame() {

            try {
                stage.close();

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

}
