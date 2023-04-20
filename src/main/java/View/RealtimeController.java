package View;

import MainModel.Main;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static MainModel.Main.mode;

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

}
