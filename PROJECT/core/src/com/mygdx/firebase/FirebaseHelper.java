package com.mygdx.firebase;

import com.mygdx.firebase.Firebase;

public class FirebaseHelper {

    public FirebaseHelper(String firebaseURL) {
        Firebase firebase = new Firebase(firebaseURL, "json/key.json");
        firebase.displayJson();
        firebase.connect();
        firebase.updateUser();

    }

    //public String getFirebaseURL() {        return firebaseURL;    }

    //public void setFirebaseURL(String firebaseURL) {        //this.firebaseURL = firebaseURL;    }

}
