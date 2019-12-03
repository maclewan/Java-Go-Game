package Controllers;

import clients.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.text.html.StyleSheet;
import java.io.IOException;

public class WaitingFrameController {
    Stage stage;




   @FXML
   void initialize(){
    try {

        Client client2 = new Client();

        client2.main();
        startGame();

    }
    catch (InterruptedException e) {
        e.printStackTrace();

    }
    catch (IOException e) {
        System.out.println("Brak serwera!");
        textWaiting.setText("Brak aktywnego serwera");
        textWaiting.setLayoutX(textWaiting.getLayoutX() + 40);
        //stage.close();
    }

    catch (ClassNotFoundException e) {
        e.printStackTrace();
    }

   }

   @FXML
   private Button goBack;

   @FXML
   private Pane board;

   @FXML
   private Label textWaiting;

   @FXML
   void goBackOnAction(ActionEvent event) {
       try {
           FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("MenuFrame.fxml"));
           MenuFrameController mfc = new MenuFrameController();
           loader.setController(mfc);
           mfc.setStage(stage);

           Scene scene = new Scene(loader.load());
           stage.setTitle("Menu");
           stage.setScene(scene);
           stage.setX(stage.getX()+150);

       } catch (IOException ioe) {
           ioe.printStackTrace();
       }

   }

    public void startGame() {
        try {


            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("GUI.fxml"));
            GuiController gc = new GuiController();
            gc.isBlack=true;
            loader.setController(gc);

            Scene scene = new Scene(loader.load());
            stage.setTitle("Go");
            stage.setScene(scene);
            stage.show();
            stage.setX(stage.getX()-200);
            stage.setY(stage.getY()-100);

        } catch (IOException ioe) {}
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
