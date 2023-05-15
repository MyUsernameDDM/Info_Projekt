package View;

import MainModel.Article;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Klasse zum speichern der Daten einer Simulation, so sollen auch mehrere Simulationen gleichzeitg möglich sein.
 */
public class Simulation {
    ArrayList<Article> walletListArticles = new ArrayList<>();
    double fee = 0.05; // 5 prozent an spesen
    int moneyInv = 0;
    int moneyAv = 1000;
    int openShares = 0;
}


