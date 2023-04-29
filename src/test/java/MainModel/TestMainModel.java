package MainModel;

import View.CourseView;
import org.junit.jupiter.api.Test;



public class TestMainModel {
    @Test
    public void testSetValues() throws InterruptedException {
        Article art= new Article("IBM");
        for(TimeSpan t: TimeSpan.values()){
            System.out.println(t.name());
            while(!art.setValues(t)){
                Thread.sleep(5000);
            }
        }
    }
    @Test
    public void testMatching()throws InterruptedException {
        Matching matches= new Matching("tes");
        matches.start();
        matches.join();
    }
    @Test
    public void testCourseView() throws InterruptedException {
        Article art= new Article("IBM");
        while(!art.setValues(TimeSpan.oneMonth))
            Thread.sleep(5000);
        CourseView cv= new CourseView();
        cv.setCourse(art);
    }
}
