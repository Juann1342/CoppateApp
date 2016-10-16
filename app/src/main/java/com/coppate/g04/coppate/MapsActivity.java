package com.coppate.g04.coppate;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    //creo marcador y asigno variables para latitud y longitud

    private Marker marcador;

    double lat = 0.0;
    double lon = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        miUbicacion();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);


        //marcador para eventos en el mapa
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {  //obtiene latitud y longitud de donde estoy pulsando
                mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marcadorflag))
                        .anchor(0.0f, 1.0f)
                        .position(latLng));
                Intent intent = new Intent(MapsActivity.this, CrearEvento.class);
                startActivity(intent);


                //obtener coordenadas
                Projection proj = mMap.getProjection();
                Point coord = proj.toScreenLocation(latLng);

                Toast.makeText(
                        MapsActivity.this,
                        "Coordenadas\n"+"Latitud:"+latLng.latitude+"\n"+
                                "Longitud:"+latLng.longitude+"\n",
                        Toast.LENGTH_SHORT).show();



            }
        });
//acciones al pulsar un marcador
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(getApplicationContext(),"Evento :)", Toast.LENGTH_SHORT).show();
                return false;
            }
        });


    }

    //metodo para agregar el marcador, con camera update centro el mapa en mi posicion

    public void agregarMarcador(double lat, double lon) {
        LatLng coordenadas = new LatLng(lat, lon);
        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas,14);

        //si el marcador es distinto a null se debera remover
        if (marcador != null) marcador.remove();
        marcador = mMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title("Aquí te encuentras")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.man)));
        mMap.animateCamera(miUbicacion);
    }

    //metodo para obtener la ubicación, se comprueba si es null para que la app no se cierre si sucede
    public void actualizarUbicacion(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lon = location.getLongitude();
            agregarMarcador(lat, lon);
        }
    }

    //el objeto del tipo location listener sirve para que aplique cualquier cambio en la ubicacion
    LocationListener locListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {  //se llama ante cualquier cambio
            actualizarUbicacion(location); //refresca ubicacion

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    //metodo para obtener la ultima ubicacion conocida, se actualiza cada 15 segundos
    private void miUbicacion() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        actualizarUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,15000,0,locListener);
    }

    }



