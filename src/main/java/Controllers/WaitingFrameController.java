package Controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WaitingFrameController{
    Stage stage= new Stage();








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

        stage.close();
        //new game
        try {
            FXMLLoader loaderG = new FXMLLoader(getClass().getClassLoader().getResource("GUI.fxml"));
            ClientController gc = clientController;
            loaderG.setController(gc);
            Scene sceneG = new Scene(loaderG.load());
            Stage stageG = new Stage();
            stageG.setTitle("Go");
            stageG.setScene(sceneG);
            stageG.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }

    }

