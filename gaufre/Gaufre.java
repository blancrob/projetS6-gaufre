/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gaufre;

import java.util.*;
import java.io.*;
import java.awt.Point;

public class Gaufre {
    
    static boolean [][] plateau;
    static int joueur;
    
    /**
     * Sauvegarde le plateau courant dans un fichier sauvegarde.txt
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void save() throws FileNotFoundException, IOException{
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
    public static void load() throws FileNotFoundException, IOException{
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
    
    public static boolean[][] jouer_coup(boolean[][] tab, int ordonnee, int abcisse){
        for(int i=ordonnee; i<6 ; i++){ //On met à false les cases mangées
            for(int j=abcisse; j<8 ; j++){
                tab[i][j]=false;
            }
        }
        
        return tab;
    }
    
    /**
     * Lance une partie de gaufre dans le terminal, en joueur contre joueur ou joueur contre ordinateur
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void moteur() throws FileNotFoundException, IOException{
        plateau = new boolean[6][8];    //plateau de jeu
        joueur = 1; //joueur dont c'est le tour de joueur
        int abcisse, ordonnee, choix, choixAction, difficulte=0, playAgain=0;
        Point p = null;
        Scanner sc = new Scanner(System.in);
        
        do{ //Tant que la variable playAgain vaut 1 on rejout
        
            for(int i=0; i<6; i++){ //On initialise le plateau à true
                for(int j=0; j<8; j++){
                    plateau[i][j]=true;
                }
            }

            System.out.println("Jeu de la Gaufre");

            System.out.println("Entrez une valeur :");
            System.out.println("1: Joueur contre Joueur");
            System.out.println("2: Joueur contre Ordinateur");
            choix = Integer.parseInt(sc.nextLine());    //Choix du mode de jeu

            if(choix==2){   //si en mode contre Ordinateur, choix de la difficulté
                System.out.println("Choisissez une difficulté :");
                System.out.println("1: Aléatoire");
                System.out.println("2: Coup gagnant/Non coup perdant");
                System.out.println("3: MinMax");    //MinMax ne marche pas, ici à titre de test pour l'équipe AI
                difficulte = Integer.parseInt(sc.nextLine());
            }

            for(int i=0; i<6 && (plateau[i][0]==true); i++){    //Affichage du plateau de jeu
                System.out.print("|");
                for(int j=0; j<8 && (plateau[i][j]==true); j++){
                    System.out.print("_|");
                }
                System.out.println("");
            }
            System.out.println("");

            while(plateau[0][0]){   //Tant que le poison n'a pas été joué, on continue la partie

                System.out.println("Tour du joueur " + joueur);

                if(choix==2 && joueur==2){  ///si c'est le tour de l'ordinateur
                    AI ai = new AI(plateau,joueur);
                    if(difficulte==1){  //difficulté aléatoire
                        p = ai.aiAleatoire();
                    }
                    else if(difficulte==2){ //difficulté Coup Gagnant et sinon Coup Non Perdant
                        p = ai.aiNonCoupPerdant();
                    }
                    else{   //difficulté MinMax (pas utilisable)
                        ai.aiMinMax();
                    }
                    ordonnee = (int) p.getY();
                    abcisse = (int) p.getX();
                }
                else{   //Tour d'un joueur humain
                    System.out.println("Appuyer sur 1 pour sauvegarder, 2 pour charger une partie, 0 sinon");
                    choixAction = Integer.parseInt(sc.nextLine());
                    if(choixAction == 1)
                        save();
                    else if(choixAction == 2)
                        load();
                    
                    System.out.print("Abcisse :");  //L'utilisateur entre l'abcisse et l'ordonnee
                    abcisse = Integer.parseInt(sc.nextLine());
                    System.out.println("");
                    System.out.print("Ordonnee :");
                    ordonnee = Integer.parseInt(sc.nextLine());
                    System.out.println("");
                }
                
                plateau = jouer_coup(plateau, ordonnee, abcisse);

                for(int i=0; i<6 && (plateau[i][0]==true); i++){    //Affichage du plateau modifié
                    System.out.print("|");
                    for(int j=0; j<8 && (plateau[i][j]==true); j++){
                        System.out.print("_|");
                    }
                    System.out.println("");
                }
                System.out.println("");

                if(joueur==1)   //On change le joueur dont c'est le tour
                    joueur=2;
                else
                    joueur=1;

            }

            if(choix==2 && joueur==2){  //Si tour de l'ordinateur
                System.out.println("ORDINATEUR VAINQUEUR");
            }
            else{   //Sinon, message de victoire adapté au joueur vainqueur
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
