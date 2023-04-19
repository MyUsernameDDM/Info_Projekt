package View;

import javafx.scene.Scene;

public class SimulationController {
    GroundView groundView = new GroundView();
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

}
