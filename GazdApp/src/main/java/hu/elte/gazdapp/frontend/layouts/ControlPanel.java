/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.frontend.layouts;

import hu.elte.gazdapp.backend.domain.Player;
import static hu.elte.gazdapp.controller.MainController.REPAY_AMOUNT;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import hu.elte.gazdapp.frontend.GuiManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    private JButton propertyButton;
    private JButton loanButton;
    private JButton repayButton;

    private GuiManager gui;

    public ControlPanel(GuiManager gui) {
        this.gui = gui;
        init();
    }

    private void init() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 70, 10));
        setLayout(new FlowLayout());
        add(infoPanel);
        add(buttonPanel);
        setPreferredSize(new Dimension(1100, 100));
        initLabels(infoPanel);
        initButtons(buttonPanel);
        addKeyListener(new HackToMove());
        setFocusable(true);
    }

    private void initLabels(JPanel infoPanel) {
        playerPiece = new JLabel();
        playerMoney = new JLabel();
        playerPosition = new JLabel();
        infoPanel.add(playerPiece);
        infoPanel.add(playerMoney);
        infoPanel.add(playerPosition);
    }

    private void initButtons(JPanel buttonPanel) {
        diceRollerButton = new JButton("Dobás");
        diceRollerButton.addActionListener(this::doRoll);
        diceRollerButton.setEnabled(false);
        buttonPanel.add(diceRollerButton);
        endRoundButton = new JButton("Kör vége");
        endRoundButton.addActionListener(this::endRound);
        endRoundButton.setEnabled(false);
        buttonPanel.add(endRoundButton);
        purchaseButton = new JButton("Vásárlás");
        purchaseButton.addActionListener(this::doPurchase);
        purchaseButton.setEnabled(false);
        buttonPanel.add(purchaseButton);
        propertyButton = new JButton("Tulajdonaim");
        propertyButton.addActionListener(this::viewProperties);
        propertyButton.setEnabled(false);
        buttonPanel.add(propertyButton);
        loanButton = new JButton("Hitel felvétel");
        loanButton.addActionListener(this::takeLoan);
        loanButton.setEnabled(false);
        buttonPanel.add(loanButton);
        repayButton = new JButton("Törleszt 5000€");
        repayButton.addActionListener(this::repay);
        repayButton.setEnabled(false);
        buttonPanel.add(repayButton);
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
    
    private void viewProperties(ActionEvent event) {
        gui.viewProperties();
        
    }
    
    private void takeLoan(ActionEvent event) {
        gui.takeLoan();
    }
    
    private void repay(ActionEvent event){
        gui.repay(REPAY_AMOUNT);
    }
    
    private void refresh(ActionEvent event) {
        gui.update();
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

    public void update()  {
        Player p = gui.getCurrentPlayer();
        if (p != null) {
            if (p.getName().equals(gui.ourPlayerName())) {
                loadButttons(p);
            }
            else{
                disableButtons(p);
            }
        }
    }
    
     private void loadButttons(Player p) {
        propertyButton.setEnabled(true);
        diceRollerButton.setEnabled(!endRoundButton.isEnabled());
        purchaseButton.setEnabled(gui.isAnyPurchasAbleItem());
        loanButton.setEnabled(gui.canLoan());
        repayButton.setEnabled(gui.canRepay());
        playerMoney.setText("Pénz: " + p.getMoney() + " €");
        playerPiece.setText("Játékos: " + p.getName() + " \tSzín: " + p.getPiece());
        playerPosition.setText("Pozíció: " + p.getPosition());
    }
     private void disableButtons(Player p) {
        propertyButton.setEnabled(false);
        diceRollerButton.setEnabled(false);
        purchaseButton.setEnabled(false);
        loanButton.setEnabled(false);
        repayButton.setEnabled(false);
        playerMoney.setText("Pénz: " + p.getMoney() + " €");
        playerPiece.setText("Játékos: " + p.getName() + " \tSzín: " + p.getPiece());
        playerPosition.setText("Pozíció: " + p.getPosition());
    }
    
    class HackToMove extends JPanel implements KeyListener{
        String n ="";
        public void keyTyped(KeyEvent e) {
           
        }
        @Override
        public void keyPressed(KeyEvent e) {
            if(n.length()>2) n ="";
            try {   
                Integer.parseInt(""+e.getKeyChar());
                n+= e.getKeyChar();
                if(n.length()==2){
                    gui.hackMove(Integer.parseInt(n.replaceFirst("^0+(?!$)", "")));               
                    n="";
                }
                
            } catch (Exception ex) {
            }

        }
        @Override
        public void keyReleased(KeyEvent e) {
           
        }
    }
}
