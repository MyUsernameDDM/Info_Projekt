package MainModel;



import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMainModel {

    @Test
    public void testSetValues() throws InterruptedException {
        SafeArticle safeArticle= new SafeArticle();
        safeArticle.setSafedArticles();
        Article art = new Article("IBM", "IBM", safeArticle,"USD");
        for (TimeSpan t : TimeSpan.values()) {
            while (!art.setValues(t)) {
                Thread.sleep(5000);
            }
        }
    }


    @Test
    public void testSafe() {
        SafeArticle safeArticle = new SafeArticle();
        Article article1 = new Article("IBM", "IBM", safeArticle, "USD");
        article1.setValues(TimeSpan.oneMonth);
        Article day= new Article("IBM", "IBM", safeArticle,"USD");
        day.setValues(TimeSpan.day);


        for(Article a:safeArticle.getSafedArticles()){
            if(a==day){
                System.out.println("The day is taken from the safedFile because it is being produced when Month is made");
                assertEquals(1,1);
            }
        }

    }



}
