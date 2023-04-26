package MainModel;


import java.io.ObjectInputFilter;
import java.util.ArrayList;

public class Article {

    private String name;

    public String getName() {
        return name;
    }
    public Article(String str){
        this.name = str;
    }

    public int getPointAmount(){
        return 10;
    }

    /*
       private ArrayList<Unit> values= new ArrayList<>();

    public ArrayList<Unit> getValues() {
        return values;
    }



    private void setValues(TimeSpan timeSpan){
        ObjectInputFilter.Config cfg = Config.builder()
                .key("QESJBL81ZZ99LAQX")
                .timeOut(10)
                .build();
        AlphaVantage.api().init(cfg);
        TimeSeriesResponse response= AlphaVantage.api().timeSeries().daily().outputSize(OutputSize.FULL)
                .forSymbol(name).fetchSync();
        for(StockUnit u: response.getStockUnits()){
            values.add(new Unit(u));
        }
    }

     */
}

