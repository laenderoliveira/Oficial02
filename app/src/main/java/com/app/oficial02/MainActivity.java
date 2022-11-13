package com.app.oficial02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button _bntIniciar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _bntIniciar = findViewById(R.id.bntIniciar);

        _bntIniciar.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), EstadosActivity.class);
            startActivity(intent);
        });
    }
}