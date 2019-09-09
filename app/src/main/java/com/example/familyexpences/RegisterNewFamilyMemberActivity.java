package com.example.familyexpences;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.familyexpences.DB.SQLiteDatabaseHelper;

import java.util.List;

public class RegisterNewFamilyMemberActivity extends AppCompatActivity {

    EditText passwordET;
    EditText repeatPasswordET;
    EditText usernameET;
    Button createBtn;
    TextView alreadyRegisteredBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_family_member);

        AutoCompleteTextView FamilyNameET = findViewById(R.id.FamilyNameET);
        //FamilyNameET = findViewById(R.id.FamilyNameET);
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

    }
}
