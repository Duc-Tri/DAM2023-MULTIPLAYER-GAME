package com.mygdx.bagarre;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListPlayerAdapter extends RecyclerView.Adapter<ListPlayerAdapter.MyViewHolder> {


    private Context context;
    private int[] illustrations;
    private String[] playerID;


    public ListPlayerAdapter() {
    }

    public ListPlayerAdapter(Context context, int[] illustrations, String[] playerID){
        this.context = context ;
        this.illustrations = illustrations;
        this.playerID = playerID;


    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater  layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_list_player, parent , false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListPlayerAdapter.MyViewHolder holder, int position) {
        holder.ivIllu.setImageResource(illustrations[position]);
        holder.tvPlayerID.setText(playerID[position]);

    }

    @Override
    public int getItemCount() {
        return playerID.length;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivIllu;
        private TextView tvPlayerID;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIllu = itemView.findViewById(R.id.ivIllu);
            tvPlayerID = itemView.findViewById(R.id.tvPlayerID);
        }
    }
}
