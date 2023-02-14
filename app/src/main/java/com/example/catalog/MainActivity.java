package com.example.catalog;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    Connection connection;
    static String bdnume = "";
    String bdparola = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));
        getSupportActionBar().hide();
        DBconnect dBconnect = new DBconnect();
        connection = dBconnect.connect();
        //
        EditText etnume = findViewById(R.id.nume_acces);
        EditText etparola = findViewById(R.id.parola_acces);
        TextView eroare = findViewById(R.id.eroare);
        FloatingActionButton insert = findViewById(R.id.insert);
        //
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nume = etnume.getText().toString();
                String parola = etparola.getText().toString();
                try {
                    String query = "exec getProfesor" + " " + nume + "," + parola;
                    Statement st = connection.createStatement();
                    ResultSet rs = st.executeQuery(query);
                    while (rs.next()) {
                        bdnume = rs.getString(1);
                        bdparola = rs.getString(2);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (nume.equals(bdnume) && !nume.isEmpty() && parola.equals(bdparola) && !parola.isEmpty()) {
                    Intent intent = new Intent(MainActivity.this, AlegereClasa.class);
                    startActivity(intent);
                } else {
                    eroare.setText("Datele introduse sunt incorecte sau lipsesc!");
                }
            }
        });
    }
}