package Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class MenuFrameController {
    private Stage stage;

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

                FXMLLoader loaderG = new FXMLLoader(getClass().getClassLoader().getResource("GUI.fxml"));
                //GuiController gc = new GuiController();
               // loaderG.setController(gc);

                Scene sceneG = new Scene(loaderG.load());

                stage.setTitle("Go");
                stage.setScene(sceneG);
                stage.setX(stage.getX()-350);
                stage.setY(stage.getY()-100);
               // gc.setStage(stage);

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        else if(type==1){
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("WaitingFrame.fxml"));
                WaitingFrameController wfc = new WaitingFrameController();
                loader.setController(wfc);

                Scene sceneG = new Scene(loader.load());

                stage.setTitle("Waiting");
                stage.setScene(sceneG);
                stage.setX(stage.getX()-100);
                wfc.setStage(stage);




            } catch (IOException ioe) {}
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;

    }
}