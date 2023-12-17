/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.imoka.demo_single_app.basic;

import ModernDocking.Dockable;
import ModernDocking.api.DockingAPI;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatSolarizedDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatGitHubDarkIJTheme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ThemesPanel extends BasePanel implements Dockable {

    public ThemesPanel(DockingAPI docking) {
        super(docking, "Themes", "themes");

        JTable table = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setTableHeader(null);

        DefaultTableModel model = new DefaultTableModel(0, 1);

        model.addRow(new Object[]{"FlatLaf Light"});
        model.addRow(new Object[]{"FlatLaf Dark"});
        model.addRow(new Object[]{"Github Dark"});
        model.addRow(new Object[]{"Solarized Dark"});
        table.setModel(model);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        add(new JScrollPane(table), gbc);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (table.getSelectedRow() == -1) {
                return;
            }
            String lookAndFeel = (String) model.getValueAt(table.getSelectedRow(), 0);

            SwingUtilities.invokeLater(() -> {
                try {
                    switch (lookAndFeel) {
                        case "FlatLaf Light":
                            UIManager.setLookAndFeel(new FlatLightLaf());
                            break;
                        case "FlatLaf Dark":
                            UIManager.setLookAndFeel(new FlatDarkLaf());
                            break;
                        case "Github Dark":
                            UIManager.setLookAndFeel(new FlatGitHubDarkIJTheme());
                            break;
                        case "Solarized Dark":
                            UIManager.setLookAndFeel(new FlatSolarizedDarkIJTheme());
                            break;
                    }
                    FlatLaf.updateUI();
                } catch (UnsupportedLookAndFeelException ex) {
                    throw new RuntimeException(ex);
                }
            });
        });
    }

    @Override
    public int getType() {
        return 1;
    }

    @Override
    public boolean isFloatingAllowed() {
        return false;
    }

    @Override
    public boolean isClosable() {
        return false;
    }

    @Override
    public boolean isWrappableInScrollpane() {
        return false;
    }
}
