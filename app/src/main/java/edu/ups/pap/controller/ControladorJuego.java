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
        timer.start();
    }

    public void pausarJuego(){
        partida.pausar();
        timer.stop();
    }
    public void reanudarJuego(){
        partida.reanudar();
        timer.start();
    }
    public void reiniciarJuego(){
        partida.reiniciar();
        timer.start();
    }
    public void moverSerpiente(Direccion direccion){
        partida.manejarEntrada(direccion);
        
    }
    public void actualizarJuego(){
        partida.actualizar();
        if(partida.estaTerminada()){
            timer.stop();
        }
    }

    



    
    
}
