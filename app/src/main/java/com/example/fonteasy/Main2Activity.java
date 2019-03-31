package com.example.fonteasy;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    private TextView button;
    private TextView button2;
    private TextView button3;
    private TextView Header;

    private Typeface TWRegular;
    private Typeface Encode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        String fuente1 = "fuentes/TWRegular.ttf";
        this.TWRegular = Typeface.createFromAsset(getAssets(),fuente1);

        button = (TextView) findViewById(R.id.button);
        button.setTypeface(TWRegular);

        button2 = (TextView) findViewById(R.id.button2);
        button2.setTypeface(TWRegular);

        button3 = (TextView) findViewById(R.id.button3);
        button3.setTypeface(TWRegular);


        String fuente2 = "fuentes/Encode.ttf";
        this.Encode = Typeface.createFromAsset(getAssets(),fuente2);

        Header = (TextView) findViewById(R.id.Header);
        Header.setTypeface(Encode);


    }
    public void siguiente2 (View view){
        Intent siguiente2 = new Intent (this, ClassifierActivity.class);
        startActivity(siguiente2);

    }public void perfil (View view){
        Intent perfil = new Intent (this, MainActivity.class);
        startActivity(perfil);

    }
    public void siguiente3 (View view){
        Intent siguiente3 = new Intent (this, VerFuentes.class);
        startActivity(siguiente3);

    }
    public void siguiente4 (View view){
        Intent siguiente4 = new Intent (this, LoginActivity.class);
        startActivity(siguiente4);

    }




}
