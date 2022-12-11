package com.example.renitus.renter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.renitus.DrawerBaseActivity;
import com.example.renitus.Entities.cart_modal;
import com.example.renitus.Entities.item_modal;
import com.example.renitus.R;
import com.example.renitus.databinding.ActivityCartBinding;
import com.example.renitus.helper.CartList;
import com.example.renitus.owner.UpdateListingItemDialog;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class cart_activity extends AppCompatActivity implements cart_RV_Adapter.onDeleteToCartListener, cart_RV_Adapter.onQuantityChangeListener {

    private ActivityCartBinding binding;
    private Toolbar toolbar;
    private Map<item_modal, Integer> finalCartList_Map = new HashMap<>();
    private RecyclerView recyclerView;
    private cart_RV_Adapter adapter;
    private ExtendedFloatingActionButton proceed_to_checkout_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.cart_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
            toolbar.setNavigationOnClickListener(arrow -> onBackPressed());
            toolbar.setTitle("Cart");
        }
        recyclerView = binding.cartRV;
        proceed_to_checkout_btn = binding.proceedToCheckoutBtn;

        recyclerView.setLayoutManager(new LinearLayoutManager(cart_activity.this,
                LinearLayoutManager.VERTICAL, false));

        if(getIntent().getBooleanExtra("fromHomeScreen", false)){
            adapter = new cart_RV_Adapter(this);
            recyclerView.setAdapter(adapter);
        }
        else{
            //CartList cartList = CartList.getInstance(); //getIntent().getParcelableArrayListExtra("CartListFromRenterHomeScreen");
            Log.d("cartList Size", ""+CartList.getInstance().size());

            adapter = new cart_RV_Adapter(this);
            recyclerView.setAdapter(adapter);

            for(item_modal item_modal:CartList.getInstance()){
                finalCartList_Map.put(item_modal, 1);
            }

            proceed_to_checkout_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CheckOutItemDialog checkOutItemDialog = new CheckOutItemDialog();
                    checkOutItemDialog.setItem(finalCartList_Map);
                    checkOutItemDialog.show(getSupportFragmentManager(), "Check Out");
                }
            });
        }

    }

    @Override
    public void onDeleteButtonClicked(item_modal item) {

    }

    @Override
    public void onQuantitychange(item_modal cartitem, int quant) {
        finalCartList_Map.put(cartitem, quant);
        Log.d("insideMap", ""+cartitem.getItem_name() +"="+quant);
    }
}