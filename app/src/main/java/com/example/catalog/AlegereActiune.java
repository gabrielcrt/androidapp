package com.example.catalog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

public class AlegereActiune extends AppCompatActivity {
    Connection connection;
    static ArrayList<String> lista_elevi = new ArrayList<>();
    static String elev;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater =getMenuInflater();
        menuInflater.inflate(R.menu.meniu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.prezente:
                intent =  new Intent(AlegereActiune.this, Prezente.class);
                startActivity(intent);
                break;
            case R.id.note:
                intent =  new Intent(AlegereActiune.this, Note.class);
                startActivity(intent);
        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alegere_actiune);
        setTitle("Clasa" + " " + AlegereClasa.returnClasa());
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));

        lista_elevi.clear();
        ListView listView = findViewById(R.id.lista);
        DBconnect dBconnect = new DBconnect();
        connection = dBconnect.connect();
        try {
            String query = "exec getElevi" + " " + "'" + AlegereClasa.returnClasa() + "'";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                lista_elevi.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lista_elevi);
        listView.setAdapter(adapter);
        listView.setOnCreateContextMenuListener(AlegereActiune.this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                elev = adapterView.getItemAtPosition(i).toString();
                Intent intent = new Intent(AlegereActiune.this, Detalii.class);
                startActivity(intent);
            }
        });
    }
}