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
            // System.out.println(socket);
        }
        catch (Exception e)
        {
            
        }
        
        out.println(this.gameType + " " + this.mapSize + "\n");
    }
    
    public void wariteOutput(String message)
    {
        out.println(message + "\n");
    }

    public String readInput() throws IOException
    {
        String line = null;
        while(line == null)
        {
            line = in.readLine();
        }
        return line;
    }
        
}
