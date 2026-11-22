package edu.ups.pap.models.abstractFactory;

import edu.ups.pap.models.Comida;
import edu.ups.pap.models.Posicion;

public class FabricaComidaRapida implements FabricaComida {

    @Override
    public Comida crearComidaNormal(Posicion p) {
        return new ComidaNormal(p);
    }

    @Override
    public Comida crearComidaEspecial(Posicion p) {
        return new ComidaVelocidad(p);
    }
    
}
