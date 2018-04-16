/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.controller;

import gazd.backend.IGameAction;
import gazd.backend.Board;
import gazd.frontend.GuiManager;

/**
 *
 * @author endrefulop
 */
public class NextPlayerGameAction implements IGameAction {
    
    Board board;

    public NextPlayerGameAction(Board board, GuiManager gui) {
        this.board = board;
    }
    

    @Override
    public void execute() {
        board.nextPlayer();
    }
    
}
