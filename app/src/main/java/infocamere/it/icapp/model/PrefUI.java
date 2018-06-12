/*
 * Copyright (c)
 * Created by Luca Visentin - yyi4216
 * 29/05/18 12.10
 */

package infocamere.it.icapp.model;

import org.json.JSONException;
import org.json.JSONObject;

public class PrefUI {
    // Labels table name
    public static final String TABLE = "prefUI";

    // Labels Table Columns names
    public static final String KEY_ROWID = "_id";
    public static final String KEY_ID = "prefUI_id";
    public static final String KEY_orderUI = "orderUI";

    // property help us to keep data
    public int _id;
    public int prefUI_id;
    public String orderUI;

    public static String getTABLE() {
        return TABLE;
    }

    public static String getKeyRowid() {
        return KEY_ROWID;
    }

    public static String getKeyId() {
        return KEY_ID;
    }

    public static String getKeyOrderUI() {
        return KEY_orderUI;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getPrefUI_id() {
        return prefUI_id;
    }

    public void setPrefUI_id(int prefUI_id) {
        this.prefUI_id = prefUI_id;
    }

    public String getOrderUI() {
        return orderUI;
    }

    public void setOrderUI(String orderUI) {
        this.orderUI = orderUI;
    }

    @Override
    public String toString() {

        return "PrefUI{" +
                "_id=" + _id +
                ", prefUI_id=" + prefUI_id +
                ", orderUI='" + orderUI + '\'' +
                '}';
    }

    public JSONObject toJSON() throws JSONException {

        JSONObject jo = new JSONObject();

        jo.put("_id", _id);
        jo.put("prefUI", prefUI_id);
        jo.put("orderUI", orderUI);

        return jo;
    }
}
