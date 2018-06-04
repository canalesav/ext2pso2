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
                    if(Comando[0].equals("cd")){
                        String[] r =b.cd(bloqueActual, Comando[1]);
                        if(r[0].equals(Comando[1])){
                            currentPath.add(Comando[1]);
                            InodosAnteriores.add(inodoActual);
                            inodoActual=Long.parseLong(r[1]);
                            bloquesAnteriores.add(bloqueActual);
                            System.out.println(Long.parseLong(r[2]));
                            bloqueActual = Long.parseLong(r[2]);
                        }else{
                            if(Comando[1].equals("..")){
                                inodoActual = InodosAnteriores.get(InodosAnteriores.size()-1);
                                InodosAnteriores.remove(InodosAnteriores.size()-1);
                                bloqueActual = bloquesAnteriores.get(bloquesAnteriores.size()-1);
                                bloquesAnteriores.remove(bloquesAnteriores.size()-1);
                                currentPath.remove(currentPath.size()-1);
                            }else{
                                System.out.println(r[0]);
                            }
                        }
                    }
                    if(Comando[0].equals("rmdir")){
                        b.rmdir(bloqueActual, Comando[1]);
                    }
                    if(Comando[0].equals("vim")){
                        leer = new Scanner(System.in);
                     entrada = leer.nextLine();
                        b.vim(bloqueActual, Comando[1], entrada);
                    }
                    if(Comando[0].equals("cat")){
                        if(Comando.length == 3){
                            System.out.println(b.cat(Comando[1], bloqueActual));
                            System.out.println(b.cat(Comando[2], bloqueActual));
                        }else{
                            System.out.println(b.cat(Comando[1], bloqueActual));
                        }
                    }
            
            }while(centinela);
    }

}
