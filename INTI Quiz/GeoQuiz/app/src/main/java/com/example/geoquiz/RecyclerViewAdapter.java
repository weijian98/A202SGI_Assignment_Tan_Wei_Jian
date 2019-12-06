package com.example.geoquiz;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> usernames = new ArrayList<>();
    private ArrayList<String> scores = new ArrayList<>();
    private Context context;

    public RecyclerViewAdapter(Context context, ArrayList<String> usernames, ArrayList<String> scores) {
        this.usernames = usernames;
        this.scores = scores;
        this.context = context;
    }

    //creates viewholder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_listitem,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //everytime an item on recyclerview is shown it will be called. it can know the numbers
        Log.d(TAG, "onBindViewHolder: called");

        Glide.with(context)
                .asBitmap()
                .load(R.drawable.icon)
                .into(holder.image);

        holder.userName.setText(usernames.get(position));
        holder.userScores.setText(scores.get(position));

    }

    @Override
    public int getItemCount() {
        //tells adapter how many list items available
        return usernames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView userName;
        TextView userScores;
        RelativeLayout recyclerViewLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            userName = itemView.findViewById(R.id.username);
            userScores = itemView.findViewById(R.id.score);
            recyclerViewLayout = itemView.findViewById(R.id.recycler_view_layout);
        }
    }
}