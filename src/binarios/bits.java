package binarios;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import static java.lang.Math.log;
import static java.lang.StrictMath.log;
import static java.rmi.server.LogStream.log;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

public class bits {

    private String ruta_archivo = "disco.bin";
    private RandomAccessFile archivo = null;
    private byte[] disco =  new byte[268393904];

    public bits() {
    }
    
    
    public void Crear_Disco() throws FileNotFoundException, IOException
    {
     archivo = new RandomAccessFile("disco.bin", "rw");
     archivo.seek(0);
         archivo.write(disco);
         
         archivo.seek(0);
     for(int i = 0;i<65536;i++){
         archivo.writeBoolean(true);
     }
     //inicia bitmap de bloques
     archivo.seek(0);
     //aqui se reservan los primeros bloques para la estructura del fs
     for(int i = 0;i<38;i++){
         archivo.writeBoolean(false);
     }
     //inicia bloque de bitsmap de inodo
     archivo.seek(65537);
     for(int i = 0;i<1024;i++){
         archivo.writeBoolean(true);
     }
     // se reserva el primer inodo para el directorio Raiz
     archivo.seek(65537);
     archivo.writeBoolean(false);
     //inicia bloque de tabla de inodos
     archivo.seek(69633);
     for(int i = 0;i<1024;i++){
         archivo.writeInt(0);
         archivo.writeInt(0);
         archivo.writeInt(0);
         archivo.writeInt(0);
         archivo.writeInt(0);
         archivo.writeInt(0);
         archivo.writeInt(0);
         archivo.writeInt(0);
         archivo.writeInt(0);
         archivo.writeInt(0);
         archivo.writeInt(0);
         archivo.writeInt(0);
         archivo.writeInt(0);
         archivo.writeInt(0);
         archivo.writeInt(0);
         archivo.writeInt(0);
         archivo.writeInt(0);
         archivo.writeInt(0);
         archivo.writeInt(0);
         archivo.writeInt(0);
     }
     // se graba el primer inodo
     Inodo inodo = new Inodo(1,0,0,37,0,0,0,0,0,0,0,0,0,0,0,0);
     archivo.seek(69633);
        archivo.writeInt(1);
         archivo.writeInt(0);
         archivo.writeInt(0);
         archivo.writeInt(37);
         archivo.writeInt(0);
         archivo.writeInt(0);
         archivo.writeInt(0);
         archivo.writeInt(0);
         archivo.writeInt(0);
         archivo.writeInt(0);
         archivo.writeInt(0);
         archivo.writeInt(0);
         archivo.writeInt(0);
         archivo.writeInt(0);
         archivo.writeInt(0);
         archivo.writeInt(0);
         archivo.writeInt(inodo.getI_ctime());
         archivo.writeInt(inodo.getI_time());
         archivo.writeInt(inodo.getMtime());
         archivo.writeInt(inodo.getI_dtime());
     
     //inicia bloques de datos
     //archivo.seek(151552);
         //archivo.write(disco);
     //se graba la primer directoryEntrie
        archivo.seek(151552);
        DirectoryEntrie direntrie = new DirectoryEntrie(0,"$");
        archivo.writeInt(direntrie.getInodo());
        archivo.writeInt(direntrie.getN_len());
        archivo.writeLong(direntrie.getRec_len());
        archivo.writeUTF(direntrie.getName());
        archivo.close();
    }
    public long bloque_Libre() throws  FileNotFoundException, IOException{
        archivo = new RandomAccessFile("disco.bin", "rw");
        for(long i = 0;i<65536;i++){
            if(archivo.readBoolean()){
                archivo.close();
                return i;
            }
        }
        archivo.close();
        return -1;
    }
    public long inodoLibre()throws  FileNotFoundException, IOException{
        archivo = new RandomAccessFile("disco.bin", "rw");
        archivo.seek(65537);
     for(long i = 0;i<1024;i++){
         if(archivo.readBoolean()){
             archivo.close();
             return i;
         }
     }
        archivo.close();
        return -1;
    }
    public boolean existeNombre(long bloque, String nombre) throws FileNotFoundException, IOException{
        String name = "";
        archivo = new RandomAccessFile("disco.bin", "rw");
        archivo.seek(bloque*4096);
        archivo.readInt();
        archivo.readInt();
        long tam = archivo.readLong();
        
        while(tam > 0){
            name = archivo.readUTF();
            if(nombre.equals(name)){
                archivo.close();
                return true;
            }
            archivo.readInt();
            archivo.readInt();
            tam = archivo.readLong();
        }
        return false;
    }
    public boolean esDirectorio(int inodo) throws FileNotFoundException, IOException{
        archivo = new RandomAccessFile("disco.bin", "rw");
        archivo.seek(69633+(inodo*80));
        int resp = archivo.readInt();
        if(resp == 1){
            archivo.close();
            return true;
        }
        archivo.close();
        return false;
    }
    public ArrayList<DirectoryEntrie> leerBloqueDirEntrie(long bloque) throws FileNotFoundException, IOException{
        archivo = new RandomAccessFile("disco.bin", "rw");
            //archivo.seek((bloque*4096));
            ArrayList<DirectoryEntrie> tabla = new ArrayList();
            int idi;
            int recl;
            long tamb;
            String nombre = "fff";
            long pos = (bloque*4096);
            long cent =0;
            do{
                
                archivo.seek(pos);
                idi = archivo.readInt();
                recl = archivo.readInt();
                tamb = archivo.readLong();
                nombre = archivo.readUTF();
                
                if(tamb != 0){
                    tabla.add(new DirectoryEntrie(idi,nombre));
                }
                pos+=tamb;
                cent+=8;
            }while(pos < (bloque*4096)+4096 && cent < 4096);
            archivo.close();
            return tabla;
    }
    
