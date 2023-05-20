package View;

import MainModel.Article;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Klasse zum speichern der Daten einer Simulation, so sollen auch mehrere Simulationen gleichzeitg möglich sein.
 */
public class Simulation implements Serializable{
    ArrayList<Article> walletListArticles = new ArrayList<>();
    double fee = 0.05; // 5 prozent an spesen
    int moneyInv = 0;
    int moneyAv = 0;
    int openShares = 0;
    double startMoney = 0;

    public ArrayList<Article> getWalletListArticles() {
        return walletListArticles;
    }

    public double getFee() {
        return fee;
    }

    public int getMoneyInv() {
        return moneyInv;
    }

    public int getMoneyAv() {
        return moneyAv;
    }

    public int getOpenShares() {
        return openShares;
    }

    public void setWalletListArticles(ArrayList<Article> walletListArticles) {
        this.walletListArticles = walletListArticles;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public void setMoneyInv(int moneyInv) {
        this.moneyInv = moneyInv;
    }

    public void setMoneyAv(int moneyAv) {
        this.moneyAv = moneyAv;
    }

    public void setOpenShares(int openShares) {
        this.openShares = openShares;
    }
}


