package com.example.renitus.renter;

import android.annotation.SuppressLint;
import android.content.Context;
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

public class renter_RV_Adapter extends RecyclerView.Adapter<renter_RV_Adapter.ViewHolder> implements Filterable {

    private ArrayList<item_modal> item_list;
    private ArrayList<item_modal> full_item_List;
    private final Context mContext;
    private onAddToCartListener addToCartListener;

    public renter_RV_Adapter(Context context, ArrayList<item_modal> itemList) {
        this.mContext = context;
        this.item_list = itemList;
        this.addToCartListener = (onAddToCartListener) mContext;
        this.full_item_List = new ArrayList<>(item_list);
    }

    public void populateAllItems(ArrayList<item_modal> itemList){
        this.item_list = itemList;
        this.full_item_List = new ArrayList<>(item_list);
        notifyDataSetChanged();
    }

    public void populateRVWithFurnitureItem(ArrayList<item_modal> furnitureList, ArrayList<item_modal> itemList){

        this.item_list = furnitureList;
        this.full_item_List = new ArrayList<>(item_list);
        notifyDataSetChanged();
    }
    public void populateRVWithShoeItem(ArrayList<item_modal> shoeList, ArrayList<item_modal> itemList){

        this.item_list = shoeList;
        this.full_item_List = new ArrayList<>(item_list);
        notifyDataSetChanged();
    }
    public void populateRVWithBookItem(ArrayList<item_modal> bookList, ArrayList<item_modal> itemList){

        this.item_list = bookList;
        this.full_item_List = new ArrayList<>(item_list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public renter_RV_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.renter_item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull renter_RV_Adapter.ViewHolder holder, int position) {
        item_modal item = item_list.get(position);
        Log.d("insideRV", "onBindViewHolder: " + item.getItem_category());
        holder.item_name.setText(item.getItem_name());
        holder.item_price.setText(item.getItem_price());

        holder.addToCart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCartListener.onCartItemClicked(item);
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
        TextView item_name, item_price;
        ExtendedFloatingActionButton addToCart_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.item_name);
            item_price = itemView.findViewById(R.id.item_price);
            addToCart_btn = itemView.findViewById(R.id.addToCart_btn);
        }
    }

    public interface onAddToCartListener {
        void onCartItemClicked(item_modal item_modal);
    }

}
