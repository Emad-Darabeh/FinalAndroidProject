package com.example.androidlabproject;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteVh> {

    Context context ;
    List<Note> noteList;

    public NoteAdapter(Context context ,  List<Note> noteList) {
        this.context = context;
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public NoteVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_row , parent , false);
        return new NoteVh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteVh holder, int position) {
        holder.setData(noteList.get(position));
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    class NoteVh extends RecyclerView.ViewHolder{
        TextView noteTitleTV, noteDateTV, noteBodyTV;
        public NoteVh(@NonNull View itemView) {
            super(itemView);
            noteTitleTV = itemView.findViewById(R.id.note_title_text_view);
            noteDateTV = itemView.findViewById(R.id.note_date_text_view);
            noteBodyTV = itemView.findViewById(R.id.note_body_text_view);
            noteBodyTV.setMaxLines(2);
        }

        public void setData(Note note) {
            noteTitleTV.setText(note.getTitle());
            noteDateTV.setText(note.getDate());
            noteBodyTV.setText(note.getBody());
        }
    }
}