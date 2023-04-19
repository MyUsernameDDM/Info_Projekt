package View;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

public class RealtimeController {
    GroundView realtimeView = new GroundView();
    SearchView searchView = new SearchView();

    public GroundView getRealtimeView() {
        return realtimeView;
    }

    public SearchView getSearchView() {
        return searchView;
    }

    public Scene getScene(){
        return realtimeView.scene;
    }

    public RealtimeController() {
        Rectangle rectangle = new Rectangle();
        rectangle.setFill(Color.RED);
        rectangle.setWidth(40);
        rectangle.setHeight(40);

        rectangle.setX(50);

        realtimeView.root.setCenter(rectangle);
        realtimeView.root.setLeft(searchView.root);
    }
}
