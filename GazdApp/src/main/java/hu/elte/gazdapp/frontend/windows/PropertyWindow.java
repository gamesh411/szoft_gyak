/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.frontend.windows;

import hu.elte.gazdapp.backend.domain.component.Property;
import hu.elte.gazdapp.frontend.GuiManager;
import hu.elte.gazdapp.frontend.util.ScreenConstants;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PropertyWindow extends JFrame {
    
    private static final int WIDTH = ScreenConstants.PROPERTY_WINDOW_WIDTH.getValue();
    private static final int HEIGHT = ScreenConstants.PROPERTY_WINDOW_HEIGHT.getValue();
    private static final int ROW_HEIGHT = ScreenConstants.ROW_HEIGHT.getValue();

    private static final String OWNED_PROPERTIES = "Eddigi tulajdon:";
    private static final String NO_PROPERTIES = "Még nincsen tulajdona!";
    private static final String PROPERTY_COLUMN_NAME = "Tulajdon";

    private static final String CANCEL_BUY_BUTTON = "Bezárás";
    
    private DefaultTableModel ownedItemsModel;

    private final GuiManager gui;
    private Set<Property> ownedItems;

    private JTable ownedItemsTable;
    private JLabel ownedItemsLabel;
    
    private JButton cancelButton;

    public PropertyWindow(GuiManager gui) {
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
        
        cancelButton = new JButton(CANCEL_BUY_BUTTON);
        constraints.insets = new Insets(20, 10, 10, 10);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 3;
        cancelButton.addActionListener(this::exitFromPropertyWindow);
        add(cancelButton, constraints);
        
        onUpdate();
        
    }
    
     private void onUpdate() {
        ownedItems = gui.getCurrentPlayersItems();
        ownedItemsModel = new DefaultTableModel(
                new String[]{PROPERTY_COLUMN_NAME}, 0);

        ownedItems.forEach(item -> ownedItemsModel
                .addRow(new Property[]{item}));

        ownedItemsTable.setModel(ownedItemsModel);
        
       
        if (!ownedItems.isEmpty()) {
            ownedItemsLabel.setText(OWNED_PROPERTIES);
            ownedItemsTable.setVisible(true);
        } else {
            ownedItemsLabel.setText(NO_PROPERTIES);
            ownedItemsTable.setVisible(false);
        } 
     }


    private void exitFromPropertyWindow(ActionEvent event) {
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

}
