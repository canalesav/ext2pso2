/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoso2;

import java.io.Serializable;
import static java.lang.Math.toIntExact;

/**
 *
 * @author Karlos
 */
public class Inodo implements Serializable{
    private short i_mode;
    //1 byte
    private long i_size;
    //2 byte
    private short i_link_count;
    //2 byte
    private int i_time;
    //4 byte
    private int i_ctime;
    //4 byte
    private int mtime;
    //4 byte
    private int i_dtime;
    //4 byte
    private int i_blocks[];
    //48 byte
    
    
    public Inodo() {}
    
    public Inodo(short i_mode) {
        this.i_mode = i_mode;
        //this.id_inodo = id_inode;
        if(i_mode == 3){
            this.i_link_count++;
        }else{
            this.i_link_count = 0;
        }
        
        
        this.i_ctime = this.i_time = this.mtime = toIntExact(System.currentTimeMillis() / 1000);
    }
    
    public Inodo(short i_mode,String Datos, int[] bloques) {
        this.i_mode = i_mode;
        //this.id_inodo = id_inode;
        this.i_size = Datos.length();
        if(i_mode == 3){
            this.i_link_count++;
        }else{
            this.i_link_count = 0;
        }
        int seg =  0;
        if(i_mode == 2 && Datos.length()<49152){
            
            for(int i=0;i<12;i++){
                do{
                    i_blocks[i] = bloques[i];
                    String Procion = Datos.substring(seg, seg+4095);
                    //grabarBloque(bloques[i],Porcion);
                    seg+=4096;
                }while(seg<49152);
            }
            do{
                String Procion = Datos.substring(seg, seg+4095);
                    i_blocks[12] = bloques[12];
                    //grabarDireccionSinmpleIndirecto(bloque,Direccion,Porcion);
                seg+=4096;
            }while(seg>49151 && seg<524288);
            do{
                String Procion = Datos.substring(seg, seg+4095);
                    i_blocks[13] = bloques[13];
                    //grabarDireccionSinmpleIndirecto(bloque,Direccion,Porcion);
                seg+=4096;
            }while(seg>524288 && seg<1048576);
            do{
                String Procion = Datos.substring(seg, seg+4095);
                    i_blocks[13] = bloques[13];
                    //grabarDireccionSinmpleIndirecto(bloque,Direccion,Porcion);
                seg+=4096;
            }while(seg>1048576 && seg<1572864);
            do{
                String Procion = Datos.substring(seg, seg+4095);
                    i_blocks[14] = bloques[14];
                    
                    //grabarDireccionSinmpleIndirecto(bloque,Direccion,Porcion);
                seg+=4096;
            }while(seg>1572864 && seg<2097152);
        }else{
            
        }
        
        this.i_ctime = this.i_time = this.mtime = toIntExact(System.currentTimeMillis() / 1000);
    }
    
    /*public Inodo(short id_inode) {
        //this.id_inodo = id_inode;
        this.i_ctime = this.i_time = this.mtime = toIntExact(System.currentTimeMillis() / 1000);
    }*/
    
    
    public void setId_inodo(short id_inodo) {
        //this.id_inodo = id_inodo;
    }
    
    /*public short getId_inodo() {
        return id_inodo;
    }*/

    
    public short getI_mode() {
        return i_mode;
    }

    public long getI_size() {
        return i_size;
    }

    public short getI_link_count() {
        return i_link_count;
    }

    public int getI_time() {
        return i_time;
    }

    public int getI_ctime() {
        return i_ctime;
    }

    public int getMtime() {
        return mtime;
    }

    public int getI_dtime() {
        return i_dtime;
    }

    public int[] getI_blocks() {
        return i_blocks;
    }

    public void setI_mode(byte i_mode) {
        this.i_mode = i_mode;
    }

    public void setI_size(long i_size) {
        this.i_size = i_size;
    }

    public void setI_link_count(short i_link_count) {
        this.i_link_count = i_link_count;
    }

    public void setI_time(int i_time) {
        this.i_time = i_time;
    }

    public void setI_ctime(int i_ctime) {
        this.i_ctime = i_ctime;
    }

    public void setMtime(int mtime) {
        this.mtime = mtime;
    }

    public void setI_dtime(int i_dtime) {
        this.i_dtime = i_dtime;
    }

    public void setI_blocks(int[] i_blocks) {
        this.i_blocks = i_blocks;
    }
    
    
}
