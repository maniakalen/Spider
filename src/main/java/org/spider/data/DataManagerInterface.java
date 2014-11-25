package org.spider.data;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.List;
import java.util.Vector;

/**
 * Created by peter.georgiev on 17/11/14.
 */
public interface DataManagerInterface {
    public TableModel getTableModel();
    public Object work(Vector<Object> row);
    public void addDataUpdateListener(DataUpdateListener listener);
    public void process(List<Object> chunks);
    public void start();

    public JPanel getConfigInterface();
    public Dimension getPreferredSize();
}
