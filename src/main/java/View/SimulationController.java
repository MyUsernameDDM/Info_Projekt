package View;

import MainModel.Main;
import javafx.scene.Scene;

import static MainModel.Main.mode;

public class SimulationController extends Controller{
    GroundView groundView = new GroundView(this);
    SearchView searchView = new SearchView();

    public GroundView getGroundView() {
        return groundView;
    }

    public SearchView getSearchView() {
        return searchView;
    }

    public Scene getScene(){
        return groundView.scene;
    }

    @Override
    public void changeMode() {
        mode = Main.status.realtime;
    }
}
