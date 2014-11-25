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
        this.setPreferredSize(new Dimension(650, 630));
        this.setJMenuBar(new JMenuBar());
        dtm = new DataTableModel();
        t = new DataTable(dtm);
        t.setSize(dtm.getPreferredSize());
        JPanel panel = new JPanel();
        panel.add(new JScrollPane(t));
        String file = System.getProperty("vmConfig");
        System.err.println(file);
        this._config = new Config(file);
        panel.add(dtm.getConfigInterface());
        this.getContentPane().add(panel);
        this.assignPluginManager();
    }

    public void assignPluginManager() {
        this._manager = new PluginManager(this, this._config);
        this._manager.registerPlugins();
    }
    public void postCreate() {
        this._manager.postRegisterPlugins();
    }
}
