package com.app.oficial02;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalheEscolaActivity extends AppCompatActivity {

    private APIInterface _apiInterface;
    private EditText _inputNomeEscola;
    private EditText _inputEnderecoEscola;
    private EditText _inputSituacaoEscola;
    private EditText _inputDependenciaAdministrativaEscola;
    private Button _bntAbrirMapa;
    private EscolaModel _escola;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_escola);

        _apiInterface = APIClient.getClient().create(APIInterface.class);

        _inputEnderecoEscola = findViewById(R.id.idInputEnderecoEscola);
        _inputNomeEscola = findViewById(R.id.idInputNomeEscola);
        _inputSituacaoEscola = findViewById(R.id.idInputSituacaoEscola);
        _inputDependenciaAdministrativaEscola = findViewById(R.id.idInputDependenciaAdministrativaEscola);
        _bntAbrirMapa = findViewById(R.id.idBntAbrirMapa);

        ProgressDialog dialogDeCarregamento = ProgressDialog.show(DetalheEscolaActivity.this, "",
                "Carregando. Aguarde...", true);

        Bundle dados = getIntent().getExtras();
        int codigoEscola = Integer.parseInt(dados.getSerializable("codigoEscola").toString());

        Call<EscolaModel> call = _apiInterface.buscarDetalhesEscola(codigoEscola);

        call.enqueue(new Callback<EscolaModel>() {
            @Override
            public void onResponse(Call<EscolaModel> call, Response<EscolaModel> response) {

                _escola = response.body();

                carregarDadosEscola(_escola);

                dialogDeCarregamento.dismiss();
            }

            @Override
            public void onFailure(Call<EscolaModel> call, Throwable t) {

                call.cancel();

                dialogDeCarregamento.dismiss();

                Toast.makeText(DetalheEscolaActivity.this, "Problema ao consultar dados, verifique sua conexão e tente novamente.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), EstadosActivity.class);
                startActivity(intent);
            }
        });

        _bntAbrirMapa.setOnClickListener(view ->
        {
            String uri = String.format(Locale.ENGLISH, "geo:%f,%f", _escola.latitude, _escola.longitude);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            this.startActivity(intent);
        });
    }

    private void carregarDadosEscola(EscolaModel escola) {

        _inputNomeEscola.setText(escola.nome);
        _inputEnderecoEscola.setText(escola.endereco);
        _inputSituacaoEscola.setText(escola.situacaoFuncionamentoTxt);
        _inputDependenciaAdministrativaEscola.setText(escola.dependenciaAdministrativaTxt);

        disabilitarEditText(_inputNomeEscola);
        disabilitarEditText(_inputEnderecoEscola);
        disabilitarEditText(_inputSituacaoEscola);
        disabilitarEditText(_inputDependenciaAdministrativaEscola);
    }

    private void disabilitarEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setBackgroundColor(Color.TRANSPARENT);
    }
}