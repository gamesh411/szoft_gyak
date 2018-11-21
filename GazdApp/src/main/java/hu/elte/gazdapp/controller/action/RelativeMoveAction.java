/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.controller.action;

import hu.elte.gazdapp.backend.domain.Board;
import hu.elte.gazdapp.backend.domain.BoardInterface;
import hu.elte.gazdapp.frontend.GuiManager;
import java.rmi.RemoteException;

/**
 *
 * @author endrefulop
 */
public class RelativeMoveAction implements GameAction {

    private final BoardInterface board;
    private final int relativePosition;

    public RelativeMoveAction(BoardInterface board, GuiManager gui, int relativePosition) {
        this.board = board;
        this.relativePosition = relativePosition;
    }

    @Override
    public void execute() throws RemoteException {
        board.stepOn(board.getCurrentPlayer().getPosition()+relativePosition);
    }

}
