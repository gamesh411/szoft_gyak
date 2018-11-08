/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.controller.action;

import hu.elte.gazdapp.backend.domain.Board;
import hu.elte.gazdapp.backend.domain.Player;
import hu.elte.gazdapp.backend.domain.component.Property;

public class InsuranceCheckAction implements GameAction {

    private Board board;
    private int cost;

    public InsuranceCheckAction(Board board) {
        this.board = board;
    }

    @Override
    public void execute() {
        Player p = board.getCurrentPlayer();
        if(p.getProperties().contains(Property.INSURANCE)){
            p.spendMoney(-5000);
            p.getProperties().remove(Property.INSURANCE);
            board.queueLateAction(new ShowMessageGameAction("A biztosító 5000 Eurót fizetett"));
        }
    }

}
