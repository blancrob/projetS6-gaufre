/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gaufre;
import java.util.*;
import java.awt.Point;
import static java.lang.Integer.max;
import static java.lang.Integer.min;
import java.lang.Iterable;



public class AI {
    
    boolean[][] plateau;
    int joueur, largeur, hauteur;
    Arbre a;

    
    AI(){
        plateau = new boolean[0][0];
        joueur = 0;
        largeur = 0;
        hauteur = 0;
        a = new Arbre();
    }
    
    AI(boolean[][] p, int j){
        plateau = p;
        joueur = j;
        largeur = p[0].length;
        hauteur = p.length;
        a = new Arbre();
        
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
     * @param res
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
     /**
     * @param plateau
     * @param ordonnee
     * @param abscisse
     *@return une configuration du plateau
     *
     */
     public boolean[][] simulerCoup(boolean[][] plateau, int ordonnee, int abscisse){
         boolean[][] res = plateau;
         for(int i=ordonnee; i<6 ; i++){ //On met à false les cases mangées
            for(int j=abscisse; j<8 ; j++){
                res[i][j]=false;
            }
        }
         return res;
     }
     /**
      * 
      * @param plateau la configuration courante.
      * @param horizon la profondeur étudiée.
     * @return un arbre
      */
     public Arbre creerArbre(boolean[][] plateau, int horizon){
        Arbre courant = new Arbre();
        if(horizon > 0){
            System.out.println("-");
            Vecteur v = new Vecteur(plateau);
            courant.setVecteur(v);
            int etat = this.heuristiquePlateau(this.heuristique());
            courant.setEtat(etat);
            for(int i=0;i<plateau.length;i++){
                for (int j=0;j<plateau[0].length && plateau[i][j];j++){
                    boolean[][] sim = this.simulerCoup(plateau,i,j);
                    Arbre temp = this.creerArbre(sim, horizon-1);
                    courant.addFils(temp);
                }
            }
            boolean[][] sim = this.simulerCoup(plateau,1,1);
            Arbre temp = this.creerArbre(sim, horizon-1);
            courant.addFils(temp);
        }
        return courant;
     }
     /**
      * @param ar un arbre des configurations possibles à partir de la configuration courante.
      * @return la valeur de l'etat du meilleur chemin vers la victoire !
      */
     public int minimax_joueur(Arbre ar){
         if(ar.fils.isEmpty()){
             return ar.getEtat();
         }
         else{
             int val = -1;   
             for (Arbre fil : ar.fils) {
                     val = max(minimax_adversaire(fil),val);
             }
             ar.setEtat(val);
             return val;
         }
     }
     
     /**
      * @param ar un arbre des configurations possibles à partir de la configuration courante.
      * @return la valeur de l'etat du meilleur chemin vers la defaite !
      */
     public int minimax_adversaire(Arbre ar){
         if(ar.fils.isEmpty()){
                return ar.getEtat();
             
         }
         else{
             int val = 99999;   
                for(Arbre arbre : ar.fils){
                   val = min(minimax_adversaire(arbre),val);
                }
             ar.setEtat(val);
             return val;
         }
     }
     /**
      * 
      * @return un tableau des vecteur des meilleurs configurations suivante pour la victoire !
      */
     public Vecteur[] meilleurResultat(int etat){
        Vecteur[] res = new Vecteur[10];
        int i = 0;
        for(Arbre ar : a.fils){
            if(ar.getEtat() == etat && i < 10){
                res[i]=ar.getVecteur();
                i++;
            }
        }
        return res;
     }
     
     public Point meilleurCoup(Vecteur[] tab){
         Point p = new Point();
         Random r = new Random();
         int k =r.nextInt(tab.length-1);
         while (tab[k]==null){
             k=r.nextInt(tab.length-1);
         }
         Vecteur v = tab[k];
         System.out.println("config :");
         boolean[][] config = v.plateau();
         int abs = 0;
         int ord = 0;
         while (ord < plateau.length && (plateau[ord][abs]==config[ord][abs])){
             abs = 0;
             while(abs < plateau[0].length && (plateau[ord][abs]==config[ord][abs])){
                 abs++;
             }
             ord++;
         }
         p.setLocation(ord,abs);
         return p;    
         
     }
     
     
     public Point aiMinMax(){
        a = this.creerArbre(plateau, 5);
        Point p = this.meilleurCoup(this.meilleurResultat(this.minimax_joueur(a)));
        return p;
     }
    
    
}
