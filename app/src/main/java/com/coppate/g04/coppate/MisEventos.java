package com.coppate.g04.coppate;

import java.sql.Array;
import java.util.ArrayList;

/**
 * Created by guillermo.bazzi on 11/19/2016.
 */
public class MisEventos {
    Evento[] eventos; // Lista de eventos de los cuales soy Owner

    Evento[] evento; // Evento del cual soy Owner

    Evento[] evento_otro_usuario; // Se usa para mostrar el detalle de un evento de otro usuario

    Evento[] eventos_cercanos; // Lista de eventos cercanos

    Evento[] eventos_que_participo; // Lista de eventos de los cuales participo


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
    public Evento[] getEventosCercanos() {return eventos_cercanos;
    }

    public void setEventosCercanos(Evento[] eventos_cercanos) {this.eventos_cercanos = eventos_cercanos;}

    public Evento[] getEventos_que_participo() {return eventos_que_participo;}

    public void setEventos_que_participo(Evento[] eventos_que_participo) {this.eventos_que_participo = eventos_que_participo;}
}
