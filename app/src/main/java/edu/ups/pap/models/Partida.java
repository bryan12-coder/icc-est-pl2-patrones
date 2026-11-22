package edu.ups.pap.models;

import edu.ups.pap.models.abstractFactory.ComidaDoble;
import edu.ups.pap.models.abstractFactory.ComidaVelocidad;
import edu.ups.pap.models.abstractFactory.FabricaComida;
import edu.ups.pap.models.abstractFactory.FabricaComidaClasica;
import edu.ups.pap.models.state.EstadoJugando;
import edu.ups.pap.models.state.EstadoPartida;
import edu.ups.pap.models.state.EstadoPausado;
import edu.ups.pap.models.state.EstadoPreparado;
import edu.ups.pap.models.state.EstadoTerminado;

public class Partida {
    private EstadoPartida estado;
    private Serpiente serpiente;
    private Tablero tablero;
    private Comida comida;
    private int puntaje;
    private boolean gano;
    private FabricaComida fabrica;
    private int velocidad = 200;

    


    public Partida() {
        this.estado= new EstadoPreparado();
        this.serpiente=new Serpiente();
        this.tablero= new Tablero(20, 15);
        this.fabrica = new FabricaComidaClasica();
        this.comida = fabrica.crearComidaNormal(new Posicion(10, 5));
        this.puntaje=0;
        this.gano=false;
    }

    public Serpiente getSerpiente() {
    return serpiente;
    }

    public void cambiarEstado(EstadoPartida nuevoEstado){
        this.estado=nuevoEstado;
    }
    public void iniciar(){
        estado.iniciar(this);
    }

    public void pausar(){
        estado.pausar(this);
    }
    public void reanudar(){
        estado.reanudar(this);
    }
    public void reiniciar(){
        estado.reiniciar(this);
    }
    public void actualizar(){
        estado.actualizar(this);

    }
    
    public void reiniciarDatos(){
         serpiente=new Serpiente();
         comida = fabrica.crearComidaNormal(new Posicion(10, 5));
         puntaje=0;
         gano = false;
         velocidad=200;

    }
    public void actualizarJuego() {//Pa que pueda avanzar, osea moverse
        Posicion lugar= serpiente.getCabeza().mover(serpiente.getDireccionActual());
        if(!tablero.dentro(lugar)){
            cambiarEstado(new EstadoTerminado());
            return;
        }
    
        boolean crecer;

        if(lugar.getX()== comida.getPosicion().getX() && lugar.getY()==comida.getPosicion().getY()){
            crecer= true;
        }else{
            crecer=false;
        }
         serpiente.mover(crecer);

        if (serpiente.chocaConCuerpo(lugar,crecer)) {
            
            cambiarEstado(new EstadoTerminado());
            return;
        }

        if(crecer){
             if (comida instanceof ComidaDoble) {
               puntaje += 20;
            } else {
               puntaje += 10;
            }
            if (comida instanceof ComidaVelocidad) {
                    velocidad = 150;
             }
            
            if (serpiente.getLongitud() == 300) {
                gano = true;
               cambiarEstado(new EstadoTerminado());
              return;
            }
            Posicion nuevaPosicion;
           
            do {
                int x = (int) (Math.random() * 20);
                int y = (int) (Math.random() * 15);

                nuevaPosicion = new Posicion(x, y);
                
            } while (serpiente.mismaPosicion(nuevaPosicion));

            double probabilidad = Math.random();

             if (probabilidad < 0.8) {
             comida = fabrica.crearComidaNormal(nuevaPosicion);
            } else {
               comida = fabrica.crearComidaEspecial(nuevaPosicion);
            }
           
           
            
        }
        

    }
    public void manejarEntrada(Direccion direccion){
        estado.manejarEntrada(this,direccion);

    }

    public int getPuntaje() {
        return puntaje;
    }

    public boolean getGano() {
    return gano;
    }
    public EstadoPartida getEstado() {
    return estado;
    }
    public boolean estaJugando() {
    return estado instanceof EstadoJugando;
    }
    public boolean estaPausada() {
    return estado instanceof EstadoPausado;
    }
    public boolean estaTerminada() {
    return estado instanceof EstadoTerminado;
    }
    public boolean estaPreparada() {
    return estado instanceof EstadoPreparado;
    }
    public Comida getComida() {
    return comida;
    }
    public int getVelocidad() {
    return velocidad;
    }
    
    
    
}
