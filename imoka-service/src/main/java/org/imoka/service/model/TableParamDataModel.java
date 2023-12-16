/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.imoka.service.model;

import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.table.AbstractTableModel;
import org.imoka.util.Util;

/**
 * TableParamDataModel
 *
 * @author r.hendrick
 */
public class TableParamDataModel extends AbstractTableModel {

    /**
     *
     */
    private ArrayList<Object> columnNames = new ArrayList<>(Arrays.asList("N°", "Table des variables", "Table des données"));
    private ArrayList<ArrayList<Object>> data = new ArrayList<>();


    public TableParamDataModel() {
        
    }

    public TableParamDataModel(ArrayList<Object> columns) {
        columnNames = columns;
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames.get(col).toString();
    }

    @Override
    public Object getValueAt(int row, int col) {
        if(data.isEmpty())
            return null;
        return data.get(row).get(col);
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        /*if (col < 2) {
            return false;
        } else {
            return true;
        }*/
        return false;
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    @Override
    public void setValueAt(Object value, int row, int col) {
        data.get(row).set(col, value);
        fireTableCellUpdated(row, col);
    }

    //
    //
    //
    //
    //
    /**
     * Methode permettant de récupérer la liste des données d'une colonnes
     *
     * @param column numéro de colonne dont on souhaite récupérer le contenu
     * @return la liste de données de la colonne
     */
    public Object[] selectedColumnData(Integer column) {
        if (data == null) {
            return null;
        }

        if (column >= getColumnCount()) {
            return null;
        }

        int sizeData = getRowCount();
        Object values[] = new Object[sizeData];

        for (int row = 0; row < getRowCount(); row++) {
            values[row] = getValueAt(row, column);
        }
        return values;
    }

    public ArrayList<Object> selectedColumnArrayList(Integer column) {
        if (data == null) {
            return null;
        }

        if (column >= getColumnCount()) {
            return null;
        }

        ArrayList<Object> values = new ArrayList<>();

        for (int row = 0; row < getRowCount(); row++) {
            values.add(getValueAt(row, column));
        }
        return values;
    }

    /**
     * Methode permettant de récupérer la liste des données d'une ligne
     *
     * @param row numéro de ligne dont on souhaite récupérer le contenu
     * @return la liste de données de la ligne
     */
    public Object[] selectedRowData(Integer row) {
        if (data == null) {
            return null;
        }

        if (row >= getRowCount()) {
            return null;
        }

        int sizeData = getColumnCount();
        Object values[] = new Object[sizeData];

        for (int col = 0; col < getRowCount(); col++) {
            values[row] = getValueAt(row, col);
        }
        return values;
    }

    /**
     * Supprime la ligne dont le numéro est celui mentionné ci-dessous
     *
     * @param row numéro de ligne à supprimer
     */
    public void removeRow(int row) {
        data.remove(row);
        fireTableRowsDeleted(row, row);
    }

    public void addRow(ArrayList<Object> rowData) {
        if (rowData.size() != getColumnCount()) {
            Util.out("Impossible d'ajouter la ligne, le nombre de colonne ne correspond pas");
            return;
        }
        this.data.add(rowData);
        fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
    }

    /**
     * Create an array using in the row table model.
     * 
     * @param id the numer of the item
     * @param tableParamName the name of the table parameter or variable
     * @param tableDataName the name of the table containing data
     * @return a array list of the parameter
     */
    public ArrayList<Object> arrayRow(Integer id, String tableParamName, String tableDataName){
            ArrayList<Object> list = new ArrayList<>();
            list.add(id);
            list.add(tableParamName);
            list.add(tableDataName);
            return list;           
    }
    
    /**
     * Add a new row from a specify name and table
     *
     * @param tableParamName
     * @param tableDataName
     */
    public void addRow(String tableParamName, String tableDataName) {
        data.add(arrayRow(getRowCount()+1, tableParamName, tableDataName));
        ArrayList<String> l = new ArrayList<>();
        String id = "" + getRowCount() + 1;
        l.add(id);
        fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
    }

    public void clearRows() {
        Integer row = getRowCount();
        data.clear();
        fireTableRowsDeleted(0, row);
    }

    /**
     * Get the current selected row and convert to screen
     *
     * @param selectedRow number corresponding to the selected row
     * @return screen at the corresponding selected row number
     */
    public ArrayList<Object> getRowAt(int selectedRow) {
//        String methodName = getClass().getSimpleName() + " : getScreenAt(selectedRow) >> ";
        
        if (selectedRow >= getRowCount() || selectedRow<0) {
            return null;
        }
//        Util.out(methodName  + "Selected row : " + selectedRow + " getRowCount : " + getRowCount());

        ArrayList<Object> list = new ArrayList<>();
        list.add(Integer.valueOf(getValueAt(selectedRow, 0).toString()));
        list.add(getValueAt(selectedRow, 1).toString());
        list.add(getValueAt(selectedRow, 2).toString());

        return list;
    }

    public void updateScreen(ArrayList<Object> rowIn) {
        String methodName = getClass().getSimpleName() + " : updateScreen() >> ";
        //Util.out(methodName + "Début de la mise à jour...");
        ArrayList<Object> rowGet = getRowAt(Integer.valueOf(rowIn.get(0).toString())-1);

        if (rowGet == null) {
            Util.out(methodName + "...Aucun écran à la position référencée");
            return;
        }

        Integer id = Integer.valueOf(rowIn.get(0).toString());
        Object tableParamName = rowIn.get(1);
        Object tableDataName = rowIn.get(2);
        
        Integer row = id -1;
        data.get(row).set(0, id);
        data.get(row).set(1, tableParamName);
        data.get(row).set(2, tableDataName);
        Util.out(methodName + "...Fin du remplcement des données");
        fireTableRowsUpdated(row, row);
    }

}
