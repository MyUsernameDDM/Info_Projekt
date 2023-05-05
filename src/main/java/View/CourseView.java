package View;

import MainModel.Article;
import MainModel.Unit;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;


public class CourseView {
    Group root = new Group();

    Rectangle backGround;

    ArrayList<Ellipse> points = new ArrayList<>();
    ArrayList<Chart> charts = new ArrayList<>();


    public CourseView() {
        //Background anpassen
        backGround = new Rectangle(500, 400);
        backGround.setFill(Color.ALICEBLUE);


        root.getChildren().add(backGround);
    }


}
