package View;

import MainModel.Article;
import MainModel.TimeSpan;
import MainModel.Unit;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class CourseUtils {
    public enum courseStatus {normalCourse, chartCourse}


    /**
     * courseState: Gibt an, ob die normale Kursansicht gezeigt wird, oder die Ansicht mit Hämmern
     */
    courseStatus courseState = CourseUtils.courseStatus.normalCourse;

    CourseView courseView;
    Article currentArticle;

    public void setCurrentArticle(Article currentArticle) {
        this.currentArticle = currentArticle;
    }

    public CourseUtils(courseStatus courseState, CourseView courseView, Article currentArticle) {
        this.courseState = courseState;
        this.courseView = courseView;
        this.currentArticle = currentArticle;
    }

    /**
     * Die Methode showNormalCourse setzt den View so, dass der normale Kurs angezeigt wird
     */
    public void showNormalCourse(){
        if (currentArticle == null || currentArticle.getValues() == null || currentArticle.getPointAmount() == 0)
            throw new IllegalArgumentException();
        Unit min = currentArticle.getValues().get(0);
        Unit max = currentArticle.getValues().get(0);
        for (Unit u : currentArticle.getValues()) {
            if (Math.max(u.getClose(), u.getOpen()) > Math.max(max.getClose(), max.getOpen())) {
                max = u;
            }
            if (Math.min(u.getClose(), u.getOpen()) < Math.min(u.getOpen(), min.getClose())) {
                min = u;
            }
        }

        int pointAmount = currentArticle.getPointAmount();
        double sectionWidth = courseView.courseWidth / pointAmount;
        double pointX = 0;
        for (Unit u :currentArticle.getValues()) {
            Ellipse temp = new Ellipse(10, 10);
            temp.setCenterX(pointX);
            temp.setCenterY(u.getClose() * 100 / max.getClose());
            temp.setOnDragDetected(mouseEvent -> {
                InfoView infoView = new InfoView();
                /*Hier sollte der Wert dann gezeigt werden im Infoview*/
            });
            courseView.points.add(temp);
            courseView.root.getChildren().add(temp);
            pointX += sectionWidth;
        }
    }


    /**
     * Die View wird so umgestellt, dass das angegebene Intervall angezeigt wird
     */
    public void changeShowInterval(TimeSpan interval){
        /*
        //enum übergeben, damit es gesetzt wird
        System.out.println("timeButtonsTest");
        int pointAmount = currentArticle.getPointAmount();
        for (int i = 0; i < pointAmount; i++) {
            courseView.points.add(new Circle(20));
        }
         */

    }

    /**
     * Die Methode showChartCourse setzt den View so, dass der Kurs mit den Charts angezeigt wird
     */
    public void showChartCourse() {
        if (currentArticle == null || currentArticle.getValues() == null || currentArticle.getPointAmount() == 0)
            throw new IllegalArgumentException();
        Unit min = currentArticle.getValues().get(0);
        Unit max = currentArticle.getValues().get(0);
        for (Unit u : currentArticle.getValues()) {
            if (Math.max(u.getClose(), u.getOpen()) > Math.max(max.getClose(), max.getOpen())) {
                max = u;
            }
            if (Math.min(u.getClose(), u.getOpen()) < Math.min(u.getOpen(), min.getClose())) {
                min = u;
            }
        }
        //anpassen an breite und höhe
        double diffMinMax = max.getClose() - min.getClose();
        diffMinMax *= 1.2;  //zwanzig prozent vergößern damit oben und unten noch platz ist.
        double valueperPixelHeight = diffMinMax / courseView.courseHeight;//Wert der angibt, wie viel sich in der höhe pro pixel verändert
        double diffTime = currentArticle.getValues().get(currentArticle.getPointAmount() - 1).getDate().getTime() - currentArticle.getValues().get(0).getDate().getTime();
        //wert, wie viel sich die Zeit verändert
        double valueperPixelWidth = diffTime / courseView.courseWidth;//wert, wie viel sich in der breite pro pixel verändert
        double candleStickWidth = courseView.backGround.getWidth() / currentArticle.getPointAmount();//wert, wie groß der body der aktie sein kann
        //übergeben an setCandleStick
        for (Unit u : currentArticle.getValues()) {
            setCandleStick(u, valueperPixelWidth, valueperPixelHeight, candleStickWidth, Math.min(min.getClose(), min.getOpen()));
        }
    }

    //Damian: i woas net genau ob du des in chart eini tian willsch, i fins so fost gschickter, weil wianiger speicher und so
    //verbraucht wert und man die arraylist von Chart net braucht
    //posx und posy die einheiten pro pixel

    /**
     * private Klasse, um einen Chart zu erstellen
     * @param u Unit
     * @param posx x-Koordinate des Chart
     * @param posy y-Koordinate
     * @param width
     * @param startHeight
     */
    private void setCandleStick(Unit u, double posx, double posy, double width, double startHeight) {
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
        courseView.root.getChildren().addAll(body, topLine, bottomLine);
    }
}
