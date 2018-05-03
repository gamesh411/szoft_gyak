/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.controller.action;

import gazd.backend.IGameAction;
import gazd.backend.Board;
import gazd.backend.Player;


public class InterestAction implements IGameAction {

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

