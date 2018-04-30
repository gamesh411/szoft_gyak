/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.frontend.windows;

import gazd.backend.Piece;
import gazd.frontend.GuiManager;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author <Andó Sándor Zsolt>
 */
public class InitGameScreen extends JFrame {

    private final GuiManager gui;
    private JComboBox colorsComboBox;
    private JLabel playerNameTFLabel;
    private JTextField playerNameTextField;
    private JButton addPlayerButton, finishNewGameInitializationButton;

    public InitGameScreen(GuiManager gui) {
        this.gui = gui;
        initNewGameScreen();
    }

    private void initNewGameScreen() {
        setLayout(new FlowLayout());
        colorsComboBox = new JComboBox(gui.getColors());
        playerNameTFLabel = new JLabel("Játékos neve: ");
        playerNameTextField = new JTextField("", 30);
        addPlayerButton = new JButton("Játékos hozzáadása");
        finishNewGameInitializationButton = new JButton("Játék kezdése");

        addPlayerButton.addActionListener(this::addPlayer);
        finishNewGameInitializationButton.addActionListener(this::closeWindowAndStartGame);

        add(colorsComboBox);
        add(playerNameTFLabel);
        add(playerNameTextField);
        add(addPlayerButton);
        add(finishNewGameInitializationButton);
    }

    private void addPlayer(ActionEvent e) {
        if (playerNameTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Üresen hagyott játékosnév mező");
        }else{
            gui.addPlayer(playerNameTextField.getText(), (Piece) colorsComboBox.getSelectedItem());
        }
    }

    private void closeWindowAndStartGame(ActionEvent e) {
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        gui.startGame();
    }
}
