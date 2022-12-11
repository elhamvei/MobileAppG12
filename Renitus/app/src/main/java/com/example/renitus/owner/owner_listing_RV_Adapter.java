package com.example.renitus.owner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renitus.Entities.item_modal;
import com.example.renitus.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;

public class owner_listing_RV_Adapter extends RecyclerView.Adapter<owner_listing_RV_Adapter.ViewHolder> implements Filterable {

    private ArrayList<item_modal> item_list;
    private ArrayList<item_modal> full_item_List;
    private final Context mContext;
    private delete_btn_Listener listener;
    private onViewClickListener viewClickListener;

    public owner_listing_RV_Adapter(Context context, ArrayList<item_modal> itemList) {
        this.mContext = context;
        this.item_list = itemList;
        this.listener = (delete_btn_Listener) mContext;
        this.viewClickListener = (onViewClickListener) mContext;
        this.full_item_List = new ArrayList<>(item_list);
    }

    public void deleteItem(item_modal item, int pos) {
        item_list.remove(pos);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public owner_listing_RV_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.owner_item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull owner_listing_RV_Adapter.ViewHolder holder, int position) {
        item_modal item = item_list.get(position);
        Log.d("insideRV", "onBindViewHolder: " + item.getItem_name());
        holder.name.setText(item.getItem_name());
        holder.price.setText("₹ " +item.getItem_price());

        holder.view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewClickListener.onItemClick(item);
            }

        });

        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDeleteButtonClicked(item, holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return item_list.size();
    }

    @Override
    public Filter getFilter() {
        return itemFilter;
    }

    private final Filter itemFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<item_modal> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(full_item_List);
            } else {
                String searchedString = charSequence.toString().toLowerCase().trim();
                for (item_modal item : full_item_List) {
                    if (item.getItem_name().toLowerCase().contains(searchedString) ||
                            item.getItem_category().toLowerCase().contains(searchedString)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            item_list.clear();
            item_list.addAll((Collection<? extends item_modal>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price;
        ExtendedFloatingActionButton delete_btn, view_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            price = itemView.findViewById(R.id.item_price);
            view_btn = itemView.findViewById(R.id.view_btn);
            delete_btn = itemView.findViewById(R.id.delete_btn);
        }
    }

    public interface delete_btn_Listener {
        void onDeleteButtonClicked(item_modal item, int pos);
    }

    public interface onViewClickListener {
        void onItemClick(item_modal item_modal);
    }

}
