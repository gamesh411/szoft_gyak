/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.frontend.windows;

import gazd.backend.Property;
import gazd.frontend.GuiManager;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.util.List;
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
    private JComboBox<Property> availableItemsComboBox;
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
        JLabel label2 = new JLabel("Sajat:");
        add(label2);
        add(ownedItemsTable);
    }

    private void initavailableItemsComboBox() {
        availableItemsComboBox = new JComboBox<>();
        JLabel label = new JLabel("Megvásárolható elemek");
        
        add(label);
        add(availableItemsComboBox);
        
        List<Property> l = gui.getAllItems();
        l.removeAll(gui.getCurrentPlayersItems());
        
        l.forEach(availableItemsComboBox::addItem);
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
        ownedItemsTable.removeAll();
        DefaultTableModel dtm = new DefaultTableModel(new String[]{"Tulajdon"}, 0);
        
        gui.getCurrentPlayersItems().forEach(row -> {
            dtm.addRow(new Property[]{row});
        });
        ownedItemsTable.setModel(dtm);
        
    }
    
    private void buySelectedItem(ActionEvent event){
        Property selected = (Property)availableItemsComboBox.getSelectedItem();
        gui.buySelectedItem(selected);
        gui.checkGame();
        availableItemsComboBox.removeItem(selected);
        fillOwnedItemsTableWithContent();
    }
    
    private void exitFromPurchasewindow(ActionEvent event){
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

}
