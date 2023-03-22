package com.mygdx.bagarre;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LobbyAdapter extends RecyclerView.Adapter<LobbyAdapter.MyViewHolder>{

    Context context;
    private List<String> joueursPseudo;
    /*String[] numLobby;*/

    public LobbyAdapter() {
    }
	
    public LobbyAdapter(Context context,List<String> joueursPseudo ) {
        this.context = context;
        this.joueursPseudo = joueursPseudo;
    }

    /*public LobbyAdapter(Context context,String[] numLobby ) {
        this.context = context;
        this.numLobby = numLobby;
    }*/

    @NonNull
    @Override
    public LobbyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_list_player_minimized,parent,false);
        return new LobbyAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LobbyAdapter.MyViewHolder holder, int position) {
        String pseudo = joueursPseudo.get(position);
        holder.tvPlayerID2.setText(pseudo);
    }

    @Override
    public int getItemCount() {
        return joueursPseudo.size();
        /*return numLobby.length;*/
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
		
        private TextView tvPlayerID2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPlayerID2 = itemView.findViewById(R.id.tvPlayerID2);
			
        /*private TextView tvNomLobby;
        private Button btnJoinLobby;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNomLobby = itemView.findViewById(R.id.tvNomLobby);
            btnJoinLobby = itemView.findViewById(R.id.btnJoinLobby);*/
			
        }
    }
}
