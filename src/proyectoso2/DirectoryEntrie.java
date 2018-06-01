/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoso2;

/**
 *
 * @author Karlos
 */
public class DirectoryEntrie {
    private int inodo;
    private int n_len;
    private long rec_len;
    private String name;

    public DirectoryEntrie() {
    }

    public DirectoryEntrie(int inodo, int n_len,long rec_len, String name) {
        this.inodo = inodo;
        this.rec_len = rec_len+2;
        this.n_len = n_len;
        this.name = name;
    }

    public int getInodo() {
        return inodo;
    }

    public long getRec_len() {
        return rec_len;
    }

    public int getN_len() {
        return n_len;
    }

    public String getName() {
        return name;
    }

    public void setInodo(int inodo) {
        this.inodo = inodo;
    }

    public void setRec_len(long rec_len) {
        this.rec_len = rec_len;
    }

    public void setN_len(int n_len) {
        this.n_len = n_len;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
    
    
}
