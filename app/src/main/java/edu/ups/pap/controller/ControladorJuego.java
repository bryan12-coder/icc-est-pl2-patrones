package edu.ups.pap.controller;

import javax.swing.Timer;

import edu.ups.pap.models.Direccion;
import edu.ups.pap.models.Partida;

public class ControladorJuego {
    private Partida partida;
    private Timer timer;

    public ControladorJuego(Partida partida) {
        this.partida = partida;
        timer= new Timer(200, e-> actualizarJuego());
    }

    public void iniciarJuego(){
        partida.iniciar();
    }

    public void pausarJuego(){
        partida.pausar();
    }
    public void reanudarJuego(){
        partida.reanudar();
    }
    public void reiniciarJuego(){
        partida.reiniciar();
    }
    public void moverSerpiente(Direccion direccion){
        partida.manejarEntrada(direccion);
        
    }
    public void actualizarJuego(){
        partida.actualizar();
    }

    



    
    
}
