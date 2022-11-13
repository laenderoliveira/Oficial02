package com.app.oficial02;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CidadesActivity extends AppCompatActivity {

    private APIInterface _apiInterface;
    private ListView _listaCidades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cidades);

        _listaCidades = findViewById(R.id.idListaCidades);

        _apiInterface = APIClient.getClient().create(APIInterface.class);

        Bundle dados = getIntent().getExtras();

        String estado = (String) dados.getSerializable("estado");

        ProgressDialog dialogDeCarregamento = ProgressDialog.show(CidadesActivity.this, "",
                "Carregando. Aguarde...", true);

        Call<List<String>> call = _apiInterface.listarCidades(estado);

        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {

                List<String> cidades = response.body();
                carregarDadosCidades(cidades);

                dialogDeCarregamento.dismiss();

                _listaCidades.setOnItemClickListener((parent, view, position, id) -> {

                    String cidadeSelecionada = (String) _listaCidades.getItemAtPosition(position);

                    Log.i("Cidade selcionada", cidadeSelecionada);

                    Intent intent = new Intent(getApplicationContext(), BoaVindasActivity.class);
                    intent.putExtra("cidade", cidadeSelecionada.toUpperCase());
                    startActivity(intent);
                });

                Toast.makeText(CidadesActivity.this, String.format("%d cidade listadas", _listaCidades.getCount()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                call.cancel();

                dialogDeCarregamento.dismiss();

                Toast.makeText(CidadesActivity.this, "Problema ao consultar dados, verifique sua conexão e tente novamente.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), EstadosActivity.class);
                startActivity(intent);
            }
        });
    }

    public void carregarDadosCidades(List<String> cidades) {

        ArrayAdapter<String> cidadesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cidades);
        _listaCidades.setAdapter(cidadesAdapter);
    }
}

