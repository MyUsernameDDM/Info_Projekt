package View;

import MainModel.Main;
import javafx.scene.Scene;

import static MainModel.Main.*;

public class RealtimeController extends Controller{
    GroundView groundView = new GroundView(this);
    SearchView searchView = new SearchView(this);
    WatchListView watchListView = new WatchListView(this);

    public RealtimeController() {
    }

    public GroundView getGroundView() {
        return groundView;
    }

    public SearchView getSearchView() {
        return searchView;
    }

    public Scene getScene(){
        return groundView.scene;
    }


    /**
     * Wechsel auf den SimulationController und somit auf den SimulationMode
     */
    @Override
    public void changeMode() {
        mode = Main.status.simulation;
    }

    @Override
    public void changeTimeMode(int ID){
        Main.buttonTime[ID] = ID;
    }
}
