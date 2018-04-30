/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gaufre;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author haudryf
 */

public class Case extends Parent {
    
    Rectangle c;
    
    public Case(int lengthX, int lengthY) {
        
        int l = Gaufre.N;
        int h = Gaufre.M;
        
        c = new Rectangle(lengthX,lengthY);
        c.setStroke(Color.BLACK);

        this.getChildren().add(c);
        
    }
    
}
