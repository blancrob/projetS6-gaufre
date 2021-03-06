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
            if (mode==1){
                if(joueur==1){
                    joueur=2;
                }
                else{
                    joueur=1;
                }
            }
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
            if (mode==1){
                if(joueur==1){
                    joueur=2;
                }
                else{
                    joueur=1;
                }
            }
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
        ffw.write("-\n");
        
        ArrayList<boolean[][]> undo2 = new ArrayList();
        while(!undo.empty()){
            undo2.add(undo.pop());
        }
        ListIterator<boolean[][]> it = undo2.listIterator(undo2.size());
        while(it.hasPrevious()){
            Vecteur v = new Vecteur(it.previous());
            ffw.write(v.vec+"\n");
        }
        it = undo2.listIterator(undo2.size());
        while(it.hasPrevious()){
            undo.push(it.previous());
        }
        ffw.write("-\n");
        
        ArrayList<boolean[][]> redo2 = new ArrayList();
        while(!redo.empty()){
            redo2.add(redo.pop());
        }
        it = redo2.listIterator(redo2.size());
        while(it.hasPrevious()){
            Vecteur v = new Vecteur(it.previous());
            ffw.write(v.vec+"\n");
        }
        it = redo2.listIterator(redo2.size());
        while(it.hasPrevious()){
            redo.push(it.previous());
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
        String[] coord,vec;
        
        for(int i=0; i<6; i++){ //on initialise toutes les cases du plateau à false
            for(int j=0; j<8; j++){
                plateau[i][j]=false;
            }
        }
        
        //On vide les piles undo et redo
        
        while(!undo.empty()){
            undo.pop();
        }
        
        while(!redo.empty()){
            redo.pop();
        }
        
        while (!(line = br.readLine()).equals("-")) {    //On récupère les coordonnées des cases dans le fichier sauvegarde.txt, puis on les met à true dans le plateau
            coord=line.split(" ");
            plateau[ Integer.parseInt(coord[0]) ][ Integer.parseInt(coord[1]) ] = true;
        }
        
        while (!(line = br.readLine()).equals("-")) {    //On récupère les coordonnées des cases dans le fichier sauvegarde.txt, puis on les met à true dans le plateau
            Vecteur2 v = new Vecteur2(Integer.parseInt(line));
            undo.push(v.toPlateau(6,8));
        }
        
        while ((line = br.readLine()) != null) {    //On récupère les coordonnées des cases dans le fichier sauvegarde.txt, puis on les met à true dans le plateau
            Vecteur2 v = new Vecteur2(Integer.parseInt(line));
            redo.push(v.toPlateau(6,8));
        }
        
        
            
        br.close();
    }
    
    public void traiterCoupHumain(int ordonnee, int abcisse){
        while(!redo.empty()){
            redo.pop();
        }
        plateau = jouer_coup(plateau, ordonnee, abcisse);
        attendre=0;
        if(joueur==1){
            joueur=2;
        }
        else{
            joueur=1;
        }
    }
    
    public void traiterCoupAI(){
        jouer_coup_ai(plateau,difficulte);
        joueur = 1;
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
            case(3):
                p = ai.aiMinMax();
                break;
        }
        int ordonnee = (int) p.getY();
        int abcisse = (int) p.getX();
        
        for(int i=ordonnee; i<6 ; i++){ //On met à false les cases mangées
            for(int j=abcisse; j<8 ; j++){
                tab[i][j]=false;
            }
        }
        
        return tab;
    }
    
}
