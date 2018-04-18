/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.frontend.layouts;

import gazd.backend.Player;
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
    private JButton endRoundButton;

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
        playerPiece = new JLabel();
        playerMoney = new JLabel();
        playerPosition = new JLabel();
        add(playerPiece);
        add(playerMoney);
        add(playerPosition);
    }

    private void initButtons() {
        diceRollerButton = new JButton("Dobás");
        diceRollerButton.addActionListener(this::doRoll);
        add(diceRollerButton);
        diceRollerButton.setEnabled(false);
        endRoundButton = new JButton("Kör vége");
        endRoundButton.addActionListener(this::endRound);
        endRoundButton.setEnabled(false);
        add(endRoundButton);
    }
    
    private void doRoll(ActionEvent event){
        gui.doRoll();
        diceRollerButton.setEnabled(false);
        endRoundButton.setEnabled(true);
    }
    
    private void endRound(ActionEvent event){
        gui.endRound();
        diceRollerButton.setEnabled(true);
        endRoundButton.setEnabled(false);
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
    
    public void update(){
        diceRollerButton.setEnabled(!endRoundButton.isEnabled());
        Player p = gui.getCurrentPlayer();
        playerMoney.setText(""+p.getMoney());
        playerPiece.setText(""+p.getPiece());
        playerPosition.setText(""+p.getPosition());
    }
}
