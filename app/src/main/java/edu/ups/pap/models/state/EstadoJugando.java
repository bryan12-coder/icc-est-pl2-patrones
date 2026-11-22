package edu.ups.pap.models.state;

import edu.ups.pap.models.Direccion;
import edu.ups.pap.models.Partida;

public class EstadoJugando implements EstadoPartida {

    @Override
    public void actualizar(Partida partida) {
        partida.actualizarJuego();
    }

    @Override
    public void manejarEntrada(Partida partida, Direccion direccion) {
        partida.getSerpiente().cambiarDireccion(direccion);
    }

    @Override
    public void pausar(Partida partida) {
        partida.cambiarEstado(new EstadoPausado());
    }

    @Override
    public void iniciar(Partida partida) {
    }

    @Override
    public void reanudar(Partida partida) {
    }

    @Override
    public void reiniciar(Partida partida) {
        partida.reiniciarDatos();
        partida.cambiarEstado(new EstadoJugando());
    }
}
