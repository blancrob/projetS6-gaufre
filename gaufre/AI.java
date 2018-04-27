/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gaufre;
import static gaufre.Gaufre.plateau;
import java.util.*;
import java.awt.Point;
public class AI {
    
    boolean[][] plateau;
    int joueur, largeur, hauteur;

    
    AI(){
        plateau = new boolean[0][0];
        joueur = 0;
        largeur = 0;
        hauteur = 0;
    }
    
    AI(boolean[][] p, int j){
        plateau = p;
        joueur = j;
        largeur = p[0].length;
        hauteur = p.length;
    }
    
    /**
     * init : void
     * @return un point p choisit de manière aléatoire, hors poison si possible, parmis les cases possibles.
     */
    public Point aiAleatoire(){
        Point p = new Point();
        Random r = new Random();
        if(!plateau[0][1] && !plateau[1][0]){
            p.setLocation(0,0);
        }
        else{
            int y = r.nextInt(hauteur);
            int x = r.nextInt(largeur);
            while (!plateau[y][x] || (x==0 && y==0)){
                y = r.nextInt(hauteur);
                x = r.nextInt(largeur);
            }
            p.setLocation(x,y);
        }
        return p;
    }
    
    /**
     * @return un point p permettant de gagner, null sinon.
     * Si il ne reste que la colonne 0 ou que la ligne 0, on mange tout sauf la case poison -> c'est un coup gagnant 
     */
    public Point aiCoupGagnant(){
        Point p = new Point();
        if (!plateau[0][1] && plateau[1][0]){
            p.setLocation(0,1);
        }
        else if(plateau[0][1] && !plateau[1][0]){
            p.setLocation(1,0);
        }
        return p;
    }
    
    /**
     * @return un point p permettant de gagner si possible ou de ne pas perdre sinon.
     */
    public Point aiNonCoupPerdant(){
        Point p = this.aiCoupGagnant();
        if ( p.getX() == 0 && p.getY() == 0 ){
            p=this.aiAleatoire();
            if(plateau[0][1] && plateau[1][0] && (plateau[2][0] || plateau[1][1] || plateau[0][2])){
               while((p.getX() <= 1 && p.getY()==0) || (p.getX() ==0 && p.getY() <=1) ){
                   p=this.aiAleatoire();
               }
            }
        }
        return p;
    }
    
     /**
     * @return un int[][] avec dans chaque case l'heuristique correspondante
     * heuristique [i][j] = nombre de cases mangées en cliquant sur la case [i][j] (sauf cases [0][0],[1][0] et [0][1] -> 0 car entraine la défaite.
     */
     public int[][] heuristique(){
        int[][] res = new int[hauteur][largeur];
        for(int i=hauteur-1 ; i>=0 ; i--) {
            for (int j=largeur-1 ; j >= 0; j--){
                if(plateau[i][j]){
                    int hauteurbis = 0;
                    int k = i;
                    while (k<hauteur && plateau[k][j]){
                        k++;
                        hauteurbis++;
                    }
                    if (j == largeur -1 || !plateau[i][j+1]){
                        res[i][j] = hauteurbis;
                    }
                    else{
                        res[i][j] = hauteurbis + res[i][j+1];
                    }
                }     
            } 
        }  
        res[0][0]=res[0][1]=res[1][0]=0;
        for (int[] re : res) {
            System.out.print("|");
            for (int j = 0; j < re.length; j++) {
                System.out.print(re[j] + "|");
            }
            System.out.println("");
        }
            System.out.println("");
        return res;
    }
     
      /**
     * @return un int correspondant à l'heuristique de la configuration du plateau
     * Simple parcours du plateau avec addition des différentes heuristiques
     */
     public int heuristiquePlateau(int[][] res){
        int somme = 0;
        for(int i=0; i<res.length; i++){
             for(int j=0; j<res[0].length; j++){
                 somme = somme + res[i][j];
             }
         }
        return somme;
     }
     
     
     public Point aiMinMax(){
         
        Point p = new Point();
        
        
        return p;
     }
    
    
}
