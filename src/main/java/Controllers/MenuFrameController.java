package Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class MenuFrameController {
    Stage stage;

    @FXML
    private Button btnOnePlayer;

    @FXML
    private Button btnTwoPlayers;

    @FXML
    void btnOnePlayerOnAction(ActionEvent event) {
        startGame(0);
    }

    @FXML
    void btnTwoPlayersOnAction(ActionEvent event) {
        startGame(1);
    }


    public void startGame(int type){  //0 - singleplayer, 1- multiplayer
        //temp:
        if(type==0) {

            try {
                stage.close();

                FXMLLoader loaderG = new FXMLLoader(getClass().getClassLoader().getResource("GUI.fxml"));
                GuiController gc = new GuiController();
                loaderG.setController(gc);

                Scene sceneG = new Scene(loaderG.load());
                Stage stageG = new Stage();
                stageG.setTitle("Go");
                stageG.setScene(sceneG);
                stageG.show();

            } catch (IOException ioe) {}
        }
        else if(type==1){
            try {
                stage.close();
/*
                FXMLLoader loaderG = new FXMLLoader(getClass().getClassLoader().getResource("WaitingFrame.fxml"));
                WaitingFrameController wfc = new WaitingFrameController();
                loaderG.setController(wfc);

                Scene sceneG = new Scene(loaderG.load());
                Stage stageG = new Stage();
                stageG.setTitle("Waiting");
                stageG.setScene(sceneG);
                stageG.show();
                wfc.setStage(stageG);

 */
                FXMLLoader loaderG = new FXMLLoader(getClass().getClassLoader().getResource("LobbyFrame.fxml"));
                LobbyFrameController lfc = new LobbyFrameController();
                loaderG.setController(lfc);

                Scene sceneG = new Scene(loaderG.load());
                Stage stageG = new Stage();
                stageG.setTitle("Go");
                stageG.setScene(sceneG);
                stageG.show();
                lfc.setStage(stageG);



            } catch (IOException ioe) {}
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
