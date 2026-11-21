package edu.ups.pap.models.state;

import edu.ups.pap.models.Direccion;
import edu.ups.pap.models.Partida;

public interface EstadoPartida {
    
    void iniciar(Partida partida);

    void pausar(Partida partida);
    void reanudar(Partida partida);

    void reiniciar(Partida partida);

    void actualizar(Partida partida);

    void manejarEntrada(Partida partida, Direccion direccion);



    
}
