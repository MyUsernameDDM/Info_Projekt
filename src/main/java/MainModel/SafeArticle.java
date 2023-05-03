package MainModel;

import java.io.*;

public class SafeArticle {


    public static Article getArticleFromFile(String name, TimeSpan ts) {
        String nameFile = "ArtSafe.ser";
        try (FileInputStream fileIn = new FileInputStream(nameFile);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            Article[] articles = (Article[]) in.readObject();
            for (Article article : articles) {
                if (article.getName().equals(name) && article.getTimeSpan() == ts) {
                    return article;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addArticleFile(Article newArticle) {
        if(newArticle==null||newArticle.getValues()==null|| newArticle.getValues().size()==0)
            throw new IllegalArgumentException();
        String name = "ArtSafe.ser";
        File f = new File(name);
        try {
            if (f.length() == 0) {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
                out.writeObject(new Article[]{newArticle});
                out.close();
                return;
            }
            FileInputStream fileIn = new FileInputStream(f);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Object obj = in.readObject();
            if (!(obj instanceof Article[])) {
                throw new ClassNotFoundException("File does not contain an array of Article objects.");
            }
            Article[] articles = (Article[]) obj;
            in.close();
            fileIn.close();
            for (Article article : articles) {
                if (article.getName().equals(newArticle.getName()) && article.getTimeSpan() == newArticle.getTimeSpan()) {
                    return;
                }
            }
            FileOutputStream fileOut = new FileOutputStream(f);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            Article[] newArticles = new Article[Math.min(100, articles.length + 1)];
            System.arraycopy(articles, Math.max(0, articles.length - 99), newArticles, 0, newArticles.length - 1);
            newArticles[newArticles.length - 1] = newArticle;
            out.writeObject(newArticles);
            out.close();
            fileOut.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
