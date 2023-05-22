package MainModel;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Klasse zum speichern der Daten einer Simulation. Die Klasse kann abgespeichert werden und wieder geladen werden.
 */
public class Simulation implements Serializable{
    private ArrayList<Article> walletListArticles = new ArrayList<>();      //Liste der Artikel, die in einer Simulation gekauft wurden
    private double moneyAv = 0;
    private double startMoney = 0;

    //verschiedene Getter und Setter
    public ArrayList<Article> getWalletListArticles() {
        return walletListArticles;
    }


    public double getMoneyAv() {
        return moneyAv;
    }

    public double getStartMoney() {
        return startMoney;
    }

    public void setMoneyAv(double moneyAv) {
        this.moneyAv = moneyAv;
    }

    public void setStartMoney(double startMoney) {
        this.startMoney = startMoney;
    }


}


