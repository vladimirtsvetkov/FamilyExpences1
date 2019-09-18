package com.example.familyexpences;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.familyexpences.Constants.Constants;
import com.example.familyexpences.DB.SQLiteDatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    EditText usernameET;
    EditText passwordET;
    Button loginBtn;
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameET = findViewById(R.id.usernameET);
        passwordET = findViewById(R.id.passwordET);
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);

        loginBtn.setOnClickListener(onClick);
        registerBtn.setOnClickListener(onClick);
    }

    View.OnClickListener onClick = new View.OnClickListener() {
        Intent intent = null;

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.loginBtn:

                    SQLiteDatabaseHelper db = new SQLiteDatabaseHelper(LoginActivity.this);

                    final String username = usernameET.getText().toString();
                    final String password = passwordET.getText().toString();
                            if (db.login(username, password)) {
                                SharedPreferences sp = getSharedPreferences(Constants.LOGIN, MODE_PRIVATE);
                                sp.edit().putString(Constants.LOGGED_USER, username).apply();
                                intent = new Intent(LoginActivity.this, Main2Activity.class);
                                intent.putExtra("username", username);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, "Wrong username or password",
                                        Toast.LENGTH_LONG).show();
                            }

                    break;
                case R.id.registerBtn:
                    intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };
}
