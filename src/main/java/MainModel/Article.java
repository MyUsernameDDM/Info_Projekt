package MainModel;

import Utils.SearchUtils;
import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


public class Article implements Serializable {
    private ArrayList<Unit> values = new ArrayList<>();
    private final String name;
    private int pointAmount = 0;
    private TimeSpan timeSpan;
    private transient TimeSeriesResponse apiResponse = null;
    private final transient SafeArticle safeArticle;


    public ArrayList<Unit> getValues() {
        return values;
    }


    public Article(String str, SafeArticle safeArticle) {
        this.name = str;
        Config cfg = Config.builder().key("QESJBL81ZZ99LAQX").timeOut(10).build();
        AlphaVantage.api().init(cfg);
        this.safeArticle=safeArticle;
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
        if(safeArticle.getSafedArticles()!=null) {
            for (Article a : safeArticle.getSafedArticles()) {
                if (a.getName().equals(name) && timeSpan == a.getTimeSpan()) {
                    pointAmount = a.getPointAmount();
                    values = a.getValues();
                    return true;
                }
            }
        }
        this.timeSpan = timeSpan;
        values.clear();

        pointAmount = 0;
        apiResponse = getResponse(timeSpan);
        if (apiResponse == null)
            return false;
        values = getValuesFromSpan(timeSpan, apiResponse);
        pointAmount = values.size();
        SafeArticle.addArticleFile(this);
        addOtherTimeSpansToFile();
        return true;
    }
    private void addOtherTimeSpansToFile(){
        Article[] otherTimeSpans = new Article[7];
        int count = 0;
        for (TimeSpan t : TimeSpan.values()) {
            if (t != timeSpan) {
                otherTimeSpans[count] = new Article(name, safeArticle);
                boolean tsInOther = false;
                int indexInOther = -1;
                for (int i = 0; i < otherTimeSpans.length; ++i) {
                    if (otherTimeSpans[i] != null) {
                        if (t == TimeSpan.oneMonth || t == TimeSpan.threeMonths) {
                            if (otherTimeSpans[i].getTimeSpan() == TimeSpan.oneMonth || otherTimeSpans[i].getTimeSpan() == TimeSpan.threeMonths) {
                                tsInOther = true;
                                indexInOther = i;
                                break;
                            }
                        }
                        if (t == TimeSpan.sixMonths || t == TimeSpan.yearToday || t == TimeSpan.year) {
                            if (otherTimeSpans[i].getTimeSpan() == TimeSpan.sixMonths || otherTimeSpans[i].getTimeSpan() == TimeSpan.year || otherTimeSpans[i].getTimeSpan() == TimeSpan.yearToday) {
                                tsInOther = true;
                                indexInOther = i;
                                break;
                            }
                        }
                        if (t == TimeSpan.fiveYear || t == TimeSpan.max) {
                            if (otherTimeSpans[i].getTimeSpan() == t) {
                                tsInOther = true;
                                indexInOther = i;
                                break;
                            }
                        }
                    }
                }
                if (t == TimeSpan.oneMonth && timeSpan == TimeSpan.threeMonths || t == TimeSpan.threeMonths && timeSpan == TimeSpan.oneMonth) {
                    otherTimeSpans[count].setValues(getValuesFromSpan(t, apiResponse));
                } else if (t == TimeSpan.sixMonths && (timeSpan == TimeSpan.year || timeSpan == TimeSpan.yearToday) || t == TimeSpan.year && (timeSpan == TimeSpan.sixMonths || timeSpan == TimeSpan.yearToday) || t == TimeSpan.yearToday && (timeSpan == TimeSpan.year || timeSpan == TimeSpan.sixMonths)) {
                    otherTimeSpans[count].setValues(getValuesFromSpan(t, apiResponse));
                } else if ((t == TimeSpan.fiveYear && timeSpan == TimeSpan.max) || (t == TimeSpan.max && timeSpan == TimeSpan.fiveYear)) {
                    otherTimeSpans[count].setValues(getValuesFromSpan(t, apiResponse));
                } else if (tsInOther && (t == TimeSpan.oneMonth || t == TimeSpan.threeMonths)) {
                    otherTimeSpans[count].setValues(getValuesFromSpan(t, otherTimeSpans[indexInOther].getApiResponse()));
                } else if (tsInOther && (t == TimeSpan.sixMonths || t == TimeSpan.yearToday || t == TimeSpan.year)) {
                    otherTimeSpans[count].setValues(getValuesFromSpan(t, otherTimeSpans[indexInOther].getApiResponse()));
                } else if (tsInOther && (t == TimeSpan.fiveYear || t == TimeSpan.max)) {
                    otherTimeSpans[count].setValues(getValuesFromSpan(t, otherTimeSpans[indexInOther].getApiResponse()));
                } else {
                    otherTimeSpans[count].setApiResponse(getResponse(t));
                    if (otherTimeSpans[count].getApiResponse() == null)
                        break;
                    otherTimeSpans[count].setValues(getValuesFromSpan(t, otherTimeSpans[count].getApiResponse()));
                }
                otherTimeSpans[count].setPointAmount(otherTimeSpans[count].getValues().size());
                otherTimeSpans[count].setTimeSpan(t);
                SafeArticle.addArticleFile(otherTimeSpans[count]);
                count++;
            }
        }
    }

    private TimeSeriesResponse getResponse(TimeSpan timeSpan) {
        TimeSeriesResponse response = null;
        if (timeSpan == TimeSpan.day) {
            response = AlphaVantage.api().timeSeries().intraday().forSymbol(name).fetchSync();
        }
        if (timeSpan == TimeSpan.oneMonth || timeSpan == TimeSpan.threeMonths) {
            response = AlphaVantage.api().timeSeries().daily().adjusted().forSymbol(name).fetchSync();
        }
        if (timeSpan == TimeSpan.sixMonths || timeSpan == TimeSpan.yearToday || timeSpan == TimeSpan.year) {
            response = AlphaVantage.api().timeSeries().weekly().forSymbol(name).fetchSync();
        }
        if (timeSpan == TimeSpan.fiveYear || timeSpan == TimeSpan.max) {
            response = AlphaVantage.api().timeSeries().monthly().forSymbol(name).fetchSync();
        }
        if (response == null || response.getStockUnits() == null || response.getStockUnits().size() == 0)//wenn zu viele api calls gemacht wurden.
        {
            return null;
        }
        return response;
    }

    private ArrayList<Unit> getValuesFromSpan(TimeSpan timeSpan, TimeSeriesResponse response) {
        ArrayList<Unit> values = new ArrayList<>();
        String first = response.getStockUnits().get(0).getDate();
        Date dStart = convStringToDate(first);
        int count = 0;
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
            if (timeSpan == TimeSpan.max && count % 4 == 0)//alle 4 monate
                values.add(new Unit(u));
            if (timeSpan != TimeSpan.max)
                values.add(new Unit(u));
            count++;
        }
        return values;
    }

    public void setValues(ArrayList<Unit> values) {
        this.values = values;
    }

    public void setPointAmount(int pointAmount) {
        this.pointAmount = pointAmount;
    }

    public void setTimeSpan(TimeSpan timeSpan) {
        this.timeSpan = timeSpan;
    }

    public TimeSpan getTimeSpan() {
        return timeSpan;
    }

    public int getPointAmount() {
        return pointAmount;
    }

    public TimeSeriesResponse getApiResponse() {
        return apiResponse;
    }

    public void setApiResponse(TimeSeriesResponse apiResponse) {
        this.apiResponse = apiResponse;
    }
}

