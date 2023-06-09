package View;

import MainModel.*;
import Utils.SearchUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

import static MainModel.Main.*;
import static MainModel.TimeSpan.*;

/**
 * Controller Klasse: Wird als zentraler Kontroller für mehrere Views benutzt.
 * GroundView, SeachView, WatchlistView als auch WalletView werden durch diesen Kontroller gehandelt.
 * Alle Attribute sind grundsätzlich package-private. So kann man bewirken, dass alle Klassen in dem Package
 * View Zugriff auf die jeweiligen Attribute haben und es werden nicht für jedes Attribut Getter und Setter benötigt.
 */
public class Controller {
    //verschiedene verwendete Attribute für die View
    GroundView groundView = new GroundView(this);       //View, in die andere View-Bausteine durch den COntroller eingefuegt werden
    SearchView searchView = new SearchView(this);
    WatchListView watchListView = new WatchListView(this);
    ArrayList<Article> watchListArticles = new ArrayList<>();       //Liste der Artikel in der Watchliste
    Article watchLCurrentArticle = null;

    CourseView courseView = new CourseView();
    CourseController courseController = new CourseController(CourseController.courseStatus.normalCourse, courseView, this);
    TimeSpan currentTimeSpan = TimeSpan.max;    //currentTimeSpan speichert die Zeitspanne, die aktuell vom Artikel angezeigt wird
    SearchUtils searchUtils = new SearchUtils();        //Hilfsklasse, um Artikel zu suchen
    SafeArticle safeArticle = new SafeArticle();
    InfoView infoView = new InfoView();     //Attribut infoview, um die Methoden davon verwenden zu koennen
    Article currentArticle;         //Artikel, der aktuell angezeigt wird

    /**
     * Methode getScene
     * @return die Scene zum jeweiligen Modus, der Returnwert ist entweder die Scene für den RealTime oder den Simulation-MOdus
     *
     */
    public Scene getScene() {
        return groundView.scene;
    }

    /**
     * Konstruktor: Enthält Methodenaufrufe, die die einzelnen Views zusammenstellen und die die Groundview geben
     */
    public Controller() {
        setGroundView();
        setWatchList();
        setSearchView();
        setCourseView();
        timeButtonListener();
        modeSceneChanger();     //setzt den Handler für den button zum Umschalten in den anderen Modus
        setWindowAdjustment();
    }

    /**
     * Methode, die die Handler für Elemente in der Groundview setzen
     */
    private void setGroundView() {
        //Handler fuer die Buttons zum setzen des Intervals
        for (int i = 0; i < groundView.timeButtons.length; i++) {
            int finalI = i;
            groundView.timeButtons[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    switch (groundView.timeButtons[finalI].getText()) {
                        case "1D":
                            currentTimeSpan = TimeSpan.day;
                            break;
                        case "1M":
                            currentTimeSpan = oneMonth;

                            break;
                        case "3M":
                            currentTimeSpan = threeMonths;
                            break;
                        case "6M":
                            currentTimeSpan = sixMonths;
                            break;
                        case "1Y":
                            currentTimeSpan = year;
                            break;
                        case "5Y":
                            currentTimeSpan = fiveYear;
                            break;
                        case "MAX":
                            currentTimeSpan = max;
                            break;
                    }
                    courseController.displayCourse(currentArticle.getName(), currentArticle.getSymbol(), currentArticle.getCurrency());
                }
            });

