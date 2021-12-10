package edu.co.icesi.firebasefacebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        callbackManager = CallbackManager.Factory.create();


        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e(">>>", "Exito");
                AccessToken token = loginResult.getAccessToken();
                AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
                FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(
                        task -> {
                            Log.e(">>>", "Exito Firebase");
                        }
                );
            }

            @Override
            public void onCancel() {
                Log.e(">>>", "onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.e(">>>", "onError: "+error.getLocalizedMessage());
            }
        });



        findViewById(R.id.loginBtn).setOnClickListener(
                v->{
                    ArrayList<String> permissions = new ArrayList<>();
                    permissions.add("email");
                    LoginManager.getInstance().logInWithReadPermissions(this, callbackManager, permissions);


                }
        );


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


}