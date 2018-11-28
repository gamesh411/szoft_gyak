/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.controller.action;

import hu.elte.gazdapp.backend.domain.Board;
import hu.elte.gazdapp.backend.domain.BoardInterface;
import hu.elte.gazdapp.backend.domain.Player;
import hu.elte.gazdapp.backend.domain.PlayerInterface;
import hu.elte.gazdapp.backend.domain.component.Property;
import java.rmi.RemoteException;

public class InsuranceCheckAction implements GameAction {

    private BoardInterface board;
    private int cost;

    public InsuranceCheckAction(BoardInterface board) {
        this.board = board;
    }

    @Override
    public void execute() throws RemoteException {
        PlayerInterface p = board.getCurrentPlayer();
        if(p.getProperties().contains(Property.INSURANCE)){
            p.spendMoney(-5000);
            p.getProperties().remove(Property.INSURANCE);
            board.queueLateAction(new ShowMessageGameAction("A biztosító 5000 Eurót fizetett", board));
        }
    }

}
