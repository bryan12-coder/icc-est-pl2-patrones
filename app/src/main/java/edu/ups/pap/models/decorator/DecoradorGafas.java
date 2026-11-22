package edu.ups.pap.models.decorator;

import java.awt.Color;
import java.awt.Graphics;

import edu.ups.pap.models.Posicion;

public class DecoradorGafas extends DecoradorApariencia {

    public DecoradorGafas(Apariencia apariencia) {
        super(apariencia);
       
    }
    @Override
    public void dibujar(Graphics g, Posicion p) {
       super.dibujar(g, p);

       g.setColor(Color.BLACK);

        g.drawOval(p.getX() * 20 + 2, p.getY() * 20 + 3, 7, 7);
        g.drawOval(p.getX() * 20 + 11, p.getY() * 20 + 3, 7, 7);

        g.drawLine(p.getX() * 20 + 9, p.getY() * 20 + 6,
        p.getX() * 20 + 11, p.getY() * 20 + 6);
    }
    
}
