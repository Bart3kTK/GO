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
    private Text player1;
    private Text player2;
    private Text player11;
    private Text player21;
    private Text server;
    private boolean isMyTurn = false;
    private boolean isRunning = true;
    private int size;
    private boolean isPaused = false;
    

    public ClientConnection(Pane pane, Text[] texts, Button[] buttons,Label[] labels, TextField[] textFields, String gameType, int size) throws UnknownHostException, IOException {
        this.pane = pane;
        this.passButton = buttons[0];
        this.surrenderButton = buttons[1];
        this.player1 = texts[0];
        this.player2 = texts[1];
        this.player11 = texts[4];
        this.player21 = texts[5];
        this.server = texts[2];
        this.size = size;

        socket = new Socket("localhost", 8888); 
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        buttons[2].setVisible(false);
        buttons[3].setVisible(false);
        buttons[4].setVisible(false);
        texts[3].setVisible(false);
        textFields[0].setVisible(false);
        initBoard();
        events();
        Platform.runLater(() -> {
            player1.setText("Player1 : 0");
            player2.setText("Player2 : 0");
            player11.setText("Territory : 0");
            player21.setText("Territory : 0");
        });
        out.println(gameType + " " + pawnsGrid.length);
    }



    private void events()
    {
        passButton.setOnMouseClicked(e -> {

            if(isMyTurn)
            {
                out.println("pass");
                isMyTurn = false;
            }
            if (isPaused) {
                out.println("end");
                isPaused = false;
                Platform.runLater(() -> {
                    surrenderButton.setText("Surrender");
                    passButton.setText("Pass");
                });
            }
        });

        surrenderButton.setOnMouseClicked(e -> {
            if(isMyTurn)
            {
                out.println("surrender");
                isMyTurn = false;
            }
            if (isPaused) {
                out.println("continue");
                isPaused = false;
                Platform.runLater(() -> {
                    surrenderButton.setText("Surrender");
                    passButton.setText("Pass");
                });
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
                    handleServerCommand(mess);
        
                }
                else
                {
                    stop();
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
        Platform.runLater(() -> {
            server.setText("Server is down!");
        });
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
                    player2.setText("Player2 : " + value);
                    break;
                case ("white"): //player1
                    player1.setText("Player1 : " + value);
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
            case ("pause"):
                isPaused = true;
                Platform.runLater(() -> {
                    surrenderButton.setText("Continue");
                    passButton.setText("End");
                    server.setText("Game is paused after double pass.\nClick continue to resume or end to finish game.");
                });
            case ("done"):
                Platform.runLater(() -> {
                    for (GUIPawn[] row : pawnsGrid) {
                        for (GUIPawn pawn : row) {
                                pawn.unlock();

                            
                        }
                    }
                    isMyTurn = true;
                });
                break;
            default:
                break;
        }
    }
    private void handleThreeWordCommand(String[] splitedCommand){
        int row = Integer.parseInt(splitedCommand[0]);
        int column = Integer.parseInt(splitedCommand[1]);
        String color = splitedCommand[2];
        if (!color.equals("territory"))
        {
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
        else
        {
            int whiteTerritory = Integer.parseInt(splitedCommand[0]);
            int blackTerritory = Integer.parseInt(splitedCommand[1]);
            Platform.runLater(() -> {
                player11.setText("Territory : " + whiteTerritory);
                player21.setText("Territory : " + blackTerritory);
            });
        }
        
    }
    private void initBoard()
    {

        pawnsGrid = new GUIPawn[size][size];
        Platform.runLater(() -> {
            for (int i = 0; i< size * size; i++)
            {
                GUISquare sq = new GUISquare(size);
                pane.getChildren().add(sq);
                GUIPawn pawn = new GUIPawn(sq.getXpos(), sq.getYpos(), sq.getRow(), sq.getColumn());
                pawnsGrid[sq.getRow()][sq.getColumn()] = pawn;
                pane.getChildren().add(pawn);
    
            }
        });
    }
}
