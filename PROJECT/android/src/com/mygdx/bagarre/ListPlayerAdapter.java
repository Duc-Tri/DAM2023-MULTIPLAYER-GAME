package com.mygdx.bagarre;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;

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
        View view = layoutInflater.inflate(R.layout.item_list_player_minimized, parent , false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListPlayerAdapter.MyViewHolder holder, int position) {
        holder.ivIllu2.setImageResource(illustrations[position]);
        holder.tvPlayerID2.setText(playerID[position]);

    }

    @Override
    public int getItemCount() {
        return playerID.length;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivIllu2;
        private TextView tvPlayerID2;
        private LinearLayout llMainLinearLayout;
        private View itemListExtended;
        HashMap<Integer, Integer> imgEquivalence = new HashMap<>();

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIllu2 = itemView.findViewById(R.id.ivIllu2);
            tvPlayerID2 = itemView.findViewById(R.id.tvPlayerID2);
            llMainLinearLayout= itemView.findViewById(R.id.llMainLinearLayout);
            itemListExtended = itemView.findViewById(R.id.itemListExtended);

            llMainLinearLayout.setOnClickListener(v -> {
                Log.i("onClick", "Je suis ans le setOnClick");

                if(itemListExtended.getVisibility() == View.GONE) {
                    itemListExtended.setVisibility(View.VISIBLE);
                    Animation anim = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.slide_down);
                    itemListExtended.startAnimation(anim);
                } else {
                    itemListExtended.setVisibility(View.GONE);
                    Animation anim = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.slide_up);
                    itemListExtended.startAnimation(anim);
                }
            });

            imgEquivalence.put(R.drawable.illu1, R.drawable.illu1_head);
            imgEquivalence.put(R.drawable.illu2, R.drawable.illu2_head);
            imgEquivalence.put(R.drawable.illu3, R.drawable.illu3_head);
            imgEquivalence.put(R.drawable.illu4, R.drawable.illu4_head);
            imgEquivalence.put(R.drawable.illu5, R.drawable.illu5_head);

        }
    }
}
