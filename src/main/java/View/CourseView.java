package View;

import MainModel.Article;
import MainModel.Unit;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;


public class CourseView {
    Group root = new Group();

    Rectangle backGround;
    Label articleNameLabel = new Label();

    ArrayList<Ellipse> points = new ArrayList<>();


    public CourseView() {
        //Background anpassen
        backGround = new Rectangle(500, 400);
        backGround.setFill(Color.ALICEBLUE);
        articleNameLabel.setId("courseArticleName");
        articleNameLabel.setLayoutX(10);
        articleNameLabel.setLayoutY(10);

        root.getChildren().addAll(backGround, articleNameLabel);
    }


}
