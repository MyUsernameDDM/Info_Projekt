package View;

import MainModel.Unit;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

public class InfoView {
    public void showInfoView(Button button, Controller controller) {
        Tooltip t = new Tooltip();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1), event -> {
            if (controller.currentArticle == null) {
                t.setText("Nichts gefunden");
            } else {
                Unit lastUnit=controller.currentArticle.getValues().get(0);
                if (lastUnit != null) {
                    String highPrice = Double.toString(lastUnit.getHigh());
                    String lowPrice = Double.toString(lastUnit.getLow());
                    t.setText("Name: " + button.getText() + "\n" + "High: " + highPrice + "\n" + "Low: " + lowPrice);
                }
            }
            Tooltip.install(button, t);
            t.setShowDelay(Duration.millis(100));
        }));
        Duration.millis(500);
        timeline.play();
    }
}

