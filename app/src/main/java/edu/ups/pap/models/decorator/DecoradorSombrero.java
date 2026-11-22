package edu.ups.pap.models.decorator;
import java.awt.Color;
import java.awt.Graphics;

import edu.ups.pap.models.Posicion;

public class DecoradorSombrero extends DecoradorApariencia {

    public DecoradorSombrero(Apariencia apariencia) {
        super(apariencia);
        
    }
    @Override 
    public void dibujar(Graphics g, Posicion p){
        super.dibujar(g, p);

        g.setColor(Color.BLACK);

        // Parte superior del sombrero
        g.fillRect(p.getX() * 20 + 5,
                   p.getY() * 20 - 8, 10, 9);

        // Ala del sombrero
        g.fillRect(p.getX() * 20 + 2,
                   p.getY() * 20, 16, 3);
    }
    
}
