package com.example.catalog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Note extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    Connection connection;
    static AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapter;
    static String elev;
    String data;
    Integer profesor_id;
    Integer elev_id;
    Integer materie_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        setTitle("");
        getSupportActionBar().hide();
        //Conexiune baza de date
        DBconnect dBconnect = new DBconnect();
        connection = dBconnect.connect();

        // elemente interfata
        FloatingActionButton btnData = findViewById(R.id.btnData);
        FloatingActionButton btnAdauga = findViewById(R.id.btnAdauga);
        FloatingActionButton btnSterge = findViewById(R.id.btnSterge);
        FloatingActionButton btnVizualizare = findViewById(R.id.btnVizualizare);
        EditText editText = findViewById(R.id.etNota);

        //definire drop-down list
        autoCompleteTextView = findViewById(R.id.auto_complete_txt);
        adapter = new ArrayAdapter<String>(Note.this, R.layout.list_item, AlegereActiune.lista_elevi);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                elev = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(Note.this, "Elev selectat:" + elev, Toast.LENGTH_SHORT).show();
            }
        });
        // dialog pentru selectare data
        btnData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        // adauga nota
        btnAdauga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Executie instructiuni SQL

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
                    String query = "exec getElevId" + " " +"'"+elev+"'";
                    Statement st = connection.createStatement();
                    ResultSet rs = st.executeQuery(query);

                    while (rs.next()) {
                        elev_id = rs.getInt(1);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                try {
                    String query = "exec getProfesorMaterie" + " " + MainActivity.bdnume;
                    Statement st = connection.createStatement();
                    ResultSet rs = st.executeQuery(query);

                    while (rs.next()) {
                        materie_id = rs.getInt(1);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                // Executie adaugare in baza de date
                try {
                    String query = "exec AdaugaNota" + " " + editText.getText().toString() + "," + "'"+data+"'" + "," + materie_id + "," + elev_id + "," + profesor_id;
                    Statement st = connection.createStatement();
                    st.executeQuery(query);


                } catch (SQLException e) {
                    e.printStackTrace();
                }
                elev = "";
                elev_id = 0;
                autoCompleteTextView.setText("");
                editText.setText("");
                Toast.makeText(Note.this, "Nota adaugata cu succes!", Toast.LENGTH_SHORT).show();
            }
        });

        // sterge nota
        btnSterge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Executie instructiuni SQL

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
                    String query = "exec getElevId" + " " +"'"+elev+"'";
                    Statement st = connection.createStatement();
                    ResultSet rs = st.executeQuery(query);

                    while (rs.next()) {
                        elev_id = rs.getInt(1);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                try {
                    String query = "exec getProfesorMaterie" + " " + MainActivity.bdnume;
                    Statement st = connection.createStatement();
                    ResultSet rs = st.executeQuery(query);

                    while (rs.next()) {
                        materie_id = rs.getInt(1);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                // Executie stergere in baza de date
                try {
                    String query = "exec StergeNota" + " " + "'"+data+"'" + "," + materie_id + "," + elev_id + "," + profesor_id;
                    Statement st = connection.createStatement();
                    st.executeQuery(query);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                elev = "";
                elev_id = 0;
                autoCompleteTextView.setText("");
                editText.setText("");
                Toast.makeText(Note.this, "Nota stearsa cu succes!", Toast.LENGTH_SHORT).show();
            }
        });

        btnVizualizare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Note.this, ListaNote.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        month = month+1;
        data =year + "-" + month + "-" + dayOfMonth;
        Toast.makeText(this,"Data selectata: "+ data, Toast.LENGTH_SHORT).show();
    }
}