package com.example.s;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.example.s.engine.EngineFactory;
import com.example.s.engine.IEngine;
import com.example.s.logger.MyLogger;
import com.example.s.players.IPlayer;
import com.example.s.players.Player;

public class Server
{    
    public static void main(String[] args) 
    {
        MyLogger.loggerConfig();
        start();
    }

    private static void start()
    {
        try (ServerSocket serverSocket = new ServerSocket(8888)) 
        {
            EngineFactory engineFactory = new EngineFactory();
            System.out.println("Server is listening on port 8888");
 
            while (true) 
            {
                Socket playerSocket = serverSocket.accept();
                System.out.println("Dolaczyl nowy uzytkownik!");
                System.out.println(playerSocket);

                ClientInputReader inputReader = new ClientInputReader(playerSocket);
                System.out.println(inputReader);

                // oczekujemy stringa w postaci: "pvp/bot 9x9/13x13/19x19" update: ZROBIONE I TAK SIE DZIEJE
                String userPreferences = inputReader.readInput();
                IPlayer player = new Player(playerSocket, inputReader);

                IEngine engine = engineFactory.getEngine(userPreferences, player);

                if (engine == null)
                {
                    continue;
                }

                engine.start();
            }
 
        } catch (IOException ex) {
            System.out.println("Serwer o takim adresie juz istnieje");
        }
    }
}