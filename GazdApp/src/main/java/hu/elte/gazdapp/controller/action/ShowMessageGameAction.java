/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.controller.action;

import hu.elte.gazdapp.backend.domain.BoardInterface;
import java.rmi.RemoteException;
import javax.swing.JOptionPane;

/**
 *
 * @author endrefulop
 */
public class ShowMessageGameAction implements GameAction {

    private String message;
    private BoardInterface board;

    public ShowMessageGameAction(String message, BoardInterface board) {
        this.message = message;
        this.board = board;
    }

    @Override
    public void execute() throws RemoteException {
        board.setMessage(board.getCurrentPlayer().getName() + ": "+ message);
    }

}
