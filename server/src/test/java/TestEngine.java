// import static org.junit.Assert.assertTrue;

// import java.io.IOException;

// import org.junit.Before;
// import org.junit.Test;

// import com.example.s.Server;

// public class TestEngine 
// {
//     private ClientTest player1;
//     private ClientTest player2;

//     @Before
//     public void connectPlayers()
//     {
//         // Server.main(null);
//         System.out.println("mamy maina");
//         this.player1 = new ClientTest("pvp", "9x9");
//         this.player2 = new ClientTest("pvp", "9x9");
//     }

//     @Test
//     public void testPlayer1PutPawn() throws IOException
//     {
//         player1.wariteOutput("8 8");
//         assertTrue("8 8 White".equals(player1.readInput()));
//     }

//     @Test
//     public void testMoveCollision() throws IOException
//     {
//         player2.wariteOutput("8 8");
//         assertTrue(player2.readInput().equals("incorrect position"));
//     }
// }
