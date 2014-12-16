package org.spider.data;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

/**
 * Created by peter.georgiev on 15/12/14.
 */
public class RowDataTable extends JTable {
    protected AbstractTableModel _model;
    public RowDataTable(AbstractTableModel model)
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
