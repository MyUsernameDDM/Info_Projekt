package View;

import MainModel.Article;

import java.util.ArrayList;

/**
 * Klasse zum speichern der Daten einer Simulation, so sollen auch mehrere Simulationen gleichzeitg möglich sein.
 */
public class Simulation {
    ArrayList<Article> walletArticles = new ArrayList<>();
    static int moneyInv = 0;
    static int moneyAv = 1000;
    static int openShares = 0;
}

