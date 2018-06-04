/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binarios;

/**
 *
 * @author Karlos
 */
public class DirectoryEntrie {
    private long rec_len;
    private int inodo;
    private int n_len;
    private String name;

    public DirectoryEntrie() {
    }

    public DirectoryEntrie(int inodo, String name) {
        this.inodo = inodo;
        this.rec_len = 18+name.length();
        this.n_len = name.length();
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
