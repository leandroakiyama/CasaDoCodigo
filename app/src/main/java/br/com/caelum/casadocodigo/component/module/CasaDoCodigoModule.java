package br.com.caelum.casadocodigo.component.module;

import javax.inject.Singleton;

import br.com.caelum.casadocodigo.converter.LivroServiceConverterFactory;
import br.com.caelum.casadocodigo.modelo.Carrinho;
import br.com.caelum.casadocodigo.server.DeviceService;
import br.com.caelum.casadocodigo.server.LivrosService;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by android6587 on 25/01/17.
 */
@Module
public class CasaDoCodigoModule {
    @Provides
    @Singleton
    public Carrinho getCarrinho(){
        return new Carrinho();
    }

    @Provides
    @Singleton
    public Retrofit getRetroFit(){
        return new Retrofit.Builder()
                .baseUrl("http://cdcmob.herokuapp.com/")
                .addConverterFactory(new LivroServiceConverterFactory())
                .build();
    }

    @Provides
    public LivrosService getLivrosService(Retrofit retrofit){
        return retrofit.create(LivrosService.class);
    }

    @Provides
    @Singleton
    public DeviceService getDeviceService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.141.106:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DeviceService service = retrofit.create(DeviceService.class);

        return service;
    }



}
