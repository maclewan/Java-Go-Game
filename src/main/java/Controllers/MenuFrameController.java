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
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("GUI.fxml"));
                GuiController gc = new GuiController();
                /**
                 * Docelowo nie GuiController tylko Clientcontroller z 2. graczem jako z botem                 *
                 */
                loader.setController(gc);
                gc.setStage(stage);

                Scene scene = new Scene(loader.load());
                stage.setTitle("Go");
                stage.setScene(scene);
                stage.setX(stage.getX()-350);
                stage.setY(stage.getY()-100);

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        else if(type==1){
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("WaitingFrame.fxml"));
                WaitingFrameController wfc = new WaitingFrameController();
                loader.setController(wfc);
                wfc.setStage(stage);

                Scene scene = new Scene(loader.load());
                stage.setTitle("Waiting");
                stage.setScene(scene);
                stage.setX(stage.getX()-150);


            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;

    }
}
