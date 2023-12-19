package com.example.xd;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GUICircle extends Circle{
    private static int clickNumber = 0;
    private boolean isClicked = false;
    public GUICircle(double x, double y)
    {
        setCenterX(x);
        setCenterY(y);
        setRadius(20);
        setPickOnBounds(true);
        setFill(Color.TRANSPARENT);

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
    
}
