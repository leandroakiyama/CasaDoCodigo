package br.com.caelum.casadocodigo.server;

import java.util.List;

import br.com.caelum.casadocodigo.modelo.Livro;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by android6587 on 24/01/17.
 */

public interface LivrosService {
    //Definir qual a requisição faremos
    //@GET("listarLivros?indicePrimeiroLivro=0&qtdLivros=20")
    //Call<List<Livro>> listaLivros();

    @GET("listarLivros")
    Call<List<Livro>> listaLivros(@Query("indicePrimeiroLivro") int indicePrimeiroLivro, @Query("qtdLivros") int qtdLivros);


}

