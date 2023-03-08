package com.mygdx.bagarre;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Objects;

public class LoginPage extends AppCompatActivity {

    private Button registerBtn, connexionBtn;
    private TextInputEditText tiPseudo;
    String pseudo;

    public void initUi(){
        registerBtn = findViewById(R.id.registerBtn);
        tiPseudo = findViewById(R.id.tiPseudo);
        connexionBtn = findViewById(R.id.connexionBtn);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        //Sharedreferences
        SharedPreferences prefs = getSharedPreferences("pref_pseudo", this.MODE_PRIVATE);

        //Initialisation de Firebase
        FirebaseAndroid db = new FirebaseAndroid("https://test-e782f-default-rtdb.europe-west1.firebasedatabase.app", "json/keyTest782f.json", this);

        //Test de connexion
        if(db.connect()) {
            Log.i("CONNEXION_ENABLED", "La connexion a étée établie");
        }

        //Initialisation des vues
        initUi();

        //Création de l'intent pour le changement d'activité
        Intent itGameMode = new Intent(LoginPage.this, GameMode.class);

        //Check s'il y a des données dans les préférences
        pseudo = prefs.getString("pseudo", null);
        if(pseudo != null) {
            //Change ce qui est écris sur le bouton d'enregistrement
            registerBtn.setText(R.string.change_account);

            //Affiche le bouton Continuer avec
            String textConnexionButton = "Continuer avec " + pseudo;
            SpannableString pseudoRed = new SpannableString(textConnexionButton);

            // Chercher l'index où commence le pseudo
            int startIndex = textConnexionButton.indexOf(pseudo);

            // Ajouter un ForegroundColorSpan rouge autour du pseudo
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.RED);
            pseudoRed.setSpan(colorSpan, startIndex, startIndex + pseudo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            connexionBtn.setText(pseudoRed);
            connexionBtn.setVisibility(View.VISIBLE);
        }

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Réccupération du pseudo
                pseudo = Objects.requireNonNull(tiPseudo.getText()).toString();

                //Enregistre des préférences pour la prochaine connexion
                SharedPreferences.Editor editPref = prefs.edit();
                editPref.putString("pseudo", pseudo);
                editPref.apply();

                //Ecriture en base
                db.registerUser(pseudo);

                //Changement d'activité en envoyant le pseudo pour l'afficher dans la prochaine activité
                itGameMode.putExtra("pseudoToDisplay", pseudo);
                startActivity(itGameMode);
            }
        });

        connexionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itGameMode.putExtra("pseudoToDisplay", pseudo);
                startActivity(itGameMode);
            }
        });


    }
}