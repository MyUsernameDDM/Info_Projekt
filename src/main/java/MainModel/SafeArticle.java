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
        String name = "ArtSafe.ser";
        File f = new File(name);
        try {
            f.createNewFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            FileInputStream fileIn = new FileInputStream(name);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Article[] articles;
            try {
                articles = (Article[]) in.readObject();
            } catch (EOFException e) {
                // File is empty, create a new array with just the new article
                articles = new Article[]{newArticle};
            }
            in.close();
            fileIn.close();

            FileOutputStream fileOut = new FileOutputStream(name);
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
