package com.coppate.g04.coppate;

// codigo obtenido de
// http://www.sachinmuralig.me/2013/11/android-simple-multi-contacts-picker.html

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class InvitarContactos extends AppCompatActivity {

    private static final int CONTACT_PICK_REQUEST = 1000;
    ListView contactsChooser;
    Button btnDone;
    TextView txtFilter;
    TextView txtLoadInfo;
    ContactsListAdapter contactsListAdapter;
    ContactsLoader contactsLoader;

    Funciones funciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*overridePendingTransition(R.anim.fadein, R.anim.fadeout);*/
        setContentView(R.layout.activity_invitar_contactos);

        funciones = new Funciones(getApplicationContext());

        contactsChooser = (ListView) findViewById(R.id.lst_contacts_chooser);
        btnDone = (Button) findViewById(R.id.btn_done);
        txtFilter = (TextView) findViewById(R.id.txt_filter);
        txtLoadInfo = (TextView) findViewById(R.id.txt_load_progress);


        //recibimos la lista del activity anterior nombrado 'Contactos'
        final ArrayList<ContactsList> contactoos = getIntent().getParcelableExtra("Contactos");

        contactsListAdapter = new ContactsListAdapter(this, new ContactsList());

        contactsChooser.setAdapter((ListAdapter) contactsListAdapter);


        loadContacts("");


        txtFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                contactsListAdapter.filter(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (contactsListAdapter.selectedContactsList.contactArrayList.isEmpty()) {
                    setResult(RESULT_CANCELED);
                } else {

                    Intent resultIntent = new Intent();
                    resultIntent.putParcelableArrayListExtra("ContactosSeleccionados", contactsListAdapter.selectedContactsList.contactArrayList);
                    resultIntent.putExtra("contactos2", contactoos);
                    setResult(RESULT_OK, resultIntent);
                    //mostrarToast("probando...");
                }
                finish();

            }
        });
    }

    // directamente no esta ingresando en OnactivityResult
    // este onctivity se recibe desde Crear evento.. ahora no sirve para nada
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CONTACT_PICK_REQUEST && resultCode == RESULT_OK) {

            ArrayList<Contact> selectedContacts = data.getParcelableArrayListExtra("ContactosSeleccionados");
            ArrayList<Contact> agregarContactos = data.getParcelableExtra("contactos2");

            String display = "";
            for (int i = 0; i < selectedContacts.size(); i++) {

                display += (i + 1) + ". " + selectedContacts.get(i).toString() + "\n";

                agregarContactos.add(selectedContacts.get(i));

            }
            TextView contactsDisplay = null;
            contactsDisplay.setText("Contactos Seleccionados : \n\n" + display);

        }

    }


    private void loadContacts(String filter) {

        if (contactsLoader != null && contactsLoader.getStatus() != AsyncTask.Status.FINISHED) {
            try {
                contactsLoader.cancel(true);
            } catch (Exception e) {
                funciones.mostrarToastCorto("Error no se han podido cargar los contactos");
            }
        }
        if (filter == null) filter = "";

        try {
            //Running AsyncLoader with adapter and  filter
            contactsLoader = new ContactsLoader(this, contactsListAdapter);
            contactsLoader.txtProgress = txtLoadInfo;
            contactsLoader.execute(filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void mostrarToast(String str) {
        try {
            Toast toast2 =
                    Toast.makeText(getApplicationContext(),
                            str.toString(), Toast.LENGTH_SHORT);


            toast2.setGravity(Gravity.CENTER | Gravity.LEFT, 0, 0);

            toast2.show();
        } catch (Exception e) {
            mostrarToast("Error no se ha podido mostrar el texto en pantalla");
        }
    }

}
