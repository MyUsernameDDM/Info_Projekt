package Utils;

import MainModel.MatchUnits;
import View.Controller;
import View.CourseUtils;
import View.SearchView;

public class SearchUtils extends Thread{

    public static void buttonClicked(MatchUnits result, CourseUtils courseUtils, SearchView searchView){
        courseUtils.displayCourse(result.getSymbol());
        searchView.clearSearching();
    }







}
