package com.example.catalog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.Objects;

public class AlegereClasa extends AppCompatActivity {
    String[] clase = {"9-A", "9-B", "10-A", "10-B", "11-A", "11-B", "12-A","12-B"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapter;
    static String clasa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alegere_clasa);
        setTitle("");
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));
        getSupportActionBar().hide();


        autoCompleteTextView = findViewById(R.id.auto_complete_txt);
        adapter = new ArrayAdapter<String>(AlegereClasa.this,R.layout.list_item,clase);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                clasa = adapterView.getItemAtPosition(i).toString();
                Intent intent = new Intent(AlegereClasa.this, AlegereActiune.class);
                startActivity(intent);
            }
        });
    }
    public static String returnClasa() {
        return clasa;
    }
}