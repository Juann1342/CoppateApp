package com.coppate.g04.coppate;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jul-Note 10/12/2016.
 */
public class EventoListaAdapter extends ArrayAdapter<FilaEvento> {

    private Context context;
    private ArrayList<FilaEvento> eventos;

    public EventoListaAdapter(Context context,ArrayList<FilaEvento> eventos){
        super(context, R.layout.fila_lista, eventos);
        // Guardamos los parámetros en variables de clase.
        this.context = context;
        this.eventos = eventos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;
        EventosHolder holder;

        if (item == null) {
            item = LayoutInflater.from(context).inflate(R.layout.fila_lista,
                    null);
            // Inicializamos el holder y guardamos las referencias a los
            // controles.
            holder = new EventosHolder();
            holder.sexo = (ImageView) item.findViewById(R.id.fl_icono);
            holder.categoria = (ImageView)item.findViewById(R.id.fl_icono2);
            holder.titulo = (TextView) item.findViewById(R.id.fl_txtPrincipal);
            holder.descripcion = (TextView) item.findViewById(R.id.fl_txtSecundario);

            // Almacenamos el holder en el Tag de la vista.
            item.setTag(holder);
        }
        // Recuperamos el holder del Tag de la vista.
        holder = (EventosHolder) item.getTag();

        // A partir del holder, asignamos los valores que queramos a los
        // controles.
        // Le asignamos una foto al ImegeView.
        holder.sexo.setImageResource(eventos.get(position)
                .getSexo());
        holder.categoria.setImageResource(eventos.get(position).getCategoria());

        // Asignamos los textos a los TextView.
        holder.titulo.setText(eventos.get(position).getTitulo());
        holder.descripcion.setText(eventos.get(position).getDescripcion());
        //holder.descripcion.setText(String.valueOf(position));

        // Devolvemos la vista para que se muestre en el ListView.
        return item;
    }

}
