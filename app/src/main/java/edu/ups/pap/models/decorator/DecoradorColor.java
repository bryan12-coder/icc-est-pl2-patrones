package edu.ups.pap.models.decorator;

import java.awt.Color;
import java.awt.Graphics;

import edu.ups.pap.models.Posicion;

public class DecoradorColor extends DecoradorApariencia {
    private Color color;

    public DecoradorColor(Apariencia apariencia) {
        this(apariencia, Color.GREEN);
    }

    public DecoradorColor(Apariencia apariencia, Color color) {
        super(apariencia);
        this.color = color;
    }

    @Override
    public void dibujar(Graphics g, Posicion p) {// Metodo pa dibujar
        super.dibujar(g, p);
        g.setColor(color);
        g.fillRect(p.getX() * 20, p.getY() * 20, 20, 20);
    }

}
