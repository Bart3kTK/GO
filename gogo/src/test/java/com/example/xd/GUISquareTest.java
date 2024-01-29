

package com.example.xd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.example.xd.GUISquare;

import javafx.scene.paint.Color;

public class GUISquareTest {
    
    @Test
    public void testConstructor() {
        int size = 5;
        GUISquare square = new GUISquare(size);
        
        assertEquals(Color.WHITE, square.getFill());
        assertEquals(Color.BLACK, square.getStroke());
        assertTrue(square.isVisible());
    }
    
    @Test
    public void testGetPosition() {
        int size = 5;
        GUISquare square = new GUISquare(size);
        assertEquals(0, square.getColumn());
        assertEquals(0, square.getRow());
    }
    
}

