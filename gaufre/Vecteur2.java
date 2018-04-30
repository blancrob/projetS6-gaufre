/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gaufre;

/**
 *
 * @author blancrob
 */
public class Vecteur2 {
    int val; //Vecteur de 32 bits  
    
    public Vecteur2 (int v){
        val=v;
    }
    public Vecteur2 (boolean [][] plateau){ 
        val=0;
        byte offset;
        int i=(plateau.length)-1;
        int j=0;
        while ( i >=0 && j< plateau[0].length ) {
            if( plateau[i][j] == false ){
                i--;
                offset=1;
            }
            else{
                j++;
                offset=0;
            }
            val=(val<<1)|offset;
        }    
    }
    
    public boolean[][] toPlateau(int hauteur, int largeur){
        int nbBits=largeur+hauteur; //nombre de bits significatifs du vecteur
        boolean[][] p =new boolean[hauteur][largeur];
        int h,l;
        for(h=0;h<hauteur;h++){ //initialisation du tableau de booleen à vrai
            for(l=0;l<largeur;l++){
                p[h][l]=true;
            }
        }
        l=(p[0].length)-1;
        h=0;
        int vecteur=val;

        int i = 0;
        int j;
        while (i < nbBits){
            while( (vecteur & 1)==1 ){
                h++;
                vecteur=vecteur>>1;
                i++;
            }
            j=h;
            while (j < hauteur ){ //remplissage avec true sur toute la hauteur correspondante.
                p[j][l]=false;
                j++;
            }
            l--;
            vecteur=vecteur>>1;
            i++;
        }
        return p;    
    }
    

    public boolean egal(Vecteur2 v){
        return this.val==v.val;
    }
}
