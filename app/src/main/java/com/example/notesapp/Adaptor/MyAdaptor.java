package com.example.notesapp.Adaptor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.CreateNoteActivity;
import com.example.notesapp.HomeActivity;
import com.example.notesapp.R;
import com.example.notesapp.Room.Entity;

import java.util.ArrayList;
import java.util.List;

public class MyAdaptor extends RecyclerView.Adapter<MyAdaptor.myviewHolder> {
List<Entity> entities;
Context context;
List<Entity> allnotesfilter;
    public MyAdaptor(List<Entity> entities, Context context) {
        this.entities = entities;
        this.context = context;
        allnotesfilter = new ArrayList<>(entities);
    }

    public void searchNotes(List<Entity> allnotesfilter)
    {
        this.entities = allnotesfilter;
        notifyDataSetChanged();
    }


    @NonNull

    @Override
    public myviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new myviewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.container_iteam, null, false));
    }

    @Override
    public void onBindViewHolder(MyAdaptor.myviewHolder holder, int position) {

            Entity entity = entities.get(position);

            if (entity.priority.equals("1"))
            {
                holder.prorityiw.setBackgroundResource(R.drawable.priority1);
            }

            if (entity.priority.equals("2"))
            {
                holder.prorityiw.setBackgroundResource(R.drawable.proiority_2);
            }

            if (entity.priority.equals("3"))
            {
                holder.prorityiw.setBackgroundResource(R.drawable.proiority_3);
            }

            if (entity.priority.equals("4"))
            {
                holder.prorityiw.setBackgroundResource(R.drawable.proiority_4);
            }


            holder.title.setText(entity.getTitle());
            holder.notes.setText(entity.getNotes());
            holder.date.setText(entity.getDate());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CreateNoteActivity.class);
                    intent.putExtra("id",entity.getId());
                    intent.putExtra("title",entity.getTitle());
                    intent.putExtra("subtitle",entity.getSubtitle());
                    intent.putExtra("notes",entity.getNotes());
                    intent.putExtra("prority",entity.getPriority());
                    intent.putExtra("update",true);
                    context.startActivity(intent);
                }
            });

    }

    @Override
    public int getItemCount() {
        return entities.size();
    }

    public static class myviewHolder extends RecyclerView.ViewHolder{
    TextView title,notes,date;
    ImageView prorityiw;
        public myviewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.container_titile);
            notes = itemView.findViewById(R.id.container_notes);
            date = itemView.findViewById(R.id.contaienr_date);
            prorityiw = itemView.findViewById(R.id.container_imageView);
        }

    }

}
