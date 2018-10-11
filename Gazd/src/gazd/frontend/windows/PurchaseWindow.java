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
import java.util.Arrays;
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
    private JComboBox<Property> availableItemsComboBox;
    private JButton buySelectedItemButton;
    private JButton cancelButton;

    public PurchaseWindow(GuiManager gui) {
        this.gui = gui;
        onCreate();
    }

    /**
     * Set the static part of the window, which does not change over time. Meant
     * to be called once at the beginning of the windows life-cycle.
     */
    private void onCreate() {
        setLayout(new GridBagLayout());

        // Display items already owned.
        ownedItemsLabel = new JLabel();
        ownedItemsTable = new JTable();
        add(ownedItemsLabel);
        add(ownedItemsTable);

        // Display purchasable items.
        availableItemsLabel = new JLabel();
        add(availableItemsLabel);
        availableItemsComboBox = new JComboBox<>();
        add(availableItemsComboBox);

        // Display buttons for making or canceling a puchase.
        buySelectedItemButton = new JButton(CONFIRM_BUY_BUTTON);
        cancelButton = new JButton(CANCEL_BUY_BUTTON);
        buySelectedItemButton.addActionListener(this::buySelectedItem);
        cancelButton.addActionListener(this::exitFromPurchasewindow);
        add(buySelectedItemButton);
        add(cancelButton);

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
