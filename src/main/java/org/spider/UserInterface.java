package org.spider;

import org.config.Config;
import org.plugins.PluginManager;
import org.spider.data.DataTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;


public class UserInterface extends JFrame {
    private PluginManager _manager;
    private Config _config;
    private Vector<Vector> d;
    private DataTable t;
    private DataTableModel dtm;
    public UserInterface(String name) {
        super(name);
        this.setLocation(new Point(500, 250));
        this.setPreferredSize(new Dimension(850, 430));
        Vector<String> row = new Vector<>();
        row.addElement("1");
        row.addElement("Peter");
        row.addElement("2");
        this.setJMenuBar(new JMenuBar());
        d = new Vector<>();
        d.addElement(row);
        dtm = new DataTableModel(d);
        t = new DataTable(dtm);
        this.getContentPane().add(new JScrollPane(t));
        String file = System.getProperty("vmConfig");
        System.err.println(file);
        this._config = new Config(file);

        DataTableModel.root = "http://destinia.com/viajes/";
        this.assignPluginManager();
    }

    public void assignPluginManager() {
        this._manager = new PluginManager(this, this._config);
        this._manager.registerPlugins();
    }
    public void postCreate() {
        for (int i = 0; i < 1; i++) {
            TaskWorker t = new TaskWorker(this.dtm);
            Vector<Object> v = t.getVector();
            v.add(0, this.dtm.getRowCount());
            v.add(1, DataTableModel.root);
            v.add(2, 0);
            this.d.addElement(v);
            t.execute();
        }
        //dtm.fireTableDataChanged();
        this._manager.postRegisterPlugins();
    }
}
