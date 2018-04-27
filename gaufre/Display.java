package gaufre;

import static java.lang.Math.toIntExact;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public class Display extends Application {
    
    public static int M = 5;
    public static int N = 8;
    
    private int lx = 100;   //taille hauteur case
    private int ly = 100;   //taille largeur case
    
    private int m_scene = lx*M+1;
    private int n_scene = ly*N+1;
    
    public static void main(String[] args) {
        Application.launch(Display.class,args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        int i = 0;
        int j = 0;
        
        primaryStage.show();
        primaryStage.setTitle("Affichage de la gaufre");
        StackPane pane = new StackPane();

        Scene scene = new Scene(pane,n_scene,m_scene,Color.GREY);
        
        pane.setStyle("-fx-border-color: black;");
        
        /*
        Case c = new Case(lx,ly);
        c.setTranslateX(-200);
        c.setTranslateY(-200);
        pane.getChildren().add(c);
        */
        
        Case[][] tab = new Case[N][M];
        
        for(i=0;i<N;i++){
            for(j=0;j<M;j++){
                tab[i][j] = new Case(lx,ly);
                tab[i][j].setTranslateX(-((n_scene/2)-(lx/2))+lx*i);
                tab[i][j].setTranslateY(-((m_scene/2)-(ly/2))+ly*j);
                
                tab[i][j].c.setFill(Color.MOCCASIN);
                
                pane.getChildren().add(tab[i][j]);
            }
        }
        
        Circle poison = new Circle();

        poison.setCenterX(50);
        poison.setCenterY(50);
        poison.setRadius(25);
        poison.setTranslateX(-(n_scene/2)+(lx/2)-1);
        poison.setTranslateY(-(m_scene/2)+(ly/2)-1);
        poison.setFill(Color.FIREBRICK);
        pane.getChildren().add(poison);
        
        pane.setStyle("-fx-border-color: black;");

        scene.setOnMouseClicked(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent me){
                //System.out.println("Coordonnée X brute : "+me.getX()+"    Coordonnée Y brute : "+ me.getY());
                int i;
                int j;
                int x = toIntExact(Math.round(Math.floor(me.getY()/ly)));
                int y = toIntExact(Math.round(Math.floor(me.getX()/lx)));
                System.out.println("Coordonnée i : "+x+"    Coordonnée j : "+y);
                
                if(tab[y][x].c.getFill() == Color.MOCCASIN){    //rajouter condition case jouable       //utiliser plateau de gaufre
                    tab[y][x].c.setFill(Color.WHITE);

                    for(i=0;i<N;i++){       //rajouter modif booleen case non jouable
                        for(j=0;j<M;j++){
                            if(j >= x && i >= y){
                                tab[i][j].c.setFill(Color.WHITE);
                            }
                        }
                    }
                }
            }
        });
        
        /*
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
                System.out.println("Coordonnée X brute : "+me.getX()+"    Coordonnée Y brute : "+ me.getY());
            }
        });
        
        scene.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override 
            public void handle(ScrollEvent se){
                c.setTranslateX(c.getTranslateX()-se.getDeltaX()); 
                c.setTranslateY(c.getTranslateY()-se.getDeltaY());
            }
        });
   
        pane.getChildren().add(c);
        */
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
}