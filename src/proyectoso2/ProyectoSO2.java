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
    private static int bloqueActual = 34; 
    private static int inodoActual = 0;
    private static bits b = new bits();
    private static RandomAccessFile flujo;
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        // TODO code application logic here
            Shell shell = new Shell();
            shell.start();
    }
    
}
