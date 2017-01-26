package br.com.caelum.casadocodigo.evento;

import java.util.List;

import br.com.caelum.casadocodigo.modelo.Livro;

/**
 * Created by android6587 on 24/01/17.
 */

public class LivroEvent {

    private final List<Livro> livros;

    public LivroEvent(List<Livro> livros){
        this.livros = livros;
    }

    public List<Livro> getLivros(){
        return livros;
    }
}
