package edu.ups.pap.models.decorator;

import java.awt.Graphics;

import edu.ups.pap.models.Posicion;

public class DecoradorApariencia implements  Apariencia{
    private Apariencia apariencia;

    

    public DecoradorApariencia(Apariencia apariencia) {
        this.apariencia = apariencia;
    }



    @Override
    public void dibujar(Graphics g, Posicion p) {
        apariencia.dibujar(g, p);
        
    }
    
}
