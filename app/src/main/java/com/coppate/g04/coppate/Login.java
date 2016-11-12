package com.coppate.g04.coppate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.coppate.g04.coppate.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.coppate.g04.coppate.Constantes;
import com.coppate.g04.coppate.VolleySingleton;
import com.coppate.g04.coppate.Usuario;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;


public class Login extends AppCompatActivity {
    private LoginButton loginButton;        //Botón y volver atrás como atributos
    private CallbackManager callbackManager;
    Funciones funciones;
    // checkbox para terminos y condiciones
    private Boolean acepta_terminos;
    private CheckBox ckb_term;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        // se inicializa en false
        acepta_terminos = false;
        funciones = new Funciones(getApplicationContext());

        ckb_term = (CheckBox) findViewById(R.id.al_acepta_terminos);
        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.loginButton);
        loginButton.setEnabled(false);

        // se agrega la validacion de terminos y condiciones
        // si el usuario acepta los terminos, se le habilita la opcion de logeo
        ckb_term.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(acepta_terminos == false){
                    acepta_terminos = true;
                    loginButton.setEnabled(acepta_terminos);
                }
                else if(acepta_terminos == true){
                    acepta_terminos = false;
                    loginButton.setEnabled(acepta_terminos);
                }
            }
        });

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) { //Si el inicio de sesion es exitoso
                Usuario.getInstance().setId_usuario(loginResult.getAccessToken().getUserId());
                Usuario.getInstance().setNombre(Profile.getCurrentProfile().getFirstName());
                Usuario.getInstance().setApellido(Profile.getCurrentProfile().getLastName());
                Usuario.getInstance().setEmail(Profile.getCurrentProfile().getName());
                Usuario.getInstance().setFecha_nacimiento("2016-11-11");
                Usuario.getInstance().setId_sexo(1);
                Usuario.getInstance().setAlias(Profile.getCurrentProfile().getName());
                Usuario.getInstance().setFoto("URI de la foto");   //Profile.getCurrentProfile().getProfilePictureUri(128,128).toString()

                loguearUsuario();  //Llama al webservice

                Toast.makeText(Login.this,"ID: " + Usuario.getInstance().getId_usuario() + " Nombre: " + Usuario.getInstance().getAlias(),Toast.LENGTH_LONG).show();
                goMainScreen();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), R.string.com_facebook_loginview_cancel_action, Toast.LENGTH_SHORT).show(); //Muestra el mensaje de cancelado y vuelvee al login

            }

            @Override
            public void onError(FacebookException error) { //Muestra mensaje de que requiere conexion a internet si es que no hay.
                Toast.makeText(getApplicationContext(), "Error al iniciar sesion en facebook", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void goMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);  //Unica pantalla en ejecución
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void TermAndCond() {
        Intent intent = new Intent(this, TermCond.class);
        startActivity(intent);
    }

    public void irTerminos(View view) {
        TermAndCond();
    }

    /**
     * Guarda un evento en la DB
     */
    public void loguearUsuario() {

        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("id_usuario", Usuario.getInstance().getId_usuario().toString());
        map.put("nombre", Usuario.getInstance().getNombre().toString());
        map.put("apellido", Usuario.getInstance().getApellido().toString());
        map.put("email", Usuario.getInstance().getEmail().toString());
        map.put("fecha_nacimiento", Usuario.getInstance().getFecha_nacimiento().toString());
        map.put("id_sexo", Integer.toString(Usuario.getInstance().getId_sexo()));
        map.put("alias", Usuario.getInstance().getAlias().toString());
        map.put("foto", Usuario.getInstance().getFoto().toString());


        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);


        // Actualizar datos en el servidor
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.POST,
                        Constantes.LOG,
                        jobject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesar la respuesta del servidor
                                //procesarRespuesta(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                funciones.mostrarToastCorto(("Error Volley: " + error.getMessage()));
                            }
                        }

                ) {
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        headers.put("Accept", "application/json");
                        return headers;
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8" + getParamsEncoding();
                    }
                }
        );

    }

    /**
     * Procesa la respuesta obtenida desde el sevidor
     *
     * @param response Objeto Json
     */
/*
    private void procesarRespuesta(JSONObject response) {

        try {
            // Obtener estado
            String estado = response.getString("estado");
            // Obtener mensaje
            String mensaje = response.getString("mensaje");

            switch (estado) {
                case "1":
                    // Mostrar mensaje
                    Toast.makeText(
                            getApplicationContext(),
                            mensaje,
                            Toast.LENGTH_LONG).show();
                    break;

                case "2":
                    // Mostrar mensaje
                    Toast.makeText(
                            getApplicationContext(),
                            mensaje,
                            Toast.LENGTH_LONG).show();
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
*/

    /**
     * Guarda un evento en la DB
     */

}