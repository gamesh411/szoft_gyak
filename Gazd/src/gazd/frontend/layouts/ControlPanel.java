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
public class ControlPanel extends JPanel {

    private JLabel playerPiece;
    private JLabel playerMoney;
    private JLabel playerPosition;
    private JButton diceRollerButton;
    private JButton endRoundButton;
    private JButton purchaseButton;

    private GuiManager gui;

    public ControlPanel(GuiManager gui) {
        this.gui = gui;
        init();
    }

    private void init() {
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
        purchaseButton = new JButton("Vásárlás");
        purchaseButton.addActionListener(this::doPurchase);
        purchaseButton.setEnabled(false);
        add(purchaseButton);
    }

    private void doRoll(ActionEvent event) {
        gui.doRoll();
        diceRollerButton.setEnabled(false);
        endRoundButton.setEnabled(true);
    }

    private void endRound(ActionEvent event) {
        gui.endRound();
        diceRollerButton.setEnabled(true);
        endRoundButton.setEnabled(false);
    }
    
    private void doPurchase(ActionEvent event) {      
        gui.doPurchase();
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

    public void update() {
        Player p = gui.getCurrentPlayer();
        if (p != null) {
            diceRollerButton.setEnabled(!endRoundButton.isEnabled());
            purchaseButton.setEnabled(true);
            playerMoney.setText("Pénz: " + p.getMoney());
            playerPiece.setText("Név: " + p.getName()+" Szín:"+ p.getPiece());
            playerPosition.setText("Pozíció: " + p.getPosition());
        }

    }
}
