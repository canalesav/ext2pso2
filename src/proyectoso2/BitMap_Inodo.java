/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoso2;

import java.io.Serializable;

/**
 *
 * @author Karlos
 */
public class BitMap_Inodo implements Serializable{
    private boolean bitmapInodo[];
    private Inodo actual;
    private Inodo nuevo;
    
    public BitMap_Inodo() {
        this.bitmapInodo =  new boolean[1024];
        for (short i = 0; i<1024;i++){
            this.bitmapInodo[i] = true;
        }
    }

    public Inodo getActual() {
        return actual;
    }

    public void setActual(Inodo actual) {
        this.actual = actual;
    }
    public short Inodo_libre(){
        for(short i = 0 ;i<1024;i++){
            if(this.bitmapInodo[i]){
                return i;
            }
        }
        return -1;
    }

    public void setBitMapINODO(boolean resp, int id){
        this.bitmapInodo[id] = resp;
    }
    public boolean getBitMapINODO(int id){
        return this.bitmapInodo[id];
    }
}