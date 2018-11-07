/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.controller.action;

import hu.elte.gazdapp.backend.domain.Board;
import hu.elte.gazdapp.frontend.GuiManager;

/**
 *
 * @author endrefulop
 */
public class StepAction implements GameAction {

    private Board board;

    public StepAction(Board board, GuiManager gui) {
        this.board = board;
    }

    @Override
    public void execute() {
        board.step();
    }

}
