package com.example.renitus.owner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renitus.DrawerBaseActivity;
import com.example.renitus.Entities.item_modal;
import com.example.renitus.SQliteDB.Item_Database_Helper;
import com.example.renitus.databinding.ActivityOwnerHomeScreenBinding;
import com.example.renitus.renter.renter_HomeScreen;
import com.example.renitus.renter.renter_RV_Adapter;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class owner_HomeScreen extends DrawerBaseActivity implements
        owner_listing_RV_Adapter.delete_btn_Listener, owner_listing_RV_Adapter.onViewClickListener,
        UpdateListingItemDialog.UpdateItemListener {


    //TODO owner profile screen, owner_modal, ownerCredentials save in shared preferences


    private ActivityOwnerHomeScreenBinding binding;
    private Item_Database_Helper itemDb;
    private ArrayList<item_modal> itemList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SearchView searchView;
    private owner_listing_RV_Adapter adapter;
    private ExtendedFloatingActionButton  list_item_btn;

    private static final String SHARED_PREFS = "sharedPrefs";
    private String owner_email ;
    private static final String login_email = "loginEmail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOwnerHomeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        allocateActivityTitle("owner");

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        owner_email = sharedPreferences.getString(login_email, "");

        recyclerView = binding.ownerListingRV;
        searchView = binding.searchBar;
        list_item_btn = binding.addListingBtn;

        itemDb = new Item_Database_Helper(owner_HomeScreen.this);
        getAllItemsInArray();

        recyclerView.setLayoutManager(new LinearLayoutManager(owner_HomeScreen.this, LinearLayoutManager.VERTICAL, false));
        adapter = new owner_listing_RV_Adapter(this, itemList);
        recyclerView.setAdapter(adapter);
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

        list_item_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(owner_HomeScreen.this, Add_item.class);
                startActivity(intent);
            }
        });
    }

    private void getAllItemsInArray() {
        Cursor cursor = itemDb.getAllItems();
        if (cursor.getCount() == 0) {
            Toast.makeText(owner_HomeScreen.this, "No. Data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                item_modal item = new item_modal();

                // get all the items of the particular owner
                if((cursor.getString(1).equals(owner_email))){

                    item.setItem_id(Integer.parseInt(cursor.getString(0)));
                    item.setItem_name(cursor.getString(2));
                    item.setItem_category(cursor.getString(3));
                    item.setItem_description(cursor.getString(4));
                    item.setItem_price(String.valueOf(cursor.getInt(5)));
                    Log.d("insideHomeScreen", ""+cursor.getInt(2));
                    itemList.add(item);
                }

            }
        }
    }

    @Override
    public void onSaveButtonClicked(item_modal item_modal) {
        itemDb.updateItem(item_modal);
        recreate();
    }
    @Override
    public void onDeleteButtonClicked(item_modal item, int pos) {
        itemDb.deleteItem(item);
        adapter.deleteItem(item, pos);
    }

    @Override
    public void onItemClick(item_modal item_modal) {
        openUpdatePlayerDialog(item_modal);
    }
    private void openUpdatePlayerDialog(item_modal item_modal) {
        UpdateListingItemDialog updateListingItemDialog = new UpdateListingItemDialog();
        updateListingItemDialog.setItem(item_modal);
        updateListingItemDialog.show(getSupportFragmentManager(), "update Homework");
    }

}