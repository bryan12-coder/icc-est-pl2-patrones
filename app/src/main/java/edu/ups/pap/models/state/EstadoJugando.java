package edu.ups.pap.models.state;

import edu.ups.pap.models.Direccion;
import edu.ups.pap.models.Partida;

public class EstadoJugando implements EstadoPartida {

    @Override
    public void iniciar(Partida partida) {
        System.out.println("No se puede iniciar una partida que ya esta iniciada");
       
    }

    @Override
    public void pausar(Partida partida) {
       partida.cambiarEstado(new EstadoPausado());
        
    }

    @Override
    public void reanudar(Partida partida) {
        System.out.println("La partida esta en juego");
    }

    @Override
    public void reiniciar(Partida partida) {
         partida.reiniciarDatos();
        partida.cambiarEstado(new EstadoPreparado());
       
       
    }

    @Override
    public void actualizar(Partida partida) {
        partida.actualizarJuego();
        
        
    }

    @Override
    public void manejarEntrada(Partida partida, Direccion direccion) {
        partida.getSerpiente().cambiarDireccion(direccion);

        
    }
    
}
