package gaufre;

import javafx.animation.AnimationTimer;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.Math.toIntExact;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 *
 * @author haudryf
 */

public class Gaufre extends Application {
    
    public static int M = 6;
    public static int N = 8;
    
    public static int begin = 0;
    
    public Moteur m;
    
    public Case[][] tab;
    
    public AnchorPane pane = new AnchorPane();
    
    private int lx = 100;   //taille hauteur case
    private int ly = 100;   //taille largeur case
    
    private int m_scene = lx*M;
    private int n_scene = ly*N;
    
    public int ai = 0;
    public long temps = 0;
    
    public Scene scene = new Scene(pane,n_scene,m_scene,Color.GREY); 
    
    public static void main(String[] args) {
        Application.launch(Gaufre.class,args);
    }
    
    public void actualiser(Case[][] tab, Moteur m){

        for(int i=0;i<N;i++){       //rajouter modif booleen case non jouable
            for(int j=0;j<M;j++){
                if(m.plateau[j][i]==false){
                    tab[i][j].c.setFill(Color.WHITE);
                }
            }
        }
    }
    
    public void affichage_gaufre(Stage primaryStage, Button btn6, Button btn7, Button btn8, Button btn9){
        primaryStage.setTitle("Affichage de la gaufre");
        pane = new AnchorPane();
        scene = new Scene(pane,n_scene,m_scene+100,Color.GREY);       
        pane.setStyle("-fx-border-color: black;");
        
        pane.getChildren().add(btn6);
        pane.getChildren().add(btn7);
        pane.getChildren().add(btn8);
        pane.getChildren().add(btn9);
        
        AnchorPane.setTopAnchor(btn6,600.0);
        AnchorPane.setLeftAnchor(btn6,0.0);
        AnchorPane.setRightAnchor(btn6,600.0);
        AnchorPane.setBottomAnchor(btn6,0.0);
            
        AnchorPane.setTopAnchor(btn7,600.0);
        AnchorPane.setLeftAnchor(btn7,200.0);
        AnchorPane.setRightAnchor(btn7,400.0);
        AnchorPane.setBottomAnchor(btn7,0.0);
        
        AnchorPane.setTopAnchor(btn8,600.0);
        AnchorPane.setLeftAnchor(btn8,400.0);
        AnchorPane.setRightAnchor(btn8,200.0);
        AnchorPane.setBottomAnchor(btn8,0.0);
        
        AnchorPane.setTopAnchor(btn9,600.0);
        AnchorPane.setLeftAnchor(btn9,600.0);
        AnchorPane.setRightAnchor(btn9,0.0);
        AnchorPane.setBottomAnchor(btn9,0.0);
              
        tab = new Case[N][M];
        init(tab,pane);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        scene.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override 
            public void handle(MouseEvent me){
                //System.out.println("Coordonnée X brute : "+me.getX()+"    Coordonnée Y brute : "+ me.getY());
                int x = toIntExact(Math.round(Math.floor(me.getY()/ly)));
                int y = toIntExact(Math.round(Math.floor(me.getX()/lx)));

                if(x<M){
                    begin=1;
                
                    System.out.println("Coordonnée i : "+x+"    Coordonnée j : "+y);
                    
                    if (m.attendre==1){
                        m.traiterCoupHumain(x,y);
                
                        actualiser(tab,m);
                    
                        if(m.mode==2){
                            ai=1;
                            temps = System.currentTimeMillis();
                        }
                        else{
                            m.attendre=1;
                        }
                    }
                }
            }
        });     
    }
    
    public void affichage_mode_jeu(Stage primaryStage, Button btn1, Button btn2){
        primaryStage.setTitle("Sélection du mode de jeu");       
        AnchorPane mode_j = new AnchorPane();
        Scene sc = new Scene(mode_j,n_scene,m_scene,Color.GREY);
        mode_j.setStyle("-fx-border-color: black;");
        
        mode_j.getChildren().add(btn1);
        mode_j.getChildren().add(btn2);
        
        AnchorPane.setTopAnchor(btn1,100.0);
        AnchorPane.setLeftAnchor(btn1,200.0);
        AnchorPane.setRightAnchor(btn1,200.0);
        AnchorPane.setBottomAnchor(btn1,350.0);
            
        AnchorPane.setTopAnchor(btn2,350.0);
        AnchorPane.setLeftAnchor(btn2,200.0);
        AnchorPane.setRightAnchor(btn2,200.0);
        AnchorPane.setBottomAnchor(btn2,100.0);
            
        primaryStage.setScene(sc);
        primaryStage.show();
    }
    
    public void affichage_mode_difficulte(Stage primaryStage, Button btn3, Button btn4, Button btn5){
        primaryStage.setTitle("Affichage des difficultés");
        AnchorPane mode_j = new AnchorPane();
        Scene sc = new Scene(mode_j,n_scene,m_scene,Color.GREY);       
        mode_j.setStyle("-fx-border-color: black;");
        
        mode_j.getChildren().add(btn3);
        mode_j.getChildren().add(btn4);
        mode_j.getChildren().add(btn5);
        
        AnchorPane.setTopAnchor(btn3,100.0);
        AnchorPane.setLeftAnchor(btn3,200.0);
        AnchorPane.setRightAnchor(btn3,200.0);
        AnchorPane.setBottomAnchor(btn3,400.0);
            
        AnchorPane.setTopAnchor(btn4,250.0);
        AnchorPane.setLeftAnchor(btn4,200.0);
        AnchorPane.setRightAnchor(btn4,200.0);
        AnchorPane.setBottomAnchor(btn4,250.0);
        
        AnchorPane.setTopAnchor(btn5,400.0);
        AnchorPane.setLeftAnchor(btn5,200.0);
        AnchorPane.setRightAnchor(btn5,200.0);
        AnchorPane.setBottomAnchor(btn5,100.0);
            
        primaryStage.setScene(sc);
        primaryStage.show();
    }
    
    public void init(Case [][] tab,AnchorPane pane) {
        int i = 0;
        int j = 0;
        
        for(i=0;i<N;i++){
            for(j=0;j<M;j++){
                tab[i][j] = new Case(lx,ly);
                //tab[i][j].setTranslateX(-((n_scene/2)-(lx/2))+lx*i);        //StackPane
                //tab[i][j].setTranslateY(-((m_scene/2)-(ly/2))+ly*j);        //StackPane
                
                tab[i][j].setTranslateX(lx*i);        //AnchorPane
                tab[i][j].setTranslateY(ly*j);        //AnchorPane

                tab[i][j].c.setFill(Color.MOCCASIN);
                AnchorPane.setTopAnchor(tab[i][j].c,(double)i*lx);
                AnchorPane.setLeftAnchor(tab[i][j].c,(double)j*ly);
                AnchorPane.setRightAnchor(tab[i][j].c,(double)(n_scene-(j*ly)));
                AnchorPane.setBottomAnchor(tab[i][j].c,(double)(m_scene-(i*lx)));
                pane.getChildren().add(tab[i][j]);
            }
        }   
        
        Circle poison = new Circle();
        poison.setCenterX(50);
        poison.setCenterY(50);
        poison.setRadius(25);
        //poison.setTranslateX(-(n_scene/2)+(lx/2)-1);      //StackPane
        //poison.setTranslateY(-(m_scene/2)+(ly/2)-1);      //StackPane
        poison.setFill(Color.FIREBRICK);
        pane.getChildren().add(poison);

        pane.setStyle("-fx-border-color: black;");
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        m = new Moteur();

        Button btn1 = new Button("Joueur VS Joueur");
        Button btn2 = new Button("Joueur VS Ordinateur");
        Button btn3 = new Button("Facile");
        Button btn4 = new Button("Moyen");
        Button btn5 = new Button("Difficile");
        Button btn6 = new Button("Arrière");
        Button btn7 = new Button("Avant");
        Button btn8 = new Button("Sauvegarder");
        Button btn9 = new Button("Charger");
             
        affichage_mode_jeu(primaryStage,btn1,btn2);
       
        btn1.setOnAction(new EventHandler <ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                // JcJ
                m.mode=1;
                affichage_gaufre(primaryStage,btn6,btn7,btn8,btn9);
            }
        });
        
        btn2.setOnAction(new EventHandler <ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                // JcO  
                m.mode=2;
                affichage_mode_difficulte(primaryStage,btn3,btn4,btn5);
                
            }
        });
        
        btn3.setOnAction(new EventHandler <ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                // Facile
                m.difficulte=1;
                affichage_gaufre(primaryStage,btn6,btn7,btn8,btn9);
            }
        });
        
        btn4.setOnAction(new EventHandler <ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                // Moyen
                m.difficulte=2;
                affichage_gaufre(primaryStage,btn6,btn7,btn8,btn9);
            }
        });
        
        btn5.setOnAction(new EventHandler <ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                // Difficile
                m.difficulte=3;
                affichage_gaufre(primaryStage,btn6,btn7,btn8,btn9);
            }
        });
        
        
        AnimationTimer timer = new AnimationTimer() {
        @Override
            public void handle(long now) {
                if (m.mode==2 && ai==1 && temps+1000<System.currentTimeMillis()){
                    m.traiterCoupAI();
                    ai=0;
                    actualiser(tab, m);
                }
            }
        };
        timer.start();
          
    }   
}