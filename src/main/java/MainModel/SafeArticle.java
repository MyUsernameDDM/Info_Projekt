package MainModel;

import java.io.*;

public class SafeArticle {
    public void clearFile(){
        try (FileOutputStream fileOut = new FileOutputStream("ArtSafe.ser", false)) {
            // Write an empty byte array to the file to clear its contents
            fileOut.write(new byte[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Article getArticleFromFile(String name, TimeSpan ts) {
        String nameFile = "ArtSafe.ser";
        File f = new File(nameFile);
        if (f.length() == 0)
            return null;
        try (FileInputStream fileIn = new FileInputStream(nameFile);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            Article[] articles = (Article[]) in.readObject();
            for (Article article : articles) {
                if (article.getName().equals(name) && article.getTimeSpan() == ts) {
                    return article;
                }
            }
        } catch (EOFException e) {
            return null;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addArticleFile(Article newArticle) {
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

}
