package View;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class CourseView {
    Group root = new Group();
    Rectangle backGround = new Rectangle(600,400);

    ArrayList<Circle> points = new ArrayList<>();
    ArrayList<Hammer> hammers = new ArrayList<>();
}
