package com.demo.hello.seamates;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class InitiateTeamActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "InitiateTeamActivity";
    EditText cpName, teamName, matesNum, qq, detail;
    Button btn;
    String cpNameText, teamNameText, usernameText, matesNumText, qqText, detailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("创建比赛");
        setContentView(R.layout.activity_initiate_team);
        //返回按钮
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //获取sp里面保存的数据
        SharedPreferences sharedPreferences = getSharedPreferences("user", Activity.MODE_PRIVATE);
        usernameText = sharedPreferences.getString("name", "");


        cpName = findViewById(R.id.teamAdd_cpName);
        teamName = findViewById(R.id.teamAdd_teamName);
        matesNum = findViewById(R.id.teamAdd_matesNum);
        qq = findViewById(R.id.teamAdd_qq);
        detail = findViewById(R.id.teamAdd_detail);

        btn = findViewById(R.id.teamAdd_btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        //弹出确认窗口
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示").setMessage("请确定是否提交").setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG, "onClick:对话框事件处理");
                cpNameText = cpName.getText().toString();
                teamNameText = teamName.getText().toString();
                matesNumText = matesNum.getText().toString();
                qqText = qq.getText().toString();
                detailText = detail.getText().toString();
                if (cpNameText.length() == 0) {
                    Toast.makeText(InitiateTeamActivity.this, "比赛名称不能为空！", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else if (teamNameText.length() == 0) {
                    Toast.makeText(InitiateTeamActivity.this, "队伍名称不能为空！", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else if (matesNumText.length() == 0) {
                    Toast.makeText(InitiateTeamActivity.this, "队员人数不能为空！", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else if (qqText.length() == 0) {
                    Toast.makeText(InitiateTeamActivity.this, "qq不能为空！", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    // 异步传输
                    new InitiateTask().execute(cpNameText, teamNameText, usernameText, matesNumText, qqText, detailText);
                    Log.i(TAG, "onClick: cpname= " + cpNameText);
                    dialog.dismiss();

                }
            }
        })
                .setNegativeButton("否", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    private class InitiateTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            for (String p : params) {
                Log.i(TAG, "doInBackground: " + p);
            }
            HashMap<String, String> hashMap = new HashMap<>();

            hashMap.put("cp_name", params[0]);
            hashMap.put("team_name", params[1]);
            hashMap.put("leader", params[2]);
            hashMap.put("mates_num", params[3]);
            hashMap.put("qq", params[4]);
            hashMap.put("detail", params[5]);
            hashMap.put("url", "android");
            String url = "/Initiate";
            String ret = WebService.executeHttpPost(url, hashMap);
            return ret;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.equals("0") || s.length() == 0) {
                Toast.makeText(InitiateTeamActivity.this, "网络不好，创建队伍失败！！！", Toast.LENGTH_SHORT).show();
            } else {

                TeamItem teamItem = new TeamItem(cpNameText, teamNameText, usernameText, Integer.parseInt(matesNumText), qqText, detailText);
                //把数据写入数据库中
                DBManager manager = new DBManager(InitiateTeamActivity.this);
                manager.addTeam(teamItem);
                Intent intent = getIntent();
                setResult(5, intent);
                finish();
            }
        }
    }
}
