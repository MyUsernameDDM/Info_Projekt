package View;

import MainModel.Article;

public class CourseUtils {
    public enum intervals {oneD, fiveD, oneM, threeM, sixM, oneY, fiveY, always}
    public enum courseStatus {normalCourse, hammerCourse}

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
     * Die Methode showNormalCourse setzt den View so, dass der Kurs mit den Hämmern angezeigt wird
     */
    public void showHammers(){

    }

    /**
     * Die View wird so umgestellt, dass das angegebene Interval angezeigt wird
     */
    public void changeShowInterval(){
        //enum übergeben, damit es gesetzt wird

    }

}
