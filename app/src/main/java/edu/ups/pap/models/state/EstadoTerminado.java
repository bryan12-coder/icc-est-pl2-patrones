package edu.ups.pap.models.state;

import edu.ups.pap.models.Direccion;
import edu.ups.pap.models.Partida;

public class EstadoTerminado implements EstadoPartida{

    @Override
    public void iniciar(Partida partida) {
        System.out.println("La partida terminó. Debes reiniciarla");
        
    }

    @Override
    public void pausar(Partida partida) {
        System.out.println("No se puede pausar una partida terminada");
       
    }

    @Override
    public void reanudar(Partida partida) {
        System.out.println("No se puede reanudar una partida terminada");
       
    }

    @Override
    public void reiniciar(Partida partida) {
       partida.reiniciarDatos();
        partida.cambiarEstado(new EstadoPreparado());
    }

    @Override
    public void actualizar(Partida partida) {
       
    }

    @Override
    public void manejarEntrada(Partida partida, Direccion direccion) {
       
    }
    
}
