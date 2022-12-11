package com.example.renitus.owner;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.renitus.databinding.ActivityOwnerProfileBinding;

public class owner_Profile extends AppCompatActivity {

    private ActivityOwnerProfileBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOwnerProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}