/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.controller;

import gazd.backend.IGameAction;
import gazd.backend.Board;
import gazd.frontend.GuiManager;

/**
 *
 * @author endrefulop
 */
public class StepAction implements IGameAction {

    private Board board;

    public StepAction(Board board, GuiManager gui) {
        this.board = board;
    }

    @Override
    public void execute() {
        board.step();
    }

}
