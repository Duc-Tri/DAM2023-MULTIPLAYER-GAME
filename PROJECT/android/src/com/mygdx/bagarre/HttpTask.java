package com.mygdx.bagarre;

import android.os.AsyncTask;

import com.mygdx.client.RetrieveLobbies;

import java.net.MalformedURLException;

public class HttpTask extends AsyncTask<Void, Void, String[]> {


    private OnHttpResultListener listener;

    public HttpTask(OnHttpResultListener listener) {
        this.listener = listener;
    }

    @Override
    protected String[] doInBackground(Void... voids) {

        String[] listLobby;
        try {
            listLobby = RetrieveLobbies.requestServer();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        return listLobby;
    }

    @Override
    protected void onPostExecute(String[] result) {
        if (listener != null) {
            listener.onHttpResult(result);
        }
    }

    public interface OnHttpResultListener {
        void onHttpResult(String[] result);
    }


}
