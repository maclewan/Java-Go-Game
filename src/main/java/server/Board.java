/** Definicje pionków:
 * 20;20 - pas
 * 50;50 - zły ruch
 */


package server;


import client.Point;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Random;

public class Board {

    private Server server;
    private boolean isBlack=false;



    private Point tempPoint = new Point(21,21,isBlack);




    private int[][] pointsArr = new int[19][19];                       /** 0-white, 1-black, 2-empty*/

    private boolean[][] groupedArr = new boolean[19][19];

    private ArrayList<ArrayList<Point>> groupList = new ArrayList();
    private Point lastAdded = new Point(23,23,5);
    private ArrayList<Point> checkersToKill;
    private Point lastKilled = new Point(23,23,5);
    public Board(Server server) {
        this.server = server;
    }

    /**Dodawanie pionka*/

    public void addChecker(int x, int y, boolean isBlack) {

        if(pointsArr[x][y]!= 2)                  /**gdy punkt już istnieje*/
            return;
        else {
            tempPoint = new Point(x, y, isBlack);
            if(isBlack) {                              //tworze pionka
                pointsArr[x][y] =1;
            }
            else
                pointsArr[x][y] =0;
            lastAdded=tempPoint;
        }


        if (isSuicide2(x, y)) {
            removeChecker(x, y, false);
            System.out.println("To jest zamobojstwo! Zrob inny ruch");
            return;
        }
        if (lastKilled.getX()==x&&lastKilled.getY()==y){
            removeChecker(x, y, false);
            System.out.println("Ten pionek byl ostatnio zbity! Zrob inny ruch");
            return;
        }



        killer();                 //podaje w sobie info do serwera


        checkersToKill.add(tempPoint);

        setPointsList(checkersToKill);  /**wyslanie info do serwera*/
        server.changePlayer();

    }

    /**Usuwanie pionka*/

    void removeChecker(int x, int y, boolean seriously) {

        pointsArr[x][y] = 2;
        groupedArr[x][y] = false;

    }





    /**Funkcja zabijająca piony bez oddechu*/

    public void killer() {
        lastKilled=new Point(23,23);
        groupCheckers();
        checkersToKill=new ArrayList<>();

        for (int i = 0; i < 19; i++) {                                    //sprawdznie i zabijanie pojedynczych pionków
            for (int j = 0; j < 19; j++) {
                if(i==lastAdded.getX()&&j==lastAdded.getY())
                    continue;
                if (groupedArr[i][j])
                    continue;
                if (pointsArr[i][j]!=2&&countBreaths(i, j) == 0) {
                    checkersToKill.add(new Point(-i-1,-j-1,5,true));
                    removeChecker(i, j,true);
                    lastKilled=new Point(i,j);

                }
            }
        }
        killGroupedCheckers(checkersToKill);                                                   //sprawdzanie i zabijanie grup
        if (!groupedArr[lastAdded.getX()][lastAdded.getY()]){                                 //sprawdzanie ostatnio dodanego
            if (countBreaths(lastAdded.getX(), lastAdded.getY()) == 0) {
                checkersToKill.add(new Point(-lastAdded.getX()-1, -lastAdded.getY()-1,5,true));
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
                if (pointsArr[i][j]!=2&&countBreathsSim(i, j,pointsToKill) == 0) {
                    pointsToKill.add(new Point(i,j,5));

                }
            }
        }
        killGroupedCheckersSim(pointsToKill);                   //sprawdzanie i zabijanie grup
        if (!groupedArr[lastAdded.getX()][lastAdded.getY()]){                     //sprawdzanie ostatnio dodanego
            if (countBreathsSim(lastAdded.getX(), lastAdded.getY(),pointsToKill) == 0) {
                pointsToKill.add(new Point(lastAdded.getX(), lastAdded.getY(),5));
            }
        }


    }

