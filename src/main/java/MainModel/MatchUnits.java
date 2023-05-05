package MainModel;

public class MatchUnits {
    private final String symbol;
    private final String name;
    private final String type;
    private final String region;
    private final String marketOpen;
    private final String marketClose;
    private final String timeZone;
    private final String currency;

    public MatchUnits(String[] values) {
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

    private String getOnlyData(String str){
        StringBuilder ret = new StringBuilder();
        int amountMarks = 0;
        for (int i = 0; i < str.length(); ++i) {
            if (amountMarks == 3 && str.charAt(i) != '"')
                ret.append(str.charAt(i));
            if (str.charAt(i) == '"')
                amountMarks++;
            if (amountMarks == 4)
                return ret.toString();
        }
        return ret.toString();
    }
    public String getSymbol() {
        return getOnlyData(symbol);
    }
    public String getName() {
        return getOnlyData(name);
    }

    public String getType() {
        return getOnlyData(type);
    }

    public String getRegion() {
        return getOnlyData(region);
    }

    public String getMarketOpen() {
        return getOnlyData(marketOpen);
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
