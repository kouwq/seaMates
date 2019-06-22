package com.demo.hello.seamates;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InitiateTeamActivity extends AppCompatActivity implements View.OnClickListener {
    EditText cpName, teamName, matesNum, qq,detail;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiate_team);

        Intent intent = getIntent();
        String stunum = intent.getStringExtra("account");

        cpName =findViewById(R.id.teamAdd_cpName);
        teamName = findViewById(R.id.teamAdd_teamName);
        matesNum = findViewById(R.id.teamAdd_matesNum);
        qq = findViewById(R.id.teamAdd_qq);
        detail =findViewById(R.id.teamAdd_detail);

        btn = findViewById(R.id.teamAdd_btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
