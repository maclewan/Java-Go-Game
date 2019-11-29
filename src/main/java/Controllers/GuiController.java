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

import java.util.ArrayList;

public class GuiController {

    //temp
    public boolean isBlack;


    public Ellipse[][] checkers = new Ellipse[19][19];
    boolean[][] groupedArr = new boolean[19][19];
    boolean[][] allreadyChecked = new boolean[19][19];
    ArrayList<ArrayList<Point>> groupList = new ArrayList();
    Point lastAdded = new Point(-1,-1,Color.WHITE);


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
        int a, b, x, y, diffX, diffY;
        double diffR;

        x = (int) e.getX();
        y = (int) e.getY();
        a = (int) (e.getX()) / 40;
        b = (int) (e.getY()) / 40;
        diffX = Math.abs(x - (a * 40 + 20));
        diffY = Math.abs(y - (b * 40 + 20));
        diffR = Math.sqrt(diffX * diffX + diffY * diffY);

        if(diffR>15){
             //clicked out of any points range
        }
        else
        if(checkers[a][b]==null){
            cleanAllreadyChecked();
            addChecker(a,b);
        }
        else if((!isBlack && checkers[a][b].getFill().equals(Color.WHITE)) || (isBlack && checkers[a][b].getFill().equals(Color.BLACK)))
        {
            removeChecker(a, b);
            checkers[a][b]=null;
        }
        else{
            removeChecker(a, b);
            checkers[a][b]=null;
        }
    }


    @FXML
    void colourOnAction(ActionEvent event) {

        isBlack = !isBlack;


    }

    @FXML
    void passBlackOnAction(ActionEvent event) {
        groupCheckers();

    }

    @FXML
    void passWhiteOnAction(ActionEvent event) {
        killer();

    }

    /*Dodaje pionek*/
    void addChecker(int a, int b) {

        checkers[a][b] = new Ellipse();
        checkers[a][b].setCenterX(a * 40 + 20);
        checkers[a][b].setCenterY(b * 40 + 20);
        checkers[a][b].setRadiusY(18);
        checkers[a][b].setRadiusX(18);
        checkers[a][b].setStrokeWidth(2);


        if (isBlack) {
            checkers[a][b].setFill(Color.BLACK);
            checkers[a][b].setStroke(Color.WHITE);
            lastAdded = new Point(a,b,Color.BLACK);
        } else {
            checkers[a][b].setFill(Color.WHITE);
            checkers[a][b].setStroke(Color.BLACK);
            lastAdded = new Point(a,b,Color.WHITE);
        }

            board.getChildren().add(checkers[a][b]);
            if(isSuicide2(a,b)) removeChecker(a, b);


    }

    /*Usuwam pionek*/
    void removeChecker(int a, int b) {
        board.getChildren().remove(checkers[a][b]);
        checkers[a][b] = null;
        groupedArr[a][b] = false;
    }


    //-----------------------------------------------------------------------------TUTAJ SPRAWDZAM ZASADY


    /*Funkcja zabijająca piony bez oddechu*/

    void killer() {


        for (int i = 0; i < 19; i++) {           //sprawdznie i zabijanie pojedynczych klocków
            for (int j = 0; j < 19; j++) {
                if(i==lastAdded.getX()&&j==lastAdded.getY())
                    continue;
                if (groupedArr[i][j])
                    continue;
                if (countBreaths(i, j) == 0) {
                    removeChecker(i, j);
                }
            }
        }
        killGroupedCheckers();                   //sprawdzanie i zabijanie grup
        if (!groupedArr[lastAdded.getX()][lastAdded.getY()]){                     //sprawdzanie ostatnio dodanego
            if (countBreaths(lastAdded.getX(), lastAdded.getY()) == 0) {
                removeChecker(lastAdded.getX(), lastAdded.getY());
            }
        }


    }

    void killerSimulation(ArrayList<Point> pointsToKill) {


        for (int i = 0; i < 19; i++) {           //sprawdznie i zabijanie pojedynczych klocków
            for (int j = 0; j < 19; j++) {
                if(i==lastAdded.getX()&&j==lastAdded.getY())
                    continue;
                if (groupedArr[i][j])
                    continue;
                if (countBreathsSim(i, j,pointsToKill) == 0&&checkers[i][j]!=null) {
                    pointsToKill.add(new Point(i,j,Color.GRAY));

                    System.out.println("added point to kill list: "+i+","+j+" breath count: "+ countBreaths(i, j));
                }
            }
        }
        killGroupedCheckersSim(pointsToKill);                   //sprawdzanie i zabijanie grup
        if (!groupedArr[lastAdded.getX()][lastAdded.getY()]){                     //sprawdzanie ostatnio dodanego
            if (countBreathsSim(lastAdded.getX(), lastAdded.getY(),pointsToKill) == 0) {
                pointsToKill.add(new Point(lastAdded.getX(), lastAdded.getY(),Color.GRAY));
            }
        }


    }

    /*Grupuje zbite piony w grupy*/
    void groupCheckers() {
        groupedArr = new boolean[19][19];
        groupList.clear();
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 19; j++) {
                if (checkers[i][j] != null && !groupedArr[i][j]) {
                    Paint tempCol = checkers[i][j].getFill();
                    ArrayList<Point> tempArr = new ArrayList<>();
                    getComrade(i, j, tempCol, tempArr);
                    if (tempArr.size() > 1)
                        groupList.add(new ArrayList<>(tempArr));
                }
            }
        }

    }

    /*Sprawdzanie i dodawanie sąsiadów do grupy*/
    void getComrade(int a, int b, Paint color, ArrayList<Point> comList) {
        if (groupedArr[a][b]) {    //czy tego punktu juz nie ma w liście
            return;
        }

        comList.add(new Point(a, b, color));         //dodaj punkt

        /*sprawdzam okoliczne punkty*/
        try {
            if (checkers[a][b + 1].getFill() == color) {         //jeśli kolor sie zgadza
                groupedArr[a][b] = true;                      //oznaczam pkt [a][b] jako element zgrupowany
                getComrade(a, b + 1, color, comList);        //sprawdzam znajomych dla sąsiada o tym samym kolorze
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        } catch (NullPointerException e) {
        }
        try {
            if (checkers[a - 1][b].getFill() == color) {
                groupedArr[a][b] = true;
                getComrade(a - 1, b, color, comList);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        } catch (NullPointerException e) {
        }
        try {
            if (checkers[a][b - 1].getFill() == color) {
                groupedArr[a][b] = true;
                getComrade(a, b - 1, color, comList);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        } catch (NullPointerException e) {
        }
        try {
            if (checkers[a + 1][b].getFill() == color) {
                groupedArr[a][b] = true;
                getComrade(a + 1, b, color, comList);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        } catch (NullPointerException e) {
        }


    }


    int countBreaths(int a, int b) {
        int counter = 0;
        try {
            if (checkers[a + 1][b] == null) {
                counter++;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        try {
            if (checkers[a - 1][b] == null) {
                counter++;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        try {
            if (checkers[a][b + 1] == null) {
                counter++;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        try {
            if (checkers[a][b - 1] == null) {
                counter++;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        return counter;
    }

    int countBreathsSim(int a, int b, ArrayList<Point> pointsToKill) {
        int counter = 0;
        try {
            if (checkers[a + 1][b] == null||myContains(pointsToKill,a+1,b)) {
                counter++;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        try {
            if (checkers[a - 1][b] == null||myContains(pointsToKill,a-1,b)) {
                counter++;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        try {
            if (checkers[a][b + 1] == null||myContains(pointsToKill,a,b+1)) {
                counter++;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        try {
            if (checkers[a][b - 1] == null||myContains(pointsToKill,a,b-1)) {
                counter++;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        return counter;
    }


    void killGroupedCheckers() {
        for(int i=0; i<groupList.size();i++){
            int counter=0;

            for(int j=0; j<groupList.get(i).size();j++){
                counter+=countBreaths(groupList.get(i).get(j).getX(),groupList.get(i).get(j).getY());
            }
            if(counter==0){
                for(int j=groupList.get(i).size()-1; j>=0;j--){
                    removeChecker(groupList.get(i).get(j).getX(),groupList.get(i).get(j).getY());
                }
            }
        }

    }

    void killGroupedCheckersSim(ArrayList<Point> pointsToKill) {
        for(int i=0; i<groupList.size();i++){
            int counter=0;

            for(int j=0; j<groupList.get(i).size();j++){
                counter+=countBreathsSim(groupList.get(i).get(j).getX(),groupList.get(i).get(j).getY(),pointsToKill);
            }
            if(counter==0){
                for(int j=groupList.get(i).size()-1; j>=0;j--){
                    pointsToKill.add(new Point(groupList.get(i).get(j).getX(),groupList.get(i).get(j).getY(),Color.GRAY));
                }
            }
        }

    }


    boolean isSuicide2(int a, int b){
        System.out.println("Call suicide2 for: "+a+","+b);

        ArrayList<Point> pointsToKill = new ArrayList<>();
        groupCheckers();
        killerSimulation(pointsToKill);

        for(int i=0;i<pointsToKill.size();i++)
            System.out.println(pointsToKill.get(i).getX()+","+pointsToKill.get(i).getY()+";");


        if(myContains(pointsToKill,a,b)){
            System.out.println("This point is suicide: "+a+","+b);
            return true;
        }
        else{
            System.out.println("This point is not suicide: "+a+","+b);
            return false;
        }

    }


    /*Sprawdzam czy jest samobojstwo*/
    public boolean isSuicide(int a, int b) {

        if (isSurround(a, b) &&  !allreadyChecked[a][b])
        {
            allreadyChecked[a][b]=true;
            if(comradesAmmount(a,b)==0) { System.out.println("1"); return true; }
            if(!isBlack) {
                if((b+1)<=18 && checkers[a][b+1].getFill().equals(Color.WHITE) ) {
                    if (!allreadyChecked[a][b + 1]) return isSuicide(a, (b + 1));
                }
                if((b-1)>=0 && checkers[a][b-1].getFill().equals(Color.WHITE)){
                    if(!allreadyChecked[a][b-1]) return isSuicide(a,(b-1));
                }
                if((a+1)<=18 && checkers[a + 1][b].getFill().equals(Color.WHITE)) {
                    if(!allreadyChecked[a+1][b]) return isSuicide(a+1,b);
                }
                if((a-1)>=0 && checkers[a - 1][b].getFill().equals(Color.WHITE)) {
                    if(!allreadyChecked[a-1][b]) return  isSuicide((a-1),b);
                }

            }
            if(isBlack) {
                if ((a+1)<=18 && checkers[a + 1][b].getFill().equals(Color.BLACK))
                    if(!allreadyChecked[a+1][b]) return isSuicide(a+1,b);
                if ((a-1)>=0 && checkers[a - 1][b].getFill().equals(Color.BLACK))
                    if(!allreadyChecked[a-1][b]) return  isSuicide((a-1),b);
                if ((b+1)<=18 && checkers[a][b + 1].getFill().equals(Color.BLACK))
                    if (!allreadyChecked[a][b + 1]) return isSuicide(a, (b + 1));
                if ((b-1)>=0 && checkers[a][b - 1].getFill().equals(Color.BLACK))
                    if(!allreadyChecked[a][b-1]) return isSuicide(a,(b-1));
            }
            return true;

        }
        return false;
    }

    /*Sprawdzam czy pionek jest otoczony*/
    boolean isSurround(int a, int b) {
        if ((a+1<=18 && checkers[a + 1][b] == null ) || (a-1>=0 && checkers[a - 1][b] == null ) || (b+1<=18 &&checkers[a][b + 1] == null) || (b-1>=0 && checkers[a][b - 1] == null)) {
            return false;
        }
            return true;
    }

    /*licze ile mam towarzyszy obok pionka*/
    int comradesAmmount(int a, int b) {
        int towarzysz=0;
        if(!isBlack) {
            if(a+1<=18 && checkers[a + 1][b].getFill().equals(Color.WHITE)) {
                //isSuicide((a+1),b);
                towarzysz++;
            }
            if(a-1>=0 && checkers[a - 1][b].getFill().equals(Color.WHITE))
            {
                //isSuicide((a-1),b);
                towarzysz++;
            }
            if(b+1<=18 && checkers[a][b+1].getFill().equals(Color.WHITE))
            {
                //isSuicide(a,(b+1));
                towarzysz++;
            }
            if(b-1>=0 && checkers[a][b-1].getFill().equals(Color.WHITE)){
               // isSuicide(a,(b-1));
                towarzysz++;
            }
        }
        else {
            if (a+1<=18 && checkers[a + 1][b].getFill().equals(Color.BLACK))
                towarzysz++;
            if (a-1>=0 && checkers[a - 1][b].getFill().equals(Color.BLACK))
                towarzysz++;
            if (b+1<=18 && checkers[a][b + 1].getFill().equals(Color.BLACK))
                towarzysz++;
            if (b-1>=0 && checkers[a][b - 1].getFill().equals(Color.BLACK))
                towarzysz++;
        }
        return towarzysz;
    }

    /*czyszcze tablice*/
    void cleanAllreadyChecked() {
        for (int i = 0; i <= 18; i++) {
            for (int j = 0; j <= 18; j++) {
                allreadyChecked[i][j] = false;
            }
        }


    }

    public boolean myContains(ArrayList<Point> arr, int x, int y) {
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).getY() == y && arr.get(i).getX() == x) {
                return true;
            }
        }
        return false;
    }


}
