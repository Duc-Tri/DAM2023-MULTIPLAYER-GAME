package com.mygdx.bagarre;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Layout;
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
        private LinearLayout llRecyclerView;
        private ConstraintLayout extendedView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIllu = itemView.findViewById(R.id.ivIllu);
            tvPlayerID = itemView.findViewById(R.id.tvPlayerID);
            llRecyclerView = itemView.findViewById(R.id.llRecyclerView);
            extendedView = itemView.findViewById(R.id.extendedView);

            llRecyclerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(extendedView.getVisibility() == View.GONE) {
                        extendedView.setVisibility(View.VISIBLE);

                        ivIllu.setImageResource(imgEquivalence( ivIllu.getDrawable());
                        android.view.animation.Animation anim = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.slide_down);
                        extendedView.startAnimation(anim);
                    } else {
                        extendedView.setVisibility(View.GONE);
                        Animation anim = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.slide_up);
                    }
                }
            });

        }

        public int imgEquivalence(Drawable draw) {
            switch (draw) {
                case R.drawable.illu1_head:
                    return R.drawable.illu1;
                case R.drawable.illu2_head:
                    return R.drawable.illu2;
                case R.drawable.illu3_head:
                    return R.drawable.illu3;
                case R.drawable.illu4_head:
                    return R.drawable.illu4;
                case R.drawable.illu5_head:
                    return R.drawable.illu5;
            }
            return resIDhead;
        }
    }
}
