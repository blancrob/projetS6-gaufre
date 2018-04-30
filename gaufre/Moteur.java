/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gaufre;

import java.util.*;
import java.io.*;
import java.awt.Point;

public class Moteur {
    
    boolean [][] plateau;
    int joueur, attendre, mode, difficulte;
    Stack<boolean[][]> undo, redo;
    
    public Moteur(){
        plateau = new boolean[6][8];
        joueur = 1;
        undo = new Stack();
        redo = new Stack();
        Scanner sc = new Scanner(System.in);
        
        for(int i=0; i<6; i++){ // Parcours du plateau, les coordonnées des cases à true sont notées dans le fichier, une par ligne
            for(int j=0; j<8; j++){
                plateau[i][j]=true;
            }
        }
        
        attendre = 1;
    }
    
     public Moteur(boolean[][] plateau){
        this.plateau=plateau;
        joueur = 0 ;
        attendre = 0 ;
        difficulte = 0 ;
        mode = 0 ;
    }
    
    public void undo(){
        if(!undo.empty()){
            boolean [][] tmp = new boolean[6][8];
            for(int i=0; i<6; i++){    //Affichage du plateau de jeu
                for(int j=0; j<8 ; j++){
                    tmp[i][j]=plateau[i][j];
                }
            }
            redo.push(tmp);
            plateau = undo.pop();
        }
    }
    
    public void redo(){
        if(!redo.empty()){
            boolean [][] tmp = new boolean[6][8];
            for(int i=0; i<6; i++){    //Affichage du plateau de jeu
                for(int j=0; j<8 ; j++){
                    tmp[i][j]=plateau[i][j];
                }
            }
            undo.push(plateau);
            plateau = redo.pop();
        }
    }
    
    /**
     * Sauvegarde le plateau courant dans un fichier sauvegarde.txt
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void save() throws FileNotFoundException, IOException{
        File f = new File("sauvegarde.txt");
        FileWriter ffw = new FileWriter(f);
        for(int i=0; i<6; i++){ // Parcours du plateau, les coordonnées des cases à true sont notées dans le fichier, une par ligne
            for(int j=0; j<8; j++){
                if(plateau[i][j]==true){
                    ffw.write(i + " " + j + "\n");
                }
            }
        }
        ffw.close();
    }
    
    /**
     * On charge un plateau de jeu à partir des coordonnées dans le fichier sauvegarde.txt
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void load() throws FileNotFoundException, IOException{
        File f = new File("sauvegarde.txt");
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;
        String[] coord;
        
        for(int i=0; i<6; i++){ //on initialise toutes les cases du plateau à false
            for(int j=0; j<8; j++){
                plateau[i][j]=false;
            }
        }
        
        while ((line = br.readLine()) != null) {    //On récupère les coordonnées des cases dans le fichier sauvegarde.txt, puis on les met à true dans le plateau
            coord=line.split(" ");
            plateau[ Integer.parseInt(coord[0]) ][ Integer.parseInt(coord[1]) ] = true;
        }
        
        System.out.println("Plateau chargé:");
        for(int i=0; i<6 && (plateau[i][0]==true); i++){    //affichage du plateau chargé
            System.out.print("|");
            for(int j=0; j<8 && (plateau[i][j]==true); j++){
                System.out.print("_|");
            }
            System.out.println("");
        }
        System.out.println("");
            
        br.close();
    }
    
    public void fin(){
        if(plateau[0][0]==false){
            if(mode==2 && joueur==2){  //Si tour de l'ordinateur
                System.out.println("ORDINATEUR VAINQUEUR");
            }
            else{   //Sinon, message de victoire adapté au joueur vainqueur
                System.out.println("JOUEUR " + joueur + " VAINQUEUR");
            }
            System.exit(0);
        }
    }
    
    public void traiterCoupHumain(int ordonnee, int abcisse){
        plateau = jouer_coup(plateau, ordonnee, abcisse);
        attendre=0;
        if(joueur==1){
            joueur=2;
        }
        else{
            joueur=1;
        }
        fin();
    }
    
    public void traiterCoupAI(){
        jouer_coup_ai(plateau,difficulte);
        joueur = 1;
        fin();
        attendre=1;
        
    }
    
    public boolean[][] jouer_coup(boolean[][] tab, int ordonnee, int abcisse){
        boolean [][] tmp = new boolean[6][8];
        for(int i=0; i<6; i++){    //Affichage du plateau de jeu
            for(int j=0; j<8 ; j++){
                tmp[i][j]=tab[i][j];
            }
        }
        
        undo.push(tmp);
        
        for(int i=ordonnee; i<6 ; i++){ //On met à false les cases mangées
            for(int j=abcisse; j<8 ; j++){
                tab[i][j]=false;
            }
        }
        
        for(int i=0; i<6 && (tab[i][0]==true); i++){    //Affichage du plateau de jeu
            System.out.print("|");
            for(int j=0; j<8 && (tab[i][j]==true); j++){
                System.out.print("_|");
            }
            System.out.println("");
        }
        System.out.println("");
        
        return tab;
    }
    
    public boolean[][] jouer_coup_ai(boolean[][] tab, int difficulte){
        
        AI ai = new AI(tab,joueur);
        Point p = null;
        
        switch(difficulte){
            case(1):
                p = ai.aiAleatoire();
                break;
            case(2):
                p = ai.aiNonCoupPerdant();
                break;
        }
        int ordonnee = (int) p.getY();
        int abcisse = (int) p.getX();
        
        for(int i=ordonnee; i<6 ; i++){ //On met à false les cases mangées
            for(int j=abcisse; j<8 ; j++){
                tab[i][j]=false;
            }
        }
        
        for(int i=0; i<6 && (tab[i][0]==true); i++){    //Affichage du plateau de jeu
            System.out.print("|");
            for(int j=0; j<8 && (tab[i][j]==true); j++){
                System.out.print("_|");
            }
            System.out.println("");
        }
        System.out.println("");
        
        return tab;
    }
    
}
