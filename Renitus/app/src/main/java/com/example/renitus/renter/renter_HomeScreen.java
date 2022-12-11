package com.example.renitus.renter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renitus.DrawerBaseActivity;
import com.example.renitus.Entities.book_modal;
import com.example.renitus.Entities.furniture_modal;
import com.example.renitus.Entities.item_modal;
import com.example.renitus.Entities.shoes_modal;
import com.example.renitus.R;
import com.example.renitus.SQliteDB.Book_Database_Helper;
import com.example.renitus.SQliteDB.Furniture_Database_Helper;
import com.example.renitus.SQliteDB.Item_Database_Helper;
import com.example.renitus.SQliteDB.Shoes_Database_Helper;
import com.example.renitus.databinding.ActivityRenterHomeScreenBinding;
import com.example.renitus.helper.CartList;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class renter_HomeScreen extends DrawerBaseActivity implements
        renter_RV_Adapter.onAddToCartListener,
        GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback {

    private GoogleMap map;
    private ActivityRenterHomeScreenBinding binding;
    private Item_Database_Helper itemDb;
    private ArrayList<Marker> markerList = new ArrayList<Marker>();
    private ArrayList<item_modal> itemList = new ArrayList<>();
    private ArrayList<item_modal> furniture_itemList = new ArrayList<>();
    private ArrayList<item_modal> book_itemList = new ArrayList<>();
    private ArrayList<item_modal> shoes_itemList = new ArrayList<>();

    private static final String SHARED_PREFS = "sharedPrefs";
    private String owner_email;
    private static final String login_email = "loginEmail";
    private Marker currentMarker = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRenterHomeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        allocateActivityTitle("Renter");

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        owner_email = sharedPreferences.getString(login_email, "");

        itemDb = new Item_Database_Helper(renter_HomeScreen.this);
        getAllItemsInArray();

        Log.d("furniture size", "" + furniture_itemList.size());
        Log.d("books size", "" + book_itemList.size());
        Log.d("Shoes size", "" + shoes_itemList.size());

        binding.addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentMarker !=null) {
                    item_modal modal = (item_modal)currentMarker.getTag();
                    if (existsInCartList(modal)) {
                        Toast.makeText(renter_HomeScreen.this, "Item already added", Toast.LENGTH_SHORT).show();
                    } else {
                        CartList.getInstance().add(modal);
                        Toast.makeText(renter_HomeScreen.this, "Added", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(renter_HomeScreen.this, "Please select an item in MAP", Toast.LENGTH_SHORT).show();
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
        this.map.setOnMarkerClickListener(this);
        LatLng toronto = new LatLng(43.69395, -79.40951);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(toronto, 10));
        for(item_modal modal:itemList){
            BitmapDescriptor icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
            if(modal.getItem_category().equals("furniture"))
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
            else if(modal.getItem_category().equals("book"))
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
            Marker marker = map.addMarker(new MarkerOptions().icon(icon).position(new LatLng(modal.getItem_lat(),modal.getItem_long()))
                    .title(modal.getItem_name()));
            marker.setTag(modal);
            markerList.add(marker);
        }
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        currentMarker = marker;
        item_modal modal = (item_modal) marker.getTag();
        binding.itemName.setText(modal.getItem_name());
        binding.itemCategory.setText(modal.getItem_category());
        binding.itemDecription.setText(modal.getItem_description());
        binding.itemPrice.setText(modal.getItem_price());
        return true;
    }



    private void getAllItemsInArray() {
        Cursor cursor = itemDb.getAllItems();
        if (cursor.getCount() == 0) {
            Toast.makeText(renter_HomeScreen.this, "No. Data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                item_modal item = new item_modal();
                item.setItem_id(Integer.parseInt(cursor.getString(0)));
                item.setItem_name(cursor.getString(2));
                item.setItem_category(cursor.getString(3));
                item.setItem_description(cursor.getString(4));
                item.setItem_price(String.valueOf(cursor.getInt(5)));
                item.setItem_lat(cursor.getDouble(6));
                item.setItem_long(cursor.getDouble(7));
                Log.d("insideHomeScreen", "" + cursor.getInt(2));

                if (item.getItem_category().equals("shoes")) {
                    shoes_itemList.add(item);
                } else if (item.getItem_category().equals("furniture")) {
                    furniture_itemList.add(item);
                } else {
                    book_itemList.add(item);
                }
                Log.d("category", "" + item.getItem_category());
                itemList.add(item);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.items_category_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Furniture:
                populate("furniture");
                return true;
            case R.id.Book:
                populate("book");
                return true;
            case R.id.Shoes:
                populate("shoes");
                return true;
            case R.id.cart:
                Intent cart_intent = new Intent(renter_HomeScreen.this, cart_activity.class);
                //cart_intent.putParcelableArrayListExtra("CartListFromRenterHomeScreen", cart_List);
                startActivity(cart_intent);
                return true;
            case R.id.All_types:
                populateAll();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void populateAll() {
        for(Marker marker:markerList){
            marker.setVisible(true);
        }
    }

    private void populate(String category) {
        for(Marker marker:markerList){
            item_modal modal = (item_modal) marker.getTag();
            if(modal.getItem_category().equals(category))
                marker.setVisible(true);
            else
                marker.setVisible(false);
        }
    }

    @Override
    public void onCartItemClicked(item_modal item_modal) {
        if (existsInCartList(item_modal)) {
            Toast.makeText(renter_HomeScreen.this, "Item already added", Toast.LENGTH_SHORT).show();
        } else {
            CartList.getInstance().add(item_modal);
            Toast.makeText(renter_HomeScreen.this, "Added", Toast.LENGTH_SHORT).show();
        }
    }

    private Boolean existsInCartList(item_modal item) {
        for (item_modal itemModal : CartList.getInstance()) {
            if (itemModal.getItem_id() == item.getItem_id())
                return true;
        }
        return false;
    }
}