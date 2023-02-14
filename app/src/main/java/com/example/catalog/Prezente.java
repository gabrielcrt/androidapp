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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.transform.Result;

public class Prezente extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
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
        setContentView(R.layout.activity_prezente);
        setTitle("");
        getSupportActionBar().hide();

        //Conexiune baza de date
        DBconnect dBconnect = new DBconnect();
        connection = dBconnect.connect();

        //definire butoane
        FloatingActionButton btnData = findViewById(R.id.btnData);
        FloatingActionButton btnAdauga = findViewById(R.id.btnAdauga);
        FloatingActionButton btnSterge = findViewById(R.id.btnSterge);
        FloatingActionButton btnAfisare = findViewById(R.id.btnAfisare);

        //definire drop-down list
        autoCompleteTextView = findViewById(R.id.auto_complete_txt);
        adapter = new ArrayAdapter<String>(Prezente.this, R.layout.list_item, AlegereActiune.lista_elevi);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                elev = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(Prezente.this, "Elev selectat: " + elev, Toast.LENGTH_SHORT).show();
            }
        });
        // Fereastra dialog pentru data
        btnData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        // adauga prezenta
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
                    String query = "exec AdaugaPrezenta" + " " + "'"+data+"'" + "," + materie_id + "," + elev_id + "," + profesor_id;
                    Statement st = connection.createStatement();
                    st.executeQuery(query);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                autoCompleteTextView.setText("");
                elev_id = 0;
                elev = "";
                Toast.makeText(Prezente.this, "Prezenta adaugata cu succes!", Toast.LENGTH_SHORT).show();
            }
        });
        // sterge prezenta
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

                // Executie adaugare in baza de date
                try {
                    String query = "exec StergePrezenta" + " " + "'"+data+"'" + "," + materie_id + "," + elev_id + "," + profesor_id;
                    Statement st = connection.createStatement();
                    st.executeQuery(query);


                } catch (SQLException e) {
                    e.printStackTrace();
                }
                autoCompleteTextView.setText("");
                elev_id = 0;
                elev = "";
                Toast.makeText(Prezente.this, "Prezenta stearsa cu succes!", Toast.LENGTH_SHORT).show();
            }
        });
        btnAfisare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Prezente.this, ListaPrezente.class);
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