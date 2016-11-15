package com.example.tania.movicuenca.GUI;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tania.movicuenca.Logica.Linea;
import com.example.tania.movicuenca.Logica.Rutas;
import com.example.tania.movicuenca.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class PantallaInicio extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener{

    public Hashtable<String, ArrayList> rbuses = new Hashtable<String, ArrayList>();
    public Hashtable<String, ArrayList> buseshorarios = new Hashtable<String, ArrayList>();
    private GoogleMap mMap;
    Button blinea;
    Button bruta;
    Button binformacion;
    SearchView buscarlinea;
    String numero_linea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            rbuses = new Rutas(this).CargarTodasRutas();
            buseshorarios = new Rutas(this).CargarTodosTiempos();

        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        blinea = (Button)findViewById(R.id.blinea);
        bruta = (Button)findViewById(R.id.bruta);
        buscarlinea = (SearchView)findViewById(R.id.buscarlinea);
        binformacion = (Button)findViewById(R.id.binformacion);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        blinea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantallaInicio.this,PantallaSecundaria.class);
                startActivity(intent);

            }
        });
        buscarlinea.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                buscarlinea.setQuery("", false);
                buscarlinea.setIconified(true);
                numero_linea = query;
                try {
                    if(new Linea(getApplicationContext()).V_Numero_Linea(numero_linea,rbuses,mMap,buseshorarios) == false){
                        //Add a marker in Cuenca and move the camera
                        LatLng cuenca = new LatLng(-2.88333,-78.98333);
                        mMap.addMarker(new MarkerOptions().position(cuenca).title("CUENCA"));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cuenca,14));
                        System.out.println(numero_linea);
                        Context context = getApplicationContext();
                        CharSequence text = "Linea no existe..!";
                        int duration = Toast.LENGTH_SHORT;

                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.custom_dialog,
                                (ViewGroup) findViewById(R.id.toast_layout_root));

                        TextView textToast = (TextView) layout.findViewById(R.id.text_toast);
                        textToast.setText(text);

                        Toast toast = new Toast(context);
                        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                        toast.setDuration(duration);
                        toast.setView(layout);
                        toast.show();

                    }
                    else{
                        Context context = getApplicationContext();
                        CharSequence text = "Ruta Cargada!";
                        int duration = Toast.LENGTH_SHORT;

                        LayoutInflater inflater = getLayoutInflater();

                        View layout = inflater.inflate(R.layout.cargarrutas,
                                (ViewGroup) findViewById(R.id.toast_cargarRutas));

                        TextView textToast = (TextView) layout.findViewById(R.id.text_informacion);
                        textToast.setText(text);

                        Toast toast = new Toast(context);
                        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                        toast.setDuration(duration);
                        toast.setView(layout);
                        toast.show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(numero_linea);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //Add a marker in Cuenca and move the camera
        LatLng cuenca = new LatLng(-2.88333,-78.98333);
        mMap.addMarker(new MarkerOptions().position(cuenca).title("CUENCA"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cuenca,14));
    }
    @Override
    public void onClick(View v) {
        Context context = getApplicationContext();
        StringBuffer cadena = new StringBuffer("MoviCuenca permite:\n1.(Press Ruta) Visualizar\ncada ruta, de una linea \nespecifica de bus.\n2.(Press Linea)Ingresado\n un destino, la aplicacion\n sugiere que linea de bus\ntomar. ");
        CharSequence text = cadena;
        int duration = Toast.LENGTH_LONG;

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.cargarayuda,
                (ViewGroup) findViewById(R.id.toast_layout_ayuda));

        TextView textToast = (TextView) layout.findViewById(R.id.text_toast);
        textToast.setText(text);

        Toast toast = new Toast(context);
        toast.setDuration(duration);
        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast.setView(layout);
        toast.show();
    }
}
