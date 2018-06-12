/*
 * Copyright (c)
 * Created by Luca Visentin - yyi4216
 * 06/06/18 11.28
 */

package infocamere.it.icapp.model.presenze;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Saldi {

    private int progr;
    private String descr;
    private String residuoAnnoPrec;
    private String annoCorrente;
    private String goduteCorrente;
    private String saldo;
    private String ultagg;

    public Saldi() {
    }

    public Saldi(int progr, String descr, String residuoAnnoPrec, String annoCorrente, String goduteCorrente, String saldo, String ultagg) {
        this.progr = progr;
        this.descr = descr;
        this.residuoAnnoPrec = residuoAnnoPrec;
        this.annoCorrente = annoCorrente;
        this.goduteCorrente = goduteCorrente;
        this.saldo = saldo;
        this.ultagg = ultagg;
    }

    public int getProgr() {
        return progr;
    }

    public void setProgr(int progr) {
        this.progr = progr;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getResiduoAnnoPrec() {
        return residuoAnnoPrec;
    }

    public void setResiduoAnnoPrec(String residuoAnnoPrec) {
        this.residuoAnnoPrec = residuoAnnoPrec;
    }

    public String getAnnoCorrente() {
        return annoCorrente;
    }

    public void setAnnoCorrente(String annoCorrente) {
        this.annoCorrente = annoCorrente;
    }

    public String getGoduteCorrente() {
        return goduteCorrente;
    }

    public void setGoduteCorrente(String goduteCorrente) {
        this.goduteCorrente = goduteCorrente;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getUltagg() {
        return ultagg;
    }

    public void setUltagg(String ultagg) {
        this.ultagg = ultagg;
    }

    @Override
    public String toString() {
        return "Saldi{" +
                "progr=" + progr +
                ", descr='" + descr + '\'' +
                ", residuoAnnoPrec='" + residuoAnnoPrec + '\'' +
                ", annoCorrente='" + annoCorrente + '\'' +
                ", goduteCorrente='" + goduteCorrente + '\'' +
                ", saldo='" + saldo + '\'' +
                ", ultagg='" + ultagg + '\'' +
                '}';
    }
}
