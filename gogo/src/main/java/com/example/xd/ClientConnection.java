package com.example.xd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class ClientConnection implements Runnable{
    private String host;
    private int port;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private GUIPawn[][] pawnsGrid;
    private Pane pane;
    private Button passButton;
    private Button surrenderButton;
    private boolean isMyTurn = false;
    private boolean isRunning = true;
    

    public ClientConnection(String host, int port, GUIPawn[][] pawnsGrid, Pane pane, Button passButton, Button surrenderButton) throws UnknownHostException, IOException {
        this.host = host;
        this.port = port;
        this.pawnsGrid = pawnsGrid;
        this.pane = pane;
        this.passButton = passButton;
        this.surrenderButton = surrenderButton;

        socket = new Socket(host, port); 
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out.println("elo 123");

        passButton.setOnMouseClicked(e -> {

            if(isMyTurn)
            {
                out.println("pass");
                isMyTurn = false;
            }
        });

        surrenderButton.setOnMouseClicked(e -> {
            if(isMyTurn)
            {
                out.println("surrender");
                isMyTurn = false;
            }
        });

        pane.setOnMouseClicked(e -> {
            if (!isMyTurn)
            {
                return;
            }

            for (GUIPawn[] row : pawnsGrid) {
                for (GUIPawn pawn : row) {
                    if (pawn.isClicked() && !pawn.isUsed())
                    {
                        out.println(pawn.getRow() + " " + pawn.getColumn());
                        pawn.setUsed(true);
                        pawn.setClicked(false);
                        isMyTurn = false;
                    }
                    pawn.lock();
                    
                }

            }
        });

    }
    @Override
    public void run() {
        try
        {
            while(isRunning)
            {
                String mess = in.readLine();
                if (mess != null)
                {   
                    handleServerCommand(mess);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeSocket();
        }

    }

    public void stop(){
        isRunning = false;
        closeSocket();
    }

    private void closeSocket()
    {
        try 
        {
            if (socket != null && !socket.isClosed())
            {
                socket.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void handleServerCommand(String command)
    {

        String[] splitedCommand = command.split(" ");
        if(splitedCommand.length == 1)
        {
            handleOneWordCommand(splitedCommand);
        }
        else if(splitedCommand.length == 3)
        {
            handleThreeWordCommand(splitedCommand);
        }
    }
    private void handleOneWordCommand(String[] splitedCommand){
        String command = splitedCommand[0];
        switch (command){
            case ("pass"):
                isMyTurn = true;
                break;
                case ("surrender"):
                    isMyTurn = true; //TODO: do przemyslenia
                    break;
                case ("done"):
                    for (GUIPawn[] row : pawnsGrid) {
                        for (GUIPawn pawn : row) {
                            pawn.unlock();
                        }
                    }
                    isMyTurn = true;
                    break;
            default:
                break;
        }
    }

    private void handleThreeWordCommand(String[] splitedCommand){
        int row = Integer.parseInt(splitedCommand[0]);
        int column = Integer.parseInt(splitedCommand[1]);
        String color = splitedCommand[2];
        GUIPawn pawn = pawnsGrid[row][column];
        if (color.equals("done")) {
            isMyTurn = true;
        }
        Platform.runLater(() -> {
            switch (color){
                case ("black"):
                    pawn.setBlack();
                    pawn.setUsed(true);
                    break;
                case ("white"):
                    pawn.setWhite();
                    pawn.setUsed(true);
                    break;
                case ("clear"):
                    pawn.setClear();
                    pawn.setUsed(false);
                    break;
                default:
                    break;

        };
        });
    }
}
