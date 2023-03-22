package com.mygdx.bagarre;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mygdx.client.RetrieveLobbies;
import java.net.MalformedURLException;

public class ListLobbyAdapter extends RecyclerView.Adapter<ListLobbyAdapter.MyViewHolder> {

    Context context;
//    int[] nbrJoueur;
    String[] numLobby;

    public ListLobbyAdapter() {
    }


    public ListLobbyAdapter(Context context,int[] numLobby/*, int[] nbrJoueur*/ ) {
        this.context = context;
        this.numLobby = numLobby;
//        this.nbrJoueur = nbrJoueur;

    /*public ListLobbyAdapter(Context context,String[] numLobby ) {
        this.context = context;
        this.numLobby = numLobby;
		
    }*/

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_list_lobby,parent,false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvNomLobby.setText("Lobby n° "+numLobby[position]);
        /*holder.tvNbrJoueur.setText(nbrJoueur[position]);*/
    }



    @Override
    public int getItemCount() {
        return numLobby.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
		
        private TextView tvNomLobby, tvNbrJoueur;
        private Button btnJoinLobby;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNomLobby = itemView.findViewById(R.id.tvNomLobby);
            tvNbrJoueur = itemView.findViewById(R.id.tvNbrJoueur);
            btnJoinLobby = itemView.findViewById(R.id.btnJoinLobby);
        }
    }
}
