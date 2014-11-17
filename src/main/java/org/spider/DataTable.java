package org.spider;

import org.spider.data.AbstractDataTableModel;
import org.spider.data.DataTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

/**
 * Created by peter.georgiev on 7/11/14.
 */
public class DataTable extends JTable {
    protected AbstractTableModel _model;
    public DataTable(AbstractTableModel model)
    {
        super(model);
        _model = model;
        setRowSelectionAllowed(true);
        this.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                //System.out.println(table.getValueAt(table.getSelectedRow(), 0).toString());
            }
        });
    }
}
