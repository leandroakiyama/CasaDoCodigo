package br.com.caelum.casadocodigo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import br.com.caelum.casadocodigo.FCM.FCMRegister;
import br.com.caelum.casadocodigo.R;
import br.com.caelum.casadocodigo.component.CasaDoCodigoApplication;
import br.com.caelum.casadocodigo.component.CasaDoCodigoComponent;
import br.com.caelum.casadocodigo.evento.SignInEvent;
import br.com.caelum.casadocodigo.server.WebClient;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by android6587 on 25/01/17.
 */

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private boolean flagLogado;
    private WebClient client;

    @Inject
    public void setWebClient(WebClient client) {this.client = client; }


    @BindView(R.id.login_email)
    EditText login_email;

    @BindView(R.id.login_senha)
    EditText login_senha;

    @BindView(R.id.login_logar)
    Button login_logar;

    @BindView(R.id.login_novo)
    Button login_novo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        CasaDoCodigoApplication application = (CasaDoCodigoApplication) getApplication();
        CasaDoCodigoComponent component = application.getComponent();
        component.inject(this);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null && !flagLogado) {
                    flagLogado=true;
                    String id = new FCMRegister().registra();
                    client.enviaDadosFirebaseParaServidor(user.getEmail(), id);

                    EventBus.getDefault().post(new SignInEvent());
                } else {
                    // User is signed out

                }
                // ...
            }
        };

        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    //Implementação manual
    @Subscribe
    public void vaiParaMain(SignInEvent event){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAuth.removeAuthStateListener(mAuthListener);
    }

    @OnClick(R.id.login_novo)
    public void cadastrarUsuario(){
        createAccount(login_email.getText().toString().trim(), login_senha.getText().toString().trim());
    }

    private void createAccount(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {

                        }

                        // ...
                    }
                });
    }

    @OnClick(R.id.login_logar)
    public void logon(){
        logonAccount(login_email.getText().toString().trim(), login_senha.getText().toString().trim());
    }
    private void logonAccount(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "E-mail ou senha incorretos.", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

}
