package com.coppate.g04.coppate;

/**
 * Clase que contiene los códigos usados en "Coppate" para
 * mantener la integridad en las interacciones entre actividades
 * y fragmentos
 */

public class Constantes {
    /**
     * Transición Home -> Crear Evento
     */
    public static final int CODIGO_EVENTO = 100;

    /**
     * Transición Home -> Actualizar Evento
     */
    public static final int CODIGO_ACTUALIZACION = 101;

    /**
     * Dirección de Hostinger
     */
    private static final String URL = "coppate.esy.es";
    /**
     * URLs del Web Service
     */

    public static final String GET = "http://" + URL + "/obtener_eventos.php";
    public static final String GET_BY_ID = "http://" + URL + "/obtener_evento_por_id.php";
    public static final String GET_BY_OWNER = "http://" + URL + "/obtener_evento_por_owner.php";
    public static final String UPDATE = "http://" + URL + "/TBD.php";
    public static final String DELETE = "http://" + URL + "/TBD.php";
    public static final String INSERT = "http://" + URL + "/insertar_evento.php";
    public static final String LOG = "http://" + URL + "/loguear_usuario.php";

    /**
     * Clave para el valor extra que representa al identificador de una evento
     */
    public static final String EXTRA_ID = "IDEXTRA";

}