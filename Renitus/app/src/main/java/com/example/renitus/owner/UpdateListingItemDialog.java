package com.example.renitus.owner;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.renitus.Entities.item_modal;
import com.example.renitus.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;

public class UpdateListingItemDialog extends AppCompatDialogFragment {


    private UpdateListingItemDialog.UpdateItemListener mListener;
    private item_modal item;
    private TextInputEditText item_name_TIED, item_price_TIED;
    private TextInputLayout item_name_TIL, item_price_TIL;
    private AppCompatEditText item_description;
    private ExtendedFloatingActionButton saveItem_btn;
    private AutoCompleteTextView category_dropdown;
    private String category = "";

    public void setItem(item_modal item) {
        this.item = item;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.update_listing_item, null);

        if (isAdded()) {
            builder.setView(view);
            builder.setCancelable(true);
            builder.setTitle("item");

            item_name_TIED = view.findViewById(R.id.item_name_TIEDT);
            item_price_TIED = view.findViewById(R.id.item_price_TIEDT);
            item_name_TIL = view.findViewById(R.id.item_name_TIL);
            item_price_TIL = view.findViewById(R.id.item_price_TIL);
            item_description =  view.findViewById(R.id.item_description);
            saveItem_btn = view.findViewById(R.id.save_btn);
            category_dropdown = view.findViewById(R.id.item_category_dropdown_menu);

            item_name_TIED.setText(item.getItem_name());
            item_price_TIED.setText(item.getItem_price());
            category_dropdown.setText(item.getItem_category());
            category = item.getItem_category();
            item_description.setText(item.getItem_description());

            ArrayList<String> categoryList = new ArrayList<>();
            categoryList.add("furniture");
            categoryList.add("shoes");
            categoryList.add("book");

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.item_category_textview, categoryList);
            category_dropdown.setAdapter(adapter);

            category_dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    category = parent.getItemAtPosition(position).toString();
                }
            });


            saveItem_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (TextUtils.isEmpty(item_name_TIED.getText()) || TextUtils.isEmpty(item_price_TIED.getText())
                            || TextUtils.isEmpty(item_description.getText()) || TextUtils.isEmpty(category)) {
                        Toast.makeText(getActivity(), "Please Enter all fields", Toast.LENGTH_SHORT).show();;
                    } else {
                        item_modal new_item = new item_modal(item_name_TIED.getText().toString(), category,
                                item_price_TIED.getText().toString(),
                                 item_description.getText().toString(),0,0);

                        Log.d("status", "" + item_name_TIED.getText().toString());
                        new_item.setItem_id(item.getItem_id());
                        mListener.onSaveButtonClicked(new_item);
                        dismiss();
                    }

                }
            });

        }
        return builder.create();
    }


    /* ********************    Important Line  *********/
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (UpdateListingItemDialog.UpdateItemListener) context;
    }

    public interface UpdateItemListener {
        void onSaveButtonClicked(item_modal item_modal);
    }
}

