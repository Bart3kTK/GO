package com.example.s.engine;

import java.util.LinkedList;
import java.util.Queue;

import com.example.s.board.Board;
import com.example.s.board.BoardFactory;
import com.example.s.players.BotPlayer;
import com.example.s.players.IPlayer;


/*
 * @brief engine factory wich produce new game engine
 * if player is putted into queue or sth goes wrong (for instance any player disconnects)
 * before game start, method return null
 */
public class EngineFactory 
{
    private BoardFactory boardFactory = new BoardFactory();
    Queue<IPlayer> queue9x9 = new LinkedList<IPlayer>();
    Queue<IPlayer> queue13x13 = new LinkedList<IPlayer>();
    Queue<IPlayer> queue19x19 = new LinkedList<IPlayer>();

    public Engine getEngine(String requirements, IPlayer player)
    {
        String[] splitedRequirements = requirements.split(" ");
        IPlayer player2 = null;

        Board gameBoard = boardFactory.createBoard(splitedRequirements[1]);

        if (splitedRequirements[0].equals("bot"))
        {
            int size = Integer.parseInt(splitedRequirements[1]);
            player2 = new BotPlayer(size, size);
        }
        else if(splitedRequirements[0].equals("replay"))
        {
            
        }

        if (splitedRequirements[1].equals("9"))
        {
            if (queue9x9.isEmpty() == false) 
            {
                player2 = queue9x9.poll();
            }
            else
            {
                queue9x9.add(player);
            }
        }
        else if (splitedRequirements[1].equals("13"))
        {
            if (queue13x13.isEmpty() == false) 
            {
                player2 = queue13x13.poll();
            }
            else
            {
                queue13x13.add(player);
            }
        }
        else if (splitedRequirements[1].equals("19"))
        {
            if (queue19x19.isEmpty() == false) 
            {
                player2 = queue19x19.poll();
            }
            else
            {
                queue19x19.add(player);
            }
        }

        if (player2 == null)
        {
            return null;
        }

        if (player.isConnected() && player2.isConnected())
        {
            return new Engine(player, player2, gameBoard);
        }

        return null;
    }
}
