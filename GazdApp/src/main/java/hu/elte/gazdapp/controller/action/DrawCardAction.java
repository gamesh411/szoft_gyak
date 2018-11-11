/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.controller.action;

import hu.elte.gazdapp.backend.domain.Board;
import hu.elte.gazdapp.backend.domain.BoardInterface;
import java.rmi.RemoteException;

/**
 *
 * @author endrefulop
 */
public class DrawCardAction implements GameAction {

    private final BoardInterface board;

    public DrawCardAction(BoardInterface board) {
        this.board = board;
    }

    @Override
    public void execute() throws RemoteException {
        board.drawCard();
    }

}
