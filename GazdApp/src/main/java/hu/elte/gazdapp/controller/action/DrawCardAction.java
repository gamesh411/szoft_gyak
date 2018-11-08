/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.controller.action;

import hu.elte.gazdapp.backend.domain.Board;

/**
 *
 * @author endrefulop
 */
public class DrawCardAction implements GameAction {

    private final Board board;

    public DrawCardAction(Board board) {
        this.board = board;
    }

    @Override
    public void execute() {
        board.drawCard();
    }

}
