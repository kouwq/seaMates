package com.demo.hello.seamates;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "LoginActivity";
    EditText account, pwd;
    Button login_btn, register_btn;
    ImageView imageView1, imageView2;
    // 创建等待框
    private ProgressDialog dialog;
    // 返回的数据
    private String info;
    // 返回主线程更新数据
    private static Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //获取控件
        account = findViewById(R.id.et_log_u);
        pwd = findViewById(R.id.et_pwd_u);
        imageView1 = findViewById(R.id.iv_unaClear);
        imageView2 = findViewById(R.id.iv_pwdClear);
        //添加清除监听
        addClearListener(account, imageView1);
        addClearListener(pwd, imageView2);
        //获取注册页面传参
        Intent intent = getIntent();
        String accountText=intent.getStringExtra("stunum");
        String pwdText=intent.getStringExtra("pwd");
        if(accountText!=null){
            account.setText(accountText);
            pwd.setText(pwdText);
        }

        login_btn = findViewById(R.id.log_btn);
        login_btn.setOnClickListener(this);
        register_btn = findViewById(R.id.log_btn_r);
        register_btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View btn) {
        Log.i(TAG, "onClick msg...");
        switch (btn.getId()) {
            case R.id.log_btn_r:
                Intent register = new Intent(this, Register.class);
                startActivityForResult(register, 1);
                Log.i(TAG, "register");
                break;
            case R.id.log_btn:
                String user = account.getText().toString();
                String pwd1 = pwd.getText().toString();
                if (user.length() == 0) {
                    Toast.makeText(this, "账号不能为空！", Toast.LENGTH_SHORT).show();
                } else if (pwd1.length() == 0) {
                    Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                }
//                else if (!user.matches("^1[3|5|7|8]\\d{9}$")) {
//                    Toast.makeText(this, "账号格式不对！", Toast.LENGTH_SHORT).show();
//                }
                else {

                    // 检测网络
                    if (!checkNetwork()) {
                        Toast toast = Toast.makeText(this, "网络未连接", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        break;
                    }
                    // 提示框
                    dialog = new ProgressDialog(this);
                    dialog.setTitle("提示");
                    dialog.setMessage("正在登陆，请稍后...");
                    dialog.setCancelable(false);
                    dialog.show();
                    // 创建子线程
                    Log.i(TAG, "onClick: user="+user);
                    new LoginTask().execute(user,pwd1);
//                    new Thread(new MyThread()).start();

                    break;
                }
        }
    }

    // 子线程接收数据，主线程修改数据
    public class MyThread implements Runnable {
        @Override
        public void run() {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("stunum", account.getText().toString());
            hashMap.put("pwd", pwd.getText().toString());
            String url = "http://10.32.152.9:8080/mis_group/Login";
            info = WebService.executeHttpPost(url, hashMap);

            handler.post(new Runnable() {
                @Override
                public void run() {
                    if(info.length()>0){
                        Log.i(TAG,"info="+info);
                        Intent main = new Intent(LoginActivity.this, MainActivity.class);

                        startActivityForResult(main, 2);
                        Toast.makeText(LoginActivity.this, "登陆seaMates", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "Log in");
                        Log.i(TAG, "run: info="+info);
                    }else{
                        Toast.makeText(LoginActivity.this, "账号或密码错误！", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
            });
        }
    }

    // 检测网络
    private boolean checkNetwork() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager.getActiveNetworkInfo() != null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }
    private class LoginTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            for (String p : params) {
                Log.i(TAG, "doInBackground: " + p);
            }
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("stunum", params[0]);
            hashMap.put("pwd", params[1]);
            hashMap.put("url","android");
            String url = "http://10.240.13.8:8080/mis_group/Login";
            String ret = WebService.executeHttpPost(url, hashMap);
            return ret;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s.equals("0")||s.length()==0){
                Toast.makeText(LoginActivity.this, "账号或密码错误！", Toast.LENGTH_SHORT).show();
            }else{
                Log.i(TAG,"info="+s.length());
                Intent main = new Intent(LoginActivity.this, MainActivity.class);
                main.putExtra("account", account.getText().toString());
                main.putExtra("pwd", pwd.getText().toString());
                startActivityForResult(main, 2);
                Toast.makeText(LoginActivity.this, "登陆seaMates", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "info="+s);
            }
            dialog.dismiss();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public static void addClearListener(final EditText editText, final ImageView imageView) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //如果有输入内容就显示clear按钮
                String str = s + "";
                if (str.length() > 0) {
                    imageView.setVisibility(View.VISIBLE);
                } else {
                    imageView.setVisibility(View.INVISIBLE);
                }

            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
    }

}
