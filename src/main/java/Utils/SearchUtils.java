package Utils;

import MainModel.MatchUnits;
import View.Controller;
import View.SearchView;

public class SearchUtils extends Thread{


    public static void labelclicked(MatchUnits result, Controller controller,SearchView searchView){
        controller.setCourseView(result.getSymbol());
        searchView.clearSearching();
    }







}
