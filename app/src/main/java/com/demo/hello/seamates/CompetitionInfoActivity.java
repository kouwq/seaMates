package com.demo.hello.seamates;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class CompetitionInfoActivity extends AppCompatActivity {
    TextView title, category, stime, etime, info;
    String titleText, categorytText, stimeText, etimeText, infoText;
    private String TAG="CompetitionInfoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("比赛详细信息");
        setContentView(R.layout.activity_competition_info);
        //返回按钮
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        int id = intent.getIntExtra("cp_id", 0);

        DBManager manager = new DBManager(this);
        CompetitionItem competitionItem = manager.findCpById(id);
        titleText = competitionItem.getCpName();
        categorytText = competitionItem.getCategory();
        stimeText = competitionItem.getCpStime();
        etimeText = competitionItem.getCpEtime();
        infoText = competitionItem.getCpInfo();

        title = findViewById(R.id.cpI_title);
        category = findViewById(R.id.cpI_category);
        stime = findViewById(R.id.cpI_stime);
        etime = findViewById(R.id.cpI_etime);
        info = findViewById(R.id.cpI_info);

        title.setText(titleText);
        category.setText(categorytText);
        stime.setText(stimeText);
        etime.setText(etimeText);
        info.setText(infoText);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.i(TAG, "onOptionsItemSelected: 返回键");
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
