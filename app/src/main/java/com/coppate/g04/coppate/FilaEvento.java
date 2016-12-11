package com.coppate.g04.coppate;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jul-note on 10/12/2016.
 */

public class FilaEvento {

    private String titulo;
    private String descripcion;
    private int sexo;
    private int categoria;

    public FilaEvento(String tit, String desc,int sexo,int categ){
        this.titulo = tit;
        this.descripcion = desc;
        this.sexo = sexo;
        this.categoria = categ;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getSexo() {
        return sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }
}
