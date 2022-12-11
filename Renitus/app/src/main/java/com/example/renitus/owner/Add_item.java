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
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Add_item extends AppCompatActivity implements GoogleMap.OnMapLongClickListener, OnMapReadyCallback {

    private GoogleMap map;
    private ActivityAddItemBinding binding;
    private TextInputEditText item_name_TIED, item_price_TIED;
    private TextInputLayout item_name_TIL, item_price_TIL;
    private AppCompatEditText item_description;
    private ExtendedFloatingActionButton upload_btn;
    private AutoCompleteTextView category_dropdown;
    private String category = "";
    private Marker currentMarker = null;
    private LatLng lastOwnerPoint = null;

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

        String item_model_json = getIntent().getStringExtra("item_modal");
        if(item_model_json != null){
            item_modal modal = (new Gson()).fromJson(item_model_json, item_modal.class);
            item_name_TIED.setText(modal.getItem_name());
            category_dropdown.setText(modal.getItem_category());
            item_price_TIED.setText(modal.getItem_price());
            item_description.setText(modal.getItem_description());
            lastOwnerPoint = new LatLng(modal.getItem_lat(),modal.getItem_long());
        }


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
                if(currentMarker == null){
                    Toast.makeText(Add_item.this, "Select owner location in MAP", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(item_name_TIED.getText()) || TextUtils.isEmpty(item_description.getText())
                        || TextUtils.isEmpty(item_price_TIED.getText()) || TextUtils.isEmpty(category)) {
                    Toast.makeText(Add_item.this, "fill all details", Toast.LENGTH_SHORT).show();
                } else {
                    LatLng currentPoint = currentMarker.getPosition();
                    item_modal item = new item_modal(item_name_TIED.getText().toString(), category,
                            item_price_TIED.getText().toString(),
                            item_description.getText().toString(),currentPoint.latitude,currentPoint.longitude);


                    Item_Database_Helper db = new Item_Database_Helper(Add_item.this);
                    if(lastOwnerPoint != null) {  // Edit mode
                        Log.d("onItemUpdate", ""+category);
                        db.updateItem(item);
                    }
                    else{
                        Log.d("onItemAdd", ""+category);
                        db.insert_item(item, owner_email);
                    }
                    Intent intent = new Intent(Add_item.this, owner_HomeScreen.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Add_item.this.finish();
                }
            }
        });

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        this.map.setOnMapLongClickListener(this);
        if(lastOwnerPoint == null) {
            LatLng toronto = new LatLng(43.69395, -79.40951);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(toronto, 10));
        }
        else {
            currentMarker = map.addMarker(new MarkerOptions().position(lastOwnerPoint).title("Owner location"));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(lastOwnerPoint, 13));
        }
    }

    @Override
    public void onMapLongClick(LatLng point) {
        if(currentMarker != null){
            currentMarker.remove();
        }
        addMark(point);
    }

    private void addMark(LatLng point){
        currentMarker = map.addMarker(new MarkerOptions().position(point).title("Owner location"));
        map.moveCamera(CameraUpdateFactory.newLatLng(point));
    }

}