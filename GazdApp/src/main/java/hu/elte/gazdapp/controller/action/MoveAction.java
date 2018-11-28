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
public class MoveAction implements GameAction {

    private BoardInterface board;
    private int newPosition;

    public MoveAction(BoardInterface board, GuiManager gui, int newPosition) {
        this.board = board;
        this.newPosition = newPosition;
    }

    @Override
    public void execute() throws RemoteException {
        board.stepOn(newPosition);
    }

}
