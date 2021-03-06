package org.spider.data;

import org.spider.DataTable;
import org.spider.TaskWorker;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataTableModel extends AbstractDataTableModel {
    int height = 600;
    DataTable table;
    JScrollPane details;
    JSplitPane splitPane;
    RowDataTable rowTable;
    public DataTableModel(Vector<Vector> data) {
        super(data);

        this._columnNames = new String[]{"Id", "Url", "Status"};
        this.m_colTypes = new Class[]{ Integer.class, String.class, Integer.class };
        this.sizes = new Integer[]{45, 650, 50};
    }
    public DataTableModel() {
        this(new Vector<Vector>());
    }
    @Override
    public Object work(Vector<Object> row) {
        try {
            while (count > 3) {
                Thread.sleep(500);
            }
            count++;
            URL url;
            if (row.elementAt(1) != null) {
                url = new URL((String) row.get(1));

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(30000);
                conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
                conn.addRequestProperty("User-Agent", "Mozilla");
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder html = new StringBuilder();
                row.remove(2);
                row.add(2, conn.getResponseCode());
                ((RowData)row).addDetailsItem("Status",conn.getResponseCode());
                ((RowData)row).addDetailsItem("Url", conn.getURL());
                while ((inputLine = in.readLine()) != null) {
                    html.append(inputLine);
                }
                in.close();
                String result = html.toString();
                Pattern pat = Pattern.compile("(https?|http?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]", Pattern.DOTALL);
                Matcher matcher = pat.matcher(result);
                while (matcher.find()) {
                    String gr = matcher.group();
                    if (gr.contains(DataTableModel.root)) {
                        _listener.dataUpdated(new URL(gr));
                    }
                }
            }
        } catch (InterruptedException | IOException ex) {
            ex.printStackTrace(System.err);
        }
        return true;
    }

    @Override
    public void process(List<Object> chunks) {
        for (Object url : chunks) {
            boolean existing = false;
            for (Vector item : this._data) {
                if (item.get(1).toString().contains(url.toString())) {
                    existing = true;
                }
            }
            if (!existing) {
                TaskWorker worker = new TaskWorker(this);
                Vector<Object> v = worker.getVector();
                v.add(0, this.getRowCount());
                v.add(1, url.toString());
                v.add(2, 0);
                this._data.addElement(v);
                worker.execute();
            }
        }
    }

    @Override
    public void start() {
        if (AbstractDataTableModel.root != null) {
            TaskWorker t = new TaskWorker(this);
            Vector<Object> v = t.getVector();
            v.add(0, this.getRowCount());
            v.add(1, AbstractDataTableModel.root);
            v.add(2, 0);
            this._data.addElement(v);
            t.execute();
        }
    }

    @Override
    public JPanel getConfigInterface() {
        table = new DataTable(this);
        table.setMinimumSize(this.getPreferredSize());

        JScrollPane scroll = new JScrollPane(table);
        rowTable = new RowDataTable(new RowDataTableModel(new RowData()));
        details = new JScrollPane(rowTable);
        //Create a split pane with the two scroll panes in it.
        this.splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                scroll, details);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(200);

        Dimension minimumSize = new Dimension(350, 100);
        scroll.setMinimumSize(minimumSize);
        details.setMinimumSize(minimumSize);

        JPanel panel = new JPanel(new BorderLayout());
        final JTextField field = new JTextField();
        Dimension dim = new Dimension(300, 25);
        JPanel control = new JPanel();
        field.setSize(dim);
        field.setPreferredSize(dim);
        control.add(field);
        JButton start = new JButton("Start");
        start.addActionListener(new StartListener(field, this));
        control.add(start);
        panel.add(splitPane, BorderLayout.CENTER);
        panel.add(control, BorderLayout.SOUTH);
        return panel;
    }
    static class StartListener implements ActionListener {
        AbstractDataTableModel m;
        JTextField f;
        public StartListener(JTextField field, AbstractDataTableModel model) {
            this.f = field;
            this.m = model;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            AbstractDataTableModel.root = f.getText();
            m.start();
        }
    }

    public Dimension getPreferredSize() {
        int width = 0;
        for (Integer i : this.sizes) {
            width += i;
        }
        return new Dimension(width, this.height);
    }

    @Override
    public void valueChanged(ListSelectionEvent event) {
        int last = event.getLastIndex();
        RowData row = (RowData)this._data.get(last);
        rowTable.setModel(new RowDataTableModel(row));
    }
}
