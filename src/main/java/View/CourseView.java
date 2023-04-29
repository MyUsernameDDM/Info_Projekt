package View;

import MainModel.Article;
import MainModel.Unit;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;


public class CourseView {
    Group root = new Group();
    Rectangle backGround = new Rectangle(600, 400);

    ArrayList<Circle> points = new ArrayList<>();
    ArrayList<Chart> charts = new ArrayList<>();

    Button changeStateButton = new Button();

    public CourseView() {
        //Background anpassen
        backGround.setFill(Color.ALICEBLUE);
        backGround.setWidth(500);
        backGround.setHeight(400);

        root.getChildren().add(backGround);
    }

    //Damian: i woas net genau ob du des in chart eini tian willsch, i fins so fost gschickter, weil wianiger speicher und so
    //verbraucht wert und man die arraylist von Chart net braucht
    //posx und posy die einheiten pro pixel
    public void setCandleStick(Unit u, double posx, double posy, double width, double startHeight) {
        double candlePosy = (posy * (Math.max(u.getOpen(), u.getClose()) - startHeight));
        double candlePosx = posx * u.getDate().getTime();
        Rectangle body = new Rectangle(candlePosx - width / 2, candlePosy);
        body.setHeight(Math.abs(u.getClose() - u.getOpen()) * posy);
        body.setWidth(width);
        if (u.getOpen() >= u.getClose()) {
            body.setFill(Color.GREEN);
        } else {
            body.setFill(Color.RED);
        }
        Line topLine = new Line(candlePosx, candlePosy, candlePosx, (posy*(u.getHigh()-startHeight)));
        Line bottomLine = new Line(candlePosx, (posy*(Math.min(u.getClose(),u.getOpen())-startHeight)), candlePosx, posy*(u.getLow()-startHeight));
        root.getChildren().addAll(body, topLine, bottomLine);
    }

    public void setCourse(Article art) {
        if (art == null || art.getValues() == null || art.getPointAmount() == 0)
            throw new IllegalArgumentException();
        Unit min = art.getValues().get(0);
        Unit max = art.getValues().get(0);
        for (Unit u : art.getValues()) {
            if (Math.max(u.getClose(), u.getOpen()) > Math.max(max.getClose(), max.getOpen())) {
                max = u;
            }
            if (Math.min(u.getClose(), u.getOpen()) < Math.min(u.getOpen(), min.getClose())) {
                min = u;
            }
        }
        //anpassen an breite und höhe
        double diffMinMax = max.getClose() - min.getClose();
        diffMinMax += diffMinMax / 80;//zwanzig prozent vergößern damit oben und unten noch platz ist.
        double valueperPixelHeight = diffMinMax / backGround.getHeight();//Wert der angibt, wie viel sich in der höhe pro pixel verändert
        double diffTime = art.getValues().get(art.getPointAmount() - 1).getDate().getTime() - art.getValues().get(0).getDate().getTime();
        //wert, wie viel sich die Zeit verändert
        double valueperPixelWidth = diffTime / backGround.getWidth();//wert, wie viel sich in der breite pro pixel verändert
        double candleStickWidth = backGround.getWidth() / art.getPointAmount();//wert, wie groß der body der aktie sein kann
        //übergeben an setCandleStick
        for (Unit u : art.getValues()) {
            setCandleStick(u, valueperPixelWidth, valueperPixelHeight, candleStickWidth, Math.min(min.getClose(), min.getOpen()));
        }
    }

}
