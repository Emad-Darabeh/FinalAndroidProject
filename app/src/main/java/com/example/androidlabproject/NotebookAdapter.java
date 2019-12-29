package com.example.androidlabproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotebookAdapter extends RecyclerView.Adapter<NotebookAdapter.NotebookVh> {

    Context context ;
    List<Notebook> notebookList;

    public NotebookAdapter(Context context ,  List<Notebook> notebookList) {
        this.context = context;
        this.notebookList = notebookList;
    }

    @NonNull
    @Override
    public NotebookVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notebook_row , parent , false);
        return new NotebookVh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotebookVh holder, final int position) {
        holder.setData(notebookList.get(position));

        holder.notebookRowLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NotesActivity.class);
                intent.putExtra("notebookId", notebookList.get(position).getId());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notebookList.size();
    }

    class NotebookVh extends RecyclerView.ViewHolder{
        TextView notebookTitleTV ;

        private LinearLayout notebookRowLL;

        public NotebookVh(@NonNull View itemView) {
            super(itemView);
            notebookTitleTV = itemView.findViewById(R.id.notebook_title_text_view);
            notebookRowLL = itemView.findViewById(R.id.notebook_row_layout);
        }

        public void setData(Notebook notebook) {
            notebookTitleTV.setText(notebook.getTitle());

        }
    }
}