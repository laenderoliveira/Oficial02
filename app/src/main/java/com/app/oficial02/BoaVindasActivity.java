package com.app.oficial02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class BoaVindasActivity extends AppCompatActivity {

    TextView _txtBoasVindas;
    Button _bntListarEscolar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boa_vindas);

        _txtBoasVindas = findViewById(R.id.idTextBoasVindas);
        _bntListarEscolar = findViewById(R.id.idBntListarEscolas);

        Bundle dados = getIntent().getExtras();

        String cidade = (String) dados.getSerializable("cidade");

        String cidadeDescricao = cidade.split(":")[1];
        String cidadeCodigo = cidade.split(":")[0];

        String mensagem = "SEJA BEM-VINDO A " + cidadeDescricao;

        if (cidadeCodigo.equals("3114204")){
            mensagem += "! A MELHOR CIDADE DO BRASIL!!";
        }

        _txtBoasVindas.setText(mensagem);

        _bntListarEscolar.setOnClickListener(view -> {

            Intent intent = new Intent(getApplicationContext(), EscolasActivity.class);
                intent.putExtra("codigoCidade", cidade.split(":")[0]);
                startActivity(intent);
        });
    }
}