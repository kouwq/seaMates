package com.demo.hello.seamates;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register extends AppCompatActivity implements View.OnClickListener {
    private EditText account,pwd,username,phone,major,info,grade;
    private Button sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        account = findViewById(R.id.reg_et_account);
        pwd = findViewById(R.id.reg_et_pwd);
        username = findViewById(R.id.reg_et_name);
        phone = findViewById(R.id.reg_et_phone);
        major = findViewById(R.id.reg_et_major);
        grade = findViewById(R.id.reg_et_grade);
        info = findViewById(R.id.reg_et_info);
        sub = findViewById(R.id.reg_btn);
        sub.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String accountText = account.getText().toString();
        String pwdText = pwd.getText().toString();
        String usernameText = username.getText().toString();
        String phoneText = phone.getText().toString();
        String majorText = major.getText().toString();
        String gradeText = grade.getText().toString();
        String infoText = info.getText().toString();



    }
}
