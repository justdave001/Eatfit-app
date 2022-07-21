package com.example.eatfit;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ItemViewHolder> {

    private List<HomeModel> modelList;
    private List list = new ArrayList<>();
    List templist = new ArrayList<>();
    String resname;
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
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(holder.nestedRv);
        holder.nestedRv.setAdapter(adapter);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeModel.setExpandable(!homeModel.isExpandable());
                list = homeModel.getMenuItems();
                resname = homeModel.getRestaurantName();
                notifyItemChanged(holder.getAdapterPosition());
            }
        });

    }
    ItemTouchHelper.SimpleCallback simpleCallback =
            new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                switch (direction){
                    case ItemTouchHelper.LEFT:
                        Object removee = list.remove(viewHolder.getAdapterPosition());



                        ParseUser currentUser = ParseUser.getCurrentUser();
                        ParseQuery<ParseObject> query = ParseQuery.getQuery("Restaurants");
                        query.whereEqualTo("user", currentUser);
                        query.whereEqualTo("res_name",resname);

                        query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> objects, ParseException e) {
                                if (e==null){
                                    for (ParseObject removed:objects){
                                        templist = removed.getList("menu_items");
                                        removed.remove("menu_items");
                                        if (templist.contains(removee)){
                                            templist.remove(removee);
                                            removed.addAll("menu_items", templist);
                                            removed.saveInBackground();

                                        }
                                    }

                                }

                            }

                        });
//                    list.remove(viewHolder.getAdapterPosition());
                        notifyDataSetChanged();

                        Snackbar.make(viewHolder.itemView,removee.toString(), Snackbar.LENGTH_LONG )
                                .setAction("Undo", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        query.whereEqualTo("user", currentUser);
                                        query.whereEqualTo("res_name",resname);

                                        query.findInBackground(new FindCallback<ParseObject>() {
                                            @Override
                                            public void done(List<ParseObject> objects, ParseException e) {
                                                if (e==null){
                                                    for (ParseObject removed:objects){
                                                        removed.add("menu_items", removee);
                                                        removed.saveInBackground();

                                                    }

                                                }

                                            }

                                        });
                                        notifyDataSetChanged();
                                    }
                                }).show();
                        break;

                    case ItemTouchHelper.RIGHT:
                        break;
                }



                }
            };



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
