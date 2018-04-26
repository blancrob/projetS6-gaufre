package gaufre;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public class display extends Application {
    
    public static void main(String[] args) {
        Application.launch(display.class,args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.show();
        primaryStage.setTitle("Affichage de la gaufre");
        Group root = new Group();
        Button btn = new Button();
        Scene scene = new Scene(root,500,500,Color.GREY);
        Circle c = new Circle();
        c.setCenterX(250);
        c.setCenterY(250);
        c.setRadius(100);
        c.setFill(Color.BEIGE);
        c.setStroke(Color.FIREBRICK);
        c.setStrokeWidth(4);
        
        c.setOnMouseClicked(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent me){
                if(c.getFill() == Color.BEIGE){
                    c.setFill(Color.LIGHTSALMON);
                }
                else{
                    c.setFill(Color.BEIGE);
                }
            }
        });
        
        scene.setOnMouseClicked(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent me){
                System.out.println("Coordonnées X : "+me.getX()+"    Coordonnées Y : "+ me.getY());
            }
        });
        
        scene.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override 
            public void handle(ScrollEvent se){
                c.setTranslateX(c.getTranslateX()-se.getDeltaX()); 
                c.setTranslateY(c.getTranslateY()-se.getDeltaY());
            }
        });

        
        root.getChildren().add(c);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
}
