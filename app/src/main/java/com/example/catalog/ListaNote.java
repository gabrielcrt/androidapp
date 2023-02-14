package com.example.catalog;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

public class ListaNote extends AppCompatActivity {
    Connection connection;
    ArrayList<String> lista_note = new ArrayList<>();
    Integer profesor_id;
    Integer elev_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_note);
        setTitle("Vizualizare note");
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));


        ListView listView = findViewById(R.id.listaNote);
        lista_note.clear();
        DBconnect dBconnect = new DBconnect();
        connection = dBconnect.connect();

        try {
            String query = "exec getProfesorId" + " " + MainActivity.bdnume;
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                profesor_id = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            String query = "exec getElevId" + " " +"'"+Note.elev+"'";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                elev_id = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            String query = "exec getNote" + " " + elev_id + "," + profesor_id;
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                lista_note.add(rs.getString(1) + " " + "Nota: " + rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter(ListaNote.this, android.R.layout.simple_list_item_1, lista_note);
        listView.setAdapter(adapter);
        listView.setOnCreateContextMenuListener(ListaNote.this);
        elev_id = 0;
    }
}