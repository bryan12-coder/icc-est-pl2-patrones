package edu.ups.pap.models.state;

import edu.ups.pap.models.Direccion;
import edu.ups.pap.models.Partida;

public class EstadoPreparado implements EstadoPartida{

    @Override
    public void iniciar(Partida partida) {
        partida.cambiarEstado(new EstadoJugando());
       
    }

    @Override
    public void pausar(Partida partida) {
        System.out.println("No puedes pausar una partida que no inicia");
        
    }

    @Override
    public void reanudar(Partida partida) {
        System.out.println("Error no se puede reanudar una paetida que no se ha comenzado");
       
    }

    @Override
    public void reiniciar(Partida partida) {
        partida.reiniciar();
        
        
    }

    @Override
    public void actualizar(Partida partida) {
       //No se puede actualizar porque la partida no inicia
    }

    @Override
    public void manejarEntrada(Partida partida, Direccion direccion) {
       // No hacemos nada ya que no vamos a procesar las direcciones si practicamente la serpiente esta quieta
        
    }
    
}
