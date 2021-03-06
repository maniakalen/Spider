package org.spider;

import org.spider.data.AbstractDataTableModel;
import org.spider.data.DataUpdateListener;
import org.spider.data.RowData;

import javax.swing.*;
import java.util.List;
import java.util.Vector;


public class TaskWorker extends SwingWorker<Integer, Object> {
    private Vector<Object> d;
    private AbstractDataTableModel dtm;
    public TaskWorker(AbstractDataTableModel dtm) {
        this.d = new RowData<>();
        this.dtm = dtm;

    }
    public Vector<Object> getVector() {
        return this.d;
    }
    @Override
    public Integer doInBackground() {
        this.dtm.addDataUpdateListener(new DataUpdateListener() {
            @Override
            public void dataUpdated(Object data) {
                publish(data);
            }
        });
        dtm.work(this.d);
        return 0;
    }
    @Override
    protected void process(List<Object> chunks) {
        dtm.process(chunks);
        dtm.fireTableDataChanged();
    }
    protected void done() {
        dtm.done();
    }
}
