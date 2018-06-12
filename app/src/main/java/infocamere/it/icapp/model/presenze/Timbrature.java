package infocamere.it.icapp.model.presenze;

import android.util.Log;

public class Timbrature {

    private String giorno;
    private String t1;
    private String t2;
    private String t3;
    private String t4;
    private String t5;
    private String t6;
    private String t7;
    private String t8;

    public Timbrature() {
    }

    public Timbrature(String giorno, String t1, String t2, String t3,
                      String t4, String t5, String t6, String t7, String t8) {
        this.giorno = giorno;
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        this.t4 = t4;
        this.t5 = t5;
        this.t6 = t6;
        this.t7 = t7;
        this.t8 = t8;
    }

    public String getGiorno() {
        return giorno;
    }

    public void setGiorno(String giorno) {
        this.giorno = giorno;
    }

    public String getT1() {
        return t1;
    }

    public void setT1(String t1) {
        if (!t1.equals("0")) {
            this.t1 = getHour(t1) + ":" + getMin(t1);
        }
        else {
            this.t1 = t1;
        }
    }

    public String getT2() {
        return t2;
    }

    public void setT2(String t2) {
        if (!t2.equals("0")) {
            this.t2 = getHour(t2) + ":" + getMin(t2);
        }
        else {
            this.t2 = t2;
        }
    }

    public String getT3() {
        return t3;
    }

    public void setT3(String t3) {
        if (!t3.equals("0")) {
            this.t3 = getHour(t3) + ":" + getMin(t3);
        }
        else {
            this.t3 = t3;
        }
    }

    public String getT4() {
        return t4;
    }

    public void setT4(String t4) {
        if (!t4.equals("0")) {
            this.t4 = getHour(t4) + ":" + getMin(t4);
        }
        else {
            this.t4 = t4;
        }
    }

    public String getT5() {
        return t5;
    }

    public void setT5(String t5) {
        if (!t5.equals("0")) {
            this.t5 = getHour(t5) + ":" + getMin(t5);
        }
        else {
            this.t5 = t5;
        }
    }

    public String getT6() {
        return t6;
    }

    public void setT6(String t6) {
        if (!t6.equals("0")) {
            this.t6 = getHour(t6) + ":" + getMin(t6);
        }
        else {
            this.t6 = t6;
        }
    }

    public String getT7() {
        return t7;
    }

    public void setT7(String t7) {
        if (!t7.equals("0")) {
            this.t7 = getHour(t7) + ":" + getMin(t7);
        }
        else {
            this.t7 = t7;
        }
    }

    public String getT8() {
        return t8;
    }

    public void setT8(String t8) {
        if (!t8.equals("0")) {
            this.t8 = getHour(t8) + ":" + getMin(t8);
        }
        else {
            this.t8 = t8;
        }
    }

    public String getHour(String time) {
        int dim = time.length();
        Log.i("HOUR", "time --> " + time);
        String h = time.substring(0,dim - 2);
        Log.i("HOUR", "h --> " + h);

        return h;
    }

    public String getMin(String time) {
        int dim = time.length();
        String m = time.substring(dim - 2);
        Log.i("MIN", "m --> " + m);

        return m;
    }

    @Override
    public String toString() {
        return "Timbrature{" +
                "giorno='" + giorno + '\'' +
                ", t1='" + t1 + '\'' +
                ", t2='" + t2 + '\'' +
                ", t3='" + t3 + '\'' +
                ", t4='" + t4 + '\'' +
                ", t5='" + t5 + '\'' +
                ", t6='" + t6 + '\'' +
                ", t7='" + t7 + '\'' +
                ", t8='" + t8 + '\'' +
                '}';
    }
}