/*
 * Copyright (c)
 * Created by Luca Visentin - yyi4216
 * 29/05/18 12.08
 */

package infocamere.it.icapp.model;

import org.json.JSONException;
import org.json.JSONObject;

public class ServiceIC {
    // Labels table name
    public static final String TABLE = "ServiceIC";

    // Labels Table Columns names
    public static final String KEY_ROWID = "_id";
    public static final String KEY_ID = "svc_id";
    public static final String KEY_serviceName = "svc_name";
    public static final String KEY_serviceVisible = "svc_visible";

    private String svcId;
    private String svcName;
    private boolean svcVisible;

    public ServiceIC() {
    }

    public ServiceIC(String svcId, String svcName, boolean svcVisible) {
        this.svcId = svcId;
        this.svcName = svcName;
        this.svcVisible = svcVisible;
    }

    public static String getTABLE() {
        return TABLE;
    }

    public static String getKeyRowid() {
        return KEY_ROWID;
    }

    public static String getKeyId() {
        return KEY_ID;
    }

    public static String getKEY_serviceName() {
        return KEY_serviceName;
    }

    public static String getKEY_serviceVisible() {
        return KEY_serviceVisible;
    }

    public String getSvcId() {
        return svcId;
    }

    public void setSvcId(String svcId) {
        this.svcId = svcId;
    }

    public String getSvcName() {
        return svcName;
    }

    public void setSvcName(String svcName) {
        this.svcName = svcName;
    }

    public boolean isSvcVisible() {
        return svcVisible;
    }

    public void setSvcVisible(boolean svcVisible) {
        this.svcVisible = svcVisible;
    }

    @Override
    public String toString() {
        return "ServiceIC{" +
                "svcId='" + svcId + '\'' +
                ", svcName='" + svcName + '\'' +
                ", svcVisible=" + svcVisible +
                '}';
    }

    public JSONObject toJSON() throws JSONException {

        JSONObject jo = new JSONObject();

        jo.put("_id", getSvcId());
        jo.put("svc_name", getSvcName());
        jo.put("svc_visible", isSvcVisible());

        return jo;
    }
}