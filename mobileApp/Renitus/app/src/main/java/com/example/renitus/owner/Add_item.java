package com.example.renitus.owner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.renitus.Entities.item_modal;
import com.example.renitus.R;
import com.example.renitus.SQliteDB.Item_Database_Helper;
import com.example.renitus.databinding.ActivityAddItemBinding;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class Add_item extends AppCompatActivity {

    private ActivityAddItemBinding binding;
    private TextInputEditText item_name_TIED, item_price_TIED;
    private TextInputLayout item_name_TIL, item_price_TIL;
    private AppCompatEditText item_description;
    private ExtendedFloatingActionButton upload_btn;
    private AutoCompleteTextView category_dropdown;
    private String category = "";

    private static final String SHARED_PREFS = "sharedPrefs";
    private String owner_email;
    private static final String login_email = "loginEmail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        owner_email = sharedPreferences.getString(login_email, "");

        item_name_TIED = binding.itemNameTIEDT;
        item_price_TIED = binding.itemPriceTIEDT;
        item_name_TIL = binding.itemNameTIL;
        item_price_TIL = binding.itemPriceTIL;
        upload_btn = binding.uploadBtn;
        item_description = binding.itemDescription;
        category_dropdown = binding.itemCategoryDropdownMenu;

        ArrayList<String> categoryList = new ArrayList<>();
        categoryList.add("furniture");
        categoryList.add("shoes");
        categoryList.add("book");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(Add_item.this, R.layout.item_category_textview, categoryList);
        category_dropdown.setAdapter(adapter);

        category_dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                category = parent.getItemAtPosition(position).toString();
            }
        });

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(item_name_TIED.getText()) || TextUtils.isEmpty(item_description.getText())
                        || TextUtils.isEmpty(item_price_TIED.getText()) || TextUtils.isEmpty(category)) {
                    Toast.makeText(Add_item.this, "fill all details", Toast.LENGTH_SHORT).show();
                } else {
                    item_modal item = new item_modal(item_name_TIED.getText().toString(), category,
                            item_price_TIED.getText().toString(),
                            item_description.getText().toString());

                    Log.d("onItemAdd", ""+category);

                    Item_Database_Helper db = new Item_Database_Helper(Add_item.this);
                    db.insert_item(item, owner_email);
                    Intent intent = new Intent(Add_item.this, owner_HomeScreen.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Add_item.this.finish();
                }
            }
        });

    }
}