package edu.ups.pap.models;

public class Tablero {
    private  int ancho;
    private  int largo;


    public Tablero(int ancho, int largo) {
        this.ancho = ancho;
        this.largo = largo;
    }

    public boolean dentro(Posicion po){
        int x = po.getX();
         int y = po.getY();
        if(x>=0 && x<ancho && y>= 0 && y<largo){
            return true;
        }else{
            return false;
        }
    }


    
    
}
