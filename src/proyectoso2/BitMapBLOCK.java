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
public class BitMapBLOCK implements Serializable  {
    private boolean[] bitmap;

    public BitMapBLOCK(boolean[] bitmap) {
        this.bitmap = bitmap;
    }
    public BitMapBLOCK() {
        this.bitmap = new boolean[65536];
        for(int i = 0; i<65536; i++){
            this.bitmap[i] = true;
        }
    }
    
    public void setBitMap(boolean resp, int id){
        this.bitmap[id] = resp;
    }
    
    public boolean getBitMap(int id){
        return this.bitmap[id];
    }
    public int getLibre(){
        for(int i = 0; i<65536; i++){
            if(this.bitmap[i]){
                return i;
            }
        }
        return -1;
    }
}
