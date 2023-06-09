package View;

import MainModel.Article;
import MainModel.TimeSpan;
import MainModel.Unit;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.Date;


/**
 * CourseController dient als Controller fuer die Kursanzeige
 */
public class CourseController {
    //courseStatus stellt das enum für die normale Kursanzeige und jene mit Charts bereit
    public enum courseStatus {normalCourse, chartCourse}

    Controller controller;



    //courseState: Gibt an, ob die normale Kursansicht gezeigt wird, oder die Ansicht mit Hämmern
    courseStatus courseState;

    CourseView courseView;

    /**
     * Der Konstruktor von courseUtils hat den Anzeigestatus, den View für die Kursanzeige und den Controller als Parameter
     * @param courseState
     * @param courseView
     * @param controller
     */
    public CourseController(courseStatus courseState, CourseView courseView, Controller controller) {
        this.courseState = courseState;
        this.courseView = courseView;
        this.controller = controller;
    }

    /**
     * Methode setzt die Groesse der Kursanzeige passend
     */
    public void adjustCourseSize(double newWidth, double newHeight) {
        courseView.backGround.setWidth(newWidth);
        courseView.backGround.setHeight(newHeight);

        //alten Kurs loeschen
        courseView.root.getChildren().clear();
        courseView.root.getChildren().add(courseView.backGround);

        showCourse();
    }

    /**
     * Erzeugt einen neuen Article und setzt den CurrentArticle auf den neu Erzeugten Article
     * @param articleName Name des Artikles
     * @param symbol Symbol um in der API die Aktie zu suchen.
     * @param currency Währung der Aktie um diese anschließend darstellen zu können.
     */
    public void displayCourse(String articleName, String symbol, String currency) {
        Article article = new Article(articleName, symbol, controller.safeArticle, currency);
        while (!article.setValues(controller.currentTimeSpan)) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        controller.currentArticle = article;
        showCourse();
    }

    /**
     * Die Methode showCourse zeichnet den Kurs und ruft dabei, je nachdem ob der normale oder der Chartkurs angezeigt werden soll
     * setNormalView oder setCandleStick auf
     */
    public void showCourse() {
        if (controller.currentArticle == null || controller.currentArticle.getValues() == null || controller.currentArticle.getPointAmount() == 0)
            throw new IllegalArgumentException();
        //Alten Kurs loeschen
        courseView.root.getChildren().clear();
        courseView.root.getChildren().addAll(courseView.backGround, courseView.articleNameLabel);

        //Artikelname setzen
        courseView.articleNameLabel.setText(controller.currentArticle.getName()+"\nCurrency:"+ controller.currentArticle.getCurrency());

        Unit min = controller.currentArticle.getValues().get(0);
        Unit max = controller.currentArticle.getValues().get(0);
        if (courseState == courseStatus.chartCourse) {
            for (Unit u : controller.currentArticle.getValues()) {
                if (u != null) {
                    if (u.getHigh() > max.getHigh()) {
                        max = u;
                    }
                    if (min.getLow() > u.getLow()) {
                        min = u;
                    }
                }
            }
        } else {
            for (Unit u : controller.currentArticle.getValues()) {
                if (u.getClose() > max.getClose()) {
                    max = u;
                }
                if (min.getClose() > u.getClose()) {
                    min = u;
                }
            }
        }
        //anpassen an breite und höhe
        double diffMinMax = max.getHigh() - min.getLow();

        double valueperPixelHeight = diffMinMax / courseView.backGround.getHeight();//Wert der angibt, wie viel sich in der höhe pro pixel verändert
        long startTime = controller.currentArticle.getValues().get(controller.currentArticle.getPointAmount() - 1).getDate().getTime();
        long diffTime = controller.currentArticle.getValues().get(0).getDate().getTime() - startTime;
        //wert, wie viel sich die Zeit verändert
        //diffTime *= 1.23;
        double candleStickWidth = courseView.backGround.getWidth() / (controller.currentArticle.getPointAmount() * 2);//wert, wie groß der body der aktie sein kann
        double valueperPixelWidth;//wert, wie viel sich in der breite pro pixel verändert
        if (courseState == courseStatus.chartCourse)
            valueperPixelWidth = diffTime / (courseView.backGround.getWidth() - candleStickWidth);
        else
            valueperPixelWidth = diffTime / (courseView.backGround.getWidth());

        //übergeben an setCandleStick
        if (courseState == courseStatus.chartCourse) {
            int count=0;
            for (Unit u : controller.currentArticle.getValues()) {
                setCandleStick(u, valueperPixelWidth, valueperPixelHeight, candleStickWidth, min.getLow(), startTime,count);
                count++;
            }
        } else {
            setNormalView(valueperPixelWidth, valueperPixelHeight, min.getLow(), controller.currentArticle.getValues().get(controller.currentArticle.getPointAmount() - 1).getDate(), controller.currentArticle.getValues().get(0).getClose() >= controller.currentArticle.getValues().get(controller.currentArticle.getPointAmount() - 1).getClose());
        }
    }

