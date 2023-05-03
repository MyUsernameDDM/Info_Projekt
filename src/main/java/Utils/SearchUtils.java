package Utils;

import MainModel.Article;
import MainModel.Matching;
import MainModel.matchUnits;

import java.util.ArrayList;

public class SearchUtils {
    /**
     * Methode um Aktien zu suchen
     * @param  str der gesuchten Aktie
     * @return Liste der Aktien die den Namen der gesuchten Aktie beinhalten
     */

    static public String[] search(String str){
        // 10 weil man immer nur 10 Vorschläge vom Matching bekommt
        String[] searchResults = new String[10];
        Matching matches = new Matching(str);
        matchUnits[] matchesResults;
        matches.start();
        matchesResults = matches.getMatches();

        for(int i = 0; i < 10; i++){
            if(searchResults[i]==null)
                break;
            searchResults[i] = matchesResults[i].getName();
        }
        return searchResults;
    }






}
