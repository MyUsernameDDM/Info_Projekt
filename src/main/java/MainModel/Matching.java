package MainModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Matching extends Thread {
    private String str;

    public Matching(String s) {
        str = s;
    }

    public ArrayList<matchUnits> matches = new ArrayList<>();

    @Override
    public void run() {
        try {
            URL url = new URL("https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords=" + str + "&apikey=" + "QESJBL81ZZ99LAQX");
            HttpURLConnection con = null;
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String input = null;
            int count = 0;
            while (count < 2) {
                input = in.readLine();
                System.out.println(input);
                if (input == null)
                    return;
                if (input.contains("{")) {
                    count++;
                    System.out.println(count);
                }

            }
            while (true) {
                String[] data = new String[9];
                for (int i = 0; i < data.length; ++i) {
                    input = in.readLine();
                    if (input == null) {
                        break;
                    }
                    if (input.contains("}"))
                        break;
                    System.out.println(input);
                    data[i] = input;
                }

                matches.add(new matchUnits(data));
                while(true){
                    input= in.readLine();
                    if(input==null)
                        break;
                    if(input.contains("{"))
                        break;
                }
                if(input==null)
                    break;
            }
            in.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