    /**
     * setNormalView zeichnet die normale Kursanzeige als Aneinanderreihungen von Linien
     */
    private void setNormalView(double posx, double posy, double startHeight, Date startTime, boolean rise) {
        int points=controller.currentArticle.getPointAmount();
        for (int i = 0; i < points - 1; ++i) {

            double startPosX = (controller.currentArticle.getValues().get(i).getDate().getTime() - startTime.getTime()) / posx;
            double startPosY = (controller.currentArticle.getValues().get(i).getClose() - startHeight) / posy;
            double endPosX = (controller.currentArticle.getValues().get(i + 1).getDate().getTime() - startTime.getTime()) / posx;
            double endPosY = (controller.currentArticle.getValues().get(i + 1).getClose() - startHeight) / posy;
            if (controller.currentTimeSpan == TimeSpan.day) {
                startPosX= courseView.backGround.getWidth()/points*Math.abs(i-points);
                startPosY = (controller.currentArticle.getValues().get(i).getClose() - startHeight) / posy;
                endPosX = courseView.backGround.getWidth()/controller.currentArticle.getPointAmount()*(Math.abs(i+1-points));
                endPosY = (controller.currentArticle.getValues().get(i + 1).getClose() - startHeight) / posy;
            }
            Line l = new Line(startPosX, courseView.backGround.getHeight() - startPosY, endPosX, courseView.backGround.getHeight() - endPosY);
            Ellipse tempEllipse = new Ellipse(startPosX, courseView.backGround.getHeight() - startPosY, 1.5, 1.5);
            controller.courseView.points.add(tempEllipse);


            if (rise) {
                l.setStroke(Color.GREEN);
                tempEllipse.setFill(Color.GREEN);
            } else {
                l.setStroke(Color.RED);
                tempEllipse.setFill(Color.RED);
            }
            controller.infoView.showChartInfoView(l, controller.currentArticle.getValues().get(i));
            controller.infoView.showChartInfoView(tempEllipse, controller.currentArticle.getValues().get(i));
            courseView.root.getChildren().addAll(l,tempEllipse);
        }
    }

    /**
     * setCandleStick wird verwendet, um einen einzelnen Chart/CandleStick zu zeichnen und wird daher für jeden Chart einzeln aufgerufen
     * @param u unit die als Kerze gezeichnet wird
     * @param posx wie viel sich pro pixel die x achse verändert
     * @param posy wie viel sich pro pixel die y achse verändert
     * @param width die Breite der Kerze
     * @param startHeight tiefste höhe, aller units, wird benötigt um den verlauf genau so groß zu machen, wie die courseview ist.
     * @param startTime anfangsZeit, um das Layout der Candles richtig darstellen zu können
     * @param index wird bei day verwendet, da dabei die zeit immer die selbe ist.
     */

    private void setCandleStick(Unit u, double posx, double posy, double width, double startHeight, long startTime, int index) {
        double candlePosy = ((Math.max(u.getOpen(), u.getClose()) - startHeight) / posy);
        double candlePosx = (u.getDate().getTime() - startTime) / posx;
        if(controller.currentTimeSpan == TimeSpan.day)
            candlePosx=courseView.backGround.getWidth()/controller.currentArticle.getPointAmount()*Math.abs(controller.currentArticle.getPointAmount()-index);
        double candlePosYMin = ((Math.min(u.getClose(), u.getOpen()) - startHeight) / posy);
        Rectangle body = new Rectangle(width, Math.abs(candlePosy - candlePosYMin));
        body.setStroke(Color.BLACK);
        body.setY(courseView.backGround.getHeight() - candlePosy);
        body.setX(candlePosx);
        if (u.getOpen() <= u.getClose()) {
            body.setFill(Color.GREEN);
        } else {
            body.setFill(Color.RED);
        }
        controller.infoView.showChartInfoView(body, u);
        double startEndX = candlePosx + width / 2;
        Line topLine = new Line(startEndX, courseView.backGround.getHeight() - candlePosy, startEndX, courseView.backGround.getHeight() - ((u.getHigh() - startHeight) / posy));
        Line bottomLine = new Line(startEndX, courseView.backGround.getHeight() - candlePosYMin, startEndX, courseView.backGround.getHeight() - ((u.getLow() - startHeight) / posy));
        courseView.root.getChildren().addAll(body, topLine, bottomLine);
    }

}
