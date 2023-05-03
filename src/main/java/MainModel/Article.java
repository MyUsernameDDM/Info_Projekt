package MainModel;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


public class Article implements Serializable {
    private final ArrayList<Unit> values = new ArrayList<>();
    private final String name;
    private final String[] apiKey = {"QESJBL81ZZ99LAQX","SW5CZKALG7G6D5GT"};
    private int pointAmount = 0;
    private TimeSpan timeSpan;
    private Config cfg;


    public ArrayList<Unit> getValues() {
        return values;
    }


    public Article(String str) {
        this.name = str;
        cfg = Config.builder().key(apiKey[0]).timeOut(10).build();
        AlphaVantage.api().init(cfg);
    }
    public Article(String str, TimeSpan ts, String pA, String values){
        name= str;
        timeSpan=ts;
        pointAmount=Integer.parseInt(pA);

    }

    public String getName() {
        return name;
    }
    /**
     * Wandelt String in date um
     *
     * @param str ; nimmt parameter und wandelt in in Date um
     * @return ; Date vom angegebenen String
     */
    public static Date convStringToDate(String str) {
        String[] endSplit = str.split("[- :]");
        return new Date(Integer.parseInt(endSplit[0]), Integer.parseInt(endSplit[1]), Integer.parseInt(endSplit[2]));
    }

    private int getYear(String str) {
        int i = 0;
        StringBuilder year = new StringBuilder();
        while (str.charAt(i) != '-') {
            year.append(str.charAt(i));
            ++i;
        }
        return Integer.parseInt(year.toString());
    }


    /**
     * Methode gibt die Differenz der beiden Tage in Tagen zurück.
     *
     * @param dStart; AnfangsTag
     * @param end;    EndTag
     * @return ; Anzahl an Tagen zwischen dem Anfangs- und dem End-Tag
     */
    private long diffDate(Date dStart, Date end) {
        return Math.abs(dStart.getTime() - end.getTime()) / 86400000;//86400000 um tage zu bekommen
    }

    /**
     * Methode setValues liest die Daten über die Aktie im angegebenen String und speichert die Daten in die ArrayList
     * values, die aus Units besteht. Die Zeitspanne der Daten kann wie folgt gewählt werden:
     * day, oneMonth, threeMonths, sixMonths, yearToday, year, fiveYear
     *
     * @param timeSpan ; Gibt an, welche Zeitspanne von daten gewollt sind.
     * @return ; false: Wenn zu viele anfragen an die Api gesendet wurden. true: Wenn keine fehler aufgetreten sind.
     */
    public boolean setValues(TimeSpan timeSpan) {
        values.clear();
        this.timeSpan=timeSpan;
        pointAmount = 0;
        TimeSeriesResponse response = null;
        if (timeSpan == TimeSpan.day) {
            response = AlphaVantage.api().timeSeries().intraday().forSymbol(name).fetchSync();
        }
        if (timeSpan == TimeSpan.oneMonth || timeSpan == TimeSpan.threeMonths) {
            response = AlphaVantage.api().timeSeries().daily().adjusted().forSymbol(name).fetchSync();
        }
        if (timeSpan == TimeSpan.sixMonths || timeSpan == TimeSpan.yearToday || timeSpan == TimeSpan.year ) {
            response = AlphaVantage.api().timeSeries().weekly().forSymbol(name).fetchSync();
        }
        if(timeSpan==TimeSpan.fiveYear){
            response= AlphaVantage.api().timeSeries().monthly().forSymbol(name).fetchSync();
        }
        if (timeSpan == TimeSpan.max) {// max benötigt 3 api calls. so umschreiben, dass man weekly nimmt und dann wenn zu viele werte sind in monthly umrechenne
            response = AlphaVantage.api().timeSeries().monthly().fetchSync();
            if (response.getStockUnits().size() > 0 && response.getStockUnits().size() < 25) {
                response = AlphaVantage.api().timeSeries().weekly().fetchSync();
                if (response.getStockUnits().size() > 0 && response.getStockUnits().size() < 25)
                    response = AlphaVantage.api().timeSeries().daily().adjusted().fetchSync();

            }
        }
        if (response == null || response.getStockUnits() == null || response.getStockUnits().size() == 0)//wenn zu viele api calls gemacht wurden.
        {
            if(cfg.getKey()==apiKey[1])
                return false;
            cfg = Config.builder().key(apiKey[1]).timeOut(10).build();
            AlphaVantage.api().init(cfg);
            if(!setValues(timeSpan)){
                return false;
            }else {
                return true;
            }
        }
        String first = response.getStockUnits().get(0).getDate();
        Date dStart = convStringToDate(first);
        for (StockUnit u : response.getStockUnits()) {
            if (timeSpan == TimeSpan.oneMonth && diffDate(dStart, convStringToDate(u.getDate())) >= 30)
                break;
            if (timeSpan == TimeSpan.threeMonths && diffDate(dStart, convStringToDate(u.getDate())) >= 90)
                break;
            if (timeSpan == TimeSpan.sixMonths && diffDate(dStart, convStringToDate(u.getDate())) >= 180)
                break;
            if (timeSpan == TimeSpan.yearToday && getYear(first) != getYear(u.getDate()))
                break;
            if (timeSpan == TimeSpan.year && diffDate(dStart, convStringToDate(u.getDate())) >= 365)
                break;
            if (timeSpan == TimeSpan.fiveYear && diffDate(dStart, convStringToDate(u.getDate())) >= 365 * 5)
                break;
            values.add(new Unit(u));
        }
        pointAmount = values.size();
        return true;
    }

    public TimeSpan getTimeSpan() {
        return timeSpan;
    }

    public int getPointAmount() {
        return pointAmount;
    }

}

