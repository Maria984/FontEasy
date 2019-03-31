package com.example.fonteasy;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.signin.SignIn;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    // Fuentes

    private TextView button5;
    private TextView button6;
    private TextView button7;
    private TextView button8;
    private TextView button9;
    private TextView ingreso;
    private TextView Header;

    private Typeface Encode;
    private Typeface TWLight;
    private Typeface TWBold;

    // Fuentes

    private GoogleApiClient googleApiClient;

    private SignInButton signInButton;

    public static final int SIGN_IN_CODE = 777;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Fuente pie

        String fuente3 = "fuentes/TWLight.ttf";
        this.TWLight = Typeface.createFromAsset(getAssets(),fuente3);

        button5 = (TextView) findViewById(R.id.button5);
        button5.setTypeface(TWLight);

        button6 = (TextView) findViewById(R.id.button6);
        button6.setTypeface(TWLight);

        button7 = (TextView) findViewById(R.id.button7);
        button7.setTypeface(TWLight);

        button8 = (TextView) findViewById(R.id.button8);
        button8.setTypeface(TWLight);

        button9 = (TextView) findViewById(R.id.button9);
        button9.setTypeface(TWLight);

        // Fuente header
        String fuente2 = "fuentes/Encode.ttf";
        this.Encode = Typeface.createFromAsset(getAssets(),fuente2);

        Header = (TextView) findViewById(R.id.Header);
        Header.setTypeface(Encode);

        String fuente4 = "fuentes/TWBold.ttf";
        this.TWBold = Typeface.createFromAsset(getAssets(),fuente4);

        ingreso = (TextView) findViewById(R.id.ingreso);
        ingreso.setTypeface(TWBold);

        // Fuentes

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();



        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInButton = (SignInButton) findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN_CODE);
            }
        });




    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            goMainScreen();

        }
        else {
            Toast.makeText(this, "No se pudo iniciar sesión", Toast.LENGTH_SHORT).show();
        }
    }

    private void goMainScreen() {
        Intent intent = new Intent(this, ScreenUno.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);


    }
    public void perfil (View view){
        Intent perfil = new Intent (this, Main2Activity.class);
        startActivity(perfil);

    }
    public void camara (View view){
        Intent camara = new Intent (this, ClassifierActivity.class);
        startActivity(camara);

    }
    public void buscar (View view){
        Intent buscar = new Intent (this, VerFuentes.class);
        startActivity(buscar);

    }
    public void usuario (View view){
        Intent usuario = new Intent (this, ScreenUno.class);
        startActivity(usuario);

    }
    public void volver1 (View view){
        Intent volver1 = new Intent (this, Main2Activity.class);
        startActivity(volver1);

    }


}
