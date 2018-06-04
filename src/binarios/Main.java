package binarios;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    private static bits b = new bits();
    private static long bloqueActual = 37;
    private static long inodoActual = 0;
    private static ArrayList<Long> bloquesAnteriores = new ArrayList();
    private static ArrayList<Long> InodosAnteriores = new ArrayList();
    private static ArrayList<String> currentPath = new ArrayList();
    private static boolean centinela = true;
    private static Scanner leer = null;
    public static void main(String[] args) throws IOException, FileNotFoundException, ParseException {
        File fichero = new File("disco.bin");
            if(!fichero.exists()){
                b.Crear_Disco();
            }
            do{
                for(int i =0;i<currentPath.size();i++){
                    System.out.print("/"+currentPath.get(i));
                }
                System.out.print("$");
                leer = new Scanner(System.in);
                    String entrada = leer.nextLine();
                    String[] Comando = entrada.split(" ");
                    
                    if(Comando[0].equals("ls")){
                        if(Comando.length >1){
                            if(Comando[1].equals("-l")){
                                b.ls_l(bloqueActual);
                            }
                        }else{
                            b.ls(bloqueActual);
                        }
                    }
                    if(Comando[0].equals("salir")){
                        centinela = false;
                    }
                    if(Comando[0].equals("mkdir")){
                        b.mkdir(bloqueActual, Comando[1]);
                    }
                    
            
            }while(centinela);
    }

}
