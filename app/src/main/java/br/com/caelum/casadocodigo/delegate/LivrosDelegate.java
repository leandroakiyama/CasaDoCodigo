package br.com.caelum.casadocodigo.delegate;

import java.util.List;

import br.com.caelum.casadocodigo.modelo.Livro;

/**
 * Created by android6587 on 23/01/17.
 */

public interface LivrosDelegate {
    //Declaração de métodos a serem implementados na MainActivity
    public void lidaComLivroSelecionado(Livro livro);
    //public void lidaComSucesso(List<Livro> livros);
    //public void lidaComErro(Throwable erro);
}
