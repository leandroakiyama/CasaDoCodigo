package br.com.caelum.casadocodigo.component;

import javax.inject.Singleton;

import br.com.caelum.casadocodigo.activity.CarrinhoActivity;
import br.com.caelum.casadocodigo.activity.LoginActivity;
import br.com.caelum.casadocodigo.activity.MainActivity;
import br.com.caelum.casadocodigo.component.module.CasaDoCodigoModule;
import br.com.caelum.casadocodigo.fragment.DetalheLivroFragment;
import br.com.caelum.casadocodigo.fragment.ListaLivrosFragment;
import dagger.Component;

/**
 * Created by android6587 on 25/01/17.
 */

@Singleton
@Component(modules = CasaDoCodigoModule.class)
public interface CasaDoCodigoComponent {
    void inject(DetalheLivroFragment fragment);
    void inject(CarrinhoActivity activity);
    void inject(MainActivity mainActivity);

    void inject(ListaLivrosFragment fragment);

    void inject(LoginActivity loginActivity);
}
