package org.spider.data;

import sun.net.www.http.HttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Vector;

/**
 * Created by peter.georgiev on 7/11/14.
 */
public class DataTableModel extends AbstractDataTableModel {

    public DataTableModel(Vector<Vector> data) {
        super();
        this._data = data;
        this._columnNames = new String[]{"Id", "Name"};
        this.m_colTypes = new Class[]{ Integer.class, String.class };
    }

    @Override
    public Object work(Vector<Object> row) {
        try {
            URL obj = new URL("http://www.google.com");
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setReadTimeout(5000);
            conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
            conn.addRequestProperty("User-Agent", "Mozilla");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer html = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                html.append(inputLine);
            }
            in.close();
            String result = html.toString();
            //Charset.forName("ISO").encode(result);
            System.out.println("URL Content... \n" +  result);
            System.out.println("Done");
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }
        return true;
    }

    @Override
    public void process(List<Object> chunks) {
        for (Object url : chunks) {
            System.err.println(((URL)url).toString());
        }
    }
}
