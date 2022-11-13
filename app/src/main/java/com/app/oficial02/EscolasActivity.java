package com.app.oficial02;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EscolasActivity extends AppCompatActivity {

    private APIInterface _apiInterface;
    private ListView _listaEscolas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolas);


        ProgressDialog dialogDeCarregamento = ProgressDialog.show(EscolasActivity.this, "",
                "Carregando. Aguarde...", true);

        _listaEscolas = findViewById(R.id.idListaEscolas);
        _apiInterface = APIClient.getClient().create(APIInterface.class);


        _listaEscolas.setOnItemClickListener((parent, view, position, id) -> {

            String escolaSelecionada = (String) _listaEscolas.getItemAtPosition(position);

            Log.i("Escola selcionada", escolaSelecionada);

            Intent intent = new Intent(getApplicationContext(), DetalheEscolaActivity.class);
            intent.putExtra("codigoEscola", escolaSelecionada.split(":")[0]);
            startActivity(intent);
        });

        Bundle dados = getIntent().getExtras();
        int codigoCidade = Integer.parseInt(dados.getSerializable("codigoCidade").toString());

        Call<List<Object>> call = _apiInterface.listarEscolarPorCidade(codigoCidade);

        call.enqueue(new Callback<List<Object>>() {
            @Override
            public void onResponse(Call<List<Object>> call, Response<List<Object>> response) {


                // Foi utilizado object porque a API retorna uma lista de objetos de tipo distintos.
                List<Object> escolas = response.body();

                Double quantidadeEscolas = (Double) escolas.get(0);

                if (quantidadeEscolas > 0){
                    List<LinkedTreeMap> escolasViewModels = (List<LinkedTreeMap>) escolas.get(1);
                    carregarDadosEscolas(escolasViewModels);

                    Log.i("Escolas carregadas", "Quantidade " + quantidadeEscolas.toString());

                    Toast.makeText(EscolasActivity.this, "Escolas listadas", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(EscolasActivity.this, "Não possui escolas para cidade selecionada.", Toast.LENGTH_SHORT).show();
                    finish();
                }

                dialogDeCarregamento.dismiss();
            }

            @Override
            public void onFailure(Call<List<Object>> call, Throwable t) {
                call.cancel();

                dialogDeCarregamento.dismiss();

                Toast.makeText(EscolasActivity.this, "Problema ao consultar dados, verifique sua conexão e tente novamente.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), EstadosActivity.class);
                startActivity(intent);
            }
        });
    }

    public void carregarDadosEscolas(List<LinkedTreeMap> escolas) {

        List<String> nomesEscolas = new ArrayList<>();

        for (LinkedTreeMap escola: escolas
             ) {

            int codigoEscola = (int) Double.parseDouble(escola.get("cod").toString());

            nomesEscolas.add(  codigoEscola + ":" + escola.get("nome").toString());
        }

        ArrayAdapter<String> cidadesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nomesEscolas);
        _listaEscolas.setAdapter(cidadesAdapter);
    }
}