package View;

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
    //Damian: i woas net genau ob du des in chart eini tian willsch, i fins so fost gschickter, weil wianiger speicher und so
    //verbraucht wert und man die arraylist von Chart net braucht
    public void setCandleStick(Unit u, double posx, double posy, double width){
        double height= u.getOpen()-u.getClose();
        Rectangle body= new Rectangle(posx-width/2,posy-Math.abs(height)/2);
        body.setHeight(height);
        body.setWidth(width);
        if(height>0) {
            body.setFill(Color.GREEN);
        }
        else {
            body.setFill(Color.RED);
        }
        Line topLine= new Line(posx,posy-Math.abs(height)/2,posx,posy-u.getHigh());
        Line bottomLine= new Line(posx,posy+Math.abs(height)/2,posx,posy+u.getLow());
        root.getChildren().addAll(body,topLine,bottomLine);
    }

}
