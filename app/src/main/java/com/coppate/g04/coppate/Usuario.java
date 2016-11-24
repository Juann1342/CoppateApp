package com.coppate.g04.coppate;

import android.net.Uri;


/**
 * Created by guillermo.bazzi on 10/31/2016.
 */
class Usuario {
    private static Usuario ourInstance = new Usuario();

    private String id_usuario;
    private String nombre;
    private String apellido;
    private String email;
    private String fecha_nacimiento;
    private int id_sexo;
    private String alias;
    private Uri foto;

    String getId_usuario() {
        return id_usuario;
    }

    void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    String getNombre() {
        return nombre;
    }

    void setNombre(String nombre) {
        this.nombre = nombre;
    }

    String getApellido() {
        return apellido;
    }

    void setApellido(String apellido) {
        this.apellido = apellido;
    }

    String getEmail() {
        return email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    int getId_sexo() {
        return id_sexo;
    }

    void setId_sexo(int id_sexo) {
        this.id_sexo = id_sexo;
    }

    String getAlias() {
        return alias;
    }

    void setAlias(String alias) {
        this.alias = alias;
    }

    Uri getFoto() {
        return foto;
    }

    void setFoto(Uri foto) {
        this.foto = foto;
    }

    static Usuario getInstance() {
        return ourInstance;
    }

    private Usuario() {
    }
}