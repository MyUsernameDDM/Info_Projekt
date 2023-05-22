package MainModel;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Klasse um Artikel in einer Datei und ArrayList abzuspeichern
 */
public class SafeArticle {
    private ArrayList<Article> safedArticles;

    public SafeArticle() {
        safedArticles = new ArrayList<>();
        String nameFile = "ArtSafe.ser";
        File f = new File(nameFile);
    }
    /**
     * In safedArticles ArrayList werden alle Articles von der Datei geschrieben
     */
    public void setSafedArticles() {
        String nameFile = "ArtSafe.ser";
        File f = new File(nameFile);
        if (f.length() == 0)
            safedArticles = null;
        try (FileInputStream fileIn = new FileInputStream(nameFile);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            safedArticles.addAll(Arrays.asList((Article[]) in.readObject()));
        } catch (EOFException e) {
            safedArticles = null;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Methode, um newArticle in der Datei ganz am ende zu speichern.
     * Wenn mehr als 100 Article in der Datei sind wird der älteste Article entfernt.
     * @param newArticle Article, der an den Anfang der Serialization Datei gegeben wird.
     */
    public static void addArticleFile(Article newArticle) {
        if (newArticle == null || newArticle.getValues() == null || newArticle.getValues().size() == 0)
            throw new IllegalArgumentException();
        String name = "ArtSafe.ser";
        File f = new File(name);
        try {
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

        } catch (EOFException ef) {
            try {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
                out.writeObject(new Article[]{newArticle});
                out.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Methode um Article in der safedARticles Arraylist abzuspeichern.
     * @param art Article der Abgespeichert wird.
     */
    public void addArticleToSafe(Article art) {
        if (safedArticles == null)
            safedArticles = new ArrayList<>();
        if (art.getPointAmount() != 0)
            safedArticles.add(art);
    }

    public ArrayList<Article> getSafedArticles() {
        return safedArticles;
    }
}
