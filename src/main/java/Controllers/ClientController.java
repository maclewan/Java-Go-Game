package Controllers;

import Server.GameLogic.Point;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ClientController {


    private boolean isBlack;
    private boolean bot=false;

    private Stage stage;

    private int killedBlack=0;
    private int killedWhite=0;

    private Ellipse[][] checkers = new Ellipse[19][19];

    private Point pointToPush;
    private boolean isSthToPush;
    private ArrayList<Point> pointsToAdd = new ArrayList<>();



    @FXML
    private void initialize() throws IOException {
        hidePassButton();

        ArrayList<Label> labelList= new ArrayList<>();
        for(int i=0;i<19;i++){
            labelList.add(new Label(Integer.toString(i)));
            labelList.get(labelList.size()-1).setLayoutX(15+40*i);
            labelList.get(labelList.size()-1).setLayoutY(0);
            labelList.get(labelList.size()-1).setStyle("-fx-font-weight: bold");

            if(!bot)
                board.getChildren().add(labelList.get(labelList.size()-1));

            labelList.add(new Label(Integer.toString(i)));
            labelList.get(labelList.size()-1).setLayoutX(0);
            labelList.get(labelList.size()-1).setLayoutY(10+40*i);
            labelList.get(labelList.size()-1).setStyle("-fx-font-weight: bold");
            if(!bot)
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

        int x = (int) e.getX();
        int y = (int) e.getY();
        int tmpA = (int) (e.getX()) / 40;
        //zmienne stworzenone aby nie wysylac serverowi info jesli sie klikni np. na srodku kwadratu
        int tmpB = (int) (e.getY()) / 40;
        int diffX = Math.abs(x - (tmpA * 40 + 20));
        int diffY = Math.abs(y - (tmpB * 40 + 20));
        diffR = Math.sqrt(diffX * diffX + diffY * diffY);
        if (diffR > 15) {
            /**clicked out of any points range*/
        }
        else{
            pointToPush = new Point(tmpA, tmpB,isBlack);
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

        if(point.getX()<0){                      //gdy pion jest do usunięcia


            removeChecker(-(point.getX()+1),-(point.getY()+1));
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
        if(!bot)
          board.getChildren().add(checkers[a][b]);


    }

    /**Usuwanie pionka*/

    private void removeChecker(int a, int b) {
        if(checkers[a][b]!=null) {

            if (checkers[a][b].getFill() == Color.BLACK)
                incrementWhitePoints();
            else
                incrementBlackPoints();
            if(!bot)
                board.getChildren().remove(checkers[a][b]);
            checkers[a][b] = null;
        }
    }

    private void incrementWhitePoints(){
        killedWhite++;
        if(!bot)
            pointsWhite.setText(Integer.toString(killedWhite));
    }

    private void incrementBlackPoints(){
        killedBlack++;
        if(!bot)
            pointsBlack.setText(Integer.toString(killedBlack));
    }



    void setStage(Stage stage) {
        this.stage = stage;
    }


    private void hidePassButton(){
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
    public void addArrayOfPoints(){
        for(int h=0; h<pointsToAdd.size();h++){
            addChecker(pointsToAdd.get(h));
        }
    }
    public void setArrayOfPoints(ArrayList<Point> list){
        this.pointsToAdd= list;

    }

    public void setPointToPush(Point pointToPush) {
        this.pointToPush = pointToPush;
    }

    public void setBot(boolean bot) {
        this.bot = bot;
    }
}
