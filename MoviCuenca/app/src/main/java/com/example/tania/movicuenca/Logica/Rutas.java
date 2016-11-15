package com.example.tania.movicuenca.Logica;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.location.Address;
import android.location.Geocoder;
import android.widget.SearchView;

import com.example.tania.movicuenca.Datos.GestionRutasB;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Tania on 02/11/2016.
 */

public class Rutas{
    Context context;
    public Rutas(){}
    public Rutas(Context context){
        this.context = context;
    }
    public Hashtable<String,ArrayList> CargarTodasRutas() throws IOException {
        Hashtable<String, ArrayList> rbuses = new Hashtable<String, ArrayList>();
        GestionRutasB regularClass = new GestionRutasB(context);
        rbuses = regularClass.CargarDatosRutas();
        return rbuses;
    }
    public Hashtable<String,ArrayList> CargarTodosTiempos() throws IOException {
        Hashtable<String, ArrayList> busestiempos = new Hashtable<String, ArrayList>();
        GestionRutasB regularClass = new GestionRutasB(context);
        busestiempos = regularClass.CargarTiemposRutas();
        return busestiempos;
    }
    public ArrayList<LatLng> ObtnerRutaEspecifica (String numero_linea, Hashtable<String,ArrayList> rbuses, Context context, GoogleMap mMap,Hashtable<String,ArrayList> buseshorarios) throws IOException {
        mMap.clear();
        ArrayList<String> ruta_linea;
        ArrayList<String> tiempos_linea;
        ArrayList<LatLng> LatLngRuta ;
        ruta_linea = new GestionRutasB().buscarRuta(numero_linea,rbuses);
        tiempos_linea = new GestionRutasB().buscarTiempos(numero_linea,buseshorarios);
        LatLngRuta = rutaEspecificaLatLng(ruta_linea,context);
        new Posiciones().dibujarPolilineas(LatLngRuta,mMap,ruta_linea,tiempos_linea);
        return  LatLngRuta;
    }
    public  ArrayList<LatLng> rutaEspecificaLatLng(ArrayList<String> ruta_linea,Context context){
        ArrayList<LatLng> LatLngRuta = new ArrayList<LatLng>();
        Iterator<String> RecorrerRutas = ruta_linea.iterator();
        while (RecorrerRutas.hasNext()) {
            double latitud = 0.0;
            double longitud = 0.0;
            String PuntoReferencia = RecorrerRutas.next()+",Cuenca,Azuay,Ecuador";
            System.out.println(PuntoReferencia);
            List<Address> geocodeMatches = null;
            try {
                geocodeMatches = new Geocoder(context.getApplicationContext().getApplicationContext()) .getFromLocationName (
                        PuntoReferencia, 1);
            } catch (IOException e) {
                // Bloque catch generada automáticamente TODO
                e.printStackTrace ();
            }
            if (! geocodeMatches.isEmpty ())
            {
                latitud = geocodeMatches.get(0).getLatitude ();
                longitud = geocodeMatches.get (0) .getLongitude ();
            }
            LatLng LatLngPReferencia = new LatLng(latitud,longitud);
            LatLngRuta.add(LatLngPReferencia);
        }
        for(int i=0 ; i<LatLngRuta.size(); i++) {
            System.out.println(LatLngRuta.get(i));
        }
        return LatLngRuta;
    }
    public ArrayList<String> buscar_lineas(Hashtable<String,ArrayList> rbuses, String destino){
        System.out.println("Buscar Lineas Sugeridas");
        System.out.println(rbuses.get("1").get(0));
        String linea;
        ArrayList<String> linea_sugeridas = new ArrayList<String>();
        for(int i = 1;i< 30; i++){
            if(i!=21){
                linea = new String(String.valueOf(i));
                if(rbuses.get(linea).contains(destino) == true){
                    linea_sugeridas.add(linea);
                }
            }
        }
        return  linea_sugeridas;
    }
}
