package com.coppate.g04.coppate;

import java.util.ArrayList;

/**
 * Created by Jul-note on 20/11/2016.
 */

public class ListaMiembros {

    Miembro[] miembros;

    private static ListaMiembros ourInstance = new ListaMiembros();

    public static ListaMiembros getInstance() {
        return ourInstance;
    }

    private ListaMiembros(){
    }

    public Miembro[] getMiembros() {
        return miembros;
    }

    public void setMiembro(Miembro[] miembros) {
        this.miembros = miembros;
    }
}
