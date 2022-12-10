package com.example.renitus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.renitus.Entities.user_modal;
import com.example.renitus.SQliteDB.user_Database_Helper;
import com.example.renitus.databinding.ActivityLoginActivityBinding;
import com.example.renitus.owner.owner_HomeScreen;
import com.example.renitus.renter.renter_HomeScreen;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity  {

    private ActivityLoginActivityBinding binding;
    private ExtendedFloatingActionButton login_btn, signUp_btn;
    private TextInputLayout login_email_TIL, login_password_TIL;
    private TextInputEditText login_email_TIEDT, login_password_TIEDT;
    private SwitchCompat SighIn_as_User_Switch;
    private user_Database_Helper userDb;

    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String login_email = "loginEmail";
    private static final String login_pass = "loginPass";
    private static final String login_type = "loginType";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userDb = new user_Database_Helper(LoginActivity.this);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        if (sharedPreferences.getString(login_type, "").equals("owner")) {
            Intent intent = new Intent(LoginActivity.this, owner_HomeScreen.class);
            startActivity(intent);
            finish();
        }
        else if(sharedPreferences.getString(login_type, "").equals("renter")){
            Intent intent = new Intent(LoginActivity.this, renter_HomeScreen.class);
            startActivity(intent);
            finish();
        }else {
            login_btn = binding.layoutInclude.loginButton1;
            signUp_btn = binding.layoutInclude.signUpButton1;
            login_email_TIL = binding.layoutInclude.emailLogin;
            login_email_TIEDT = binding.layoutInclude.emailtxt;
            login_password_TIL = binding.layoutInclude.passwordLogin;
            login_password_TIEDT = binding.layoutInclude.passwordLoginTIEDT;
            SighIn_as_User_Switch = binding.layoutInclude.ownerSwitch;

            login_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String email = login_email_TIEDT.getText().toString();
                    String password = login_password_TIEDT.getText().toString();
                    user_Database_Helper user_database_helper = new user_Database_Helper(LoginActivity.this);
                    if (SighIn_as_User_Switch.isChecked()) {
                        // ownerSignIn
                        user_modal user = new user_modal(email, password, "owner");
                        //check for owner
                        if (user_database_helper.checkRenter(user)) {
                            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(login_email, email);
                            editor.putString(login_pass, password);
                            editor.putString(login_type, "owner");
                            editor.apply();
                            Toast.makeText(LoginActivity.this, "valid owner", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, owner_HomeScreen.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "not a valid owner", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        //renter signIn
                        user_modal user = new user_modal(email, password, "renter");
                        //check for renter
                        if (user_database_helper.checkRenter(user)) {
                            Toast.makeText(LoginActivity.this, "valid renter", Toast.LENGTH_SHORT).show();
                            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(login_email, email);
                            editor.putString(login_pass, password);
                            editor.putString(login_type, "renter");
                            editor.apply();
                            Intent intent = new Intent(LoginActivity.this, renter_HomeScreen.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(LoginActivity.this, "not a valid renter", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            signUp_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String email = login_email_TIEDT.getText().toString();
                    String password = login_password_TIEDT.getText().toString();
                    user_modal user = new user_modal(email, password, "");

                    Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                    intent.putExtra("intent_from_login", user);
                    startActivity(intent);
                }
            });

        }
    }

}