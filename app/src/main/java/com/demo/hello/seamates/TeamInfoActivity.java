package com.demo.hello.seamates;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TeamInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_info);
        Intent intent =getIntent();
        int team_id = intent.getIntExtra("team_id",0);

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
        cpName.setText("比赛类型："+cpNameText);
        leader.setText(getString(R.string.leadText) + leaderText);
        detail.setText("说明："+detailText);
        qq.setText(getString(R.string.qqText) + qqText);
        matesNum.setText("组队人数："+String.valueOf(matesNumText));
    }
}