    /**Grupuje zbite piony w grupy*/
    public void groupCheckers() {
        groupedArr = new boolean[19][19];
        groupList.clear();
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 19; j++) {
                if (pointsArr[i][j]!=2 && !groupedArr[i][j]) {    //jesli istnieje i nie jest zgrupowany
                    int tempCol = pointsArr[i][j];
                    ArrayList<Point> tempArr = new ArrayList<>();    //nowa grupa
                    getComrade(i, j, tempCol, tempArr);
                    if (tempArr.size() > 1)
                        groupList.add(new ArrayList<>(tempArr));     //jesli grupa nie jest pojedyncza to dodaj
                }
            }
        }


    }

    /**Sprawdzanie i dodawanie sąsiadów do grupy*/
    void getComrade(int a, int b, int color, ArrayList<Point> comList) {
        if (groupedArr[a][b]) {    //czy tego punktu juz nie ma w liście
            return;
        }

        comList.add(new Point(a, b, color));         //dodaj punkt

        /**sprawdzam okoliczne punkty*/
        try {
            if (pointsArr[a][b + 1] == color) {         //jeśli kolor sie zgadza
                groupedArr[a][b] = true;                      //oznaczam pkt [a][b] jako element zgrupowany
                getComrade(a, b + 1, color, comList);        //sprawdzam znajomych dla sąsiada o tym samym kolorze
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        } catch (NullPointerException e) {
        }
        try {
            if (pointsArr[a - 1][b] == color) {
                groupedArr[a][b] = true;
                getComrade(a - 1, b, color, comList);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        } catch (NullPointerException e) {
        }
        try {
            if (pointsArr[a][b - 1] == color) {
                groupedArr[a][b] = true;
                getComrade(a, b - 1, color, comList);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        } catch (NullPointerException e) {
        }
        try {
            if (pointsArr[a + 1][b] == color) {
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
            if (pointsArr[a + 1][b] == 2) {
                counter++;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        try {
            if (pointsArr[a - 1][b] == 2) {
                counter++;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        try {
            if (pointsArr[a][b + 1] == 2) {
                counter++;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        try {
            if (pointsArr[a][b - 1] == 2) {
                counter++;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        return counter;
    }

    int countBreathsSim(int a, int b, ArrayList<Point> pointsToKill) {
        int counter = 0;
        try {
            if (pointsArr[a + 1][b] == 2||myContains(pointsToKill,a+1,b)) {
                counter++;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        try {
            if (pointsArr[a - 1][b] == 2||myContains(pointsToKill,a-1,b)) {
                counter++;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        try {
            if (pointsArr[a][b + 1] == 2||myContains(pointsToKill,a,b+1)) {
                counter++;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        try {
            if (pointsArr[a][b - 1] == 2||myContains(pointsToKill,a,b-1)) {
                counter++;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        return counter;
    }


    void killGroupedCheckers(ArrayList<Point> checkersToKill) {



        for(int i=0; i<groupList.size();i++){
            int counter=0;
            if(myContains(groupList.get(i),lastAdded.getX(),lastAdded.getY())){                //gdy ostatni - wyrzuć na koniec
                ArrayList<Point> tempPointList = new ArrayList<>(groupList.get(i));
                groupList.add(tempPointList);
                groupList.remove(i);
            }

            for(int j=0; j<groupList.get(i).size();j++){
                counter+=countBreaths(groupList.get(i).get(j).getX(),groupList.get(i).get(j).getY());
            }
            if(counter==0){
                for(int j=groupList.get(i).size()-1; j>=0;j--){

                    checkersToKill.add(new Point(-groupList.get(i).get(j).getX()-1,-groupList.get(i).get(j).getY()-1,5,true));
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
                    pointsToKill.add(new Point(groupList.get(i).get(j).getX(),groupList.get(i).get(j).getY(),5));
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






    public boolean myContains(ArrayList<Point> arr, int x, int y) {
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).getY() == y && arr.get(i).getX() == x) {
                return true;
            }
        }
        return false;
    }

    void botMove(){       /**przykładowy bot, zawsze białe*/

        int[][] killPotential = new int[19][19];
        for(int i=0;i<19;i++){
            for(int j=0;j<19;j++){
                if(pointsArr[i][j]!=2)
                    continue;

                addChecker(i,j,false);

                if(pointsArr[i][j]!=2){                  //gdy punkt od razu znika - samobójstwo, albo nielegalny ruch
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

        Point botPoint = new Point(randomElement[0],randomElement[1],false);
        addChecker(botPoint.getX(),botPoint.getY(),botPoint.isBlack());
        /**tutaj wywołuje sie fkcja addChecker*/
    }







    private void setPointsList(ArrayList<Point> listToKill) {
        server.setPointList(listToKill);
    }
    public void startArrayOfCheckers(){
        for(int i=0;i<19;i++){
            for(int j=0;j<19;j++){
                pointsArr[i][j]=2;
            }
        }
    }


}

