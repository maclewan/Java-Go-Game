package Controllers;

import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;

public class WaitingFrameController {
    Stage stage;
    boolean createServer=false;



    @FXML
    void initialize() throws IOException, ClassNotFoundException {

    }

    public void setCreateServer(boolean createServer) throws IOException, ClassNotFoundException {
        this.createServer = createServer;

    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
