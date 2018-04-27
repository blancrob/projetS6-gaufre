/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gaufre;

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
     * 
     * @return
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
    
    public Point aiCoupGagnant(){
        Point p = new Point();
        if (!plateau[0][1] && plateau[1][0]){
            p.setLocation(1,0);
        }
        else if(plateau[0][1] && !plateau[1][0]){
            p.setLocation(0,1);
        }
        return p;
    }
    
    public Point aiNonCoupPerdant(){
        Point p = new Point();
        p = this.aiCoupGagnant();
        if ( p==null ){
            p=this.aiAleatoire();
            if(plateau[0][1] && plateau[1][0] && (plateau[2][0] || plateau[1][1] || plateau[0][2])){
               while((p.getX() <= 1 && p.getY()==0) || (p.getX() ==0 && p.getY() <=1) ){
                   p=this.aiAleatoire();
               }
            }
        }
        return p;
    }
    
    
    
    
}
