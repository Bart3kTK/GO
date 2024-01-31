
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.example.s.board.Board;
import com.example.s.board.pawns.BlackPawn;
import com.example.s.board.pawns.Pawn;
import com.example.s.board.pawns.WhitePawn;


public class BoardTest {
    private Board board;

    @Before
    public void setUp() {
        board = new Board(5, 5);
    }

    @Test
    public void testPutPawnAndGetBoard() {
        Pawn pawn = new BlackPawn(1, 1);
        board.putPawn(pawn);
        assertEquals(pawn, board.getboard()[1][1]);
    }


    @Test
    public void testIsPositionAllowed() {
        assertTrue(board.isPositionAllowed(0, 0, "black"));
        assertTrue(board.isPositionAllowed(1, 1, "white"));


        // Test with an already occupied position
        assertFalse(board.isPositionAllowed(0, 0, "white"));

        // Add more test cases based on your implementation
    }

    @Test
    public void testDoPossibleUpdates() {
        Pawn blackPawn = new BlackPawn(1, 1);
        Pawn whitePawn = new WhitePawn(0, 1);

        board.putPawn(blackPawn);
        board.putPawn(whitePawn);

        board.doPossibleUpdates(blackPawn);

        assertEquals("----- white ----- ----- ----- \n" +
                     "----- black ----- ----- ----- \n" +
                     "----- ----- ----- ----- ----- \n" +
                     "----- ----- ----- ----- ----- \n" +
                     "----- ----- ----- ----- ----- \n", board.toString());
    }

    @Test
    public void testCountTerritoryPoints() {

        assertEquals(25, board.countTerritoryPoints("black"));
    }

    @Test
    public void testIsTerritory() {
        Pawn blackPawn = new BlackPawn(1, 1);
        Pawn whitePawn = new WhitePawn(0, 1);

        board.putPawn(blackPawn);
        board.putPawn(whitePawn);

        assertTrue(board.isTerritory(board.getboard()[2][1], "black"));
        assertFalse(board.isTerritory(board.getboard()[1][1], "white"));
    }

    @Test
    public void testMainTerritoryPawns() {
        Pawn blackPawn = new BlackPawn(1, 1);
        Pawn whitePawn = new WhitePawn(0, 1);

        board.putPawn(blackPawn);
        board.putPawn(whitePawn);

        assertEquals(0, board.mainTerritoryPawns("black").size());
        assertEquals(0, board.mainTerritoryPawns("white").size());
    }

    @Test
    public void testClearChecked() {
        Pawn blackPawn = new BlackPawn(1, 1);
        board.putPawn(blackPawn);

        board.clearChecked();

        assertFalse(blackPawn.getChecked());
    }

}
