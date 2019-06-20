package com.demo.hello.seamates;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompetitionFragment extends Fragment implements Runnable {

    Handler handler;
    private List<HashMap<String, String>> listItems;//存放文字、图片信息
    private SimpleAdapter listItemAdapter;//适配器
    private static final String TAG = "CompetitionFragment";
    ListView list;


    public CompetitionFragment() {
        // Required empty public constructor
    }
    @SuppressLint("HandlerLeak")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("HandlerLeak")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 初始化Fragment的布局
        View info = inflater.inflate(R.layout.fragment_competition, container, false);
        list = info.findViewById(R.id.lv);
        initListView();
        list.setAdapter(listItemAdapter);

        //开启子线程
        Thread t = new Thread(this);
        t.start();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 2) {
                    listItems = (List<HashMap<String, String>>) msg.obj;
                    listItemAdapter = new SimpleAdapter(getActivity(), listItems,
                            R.layout.competition_info,
                            new String[]{"cp_name", "c_stime"},
                            new int[]{R.id.itemTitle, R.id.itemDetail}
                    );
                    list.setAdapter(listItemAdapter);
                }
                super.handleMessage(msg);
            }
        };

        return info;
//        return inflater.inflate(R.layout.fragment_competition, container, false);
    }

    /*
    初始化界面
     */
    private void initListView() {
        listItems = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ItemTitle", "Rate: " + i);//标题文字
            map.put("ItemDetail", "" + i);//详情讲述
            listItems.add(map);
        }
        //生成适配器的Item和动态数组对应的元素
        listItemAdapter = new SimpleAdapter(getActivity(), listItems,//listItems数据源
                R.layout.competition_info,//litItem的xml布局实现
                new String[]{"ItemTitle", "ItemDetail"},
                new int[]{R.id.itemTitle, R.id.itemDetail}
        );
    }

    /*
    开启子线程
     */
    public void run() {
        Log.i(TAG, "run: 开始获取比赛数据");
        List<HashMap<String, String>> retList = new ArrayList<HashMap<String, String>>();

        try {
            Thread.sleep(3000);

            HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put("stunum", account.getText().toString());
//        hashMap.put("pwd", pwd.getText().toString());
            hashMap.put("url","android");
            String url = "http://10.32.179.52:8080/mis_group/SendCompetition";
            String info = WebService.executeHttpPost(url, hashMap);
            if(info.equals(0)||info.length()==0){
                Looper.prepare();
                Toast.makeText(getActivity(),"获取信息失败",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }else {
                try {
                    JSONObject jsonObject = new JSONObject(info);
                    Iterator<String> iterator=jsonObject.keys();
                    while(iterator.hasNext()){
                        String key = iterator.next();
                        JSONObject json= jsonObject.getJSONObject(key);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("cp_id",json.getString("cp_id"));
                        map.put("cp_name",json.getString("cp_name"));
                        map.put("category",json.getString("category"));
                        map.put("c_stime",json.getString("c_stime"));
                        map.put("c_etime",json.getString("c_etime"));
                        map.put("c_info",json.getString("c_info"));
                        retList.add(map);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Message msg = handler.obtainMessage(2);
        msg.obj = retList;
        handler.sendMessage(msg);
        Log.i("run():thread", "sendMessage.....");
    }


}
