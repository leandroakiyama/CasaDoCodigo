package br.com.caelum.casadocodigo.infra;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by android6587 on 24/01/17.
 */

public class infra {
    public static void colocaBotaoVoltar(AppCompatActivity activity){
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static void retiraBotaoVoltar(AppCompatActivity activity){
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }
}
