package com.app.oficial02;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EstadosActivity extends AppCompatActivity {

    private Button _bntListarCidades;
    private EditText _inputEstado;
    private List<String> _estadosValidos;
    private static final String ARQUIVOS_PREFERENCIA = "CONFIG_ESTADO";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estados);

        carregarEstadosValidos();

        _bntListarCidades = findViewById(R.id.idBntListarCidades);
        _inputEstado = findViewById(R.id.idInputEstado);
        _inputEstado.setInputType(4096); // Somente maiusculas

        SharedPreferences preferences = getSharedPreferences(ARQUIVOS_PREFERENCIA,0);
        SharedPreferences.Editor editor = preferences.edit();

        if (preferences.contains("estado")) {
            String estadoSalvo = preferences.getString("estado", "MG");
            _inputEstado.setText(estadoSalvo);
        }

        _bntListarCidades.setOnClickListener(view -> {

            Intent intent = new Intent(getApplicationContext(), CidadesActivity.class);

            String estado = _inputEstado.getText().toString().toUpperCase();

            if (!estado.isEmpty() && estado.length() == 2 && _estadosValidos.contains(estado.toUpperCase()) ){

                editor.putString("estado",estado);
                editor.commit();

                intent.putExtra("estado", estado);
                startActivity(intent);
            }
            else{
                Toast.makeText(this, "Estado inválido", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void carregarEstadosValidos(){

        _estadosValidos = new ArrayList<>();
        _estadosValidos.add("MG");
        _estadosValidos.add("SP");
        _estadosValidos.add("RJ");
        _estadosValidos.add("RS");
        _estadosValidos.add("SC");
        _estadosValidos.add("PR");
        _estadosValidos.add("RJ");
        _estadosValidos.add("ES");
        _estadosValidos.add("GO");
        _estadosValidos.add("MT");
        _estadosValidos.add("MS");
        _estadosValidos.add("DF");
        _estadosValidos.add("BA");
        _estadosValidos.add("CE");
        _estadosValidos.add("MA");
        _estadosValidos.add("PI");
        _estadosValidos.add("PE");
        _estadosValidos.add("AC");
        _estadosValidos.add("AM");
        _estadosValidos.add("RO");
        _estadosValidos.add("RR");
        _estadosValidos.add("AM");
        _estadosValidos.add("TO");
        _estadosValidos.add("SE");
        _estadosValidos.add("AL");
        _estadosValidos.add("PB");
    }
}