package View;

import MainModel.Unit;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Klasse zum darstellen von informationen beim hovern über verschiedenen Dingen
 */
public class InfoView {
    /**
     * Methode zum anzeigen eines Infofeldes in der Wallet
     * @param button der Knopf bei welchem das InfoFeld kommt.
     * @param controller controller um den aktuellen Article zu bekommen.
     */
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

    /**
     * Methode zum Anzeige eines Infofeldes, das den Wert an einem bestimmte Punkt anzeigt
     * @param node Parameter, der entweder ein RectAngle für die Charts oder eine Ellipse/Linie für die normale ANzeige ist
     * @param unit Unit, sodass man alle Informationen für den aktuellen zeitpunkt anzeigen kann
     */
    public void showChartInfoView(Node node, Unit unit) {
        Tooltip t = new Tooltip();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), event -> {
            if (unit == null) {
                t.setText("Nichts gefunden");
            } else if(node instanceof Rectangle){
                String formattedDate = getFormattedDate(unit);
                t.setText("Low: " + unit.getLow() + "\n" + "High: " + unit.getHigh() + "\n" + "Open: " + unit.getOpen()
                        + "\n" + "Close: " + unit.getClose() + "\n" + "Volume: " + unit.getVolume() + "\n" + "Time: " + formattedDate);
            }else{
                String formattedDate = getFormattedDate(unit);;
                t.setText("Open: " + unit.getOpen()
                        + "\nTime: " + formattedDate);
            }

            Tooltip.install(node, t);
            t.setShowDelay(Duration.millis(1));
        }));
        Duration.millis(50);
        timeline.play();
    }

    //Private Methode, die das Datum eines Units in einem String zurückliefert
    private String getFormattedDate(Unit unit) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, unit.getDate().getYear());
        cal.set(Calendar.MONTH, unit.getDate().getMonth() - 1);
        cal.set(Calendar.DAY_OF_MONTH, unit.getDate().getDate());
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);

    }

    /**
     * Methode, um die ID von einer Node in einem InfoView anzuzeigen
     * @param node kann ein Button, Label oder andere Klassen sein, die Unterklassen von node sind
     */
    public void showIDofNodeInfoView(Node node){
        Tooltip t = new Tooltip();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), event -> {
            t.setText(node.getId());
            Tooltip.install(node, t);
            t.setShowDelay(Duration.millis(1));
        }));
        Duration.millis(50);
        timeline.play();
    }
}

