package com.example.familyexpences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.familyexpences.DB.SQLiteDatabaseHelper;
import com.example.familyexpences.DTOs.User;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class RegisterNewFamilyMemberActivity extends AppCompatActivity {

    EditText passwordET;
    EditText repeatPasswordET;
    EditText usernameET;
    Button createBtn;
    TextView alreadyRegisteredBtn;
    AutoCompleteTextView FamilyNameET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_family_member);

        FamilyNameET = findViewById(R.id.FamilyNameET);
        passwordET = findViewById(R.id.passwordET);
        repeatPasswordET = findViewById(R.id.repeatPasswordET);
        usernameET = findViewById(R.id.usernameET);
        createBtn = findViewById(R.id.createBtn);
        alreadyRegisteredBtn = findViewById(R.id.createBtn);

        final SQLiteDatabaseHelper db = new SQLiteDatabaseHelper(RegisterNewFamilyMemberActivity.this);

        final List<String> FamiliesList = db.getFamilies();

        ArrayAdapter<String> CategoriesAdapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                FamiliesList);
        FamilyNameET.setAdapter(CategoriesAdapter);



        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameET.getText().toString();
                String password = passwordET.getText().toString();
                final String name = FamilyNameET.getText().toString();
                int familyID = db.getFamilyID(name);

                User user = new User(username, password, name);

                if (usernameET.getText().length() == 0 ||
                        passwordET.getText().length() == 0 ||
                        FamilyNameET.getText().length() == 0 ||
                        !passwordET.getText().toString().
                                equals(repeatPasswordET.getText().toString())) {
                    Toast.makeText(RegisterNewFamilyMemberActivity.this,"Error, fill valid data!",Toast.LENGTH_SHORT).show();
                    //return;
                } else if (db.InsertFamilyUser(user, familyID)){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        if (!isFinishing()){
                            new AlertDialog.Builder(RegisterNewFamilyMemberActivity.this)
                                    .setTitle("Information")
                                    .setIcon(android.R.drawable.ic_dialog_info)
                                .setMessage("Successful registration! \n" +
                                            "Please wait until admin approve your request! " +
                                            ":)")
                                    .setCancelable(false)
                                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(RegisterNewFamilyMemberActivity.this,LoginActivity.class);
                                            startActivity(intent);
                                        }
                                    }).show();
                        }
                        }
                        });

                    } else {}

            }
        });
        //todo get family_ID from array adapter

    }

}
