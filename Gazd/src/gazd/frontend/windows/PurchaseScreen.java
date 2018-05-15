/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.frontend.windows;

import gazd.frontend.GuiManager;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author <Andó Sándor Zsolt>
 */
public class PurchaseScreen extends JFrame {

    private GuiManager gui;
    private JTable ownedItemsTable;
    private JComboBox availableItemsComboBox;
    private JButton buySelectedItemButton, cancelButton; 

    public PurchaseScreen(GuiManager gui) {
        this.gui = gui;
        setLayout(new GridBagLayout());
        initOwnedItemsTable();
        initavailableItemsComboBox();
        initButtons();
    }

    private void initOwnedItemsTable() {
        //TODO: make COLUMN_NAMES_CONSTANT
        ownedItemsTable = new JTable();
        fillOwnedItemsTableWithContent();
        add(ownedItemsTable);
    }

    private void initavailableItemsComboBox() {
        availableItemsComboBox = new JComboBox();
        JLabel label = new JLabel("Megvásárolható elemek");
        add(label);
        add(availableItemsComboBox);
        
        /*gui.getAllItems().foreach(item -> {
            availableItemsComboBox.addItem(item);
        });*/
    }

    private void initButtons() {
        buySelectedItemButton = new JButton("Kijelölt elem megvásárlása");
        cancelButton = new JButton("Inkább mégse vásárlok be");
        
        buySelectedItemButton.addActionListener(this::buySelectedItem);
        cancelButton.addActionListener(this::exitFromPurchasewindow);
        
        add(buySelectedItemButton);
        add(cancelButton);
    }

    private void fillOwnedItemsTableWithContent() {
        //TODO: make COLUMN_NAMES_CONSTANT
        /*ownedItemsTable.removeAll();
        DefaultTableModel dtm = new DefaultTableModel(COLUMN_NAMES_CONSTANT, 0);

        gui.getCurrentPlayersItems().foreach(row -> {
            dtm.addRow(row);
        });
        ownedItemsTable.setModel(dtm);*/
    }
    
    private void buySelectedItem(ActionEvent event){
        //gui.buySelectedItem(availableItemsComboBox.getSelectedItem());
        fillOwnedItemsTableWithContent();
    }
    
    private void exitFromPurchasewindow(ActionEvent event){
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

}
