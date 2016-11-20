package com.coppate.g04.coppate;

/**
 * Created by guillermo.bazzi on 11/19/2016.
 */
public class MisEventos {
    Evento[] eventos;

    Evento[] evento;

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
}
