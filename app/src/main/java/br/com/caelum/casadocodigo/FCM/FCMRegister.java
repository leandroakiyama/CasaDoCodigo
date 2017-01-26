package br.com.caelum.casadocodigo.FCM;

import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by android6587 on 26/01/17.
 */

public class FCMRegister {
    public String registra(){
        FirebaseInstanceId instance = FirebaseInstanceId.getInstance();
        String token = instance.getToken();
        return token;
    }
}
