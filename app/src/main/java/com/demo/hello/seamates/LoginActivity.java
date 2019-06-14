package com.demo.hello.seamates;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "LoginActivity";
    EditText username, pwd;
    Button btn1, btn2;
    ImageView imageView1, imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.et_log_u);
        pwd = findViewById(R.id.et_pwd_u);
        imageView1 = findViewById(R.id.iv_unaClear);
        imageView2 = findViewById(R.id.iv_pwdClear);
        //添加清除监听
        addClearListener(username,imageView1);
        addClearListener(pwd,imageView2);

        btn1 = findViewById(R.id.log_btn1);
        btn1.setOnClickListener(this);
        btn2 = findViewById(R.id.log_btn2);
        btn2.setOnClickListener(this);
    }


    @Override
    public void onClick(View btn) {
        Log.i(TAG, "onClick msg...");
        if (btn.getId() == R.id.log_btn2) {
            Intent register = new Intent(this, Register.class);
            startActivityForResult(register, 1);
            Log.i(TAG, "register");
        } else {
            String user = username.getText().toString();
            String pwd1 = pwd.getText().toString();
            if (user.length() == 0) {
                Toast.makeText(this, "账号不能为空！", Toast.LENGTH_SHORT).show();
            } else if (pwd1.length() == 0) {
                Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
            } else if (!user.matches("^1[3|5|7|8]\\d{9}$")) {
                Toast.makeText(this, "账号格式不对！", Toast.LENGTH_SHORT).show();
            } else {
                if (user.equals("13550585364") & pwd1.equals("123")) {
                    Intent main = new Intent(this, MainActivity.class);
                    startActivityForResult(main, 2);
                    Toast.makeText(this, "登陆seaMates", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "Log in");
                } else {
                    Toast.makeText(this, "账号或密码错误！", Toast.LENGTH_SHORT).show();

                }
            }
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
        imageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
    }

}
