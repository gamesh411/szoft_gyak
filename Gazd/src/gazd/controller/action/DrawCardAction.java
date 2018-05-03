/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.controller.action;

import gazd.backend.IGameAction;
import gazd.backend.Board;

/**
 *
 * @author endrefulop
 */
public class DrawCardAction implements IGameAction {

    private final Board board;

    public DrawCardAction(Board board) {
        this.board = board;
    }

    @Override
    public void execute() {
        board.drawCard();
    }

}
