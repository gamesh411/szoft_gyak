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
public class RelativeMoveAction implements GameAction {

    private final Board board;
    private final int relativePosition;

    public RelativeMoveAction(Board board, GuiManager gui, int relativePosition) {
        this.board = board;
        this.relativePosition = relativePosition;
    }

    @Override
    public void execute() {
        board.stepOn(board.getCurrentPlayer().getPosition()+relativePosition);
    }

}
