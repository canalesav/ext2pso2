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
   
}