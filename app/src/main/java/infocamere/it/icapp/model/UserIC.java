/*
 * Copyright (c)
 * Created by Luca Visentin - yyi4216
 * 29/05/18 12.08
 */

package infocamere.it.icapp.model;

import org.json.JSONException;
import org.json.JSONObject;

public class UserIC {
    // Labels table name
    public static final String TABLE = "userIC";

    // Labels Table Columns names
    public static final String KEY_ROWID = "_id";
    public static final String KEY_ID = "userIC_id";
    public static final String KEY_YYI = "yyi";
    public static final String KEY_name = "name";
    public static final String KEY_surname = "surname";
    public static final String KEY_email = "email";
    public static final String KEY_phone_fix = "phone_fix";
    public static final String KEY_phone_mobile = "phone_mobile";
    public static final String KEY_sede = "sede";
    public static final String KEY_office = "office";
    public static final String KEY_cdr = "cdr";
    public static final String KEY_matricola = "matricola";

    // property help us to keep data
    public int _id;
    public int userIC_id;
    public String yyi;
    public String name;
    public String surname;
    public String email;
    public String phone_fix;
    public String phone_mobile;
    public String sede;
    public String office;
    public String cdr;
    public String matricola;

    public static String getTABLE() {
        return TABLE;
    }

    public static String getKeyRowid() {
        return KEY_ROWID;
    }

    public static String getKeyId() {
        return KEY_ID;
    }

    public static String getKeyYyi() {
        return KEY_YYI;
    }

    public static String getKEY_name() {
        return KEY_name;
    }

    public static String getKEY_surname() {
        return KEY_surname;
    }

    public static String getKEY_email() {
        return KEY_email;
    }

    public static String getKEY_phone_fix() {
        return KEY_phone_fix;
    }

    public static String getKEY_phone_mobile() {
        return KEY_phone_mobile;
    }

    public static String getKEY_sede() {
        return KEY_sede;
    }

    public static String getKEY_office() {
        return KEY_office;
    }

    public static String getKEY_cdr() {
        return KEY_cdr;
    }

    public static String getKEY_matricola() {
        return KEY_matricola;
    }

    public int getUserIC_id() {
        return userIC_id;
    }

    public void setUserIC_id(int userIC_id) {
        this.userIC_id = userIC_id;
    }

    public String getYyi() {
        return yyi;
    }

    public void setYyi(String yyi) {
        this.yyi = yyi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_fix() {
        return phone_fix;
    }

    public void setPhone_fix(String phone_fix) {
        this.phone_fix = phone_fix;
    }

    public String getPhone_mobile() {
        return phone_mobile;
    }

    public void setPhone_mobile(String phone_mobile) {
        this.phone_mobile = phone_mobile;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getCdr() {
        return cdr;
    }

    public void setCdr(String cdr) {
        this.cdr = cdr;
    }

    public String getMatricola() {
        return matricola;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return "UserIC{" +
                "userIC_id=" + userIC_id +
                ", yyi='" + yyi + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phone_fix='" + phone_fix + '\'' +
                ", phone_mobile='" + phone_mobile + '\'' +
                ", sede='" + sede + '\'' +
                ", office='" + office + '\'' +
                ", cdr='" + cdr + '\'' +
                ", matricola='" + matricola + '\'' +
                '}';
    }

    public JSONObject toJSON() throws JSONException {

        JSONObject jo = new JSONObject();

        jo.put("_id", _id);
        jo.put("userIC_id", userIC_id);
        jo.put("yyi", yyi);
        jo.put("name", name);
        jo.put("surname", surname);
        jo.put("email", email);
        jo.put("matricola", matricola);
        jo.put("cdr", cdr);
        jo.put("phone_fix", phone_fix);
        jo.put("phone_mobile", phone_mobile);
        jo.put("sede", sede);
        jo.put("office", office);

        return jo;
    }
}
