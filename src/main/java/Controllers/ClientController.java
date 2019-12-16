package Controllers;

import client.Point;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class ClientController {


    private boolean isBlack;




    private Stage stage;

    private int tmpA,tmpB; //zmienne stworzenone aby nie wysylac serverowi info jesli sie klikni np. na srodku kwadratu
    private int x, y, diffX, diffY;
    private int killedBlack=0;
    private int killedWhite=0;

    private Ellipse[][] checkers = new Ellipse[19][19];

    private Point pointToPush;
    private boolean isSthToPush;





    @FXML
    private void initialize() throws IOException {
        hidePassButton();

        ArrayList<Label> labelList= new ArrayList<>();
        for(int i=0;i<19;i++){
            labelList.add(new Label(Integer.toString(i)));
            labelList.get(labelList.size()-1).setLayoutX(15+40*i);
            labelList.get(labelList.size()-1).setLayoutY(0);
            labelList.get(labelList.size()-1).setStyle("-fx-font-weight: bold");

            board.getChildren().add(labelList.get(labelList.size()-1));

            labelList.add(new Label(Integer.toString(i)));
            labelList.get(labelList.size()-1).setLayoutX(0);
            labelList.get(labelList.size()-1).setLayoutY(10+40*i);
            labelList.get(labelList.size()-1).setStyle("-fx-font-weight: bold");

            board.getChildren().add(labelList.get(labelList.size()-1));
        }


    }


    @FXML // fx:id="board"
    private Pane board; // Value injected by FXMLLoader


    @FXML // fx:id="passWhite"
    private Button btnPass; // Value injected by FXMLLoader

    @FXML // fx:id="pointsBlack"
    private Label pointsBlack; // Value injected by FXMLLoader

    @FXML // fx:id="pointsWhite"
    private Label pointsWhite; // Value injected by FXMLLoader

    @FXML
    private Label lblGraczB;

    @FXML
    private Label lblGraczC;

    @FXML
    private Button botMove;



    @FXML
    public void boardClicked(MouseEvent e) {

        double diffR;

        x = (int) e.getX();
        y = (int) e.getY();
        tmpA = (int) (e.getX()) / 40;
        tmpB = (int) (e.getY()) / 40;
        diffX = Math.abs(x - (tmpA * 40 + 20));
        diffY = Math.abs(y - (tmpB * 40 + 20));
        diffR = Math.sqrt(diffX * diffX + diffY * diffY);
        if (diffR > 15) {
            /**clicked out of any points range*/
        }
        else{
            pointToPush = new Point(tmpA,tmpB,isBlack);
            isSthToPush = true;

            /** wyślij do serwera informacje z "pointToCreate"*/

            }
    }




    @FXML
    void btnPassOnAction(ActionEvent event)  {
        /** wyslij do serwera informacje z "passPoint"*/
        pointToPush = new Point(20,20,isBlack);
        isSthToPush = true;
        System.out.println("Zrobiles PASS jednej rundy.");

    }



    /**Dodawanie pionka*/

    @FXML
    public void addChecker(Point point) {

        if(point.isNotDelete()==false){                      //gdy pion jest do usunięcia
            System.out.println("Jest cos do usuniecia");
            removeChecker(point.getX(),point.getY());
            return;
        }

        int a=point.getX();
        int b=point.getY();


        checkers[a][b] = new Ellipse();
        checkers[a][b].setCenterX(a * 40 + 20);
        checkers[a][b].setCenterY(b * 40 + 20);
        checkers[a][b].setRadiusY(18);
        checkers[a][b].setRadiusX(18);
        checkers[a][b].setStrokeWidth(2);


        if (point.isBlack()) {
            checkers[a][b].setFill(Color.BLACK);
            checkers[a][b].setStroke(Color.WHITE);
        } else {
            checkers[a][b].setFill(Color.WHITE);
            checkers[a][b].setStroke(Color.BLACK);
        }

        board.getChildren().add(checkers[a][b]);


    }

    /**Usuwanie pionka*/

    void removeChecker(int a, int b) {
        if(checkers[a][b]!=null) {

            if (checkers[a][b].getFill() == Color.BLACK)
                incrementWhitePoints();
            else
                incrementBlackPoints();

            board.getChildren().remove(checkers[a][b]);
            checkers[a][b] = null;
        }
    }

    public void incrementWhitePoints(){
        killedWhite++;
        pointsWhite.setText(Integer.toString(killedWhite));
    }

    public void incrementBlackPoints(){
        killedBlack++;
        pointsBlack.setText(Integer.toString(killedBlack));
    }



    public void setStage(Stage stage) {
        this.stage = stage;
    }


    public void hidePassButton(){
        if(isBlack) {
            lblGraczC.setText("Ja");
            btnPass.setLayoutX(1000);
        }
        else {
            lblGraczB.setText("Ja");

        }

    }

    public boolean isBlack() {
        return isBlack;
    }

    public void setIsBlack(boolean black) {
        isBlack = black;
    }

    public Point getPointToPush() {
        return pointToPush;
    }

    public boolean isSthToPush() {
        return isSthToPush;
    }

    public void setSthToPush(boolean sthToPush) {
        isSthToPush = sthToPush;
    }
}
