package com.example.tania.movicuenca.GUI;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tania.movicuenca.Logica.Rutas;
import com.example.tania.movicuenca.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;


public class PantallaSecundaria extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    public Hashtable<String, ArrayList> rbuses = new Hashtable<String, ArrayList>();
    public  ArrayList<String > lineas_sugeridas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            rbuses = new Rutas(this).CargarTodasRutas();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView lineas = (TextView)findViewById(R.id.opcionesbuses);
        final ListView lv = (ListView)findViewById(R.id.listViewPlaceCuenca);
        final ArrayList<String> arrayPlaces = new ArrayList<>();
        arrayPlaces.addAll(Arrays.asList(getResources().getStringArray(R.array.array_places)));
        adapter = new ArrayAdapter<>(
                PantallaSecundaria.this,
                android.R.layout.simple_list_item_1,
                arrayPlaces);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //lineas.setText("Lugar de: "+ lv.getItemAtPosition(position));
                lineas_sugeridas = new Rutas().buscar_lineas(rbuses, (String) lv.getItemAtPosition(position));
                StringBuffer cadena = new StringBuffer();
                if(lineas_sugeridas.size() != 0){
                    for (int x=0;x<lineas_sugeridas.size();x++){
                        cadena =cadena.append("Linea: "+lineas_sugeridas.get(x));
                        cadena =cadena.append("\n");
                    }
                }
                else{
                    cadena = new StringBuffer("No existen opciones..!!!");
                }
                Context context = getApplicationContext();
                CharSequence text =cadena;
                int duration = Toast.LENGTH_LONG;

                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.cargarlinear,
                        (ViewGroup) findViewById(R.id.toast_layout_root));

                TextView textToast = (TextView) layout.findViewById(R.id.text_toast);
                textToast.setText(text);

                Toast toast = new Toast(context);
                toast.setDuration(duration);
                toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                toast.setView(layout);
                toast.show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search,menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                lineas_sugeridas = new Rutas().buscar_lineas(rbuses,query);
                StringBuffer cadena = new StringBuffer();
                if(lineas_sugeridas.size() != 0){
                    for (int x=0;x<lineas_sugeridas.size();x++){
                        cadena =cadena.append("Linea: "+lineas_sugeridas.get(x));
                        cadena =cadena.append("\n");
                    }

                }
                else{
                    cadena = new StringBuffer("No existen opciones..!!!");
                }
                Context context = getApplicationContext();
                CharSequence text = cadena;
                int duration = Toast.LENGTH_LONG;

                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.cargarlinear,
                        (ViewGroup) findViewById(R.id.toast_layout_root));

                TextView textToast = (TextView) layout.findViewById(R.id.text_toast);
                textToast.setText(text);

                Toast toast = new Toast(context);
                toast.setDuration(duration);
                toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                toast.setView(layout);
                toast.show();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
