package com.example.xd;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
//wiersze kolumny
public class GUISquare extends Rectangle {
    private static int number = 0;
    private final int index;
    private int side = 50;
    private int ilosc = 11;
    public GUISquare(){
        index = number;
        number ++;
        setWidth(side);
        setHeight(side);
        setStroke(Color.BLACK);
        setFill(Color.WHITE);
        setStrokeWidth(1);
        setX(100+ side * (index / ilosc));
        setY(100+ side * (index % ilosc));
        if(index / ilosc == 10)
        {
            setVisible(false);
        }
        if(index % ilosc == 10)
        {
            setVisible(false);
        }
    }
    public double getXpos()
    {
        return getX();
    }
    public double getYpos()
    {
        return getY();
    }

}