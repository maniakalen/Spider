package org.spider;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by peter.georgiev on 7/11/14.
 */
public class DataTable extends JTable {
    protected DataTableModel _model;
    public DataTable(DataTableModel model)
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
