/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.controller.action;

import gazd.backend.IGameAction;
import gazd.backend.Board;
import gazd.frontend.GuiManager;

/**
 *
 * @author endrefulop
 */
public class MoveAction implements IGameAction {

    private Board board;
    private int newPosition;

    public MoveAction(Board board, GuiManager gui, int newPosition) {
        this.board = board;
        this.newPosition = newPosition;
    }

    @Override
    public void execute() {
        board.stepOn(newPosition);
    }

}
