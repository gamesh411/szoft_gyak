/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.controller.action;

import gazd.backend.domain.Board;
import gazd.frontend.GuiManager;

/**
 *
 * @author endrefulop
 */
public class NextPlayerGameAction implements GameAction {

    private Board board;

    public NextPlayerGameAction(Board board, GuiManager gui) {
        this.board = board;
    }

    @Override
    public void execute() {
        board.nextPlayer();
    }

}
