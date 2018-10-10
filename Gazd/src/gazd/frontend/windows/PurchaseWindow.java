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
import java.util.HashSet;
import java.util.Set;
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
public class PurchaseWindow extends JFrame {
    
    private static final String OWNED_PROPERTIES = "Eddigi tulajdon:";
    private static final String NO_PROPERTIES = "Még nincsen tulajdona!";

    private static final String AVAILABLE_ITEMS = "Megvásárolható elemek:";
    private static final String NO_MORE_ITEMS = "Nem maradt megvásárolható elem!";

    private GuiManager gui;
    private Set<Property> ownedItems;
    private Set<Property> availableItems;
    
    private JTable ownedItemsTable;
    
    private boolean hasAnyItems;
    private boolean canPurchaseAnyItems;

    private JLabel ownedItemsLabel;
    private JLabel availableItemsLabel;
    
    private JComboBox<Property> availableItemsComboBox;
    private JButton buySelectedItemButton, cancelButton;

    public PurchaseWindow(GuiManager gui) {
        this.gui = gui;
        onCreate();
        onUpdate();
    }
    
    private void onCreate() {
        setLayout(new GridBagLayout());
        //TODO: make COLUMN_NAMES_CONSTANT
        ownedItemsTable = new JTable();
        fillOwnedItemsTableWithContent();
        ownedItemsLabel = new JLabel();
        add(ownedItemsLabel);
        add(ownedItemsTable);
        initavailableItemsComboBox();
        initButtons();
    }
    
    private void onUpdate() {
        if (!ownedItems.isEmpty()) {
            ownedItemsLabel.setText(OWNED_PROPERTIES);
            ownedItemsTable.setVisible(true);
        } else {
            ownedItemsLabel.setText(NO_PROPERTIES);
            ownedItemsTable.setVisible(false);
        }
        
        if (!availableItems.isEmpty()) {
            availableItemsLabel.setText(AVAILABLE_ITEMS);
            availableItemsComboBox.setVisible(true);
        } else {
            availableItemsLabel.setText(NO_MORE_ITEMS);
            availableItemsComboBox.setVisible(false);
        }
    }
    

    private void initavailableItemsComboBox() {
        Set<Property> propertiesAvailableForPurchase = new HashSet<>(gui.getAllItems());
        propertiesAvailableForPurchase.removeAll(gui.getCurrentPlayersItems());
        
        if (propertiesAvailableForPurchase.isEmpty()) {
            JLabel noMoreItemsLabel = new JLabel(NO_MORE_ITEMS);
            add(noMoreItemsLabel);
            return;
        }
        
        availableItemsLabel = new JLabel(AVAILABLE_ITEMS);
        add(availableItemsLabel);
        
        availableItemsComboBox = new JComboBox<>();
        propertiesAvailableForPurchase.forEach(availableItemsComboBox::addItem);
        add(availableItemsComboBox);
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
