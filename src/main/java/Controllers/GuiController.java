package Controllers;

/**
 * Sample Skeleton for 'GUI.fxml' Controller Class
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class GuiController {

    @FXML // fx:id="board"
    private Pane board; // Value injected by FXMLLoader

    @FXML // fx:id="passBlack"
    private Button passBlack; // Value injected by FXMLLoader

    @FXML // fx:id="passWhite"
    private Button passWhite; // Value injected by FXMLLoader

    @FXML // fx:id="pointsBlac"
    private Label pointsBlac; // Value injected by FXMLLoader

    @FXML // fx:id="pointsWhite"
    private Label pointsWhite; // Value injected by FXMLLoader

    @FXML
    void boardClicked(MouseEvent e) {
        int a, b,x,y,diffX,diffY;
        double diffR;

        x=(int)e.getX();
        y=(int)e.getY();
        a=(int) (e.getX())/40;
        b=(int) (e.getY())/40;
        diffX=Math.abs(x-(a*40+20));
        diffY=Math.abs(y-(b*40+20));
        diffR=Math.sqrt(diffX*diffX+diffY*diffY);

        if(diffR>15){
            System.out.println("null");   //clicked out of any points range
        }
        else
            System.out.println(a+","+b);  //clicked in range of point
    }

    @FXML
    void passBlackOnAction(ActionEvent event) {

    }

    @FXML
    void passWhiteOnAction(ActionEvent event) {

    }

}
