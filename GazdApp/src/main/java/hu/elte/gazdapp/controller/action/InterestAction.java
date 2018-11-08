/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.controller.action;

import hu.elte.gazdapp.backend.domain.Board;
import hu.elte.gazdapp.backend.domain.Player;


public class InterestAction implements GameAction {

    private Board board;
    private int interest;

    public InterestAction(Board board, int interest) {
        this.board = board;
        this.interest = interest;
    }

    @Override
    public void execute() {
        Player p = board.getCurrentPlayer();
        int money = p.getMoney();
        System.out.println(money);
        money = (int)(money *((100.0+interest)/100.0));
        System.out.println(money);
        board.getCurrentPlayer().setMoney(money);
    }

}

