package edu.ups.pap.models;

import java.util.ArrayList;

public class Serpiente {
    private ArrayList<Posicion> segmentos;
     private Direccion direccionActual;


    public Serpiente() {
        segmentos=new ArrayList<>();
        segmentos.add(new Posicion(5, 3)); // Cabeza
        segmentos.add(new Posicion(4, 3)); // Cuerpo
        segmentos.add(new Posicion(3, 3)); // Cola
        direccionActual = Direccion.DERECHA;
    }

    public Posicion getCabeza(){
        return segmentos.get(0);
    }
    public void cambiarDireccion(Direccion nuevaDireccion) {
        if(!direccionActual.Opuesta(nuevaDireccion)){
            direccionActual = nuevaDireccion;
        }
    }

    public void mover(boolean crecer) {
      Posicion nuevaCabeza = getCabeza().mover(direccionActual);
     segmentos.add(0, nuevaCabeza);
       segmentos.remove(segmentos.size() - 1);
       if(!crecer){
        segmentos.remove(segmentos.size() - 1);
       }
    }
    public boolean chocaConCuerpo(Posicion posicion,boolean crecer) {
         int limite = segmentos.size();

        if (!crecer) {
           limite--;
    }
        for (int i = 1; i < limite; i++) {
            Posicion segmento = segmentos.get(i);
            if (segmento.getX() == posicion.getX() &&
                segmento.getY() == posicion.getY()) {

               return true;
            }

        
        }

        return false;


    }
    public int getLongitud(){
        return segmentos.size();
        
    }
    public boolean mismaPosicion(Posicion posicion1){
         for (int i = 0; i < segmentos.size(); i++) {
        Posicion segmento = segmentos.get(i);

        if(segmento.getX()== posicion1.getX()&& segmento.getY()==posicion1.getY()){
            return true;
        }

        // Aquí comprobaremos las coordenadas
        }  
        return false;


    }
    public Direccion getDireccionActual() {
    return direccionActual;
    }
    public ArrayList<Posicion> getSegmentos() {
        ArrayList<Posicion> copia = new ArrayList<>();
        for (Posicion segmento : segmentos) {
        copia.add(new Posicion(segmento.getX(), segmento.getY()));
       
        }
        return copia;

    
    }

     
}
