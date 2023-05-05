package View;

import MainModel.Article;
import MainModel.Main;
import MainModel.SafeArticle;
import MainModel.TimeSpan;
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
        setGroundView();
        setWatchList();
        setSearchList();
        setCourseView("IBM");
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
    public void setCourseView(String str) {
        System.out.println(str);
        Article testArticle = new Article(str);
        int count=0;
        currentArticle = testArticle;
        courseUtils.setCurrentArticle(testArticle);
        while (!testArticle.setValues(TimeSpan.max)) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            count++;
        }
        //SafeArticle.clearFile();
        courseUtils.setCurrentArticle(testArticle);
        courseUtils.courseState = CourseUtils.courseStatus.normalCourse;
        //courseUtils.showNormalCourse();
        courseUtils.showCourse();
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

    private void setSearchList() {
        groundView.window.setLeft(searchView.root);
        groundView.root.getChildren().add(searchView.outputSearchView);
        Controller thisController= this;
        searchView.searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                searchView.showSearchResults(thisController);

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
                    SafeArticle.addArticleFile(currentArticle);
                }
            }
        });

        //Probe
        wlAddArticle("IBM");
        watchListArticles.add(new Article("IBM"));

    }

    /**
     * @param articleName Name des aktuell angezeigten Artikels
     */
    public void wlAddArticle(String articleName) {
        //todo Artikel selbst auch noch speichern in einer Liste, dass man schnell drauf zugreifen kann, aktuell sollte es in ser datei geschrieben werden

        //nicht hinzufuegen, falls der Artikel bereits enthalten ist
        for (Button b : watchListView.buttonList) {
            String name = b.getText();
            if (name.equals(articleName)) {
                return;
            }
        }
        Button temp = new Button(articleName);
        temp.setOnAction(actionEvent -> {
            Article tempArticle = new Article(articleName);

            //Daten aus Datei oder von API holen: TimeSpan dieselbe von Artikel, das davor angezeigt wurde
            while (!tempArticle.setValues(courseUtils.currentArticle.getTimeSpan())) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }

            }
            courseUtils.currentArticle = tempArticle;
            courseUtils.showCourse();
            wlSafeCurrentArticle(articleName);
        });
        watchListView.buttonList.add(temp);
        watchListView.vBox.getChildren().add(temp);
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

    public void menuButtonsListener(){
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

    public void timeButtonListener(){
        for(int i = 0; i < groundView.timeButtons.length; i++){
            int finalI = i;
            groundView.timeButtons[i].setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    groundView.timeButtonsHover(finalI, true);
                }
            });
        }

        for(int i = 0; i < groundView.timeButtons.length; i++){
            int finalI = i;
            groundView.timeButtons[i].setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    groundView.timeButtonsHover(finalI, false);
                }
            });
        }

        for(int i = 0; i < groundView.timeButtons.length; i++){
            int finalI = i;
            groundView.timeButtons[i].setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    groundView.timeButtonsOnMouseClicked(finalI);
                }
            });
        }
    }

    public void modeSceneChanger(){
        groundView.modeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                changeModeSimulation();
            }
        });{

        }
    }




}