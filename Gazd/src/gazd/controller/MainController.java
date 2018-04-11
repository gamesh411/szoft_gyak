/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.controller;

import gazd.backend.*;
import java.util.LinkedList;

/**
 *
 * @author MetaPC
 */
public class MainController {
    
    Board board;
    
    public void newGame(){
       board = new Board();
       board.addPlayer(new Player(Piece.RED));
       board.addPlayer(new Player(Piece.BLUE));
       board.addPlayer(new Player(Piece.GREEN));
       board.addPlayer(new Player(Piece.YELLOW));
       board.addPlayer(new Player(Piece.PURPLE));
       board.start();
    }
    
    public void round(){
        board.round();
    }
    
    
    public LinkedList<Player> getPlayers() {
        return board.getPlayers();
    }
    
    
}
