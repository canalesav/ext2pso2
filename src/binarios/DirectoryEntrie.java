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
    
    public void findEntry(String name) {
        /*for (Bloque_Directorio block : this) {
            for (Entrada_Directorio dirEntry : block) {
                if (dirEntry.getFilename().equals(name)) {
                    return dirEntry;
                }
            }
        }*/
  //      return null;
    }

    public void getBlockContaining(String name) {
        /*for (Bloque_Directorio block : this) {
            for (Entrada_Directorio entry : block) {
                if (entry.getFilename().equals(name)) {
                    return block;
                }
            }
        }*/
     //   return null;
    }

    // Returns the inode number of the "." dir_entry of this directory (self reference)
    public void getInode() {
        /*Bloque_Directorio firstBlock = this.get(0);
        Entrada_Directorio self = firstBlock.get(0);
        return self.getInode();*/
    }

    // Returns the inode number of the ".." dir_entry of this directory (parent reference)
    public void getParentInode() {
        /*Bloque_Directorio firstBlock = this.get(0);
        Entrada_Directorio parent = firstBlock.get(1);
        return parent.getInode();*/
    }

    public void getLastBlock() {
        /*return get(size() - 1);*/
    }
    
    
    
}
