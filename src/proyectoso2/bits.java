package proyectoso2;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
/**
 * @web http://www.jc-mouse.net
 * @author Mouse
 */
public class bits {

    
    BitMapBLOCK bloque = new BitMapBLOCK();
    BitMap_Inodo inodo = new BitMap_Inodo();
    tabla_Inodo inodoTabla = new tabla_Inodo();
    
    private byte[] disco =  new byte[258435456];
    private RandomAccessFile archivo = null;
    private Inodo inodoAc = new Inodo();
    public void Crear_Disco() throws FileNotFoundException, IOException
    {
     archivo = new RandomAccessFile("disco.bin", "rw");
     //for(long i = 0;i<260000000;i++){
        //archivo.write(disco);
        escribir();
     //}
    }
    public void escribir() throws FileNotFoundException, IOException
    {
        /*archivo = new RandomAccessFile("disco.bin", "rw");
        archivo.write(disco);
        archivo.seek(135168);
        archivo.writeInt(0);
        archivo.writeInt(12);
        archivo.writeLong(23000);
        archivo.writeUTF("$");
        archivo.close();*/
        
        try {
            //Objeto a guardar en archivo *.DAT
            BitMapBLOCK bloque = new BitMapBLOCK();
            for(int i=0;i<34;i++){
                bloque.setBitMap(false, i);
            }
            inodo.setBitMapINODO(false, 0);
            
            //Se crea un Stream para guardar archivo
            ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream( "disco.bin" ));
            //Se escribe el objeto en archivo
            file.writeObject(bloque);
            file.writeObject(inodo);
            file.writeObject(inodoTabla);
            for(int i = 0;i<267635456;i++){
                file.write(0);
            }
            //se cierra archivo
            file.close();
            
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    public void guardaCambiosEstructura() throws FileNotFoundException, IOException{
        ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream( "disco.bin",true ));
            //Se escribe el objeto en archivo
            file.writeObject(bloque);
            file.writeObject(inodo);
            file.writeObject(inodoTabla);
            //se cierra archivo
            file.close();
    }

