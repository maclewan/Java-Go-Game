package Server.GameLogic;

import java.io.Serializable;

public class Point implements Serializable {
    private int x,y;
    private boolean notDelete = true;
    private int color;



    public Point(int x, int y, boolean isBlack) {
        this.x = x;
        this.y = y;
        if(isBlack)
            this.color=1;
        else
            this.color=0;
    }

    public Point(int x, int y, int color) {
        this.x = x;
        this.y = y;
        this.color=color;

    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.color=5;
    }

    public Point(int x, int y, int color, boolean notDelete) {
        this.x = x;
        this.y = y;
        this.notDelete=notDelete;
        this.color=color;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isNotDelete() {
        return notDelete;
    }

    public boolean isBlack(){
        if(color==1)
            return true;

        return false;
    }






}
