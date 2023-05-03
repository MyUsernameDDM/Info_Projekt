package View;

import MainModel.Article;
import MainModel.Main;
import MainModel.TimeSpan;
import Utils.SearchUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

import static MainModel.Main.mode;
import static MainModel.Main.status;
import static MainModel.TimeSpan.max;

public class Controller {
    GroundView groundView = new GroundView(this);
    SearchView searchView = new SearchView(this);
    WatchListView watchListView = new WatchListView(this);
    ArrayList<Article> watchListArticles = new ArrayList<>();
    Article watchLCurrentArticle = null;
    Article currentArticle;
    CourseView courseView = new CourseView();
    CourseUtils courseUtils = new CourseUtils(CourseUtils.courseStatus.normalCourse, courseView, currentArticle);
    SearchUtils searchUtils = new SearchUtils();

    public GroundView getGroundView() {
        return groundView;
    }

    public SearchView getSearchView() {
        return searchView;
    }

    public Scene getScene() {
        return groundView.scene;
    }

    public Controller() {
        setWatchList();
        setSearchList();
        setCourseView();
        setAddAndRemoveArticle();
        menuButtonsListener();

        //Handler fuer die Buttons zum setzen des Intervals
        for (int i = 0; i < groundView.timeButtons.length; i++) {
            int finalI = i;
            groundView.timeButtons[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    switch (groundView.timeButtons[finalI].getText()) {
                        case "1D":
                            courseUtils.changeShowInterval(TimeSpan.day);
                            break;
                        case "1M":
                            courseUtils.changeShowInterval(TimeSpan.oneMonth);
                            break;
                        case "3M":
                            courseUtils.changeShowInterval(TimeSpan.threeMonths);
                            break;
                        case "6M":
                            courseUtils.changeShowInterval(TimeSpan.sixMonths);
                            break;
                        case "1Y":
                            courseUtils.changeShowInterval(TimeSpan.year);
                            break;
                        case "5Y":
                            courseUtils.changeShowInterval(TimeSpan.fiveYear);
                            break;
                        case "MAX":
                            courseUtils.changeShowInterval(max);
                            break;
                    }
                }
            });
        }


    }

    /**
     * Methode fuegt die CourseView an den GroundView an
     */
    private void setCourseView() {
        Article testArticle = new Article("IBM");
        while (!testArticle.setValues(TimeSpan.year)) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

        }

        courseUtils.setCurrentArticle(testArticle);
        courseUtils.courseState = CourseUtils.courseStatus.chartCourse;
        //courseUtils.showNormalCourse();
        courseUtils.showChartCourse();
        groundView.window.setCenter(courseView.root);


        //Handler für den Button zum Veraendern der Ansicht
        groundView.changeStateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (courseUtils.courseView.root.getChildren().size() > 1) {
                    courseUtils.courseView.root.getChildren().subList(1, courseUtils.courseView.root.getChildren().size()).clear();
                }
                if (groundView.changeStateButton.getText().equals("Normal")) {
                    groundView.changeStateButton.setText("Charts");
                    courseUtils.courseState = CourseUtils.courseStatus.chartCourse;
                    courseUtils.showChartCourse();
                } else {
                    groundView.changeStateButton.setText("Normal");
                    courseUtils.courseState = CourseUtils.courseStatus.normalCourse;
                    courseUtils.showChartCourse();
                }
            }
        });
    }

    private void setSearchList() {
        groundView.window.setLeft(searchView.root);
        searchView.outputSearchView.setLayoutY(100);
        searchView.outputSearchView.setVisible(false);
        groundView.root.getChildren().add(searchView.outputSearchView);

        searchView.searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String[] help = SearchUtils.search(String.valueOf(searchView.searchBox.getValue()));
                searchView.showSearchResults(help);

                /*
                ArrayList<String> searchHelp = Article.matching (statische Methode);
                ArrayList<String> searchHelp = searchUtils.search(, shares);


                searchView.showSearchResults(searchHelp);
                 */
            }
        });
    }


    /**
     * setzt die ActionHandler
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
                    wlAddArticle(currentArticle.getName());

                }
            }
        });

        //Probe
        for (int i = 0; i < 20; i++) {
            wlAddArticle("Article " + i);
            watchListArticles.add(new Article("Article " + i));
        }
    }

    /**
     * @param articleName Name des aktuell angezeigten Artikels
     */
    public void wlAddArticle(String articleName) {
        //nicht hinzufuegen, falls der Artikel bereits enthalten ist
        for (Button b : watchListView.buttonList) {
            String name = b.getText();
            if (name.equals(articleName)) {
                return;
            }
        }
        Button temp = new Button(articleName);
        temp.setOnAction(actionEvent -> {
            /*Mothe aufrufen zur Anzeige des GRaphs*/
            wlSafeCurrentArticle(articleName);
        });
        watchListView.buttonList.add(temp);
        watchListView.vBox.getChildren().add(temp);

        /*

        ArrayList<String> searchHelp = searchUtils.search(String.valueOf(searchView.searchBox.getValue()), shares);
        searchView.showSearchResults(searchHelp);

         */
    }

    /**
     * @param articleName Name des neuen, aktuell ausgewählten Elements in der WatchList
     */
    public void wlSafeCurrentArticle(String articleName) {
        //vorher ausgewählten Button wieder zuruecksetzen
        if (watchLCurrentArticle != null) {
            for (int i = 0; i < watchListView.buttonList.size(); i++) {
                if (watchLCurrentArticle.getName().equals(watchListView.buttonList.get(i).getText())) {
                    watchListView.buttonList.get(i).setStyle("");
                }
            }
        }

        //neuen Artikel als aktuellen Artikel erstellen
        for (Article article : watchListArticles) {
            if (articleName.equals(article.getName())) {
                watchLCurrentArticle = article;
                for (int i = 0; i < watchListView.buttonList.size(); i++) {
                    if (articleName.equals(watchListView.buttonList.get(i).getText())) {
                        setButtonStyle(watchListView.buttonList.get(i));
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
     * Wechsel auf den SimulationController und somit auf den SimulationMode
     */
    public void changeMode() {
        mode = Main.status.simulation;
    }

    public void changeModeRealtime() {
        mode = status.realtime;
    }

    /**
     * Diese Methode kann häufiger aufgerufen werden um einen einheitlichen Stil zu haben
     *
     * @param button Button der bearbeitet wird
     */
    public void setButtonStyle(Button button) {
        button.setStyle("-fx-border-insets: 5");
        button.setStyle("-fx-border-color: #1970d2");
    }

    public void setAddAndRemoveArticle() {
        watchListView.addButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                watchListView.setAddButtonHover(true);
            }
        });

        watchListView.addButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                watchListView.setAddButtonHover(false);
            }
        });

        watchListView.removeButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                watchListView.setRemoveButtonHover(true);
            }
        });

        watchListView.removeButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                watchListView.setRemoveButtonHover(false);
            }
        });
    }

    public void menuButtonsListener(){
        groundView.simulationMode.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                groundView.simulationModeHover(true);
            }
        });

        groundView.simulationMode.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                groundView.simulationModeHover(false);
            }
        });

        groundView.wallet.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                groundView.walletHover(false);
            }
        });

        groundView.wallet.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                groundView.walletHover(true);
            }
        });



    }

}