package edu.ups.pap.models.abstractFactory;

import edu.ups.pap.models.Comida;
import edu.ups.pap.models.Posicion;

public interface FabricaComida {
    
    Comida crearComidaNormal(Posicion p);
    Comida crearComidaEspecial(Posicion p);

    
} 
