package Controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WaitingFrameController{
    private Stage stage= new Stage();








    @FXML
    void initialize() throws IOException, ClassNotFoundException {


        ClientController clientController = new ClientController();

        Thread observer = new Observer(this);
        observer.start();

    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public synchronized void startGame(ClientController clientController){


        //new game
        try {
            FXMLLoader loaderG = new FXMLLoader(getClass().getClassLoader().getResource("GUI.fxml"));
            ClientController gc = clientController;
            loaderG.setController(gc);
            gc.setStage(stage);

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

    }

