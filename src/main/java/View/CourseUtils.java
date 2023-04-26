package View;

import MainModel.Article;
import javafx.scene.shape.Circle;

public class CourseUtils {
    public enum intervals {oneD, oneM, threeM, sixM, oneY, fiveY, max}
    public enum courseStatus {normalCourse, chartCourse}

    /**
     * interval: Gibt an, für welches Zeitintervall der Kurs angezegit werden soll
     */
    intervals interval;

    /**
     * courseState: Gibt an, ob die normale Kursansicht gezeigt wird, oder die Ansicht mit Hämmern
     */
    courseStatus courseState = CourseUtils.courseStatus.normalCourse;

    CourseView courseView;
    Article currentArticle;

    public CourseUtils(intervals interval, courseStatus courseState, CourseView courseView, Article currentArticle) {
        this.interval = interval;
        this.courseState = courseState;
        this.courseView = courseView;
        this.currentArticle = currentArticle;
    }

    /**
     * Die Methode showNormalCourse setzt den View so, dass der normale Kurs angezeigt wird
     */
    public void showNormalCourse(){

    }
    /**
     * Die Methode showNormalCourse setzt den View so, dass der Kurs mit den Charts angezeigt wird
     */
    public void showCharts(){

    }

    /**
     * Die View wird so umgestellt, dass das angegebene Intervall angezeigt wird
     */
    public void changeShowInterval(intervals interval){
        this.interval = interval;
        //enum übergeben, damit es gesetzt wird
        System.out.println("timeButtonsTest");
        int pointAmount = currentArticle.getPointAmount();
        for (int i = 0; i < pointAmount; i++) {
            courseView.points.add(new Circle(20));
        }

    }

}
