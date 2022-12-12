package com.example.renitus.renter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.renitus.Entities.item_modal;
import com.example.renitus.R;
import com.example.renitus.helper.CartList;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.ObjIntConsumer;

public class CheckOutItemDialog extends AppCompatDialogFragment {


    private Map<item_modal, Integer> finalCartList_Map = new HashMap<>();
    private TextView total_items, total_price;
    private ExtendedFloatingActionButton proceed_to_payment_btn;
    private int totalPrice = 0;
    private String totalItems = "";

    public void setItem(Map<item_modal, Integer> cartListMap) {
        this.finalCartList_Map = cartListMap;
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.checkout_item, null);
        View view1 = inflater.inflate(R.layout.custom_toast, null);

        if (isAdded()) {
            builder.setView(view);
            builder.setCancelable(true);
            builder.setTitle("item");

            LayoutInflater inflater1 = getActivity().getLayoutInflater();
            View toastLayout = inflater1.inflate(R.layout.custom_toast, view1.findViewById(R.id.custom_toast_layout));
            final  Toast toast = new Toast(getActivity().getApplicationContext());
            toast.setGravity(Gravity.CENTER_VERTICAL, 10, 5);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(toastLayout);

            total_items = view.findViewById(R.id.No_of_items);
            total_price = view.findViewById(R.id.total_price);
            proceed_to_payment_btn = view.findViewById(R.id.proceed_to_payment_btn);

            totalItems = String.valueOf(finalCartList_Map.size());

            for (Map.Entry<item_modal, Integer> items : finalCartList_Map.entrySet()) {
                totalPrice += Integer.parseInt(items.getKey().getItem_price()) * items.getValue();
            }

            total_price.setText("Total Price = " + totalPrice+" CAD");
            total_items.setText("Total Items = "+ totalItems);

            proceed_to_payment_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toast.show();
                    startActivity(new Intent(getActivity(), renter_HomeScreen.class));
                    CartList.getInstance().clear();
                    dismiss();
//                    item_modal new_item = new item_modal(item_name_TIED.getText().toString(), category,
//                            item_price_TIED.getText().toString(),
//                            item_description.getText().toString());
//
//                    Log.d("status", "" + item_name_TIED.getText().toString());
//                    new_item.setItem_id(item.getItem_id());
//                    mListener.onSaveButtonClicked(new_item);
//                    dismiss();

                }
            });

        }
        return builder.create();
    }


    /* ********************    Important Line  *********/


}

