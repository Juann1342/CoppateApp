package com.coppate.g04.coppate;

/**
 * Created by guillermo.bazzi on 10/31/2016.
 */
public class Usuario {
    private static Usuario ourInstance = new Usuario();

    private String nombre;
    private String apellido;
    private String idUsuario;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public static Usuario getInstance() {
        return ourInstance;
    }

    private Usuario() {
    }

}
