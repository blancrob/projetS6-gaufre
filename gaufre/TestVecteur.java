/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gaufre;

/**
 *
 * @author robin
 */
public class TestVecteur {
    
    public static void print(boolean[][] p) {
    for (int y = 0; y < p.length; ++y) {
        for (int x = 0; x < p[y].length; ++x) {
            if(p[y][x])
                System.out.print("T ");
            else
                System.out.print(". ");

        }
        System.out.println();
    }
    }
    public static void remplirLigne(boolean[][]p, int h, int nb){
        int i=0;
        while (i<nb){
            p[h][i]=true;
            i++;
        }
    }
    
    public static void main(String[] args) {
        boolean[][]plateau=new boolean[6][8];
        remplirLigne(plateau,0,7);
        remplirLigne(plateau,1,5);
        remplirLigne(plateau,2,5);
        remplirLigne(plateau,3,4);
        remplirLigne(plateau,4,3);
        remplirLigne(plateau,5,2);
        print(plateau);
        Vecteur2 v=new Vecteur2(plateau);
        System.out.println(v.val);
        Vecteur2 v2=new Vecteur2(v.val);
         System.out.println(v2.val);
         System.out.println(v2.egal(v));
        plateau=v.toPlateau(6, 8);
        print(plateau);
    }
        
        
        
        
        
    }
