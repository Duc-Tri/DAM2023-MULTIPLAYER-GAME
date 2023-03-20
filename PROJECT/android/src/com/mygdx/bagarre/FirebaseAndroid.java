package com.mygdx.bagarre;

import android.util.Log;
import com.badlogic.gdx.backends.android.AndroidFiles;
import com.badlogic.gdx.files.FileHandle;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mygdx.firebase.Firebase;

public class FirebaseAndroid {

    FirebaseDatabase db;
    DatabaseReference refPseudo;

    public FirebaseAndroid() {
        db = FirebaseDatabase.getInstance("https://test-e782f-default-rtdb.europe-west1.firebasedatabase.app/");
        refPseudo = db.getReference("players");
    }
    
    public String registerUser(String pseudo){

        String userId = refPseudo.push().getKey();

        refPseudo.child(userId).setValue(pseudo, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Log.i("WRITED_ON_BASE", pseudo + " écris avec succès");
                Log.i("USER_ID", userId);
            }
        });

        return userId;
    }

    public void deleteAllPseudos() {

        refPseudo.removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Log.i("REF_PSEUDO_EMPTIED", "La référence des pseudos de la base de donnée a été vidée");
            }
        });
    }

    public void suppressUnusedPseudo(String childRef, String oldPseudo) {

        refPseudo.child(childRef).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Log.i("UNUSED_PSEUDO_DELETED", "Le pseudo " + oldPseudo + " n'était plus utilisé et a été supprimé de la base de donnée");
            }
        });
    }

}
