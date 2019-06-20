package com.demo.hello.seamates;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.HashMap;

public class Register extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "Register";
    private EditText account, pwd, username, phone, major, info, grade;
    private RadioGroup radioGroup;
    private RadioButton sex;
    private Button sub;
    // 创建等待框
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        account = findViewById(R.id.reg_et_account);
        pwd = findViewById(R.id.reg_et_pwd);
        sex =findViewById(R.id.reg_male);
        username = findViewById(R.id.reg_et_name);
        phone = findViewById(R.id.reg_et_phone);
        major = findViewById(R.id.reg_et_major);
        grade = findViewById(R.id.reg_et_grade);
        info = findViewById(R.id.reg_et_info);

        radioGroup = findViewById(R.id.reg_rg_sex);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                sex = findViewById(group.getCheckedRadioButtonId());
            }
        });

        sub = findViewById(R.id.reg_btn);
        sub.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String accountText = account.getText().toString();
        String pwdText = pwd.getText().toString();
        String sexText = sex.getText().toString();
        String usernameText = username.getText().toString();
        String phoneText = phone.getText().toString();
        String majorText = major.getText().toString();
        String gradeText = grade.getText().toString();
        String infoText = info.getText().toString();

        Log.i(TAG, "onClick: account=" + accountText);
        Log.i(TAG, "onClick: pwd=" + pwdText);
        Log.i(TAG, "onClick: sex=" + sexText);
        Log.i(TAG, "onClick: username=" + usernameText);
        Log.i(TAG, "onClick: phone=" + phoneText);

        if (accountText.length() == 0) {
            Toast.makeText(this, "账号不能为空！", Toast.LENGTH_SHORT).show();
        } else if (pwdText.length() == 0) {
            Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
        } else if (usernameText.length() == 0) {
            Toast.makeText(this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
        } else if (phoneText.length() == 0) {
            Toast.makeText(this, "电话不能为空！", Toast.LENGTH_SHORT).show();
        } else if (majorText.length() == 0) {
            Toast.makeText(this, "专业不能为空！", Toast.LENGTH_SHORT).show();
        } else if (gradeText.length() == 0) {
            Toast.makeText(this, "年级不能为空！", Toast.LENGTH_SHORT).show();
        } else {

            // 提示框
            dialog = new ProgressDialog(this);
            dialog.setTitle("提示");
            dialog.setMessage("正在注册，请稍后...");
            dialog.setCancelable(false);
            dialog.show();
            // 异步传输
            new RegisterTask().execute(accountText, pwdText, usernameText, sexText, phoneText, gradeText, majorText, infoText);
//                    new Thread(new MyThread()).start();

        }

    }

    private class RegisterTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            for (String p : params) {
                Log.i(TAG, "doInBackground: " + p);
            }
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("stunum", params[0]);
            hashMap.put("pwd", params[1]);
            hashMap.put("username", params[2]);
            hashMap.put("sex", params[3]);
            hashMap.put("phone", params[4]);
            hashMap.put("grade", params[5]);
            hashMap.put("major", params[6]);
            hashMap.put("info", params[7]);
            hashMap.put("url", "android");
            String url = "http://10.240.13.8:8080/mis_group/Register";
            String ret = WebService.executeHttpPost(url, hashMap);
            return ret;
        }

        @Override
        protected void onPostExecute(String s) {
//            Toast.makeText(Register.this, s, Toast.LENGTH_SHORT).show();
            if(s.equals("0")||s.length()==0) {
                Toast.makeText(Register.this, "注册失败！！！", Toast.LENGTH_SHORT).show();
            }else{
                Intent main = new Intent(Register.this, LoginActivity.class);
                main.putExtra("stunum", account.getText().toString());
                main.putExtra("account", username.getText().toString());
                main.putExtra("pwd", pwd.getText().toString());

                startActivityForResult(main, 2);
                Toast.makeText(Register.this, "注册成功！", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "注册成功");
                Log.i(TAG, "run: info=" + s);
            }
            dialog.dismiss();
        }
    }

}
