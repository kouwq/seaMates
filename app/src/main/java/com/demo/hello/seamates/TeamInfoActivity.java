package com.demo.hello.seamates;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class TeamInfoActivity extends AppCompatActivity {
    public static int resultCode = 7;
    private String TAG="TeamInfoActivity";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("队伍详情信息");
        setContentView(R.layout.activity_team_info);
        //返回按钮
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        int team_id = intent.getIntExtra("team_id", 0);

        DBManager manager = new DBManager(this);
        TeamItem teamItem = manager.findTeamById(team_id);
        String titleText = teamItem.getTeamName();
        String cpNameText = teamItem.getCpName();
        String leaderText = teamItem.getLeader();
        String detailText = teamItem.getDetail();
        String qqText = teamItem.getQq();
        int matesNumText = teamItem.getMatesNum();

        TextView title = findViewById(R.id.teamInfo_title);
        TextView cpName = findViewById(R.id.teamInfo_cpName);
        TextView leader = findViewById(R.id.teamInfo_leader);
        TextView detail = findViewById(R.id.teamInfo_detail);
        TextView qq = findViewById(R.id.teamInfo_qq);
        TextView matesNum = findViewById(R.id.teamInfo_matesNum);

        title.setText(titleText);
        cpName.setText(getString(R.string.category) + cpNameText);
        leader.setText(getString(R.string.leadText) + leaderText);
        detail.setText(getString(R.string.refer) + detailText);
        qq.setText(getString(R.string.qqText) + qqText);
        matesNum.setText(getString(R.string.matesNum) + String.valueOf(matesNumText));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(resultCode);
                Log.i(TAG, "onOptionsItemSelected: 返回键");
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
