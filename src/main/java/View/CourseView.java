package View;

import MainModel.Article;
import MainModel.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;

import static MainModel.Main.mode;
import static View.CourseUtils.intervals.*;


public class CourseView {
    Group root = new Group();
    Rectangle backGround = new Rectangle(600,400);

    ArrayList<Circle> points = new ArrayList<>();
    ArrayList<Chart> charts = new ArrayList<>();

    Button changeStateButton = new Button();

    public CourseView(){
        //Background anpassen
        backGround.setFill(Color.ALICEBLUE);
        backGround.setWidth(500);
        backGround.setHeight(400);

        root.getChildren().add(backGround);
    }

}