    public void leer()
    {
        try {
            //BitMapBLOCK bloque = new BitMapBLOCK();
            //BitMap_Inodo inodo = new BitMap_Inodo();
            //tabla_Inodo inodoTabla = new tabla_Inodo();
            //Stream para leer archivo
            ObjectInputStream file = new ObjectInputStream(new FileInputStream( "disco.bin" ));
            //Se lee el objeto de archivo y este debe convertirse al tipo de clase que corresponde
            bloque = (BitMapBLOCK) file.readObject();
            inodo = (BitMap_Inodo) file.readObject();
            inodoTabla = (tabla_Inodo) file.readObject();
            //se cierra archivo
            file.close();
            //Se utilizan metodos de la clase asi como variables guardados en el objeto
            //System.out.println("El objeto se llama:" +  bloque );
            /*archivo = new RandomAccessFile("disco.bin", "rw");
            archivo.seek(33*4096);
            int idi;
            int recl;
            long tamb;
            String nombre = "fff";
            idi = archivo.readInt();
            recl = archivo.readInt();
            tamb = archivo.readLong();
            nombre = archivo.readUTF();
            //resp3 = archivo.readLine();
            archivo.close();*/
            /*String res = String.valueOf(bloque.getLibre()) ;
            String res1 = String.valueOf(inodo.getBitMapINODO(0)) ;
            
            System.out.println("El primer bloque libre es : " + res);
            System.out.println("el primer inodo del disco es  : " + res1);
            System.out.println("El mensaje es : " + idi+" "+recl+" "+tamb+" "+nombre);*/
        } catch (ClassNotFoundException ex) {
             System.out.println(ex);
        } catch (IOException ex) {
             System.out.println(ex);
       }
    }
    public void escribeDirectoryEntry(long Bloque,int nInodo,String nombre) throws FileNotFoundException, IOException{
        int tamN = nombre.length();
        long tRecord = 16+tamN;
        archivo = new RandomAccessFile("disco.bin", "rw");
        //archivo.write(disco);
        archivo.seek(Bloque*4096);
        archivo.writeInt(nInodo);
        archivo.writeInt(tamN);
        archivo.writeLong(tRecord);
        archivo.writeUTF(nombre);
        archivo.close();
        //System.out.print("hola");
        //DirectoryEntrie dirEntrie =  new DirectoryEntrie(nInodo,tamN,tRecord,nombre);
    }
    public DirectoryEntrie leerDirectoryEntrie(int bloque) throws FileNotFoundException, IOException{
        
        archivo = new RandomAccessFile("disco.bin", "rw");
            archivo.seek(bloque*4096);
            int idi;
            int recl;
            long tamb;
            String nombre = "fff";
            idi = archivo.readInt();
            recl = archivo.readInt();
            tamb = archivo.readLong();
            nombre = archivo.readUTF();
            archivo.close();
        return new DirectoryEntrie(idi,recl,tamb,nombre);
    }
    public void escribeBloque(int bloque, String contenido) throws FileNotFoundException, IOException{
        archivo = new RandomAccessFile("disco.bin", "rw");
        //archivo.write(disco);
        archivo.seek(bloque*4096);
        archivo.writeUTF(contenido);
        archivo.close();
    }
    public String leerBloqueDatos(int bloque) throws FileNotFoundException, IOException{
        String datos = "";
        
        archivo = new RandomAccessFile("disco.bin", "rw");
        //archivo.write(disco);
        archivo.seek(bloque*4096);
        datos = archivo.readUTF();
        archivo.close();
        return datos;
    }
    public void escribeBloqueDatos(int bloque,String datos) throws FileNotFoundException, IOException{
        archivo = new RandomAccessFile("disco.bin", "rw");
        archivo.write(disco);
        archivo.seek(bloque*4096);
        archivo.writeUTF(datos);
        archivo.close();
    }
    public ArrayList<Integer> leerBloqueDirecciones(int bloque) throws FileNotFoundException, IOException{
        ArrayList<Integer> direcciones = new ArrayList();
        archivo = new RandomAccessFile("disco.bin", "rw");
        archivo.seek(bloque*4096);
        Integer direccion = -1;
        for(int a = 0;a<1024;a++){
            direccion = archivo.readInt();
            if(direccion != 0){
                direcciones.add(a);
            }
        }
        archivo.close();
        return direcciones;
    }
    public boolean escribeBloqueDirecciones(int bloque,int direccion) throws FileNotFoundException, IOException{
        archivo = new RandomAccessFile("disco.bin", "rw");
            archivo.seek(bloque*4096);
            int pos = archivo.readInt();
            
            if(pos == 0){
                pos++;
                archivo.seek(bloque*4096);
                archivo.writeInt(pos);
                archivo.writeInt(direccion);
                archivo.close();
                return true;
            }else{
                if(pos>0 && pos<1024){
                    pos++;
                    archivo.seek(bloque*4096);
                    archivo.writeInt(pos);
                    archivo.seek((bloque*4096)+(pos*4));
                    archivo.writeInt(direccion);
                    archivo.close();
                    return true;
                }
            }
            
            archivo.close();
            return false;
    }
    public ArrayList<DirectoryEntrie> leerBloqueDirEntrie(int bloque) throws FileNotFoundException, IOException{
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
                    tabla.add(new DirectoryEntrie(idi,recl,tamb,nombre));
                }
                pos+=tamb;
                cent+=8;
            }while(pos < (bloque*4096)+4096 && cent < 4096);
            archivo.close();
            return tabla;
    }
    public boolean escribeBloqueDirEntrie(int bloque, DirectoryEntrie dirEntrie) throws FileNotFoundException, IOException{
        archivo = new RandomAccessFile("disco.bin", "rw");
            
            int idi;
            int recl;
            long tamb;
            String nombre = "fff";
            
            int pos =  bloque*4096;
            int centinela = 0;
            do{
                archivo.seek(pos);
                idi = archivo.readInt();
                recl = archivo.readInt();
                tamb = archivo.readLong();
                nombre = archivo.readUTF();
                
                if( tamb == 0 && pos < (bloque*4096)+4096){
                    
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
    public BitMapBLOCK getBMBloque() {
        return bloque;
    }

    public BitMap_Inodo getBMInodo() {
        return inodo;
    }

    public tabla_Inodo getInodoTabla() {
        return inodoTabla;
    }

    public void setBloque(BitMapBLOCK bloque) {
        this.bloque = bloque;
    }

    public void setInodo(BitMap_Inodo inodo) {
        this.inodo = inodo;
    }

    public void setInodoTabla(tabla_Inodo inodoTabla) {
        this.inodoTabla = inodoTabla;
    }
}
