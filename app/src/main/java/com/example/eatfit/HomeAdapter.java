package com.example.eatfit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ItemViewHolder> {

    private List<HomeModel> modelList;
    private List list = new ArrayList<>();
    Context context;
    public HomeAdapter(Context context,List<HomeModel> modelList){
        this.context=context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.res_item,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        HomeModel homeModel = modelList.get(position);
        holder.mTextView.setText(homeModel.getRestaurantName());

        boolean isExpandable = homeModel.isExpandable();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

        if (isExpandable){
            holder.mArrowImage.setImageResource(R.drawable.up);
        } else {
            holder.mArrowImage.setImageResource(R.drawable.dd);
        }

        NestedAdapter adapter = new NestedAdapter(list);
        holder.nestedRv.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.nestedRv.setHasFixedSize(true);
        holder.nestedRv.setAdapter(adapter);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeModel.setExpandable(!homeModel.isExpandable());
                list = homeModel.getMenuItems();
                notifyItemChanged(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout linearLayout;
        public RelativeLayout expandableLayout;
        public TextView mTextView;
        public ImageView mArrowImage;
        public RecyclerView nestedRv;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.linearLayout);
            expandableLayout =  itemView.findViewById(R.id.expandableLayout);
            mTextView = itemView.findViewById(R.id.itemTv);
            mArrowImage = itemView.findViewById(R.id.dropDown);
            nestedRv = itemView.findViewById(R.id.childRv);
        }
    }
}
