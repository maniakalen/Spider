package org.spider;

/**
 * Created by peter.georgiev on 14/11/14.
 */
public class VarVal {

    private int val;

    public VarVal(int v) {
        val = v;
    }
    public void updateVal(int v) {
        val = v;
    }
    public String toString() {
        return Integer.toString(val);
    }
}
