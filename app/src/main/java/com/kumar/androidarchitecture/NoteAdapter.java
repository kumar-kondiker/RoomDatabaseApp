package com.kumar.androidarchitecture;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class NoteAdapter extends ListAdapter<Note,NoteAdapter.NoteViewHolder> {

  OnItemClickListnerInf listnerInf;

    public NoteAdapter() {
        super(DiffCallback);
    }

    private static final DiffUtil.ItemCallback<Note> DiffCallback =new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getProirity()==newItem.getProirity();
        }
    };




    public Note getNote(int position)
    {
       return getItem(position);
    }


    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.note_object_layout,parent,false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note currentnote=getItem(position);
        holder.title_textview.setText(currentnote.getTitle());
        holder.description_textview.setText(currentnote.getDescription());
        holder.proirity_textview.setText(String.valueOf(currentnote.getProirity()));

    }


    public class NoteViewHolder extends RecyclerView.ViewHolder
    {
        private TextView title_textview;
        private TextView description_textview;
        private TextView proirity_textview;

        public NoteViewHolder(@NonNull final View itemView) {
            super(itemView);
            title_textview=itemView.findViewById(R.id.textview_title);
            description_textview=itemView.findViewById(R.id.text_view_description);
            proirity_textview=itemView.findViewById(R.id.text_view_proirity);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();

                    if(position!=RecyclerView.NO_POSITION && listnerInf!= null)
                    listnerInf.onItemClick(getItem(position));
                }
            });
        }
    }

    public interface OnItemClickListnerInf{
        public void onItemClick(Note note);
    }

    public void onItemClickListner(OnItemClickListnerInf listnerInf)
    {
        this.listnerInf=listnerInf;
    }

}
