package br.com.caelum.casadocodigo.server;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import br.com.caelum.casadocodigo.component.CasaDoCodigoComponent;
import br.com.caelum.casadocodigo.converter.LivroServiceConverterFactory;
import br.com.caelum.casadocodigo.evento.LivroEvent;
import br.com.caelum.casadocodigo.modelo.Livro;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by android6587 on 24/01/17.
 */

public class WebClient {

    LivrosService service;
    DeviceService deviceService;

    @Inject
    public WebClient(LivrosService service, DeviceService deviceService){
        this.service = service;
        this.deviceService = deviceService;
    }


    public void getLivros(int indicePrimeiroLivro, int qtdLivros){
        //Recuperar a listagem de livros
        /*Retrofit client = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(new LivroServiceConverterFactory())
                .build();

        LivrosService service = client.create(LivrosService.class);*/

        Call<List<Livro>> call = service.listaLivros(indicePrimeiroLivro, qtdLivros);
        //Chamada e tratamento de sucesso e erro da chamada do webService
        call.enqueue(new Callback<List<Livro>>() {
            @Override
            public void onResponse(Call<List<Livro>> call, Response<List<Livro>> response) {
                //delegate.lidaComSucesso(response.body());
                EventBus.getDefault().post(new LivroEvent(response.body()));
            }

            @Override
            public void onFailure(Call<List<Livro>> call, Throwable t) {
                //delegate.lidaComErro(t);
                EventBus.getDefault().post(t);
            }
        });
    }

    public void enviaDadosFirebaseParaServidor(String email, String id) {
        Call<String> call = deviceService.mandaTokenParaServer(email, id);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("CasaDoCodigo", "code : " + response.code());
                Log.i("CasaDoCodigo", "" + response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("CasaDoCodigo", t.getMessage());
            }
        });


    }
}
