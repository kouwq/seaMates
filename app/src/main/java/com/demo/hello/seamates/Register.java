package com.demo.hello.seamates;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class Register extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "Register";
    private EditText account,pwd,username,phone,major,info,grade;
    private Button sub;
    // 创建等待框
    private ProgressDialog dialog;

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
        if (accountText.length() == 0) {
            Toast.makeText(this, "账号不能为空！", Toast.LENGTH_SHORT).show();
        } else if (pwdText.length() == 0) {
            Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
        }
//                else if (!user.matches("^1[3|5|7|8]\\d{9}$")) {
//                    Toast.makeText(this, "账号格式不对！", Toast.LENGTH_SHORT).show();
//                }
        else {

            // 提示框
            dialog = new ProgressDialog(this);
            dialog.setTitle("提示");
            dialog.setMessage("正在登陆，请稍后...");
            dialog.setCancelable(false);
            dialog.show();
            // 创建子线程，分别进行Get和Post传输
            new RegisterTask().execute(accountText,pwdText,usernameText,phoneText,majorText,gradeText,infoText,"android");
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
            hashMap.put("url",params[2]);
            String url = "http://10.32.132.244:8080/mis_group/LoginServlet";
            String ret = WebService.executeHttpPost(url, hashMap);
            return ret;
        }

        @Override
        protected void onPostExecute(String s) {
//            Toast.makeText(Register.this, s, Toast.LENGTH_SHORT).show();
            if(s.length()>0){
                Intent main = new Intent(Register.this, LoginActivity.class);

                startActivityForResult(main, 2);
                Toast.makeText(Register.this, "登陆seaMates", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Log in");
                Log.i(TAG, "run: info="+s);
            }else{
                Toast.makeText(Register.this, "账号或密码错误！", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        }
    }

}
