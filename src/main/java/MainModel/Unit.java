package MainModel;

import com.crazzyghost.alphavantage.timeseries.response.StockUnit;

import java.io.Serializable;
import java.util.Date;

/**
 * Klasse zum Abspeicheren von einer Einheit einer Aktie
 */
public class Unit implements Serializable {
    private final double open;
    private final double high;
    private final double low;
    private final double close;
    private final double adjustedClose;
    private final long volume;
    private final double dividendAmount;
    private final double splitCoefficient;
    private final Date dateTime;

    /**
     * Erstellen von einer Neuen Unit aus einer Einheit einer Aktie von der API
     * @param unit eine Einheit einer Aktie die von der API verwendet wird
     */
    public Unit(StockUnit unit){
        this.open= unit.getOpen();
        this.high= unit.getHigh();
        this.low= unit.getLow();
        this.close=unit.getClose();
        this.adjustedClose= unit.getAdjustedClose();
        this.volume= unit.getVolume();
        this.dividendAmount= unit.getDividendAmount();
        this.splitCoefficient= unit.getSplitCoefficient();
        this.dateTime= Article.convStringToDate(unit.getDate());
    }

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getClose() {
        return close;
    }

    public double getAdjustedClose() {
        return adjustedClose;
    }

    public long getVolume() {
        return volume;
    }

    public double getDividendAmount() {
        return dividendAmount;
    }

    public double getSplitCoefficient() {
        return splitCoefficient;
    }

    public Date getDate() {
        return dateTime;
    }
}


