package infocamere.it.icapp.model.presenze;

import android.util.Log;

public class Timbratura {

    private String giorno;
    private String hour;
    private String type;

    public Timbratura() {
    }

    public Timbratura(String giorno, String hour, String type) {
        this.giorno = giorno;
        this.hour = getHourFormat(hour) + ":" + getMinFormat(hour);
        this.type = refactorType(type);
    }

    public String getGiorno() {
        return giorno;
    }

    public void setGiorno(String giorno) {
        this.giorno = giorno;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHourFormat(String time) {
        int dim = time.length();

        if (dim == 1) {
            return "0";
        }

        String h = time.substring(0,dim - 2);

        return h;
    }

    public String getMinFormat(String time) {
        int dim = time.length();

        if (dim == 1) {
            return "";
        }

        String m = time.substring(dim - 2);

        return m;
    }

    public String refactorType(String type) {
        String tipoTimb;
        if (type.equals("E") || type == "E") {
            tipoTimb = "IN";
        }
        else {
            tipoTimb = "OUT";
        }

        return tipoTimb;
    }

    @Override
    public String toString() {
        return "Timbratura{" +
                "giorno='" + giorno + '\'' +
                ", hour='" + hour + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
