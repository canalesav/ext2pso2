/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binarios;

import java.io.Serializable;
import static java.lang.Math.toIntExact;

/**
 *
 * @author Karlos
 */
public class Inodo implements Serializable{
    private int i_mode;
    //4 byte
    private int i_size;
    //4 byte
    private int i_link_count;
    //4 byte
    private int i_time;
    //4 byte
    private int i_ctime;
    //4 byte
    private int mtime;
    //4 byte
    private int i_dtime;
    //4 byte
    private int i_blocks1;
    private int i_blocks2;
    private int i_blocks3;
    private int i_blocks4;
    private int i_blocks5;
    private int i_blocks6;
    private int i_blocks7;
    private int i_blocks8;
    private int i_blocks9;
    private int i_blocks10;
    private int i_blocks11;
    private int i_blocks12;
    private int i_blocks13;
    //52 byte
    
    
    public Inodo() {}
    
    public Inodo(int i_mode, int i_size, int i_link_count,int i_blocks1, int i_blocks2, int i_blocks3, int i_blocks4, int i_blocks5, int i_blocks6, int i_blocks7, int i_blocks8, int i_blocks9, int i_blocks10, int i_blocks11, int i_blocks12, int i_blocks13, int cti,int lti,int mti,int rti) {
        this.i_mode = i_mode;
        this.i_size = i_size;
        this.i_link_count = i_link_count;
        this.i_dtime = rti;
        this.i_ctime = cti; 
        this.i_time = lti = toIntExact(System.currentTimeMillis() / 1000);
        this.mtime = mti;
        this.i_blocks1 = i_blocks1;
        this.i_blocks2 = i_blocks2;
        this.i_blocks3 = i_blocks3;
        this.i_blocks4 = i_blocks4;
        this.i_blocks5 = i_blocks5;
        this.i_blocks6 = i_blocks6;
        this.i_blocks7 = i_blocks7;
        this.i_blocks8 = i_blocks8;
        this.i_blocks9 = i_blocks9;
        this.i_blocks10 = i_blocks10;
        this.i_blocks11 = i_blocks11;
        this.i_blocks12 = i_blocks12;
        this.i_blocks13 = i_blocks13;
    }

    public Inodo(int i_mode, int i_size, int i_link_count,int i_blocks1, int i_blocks2, int i_blocks3, int i_blocks4, int i_blocks5, int i_blocks6, int i_blocks7, int i_blocks8, int i_blocks9, int i_blocks10, int i_blocks11, int i_blocks12, int i_blocks13) {
        this.i_mode = i_mode;
        this.i_size = i_size;
        this.i_link_count = i_link_count;
        
        this.i_ctime = this.i_time = this.mtime = toIntExact(System.currentTimeMillis() / 1000);
        this.i_blocks1 = i_blocks1;
        this.i_blocks2 = i_blocks2;
        this.i_blocks3 = i_blocks3;
        this.i_blocks4 = i_blocks4;
        this.i_blocks5 = i_blocks5;
        this.i_blocks6 = i_blocks6;
        this.i_blocks7 = i_blocks7;
        this.i_blocks8 = i_blocks8;
        this.i_blocks9 = i_blocks9;
        this.i_blocks10 = i_blocks10;
        this.i_blocks11 = i_blocks11;
        this.i_blocks12 = i_blocks12;
        this.i_blocks13 = i_blocks13;
    }

    

    public int getI_mode() {
        return i_mode;
    }

    public int getI_size() {
        return i_size;
    }

    public int getI_link_count() {
        return i_link_count;
    }

    public int getI_time() {
        return i_time;
    }

    public int getI_ctime() {
        return i_ctime;
    }

    public int getMtime() {
        return mtime;
    }

    public int getI_dtime() {
        return i_dtime;
    }

    public int getI_blocks1() {
        return i_blocks1;
    }

    public int getI_blocks2() {
        return i_blocks2;
    }

    public int getI_blocks3() {
        return i_blocks3;
    }

    public int getI_blocks4() {
        return i_blocks4;
    }

    public int getI_blocks5() {
        return i_blocks5;
    }

    public int getI_blocks6() {
        return i_blocks6;
    }

    public int getI_blocks7() {
        return i_blocks7;
    }

    public int getI_blocks8() {
        return i_blocks8;
    }

    public int getI_blocks9() {
        return i_blocks9;
    }

    public int getI_blocks10() {
        return i_blocks10;
    }

    public int getI_blocks11() {
        return i_blocks11;
    }

    public int getI_blocks12() {
        return i_blocks12;
    }

    public int getI_blocks13() {
        return i_blocks13;
    }

    public void setI_mode(int i_mode) {
        this.i_mode = i_mode;
    }

    public void setI_size(int i_size) {
        this.i_size = i_size;
    }

    public void setI_link_count(int i_link_count) {
        this.i_link_count = i_link_count;
    }

    public void setI_time(int i_time) {
        this.i_time = i_time;
    }

    public void setI_ctime(int i_ctime) {
        this.i_ctime = i_ctime;
    }

    public void setMtime(int mtime) {
        this.mtime = mtime;
    }

    public void setI_dtime(int i_dtime) {
        this.i_dtime = i_dtime;
    }

    public void setI_blocks1(int i_blocks1) {
        this.i_blocks1 = i_blocks1;
    }

    public void setI_blocks2(int i_blocks2) {
        this.i_blocks2 = i_blocks2;
    }

    public void setI_blocks3(int i_blocks3) {
        this.i_blocks3 = i_blocks3;
    }

    public void setI_blocks4(int i_blocks4) {
        this.i_blocks4 = i_blocks4;
    }

    public void setI_blocks5(int i_blocks5) {
        this.i_blocks5 = i_blocks5;
    }

    public void setI_blocks6(int i_blocks6) {
        this.i_blocks6 = i_blocks6;
    }

    public void setI_blocks7(int i_blocks7) {
        this.i_blocks7 = i_blocks7;
    }

    public void setI_blocks8(int i_blocks8) {
        this.i_blocks8 = i_blocks8;
    }

    public void setI_blocks9(int i_blocks9) {
        this.i_blocks9 = i_blocks9;
    }

    public void setI_blocks10(int i_blocks10) {
        this.i_blocks10 = i_blocks10;
    }

    public void setI_blocks11(int i_blocks11) {
        this.i_blocks11 = i_blocks11;
    }

    public void setI_blocks12(int i_blocks12) {
        this.i_blocks12 = i_blocks12;
    }

    public void setI_blocks13(int i_blocks13) {
        this.i_blocks13 = i_blocks13;
    }
    
    
}
