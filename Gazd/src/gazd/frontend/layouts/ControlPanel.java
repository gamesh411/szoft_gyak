/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.frontend.layouts;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import gazd.frontend.GuiManager;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author <Andó Sándor Zsolt>
 */
public class ControlPanel extends JPanel{
    
    private JLabel playerPiece;
    private JLabel playerMoney;
    private JLabel playerPosition;
    private JButton diceRollerButton;

    private GuiManager gui;

    public ControlPanel(GuiManager gui) {
	this.gui = gui;
        init();
    }
    
    private void init(){
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(1100, 100));
        initLabels();
        initButtons();
    }

    private void initLabels() {
        playerPiece = new JLabel("Jatekos szine");
        playerMoney = new JLabel("Jatekos penze");
        playerPosition = new JLabel("Jatekos helyzete");
        add(playerPiece);
        add(playerMoney);
        add(playerPosition);
    }

    private void initButtons() {
        diceRollerButton = new JButton("Dobás");
        diceRollerButton.addActionListener(this::doRoll);
        add(diceRollerButton);
    }
    
    private void doRoll(ActionEvent event){
        gui.doRoll();
    }

    public JLabel getCurrentPlayerPieceLabel() {
    	return playerPiece;
    }

    public JLabel getCurrentPlayerMoneyLabel() {
    	return playerMoney;
    }

    public JLabel getCurrentPlayerPositionLabel() {
    	return playerPosition;
    }
    
}
