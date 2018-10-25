/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.frontend.windows;

import gazd.backend.domain.component.Property;
import gazd.frontend.GuiManager;
import gazd.frontend.util.ScreenConstants;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.util.Set;
import java.util.stream.Collectors;
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
    
    private static final int WIDTH = ScreenConstants.PURCHASE_WINDOW_WIDTH.getValue();
    private static final int HEIGHT = ScreenConstants.PURCHASE_WINDOW_HEIGHT.getValue();
    private static final int ROW_HEIGHT = ScreenConstants.ROW_HEIGHT.getValue();

    private static final String OWNED_PROPERTIES = "Eddigi tulajdon:";
    private static final String NO_PROPERTIES = "Még nincsen tulajdona!";

    private static final String AVAILABLE_ITEMS = "Megvásárolható elemek:";
    private static final String NO_MORE_ITEMS = "Nem maradt megvásárolható elem!";

    private static final String PROPERTY_COLUMN_NAME = "Tulajdon";

    private static final String CANCEL_BUY_BUTTON = "Inkább mégse vásárlok be";
    private static final String CONFIRM_BUY_BUTTON = "Kijelölt elem megvásárlása";

    private final GuiManager gui;
    private Set<Property> ownedItems;
    private Set<Property> availableItems;
    private DefaultTableModel availableItemsModel;

    private JTable ownedItemsTable;
    private JLabel ownedItemsLabel;
    private JLabel availableItemsLabel;
    private JLabel money;
    private JComboBox<Property> availableItemsComboBox;
    private JButton buySelectedItemButton;
    private JButton cancelButton;

    public PurchaseWindow(GuiManager gui) {
        this.gui = gui;
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        onCreate();
    }

    /**
     * Set the static part of the window, which does not change over time. Meant
     * to be called once at the beginning of the windows life-cycle.
     */
    private void onCreate() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill= GridBagConstraints.HORIZONTAL;

        // Display items already owned.
        ownedItemsLabel = new JLabel();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(ownedItemsLabel, constraints);
        
        
        ownedItemsTable = new JTable();
        ownedItemsTable.setRowHeight(ROW_HEIGHT);
        constraints.insets = new Insets(0, 10, 10, 10);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(ownedItemsTable, constraints);
        
        money = new JLabel("Aktuális egyenleg: " + gui.getCurrentPlayer().getMoney());
        constraints.insets = new Insets(20, 10, 10, 10);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(money, constraints);

        // Display purchasable items.
        availableItemsLabel = new JLabel();
        constraints.insets = new Insets(10, 10, 10, 5);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridy = 0;
        add(availableItemsLabel, constraints);
        
        
        availableItemsComboBox = new JComboBox<>();
        constraints.insets = new Insets(10, 5, 10, 10);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 2;
        constraints.gridy = 0;
        add(availableItemsComboBox, constraints);

        // Display buttons for making or canceling a puchase.
        buySelectedItemButton = new JButton(CONFIRM_BUY_BUTTON);
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 2;
        constraints.gridy = 2;
        buySelectedItemButton.addActionListener(this::buySelectedItem);
        add(buySelectedItemButton, constraints);
        
        
        cancelButton = new JButton(CANCEL_BUY_BUTTON);
        constraints.insets = new Insets(20, 10, 10, 10);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 2;
        constraints.gridy = 3;
        cancelButton.addActionListener(this::exitFromPurchasewindow);
        add(cancelButton, constraints);

        onUpdate();
    }

    /**
     * Actualize the dynamic parts of the window. It can be called many times,
     * and should set the window's state to reflect current choices.
     */
    private void onUpdate() {
        ownedItems = gui.getCurrentPlayersItems();
        availableItems = gui.getAllItems()
                .stream()
                .filter(item -> !ownedItems.contains(item))
                .collect(Collectors.toSet());

        availableItemsModel = new DefaultTableModel(
                new String[]{PROPERTY_COLUMN_NAME}, 0);

        ownedItems.forEach(item -> availableItemsModel
                .addRow(new Property[]{item}));

        ownedItemsTable.setModel(availableItemsModel);

        availableItemsComboBox.removeAllItems();
        availableItems.forEach(availableItemsComboBox::addItem);

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
            buySelectedItemButton.setEnabled(true);
        } else {
            availableItemsLabel.setText(NO_MORE_ITEMS);
            availableItemsComboBox.setVisible(false);
            buySelectedItemButton.setEnabled(false);
        }
        
        money.setText("Aktuális egyenleg: " + gui.getCurrentPlayer().getMoney());
    }

    private void buySelectedItem(ActionEvent event) {
        Property selected = (Property) availableItemsComboBox.getSelectedItem();
        gui.buySelectedItem(selected);
        gui.checkGame();
        onUpdate();
    }

    private void exitFromPurchasewindow(ActionEvent event) {
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

}
