/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gaufre;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * La classe Arbre 
 */
public class Arbre {
    int etat;
    Arbre pere;
    List<Arbre> fils;
    Vecteur vec;
    
    public Arbre(){
        etat = 0;
        pere = null;
        fils = new ArrayList();
        vec = new Vecteur();
    }
    
    
    public int getEtat(){
        return this.etat;
    }
    
            
    public void addFils(Arbre a){
        fils.add(a);
    }
    
    public void setEtat(int n){
        this.etat = n;
    }
    
    public void setVecteur(Vecteur v){
        this.vec = v;
    }
    
}
