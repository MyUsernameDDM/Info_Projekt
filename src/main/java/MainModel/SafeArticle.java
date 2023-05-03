package MainModel;

import java.io.*;

public class SafeArticle {
    File file;
    String name = "ArtSafe.ser";

    public SafeArticle() {
        file = new File(name);
    }

    public Article getArticleFromFile(String name, TimeSpan ts) {
        try (FileInputStream fileIn = new FileInputStream(name);
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

    public void addArticleFile(Article newArticle) {
        try (FileInputStream fileIn = new FileInputStream(name);
             ObjectInputStream in = new ObjectInputStream(fileIn);
             FileOutputStream fileOut = new FileOutputStream(name);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            Article[] articles = (Article[]) in.readObject();
            Article[] newArticles = new Article[Math.min(100, articles.length + 1)];
            System.arraycopy(articles, Math.max(0, articles.length - 99), newArticles, 0, newArticles.length - 1);
            newArticles[newArticles.length - 1] = newArticle;
            out.writeObject(newArticles);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
