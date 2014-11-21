package org.spider.data;

import org.spider.TaskWorker;

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

    public DataTableModel(Vector<Vector> data) {
        super(data);

        this._columnNames = new String[]{"Id", "Url", "Status"};
        this.m_colTypes = new Class[]{ Integer.class, String.class, Integer.class };
    }

    @Override
    public Object work(Vector<Object> row) {
        try {
            while (count > 10) {
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
