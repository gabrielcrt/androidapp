package com.example.catalog;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

public class ListaPrezente extends AppCompatActivity {
    Connection connection;
    ArrayList<Date> lista_prezente = new ArrayList<>();
    Integer profesor_id;
    Integer elev_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_prezente);
        setTitle("Vizualizare prezente");
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));
        ListView listView = findViewById(R.id.listaPrezente);
        lista_prezente.clear();
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
            String query = "exec getElevId" + " " +"'"+Prezente.elev+"'";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                elev_id = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            String query = "exec getPrezente" + " " + elev_id + "," + profesor_id;
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                lista_prezente.add(rs.getDate(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter(ListaPrezente.this, android.R.layout.simple_list_item_1, lista_prezente);
        listView.setAdapter(adapter);
        listView.setOnCreateContextMenuListener(ListaPrezente.this);
        elev_id = 0;
    }
}