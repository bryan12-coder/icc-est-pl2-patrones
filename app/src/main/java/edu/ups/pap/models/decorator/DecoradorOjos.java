package edu.ups.pap.models.decorator;

import java.awt.Color;
import java.awt.Graphics;

import edu.ups.pap.models.Posicion;

public class DecoradorOjos extends DecoradorApariencia {

    public DecoradorOjos(Apariencia apariencia) {
        super(apariencia);
        
    }
     @Override
    public void dibujar(Graphics g, Posicion p) {
        super.dibujar(g, p);
        g.setColor(Color.BLACK);
    }
    
}
