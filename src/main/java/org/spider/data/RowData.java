package org.spider.data;

import java.util.HashMap;
import java.util.Vector;

/**
 * Created by peter.georgiev on 9/12/14.
 */
public class RowData<T> extends Vector<T> {
    protected HashMap<Object, Object> details;

    public RowData() {
        super();
        this.details = new HashMap<>();
    }
    public void addDetailsItem(Object item, Object data)
    {
        this.details.put(item, data);
    }

    public HashMap<Object, Object> getDetails()
    {
        return this.details;
    }

    public Object getDetailsItem(Object item) throws DataNotFoundException
    {
        if (!this.details.containsKey(item)) {
            throw new DataNotFoundException("Details item not found");
        }
        return this.details.get(item);
    }
}
