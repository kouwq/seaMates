package com.demo.hello.seamates;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register extends AppCompatActivity implements View.OnClickListener {
    private EditText account,pwd,username,email,address;
    private Button sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        account = findViewById(R.id.reg_account);
        pwd = findViewById(R.id.reg_pwd);
        username = findViewById(R.id.reg_name);
        email = findViewById(R.id.reg_email);
        address = findViewById(R.id.reg_address);
        sub = findViewById(R.id.reg_btn);
        sub.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String accountText = account.getText().toString();
        String pwdText = pwd.getText().toString();
        String usernameText = username.getText().toString();
        String emailText = email.getText().toString();
        String addressText = address.getText().toString();



    }
}
