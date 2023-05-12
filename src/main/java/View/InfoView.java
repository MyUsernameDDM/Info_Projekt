package View;

import MainModel.Unit;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class InfoView {
    public void showInfoView(Button button, Controller controller) {
        Tooltip t = new Tooltip();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1), event -> {
            if (controller.currentArticle == null) {
                t.setText("Nichts gefunden");
            } else {
                Unit lastUnit = controller.currentArticle.getValues().get(0);
                if (lastUnit != null) {
                    t.setText("Name: " + button.getText() + "\n" + "High: " + lastUnit.getHigh() + "\n" + "Low: " + lastUnit.getLow());
                }
            }
            Tooltip.install(button, t);
            t.setShowDelay(Duration.millis(100));
        }));
        Duration.millis(500);
        timeline.play();
    }

    public void showChartInfoView(Node node, Unit unit) {
        Tooltip t = new Tooltip();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1), event -> {
            if (unit == null) {
                t.setText("Nichts gefunden");
            } else {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, unit.getDate().getYear() );
                cal.set(Calendar.MONTH, unit.getDate().getMonth());
                cal.set(Calendar.DAY_OF_MONTH, unit.getDate().getDate());
                Date date = cal.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String formattedDate = sdf.format(date);
                t.setText("Low: " + unit.getLow() + "\n" + "High: " + unit.getHigh() + "\n" + "Open: " + unit.getOpen()
                        + "\n" + "Close: " + unit.getClose() + "\n" + "Volume: " + unit.getVolume() + "\n" + "Time: " + formattedDate);
            }

            Tooltip.install(node, t);
            t.setShowDelay(Duration.millis(1));
        }));
        Duration.millis(50);
        timeline.play();
    }
}

