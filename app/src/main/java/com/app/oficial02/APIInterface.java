package com.app.oficial02;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface APIInterface {
    @GET("/api/cidades/{estado}")
    Call<List<String>> listarCidades(@Path("estado") String user);

    // Foi utilizado object porque a API retorna uma lista de objetos de tipo distintos.
    @GET("api/escolas/buscaavancada?")
    Call<List<Object>> listarEscolarPorCidade(@Query("cidade") int cidade);

    @GET("/api/escola/{escola}")
    Call<EscolaModel> buscarDetalhesEscola(@Path("escola") int escola);

}
