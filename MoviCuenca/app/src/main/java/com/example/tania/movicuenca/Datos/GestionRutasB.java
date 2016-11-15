package com.example.tania.movicuenca.Datos;
import android.content.Context;
import com.example.tania.movicuenca.R;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;


/**
 * Created by Tania on 27/10/2016.
 */
public class GestionRutasB {
    private Context context;
    public  GestionRutasB(){}

    public GestionRutasB(Context current) {
        this.context = current;
    }
    public Hashtable<String,ArrayList> CargarDatosRutas() throws IOException {
        Hashtable<String, ArrayList> rbuses = new Hashtable<String, ArrayList>();
        String linea;
        String numero_linea;
        String puntos_referencia;
        InputStream is =context.getResources().openRawResource(R.raw.rutabuses);
        BufferedReader reader =  new BufferedReader(new InputStreamReader(is));
        if (is != null) {
            while ((linea = reader.readLine()) != null) {
                numero_linea = linea.split(";")[0];
                int posicion = 0;
                List<String> listado_rutas = new ArrayList<>();
                StringTokenizer st = new StringTokenizer(linea, ";");
                //bucle por todas las palabras
                while (st.hasMoreTokens()) {
                    puntos_referencia = st.nextToken();
                    if (posicion != 0) {
                        listado_rutas.add(puntos_referencia);
                    }
                    posicion++;
                }
                rbuses.put(numero_linea, (ArrayList) listado_rutas);
            }
            is.close();
        }else {
            System.out.println("No ahi contenido en el archivo");
        }
        return  rbuses;
    }
    public Hashtable<String,ArrayList> CargarTiemposRutas() throws IOException {
        Hashtable<String, ArrayList> busestiempos = new Hashtable<String, ArrayList>();
        String linea;
        String numero_linea;
        String tiempos;
        InputStream is =context.getResources().openRawResource(R.raw.horariosbuses);
        BufferedReader reader =  new BufferedReader(new InputStreamReader(is));
        if (is != null) {
            while ((linea = reader.readLine()) != null) {
                numero_linea = linea.split(";")[0];
                int posicion = 0;
                List<String> listado_rutas = new ArrayList<>();
                StringTokenizer st = new StringTokenizer(linea, ";");
                //bucle por todas las palabras
                while (st.hasMoreTokens()) {
                    tiempos = st.nextToken();
                    if (posicion != 0) {
                        listado_rutas.add(tiempos);
                    }
                    posicion++;
                }
                busestiempos.put(numero_linea, (ArrayList) listado_rutas);
            }
            is.close();
        }else {
            System.out.println("No ahi contenido en el archivo");
        }
        return  busestiempos;
    }
    public  ArrayList<String> buscarRuta(String ruta, Hashtable<String,ArrayList> rbuses){
        ArrayList<String> RutaLineaEspecifica = rbuses.get(ruta) ;
        return RutaLineaEspecifica;
    }
    public  ArrayList<String> buscarTiempos(String ruta, Hashtable<String,ArrayList> busestiempos){
        ArrayList<String> TiempoLineaEspecifica = busestiempos.get(ruta) ;
        return TiempoLineaEspecifica;
    }
}
