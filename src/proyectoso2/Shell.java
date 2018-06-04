/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoso2;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;
/**
 *
 * @author Karlos
 */
public class Shell {

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
    private String currentPath = "/";

    public Shell() throws FileNotFoundException, IOException, ClassNotFoundException {
        File fichero = new File("disco.bin");
        if(!fichero.exists()){
            b.setBloque(bmBloque);
            b.setInodo(bmInodo);
            b.setInodoTabla(tablaInodo);
            b.Crear_Disco();
        }
        b.leer();

        String pathActual = "$";
        bmInodo = b.getBMInodo();
        bmBloque = b.getBMBloque();
        tablaInodo = b.getInodoTabla();
        System.out.println("tablaInodo");
        imprimir(tablaInodo.getInodos());
        //System.out.println("");
        //System.out.println("--------------------------------------------------------------------");
        //ObjectInputStream file = new ObjectInputStream(new FileInputStream( "disco1111111.bin" ));
        //tabla_Inodo inodoTabla = (tabla_Inodo) file.readObject();
        //imprimir(inodoTabla.getInodos());
    }
    
    public void start() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String input, command;
        mainloop:
        for (; ; ) {
            System.out.printf("%n%s$ ", getCurrentPath());
            input = scanner.nextLine();
            command = input.split(" ")[0];
            switch (command) {
                case "ls": {
                    String opts[] = input.split(" ");
                    if (opts.length == 2) {
                        // ls -l
//                        if (opts[1].equals("-l"))
//                            lsExtended(fileSystem.getCurrentDirectory());
//                        else
//                            System.out.printf("Unknown option '%s'%n", opts[1]);
                    } else if (opts.length == 1) {
                        // ls
                        ls();
                    } else {
                        System.out.println("Invalid 'ls' usage. Use 'ls' or 'ls -l'");
                    }
                    break;
                }
//                case "cd": {                      fn+command+(/?)
//                    String opts[] = input.split(" ", 2);
//                    String path = (opts.length == 2) ? opts[1] : ".";
//                    try {
//                        cd(path);
//                    } catch (IOException ioe) {
//                        System.out.println("Unexpected IO Exception ocurred");
//                    }
//                    break;
//                }
//                case "cat": {
//                    if (input.contains(" > ")) {
//                        String opts[] = input.split(" > ");
//                        String fileName = opts[1].trim();
//                        if (Utils.containsIllegals(fileName)) {
//                            System.out.println("Illegal character found in the file name");
//                            break;
//                        }
//                        if (fileName.length() > 255) {
//                            System.out.println("Error: File name too long (a maximum of 255 characters is allowed");
//                            break;
//                        }
//                        String content = "";
//                        String line;
//                        while (!(line = scanner.nextLine()).equals("eof")) {
//                            content += line + "\n";
//                        }
//                        try {
//                            fileSystem.writeFile(fileName, content);
//                        } catch (IllegalArgumentException iae) {
//                            System.out.println(iae.getMessage());
//                        }
//                    } else if (input.contains(" >> ")) {
//                        String opts[] = input.split(">>");
//                        String fileName = opts[1].trim();
//                        String content = "";
//                        String line;
//                        while (!(line = scanner.nextLine()).equals("eof")) {
//                            content += line + "\n";
//                        }
//                        if (!fileSystem.append(fileName, content)) {
//                            System.out.println("The file was not found");
//                            break;
//                        }
//                    } else {
//                        String opts[] = input.split(" ", 2);
//                        if (opts.length == 2) {
//                            // cat file.txt
//                            String fileName = opts[1];
//                            cat(fileName);
//                        } else {
//                            System.out.println("Invalid 'cat' usage. Use 'cat <filename>' or 'cat > <filename>' or cat >> <filename>");
//                        }
//                    }
//                    break;
//                }
                case "mkdir": {
                    String opts[] = input.split(" ", 2);
                    String dirName = opts[1];
                    try {
                        mkdir(dirName);
                    } catch (IllegalArgumentException iae) {
                        System.out.println(iae.getMessage());
                    }
                    break;
                }
//
                case "rmdir": {
                    // . and .. can't be deleted
                    String opts[] = input.split(" ", 2);
                    if (opts.length == 2) {
                        String name = opts[1];
                        if (name.equals(".") || name.equals("..")) {
                            System.out.println("The system can't delete this directory");
                        } else {
                            try {
                                if (!rmdir(name)) {
                                    System.out.printf("The system could not find the directory '%s'%n", name);
                                }
                            } catch (IllegalArgumentException iae) {
                                System.out.println(iae.getMessage());
                            }
                        }
                    } else {
                        System.out.println("Invalid 'rmdir' usage. Use 'rmdir <directory name>'");
                    }
                    break;
                }
//
//                case "rm": {
//                    String opts[] = input.split(" ", 2);
//                    if (opts.length == 2) {
//                        String name = opts[1];
//                        if (!fileSystem.removeEntry(name)) {
//                            System.out.printf("The system could not find the file '%s'%n", name);
//                        }
//                    } else {
//                        System.out.println("Invalid 'rm' usage. Use 'rm <filename>'");
//                    }
//                    break;
//                }
//
//                case "ln": {
//                    String params[] = input.split(" ", 3);
//                    if (params[1].equals("-s")) {
//                        // ln -s (simbolic link)
//                        String paths[] = input.split(" -s ")[1].split(" ", 2);
//                        if (paths.length == 2) {
//                            String source = paths[0];
//                            String dest = paths[1];
//                            fileSystem.writeLink(source, dest, DirectoryEntry.SYM_LINK);
//                        } else {
//                            System.out.println("Invalid 'ln' usage. Use 'ln [-s] <source> <destination>' or 'ln <source> <destination>'");
//                        }
//                    } else {
//                        // ln (hard link)
//                        String paths[] = input.split(" ", 2)[1].split(" ", 2);
//                        String source = paths[0];
//                        String des = paths[1];
//                        fileSystem.writeLink(source, des, DirectoryEntry.HARD_LINK);
//                    }
//                    break;
//                }
                case "exit":
                    break mainloop;
                default:
                    System.out.printf("Unknown command '%s'%n", input.trim());
                    break;
            }
        }
    }
    
    public static void ls() throws IOException{
        ArrayList<DirectoryEntrie> tabla = new ArrayList();
        System.out.println("bloqueActual = "+bloqueActual);
        System.out.println("tabla = "+tabla.size());
        tabla = b.leerBloqueDirEntrie(bloqueActual);
        int centinela = 0;
        for(int i =0; i<tabla.size();i++){
            System.out.print("    i="+i+"\t"+tabla.get(i).getName()+"  ");
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
            if(b.escribeBloqueDirEntrie(bloqueLibre, dirent)/* && b.escribeBloqueDirEntrie(bloqueActual, dirent)*/){
                
                inodo.setI_blocks(bloques);
                for (int i = 0; i < bloques.length; i++) {
                    System.out.println("i) "+bloques[i]);
                }
                bmBloque.setBitMap(false, bloqueLibre);
                bmInodo.setBitMapINODO(false, inodoLibre);
                tablaInodo.setInodoEntry(inodoLibre, inodo);
                //ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream( "disco1111111.bin",true ));
                //file.writeObject(tablaInodo);
                b.setBloque(bmBloque);
                b.setInodo(bmInodo);
                b.setInodoTabla(tablaInodo);
                //imprimir(tablaInodo.getInodos());
                System.out.println("");System.out.println("");System.out.println("");
                System.out.println("inodo = " + inodo.getI_blocks().length);
                System.out.println("bloques = " + bloques.length);
                try {
                    //b.escribeDirectoryEntry(bloqueLibre, inodoLibre, Nombre);
                    b.guardaCambiosEstructura();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Shell.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Shell.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                System.out.println("el comando fallo");
            }
            
        }
//        System.out.println("Bloques");
//        System.out.println("Bloques");
//        imprimir(bmBloque.getBitMaps());
//        System.out.println("Inodos");
//        System.out.println("Inodos");
//        System.out.println("Inodos");
//        imprimir(bmInodo.getBitMapINODOs());
    }
    
    public static boolean rmdir(String Nombre) throws IOException{
        ArrayList<DirectoryEntrie> tabla = new ArrayList();
        tabla = b.buscarDirEntrie(Nombre);
        int centinela = 0;
        for(int i =0; i<tabla.size();i++){
            System.out.print("i)"+tabla.get(i).getName()+"\t"+tabla.get(i).getInodo());
            if(centinela == 5){
                System.out.println();
                centinela = 0;
            }
            centinela++;
        }
        return true;
        
//        int bloqueLibre = bmBloque.getLibre();
//        short inodoLibre = bmInodo.Inodo_libre();
//        short mode = 1;
//        int[] bloques = new int[15];
//        
//        if(bloqueLibre != -1 && inodoLibre != -1){
//            bloques[0] =bloqueLibre;
//            Inodo inodo = new Inodo(mode,"",bloques);
//            DirectoryEntrie dirent = new DirectoryEntrie(inodoLibre,Nombre.length(),16+Nombre.length(),Nombre);
//            
//            //b.escribeDirectoryEntry(bloqueLibre, inodoLibre,Nombre+"/n"); b.escribeDirectoryEntry(bloqueActual, inodoActual,Nombre+"/n");
//            if(b.escribeBloqueDirEntrie(bloqueLibre, dirent) && b.escribeBloqueDirEntrie(bloqueActual, dirent)){
//                
//                bmBloque.setBitMap(false, bloqueLibre);
//                bmInodo.setBitMapINODO(false, inodoLibre);
//                tablaInodo.setInodoEntry(inodoLibre, inodo);
//                //b.escribeDirectoryEntry(bloqueLibre, inodoLibre, Nombre);
//                b.guardaCambiosEstructura();  
//            }else{
//                System.out.println("el comando fallo");
//            }
//            
//        }
    }
    
    public String getCurrentPath() {
        return currentPath == null ? "/" : FilenameUtils.separatorsToUnix(currentPath);
    }
    
    public static void imprimir(Inodo[] lista) {
        for (int i = 0; i < lista.length; i++) {
            if(((Inodo)lista[i]).getI_blocks() != null){
                System.out.print(i+")"+((Inodo)lista[i]).getI_blocks().length+"\t" );
                for (int j = 0; j < ((Inodo)lista[i]).getI_blocks().length; j++) {
                    System.out.println("j\t"+j+((Inodo)lista[i]).getI_blocks()[j]);
                }
            }
            else
            System.out.print(i+")"+((Inodo)lista[i]).getI_blocks()+"\t" );
            if(i%15==0)
                System.out.println("");
        }
    }
    
}
