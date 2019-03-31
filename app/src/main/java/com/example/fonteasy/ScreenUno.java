package com.example.fonteasy;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class ScreenUno extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private ImageView photoImageView;
    private TextView nameTextView;
    private TextView emailTextView;
    private TextView idTextView;

    private GoogleApiClient googleApiClient;

    // Fuentes

    private TextView button5;
    private TextView button6;
    private TextView button7;
    private TextView button8;
    private TextView button9;
    private TextView Header;

    private Typeface Encode;
    private Typeface TWLight;

    // Fuentes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_uno);



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

        // Fuentes



        photoImageView = (ImageView) findViewById(R.id.photoImageView);
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        emailTextView = (TextView) findViewById(R.id.emailTextView);
        idTextView = (TextView) findViewById(R.id.idTextView);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso )
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()){
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        }
        else {
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()){

            GoogleSignInAccount account = result.getSignInAccount();

            nameTextView.setText(account.getDisplayName());
            emailTextView.setText(account.getEmail());
            idTextView.setText(account.getId());

            Glide.with(this).load(account.getPhotoUrl()).into(photoImageView);

        } else{
            goLogInScreen();

        }
    }

    private void goLogInScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logOut (View view) {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()){
                    goLogInScreen();
                }
                else {
                    Toast.makeText(getApplicationContext(), getString(R.string.not_close_session), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    public void revoke(View view) {
        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()){
                    goLogInScreen();
                }
                else {
                    Toast.makeText(getApplicationContext(), getString(R.string.not_revoke), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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
    public void volver (View view){
        Intent volver = new Intent (this, Main2Activity.class);
        startActivity(volver);

    }
}
