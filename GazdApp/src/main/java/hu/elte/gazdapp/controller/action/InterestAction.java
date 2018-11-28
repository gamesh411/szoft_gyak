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
import java.rmi.RemoteException;


public class InterestAction implements GameAction {

    private BoardInterface board;
    private int interest;

    public InterestAction(BoardInterface board, int interest) {
        this.board = board;
        this.interest = interest;
    }

    @Override
    public void execute() throws RemoteException {
        PlayerInterface p = board.getCurrentPlayer();
        int money = p.getMoney();
        System.out.println(money);
        money = (int)(money *((100.0+interest)/100.0));
        System.out.println(money);
        board.getCurrentPlayer().setMoney(money);
    }

}

