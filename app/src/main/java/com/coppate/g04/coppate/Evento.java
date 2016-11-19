package com.coppate.g04.coppate;

public class Evento {
    private String id_evento;
    private String nombre;
    private String id_owner;
    private String edad_min;
    private String edad_max;
    private String cupo_min;
    private String cupo_max;
    private String costo;
    private String fecha_inicio;
    private String fecha_fin;
    private String time_stamp;
    private String foto;
    private String ubicacion;
    private double latitud;
    private double longitud;
    private String id_categoria;
    private String desc_evento;
    private String id_sexo;
    private String estado; //estados: "activo" "terminado" "cancelado"

    public Evento() {
    }

    public Evento(String id_evento, String nombre, String id_owner, String edad_min, String edad_max, String cupo_min, String cupo_max, String costo, String fecha_inicio, String fecha_fin, String time_stamp, String foto, String ubicacion, double latitud, double longitud, String id_categoria, String desc_evento, String id_sexo, String estado) {
        this.id_evento = id_evento;
        this.nombre = nombre;
        this.id_owner = id_owner;
        this.edad_min = edad_min;
        this.edad_max = edad_max;
        this.cupo_min = cupo_min;
        this.cupo_max = cupo_max;
        this.costo = costo;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.time_stamp = time_stamp;
        this.foto = foto;
        this.ubicacion = ubicacion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.id_categoria = id_categoria;
        this.desc_evento = desc_evento;
        this.id_sexo = id_sexo;
        this.estado = estado;
    }

    public String getId_evento() {
        return id_evento;
    }

    public void setId_evento(String id_evento) {
        this.id_evento = id_evento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId_owner() {
        return id_owner;
    }

    public void setId_owner(String id_owner) {
        this.id_owner = id_owner;
    }

    public String getEdad_min() {
        return edad_min;
    }

    public void setEdad_min(String edad_min) {
        this.edad_min = edad_min;
    }

    public String getEdad_max() {
        return edad_max;
    }

    public void setEdad_max(String edad_max) {
        this.edad_max = edad_max;
    }

    public String getCupo_min() {
        return cupo_min;
    }

    public void setCupo_min(String cupo_min) {
        this.cupo_min = cupo_min;
    }

    public String getCupo_max() {
        return cupo_max;
    }

    public void setCupo_max(String cupo_max) {
        this.cupo_max = cupo_max;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(String id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getDesc_evento() {
        return desc_evento;
    }

    public void setDesc_evento(String desc_evento) {
        this.desc_evento = desc_evento;
    }

    public String getId_sexo() {
        return id_sexo;
    }

    public void setId_sexo(String id_sexo) {
        this.id_sexo = id_sexo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}