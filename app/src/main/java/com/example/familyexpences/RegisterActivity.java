package com.example.familyexpences;

import android.content.Intent;


import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.familyexpences.DB.SQLiteDatabaseHelper;
import com.example.familyexpences.DTOs.User;

public class RegisterActivity extends AppCompatActivity {

    LinearLayout background;
    EditText usernameET;
    EditText passwordET;
    EditText FamilynameET;
    EditText repeatPasswordET;
    Button createBtn;
    Button RegisterNewFamilyMemberBT;
    TextView alreadyRegisteredBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        background = findViewById(R.id.registerBackground);
        usernameET = findViewById(R.id.usernameET);
        passwordET = findViewById(R.id.passwordET);
        repeatPasswordET = findViewById(R.id.repeatPasswordET);
        FamilynameET = findViewById(R.id.FamilynameET);

        createBtn = findViewById(R.id.createBtn);
        RegisterNewFamilyMemberBT = findViewById(R.id.RegisterNewFamilyMemberBT);
        alreadyRegisteredBtn = findViewById(R.id.alreadyRegisteredBtn);
        RegisterNewFamilyMemberBT.setOnClickListener(onClick);
        createBtn.setOnClickListener(onClick);
        alreadyRegisteredBtn.setOnClickListener(onClick);


    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        Intent intent;

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.createBtn:
                    String username = usernameET.getText().toString();
                    String password = passwordET.getText().toString();
                    String name = FamilynameET.getText().toString();

                    User user = new User(username, password, name);
                    
                    if (usernameET.getText().length() == 0 ||
                            passwordET.getText().length() == 0 ||
                            FamilynameET.getText().length() == 0 ||
                            !passwordET.getText().toString().
                                    equals(repeatPasswordET.getText().toString())) {
                        Snackbar.make(background, "Error, fill valid data!", Snackbar.LENGTH_LONG)
                                .show();
                        return;
                    }
//
                    SQLiteDatabaseHelper db = new SQLiteDatabaseHelper(RegisterActivity.this);
                    int UserId = db.insertUser(user);
                    if (UserId !=0) {
                        int Family_Id = db.insertFamily(name, UserId);
                        db.updateUser(Family_Id,UserId);
                        //todo db.updateUser(int FamilyID, int userId)
                        intent = new Intent(RegisterActivity.this, Main2Activity.class);
                        intent.putExtra("username",username);
                        startActivity(intent);
                    } else {
                        Toast.makeText(RegisterActivity.this, "Error: username already exists!",
                                Toast.LENGTH_LONG).show();
                    }

                    break;
                case R.id.alreadyRegisteredBtn:
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    break;
                case R.id.RegisterNewFamilyMemberBT:
                    Intent RegisterNewFamilyMember = new Intent(RegisterActivity.this, RegisterNewFamilyMemberActivity.class);
                    startActivity(RegisterNewFamilyMember);
                    break;

            }

            //intent = new Intent(RegisterActivity.this, LoginActivity.class);
            //startActivity(intent);
        }
    };
}