            //Handler, sodass die Suchergebnisse nicht mehr angezeigt werden
            groundView.root.setOnMouseClicked(e -> {
                searchView.clearScrollPane();
            });
        }
        groundView.window.setPrefWidth(groundView.scene.getWidth());
        groundView.window.setPrefHeight(groundView.scene.getHeight());

        //HoverStyle fuer den ModeButton ändern
        groundView.modeButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                groundView.simulationModeHover(true);
            }
        });

        groundView.modeButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                groundView.simulationModeHover(false);
            }
        });

    }


    /**
     * setzt den Listener, sodass auf die Fenstergrößenänderung reagiert werden kann
     */
    protected void setWindowAdjustment() {
        ChangeListener changeListener = new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                Main.windowWidth = Main.primaryStage.getWidth();
                Main.windowHeight = Main.primaryStage.getHeight() ;

                adjustWindowSize(windowWidth, windowHeight);
            }
        };
        groundView.scene.widthProperty().addListener(changeListener);
        groundView.scene.heightProperty().addListener(changeListener);
    }

    /**
     * Methode passt die Elemente an die Fenstergröße an
     */
    public void adjustWindowSize(double newSceneWidth, double newSceneHeight) {
        //Hintergrund
        groundView.window.setMaxWidth(newSceneWidth);
        groundView.window.setMaxHeight(newSceneHeight);

        //Kursgroesse anpassen
        courseController.adjustCourseSize(newSceneWidth - watchListView.wlRoot.getPrefWidth(), newSceneHeight - groundView.timeBox.getPrefHeight() - groundView.menu.getPrefHeight() - 35);
    }

    /**
     * Methode fuegt die CourseView in die GroundView ein
     */
    private void setCourseView() {
        safeArticle.setSafedArticles();

        //IBM als Standardmaessig gesuchten Artikel einstellen. Macht das Programm intuitiver.
        courseController.displayCourse("IBM", "IBM", "USD");
        groundView.timeButtons[6].getStyleClass().add("buttonTimeClicked");

        //die Groesse direkt anpassen
        courseController.adjustCourseSize(groundView.scene.getWidth() - watchListView.wlRoot.getPrefWidth(), groundView.scene.getHeight() - groundView.timeBox.getPrefHeight() - groundView.menu.getPrefHeight());
        //SearchView in Groundview einfügen
        groundView.window.setCenter(courseView.root);

        //Handler für den Button zum Veraendern der Ansicht zwischen normaler und Chartansicht
        groundView.changeStateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (groundView.changeStateButton.getText().equals("Normal")) {
                    groundView.changeStateButton.setText("Charts");
                    courseController.courseState = CourseController.courseStatus.chartCourse;
                    courseController.showCourse();
                } else {
                    groundView.changeStateButton.setText("Normal");
                    courseController.courseState = CourseController.courseStatus.normalCourse;
                    courseController.showCourse();
                }
            }
        });
    }

    /**
     * Die SearchView in die GroundView einfügen und den Handler für den SearchButton setzen
     */
    private void setSearchView() {
        groundView.window.setLeft(searchView.root);
        groundView.root.getChildren().add(searchView.outputSearchView);

        //SearchView: Eingabefeld und Search-Button in das Menu einfuegen
        groundView.menu.getChildren().addAll(searchView.root);
        searchView.searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showSearchResults();
            }
        });
    }

    /**
     * Methode, die die Liste aus Artikelvorschlaegen anzeigt
     */
    private void showSearchResults() {
        if (searchView.searchInputTextField.getText().equals("")) {
            return;
        }

        searchView.outputSearchView.setMaxHeight(100);
        searchView.outputSearchView.setMaxWidth(400);
        searchView.outputSearchView.setLayoutX(searchView.root.getLayoutX());
        searchView.outputSearchView.setLayoutY(searchView.searchInputTextField.getLayoutY() + searchView.searchInputTextField.getHeight());
        MatchUnits[] result = searchUtils.getMatchings(searchView.searchInputTextField.getText());
        if (result == null) {
            return;
        }
        int count = 0;

        //alte Anzeige loeschen
        if (searchView.recommendsBox.getChildren().size() > 0) {
            searchView.recommendsBox.getChildren().clear();
        }

        //bis zu 10 Artikelvorschläge anzeigen
        for (int i = 0; i < 10; ++i) {
            if (result[i] == null) {
                continue;
            }

            searchView.recommends[count].setText(result[i].getName());
            int finalI = i;
            searchView.recommends[count].setOnMouseClicked(e -> {
                SearchUtils.buttonClicked(result[finalI], courseController, searchView);
            });
            searchView.recommendsBox.getChildren().add(searchView.recommends[count]);
            count++;
        }
        searchView.outputSearchView.setContent(searchView.recommendsBox);
        searchView.outputSearchView.setFitToWidth(true);
        searchView.outputSearchView.prefWidthProperty().bind(searchView.getWidthButton().prefWidthProperty());
        //auf Sichtbar setzen
        searchView.outputSearchView.setVisible(true);
    }


    /**
     * Methode gibt die Watchlist in die BorderPane der Groundview und setzt die ActionHandler fuer die Elemente der Watchlist
     */
    private void setWatchList() {
        groundView.window.setRight(watchListView.wlRoot);

        watchListView.removeButton.setOnAction(actionEvent -> {
            wlRemoveCurrentArticle();
        });

        //Handler fuer den addButton in der Watchlist
        watchListView.addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (currentArticle != null) {
                    wlAddArticle();
                }
            }
        });

        //Handler für das darueberhovern
        watchListView.addButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                watchListView.setButtonHover(true, watchListView.addButton);
            }
        });

        watchListView.addButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                watchListView.setButtonHover(false, watchListView.addButton);
            }
        });

        watchListView.removeButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                watchListView.setButtonHover(true, watchListView.removeButton);
            }
        });

        watchListView.removeButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                watchListView.setButtonHover(false, watchListView.removeButton);
            }
        });

    }

    /**
     *
     */
    public void wlAddArticle() {
        //todo Artikel selbst auch noch speichern in einer Liste, dass man schnell drauf zugreifen kann, aktuell sollte es in ser datei geschrieben werden; phillip beim bearbeiten

        //nicht hinzufuegen, falls der Artikel bereits enthalten ist
        for (Button b : watchListView.buttonList) {
            String name = b.getText();
            if (name.equals(currentArticle.getName())) {
                return;
            }
        }

        //Article tempArticle = new Article(article.getName(), article.getSymbol(), safeArticle);
        watchListArticles.add(currentArticle);

        Button temp = new Button(currentArticle.getName());
        temp.getStyleClass().add("buttonInList");
        watchListView.buttonList.add(temp);
        watchListView.vBox.getChildren().add(temp);

        temp.setOnAction(actionEvent -> {
            if (!(temp.getText().equals(currentArticle.getName()))) {
                //Daten aus Datei oder von API holen: TimeSpan dieselbe von Artikel, das davor angezeigt wurde
                for (Article s : safeArticle.getSafedArticles()) {
                    if (s.getTimeSpan() == currentTimeSpan && s.getName().equals(temp.getText())) {
                        currentArticle = s;
                        break;
                    }
                }
                courseController.showCourse();
            }
            wlSafeCurrentArticle(currentArticle.getName());
        });
    }

    /**
     * @param articleName Name des neuen, aktuell ausgewählten Elements in der WatchList
     */
    public void wlSafeCurrentArticle(String articleName) {
        //vorher ausgewählten Button wieder zuruecksetzen
        if (watchLCurrentArticle != null) {
            for (int i = 0; i < watchListView.buttonList.size(); i++) {
                if (watchLCurrentArticle.getName().equals(watchListView.buttonList.get(i).getText())) {
                    watchListView.buttonList.get(i).getStyleClass().remove("buttonInListClicked");
                    watchListView.buttonList.get(i).getStyleClass().add("buttonInList");
                }
            }
        }

        //neuen Artikel als aktuellen Artikel erstellen
        for (Article article : watchListArticles) {
            if (articleName.equals(article.getName())) {
                watchLCurrentArticle = article;
                for (int i = 0; i < watchListView.buttonList.size(); i++) {
                    if (articleName.equals(watchListView.buttonList.get(i).getText())) {
                        watchListView.buttonList.get(i).getStyleClass().remove("buttonInList");
                        watchListView.buttonList.get(i).getStyleClass().add("buttonInListClicked");
                    }
                }
            }
        }
    }

    /**
     * Die Methode löscht das aktuell ausgewählte Element der Watchlist aus dieser
     */
    public void wlRemoveCurrentArticle() {
        if (watchLCurrentArticle != null) {
            for (Button b : watchListView.buttonList) {
                if (b.getText().equals(watchLCurrentArticle.getName())) {
                    watchListView.vBox.getChildren().remove(b);
                    watchListView.buttonList.remove(b);
                    return;
                }
            }
            watchListArticles.remove(watchLCurrentArticle);
        }
    }

    /**
     * MouseHoverListener für die TimeButtons setzen
     */
    private void timeButtonListener() {
        for (int i = 0; i < groundView.timeButtons.length; i++) {
            int finalI = i;
            groundView.timeButtons[i].setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    groundView.timeButtonsHover(finalI, true);
                }
            });
        }

        for (int i = 0; i < groundView.timeButtons.length; i++) {
            int finalI = i;
            groundView.timeButtons[i].setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    groundView.timeButtonsHover(finalI, false);
                }
            });
        }

        for (int i = 0; i < groundView.timeButtons.length; i++) {
            int finalI = i;
            groundView.timeButtons[i].setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    groundView.timeButtonsOnMouseClicked(finalI);
                }
            });
        }
    }

    /**
     * Setzt den Handler zum Umschalten auf dem SimulationMode
     */
    public void modeSceneChanger() {
        groundView.modeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mode = Main.status.simulation;
                Main.changeBetweenModes();
                //nur beim ersten mal in den Simulationsmodus wechseln eine SImulation starten
                if (Main.simulationController.getSimulation() == null) {
                    Main.simulationController.simulationUtils.newSimulation();
                }
            }
        });
    }


}