    public void ls(long bloque) throws FileNotFoundException, IOException{
        
        ArrayList<DirectoryEntrie> lista = this.leerBloqueDirEntrie(bloque);
        for(int i = 0;i<lista.size();i++){
            if(this.esDirectorio(lista.get(i).getInodo())){
                System.out.println(lista.get(i).getName());
            }else{
                System.out.println(lista.get(i).getName());
            }
        }
        
    }
    public Inodo getInodo(long inodo) throws FileNotFoundException, IOException{
        archivo = new RandomAccessFile("disco.bin", "rw");
        archivo.seek(69633+(inodo*80)); 
       Inodo deVuelta= new Inodo(archivo.readInt(),archivo.readInt(),archivo.readInt(),archivo.readInt(),archivo.readInt(),archivo.readInt(),archivo.readInt(),archivo.readInt(),archivo.readInt(),archivo.readInt(),archivo.readInt(),archivo.readInt(),archivo.readInt(),archivo.readInt(),archivo.readInt(),archivo.readInt(),archivo.readInt(),archivo.readInt(),archivo.readInt(),archivo.readInt());
       return deVuelta;
    }
        public void ls_l(long bloque) throws FileNotFoundException, IOException, ParseException{
        
        ArrayList<DirectoryEntrie> lista = this.leerBloqueDirEntrie(bloque);
        //DateFormat df = new SimpleDateFormat("yyyyMMdd");
        DateFormat df = new SimpleDateFormat("ddMMyyyy");
        for(int i = 0;i<lista.size();i++){
            if(this.esDirectorio(lista.get(i).getInodo())){
                System.out.println(lista.get(i).getName()+"     <DIR>       "+df.parse(String.valueOf(this.getInodo(lista.get(i).getInodo()).getI_ctime()))+"     "+lista.get(i).getRec_len());
            }else{
                System.out.println(lista.get(i).getName()+"     <Archivo/Link>       "+df.parse(String.valueOf(this.getInodo(lista.get(i).getInodo()).getI_ctime()))+"     "+this.getInodo(lista.get(i).getInodo()).getI_size());
            }
        }
        
    }
        public boolean escribeBloqueDirEntrie(long bloque, DirectoryEntrie dirEntrie) throws FileNotFoundException, IOException{
        archivo = new RandomAccessFile("disco.bin", "rw");
            
            int idi;
            int recl;
            long tamb;
            String nombre = "fff";
            
            long pos =  bloque*4096;
            int centinela = 0;
            do{
                archivo.seek(pos);
                
                idi = archivo.readInt();
                recl = archivo.readInt();
                tamb = archivo.readLong();
                nombre = archivo.readUTF();
                
                if( tamb == 0 && pos < (bloque*4096)+4096){
                    //System.out.println(pos);
                    archivo.seek(pos);
                    idi = dirEntrie.getInodo();
                    recl = dirEntrie.getN_len();
                    tamb = dirEntrie.getRec_len();
                    nombre = dirEntrie.getName();
                    
                    
                    archivo.writeInt(idi);
                    archivo.writeInt(recl);
                    archivo.writeLong(tamb);
                    archivo.writeUTF(nombre);
                    
                    return true;
                }
               pos+=tamb; 
               centinela+=8;
            }while(centinela < 4096);
            archivo.close();
            return false;
    }
    
    public void mkdir(long bloque,String nombre) throws IOException{
        long il = this.inodoLibre();
        long bl = this.bloque_Libre();
        if(!this.existeNombre(bloque, nombre)){
             //archivo.seek((bloque*4096));
                
               DirectoryEntrie direntrie = new DirectoryEntrie((int) il,nombre);
               this.escribeBloqueDirEntrie( bloque, direntrie);
               this.escribeBloqueDirEntrie( bl, direntrie);
               //System.out.println(bl);
               
                archivo = new RandomAccessFile("disco.bin", "rw");
                
                //modifico los bitmaps
                archivo.seek(65537+(il*80));
                archivo.writeBoolean(false);
                archivo.seek(0+bl);
                archivo.writeBoolean(false);
                
                //modifica el inodo de nuevo
                
                Inodo inodo = new Inodo(1,0,0, (int) bl,0,0,0,0,0,0,0,0,0,0,0,0);
                archivo.seek(69633+(il*80));
                archivo.writeInt(1);
                archivo.writeInt(0);
                archivo.writeInt(0);
                archivo.writeInt(inodo.getI_blocks1());
                archivo.writeInt(0);
                archivo.writeInt(0);
                archivo.writeInt(0);
                archivo.writeInt(0);
                archivo.writeInt(0);
                archivo.writeInt(0);
                archivo.writeInt(0);
                archivo.writeInt(0);
                archivo.writeInt(0);
                archivo.writeInt(0);
                archivo.writeInt(0);
                archivo.writeInt(0);
                archivo.writeInt(inodo.getI_ctime());
                archivo.writeInt(inodo.getI_time());
                archivo.writeInt(inodo.getMtime());
                archivo.writeInt(inodo.getI_dtime());
        }
        archivo.close();
    }
    public Date convertIntToDate(Integer intDate) {

    if (intDate < 100000 || intDate > 999999) {
        //log.warn("Unable to parse int date {}", intDate);
        return null;
    }

    int intYear = intDate/100;
    int intMonth = intDate - (intYear * 100);

    Calendar result = new GregorianCalendar();
    result.set(intYear, intMonth - 1, 1, 0, 0, 0);

    return result.getTime();
}
    
    
}
