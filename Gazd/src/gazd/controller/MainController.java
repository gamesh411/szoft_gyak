/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.controller;

import gazd.backend.*;
import gazd.frontend.GuiManager;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author MetaPC
 */
public class MainController {
    
    Board board;
    GuiManager gui;
    
    public void newGame(GuiManager gui){
       this.gui = gui;
       board = new Board();
       board.addPlayer(new Player(Piece.RED));
       board.addPlayer(new Player(Piece.BLUE));
       board.addPlayer(new Player(Piece.GREEN));
       board.addPlayer(new Player(Piece.YELLOW));
       board.addPlayer(new Player(Piece.PURPLE));
       board.start();
    }
    
    public void onRoll(){
        board.queueLateAction(new MoveGameAction(board, gui));
        board.queueLateAction(new NextPlayerGameAction(board, gui));

        board.doTurn();
    }
    
    
    public List<Player> getPlayers() {
        return board.getPlayers();
    }
    
    
}
