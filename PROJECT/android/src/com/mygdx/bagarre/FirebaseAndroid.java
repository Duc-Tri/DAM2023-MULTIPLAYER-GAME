package com.mygdx.bagarre;

import android.util.Log;

import androidx.annotation.NonNull;
<<<<<<< Updated upstream

import com.badlogic.gdx.backends.android.AndroidFiles;
import com.badlogic.gdx.files.FileHandle;
import com.google.firebase.FirebaseApp;
=======
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
>>>>>>> Stashed changes
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
<<<<<<< Updated upstream
import com.google.firebase.database.ValueEventListener;
import com.mygdx.firebase.Firebase;
=======

import java.util.List;
>>>>>>> Stashed changes

import java.util.ArrayList;
import java.util.List;

public class FirebaseAndroid {

    FirebaseDatabase db;
    DatabaseReference refPseudo;
    LobbyAdapter adapter;

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

<<<<<<< Updated upstream
    public interface OnListReadyListener {
        void onListReady(List<String> listPseudo);
    }

    public void getListRecycler(OnListReadyListener listener) {
        final List<String> listPseudo = new ArrayList<>();
        refPseudo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snap : snapshot.getChildren()) {
                    String pseudoPlayers = (String) snap.getValue(Object.class);
                    Log.i("PSEUDO", pseudoPlayers);
                    listPseudo.add(pseudoPlayers);
                    Log.i("PSEUDO_IN_LIST", listPseudo.get(0));
                }
                listener.onListReady(listPseudo);
=======
    public String majLobbyPseudo(String pseudo, int NumLobby) {

        DatabaseReference refLobby = db.getReference("Lobby" + NumLobby);
        String childRef = refLobby.push().getKey();

        refLobby.child(childRef).setValue(pseudo, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Log.i("LOBBY_LIST", pseudo + " ajouté dans le Lobby N°"+NumLobby);
            }
        });

        return childRef;
    }

    public List<String> recyclerPseudo(int NumLobby) {

        List<String> listPseudo = null;
        DatabaseReference refLobby = db.getReference("Lobby" + NumLobby);

        refLobby.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String nomJoueur = snapshot.getValue(String.class);
                listPseudo.add(nomJoueur);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

>>>>>>> Stashed changes
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
<<<<<<< Updated upstream
                Log.e("LIST_FAILED", "Erreur de lecture des données");
            }
        });

=======

            }
        });
         return listPseudo;
>>>>>>> Stashed changes
    }

}
