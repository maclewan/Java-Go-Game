package Controllers;

import client.Point;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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


    public boolean isBlack;
    public boolean yourTurn=true;
    boolean firstTurn=true;
    public boolean lastPass=false;
    public boolean madeMove=false;
    public boolean doIPass=false;


Observer observer;
    private Stage stage;
    Point tempPoint = new Point(21,21,isBlack);

    private int tmpA,tmpB; //zmienne stworzenone aby nie wysylac serverowi info jesli sie klikni np. na srodku kwadratu
    int x, y, diffX, diffY;
    private int killedBlack=0;
    private int killedWhite=0;

    public Ellipse[][] checkers = new Ellipse[19][19];
    boolean[][] groupedArr = new boolean[19][19];
    boolean[][] allreadyChecked = new boolean[19][19];
    ArrayList<ArrayList<Point>> groupList = new ArrayList();
    ArrayList<Point> lastlyKilled = new ArrayList<>();
    Point lastAdded = new Point(0,0,Color.WHITE);





    public ClientController() throws IOException, ClassNotFoundException {
        this.observer=observer;
    }


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

       // ServerMenagment sv = new ServerMenagment(this);


    }


    @FXML // fx:id="board"
    public Pane board; // Value injected by FXMLLoader

    @FXML // fx:id="colour"
    private ToggleButton colour; // Value injected by FXMLLoader



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
        if (yourTurn) {

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
            } else if (checkers[tmpA][tmpB] == null) {
                tempPoint = new Point(tmpA,tmpB,isBlack);

                yourTurn=false;
                cleanAllreadyChecked();
                madeMove=true;
                addChecker(tempPoint);
                //groupCheckers();
                killer();
                System.out.println("lalalala");
                /**Wysylam i odbieram informacje*/
                //observer.setAB(a,b);
            }
    }
        else          System.out.println("To nie twoja tura, poczekaj");

    }





    @FXML
    void btnPassOnAction(ActionEvent event)  {
        if(yourTurn) {
            //doIPass = true;
            tempPoint= new Point(20,20,isBlack);
            yourTurn = false;
            System.out.println("Zprobiles PASS jednej rundy.");
        }
        else System.out.println("Nie mozesz zPASSowac nie swojej rundy");
    }




    /**Dodawanie pionka*/

    @FXML
    public void addChecker(Point point) {

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
            lastAdded = new Point(a,b,Color.BLACK);
        } else {
            checkers[a][b].setFill(Color.WHITE);
            checkers[a][b].setStroke(Color.BLACK);
            lastAdded = new Point(a,b,Color.WHITE);
        }

       board.getChildren().add(checkers[a][b]);
        System.out.println("dodaje go na serio");

        if(isSuicide2(a,b)) {removeChecker(a, b,false); yourTurn=true;  System.out.println("To jest zamobojstwo! Zrob inny ruch");}
        if(myContains(lastlyKilled,a,b)) {removeChecker(a, b,false); yourTurn=true; System.out.println("Ten pionek byl ostatnio zbity! Zrob inny ruch"); }

        groupCheckers();


    }

    /**Usuwanie pionka*/

    void removeChecker(int a, int b, boolean seriously) {
        if(checkers[a][b]!=null) {
            if (seriously) {
                if (checkers[a][b].getFill() == Color.BLACK)
                    incrementWhitePoints();
                else
                    incrementBlackPoints();
            }

            board.getChildren().remove(checkers[a][b]);
            checkers[a][b] = null;
            groupedArr[a][b] = false;
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


    //-----------------------------------------------------------------------------TUTAJ SPRAWDZAM ZASADY


    /**Funkcja zabijająca piony bez oddechu*/

    public void killer() {
        lastlyKilled=new ArrayList<>();

        for (int i = 0; i < 19; i++) {                                    //sprawdznie i zabijanie pojedynczych pionków
            for (int j = 0; j < 19; j++) {
                if(i==lastAdded.getX()&&j==lastAdded.getY())
                    continue;
                if (groupedArr[i][j])
                    continue;
                if (countBreaths(i, j) == 0&&checkers[i][j]!=null) {
                    lastlyKilled.add(new Point(i,j,Color.GREY));
                    removeChecker(i, j,true);
                }
            }
        }
        killGroupedCheckers();                                                   //sprawdzanie i zabijanie grup
        if (!groupedArr[lastAdded.getX()][lastAdded.getY()]){                     //sprawdzanie ostatnio dodanego
            if (countBreaths(lastAdded.getX(), lastAdded.getY()) == 0) {
                removeChecker(lastAdded.getX(), lastAdded.getY(),true);
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

    /**Grupuje zbite piony w grupy*/
    public void groupCheckers() {
        groupedArr = new boolean[19][19];
        groupList.clear();
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 19; j++) {
                if (checkers[i][j] != null && !groupedArr[i][j]) {    //jesli istnieje i nie jest zgrupowany
                    Paint tempCol = checkers[i][j].getFill();
                    ArrayList<Point> tempArr = new ArrayList<>();    //nowa grupa
                    getComrade(i, j, tempCol, tempArr);
                    if (tempArr.size() > 1)
                        groupList.add(new ArrayList<>(tempArr));     //jesli grupa nie jest pojedyncza to dodaj
                }
            }
        }

    }

    /**Sprawdzanie i dodawanie sąsiadów do grupy*/
    void getComrade(int a, int b, Paint color, ArrayList<Point> comList) {
        if (groupedArr[a][b]) {    //czy tego punktu juz nie ma w liście
            return;
        }

        comList.add(new Point(a, b, color));         //dodaj punkt

        /**sprawdzam okoliczne punkty*/
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
            if(myContains(groupList.get(i),lastAdded.getX(),lastAdded.getY())){
                ArrayList<Point> tempPointList = new ArrayList<>(groupList.get(i));
                groupList.add(tempPointList);
                groupList.remove(i);


            }



            for(int j=0; j<groupList.get(i).size();j++){
                counter+=countBreaths(groupList.get(i).get(j).getX(),groupList.get(i).get(j).getY());
            }
            if(counter==0){
                for(int j=groupList.get(i).size()-1; j>=0;j--){
                    removeChecker(groupList.get(i).get(j).getX(),groupList.get(i).get(j).getY(),true);
                }
            }
        }

    }

    void killGroupedCheckersSim(ArrayList<Point> pointsToKill) {
        for(int i=0; i<groupList.size();i++){
            int counter=0;
            if(myContains(groupList.get(i),lastAdded.getX(),lastAdded.getY())){
                ArrayList<Point> tempPointList = new ArrayList<>(groupList.get(i));
                groupList.remove(i);
                groupList.add(tempPointList);

            }
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

        ArrayList<Point> pointsToKill = new ArrayList<>();
        groupCheckers();
        killerSimulation(pointsToKill);

        if(myContains(pointsToKill,a,b)){
            return true;
        }
        else{
            return false;
        }

    }


    /**Sprawdzam czy jest samobojstwo*/
    public boolean isSuicide(int a, int b) {

        if (isSurround(a, b) &&  !allreadyChecked[a][b])
        {
            allreadyChecked[a][b]=true;
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

    /**Sprawdzam czy pionek jest otoczony*/
    boolean isSurround(int a, int b) {
        if ((a+1<=18 && checkers[a + 1][b] == null ) || (a-1>=0 && checkers[a - 1][b] == null ) || (b+1<=18 &&checkers[a][b + 1] == null) || (b-1>=0 && checkers[a][b - 1] == null)) {
            return false;
        }
        return true;
    }

    /**licze ile mam towarzyszy obok pionka*/
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

    /**czyszcze tablice*/
    public void cleanAllreadyChecked() {
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

    void botMove(){       /**przykładowy bot*/

        int[][] killPotential = new int[19][19];
        for(int i=0;i<19;i++){
            for(int j=0;j<19;j++){
                if(checkers[i][j]!=null)
                    continue;
                Point botPoint = new Point(i,j,isBlack);
                addChecker(botPoint);

                if(checkers[i][j]==null){
                    killPotential[i][j]=-1;
                    continue;
                }
                ArrayList<Point> pointsToKill = new ArrayList<>();     //liczenie potencjału zabójczości
                groupCheckers();
                killerSimulation(pointsToKill);
                killPotential[i][j]=pointsToKill.size();
                removeChecker(i,j,false);
            }
        }

        ArrayList<int[]> maxValueIndexes = new ArrayList<>();      //szukanie indeksów o maksymalnym potencjale zabijania
        int maxVal=-1;
        for(int i=0;i<19;i++) {
            for (int j = 0; j < 19; j++) {
                if(killPotential[i][j]==-1){
                    continue;
                }
                if(killPotential[i][j]>maxVal) {        //gdy wiekszy, zeruj tablice z indeksami
                    maxVal = killPotential[i][j];
                    maxValueIndexes = new ArrayList<>();
                    maxValueIndexes.add(new int[2]);
                    maxValueIndexes.get(0)[0]=i;
                    maxValueIndexes.get(0)[1]=j;
                }
                else if(killPotential[i][j]==maxVal){     //gdy równy dodaj do listy
                    maxValueIndexes.add(new int[2]);
                    maxValueIndexes.get(maxValueIndexes.size()-1)[0]=i;
                    maxValueIndexes.get(maxValueIndexes.size()-1)[1]=j;
                }


            }
        }


        Random rand = new Random();                       //wybierz losowe pole do wstawienia piona z najoptymalniejszych
        int[] randomElement = maxValueIndexes.get(rand.nextInt(maxValueIndexes.size()));

        Point botPoint = new Point(randomElement[0],randomElement[1],isBlack);
        addChecker(botPoint);




    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void startChat() {

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Chat.fxml"));
        ChatController ChatC=new ChatController();
        loader.setController(ChatC);
        stage.setTitle("Menu");
        Scene s = null;
        try {
            s = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(s);
        stage.setResizable(false);
        stage.show();

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
    public void updateTempPoint(int x, int y, boolean isBlack){
        tempPoint = new Point(x,y,isBlack);
    }
    public Point getTempPoint(){
        return tempPoint;
    }

}
