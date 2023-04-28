package MainModel;

public class matchUnits {
    private final String symbol;
    private final String name;
    private final String type;
    private final String region;
    private final String marketOpen;
    private final String marketClose;
    private final String timeZone;
    private final String currency;

    public matchUnits(String[] values) {
        if (values.length != 9) {
            throw new IllegalArgumentException();
        }
        symbol = values[0];
        name = values[1];
        type = values[2];
        region = values[3];
        marketOpen = values[4];
        marketClose = values[5];
        timeZone = values[6];
        currency = values[7];
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getRegion() {
        return region;
    }

    public String getMarketOpen() {
        return marketOpen;
    }

    public String getMarketClose() {
        return marketClose;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public String getCurrency() {
        return currency;
    }
}
