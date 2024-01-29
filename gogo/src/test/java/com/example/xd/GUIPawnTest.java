package com.example.xd;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;



public class GUIPawnTest extends Application{
    private GUIPawn guiPawn;

    @Override
    public void start(Stage stage) {
        guiPawn = new GUIPawn(50, 50, 1, 1);
        stage.setScene(new Scene(new Group(guiPawn), 100, 100));
        stage.show();
    }

    @Test
    public void testSetClear() {
        GUIPawn guiPawn = new GUIPawn(50, 50, 1, 1);
        guiPawn.setClear();
        assertEquals(Color.TRANSPARENT, guiPawn.getFill());
        assertEquals(Color.TRANSPARENT, guiPawn.getStroke());
        assertFalse(guiPawn.isClicked());
    }

    @Test
    public void testSetBlack() {
        GUIPawn guiPawn = new GUIPawn(50, 50, 1, 1);
        guiPawn.setBlack();
        assertEquals(Color.BLACK, guiPawn.getFill());
        assertEquals(Color.BLACK, guiPawn.getStroke());
        assertFalse(guiPawn.isClicked());
    }

    @Test
    public void testSetWhite() {
        GUIPawn guiPawn = new GUIPawn(50, 50, 1, 1);
        guiPawn.setWhite();
        assertEquals(Color.WHITE, guiPawn.getFill());
        assertEquals(Color.BLACK, guiPawn.getStroke());
        assertFalse(guiPawn.isClicked());
    }

}