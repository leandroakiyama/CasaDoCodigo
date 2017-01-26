package br.com.caelum.casadocodigo.endlessList;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by android6587 on 24/01/17.
 */

public abstract class EndlessListListener extends RecyclerView.OnScrollListener {

    private int qtdTotalItens;
    private int primeiroItemVisivel;
    private int qtdItensVisiveis;
    private boolean carregando = true;
    private int totalAnterior = 0;

    public abstract void carregaMaisItens();

    @Override
    public void onScrolled(RecyclerView recyclerView, int qtdScrollHorizontal, int qtdScrollVertical){
        super.onScrolled(recyclerView, qtdScrollHorizontal, qtdScrollVertical);

        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

        qtdTotalItens = layoutManager.getItemCount();
        primeiroItemVisivel = layoutManager.findFirstVisibleItemPosition();
        qtdItensVisiveis = layoutManager.getChildCount();

        //Calcula o limite para carregar
        int indiceLimiteParaCarregar = qtdTotalItens - qtdItensVisiveis - 5;

        //Verifica se o primeiro item visível já passou do limite de carregamento
        if (carregando){
            if(qtdTotalItens > totalAnterior){
                totalAnterior = qtdTotalItens;
                carregando = false;
            }
        }
        if (!carregando && primeiroItemVisivel >= indiceLimiteParaCarregar){
            carregaMaisItens();
            carregando = true;
        }

    }

}
