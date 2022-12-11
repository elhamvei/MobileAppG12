package com.example.renitus.renter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renitus.Entities.cart_modal;
import com.example.renitus.Entities.item_modal;
import com.example.renitus.R;
import com.example.renitus.helper.CartList;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;

public class cart_RV_Adapter extends RecyclerView.Adapter<cart_RV_Adapter.ViewHolder> implements Filterable {

    private ArrayList<item_modal> full_cart_list;
    private final Context mContext;
    private onDeleteToCartListener deleteListener;
    private onQuantityChangeListener quantityChangeListener;

    public cart_RV_Adapter(Context context) {
        this.mContext = context;
        this.deleteListener = (onDeleteToCartListener) mContext;
        this.quantityChangeListener = (onQuantityChangeListener) mContext;
        this.full_cart_list = new ArrayList<>(CartList.getInstance());
    }

    @NonNull
    @Override
    public cart_RV_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cart_item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull cart_RV_Adapter.ViewHolder holder, int position) {
        item_modal item = CartList.getInstance().get(position);
        Log.d("insideRV", "onBindViewHolder: " + item.getItem_category());
        holder.item_name.setText(item.getItem_name());
        holder.item_price.setText(item.getItem_price());
        holder.item_quantity.setText("1");

        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartList.getInstance().remove(holder.getAdapterPosition());
                full_cart_list.remove(holder.getAdapterPosition());
                deleteListener.onDeleteButtonClicked(item);
                notifyDataSetChanged();
            }
        });

        holder.decr_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(holder.item_quantity.getText()) ||
                        Integer.parseInt(holder.item_quantity.getText().toString()) <= 1) {
                    holder.item_quantity.setText("1");
//                    quantityChangeListener.onQuantitychange(item, 1);
                } else {
                    int quant = Integer.parseInt(holder.item_quantity.getText().toString())-1;
                    holder.item_quantity.setText(""+quant);
                    quantityChangeListener.onQuantitychange(item, quant);
                }
            }
        });
        holder.incr_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(holder.item_quantity.getText()) ||
                        Integer.parseInt(holder.item_quantity.getText().toString()) <= 0) {
                    holder.item_quantity.setText("1");
                    //quantityChangeListener.onQuantitychange(item, 1);
                } else {
                    int quant = Integer.parseInt(holder.item_quantity.getText().toString())+1;
                    holder.item_quantity.setText(""+quant);
                    quantityChangeListener.onQuantitychange(item, quant);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return CartList.getInstance().size();
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
                filteredList.addAll(full_cart_list);
            } else {
                String searchedString = charSequence.toString().toLowerCase().trim();
                for (item_modal item : full_cart_list) {
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
            CartList.getInstance().clear();
            CartList.getInstance().addAll((Collection<? extends item_modal>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView item_name, item_price;
        ExtendedFloatingActionButton delete_btn;
        AppCompatButton incr_btn, decr_btn;
        EditText item_quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.item_name);
            item_price = itemView.findViewById(R.id.item_price);
            delete_btn = itemView.findViewById(R.id.delete_btn);
            item_quantity = itemView.findViewById(R.id.quantity_EDT);
            incr_btn = itemView.findViewById(R.id.increment_btn);
            decr_btn = itemView.findViewById(R.id.decrement_btn);
        }
    }

    public interface onDeleteToCartListener {
        void onDeleteButtonClicked(item_modal item);
    }

    public interface onQuantityChangeListener{
        void onQuantitychange(item_modal item, int quantity);
    }

}
