
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gaufre;

/**
 *
 * 
 */
public class Vecteur {
    int vec;
    int largeur;
    int hauteur;
            
    
    
    public Vecteur(){
        vec = 0;
        largeur = 0;
        hauteur =0;
    }
    
    public Vecteur(boolean[][] plateau){
        largeur = plateau[0].length;
        hauteur = plateau.length;
        vec = 0;
        int j = 0;
        int i = hauteur-1;
        while (j < largeur || i >= 0){
           //System.out.println("i= "+i+" | j= "+j);
           if(j<largeur && (i<0 || plateau[i][j])){
               vec = vec*2;
               j++;
           }
           else{
               vec = vec*2+1;
               i--;
           }
        }
        
    }
    
    public boolean[][] plateau(){
        boolean[][] res = new boolean[hauteur][largeur];
        int i = hauteur + largeur - 1;
        int ord = hauteur -1;
        int abs = 0;
        while (i>=0){
            if ((vec & 1)== 0 && ord >= 0){
                int j = ord;
                while (j>=0){
                    res[j][abs]=true;
                    j--;
                } 
                abs++;
            }
            else{
                ord--;
            }
            i--;
            vec = vec>>1;
        }
        for(int k = 0; k<res.length; k++){
            for(int j = 0; j<res[0].length; j++){
                if(!res[k][j]){
                    res[k][j] = false;
                }
            }
        }
        for(int bl=0; bl<6 && (res[bl][0]==true); bl++){    //Affichage du plateau de jeu
            System.out.print("|");
            for(int blbl=0; blbl<8 && (res[bl][blbl]==true); blbl++){
                System.out.print("_|");
            }
            System.out.println("");
        }
        System.out.println("");
        return res;
    }
       
}
