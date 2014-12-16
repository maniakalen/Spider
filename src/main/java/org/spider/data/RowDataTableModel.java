package org.spider.data;

import javax.swing.table.AbstractTableModel;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

/**
 * Created by peter.georgiev on 15/12/14.
 */
public class RowDataTableModel extends AbstractTableModel {
    public static int count = 1;
    public static String root;
    protected String[] _columnNames;
    protected Class[] m_colTypes;
    protected Integer[] sizes;
    protected Vector<Vector> _data;
    protected DataUpdateListener _listener;


    public RowDataTableModel(RowData data)
    {
        Vector<Vector> results = new Vector<>();
        Vector<String> row;
        Iterator it = data.getDetails().entrySet().iterator();
        while (it.hasNext()) {
            row = new Vector<>();
            Map.Entry pairs = (Map.Entry)it.next();
            row.add(pairs.getKey().toString());
            row.add(pairs.getValue().toString());
            it.remove(); // avoids a ConcurrentModificationException
            results.add(row);
        }

        this._data = results;

        this._columnNames = new String[]{"Element", "Value"};
        this.m_colTypes = new Class[]{ String.class, String.class };
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
