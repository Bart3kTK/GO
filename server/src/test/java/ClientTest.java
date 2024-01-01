import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
// kalsa do testow docelowo jej tu nie bedzie
public class ClientTest {
    private String gameType = "bot";
    private String mapSize = "9x9";

    private Socket socket; 
    private PrintWriter out;
    private BufferedReader in;

    public ClientTest(String gameType, String mapSize)
    {
        this.gameType = gameType;
        this.mapSize = mapSize;

        try
        {
            this.socket = new Socket("localhost", 8888);
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch (Exception e)
        {
            
        }
        
        out.println(this.gameType + " " + this.mapSize);
    }
    
    public void wariteOutput(String message)
    {
        out.println(message);
    }

    public String readInput() throws IOException
    {
        return in.readLine();
    }
        
}
