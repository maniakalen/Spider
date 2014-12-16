package org.spider;

import org.spider.data.AbstractDataTableModel;

import javax.swing.*;

/**
 * Created by peter.georgiev on 7/11/14.
 */
public class DataTable extends JTable {
    protected AbstractDataTableModel _model;
    public DataTable(AbstractDataTableModel model)
    {
        super(model);
        _model = model;
        setRowSelectionAllowed(true);
        this.getSelectionModel().addListSelectionListener(model);
    }
}
