package View;

import MainModel.Article;
import MainModel.TimeSpan;
import MainModel.Unit;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.Date;


public class CourseController {
    public enum courseStatus {normalCourse, chartCourse}

    Controller controller;


    /**
     * courseState: Gibt an, ob die normale Kursansicht gezeigt wird, oder die Ansicht mit Hämmern
     */
    courseStatus courseState;

    CourseView courseView;

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
     * Die View wird so umgestellt, dass das angegebene Intervall angezeigt wird
     */
    public void changeShowInterval(TimeSpan interval) {

        while (!(controller.currentArticle.setValues(interval))) {
            controller.currentArticle.setValues(interval);
        }
        showCourse();
    }


    public void displayCourse(String articleName, String symbol) {
        Article article = new Article(articleName, symbol, controller.safeArticle);
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
     * Die Methode showChartCourse setzt den View so, dass der Kurs mit den Charts angezeigt wird
     */
    public void showCourse() {
        if (controller.currentArticle == null || controller.currentArticle.getValues() == null || controller.currentArticle.getPointAmount() == 0)
            throw new IllegalArgumentException();
        //Alten Kurs loeschen
        courseView.root.getChildren().clear();
        courseView.root.getChildren().addAll(courseView.backGround, courseView.articleNameLabel);

        //Artikelname setzen
        courseView.articleNameLabel.setText(controller.currentArticle.getName());

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
            setNormalView(valueperPixelWidth, valueperPixelHeight, min.getLow(), controller.currentArticle.getValues().get(controller.currentArticle.getPointAmount() - 1).getDate(), controller.currentArticle.getValues().get(0).getClose() > controller.currentArticle.getValues().get(controller.currentArticle.getPointAmount() - 1).getClose());
        }
    }

    private void setNormalView(double posx, double posy, double startHeight, Date startTime, boolean rise) {
        for (int i = 0; i < controller.currentArticle.getPointAmount() - 1; ++i) {

            double startPosX = (controller.currentArticle.getValues().get(i).getDate().getTime() - startTime.getTime()) / posx;
            double startPosY = (controller.currentArticle.getValues().get(i).getClose() - startHeight) / posy;
            double endPosX = (controller.currentArticle.getValues().get(i + 1).getDate().getTime() - startTime.getTime()) / posx;
            double endPosY = (controller.currentArticle.getValues().get(i + 1).getClose() - startHeight) / posy;
            if (controller.currentTimeSpan == TimeSpan.day) {
                startPosX= courseView.backGround.getWidth()/controller.currentArticle.getPointAmount()*i;
                startPosY = (controller.currentArticle.getValues().get(i).getClose() - startHeight) / posy;
                endPosX = courseView.backGround.getWidth()/controller.currentArticle.getPointAmount()*(i+1);
                endPosY = (controller.currentArticle.getValues().get(i + 1).getClose() - startHeight) / posy;
            }
            Line l = new Line(startPosX, courseView.backGround.getHeight() - startPosY, endPosX, courseView.backGround.getHeight() - endPosY);
            controller.courseView.points.add(new Ellipse(startPosX, startPosY, 5, 5));


            if (rise) {
                l.setStroke(Color.GREEN);
            } else {
                l.setStroke(Color.RED);
            }
            controller.infoView.showChartInfoView(l, controller.currentArticle.getValues().get(i));
            courseView.root.getChildren().add(l);
        }
    }

    /**
     * private Klasse, um einen Chart zu erstellen
     *
     * @param u           Unit
     * @param posx        x-Koordinate des Chart
     * @param posy        y-Koordinate
     * @param width
     * @param startHeight
     */
    private void setCandleStick(Unit u, double posx, double posy, double width, double startHeight, long startTime, int index) {
        double candlePosy = ((Math.max(u.getOpen(), u.getClose()) - startHeight) / posy);
        double candlePosx = (u.getDate().getTime() - startTime) / posx;
        if(controller.currentTimeSpan == TimeSpan.day)
            candlePosx=courseView.backGround.getWidth()/controller.currentArticle.getPointAmount()*index;
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
