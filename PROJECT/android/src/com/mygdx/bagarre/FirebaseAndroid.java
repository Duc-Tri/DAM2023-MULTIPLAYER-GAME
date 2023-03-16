package com.mygdx.bagarre;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidFiles;
import com.badlogic.gdx.backends.android.ZipResourceFile;
import com.badlogic.gdx.files.FileHandle;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FirebaseAndroid {

    String url;//"https://damcorp-bc7bc-default-rtdb.firebaseio.com/","json/key.json"
    String jsonPath;//"json/key.json"
    DatabaseReference ref, refPseudo;
    FirebaseApp firebaseApp;
    FirebaseDatabase firebaseDatabase;
    byte[] byteArray;
    FileHandle filehandle;
    AndroidFiles androidFiles;
    Context mContext;

    public FirebaseAndroid(String url, String jsonPath, Context context) {
        this.url = url;
        this.jsonPath = jsonPath;
        mContext = context;
        AssetManager assetManager = mContext.getAssets();
        androidFiles = new AndroidFiles() {
            @Override
            public boolean setAPKExpansion(int mainVersion, int patchVersion) {
                return false;
            }

            @Override
            public ZipResourceFile getExpansionFile() {
                return null;
            }

            @Override
            public FileHandle getFileHandle(String path, FileType type) {
                return null;
            }

            @Override
            public FileHandle classpath(String path) {
                return null;
            }

            @Override
            public FileHandle internal(String jsonPath) {
                return new AndroidFile(assetManager, jsonPath, FileType.Internal);
            }

            @Override
            public FileHandle external(String path) {
                return null;
            }

            @Override
            public FileHandle absolute(String path) {
                return null;
            }

            @Override
            public FileHandle local(String path) {
                return null;
            }

            @Override
            public String getExternalStoragePath() {
                return null;
            }

            @Override
            public boolean isExternalStorageAvailable() {
                return false;
            }

            @Override
            public String getLocalStoragePath() {
                return null;
            }

            @Override
            public boolean isLocalStorageAvailable() {
                return false;
            }
        };

        filehandle = androidFiles.internal(jsonPath);
        byteArray = filehandle.readBytes();
    }

    public boolean connect(){
        try {
            InputStream inputStream = new ByteArrayInputStream(byteArray);
            FirebaseOptions options;
            try {
                options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(inputStream))
                        .setDatabaseUrl(url)
                        .build();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            firebaseApp = FirebaseApp.initializeApp(options);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        if(firebaseApp!= null){
            firebaseDatabase = FirebaseDatabase.getInstance(FirebaseApp.getInstance());

            return true;
        }else{
            return false;
         }

    }
    public void updateUser() {
        ref = firebaseDatabase.getReference("message");
        ref.setValue("Text a ecrire ",new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                System.out.println("onComplete  " );
            }
        });
    }

    public String registerUser(String pseudo){
        refPseudo = firebaseDatabase.getReference("Pseudo");
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

    public void displayJson(){
        String stringFileHandle = filehandle.readString();
        System.out.println("displayJson    " + stringFileHandle);
    }

    public void deleteAllPseudos() {
        refPseudo = firebaseDatabase.getReference("Pseudo");
        refPseudo.removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Log.i("REF_PSEUDO_EMPTIED", "La référence des pseudos de la base de donnée a été vidée");
            }
        });
    }

    public void suppressUnusedPseudo(String childRef, String oldPseudo) {
        refPseudo = firebaseDatabase.getReference("Pseudo");
        refPseudo.child(childRef).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Log.i("UNUSED_PSEUDO_DELETED", "Le pseudo " + oldPseudo + " n'était plus utilisé et a été supprimé de la base de donnée");
            }
        });
    }

}
