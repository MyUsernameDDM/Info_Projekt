package View;

import MainModel.Article;
import MainModel.TimeSpan;
import MainModel.Unit;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

import java.util.ArrayList;

public class InfoView {
    public void showInfoView(Button button){
        Tooltip t = new Tooltip();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1), event -> {
            Article art=new Article(button.getText()); //Platzhalter
            if(art.setValues(TimeSpan.day)){
                ArrayList<Unit> values = art.getValues();
                String highPrice = Double.toString(values.get(0).getHigh());
                String lowPrice = Double.toString(values.get(0).getLow());
                t.setText("Name: " + button.getText() + "\n" + "High: " + highPrice + "\n" + "Low: " + lowPrice);
                Tooltip.install(button, t);
                t.setShowDelay(Duration.millis(100));
            } else {
                t.setText("Nichts gefunden");
                Tooltip.install(button, t);
                t.setShowDelay(Duration.millis(100));
            }
        }));
        Duration.millis(500);
        timeline.play();
    }
}
