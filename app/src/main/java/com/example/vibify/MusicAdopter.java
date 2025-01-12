package com.example.vibify;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MusicAdopter extends RecyclerView.Adapter<MusicAdopter.ViewHolder> {
    ArrayList<AudioModel>songList;

    public MusicAdopter(ArrayList<AudioModel> songList, Context context) {
        this.songList = songList;
        this.context = context;
    }

    Context context;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recyle_item,parent,false);
        return new MusicAdopter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AudioModel songData=songList.get(position);
        holder.textView.setText(songData.getTitle());

        if(MymediapPlayer.currentIndex==position){
            holder.textView.setTextColor(Color.parseColor("#FF0000"));
        }else{
            holder.textView.setTextColor(Color.parseColor("#000000"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MymediapPlayer.getInstance().reset();
                MymediapPlayer.currentIndex=position;
                Intent intent=new Intent(context, MusicPlayerAcitvity.class);
                intent.putExtra("List", songList);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView iconView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.music_title_text);
            iconView=itemView.findViewById(R.id.icon_view);

        }
    }
}
