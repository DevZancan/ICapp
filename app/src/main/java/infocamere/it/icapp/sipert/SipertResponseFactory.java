/*
 * Copyright (c)
 * Created by Luca Visentin - yyi4216
 * 06/06/18 11.48
 */

package infocamere.it.icapp.sipert;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import infocamere.it.icapp.model.presenze.Saldi;
import infocamere.it.icapp.model.presenze.Timbratura;
import infocamere.it.icapp.model.presenze.Timbrature;

class SipertResponseFactory {

    static ArrayList<Saldi> buildSaldi(JSONObject saldiResponse) throws JSONException {

        ArrayList<Saldi> saldi = new ArrayList<>();
        Saldi s;
        JSONObject tmp;
        JSONArray tmparray;

        JSONArray saldiArray = saldiResponse.getJSONArray("saldi");
        for (int i=0; i<saldiArray.length(); i++) {
            s = new Saldi();
            tmp = saldiArray.getJSONObject(i);
            s.setProgr(Integer.valueOf(tmp.getString("progr")));
            s.setDescr(tmp.getString("descr"));
            s.setUltagg(tmp.getString("ultagg"));
            tmparray = tmp.getJSONArray("celle");
            if (tmparray.length() == 4) {
                s.setResiduoAnnoPrec(tmparray.getString(0));
                s.setAnnoCorrente(tmparray.getString(1));
                s.setGoduteCorrente(tmparray.getString(2));
                s.setSaldo(tmparray.getString(3));
            }
            saldi.add(s);
        }

        return saldi;
    }


    static ArrayList<Timbratura> buildTimbrature(JSONObject timbratureResponse) throws JSONException {

        String retcode = timbratureResponse.getString("retcode");

        if (retcode.equals("5") || retcode == "5") {
            return null;
        }

        ArrayList<Timbratura> timbrature = new ArrayList<>();
        Timbratura timb1 = new Timbratura(timbratureResponse.getString("giorno"),
                timbratureResponse.getString("t1"),
                timbratureResponse.getString("v1"));
        timbrature.add(timb1);

        Timbratura timb2 = new Timbratura(timbratureResponse.getString("giorno"),
                timbratureResponse.getString("t2"),
                timbratureResponse.getString("v2"));
        timbrature.add(timb2);

        Timbratura timb3 = new Timbratura(timbratureResponse.getString("giorno"),
                timbratureResponse.getString("t3"),
                timbratureResponse.getString("v3"));
        timbrature.add(timb3);

        Timbratura timb4 = new Timbratura(timbratureResponse.getString("giorno"),
                timbratureResponse.getString("t4"),
                timbratureResponse.getString("v4"));
        timbrature.add(timb4);

        Timbratura timb5 = new Timbratura(timbratureResponse.getString("giorno"),
                timbratureResponse.getString("t5"),
                timbratureResponse.getString("v5"));
        timbrature.add(timb5);

        Timbratura timb6 = new Timbratura(timbratureResponse.getString("giorno"),
                timbratureResponse.getString("t6"),
                timbratureResponse.getString("v6"));
        timbrature.add(timb6);

        Timbratura timb7 = new Timbratura(timbratureResponse.getString("giorno"),
                timbratureResponse.getString("t7"),
                timbratureResponse.getString("v7"));
        timbrature.add(timb7);

        Timbratura timb8 = new Timbratura(timbratureResponse.getString("giorno"),
                timbratureResponse.getString("t8"),
                timbratureResponse.getString("v8"));
        timbrature.add(timb8);

        return timbrature;
    }

    static boolean getGogone(JSONObject timbResponse) throws JSONException {
        String retcode = timbResponse.getString("retcode");
        if (retcode.equals("5") || retcode == "5") {
            return true;
        }
        else {
            return false;
        }
    }
}
