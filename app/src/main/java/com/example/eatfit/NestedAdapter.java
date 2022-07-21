package com.example.eatfit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NestedAdapter extends RecyclerView.Adapter<NestedAdapter.NestedViewHolder> {

    public List<String> mList;

    public NestedAdapter(List<String> mList){
        this.mList = mList;
    }

    @NonNull
    @Override
    public NestedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.nested_item, parent,
                false
        );

        return new NestedViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NestedViewHolder holder, int position) {
        holder.nestedItemTv.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class NestedViewHolder extends RecyclerView.ViewHolder{
        public TextView nestedItemTv;



        public NestedViewHolder(@NonNull View itemView) {
            super(itemView);

            nestedItemTv = itemView.findViewById(R.id.nestedItemTv);

        }

    }

}
