package com.promise.www.schoollibrary;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.promise.www.schoollibrary.ModelClass.NoteModel;

import java.util.List;


/**
 * Created by MR AGUDA on 19-Apr-18.
 */

    public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {


        private Context ctx;
        private List<NoteModel> notes;


    public NoteAdapter(Context context, List<NoteModel> notes) {
            ctx = context;
            this.notes = notes;
        }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_per_one_model, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
            final NoteModel noteModel = notes.get(position);
        holder.title.setText(noteModel.getName());
        holder.content.setText(noteModel.getContent());
        holder.time.setText(noteModel.getTime());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, NoteEditorActivity.class);
                intent.putExtra("name",noteModel.getName());
                intent.putExtra("content", noteModel.getContent());
                ctx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


    public class NoteViewHolder extends RecyclerView.ViewHolder {
            TextView title, content, time;
            private View view;


            NoteViewHolder(View itemView) {
                super(itemView);
                view = itemView;


                title = (TextView) itemView.findViewById(R.id.name);
                content = (TextView) itemView.findViewById(R.id.content);
                time = (TextView) itemView.findViewById(R.id.time);
            }



        }




}
