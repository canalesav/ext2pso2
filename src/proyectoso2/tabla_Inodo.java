/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoso2;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;

/**
 *
 * @author Karlos
 */
public class tabla_Inodo implements Serializable{
    private Inodo table[];
    private Inodo actual;
    private Inodo nuevo;
    
    public tabla_Inodo() {
        this.table = new Inodo[1024];
        for (short i = 0; i<1024;i++){
            this.nuevo = new Inodo();
            this.table[i] = this.nuevo;
        }
    }
    public Inodo getInodo(int id){
        return table[id];
    }
    public Inodo[] getInodos(){
        return this.table;
    }
    public void setInodo(Inodo in, int id){
        table[id] = in;
    }

    public Inodo[] getTable() {
        return table;
    }

    public Inodo getActual() {
        return actual;
    }

    public void setTable(Inodo[] table) {
        this.table = table;
    }

    public void setActual(Inodo actual) {
        this.actual = actual;
    }
   
    public Inodo getInodoEntry(int id){
        return this.table[id];
    }
    public void setInodoEntry(int id,Inodo inodo){
        this.table[id] = inodo;
    }
    
    public void write(RandomAccessFile raf) throws IOException {
        for (int i = 0; i < table.length; i++) {
            Inodo inode = new Inodo();
            if(table[i] != null)
                inode = table[i];
            raf.writeLong(inode.getI_mode());
            raf.writeLong(inode.getI_size());
            raf.writeByte(inode.getI_link_count());
            raf.writeByte(inode.getI_time());
            raf.writeByte(inode.getI_ctime());
            raf.writeByte(inode.getMtime());
            raf.writeByte(inode.getI_dtime());
            if(inode.getI_blocks() != null)
            for (int j = 0; j < inode.getI_blocks().length; j++) {
                raf.writeByte(inode.getI_blocks()[j]);
            }
        }
    }
    
    public void read(RandomAccessFile raf) throws IOException {
        System.out.println("LEYENDO");
        for (int i = 0; i < table.length; i++) {
            table[i].setI_mode(raf.readByte());
            table[i].setI_size(raf.readLong());
            table[i].setI_link_count(raf.readByte());
            table[i].setI_time(raf.readByte());
            table[i].setI_ctime(raf.readByte());
            table[i].setMtime(raf.readByte());
            table[i].setI_dtime(raf.readByte());
            if(table[i].getI_blocks() != null)
            for (int j = 0; j < table[i].getI_blocks().length; j++) {
                System.out.println("Iblock = "+ table[i].getI_blocks()[j]);
                table[i].getI_blocks()[j] = raf.readByte();
            }
        }
    }
   
}