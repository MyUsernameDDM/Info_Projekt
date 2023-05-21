package MainModel;

import MainModel.Article;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Klasse zum speichern der Daten einer Simulation, so sollen auch mehrere Simulationen gleichzeitg möglich sein.
 */
public class Simulation implements Serializable{
    private ArrayList<Article> walletListArticles = new ArrayList<>();
    private double moneyAv = 0;

    private double startMoney = 0;

    public ArrayList<Article> getWalletListArticles() {
        return walletListArticles;
    }


    public double getMoneyAv() {
        return moneyAv;
    }

    public double getStartMoney() {
        return startMoney;
    }

    public void setWalletListArticles(ArrayList<Article> walletListArticles) {
        this.walletListArticles = walletListArticles;
    }

    public void setMoneyAv(double moneyAv) {
        this.moneyAv = moneyAv;
    }

    public void setStartMoney(double startMoney) {
        this.startMoney = startMoney;
    }

    public void setMoneyAv(int moneyAv) {
        this.moneyAv = moneyAv;
    }

}


