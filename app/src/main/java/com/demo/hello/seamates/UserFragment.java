package com.demo.hello.seamates;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment implements Runnable {
    private Handler handler;
    private static final String TAG = "UserFragment";
    private TextView name, account, sex, phone, grade, major, info;
    private String nameText, accountText, sexText, phoneText, gradeText, majorText, infoText;

    public UserFragment() {
        // Required empty public constructor
    }


    @SuppressLint("HandlerLeak")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 初始化Fragment的布局
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        name = view.findViewById(R.id.tv_name);
        account = view.findViewById(R.id.tv_account);
        sex = view.findViewById(R.id.user_sex);
        phone = view.findViewById(R.id.user_phone);
        grade = view.findViewById(R.id.user_grade);
        major = view.findViewById(R.id.user_major);
        info = view.findViewById(R.id.user_info);

//        accountText=getActivity().getIntent().getStringExtra("account");
        accountText = "41711001";
        //获取sp里面保存的数据
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", Activity.MODE_PRIVATE);
        if (accountText.equals(sharedPreferences.getString("account", ""))) {
            nameText = sharedPreferences.getString("name", "");
            sexText = sharedPreferences.getString("sex", "");
            phoneText = sharedPreferences.getString("phone", "");
            gradeText = sharedPreferences.getString("grade", "");
            majorText = sharedPreferences.getString("major", "");
            infoText = sharedPreferences.getString("info", "");

        } else {

            //开启子线程
            Thread t = new Thread(this);
            t.start();

            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 3) {
                        Bundle bdl = (Bundle) msg.obj;
                        nameText = bdl.getString("name");
                        sexText = bdl.getString("sex");
                        phoneText = bdl.getString("phone");
                        gradeText = bdl.getString("grade");
                        majorText = bdl.getString("major");
                        infoText = bdl.getString("info");
                        //保存更新的数据
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("name", nameText);
                        editor.putString("account", accountText);
                        editor.putString("sex", sexText);
                        editor.putString("phone", phoneText);
                        editor.putString("grade", gradeText);
                        editor.putString("major", majorText);
                        editor.putString("info", infoText);
                        editor.apply();
                    }
                    super.handleMessage(msg);
                }
            };
        }

        name.setText(nameText);
//        account.setText(accountText);
        account.setText("41711001");
        sex.setText(sexText);
        phone.setText(phoneText);
        grade.setText(gradeText);
        major.setText(majorText);
        info.setText(infoText);

        return view;
//        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void run() {
        Log.i(TAG, "run: 开始获取用户数据");
        Bundle bundle = new Bundle();

        try {
            Thread.sleep(1000);

            HashMap<String, String> postMap = new HashMap<>();
//            postMap.put("stunum", accountText);
            postMap.put("stunum", "41711001");
            postMap.put("url", "android");
            String url = "/SendUserInfo";
            String info = WebService.executeHttpPost(url, postMap);
            if (info.equals(0) || info.length() == 0) {
                Looper.prepare();
                Toast.makeText(getActivity(), "获取信息失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            } else {
                try {
                    JSONObject jsonObject = new JSONObject(info);

                    bundle.putString("name", jsonObject.getString("name"));
                    bundle.putString("sex", jsonObject.getString("sex"));
                    bundle.putString("phone", jsonObject.getString("phone"));
                    bundle.putString("grade", jsonObject.getString("grade"));
                    bundle.putString("major", jsonObject.getString("major"));
                    bundle.putString("info", jsonObject.getString("info"));
                    Log.i(TAG, "run: 成功获取数据");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Message msg = handler.obtainMessage(3);
        msg.obj = bundle;
        handler.sendMessage(msg);
        Log.i("run():thread", "sendMessage.....");
    }


}
