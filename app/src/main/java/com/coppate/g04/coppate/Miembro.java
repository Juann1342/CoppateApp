package com.coppate.g04.coppate;

/**
 * Created by Jul-note on 20/11/2016.
 */

public class Miembro {

    private String id_evento;

    public Miembro(){}

    public Miembro(String id_evento) {
        this.id_evento = id_evento;
        //this.id_usuario = id_usuario;
    }

    public String getId_evento() {
        return id_evento;
    }

    public void setId_evento(String id_evento) {
        this.id_evento = id_evento;
    }

}
