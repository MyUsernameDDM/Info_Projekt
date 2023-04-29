package View;

import MainModel.Article;
import MainModel.Unit;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;


public class CourseView {
    Group root = new Group();
    Rectangle backGround = new Rectangle(600, 400);

    ArrayList<Circle> points = new ArrayList<>();
    ArrayList<Chart> charts = new ArrayList<>();

    //braucht es nicht mehr, ist draußen bei den Zeitänderungen
    Button changeStateButton = new Button();

    public CourseView() {
        //Background anpassen
        backGround.setFill(Color.ALICEBLUE);
        backGround.setWidth(500);
        backGround.setHeight(400);

        root.getChildren().add(backGround);
    }


}
