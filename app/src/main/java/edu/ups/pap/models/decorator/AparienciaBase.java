package edu.ups.pap.models.decorator;

import java.awt.Color;
import java.awt.Graphics;



import edu.ups.pap.models.Posicion;

public class AparienciaBase implements Apariencia {

    @Override
    public void dibujar(Graphics g, Posicion p) {
       g.setColor(Color.GREEN);
       g.fillRect(p.getX() * 20, p.getY() * 20, 20, 20);
    }
    
}
