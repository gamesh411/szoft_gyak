/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.controller.action;

import javax.swing.JOptionPane;

/**
 *
 * @author endrefulop
 */
public class ShowMessageGameAction implements GameAction {

    private String message;

    public ShowMessageGameAction(String message) {
        this.message = message;
    }

    @Override
    public void execute() {
        JOptionPane.showMessageDialog(null, message);
    }

}
