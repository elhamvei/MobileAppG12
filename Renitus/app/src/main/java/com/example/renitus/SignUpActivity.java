package com.example.renitus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.renitus.Entities.user_modal;
import com.example.renitus.SQliteDB.user_Database_Helper;
import com.example.renitus.databinding.ActivitySignUpBinding;
import com.example.renitus.owner.owner_HomeScreen;
import com.example.renitus.renter.renter_HomeScreen;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private ExtendedFloatingActionButton signIn_btn;
    private TextInputLayout login_email_TIL, login_password_TIL;
    private TextInputEditText login_email_TIEDT, login_password_TIEDT;
    private AutoCompleteTextView userType_dropdown;
    private String user_Type = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        login_email_TIL = binding.emailTIL;
        login_email_TIEDT = binding.emailTIEDT;
        login_password_TIL = binding.passwordLogin;
        login_password_TIEDT = binding.passwordLoginTIEDT;
        userType_dropdown = binding.statusDropdownMenu;
        signIn_btn = binding.SignUpButton;

        user_modal user = getIntent().getParcelableExtra("intent_from_login");
        login_email_TIEDT.setText(user.getUser_email());

        ArrayList<String> userType_List = new ArrayList<>();
        userType_List.add("renter");
        userType_List.add("owner");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(SignUpActivity.this, R.layout.user_type_textview, userType_List);
        userType_dropdown.setAdapter(adapter);

        userType_dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                user_Type = parent.getItemAtPosition(position).toString();
            }
        });

        signIn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(login_email_TIEDT.getText())||TextUtils.isEmpty(login_password_TIEDT.getText())
                        || user_Type==null){
                    Toast.makeText(SignUpActivity.this, "Enter all details", Toast.LENGTH_SHORT).show();
                }
                else{
                    String email = login_email_TIEDT.getText().toString();
                    String password = login_password_TIEDT.getText().toString();

                    user_modal user = new user_modal(email, password, user_Type);
                    user_Database_Helper db = new user_Database_Helper(SignUpActivity.this);
                    db.insert_renter(user);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            SignUpActivity.this.finish();
                        }
                    }, 3500);
                }
            }
        });
    }
}