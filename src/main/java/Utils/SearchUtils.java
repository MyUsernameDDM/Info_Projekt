package Utils;

import MainModel.MatchUnits;
import View.Controller;
import View.SearchView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SearchUtils extends Thread{


    public static void labelclicked(MatchUnits result, Controller controller,SearchView searchView){
        controller.setCourseView(result.getSymbol());
        searchView.clearSearching();
    }
    public static MatchUnits[] getMatchings (String str) {
        MatchUnits[] matches= new MatchUnits[10];
        try {
            URL url = new URL("https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords=" + str + "&apikey=" + "QESJBL81ZZ99LAQX");
            HttpURLConnection con;
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String input;
            int count = 0;
            while (count < 2) {
                input = in.readLine();
                if (input == null)
                    return null;
                if (input.contains("{")) {
                    count++;
                }

            }
            for (int j = 0; j < matches.length; ++j) {
                String[] data = new String[9];
                for (int i = 0; i < data.length; ++i) {
                    input = in.readLine();
                    if (input == null) {
                        break;
                    }
                    if (input.contains("}"))
                        break;
                    data[i] = input;
                }

                matches[j] = new MatchUnits(data);
                while (true) {
                    input = in.readLine();
                    if (input == null)
                        break;
                    if (input.contains("{"))
                        break;
                }
                if (input == null)
                    break;
            }
            in.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return matches;
    }







}
