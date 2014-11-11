package org.spider;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

/**
 * Created by peter.georgiev on 7/11/14.
 */
public class DataTableModel extends AbstractTableModel {
    private String[] _columnNames = {"Id","Name"};
    public Class[] m_colTypes = { Integer.class, String.class };
    private Vector<Vector> _data;

    public DataTableModel(Vector<Vector> data) {
        super();
        this._data = data;
    }
    public int getColumnCount() {
        return _columnNames.length;
    }

    public int getRowCount() {
        return _data.size();
    }

    public String getColumnName(int col) {
        return _columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return _data.elementAt(row).elementAt(col);
    }

    public Class getColumnClass(int c) {
        return m_colTypes[c];
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        return false;
    }
    public String toString() {
        return _data.toString();
    }
    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) {
        //_data.elementAt(row).set(col, value);
        //fireTableCellUpdated(row, col);
    }
}
