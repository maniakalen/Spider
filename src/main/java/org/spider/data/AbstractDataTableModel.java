package org.spider.data;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.util.List;
import java.util.Vector;


public abstract class AbstractDataTableModel extends AbstractTableModel implements DataManagerInterface {
    static int count = 1;

    protected String[] _columnNames = {};
    protected Class[] m_colTypes = { Integer.class, String.class };

    protected Vector<Vector> _data;
    protected DataUpdateListener _listener;

    public AbstractDataTableModel(Vector<Vector> data) {
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

    public TableModel getTableModel() {
        return this;
    }

    public Object work(Vector<Object> row) {
        return true;
    }
    public void process(List<Object> chunks) {}
    public void addDataUpdateListener(DataUpdateListener listener) {
        this._listener = listener;
    }
    public void done() {
        count--;
    }
}
