package com.example.tania.movicuenca.Logica;
import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Tania on 31/10/2016.
 */

public class Linea {

    Context context;
    public Linea(){
    }
    public Linea(Context context){
        this.context = context;
    }
    static int isNumeric(String cadena){
        try {
            return (Integer.parseInt(cadena));
        } catch (NumberFormatException nfe){
            return 0;
        }
    }
    public  boolean V_Numero_Linea(String v_numero, Hashtable<String,ArrayList> rbuses, GoogleMap mMap, Hashtable<String,ArrayList> buseshorarios) throws IOException {
        int numero = isNumeric(v_numero);
        ArrayList<LatLng> ruta_Elinea;
        if(numero>0 && numero<30 && numero!=21){
            ruta_Elinea = new Rutas().ObtnerRutaEspecifica(v_numero,rbuses,context,mMap,buseshorarios);
            return  true;
        }
        else{
            return  false;
        }
    }
}
