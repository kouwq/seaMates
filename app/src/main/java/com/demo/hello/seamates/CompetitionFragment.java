package com.demo.hello.seamates;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompetitionFragment extends Fragment implements Runnable, AdapterView.OnItemClickListener {

    private static final String TAG = "CompetitionFragment";
    private final String DATE_SP_KEY = "lastDateStr";
    private String data[] = {"wait..."};
    private String updateDate = "";
    private ListView list;
    private Handler handler;
    private List<HashMap<String, String>> listItems;//存放文字、图片信息
    private SimpleAdapter listItemAdapter;//适配器

    public CompetitionFragment() {
        // Required empty public constructor
    }

    @SuppressLint("HandlerLeak")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 初始化Fragment的布局
        View view = inflater.inflate(R.layout.fragment_competition, container, false);
        //获取sp时间
        SharedPreferences sp = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        updateDate = sp.getString(DATE_SP_KEY, "");
        Log.i("List", "lastDateStr=" + updateDate);
        //初始化界面
        list = view.findViewById(R.id.cp_lv);
        ListAdapter adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, data);
        list.setAdapter(adapter);

        list.setEmptyView(view.findViewById(R.id.cp_tv_nodata));
        list.setOnItemClickListener(this);


        //开启子线程
        Thread t = new Thread(this);
        t.start();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 2) {
                    listItems = (List<HashMap<String, String>>) msg.obj;
                    listItemAdapter = new SimpleAdapter(getActivity(), listItems,
                            R.layout.competition_list,
                            new String[]{"cp_name", "cp_id", "c_stime"},
                            new int[]{R.id.cpList_title, R.id.cpList_id, R.id.cpList_time}
                    );
                    list.setAdapter(listItemAdapter);

                }
                super.handleMessage(msg);
            }
        };

        return view;
//        return inflater.inflate(R.layout.fragment_competition, container, false);
    }

    /*
    开启子线程
     */
    public void run() {
        Log.i(TAG, "run: 开始获取比赛数据");
        List<HashMap<String, String>> retList = new ArrayList<>();

        //获取当前系统时间
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final String todayStr = sdf.format(today);

        if (todayStr.equals(updateDate)) {
            //如果相等，则不从网络中获取数据
            Log.i("run", "日期相等，从数据库中获取数据");
            DBManager manager = new DBManager(getActivity());
            for (CompetitionItem competitionItem : manager.listAllCp()) {
                HashMap<String, String> stringHashMap = new HashMap<>();
                stringHashMap.put("cp_name", competitionItem.getCpName());
                stringHashMap.put("cp_id", String.valueOf(competitionItem.getId()));
                stringHashMap.put("c_stime", "开始时间：" + competitionItem.getCpStime());
                retList.add(stringHashMap);
            }
        } else {
            Log.i("run", "日期不相等，从服务器中获取在线数据");
            //获取网络数据
            try {
                List<CompetitionItem> cpList = new ArrayList<>();
                Thread.sleep(1000);
                //从服务器获取数据
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("url", "android");
                String url = "/SendCompetition";
                String info = WebService.executeHttpPost(url, hashMap);
                if (info.equals("0") || info.length() == 0) {
                    Looper.prepare();
                    Toast.makeText(getActivity(), "获取信息失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(info);
                        Iterator<String> iterator = jsonObject.keys();
                        while (iterator.hasNext()) {
                            String key = iterator.next();
                            JSONObject json = jsonObject.getJSONObject(key);
                            HashMap<String, String> map = new HashMap<>();
                            String cp_id = json.getString("cp_id");
                            String cp_name = json.getString("cp_name");
                            String category = json.getString("category");
                            String c_stime = json.getString("c_stime");
                            String c_etime = json.getString("c_etime");
                            String c_info = json.getString("c_info");

                            map.put("cp_id", cp_id);
                            map.put("cp_name", cp_name);
                            map.put("category", category);
                            map.put("c_stime", "开始时间：" + c_stime);
                            map.put("c_etime", c_etime);
                            map.put("c_info", c_info);
                            retList.add(map);

                            CompetitionItem competitionItem = new CompetitionItem(cp_name, category, c_stime, c_etime, c_info);
                            cpList.add(competitionItem);
                            Log.i(TAG, "run: 成功获取数据");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                //把数据写入数据库中
                DBManager manager = new DBManager(getActivity());
                manager.deleteAllCp();
                Log.i("db", "删除所有记录");
                manager.addAllCp(cpList);
                Log.i("db", "添加新记录集");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //更新记录日期
            SharedPreferences sp = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            edit.putString(DATE_SP_KEY, todayStr);
            edit.apply();
            Log.i("run", "更新日期结束：" + todayStr);
        }
        Message msg = handler.obtainMessage(2);
        msg.obj = retList;
        handler.sendMessage(msg);
        Log.i("run():thread", "sendMessage.....");
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HashMap<String, String> map = (HashMap<String, String>) parent.getItemAtPosition(position);
        int cp_id = Integer.parseInt(map.get("cp_id"));

        Intent info = new Intent(getActivity(), CompetitionInfoActivity.class);
        info.putExtra("cp_id", cp_id);
        startActivity(info);
    }
}
