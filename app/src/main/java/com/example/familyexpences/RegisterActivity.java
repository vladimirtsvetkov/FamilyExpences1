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

import com.example.familyexpences.DB.SQLiteDatabaseHelper;
import com.example.familyexpences.DTOs.User;

public class RegisterActivity extends AppCompatActivity {

    LinearLayout background;
    EditText usernameET;
    EditText passwordET;
    EditText repeatPasswordET;
    Button createBtn;
    TextView alreadyRegisteredBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        background = findViewById(R.id.registerBackground);
        usernameET = findViewById(R.id.usernameET);
        passwordET = findViewById(R.id.passwordET);
        repeatPasswordET = findViewById(R.id.repeatPasswordET);

        createBtn = findViewById(R.id.createBtn);
        alreadyRegisteredBtn = findViewById(R.id.alreadyRegisteredBtn);
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
                    String name = usernameET.getText().toString();

                    User user = new User(username, password, name);

                    if (usernameET.getText().length() == 0 ||
                            passwordET.getText().length() == 0 ||
                            !passwordET.getText().toString().
                                    equals(repeatPasswordET.getText().toString())) {
                        Snackbar.make(background, "Error", Snackbar.LENGTH_LONG)
                                .show();
                        return;
                    }
//
                    SQLiteDatabaseHelper db = new SQLiteDatabaseHelper(RegisterActivity.this);
                    db.insertUser(user);
                    break;
                case R.id.alreadyRegisteredBtn:
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    break;
            }

            intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    };
}
