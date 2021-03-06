/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.controller.action;

import hu.elte.gazdapp.backend.domain.Board;
import hu.elte.gazdapp.backend.domain.BoardInterface;
import hu.elte.gazdapp.backend.state.DiceState;
import java.rmi.RemoteException;

/**
 *
 * @author MetaPC
 */
public class DiceStateAction implements GameAction {

    Integer[] diceNumbers;
    BoardInterface board;

    public DiceStateAction(BoardInterface board, Integer... diceNumbers) {
        this.diceNumbers = diceNumbers;
        this.board = board;
    }

    @Override
    public void execute() throws RemoteException {
        board.getCurrentPlayer().setState(new DiceState(diceNumbers));

    }

}
