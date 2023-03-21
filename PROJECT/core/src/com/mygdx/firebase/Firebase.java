package com.mygdx.firebase;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Firebase {

    String url;//"https://damcorp-bc7bc-default-rtdb.firebaseio.com/","json/key.json"
    String jsonPath;//"json/key.json"
//    DatabaseReference ref;
//    FirebaseApp firebaseApp;
//    private FirebaseOptions options;
//    FirebaseDatabase firebaseDatabase;
//    byte[] byteArray;
//    FileHandle filehandle;
//    public Firebase(String url, String jsonPath) {
//        this.url = url;
//        this.jsonPath = jsonPath;
//        filehandle= Gdx.files.internal(jsonPath);
//        byteArray = filehandle.readBytes();
//    }
//    public boolean connect(){
//        try {
//            InputStream inputStream = new ByteArrayInputStream(byteArray);
//            try {
//                options = new FirebaseOptions.Builder()
//                        .setCredentials(GoogleCredentials.fromStream(inputStream))
//                        .setDatabaseUrl(url)
//                        .build();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//
//            firebaseApp = FirebaseApp.initializeApp(options);
//        } catch (RuntimeException e) {
//            throw new RuntimeException(e);
//        }
//        if(firebaseApp!= null){
//            firebaseDatabase = FirebaseDatabase.getInstance(FirebaseApp.getInstance());
//
//            return true;
//        }else{
//            return false;
//         }
//
//    }
//    public void updateUser() {
//        ref = firebaseDatabase.getReference("message");
//        ref.setValue("Text a ecrire ",new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                System.out.println("onComplete  " );
//            }
//        });
//    }
//    public void displayJson(){
//        String stringFileHandle = filehandle.readString();
//        System.out.println("displayJson    " + stringFileHandle);
//    }

}
