package com.example.xd;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GUIPawn extends Circle{
    private static int clickNumber = 0;
    private boolean isClicked = false;
    public GUIPawn(double x, double y)
    {
        setCenterX(x);
        setCenterY(y);
        setRadius(20);
        setPickOnBounds(true);
        setClear();

        setOnMouseClicked(e -> {
            if (!isClicked)
            {
                if (clickNumber % 2 == 0)
                {
                    setFill(Color.BLACK);
                    isClicked = true;
                }
                else
                {
                    setFill(Color.WHITE);
                    setStrokeWidth(1);
                    setStroke(Color.BLACK);
                    isClicked = true;
                }
                
                clickNumber ++;
            }
            
        });
    }

    public void setClear()
    {
        setFill(Color.TRANSPARENT);
        setStroke(Color.TRANSPARENT);
        isClicked = false;
    }
    public void setBlack()
    {
        setFill(Color.BLACK);
        setStroke(Color.BLACK);
        isClicked = true;
    }
    public void setWhite()
    {
        setFill(Color.WHITE);
        setStroke(Color.BLACK);
        isClicked = true; 
    }
    
}
