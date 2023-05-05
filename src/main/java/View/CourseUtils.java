package View;

import MainModel.Article;
import MainModel.TimeSpan;
import MainModel.Unit;
import javafx.scene.paint.Color;
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
     * Methode setzt die Groesse der Kursanzeige passend
     */
    public void adjustCourseSize(double newWidth, double newHeight){
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

        while(!(currentArticle.setValues(interval))){
            currentArticle.setValues(interval);
        }
        showCourse();
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
    public void showCourse() {
        if (currentArticle == null || currentArticle.getValues() == null || currentArticle.getPointAmount() == 0)
            throw new IllegalArgumentException();
        //Alten Kurs loeschen
        courseView.root.getChildren().clear();
        courseView.root.getChildren().add(courseView.backGround);

        Unit min = currentArticle.getValues().get(0);
        Unit max = currentArticle.getValues().get(0);
        if(courseState==courseStatus.chartCourse) {
            for (Unit u : currentArticle.getValues()) {
                if (u.getHigh() > max.getHigh()) {
                    max = u;
                }
                if (min.getLow() > u.getLow()) {
                    min = u;
                }
            }
        }else{
            for (Unit u : currentArticle.getValues()) {
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
        long startTime = currentArticle.getValues().get(currentArticle.getPointAmount() - 1).getDate().getTime();
        long diffTime = currentArticle.getValues().get(0).getDate().getTime() - startTime;
        //wert, wie viel sich die Zeit verändert
        diffTime *= 1.23;
        double valueperPixelWidth = diffTime / courseView.backGround.getWidth();//wert, wie viel sich in der breite pro pixel verändert
        double candleStickWidth = courseView.backGround.getWidth() / (currentArticle.getPointAmount() * 2);//wert, wie groß der body der aktie sein kann
        //übergeben an setCandleStick
        if (courseState == courseStatus.chartCourse) {
            for (Unit u : currentArticle.getValues()) {
                setCandleStick(u, valueperPixelWidth, valueperPixelHeight, candleStickWidth, min.getLow(), startTime);
            }
        } else {
            setNormalView(valueperPixelWidth, valueperPixelHeight,min.getLow(),startTime,currentArticle.getValues().get(0).getClose()>currentArticle.getValues().get(currentArticle.getPointAmount()-1).getClose());
        }
    }

    private void setNormalView(double posx, double posy, double startHeight, long startTime, boolean rise) {
        for (int i = 0; i < currentArticle.getPointAmount() - 1; ++i) {
            double startPosX = (currentArticle.getValues().get(i).getDate().getTime() - startTime) / posx;
            double startPosY = (currentArticle.getValues().get(i).getClose() - startHeight) / posy;
            double endPosX = (currentArticle.getValues().get(i + 1).getDate().getTime() - startTime) / posx;
            double endPosY = (currentArticle.getValues().get(i + 1).getClose() - startHeight) / posy;
            Line l = new Line(startPosX, courseView.backGround.getHeight()-startPosY, endPosX, courseView.backGround.getHeight()-endPosY);
            if (rise) {
                l.setStroke(Color.GREEN);
            } else {
                l.setStroke(Color.RED);
            }
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
    private void setCandleStick(Unit u, double posx, double posy, double width, double startHeight, long startTime) {
        double candlePosy = ((Math.max(u.getOpen(), u.getClose()) - startHeight) / posy);
        double candlePosx = (u.getDate().getTime() - startTime) / posx;
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
        double startEndX = candlePosx + width / 2;
        Line topLine = new Line(startEndX, courseView.backGround.getHeight() - candlePosy, startEndX, courseView.backGround.getHeight() - ((u.getHigh() - startHeight) / posy));
        Line bottomLine = new Line(startEndX, courseView.backGround.getHeight() - candlePosYMin, startEndX, courseView.backGround.getHeight() - ((u.getLow() - startHeight) / posy));
        courseView.root.getChildren().addAll(body, topLine, bottomLine);
    }

}
