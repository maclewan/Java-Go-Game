package client;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Point {
    private int x,y;
    Paint color;

    public Point(int x, int y, Paint color) {
        this.x = x;
        this.y = y;
        this.color=color;
    }

    public Point(int x, int y, boolean isBlack) {
        this.x = x;
        this.y = y;
        if(isBlack)
            this.color=Color.BLACK;
        else
            this.color=Color.WHITE;
    }

    public Point(int x, int y, int color) {
        this.x = x;
        this.y = y;
        if(color==0)
            this.color=Color.WHITE;
        else if(color==1)
            this.color=Color.BLACK;
        else
            this.color=Color.GREY;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.color=Color.GREY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public boolean isBlack(){
        if(color==Color.BLACK)
            return true;

        return false;
    }




}
