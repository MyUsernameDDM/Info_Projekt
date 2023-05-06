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

import static MainModel.Main.mode;
import static MainModel.Main.status;
import static MainModel.TimeSpan.*;

public class Controller {
    GroundView groundView = new GroundView(this);
    SearchView searchView = new SearchView(this);
    WatchListView watchListView = new WatchListView(this);
    ArrayList<Article> watchListArticles = new ArrayList<>();
    Article watchLCurrentArticle = null;
    Article currentArticle;
    CourseView courseView = new CourseView();
    CourseUtils courseUtils = new CourseUtils(CourseUtils.courseStatus.normalCourse, courseView, this);
    TimeSpan currentTimeSpan = TimeSpan.max;
    SearchUtils searchUtils = new SearchUtils();
    SafeArticle safeArticle = new SafeArticle();

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
        setGroundView();
        setWatchList();
        setSearchList();
        setCourseView();
        setAddAndRemoveArticle();
        menuButtonsListener();
        timeButtonListener();
        modeSceneChanger();
        setWindowAdjustment();
    }

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
                    courseUtils.displayCourse(currentArticle.getName(), currentArticle.getSymbol());
                }
            });
            groundView.root.setOnMouseClicked(e -> {
                searchView.clearScrollPane();
            });
        }
        groundView.window.setPrefWidth(groundView.scene.getWidth());
        groundView.window.setPrefHeight(groundView.scene.getHeight());

    }


    protected void setWindowAdjustment() {
        ChangeListener changeListener = new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                double newSceneWidth = groundView.scene.getWidth();
                double newSceneHeight = groundView.scene.getHeight();

                //Hintergrund
                groundView.window.setPrefWidth(newSceneWidth);
                groundView.window.setPrefHeight(newSceneHeight);

                double widthRatio = newSceneWidth / groundView.oldSceneWidth;
                double heightRatio = newSceneHeight / groundView.oldSceneHeight;

                courseUtils.adjustCourseSize(groundView.scene.getWidth() - watchListView.wlRoot.getPrefWidth(), groundView.scene.getHeight() - groundView.timeBox.getPrefHeight() - groundView.menu.getPrefHeight());
                groundView.oldSceneWidth = newSceneWidth;
                groundView.oldSceneHeight = newSceneHeight;

            }
        };

        groundView.scene.heightProperty().addListener(changeListener);
        groundView.scene.widthProperty().addListener(changeListener);
    }

    /**
     * Methode fuegt die CourseView an den GroundView an
     */
    public void setCourseView() {
        safeArticle.setSafedArticles();

        courseUtils.displayCourse("IBM", "IBM");
        groundView.timeButtons[6].getStyleClass().add("buttonTimeClicked");

        groundView.window.setCenter(courseView.root);

        courseUtils.adjustCourseSize(groundView.scene.getWidth() - watchListView.wlRoot.getPrefWidth(), groundView.scene.getHeight() - groundView.timeBox.getPrefHeight() - groundView.menu.getPrefHeight());


        //Handler für den Button zum Veraendern der Ansicht
        groundView.changeStateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                if (groundView.changeStateButton.getText().equals("Normal")) {
                    groundView.changeStateButton.setText("Charts");
                    courseUtils.courseState = CourseUtils.courseStatus.chartCourse;
                    courseUtils.showCourse();
                } else {
                    groundView.changeStateButton.setText("Normal");
                    courseUtils.courseState = CourseUtils.courseStatus.normalCourse;
                    courseUtils.showCourse();
                }
            }
        });
    }

    public void setCurrentArticle(Article currentArticle) {
        this.currentArticle = currentArticle;
    }

    private void setSearchList() {
        groundView.window.setLeft(searchView.root);
        groundView.root.getChildren().add(searchView.outputSearchView);

        //SearchView: Eingabefeld und Search-Button in das Menu einfuegen
        groundView.menu.getChildren().addAll(searchView.root);
        Controller thisController = this;
        searchView.searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showSearchResults();

                /*
                String[] help = searchUtils.search(String.valueOf(searchView.searchBox.getValue()));
                searchView.showSearchResults(help);


                 */
                /*
                ArrayList<String> searchHelp = Article.matching (statische Methode);
                ArrayList<String> searchHelp = searchUtils.search(, shares);


                searchView.showSearchResults(searchHelp);
                 */
            }
        });
    }

    /**
     * Methode, die die Liste aus Artikelvorschlaegen anzeigt
     */
    public void showSearchResults() {
        if(searchView.searchInputTextField.getText().equals("")){
            return;
        }
        searchView.outputSearchView.setVisible(true);
        searchView.outputSearchView.setMaxHeight(100);
        searchView.outputSearchView.setLayoutX(searchView.root.getLayoutX());
        searchView.outputSearchView.setLayoutY(searchView.searchInputTextField.getLayoutY() + searchView.searchInputTextField.getHeight());
        MatchUnits[] result = searchUtils.getMatchings(searchView.searchInputTextField.getText());
        if (result == null) {
            return;
        }
        int count = 0;
        if (searchView.recommendsBox.getChildren().size() > 0)
            searchView.recommendsBox.getChildren().clear();
        for (int i = 9; i >= 0; --i) {
            if (result[i] == null){
                continue;
            }

            searchView.recommends[count].setText(result[i].getName());
            int finalI = i;
            searchView.recommends[count].setOnMouseClicked(e -> {
                SearchUtils.buttonClicked(result[finalI], courseUtils, searchView);
            });
            searchView.recommendsBox.getChildren().add(searchView.recommends[count]);
            count++;
        }
        searchView.outputSearchView.setContent(searchView.recommendsBox);
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
                    wlAddArticle();
                }
            }
        });

    }

    /**
     *
     */
    public void wlAddArticle() {
        //todo Artikel selbst auch noch speichern in einer Liste, dass man schnell drauf zugreifen kann, aktuell sollte es in ser datei geschrieben werden

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
        watchListView.buttonList.add(temp);
        watchListView.vBox.getChildren().add(temp);

        temp.setOnAction(actionEvent -> {
            //Daten aus Datei oder von API holen: TimeSpan dieselbe von Artikel, das davor angezeigt wurde
            while (!currentArticle.setValues(currentArticle.getTimeSpan())) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
            courseUtils.showCourse();
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
    public void changeModeSimulation() {
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

    public void menuButtonsListener() {
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

    public void timeButtonListener() {
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

    public void modeSceneChanger() {
        groundView.modeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                changeModeSimulation();
            }
        });
        {

        }
    }


}