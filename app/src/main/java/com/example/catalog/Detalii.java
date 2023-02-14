package com.example.catalog;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class Detalii extends AppCompatActivity {
    Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalii);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));
        setTitle("Elev - " + AlegereActiune.elev);

        DBconnect dBconnect = new DBconnect();
        connection = dBconnect.connect();

        TextView txtNume = findViewById(R.id.txtNume);
        TextView txtPrenume = findViewById(R.id.txtPrenume);
        TextView txtLocalitate = findViewById(R.id.txtLocalitate);
        TextView txtAdresa = findViewById(R.id.txtAdresa);
        TextView txtTelefon = findViewById(R.id.txtTelefon);

        try {
            String query = "exec getDateElev" + "'"+AlegereActiune.elev+"'";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                txtNume.setText("Nume: " + rs.getString(1));
                txtPrenume.setText("Prenume: " + rs.getString(2));
                txtLocalitate.setText("Localitate: " +rs.getString(3));
                txtAdresa.setText("Adresa: " + rs.getString(4));
                txtTelefon.setText("Telefon: " + rs.getString(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}