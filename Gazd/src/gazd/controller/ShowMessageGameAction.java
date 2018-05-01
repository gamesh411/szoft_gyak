/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.controller;

import gazd.backend.IGameAction;
import javax.swing.JOptionPane;

/**
 *
 * @author endrefulop
 */
public class ShowMessageGameAction implements IGameAction {

    private String message;

    public ShowMessageGameAction(String message) {
        this.message = message;
    }

    @Override
    public void execute() {
        JOptionPane.showMessageDialog(null, message);
    }

}
