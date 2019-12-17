package Controllers;


import Observers.Observer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class WaitingFrameController{
    private Stage stage= new Stage();




    @FXML
    void initialize() {

        Thread observer = new Observer(this);
        observer.start();
    }

    @FXML
    private Label textWaiting;


    void setStage(Stage stage) {
        this.stage = stage;
    }


    public synchronized void startGame(ClientController clientController){


        /**
         * New game
         */

        try {
            FXMLLoader loaderG = new FXMLLoader(getClass().getClassLoader().getResource("GUI.fxml"));
            loaderG.setController(clientController);
            clientController.setStage(stage);

            Scene sceneG = new Scene(loaderG.load());

            stage.setTitle("Go");
            stage.setScene(sceneG);
            stage.show();
            stage.setMinWidth(1200);
            stage.setMinHeight(800);
            stage.setX(stage.getX()-250);
            stage.setY(stage.getY()-100);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void absentServer(){
        textWaiting.setText("Brak aktywnego serwera");
        textWaiting.setLayoutX(textWaiting.getLayoutX()+40);
    }

    public synchronized void backToMenu(){
        try {
            FXMLLoader loaderG = new FXMLLoader(getClass().getClassLoader().getResource("MenuFrame.fxml"));
            MenuFrameController mfc = new MenuFrameController();
            loaderG.setController(mfc);
            mfc.setStage(stage);

            Scene sceneG = new Scene(loaderG.load());

            stage.setTitle("Menu");
            stage.setScene(sceneG);
            stage.show();
            stage.setX(stage.getX()+100);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    }

