package org.spider;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

import org.config.Config;
import org.plugins.*;
import org.spider.data.DataTableModel;
import org.spider.data.TableValues;
/**
 * Created by peter.georgiev on 7/11/14.
 */
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
        this.setJMenuBar(new JMenuBar());
        d = new Vector<>();
        d.addElement(row);
        dtm = new DataTableModel(d);
        t = new DataTable(dtm);
        this.getContentPane().add(t);
        String file = System.getProperty("vmConfig");
        System.err.println(file);
        this._config = new Config(file);
        this.assignPluginManager();
    }

    public void assignPluginManager() {
        this._manager = new PluginManager(this, this._config);
        this._manager.registerPlugins();
    }
    public void postCreate() {
        for (int i = 0; i < 5; i++) {
            TaskWorker t = new TaskWorker(this.dtm);
            this.d.addElement(t.getVector());
            t.execute();
        }
        //dtm.fireTableDataChanged();
        this._manager.postRegisterPlugins();
    }
}
