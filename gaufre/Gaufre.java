/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gaufre;

import java.util.*;
import java.io.*;
import java.awt.Point;

/**
 *
 * @author costeb
 */
public class Gaufre {
    
    static boolean [][] plateau;
    static int joueur;
    
    public static void save() throws FileNotFoundException, IOException{
        File f = new File("test.txt");
        FileWriter ffw = new FileWriter(f);
        for(int i=0; i<6; i++){
            for(int j=0; j<8; j++){
                if(plateau[i][j]==true){
                    ffw.write(i + " " + j + "\n");
                }
            }
        }
        ffw.close();
    }
    
    public static void load() throws FileNotFoundException, IOException{
        File f = new File("test.txt");
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;
        String[] coord;
        
        for(int i=0; i<6; i++){
            for(int j=0; j<8; j++){
                plateau[i][j]=false;
            }
        }
        
        while ((line = br.readLine()) != null) {
            coord=line.split(" ");
            plateau[ Integer.parseInt(coord[0]) ][ Integer.parseInt(coord[1]) ] = true;
        }
        
        System.out.println("Plateau chargé:");
        for(int i=0; i<6 && (plateau[i][0]==true); i++){
            System.out.print("|");
            for(int j=0; j<8 && (plateau[i][j]==true); j++){
                System.out.print("_|");
            }
            System.out.println("");
        }
        System.out.println("");
            
        br.close();
    }
     
    public static void moteur() throws FileNotFoundException, IOException{
        plateau = new boolean[6][8];
        joueur = 1;
        int abcisse, ordonnee, choix, choixAction, difficulte=0, playAgain=0;
        Point p = null;
        Scanner sc = new Scanner(System.in);
        
        do{
        
            for(int i=0; i<6; i++){
                for(int j=0; j<8; j++){
                    plateau[i][j]=true;
                }
            }

            System.out.println("Jeu de la Gaufre");

            System.out.println("Entrez une valeur :");
            System.out.println("1: Joueur contre Joueur");
            System.out.println("2: Joueur contre Ordinateur");
            choix = Integer.parseInt(sc.nextLine());

            if(choix==2){
                System.out.println("Choisissez une difficulté :");
                System.out.println("1: Aléatoire");
                System.out.println("2: Coup gagnant/Non coup perdant");
                difficulte = Integer.parseInt(sc.nextLine());
            }

            for(int i=0; i<6 && (plateau[i][0]==true); i++){
                System.out.print("|");
                for(int j=0; j<8 && (plateau[i][j]==true); j++){
                    System.out.print("_|");
                }
                System.out.println("");
            }
            System.out.println("");

            while(plateau[0][0]){

                System.out.println("Tour du joueur " + joueur);
                System.out.println("Entrez les coordonées du point à manger");

                if(choix==2 && joueur==2){
                    AI ai = new AI(plateau,joueur);
                    if(difficulte==1){
                        p = ai.aiAleatoire();
                    }
                    else if(difficulte==2){
                        p = ai.aiNonCoupPerdant();
                    }
                    ordonnee = (int) p.getY();
                    abcisse = (int) p.getX();
                }
                else{
                    System.out.println("Appuyer sur 1 pour sauvegarder, 0 sinon");
                    choixAction = Integer.parseInt(sc.nextLine());
                    if(choixAction == 1)
                        save();
                    
                    System.out.println("Appuyer sur 1 pour charger, 0 sinon");
                    choixAction = Integer.parseInt(sc.nextLine());
                    if(choixAction == 1)
                        load();
                    
                    System.out.print("Abcisse :");
                    abcisse = Integer.parseInt(sc.nextLine());
                    System.out.println("");
                    System.out.print("Ordonnee :");
                    ordonnee = Integer.parseInt(sc.nextLine());
                    System.out.println("");
                }

                for(int i=ordonnee; i<6 ; i++){
                    for(int j=abcisse; j<8 ; j++){
                        plateau[i][j]=false;
                    }
               }

                for(int i=0; i<6 && (plateau[i][0]==true); i++){
                    System.out.print("|");
                    for(int j=0; j<8 && (plateau[i][j]==true); j++){
                        System.out.print("_|");
                    }
                    System.out.println("");
                }
                System.out.println("");

                if(joueur==1)
                    joueur=2;
                else
                    joueur=1;

            }

            if(choix==2 && joueur==2){
                System.out.println("ORDINATEUR VAINQUEUR");
            }
            else{
                System.out.println("JOUEUR " + joueur + " VAINQUEUR");
            }
            System.out.println("Entrez 1 pour rejouer, 0 pour quitter");
            playAgain = Integer.parseInt(sc.nextLine());
        }while(playAgain==1);
        
    }
    
     public static void main(String[] args) throws FileNotFoundException, IOException {
        moteur();
     }
    
}
