package com.example.xd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class ClientConnection implements Runnable{
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private GUIPawn[][] pawnsGrid;
    private Pane pane;
    private Button passButton;
    private Button surrenderButton;
    private Button left;
    private Button right;
    private Button confirm;
    private Text player1;
    private Text player2;
    private Text server;
    private TextField area;
    private Label label;
    private boolean isMyTurn = false;
    private boolean isRunning = true;
    private int size;
    private String gameType;
    

    public ClientConnection(Pane pane, Text[] texts, Button[] buttons,Label[] labels, TextField[] textFields, String gameType, int size) throws UnknownHostException, IOException {
        this.pane = pane;
        this.passButton = buttons[0];
        this.surrenderButton = buttons[1];
        this.left = buttons[2];
        this.right = buttons[3];
        this.confirm = buttons[4];
        this.player1 = texts[0];
        this.player2 = texts[1];
        this.server = texts[2];
        this.area = textFields[0];
        this.label = labels[0];
        this.size = size;
        this.gameType = gameType;

        socket = new Socket("localhost", 8888); 
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        if (!gameType.equals("replay"))
        {
            left.setVisible(false);
            right.setVisible(false);
            confirm.setVisible(false);
            area.setVisible(false);
            initBoard();
            events();
            out.println(gameType + " " + pawnsGrid.length);
        }
        else
        {
            surrenderButton.setVisible(false);
            passButton.setVisible(false);
            player1.setVisible(false);
            player2.setVisible(false);
            out.println(gameType + " " + 888);
        }
    }



    private void events()
    {
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

            boolean messageisSent = false;

            for (GUIPawn[] row : pawnsGrid) {
                for (GUIPawn pawn : row) {
                    if (pawn.isClicked())
                    {
                        out.println(pawn.getRow() + " " + pawn.getColumn());
                        isMyTurn = false;
                        messageisSent = true; 
                    }                       
                }
            }
            if (messageisSent)
            {
                for (GUIPawn[] row : pawnsGrid) {
                    for (GUIPawn pawn : row) {
                        pawn.setClicked(false);
                        pawn.lock();
                    }
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
                    if(!gameType.equals("replay")) handleServerCommand(mess);
                    else handleReplayCommand(mess);

                    
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
        System.out.println("ELo dostałem: " + command);
        String[] splitedCommands = command.split(";");

        for (String splitedCommand : splitedCommands){
            String[] splitedCommand2 = splitedCommand.split(" ");

            if(splitedCommand2.length == 1)
            {
                handleOneWordCommand(splitedCommand2);
            }
            else if(splitedCommand2.length == 2)
            {
                handleTwoWordCommand(splitedCommand2);
            }
            else if(splitedCommand2.length == 3)
            {
                handleThreeWordCommand(splitedCommand2);
            }
        }
    }
    private void handleTwoWordCommand(String[] splitedCommand) {
;
        String type = splitedCommand[0];
        String value = splitedCommand[1];

        Platform.runLater(() -> {
            switch (type){
                case ("black"): //player2
                    player2.setText(value);
                    break;
                case ("white"): //player1
                    player1.setText(value);
                    break;
                case ("server"): //server message
                    server.setText(value);
                    break;
                default:
                    break;
        };
        });
    }
    private void handleOneWordCommand(String[] splitedCommand){
        String command = splitedCommand[0];
        switch (command){
            case ("surrender"):
                isMyTurn = true; //TODO: do przemyslenia
                break;
            case ("pass"):
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
        System.out.println("row: " + row + " column: " + column + " color: " + color);
        GUIPawn pawn = pawnsGrid[row][column];
        Platform.runLater(() -> {
            switch (color){
                case ("black"):
                    pawn.setBlack();
                    break;
                case ("white"):
                    pawn.setWhite();
                    break;
                case ("clear"):
                    pawn.setClear();
                    break;
                default:
                    break;
        };
        });
    }
    private void handleReplayCommand(String command)
    {
        String[] splitedString = command.split(" ");
    }
    private void initBoard()
    {

        pawnsGrid = new GUIPawn[size][size];

        for (int i = 0; i< size * size; i++)
        {
            GUISquare sq = new GUISquare(size);
            pane.getChildren().add(sq);
            GUIPawn pawn = new GUIPawn(sq.getXpos(), sq.getYpos(), sq.getRow(), sq.getColumn());
            pawnsGrid[sq.getRow()][sq.getColumn()] = pawn;
            pane.getChildren().add(pawn);

        }
    }
}
