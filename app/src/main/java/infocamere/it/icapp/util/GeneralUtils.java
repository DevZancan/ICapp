package infocamere.it.icapp.util;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import infocamere.it.icapp.search.TypicalSearch;

public class GeneralUtils {

    public TypicalSearch getTypicalSearch(String originalQuery) {

        TypicalSearch typicalSearch = new TypicalSearch();

        if (originalQuery.toLowerCase().contains("chiama")) {
            typicalSearch.setDoCall(true);
            String wrapQuery = originalQuery.replace("chiama", "").trim();
            wrapQuery = wrapQuery.replace("Chiama", "").trim();
            typicalSearch.setQuery(wrapQuery);
        }
        else {
            typicalSearch.setDoCall(false);
            typicalSearch.setQuery(originalQuery);
        }

        return typicalSearch;
    }

    public static String[] getOrderLayouts(String orderUI) {
        String[] orderLayouts = orderUI.split("-");

        return orderLayouts;
    }

    public static String getTodayForRequest() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String today = df.format(c);
        Log.i("UTILS", "today --> " + today);

        return today;
    }
}
