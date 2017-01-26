package br.com.caelum.casadocodigo.fragment;

import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.BundleCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import br.com.caelum.casadocodigo.R;
import br.com.caelum.casadocodigo.activity.MainActivity;
import br.com.caelum.casadocodigo.adapter.LivroAdapter;
import br.com.caelum.casadocodigo.component.CasaDoCodigoApplication;
import br.com.caelum.casadocodigo.endlessList.EndlessListListener;
import br.com.caelum.casadocodigo.infra.infra;
import br.com.caelum.casadocodigo.modelo.Autor;
import br.com.caelum.casadocodigo.modelo.Livro;
import br.com.caelum.casadocodigo.server.LivrosService;
import br.com.caelum.casadocodigo.server.WebClient;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by android6587 on 23/01/17.
 */

public class ListaLivrosFragment extends Fragment {

    private ArrayList<Livro> livros = new ArrayList<>();
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    @Inject
    WebClient client;

    @BindView(R.id.lista_livros) RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_lista_livros, container, false);
        CasaDoCodigoApplication application = (CasaDoCodigoApplication) getActivity().getApplication();

        application.getComponent().inject(this);

        //Bind utilizando o ButterKnife
        ButterKnife.bind(this, view);

        //Lista manual
        //Código removido para receber a lista de livros apenas no método populaListaCom()
        /*List<Livro> livros = new ArrayList<>();
        for(int i=0; i<6; i++){
            Autor autor = new Autor();
            autor.setNome("Autor " + i);
            Livro livro = new Livro("Livro " + i, "Descrição " + i, Arrays.asList(autor));
            livros.add(livro);
        }*/
        recyclerView.setAdapter(new LivroAdapter(livros));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //Inicia tratamento do scroll infinito
        recyclerView.addOnScrollListener(new EndlessListListener(){
            @Override
            public void carregaMaisItens() {
                client.getLivros(livros.size(), 10);
            }
        });




        return view;
    }

    private void criaEConfiguraRemoteConfig() {
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        mFirebaseRemoteConfig.setDefaults(R.xml.preferencias);


        mFirebaseRemoteConfig.fetch(15).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Once the config is successfully fetched it must be activated before newly fetched
                    // values are returned.
                    mFirebaseRemoteConfig.activateFetched();
                }
            }
        });

    }

    @BindView(R.id.view)
    View include;

    @Override
    public void onResume() {
        super.onResume();
        infra.retiraBotaoVoltar((AppCompatActivity)getActivity());
        removeInclude();
        executaConfiguracoes();
    }

    private void executaConfiguracoes() {
        if(mFirebaseRemoteConfig.getBoolean("Portugues")){
            Toast.makeText(getContext(), "Seu idioma é Portugês do Brasil.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(), "Seu idioma não é do BR.", Toast.LENGTH_SHORT).show();
        }
    }

    private void removeInclude() {
        if (!livros.isEmpty()){
            include.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("lista", livros);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.getSerializable("lista") != null){
            livros = (ArrayList<Livro>) savedInstanceState.getSerializable("lista");
        }

        criaEConfiguraRemoteConfig();
    }

    public void populaListaCom(final List<Livro> livros){
        //this.livros.clear();
        this.livros.addAll(livros);
        recyclerView.getAdapter().notifyDataSetChanged();

        removeInclude();
    }


}
