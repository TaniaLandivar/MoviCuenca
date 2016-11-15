package com.example.tania.movicuenca.Logica;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tania.movicuenca.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

import static com.example.tania.movicuenca.Logica.Linea.isNumeric;


/**
 * Created by Tania on 22/10/2016.
 */

public class Posiciones {

    int hora,minuto,segundo;
    String sec,min,hor;
    public Posiciones(){}

    public void dibujarPolilineas(ArrayList<LatLng> Ruta_Linea, GoogleMap mMap, ArrayList<String> ruta_linea,ArrayList<String> tiempos_linea){
        updateTime();
        //curTime = hor+hora+min+minuto+sec+segundo;
        //System.out.println(curTime);
        int z, cont_linea = 0;
        for (z = 0; z < (Ruta_Linea.size()- 1); z++) {
            String curTime;
            curTime = calcular_hora(tiempos_linea, z);
            if (ruta_linea.get(z).equals(ruta_linea.get(z + 1)) == false && (cont_linea == 0)) {
                if (ruta_linea.get(z + 1).equals(ruta_linea.get(z + 2)) == true) {
                    cont_linea++;
                    LatLng src = Ruta_Linea.get(z + 1);
                    mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ruta_retorno)).anchor(0.0f, 1.0f).position(src).title("RUTA DE RETORNO"));
                }
                LatLng src = Ruta_Linea.get(z);
                LatLng dest = Ruta_Linea.get(z + 1);
                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.busretorno1)).anchor(0.0f, 1.0f).position(src).title(ruta_linea.get(z)).snippet("Próximo bus: " + curTime));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(src, 14));
                Polyline line = mMap.addPolyline(new PolylineOptions()
                        .add(new LatLng(src.latitude, src.longitude),
                                new LatLng(dest.latitude, dest.longitude))
                        .width(5).color(Color.MAGENTA).geodesic(true));
                if (z == 0) {
                    mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.inicio_rutas1)).rotation(180.0f).anchor(0.0f, 1.0f).position(src).title("INICIO RUTA BUS"));
                }
            } else {
                LatLng src = Ruta_Linea.get(z);
                LatLng dest = Ruta_Linea.get(z + 1);
                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.buscabeza)).anchor(0.0f, 1.0f).rotation(180.0f).position(src).title(ruta_linea.get(z)).snippet("Próximo bus: " + curTime));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(src, 14));
                Polyline line = mMap.addPolyline(new PolylineOptions()
                        .add(new LatLng(src.latitude, src.longitude),
                                new LatLng(dest.latitude, dest.longitude))
                        .width(2).color(Color.BLUE).geodesic(true));
            }
        }
    }
    private void updateTime(){
        Calendar c = Calendar.getInstance();
        hora =c.get(Calendar.HOUR_OF_DAY);
        System.out.println(hora);
        minuto = c.get(Calendar.MINUTE);
        System.out.println(minuto);
        setZeroClock();
    }
    private void setZeroClock(){
        if(hora >= 0 & hora <= 9){
            hor = "0";
        }
        else{
            hor = "";
        }
        if(minuto >= 0 & minuto <= 9){
            min = ":0";
        }
        else{
            min = ":";
        }
    }
    public String calcular_hora(ArrayList<String> tiempos_linea, int z){
        String curTime;
        int hora_inicio = isNumeric(tiempos_linea.get(0));
        int frecencia = isNumeric(tiempos_linea.get(1));
        int tiempo_puntos = isNumeric(tiempos_linea.get(2));
        int hora_fin = isNumeric(tiempos_linea.get(3));
        int hora_actual_minutos, hora_actual_minutos_basico ;
        int division;
        hora_actual_minutos = ((hora * 60)+minuto)-(tiempo_puntos*z);
        hora_actual_minutos_basico = ((hora * 60)+minuto);
        if(hora_actual_minutos > (hora_fin*60)){
            curTime ="No disponible";
        }
        else{
            if(hora_actual_minutos >= (hora_inicio*60) &&(hora_actual_minutos< (hora_fin*60)) ){
                if(hora_actual_minutos%frecencia == 0){
                    division = new Integer(hora_actual_minutos/frecencia);
                }
                else{
                    division = (new Integer(hora_actual_minutos/frecencia))+1;
                }
                System.out.println(division);
                curTime = new String(String.valueOf((division*frecencia)-hora_actual_minutos ));
            }
            else{
                curTime = new String(String.valueOf(z*tiempo_puntos));
            }
        }
        if(hora_actual_minutos_basico > (hora_fin*60) || hora_actual_minutos_basico < (hora_inicio*60)){
            return ("No disponible");
        }
        else{
            return (curTime+" min");
        }
    }

}
