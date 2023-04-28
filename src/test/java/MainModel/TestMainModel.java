package MainModel;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;


public class TestMainModel {
    @Test
    public void testSetValues() throws InterruptedException {
        Article art= new Article("IBM");
        for(TimeSpan t: TimeSpan.values()){
            System.out.println(t.name());
            while(!art.setValues(t)){
                Thread.sleep(5000);
            }
            for(Unit u: art.getValues()){
                System.out.println(u.getDate());
            }
        }
    }
    @Test
    public void testMatching() throws IOException, InterruptedException {
        Matching matches= new Matching("Tes");
        matches.start();
        matches.join();


    }
}
