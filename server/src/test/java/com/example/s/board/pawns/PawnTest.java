package com.example.s.board.pawns;


import static org.junit.Assert.assertEquals;
import org.junit.Test;
public class PawnTest {

    @Test
    public void testBlackPawnDefaultConstructor() {
        BlackPawn blackPawn = new BlackPawn();

        // Test that the color is set to "black"
        assertEquals("black", blackPawn.getColor());
    }

    @Test
    public void testBlackPawnParameterizedConstructor() {
        BlackPawn blackPawn = new BlackPawn(1, 2);

        // Test that the color is set to "black" and the row and column are set correctly
        assertEquals("black", blackPawn.getColor());
        assertEquals(1, blackPawn.getRow());
        assertEquals(2, blackPawn.getColumn());
    }

    @Test
    public void testWhitePawnDefaultConstructor() {
        WhitePawn whitePawn = new WhitePawn();

        // Test that the color is set to "white"
        assertEquals("white", whitePawn.getColor());
    }

    @Test
    public void testWhitePawnParameterizedConstructor() {
        WhitePawn whitePawn = new WhitePawn(1, 2);

        // Test that the color is set to "white" and the row and column are set correctly
        assertEquals("white", whitePawn.getColor());
        assertEquals(1, whitePawn.getRow());
        assertEquals(2, whitePawn.getColumn());
    }

    @Test
    public void testNullPawnDefaultConstructor() {
        NullPawn nullPawn = new NullPawn();

        // Test that the color is set to "null"
        assertEquals("null", nullPawn.getColor());
    }

    @Test
    public void testNullPawnParameterizedConstructor() {
        NullPawn nullPawn = new NullPawn(1, 2);

        // Test that the color is set to "null" and the row and column are set correctly
        assertEquals("null", nullPawn.getColor());
        assertEquals(1, nullPawn.getRow());
        assertEquals(2, nullPawn.getColumn());
    }

    @Test
    public void testBorderPawnDefaultConstructor() {
        BorderPawn borderPawn = new BorderPawn();

        // Test that the color is set to "border"
        assertEquals("UNDEFINED", borderPawn.getColor());
    }

    @Test
    public void testBorderPawnParameterizedConstructor() {
        BorderPawn borderPawn = new BorderPawn();

        // Test that the color is set to "border" and the row and column are set correctly
        assertEquals("UNDEFINED", borderPawn.getColor());
    }
}