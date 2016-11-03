package com.coppate.g04.coppate;

/**
 * Created by guillermo.bazzi on 10/31/2016.
 */
public class Usuario {
    private static Usuario ourInstance = new Usuario();

    private String id_usuario;
    private String nombre;
    private String apellido;
    private String email;
    private String fecha_nacimiento;
    private String id_sexo;
    private String alias;
    private String foto;

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getId_sexo() {
        return id_sexo;
    }

    public void setId_sexo(String id_sexo) {
        this.id_sexo = id_sexo;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public static Usuario getInstance() {
        return ourInstance;
    }

    private Usuario() {
    }

}
