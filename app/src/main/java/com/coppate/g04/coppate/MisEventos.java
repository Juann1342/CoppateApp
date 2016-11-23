package com.coppate.g04.coppate;

import java.sql.Array;
import java.util.ArrayList;

/**
 * Created by guillermo.bazzi on 11/19/2016.
 */
public class MisEventos {
    Evento[] eventos;

    Evento[] evento;

    Evento[] evento_otro_usuario;

    private static MisEventos ourInstance = new MisEventos();

    public static MisEventos getInstance() {
        return ourInstance;
    }

    private MisEventos() {
    }

    public Evento[] getEventos() {
        return eventos;
    }

    public void setEventos(Evento[] eventos) {
        this.eventos = eventos;
    }

    public Evento[] getEvento() {
        return evento;
    }

    public void setEvento(Evento[] eventos) {
        this.evento = eventos;
    }

    public Evento[] getEventoOtroUsuario() {
        return evento_otro_usuario;
    }

    public void setEventoOtroUsuario(Evento[] evento_otro_usuario) {
        this.evento_otro_usuario = evento_otro_usuario;
    }

}
