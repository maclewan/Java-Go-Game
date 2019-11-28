package Controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import Controllers.GameRules;
public class GuiController {

    //temp
    boolean isBlack;

    Circle[][] checkers = new Circle[19][19];

    @FXML // fx:id="board"
    private Pane board; // Value injected by FXMLLoader

    @FXML // fx:id="colour"
    private ToggleButton colour; // Value injected by FXMLLoader

    @FXML // fx:id="passBlack"
    private Button passBlack; // Value injected by FXMLLoader

    @FXML // fx:id="passWhite"
    private Button passWhite; // Value injected by FXMLLoader

    @FXML // fx:id="pointsBlack"
    private Label pointsBlack; // Value injected by FXMLLoader

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
            System.out.println("out of range");   //clicked out of any points range
        }
        else
            if(checkers[a][b]==null){
                addChecker(a,b);
            }
            else {

                if ((!isBlack && checkers[a][b].getFill().equals(Color.WHITE)) || (isBlack &&checkers[a][b].getFill().equals(Color.BLACK) )) {
                    removeChecker(a, b);
                    checkers[a][b]=null;    //aby mozna bylo pozniej dodac nowy pionek w miejsce usunietego
                }


            }
    }

    @FXML
    void colourOnAction(ActionEvent event) {

        isBlack=!isBlack;


    }

    @FXML
    void passBlackOnAction(ActionEvent event) {

    }

    @FXML
    void passWhiteOnAction(ActionEvent event) {

    }

    /*Dodaje pionek*/
    void addChecker(int a, int b){
        if(!isSuicide(a,b)) {
            checkers[a][b] = new Circle();
            checkers[a][b].setCenterX(a * 40 + 20);
            checkers[a][b].setCenterY(b * 40 + 20);
            checkers[a][b].setRadius(18);

            System.out.println(isBlack);
            if (isBlack)
                checkers[a][b].setFill(Color.BLACK);
            else
                checkers[a][b].setFill(Color.WHITE);

            board.getChildren().add(checkers[a][b]);
        }

    }

    /*Usuwam pionek*/
    void removeChecker(int a, int b){
        board.getChildren().remove(checkers[a][b]);
    }

    //-----------------------------------------------------------------------------TUTAJ SPRAWDZAM ZASADY
    /*Sprawdzam czy zostanie dokonane morderstwo*/
     boolean isKill(int a, int b)
    {
        if(isSurround(a,b))
        {



        }
        return false;
    }
    /*Sprawdzam czy jest samobojstwo*/
    boolean isSuicide(int a, int b) {
        if (isSurround(a, b))
        {
            if (!isBlack && checkers[a + 1][b].getFill().equals(Color.BLACK) && checkers[a - 1][b].getFill().equals(Color.BLACK) && checkers[a][b + 1].getFill().equals(Color.BLACK) && checkers[a][b - 1].getFill().equals(Color.BLACK))
                return true;
            if(isBlack && checkers[a+1][b].getFill().equals(Color.WHITE) && checkers[a-1][b].getFill().equals(Color.WHITE) && checkers[a][b+1].getFill().equals(Color.WHITE) && checkers[a][b-1].getFill().equals(Color.WHITE))
                return true;
        }
        return false;
    }

    /*Sprawdzam czy pionek jest otoczony*/
    boolean isSurround(int a, int b)
    {
        if (checkers[a + 1][b] != null && checkers[a - 1][b] != null && checkers[a][b + 1] != null && checkers[a][b - 1] != null) {
            return true;
        }
            return false;
    }

    /*licze ile mam towarzyszy obok pionka*/
    void haveComrade(int a, int b)
    {

    }




}
