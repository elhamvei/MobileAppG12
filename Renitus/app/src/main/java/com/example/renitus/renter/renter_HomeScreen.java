package com.example.renitus.renter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;

public class renter_HomeScreen extends DrawerBaseActivity implements renter_RV_Adapter.onAddToCartListener {

    private ActivityRenterHomeScreenBinding binding;
    private Item_Database_Helper itemDb;
    private ArrayList<item_modal> itemList = new ArrayList<>();
    private ArrayList<item_modal> furniture_itemList = new ArrayList<>();
    private ArrayList<item_modal> book_itemList = new ArrayList<>();
    private ArrayList<item_modal> shoes_itemList = new ArrayList<>();
    private ArrayList<item_modal> cart_List = new ArrayList<>();
    private RecyclerView recyclerView;
    private SearchView searchView;
    private renter_RV_Adapter adapter;
    private ExtendedFloatingActionButton buy_item_btn;


    private static final String SHARED_PREFS = "sharedPrefs";
    private String owner_email;
    private static final String login_email = "loginEmail";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRenterHomeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        allocateActivityTitle("Renter");

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        owner_email = sharedPreferences.getString(login_email, "");

        recyclerView = binding.renterRV;
        searchView = binding.searchBar;
        buy_item_btn = binding.buyItemBtn;

        itemDb = new Item_Database_Helper(renter_HomeScreen.this);
        getAllItemsInArray();

        recyclerView.setLayoutManager(new LinearLayoutManager(renter_HomeScreen.this, LinearLayoutManager.VERTICAL, false));
        adapter = new renter_RV_Adapter(this, itemList);
        recyclerView.setAdapter(adapter);
        Log.d("furniture size", "" + furniture_itemList.size());
        Log.d("books size", "" + book_itemList.size());
        Log.d("Shoes size", "" + shoes_itemList.size());

        searchView.clearFocus();
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        buy_item_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(renter_HomeScreen.this, cart_activity.class);
                intent.putParcelableArrayListExtra("CartListFromRenterHomeScreen", cart_List);
                startActivity(intent);
            }
        });
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
                populateFurniture();
                return true;
            case R.id.Book:
                populateBook();
                return true;
            case R.id.Shoes:
                populateShoes();
                return true;
            case R.id.cart:
                Intent cart_intent = new Intent(renter_HomeScreen.this, cart_activity.class);
                cart_intent.putExtra("fromHomeScreen", true);
                startActivity(cart_intent);
                return true;
            case R.id.All_types:
                populateAll();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void populateAll() {
        adapter.populateAllItems(itemList);
    }

    private void populateShoes() {
        Log.d("Shoes clicked", "" + shoes_itemList.size());
        adapter.populateRVWithShoeItem(shoes_itemList, itemList);
    }

    private void populateBook() {
        Log.d("books clicked", "" + book_itemList.size());
        adapter.populateRVWithBookItem(book_itemList, itemList);
    }

    private void populateFurniture() {
        Log.d("furniture clicked", "" + furniture_itemList.size());
        adapter.populateRVWithFurnitureItem(furniture_itemList, itemList);
    }

    @Override
    public void onCartItemClicked(item_modal item_modal) {
        cart_List.add(item_modal);
        Toast.makeText(renter_HomeScreen.this, "Added", Toast.LENGTH_SHORT).show();
    }
}