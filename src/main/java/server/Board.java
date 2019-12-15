package server;


import client.Point;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Random;

public class Board {


    private boolean isBlack=false;



    private Point tempPoint = new Point(21,21,isBlack);


    private int x, y;


    private int[][] pointsArr = new int[19][19];                       /** 0-white, 1-black, 2-empty*/
    private boolean[][] groupedArr = new boolean[19][19];

    private ArrayList<ArrayList<Point>> groupList = new ArrayList();
    private ArrayList<Point> lastlyKilled = new ArrayList<>();
    private Point lastAddedWhite = new Point(23,23, Color.WHITE);
    private Point lastAddedBlack = new Point(23,23, Color.BLACK);
    private Point lastAdded = new Point(23,23,Color.GREY);







    /**Dodawanie pionka*/

    public void addChecker(int x, int y, boolean isBlack) {

        if(pointsArr[x][y]!= 2)                     /**gdy punkt już istnieje*/
            //todo: info do clienta - zły ruch
            return;

        else {
            tempPoint = new Point(x, y, isBlack);
            if(isBlack) {                              //tworze pionka
                pointsArr[x][y] =1;
            }
            else
                pointsArr[x][y] =0;
        }


        if (isSuicide2(x, y)) {
            removeChecker(x, y, false);
            System.out.println("To jest zamobojstwo! Zrob inny ruch");
            //todo: info do clienta - zły ruch
        }
        if (myContains(lastlyKilled, x, y)) {
            removeChecker(x, y, false);
            System.out.println("Ten pionek byl ostatnio zbity! Zrob inny ruch");
            //todo: info do clienta - zły ruch
        }

        if(tempPoint.isBlack()){
            lastAddedBlack=tempPoint;
        }
        else
            lastAddedWhite=tempPoint;


        killer();                 //podaje w sobie info do serwera
        //groupCheckers();   //czy tu potrzebne?

        //todo: info do clientow - dobry ruch, zwróć x, y, kolor
    }

    /**Usuwanie pionka*/

    void removeChecker(int x, int y, boolean seriously) {

        pointsArr[x][y] = 2;
        groupedArr[x][y] = false;

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
                if (pointsArr[i][j]!=2&&countBreaths(i, j) == 0) {
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

        //todo: info do clientow - usuń piony które są w lastlyKilled


    }

    void killerSimulation(ArrayList<Point> pointsToKill) {


        for (int i = 0; i < 19; i++) {           //sprawdznie i zabijanie pojedynczych klocków
            for (int j = 0; j < 19; j++) {
                if(i==lastAdded.getX()&&j==lastAdded.getY())
                    continue;
                if (groupedArr[i][j])
                    continue;
                if (pointsArr[i][j]!=2&&countBreathsSim(i, j,pointsToKill) == 0) {
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


    void killGroupedCheckers() {

        if(tempPoint.isBlack())
            lastAdded=lastAddedBlack;

        else
            lastAdded=lastAddedWhite;


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
                ArrayList<Point> pointsToKill= new ArrayList<>();
                for(int j=groupList.get(i).size()-1; j>=0;j--){
                    removeChecker(groupList.get(i).get(j).getX(),groupList.get(i).get(j).getY(),true);
                    pointsToKill.add(new Point(groupList.get(i).get(j).getX(),groupList.get(i).get(j).getY()));
                }
                //todo: info do clientow - usunac piony z pointsToKill
            }
        }

    }

    void killGroupedCheckersSim(ArrayList<Point> pointsToKill) {
        if(tempPoint.isBlack())
            lastAdded=lastAddedBlack;

        else
            lastAdded=lastAddedWhite;


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
                if(pointsArr[i][j]!=2)
                    continue;

                addChecker(i,j,isBlack);

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

        Point botPoint = new Point(randomElement[0],randomElement[1],isBlack);
        addChecker(botPoint.getX(),botPoint.getY(),botPoint.isBlack());
        //todo: info do serwera o dodaniu piona
    }





}

