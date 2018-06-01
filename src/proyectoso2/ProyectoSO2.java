/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoso2;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
/**
 *
 * @author Karlos
 */
public class ProyectoSO2 {

    /**
     * @param args the command line arguments
     */
    private static BitMap_Inodo bmInodo = new BitMap_Inodo();
    private static BitMapBLOCK bmBloque = new BitMapBLOCK();
    private static tabla_Inodo tablaInodo = new tabla_Inodo();
    private static int bloqueActual = 33; 
    private static int inodoActual = 0;
    private static bits b = new bits();
    private static RandomAccessFile flujo;
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        // TODO code application logic here
            File fichero = new File("disco.bin");
            if(!fichero.exists()){
                b.Crear_Disco();
            }
            b.leer();
            
            String pathActual = "$";
            bmInodo = b.getBMInodo();
            bmBloque = b.getBMBloque();
            tablaInodo = b.getInodoTabla();
            
            mkdir("holamas");
            ls();
            //bmInodo.Inodo_libre();
        
    }
    public static void ls() throws IOException{
        ArrayList<DirectoryEntrie> tabla = new ArrayList();
        tabla = b.leerBloqueDirEntrie(bloqueActual);
        int centinela = 0;
        for(int i =0; i<tabla.size() -1;i++){
            System.out.print("    "+tabla.get(i).getName()+"  ");
            if(centinela == 5){
                System.out.println();
                centinela = 0;
            }
            centinela++;
        }
    }
    public static void mkdir(String Nombre) throws IOException{
        int bloqueLibre = bmBloque.getLibre();
        short inodoLibre = bmInodo.Inodo_libre();
        short mode = 1;
        int[] bloques = new int[15];
        
        if(bloqueLibre != -1 && inodoLibre != -1){
            bloques[0] =bloqueLibre;
            Inodo inodo = new Inodo(mode,"",bloques);
            DirectoryEntrie dirent = new DirectoryEntrie(inodoLibre,Nombre.length(),16+Nombre.length(),Nombre);
            
            //b.escribeDirectoryEntry(bloqueLibre, inodoLibre,Nombre+"/n"); b.escribeDirectoryEntry(bloqueActual, inodoActual,Nombre+"/n");
            if(b.escribeBloqueDirEntrie(bloqueLibre, dirent) && b.escribeBloqueDirEntrie(bloqueActual, dirent)){
                
                bmBloque.setBitMap(false, bloqueLibre);
                bmInodo.setBitMapINODO(false, inodoLibre);
                tablaInodo.setInodoEntry(inodoLibre, inodo);
                //b.escribeDirectoryEntry(bloqueLibre, inodoLibre, Nombre);
                b.guardaCambiosEstructura();  
            }else{
                System.out.println("el comando fallo");
            }
            
        }
    }
    
}
