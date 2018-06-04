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

    public String[] cd(long bloque,String nombre)throws FileNotFoundException, IOException{
        Inodo inodo = new Inodo();
        String[] respuesta= new String[3];
        
        archivo = new RandomAccessFile("disco.bin", "rw");
        archivo.seek(bloque*4096);
        int id = archivo.readInt();
        int tamn = archivo.readInt();
        long tam = archivo.readLong();
        String nom = archivo.readUTF();
        while(tam > 0){
            if(nombre.equals(nom)){
                archivo.close();
                inodo = this.getInodo(id);
                respuesta[0] = nombre;
                respuesta[1] = String.valueOf(id);
                respuesta[2] = String.valueOf(inodo.getI_blocks1());
                archivo.close();
                return respuesta;
            }
            id=archivo.readInt();
            archivo.readInt();
            tam = archivo.readLong();
            nom = archivo.readUTF();
        }
        archivo.close();
        
        
        respuesta[0] ="ni siquiera lo busca";
        return respuesta;
    }
    public void rmdir(long bloque,String nombre)throws FileNotFoundException, IOException{
        Inodo inodo = new Inodo();
        String respuesta= "a";
        
        archivo = new RandomAccessFile("disco.bin", "rw");
        archivo.seek(bloque*4096);
        int id = archivo.readInt();
        int tamn = archivo.readInt();
        long tam = archivo.readLong();
        String nom = archivo.readUTF();
        while(tam > 0){
            if(nombre.equals(nom)){
                long posicion = archivo.getFilePointer()-tam;
                archivo.seek(posicion);
                archivo.writeInt(0);
                archivo.writeInt(0);
                archivo.writeLong(0);
                for(int i = 0;i<tamn-1;i++){
                    respuesta = respuesta+"a";
                }
                archivo.writeUTF(respuesta);
                archivo.seek(posicion);
            }
            id=archivo.readInt();
            tamn = archivo.readInt();
            tam = archivo.readLong();
            nom = archivo.readUTF();
        }
        archivo.close();
    }
    public boolean vim(long bloque, String nombre, String datos) throws FileNotFoundException, IOException{
        long il = this.inodoLibre();
        long bl = this.bloque_Libre();
        int posI = 0;
        int posF = 0;
        ArrayList<Integer> bloquesUsados = new ArrayList();
        ArrayList<String> SegmentosDatos = new ArrayList();
        DirectoryEntrie direntrie = new DirectoryEntrie((int) il,nombre);
        this.escribeBloqueDirEntrie( bloque, direntrie);
        long tamañoArchivo = datos.length();
        archivo = new RandomAccessFile("disco.bin", "rw");
        archivo.seek(65537+il);
        archivo.writeBoolean(false);
        if(tamañoArchivo < 4096){
            SegmentosDatos.add(datos);
        }
        if(tamañoArchivo > 4096 && tamañoArchivo < 8192){
            SegmentosDatos.add(datos.substring(0,4095));
            SegmentosDatos.add(datos.substring(4095));
        }
        if(tamañoArchivo > 8192 && tamañoArchivo < 12288){
            SegmentosDatos.add(datos.substring(0,4095));
            SegmentosDatos.add(datos.substring(4095,8192));
            SegmentosDatos.add(datos.substring(8192));
        }
        if(tamañoArchivo > 12288 && tamañoArchivo < 16384){
            SegmentosDatos.add(datos.substring(0,4095));
            SegmentosDatos.add(datos.substring(4095,8192));
            SegmentosDatos.add(datos.substring(8192,12288));
            SegmentosDatos.add(datos.substring(12288));
        }
        if(tamañoArchivo > 16384 && tamañoArchivo < 20480){
            SegmentosDatos.add(datos.substring(0,4095));
            SegmentosDatos.add(datos.substring(4095,8192));
            SegmentosDatos.add(datos.substring(8192,12288));
            SegmentosDatos.add(datos.substring(12288,16384));
            SegmentosDatos.add(datos.substring(16384));
        }
        if(tamañoArchivo > 20480 && tamañoArchivo < 24576){
            SegmentosDatos.add(datos.substring(0,4095));
            SegmentosDatos.add(datos.substring(4095,8192));
            SegmentosDatos.add(datos.substring(8192,12288));
            SegmentosDatos.add(datos.substring(12288,16384));
            SegmentosDatos.add(datos.substring(16384,20480));
            SegmentosDatos.add(datos.substring(20480));
        }
        if(tamañoArchivo > 24576 && tamañoArchivo < 28672){
            SegmentosDatos.add(datos.substring(0,4095));
            SegmentosDatos.add(datos.substring(4095,8192));
            SegmentosDatos.add(datos.substring(8192,12288));
            SegmentosDatos.add(datos.substring(12288,16384));
            SegmentosDatos.add(datos.substring(16384,20480));
            SegmentosDatos.add(datos.substring(20480,24576));
            SegmentosDatos.add(datos.substring(24576));
        }
        if(tamañoArchivo > 28672 && tamañoArchivo < 32768){
            SegmentosDatos.add(datos.substring(0,4095));
            SegmentosDatos.add(datos.substring(4095,8192));
            SegmentosDatos.add(datos.substring(8192,12288));
            SegmentosDatos.add(datos.substring(12288,16384));
            SegmentosDatos.add(datos.substring(16384,20480));
            SegmentosDatos.add(datos.substring(20480,24576));
            SegmentosDatos.add(datos.substring(24576,28672));
            SegmentosDatos.add(datos.substring(28672));
        }
        if(tamañoArchivo > 32768 && tamañoArchivo < 36864){
            SegmentosDatos.add(datos.substring(0,4095));
            SegmentosDatos.add(datos.substring(4095,8192));
            SegmentosDatos.add(datos.substring(8192,12288));
            SegmentosDatos.add(datos.substring(12288,16384));
            SegmentosDatos.add(datos.substring(16384,20480));
            SegmentosDatos.add(datos.substring(20480,24576));
            SegmentosDatos.add(datos.substring(24576,28672));
            SegmentosDatos.add(datos.substring(28672,32768));
            SegmentosDatos.add(datos.substring(32768));
        }
        if(tamañoArchivo > 36864 && tamañoArchivo < 40960){
            SegmentosDatos.add(datos.substring(0,4095));
            SegmentosDatos.add(datos.substring(4095,8192));
            SegmentosDatos.add(datos.substring(8192,12288));
            SegmentosDatos.add(datos.substring(12288,16384));
            SegmentosDatos.add(datos.substring(16384,20480));
            SegmentosDatos.add(datos.substring(20480,24576));
            SegmentosDatos.add(datos.substring(24576,28672));
            SegmentosDatos.add(datos.substring(28672,32768));
            SegmentosDatos.add(datos.substring(32768,36864));
            SegmentosDatos.add(datos.substring(32768));
        }
        if(tamañoArchivo > 40960 && tamañoArchivo < 45056){
            SegmentosDatos.add(datos.substring(0,4095));
            SegmentosDatos.add(datos.substring(4095,8192));
            SegmentosDatos.add(datos.substring(8192,12288));
            SegmentosDatos.add(datos.substring(12288,16384));
            SegmentosDatos.add(datos.substring(16384,20480));
            SegmentosDatos.add(datos.substring(20480,24576));
            SegmentosDatos.add(datos.substring(24576,28672));
            SegmentosDatos.add(datos.substring(28672,32768));
            SegmentosDatos.add(datos.substring(32768,36864));
            SegmentosDatos.add(datos.substring(32768,40960));
            SegmentosDatos.add(datos.substring(40960));
        }
        if(tamañoArchivo > 45056 && tamañoArchivo < 49152){
            SegmentosDatos.add(datos.substring(0,4095));
            SegmentosDatos.add(datos.substring(4095,8192));
            SegmentosDatos.add(datos.substring(8192,12288));
            SegmentosDatos.add(datos.substring(12288,16384));
            SegmentosDatos.add(datos.substring(16384,20480));
            SegmentosDatos.add(datos.substring(20480,24576));
            SegmentosDatos.add(datos.substring(24576,28672));
            SegmentosDatos.add(datos.substring(28672,32768));
            SegmentosDatos.add(datos.substring(32768,36864));
            SegmentosDatos.add(datos.substring(32768,40960));
            SegmentosDatos.add(datos.substring(40960,45056));
            SegmentosDatos.add(datos.substring(45056));
        }
           
        for(int i = 0;i<SegmentosDatos.size();i++){                               
            
            archivo.seek(bl);
            archivo.writeBoolean(false);
            bloquesUsados.add((int)bl);
            archivo.seek(bl*4096);
            archivo.writeUTF(SegmentosDatos.get(i));
            bl = this.bloque_Libre();
        }
        for(int d =bloquesUsados.size();d<12;d++ ){
            bloquesUsados.add(0);
        }
        Inodo inodo = new Inodo(2, (int) tamañoArchivo,0,bloquesUsados.get(0),bloquesUsados.get(1),bloquesUsados.get(2),bloquesUsados.get(3),bloquesUsados.get(4),bloquesUsados.get(5),bloquesUsados.get(6),bloquesUsados.get(7),bloquesUsados.get(8),bloquesUsados.get(9),bloquesUsados.get(10),bloquesUsados.get(11),0);
        System.out.println(il);
        archivo = new RandomAccessFile("disco.bin", "rw");
        archivo.seek(65537+il);
        archivo.seek(69633+(il*80));
                archivo.writeInt(2);
                archivo.writeInt(inodo.getI_size());
                archivo.writeInt(0);
                archivo.writeInt(inodo.getI_blocks1());
                archivo.writeInt(inodo.getI_blocks2());
                archivo.writeInt(inodo.getI_blocks3());
                archivo.writeInt(inodo.getI_blocks4());
                archivo.writeInt(inodo.getI_blocks5());
                archivo.writeInt(inodo.getI_blocks6());
                archivo.writeInt(inodo.getI_blocks7());
                archivo.writeInt(inodo.getI_blocks8());
                archivo.writeInt(inodo.getI_blocks9());
                archivo.writeInt(inodo.getI_blocks10());
                archivo.writeInt(inodo.getI_blocks11());
                archivo.writeInt(inodo.getI_blocks12());
                archivo.writeInt(0);
                archivo.writeInt(inodo.getI_ctime());
                archivo.writeInt(inodo.getI_time());
                archivo.writeInt(inodo.getMtime());
                archivo.writeInt(inodo.getI_dtime());
        
        archivo.close();
        return true;
    }
    public String cat(String nombre,long bloque) throws FileNotFoundException, IOException{
        Inodo inodo = new Inodo();
        ArrayList<Long> bloques = new ArrayList();
        String datos = "";
        int cent = 0;
        long dir = 0;
            archivo = new RandomAccessFile("disco.bin", "rw");
            archivo.seek(bloque*4096);
            int id = archivo.readInt();
            int tamn = archivo.readInt();
            long tam = archivo.readLong();
            String nom = archivo.readUTF();
            
            while(tam > 0 && cent < 4096){
                cent+=80;
                if(nombre.equals(nom)){
                    
                    archivo.seek(69633+(id*80));
                    archivo.readInt();
                    archivo.readInt();
                    archivo.readInt();
                    for (int i = 0;i<12;i++){
                        dir = archivo.readInt();
                        
                        if(dir !=0 && dir < 65532){
                            bloques.add(dir);
                        }
                    }
                    
                    for(int i = 0;i<bloques.size();i++){
                        archivo.seek(bloques.get(i)*4096);
                        datos = datos+archivo.readUTF();
                    }
                    archivo.close();
                    return datos;
                                    }
                id=archivo.readInt();
                archivo.readInt();
                tam = archivo.readLong();
                nom = archivo.readUTF();
                
            }
            archivo.close();
            return datos;
    }
    
}
