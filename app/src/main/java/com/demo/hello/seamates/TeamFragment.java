package com.demo.hello.seamates;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
public class TeamFragment extends Fragment implements AdapterView.OnItemClickListener, Runnable, OnClickListener, AdapterView.OnItemLongClickListener {
    private static final String TAG = "TeamFragment";
    private final String DATE_SP_KEY = "lastTeamDate";
    private String data[] = {"wait..."};
    private String lastDate = "";
    private ListView list;
    private Handler handler;
    private List<HashMap<String, String>> listItems;//存放文字、图片信息
    private SimpleAdapter listItemAdapter;//适配器
    private FloatingActionButton fab;

    public TeamFragment() {
        // Required empty public constructor
    }


    @SuppressLint("HandlerLeak")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 初始化Fragment的布局
        View view = inflater.inflate(R.layout.fragment_team, container, false);

        //获取sp上次保存时间
        SharedPreferences sp = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        lastDate = sp.getString(DATE_SP_KEY, "");
        Log.i("List", "lastDateStr=" + lastDate);

        //初始化界面
        list = view.findViewById(R.id.team_lv);
        ListAdapter adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, data);
        list.setAdapter(adapter);

        list.setEmptyView(view.findViewById(R.id.team_tv_nodata));
        list.setOnItemClickListener(this);
        list.setOnItemLongClickListener(this);

        //悬浮按钮添加监听
        fab = view.findViewById(R.id.team_fab);
        fab.setOnClickListener(this);
        //开启子线程
        Thread t = new Thread(this);
        t.start();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 3) {
                    listItems = (List<HashMap<String, String>>) msg.obj;
                    listItemAdapter = new SimpleAdapter(getActivity(), listItems,
                            R.layout.team_list,
                            new String[]{"team_name", "leader", "team_id", "qq"},
                            new int[]{R.id.teamList_title, R.id.teamList_leader, R.id.teamList_id, R.id.teamList_qq}
                    );
                    list.setAdapter(listItemAdapter);

                }
                super.handleMessage(msg);
            }
        };

        return view;
//        return inflater.inflate(R.layout.fragment_team, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 4 && resultCode == 5) {
            List<HashMap<String, String>> retList = new ArrayList<>();
            DBManager manager = new DBManager(getActivity());
            for (TeamItem teamItem : manager.listAllTeam()) {
                HashMap<String, String> stringHashMap = new HashMap<>();
                stringHashMap.put("team_name", teamItem.getTeamName());
                stringHashMap.put("team_id", String.valueOf(teamItem.getTeamId()));
                stringHashMap.put("leader", getString(R.string.leadText) + teamItem.getLeader());
                stringHashMap.put("qq", getString(R.string.qqText) + teamItem.getQq());
                retList.add(stringHashMap);
            }
            listItemAdapter = new SimpleAdapter(getActivity(), retList,
                    R.layout.team_list,
                    new String[]{"team_name", "leader", "team_id", "qq"},
                    new int[]{R.id.teamList_title, R.id.teamList_leader, R.id.teamList_id, R.id.teamList_qq}
            );
            list.setAdapter(listItemAdapter);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HashMap<String, String> map = (HashMap<String, String>) parent.getItemAtPosition(position);
        int team_id = Integer.parseInt(map.get("team_id"));
        //打开新页面
        Intent teamInfo = new Intent(getActivity(), TeamInfoActivity.class);
        teamInfo.putExtra("team_id", team_id);
        startActivity(teamInfo);

    }

    @Override
    public void run() {
        Log.i(TAG, "run: 开始获取队伍数据");
        List<HashMap<String, String>> retList = new ArrayList<>();

        //获取当前系统时间
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final String todayStr = sdf.format(today);

        if (todayStr.equals(lastDate)) {
            //如果相等，则不从服务器中获取数据
            Log.i("run", "日期相等，从数据库中获取数据");
            DBManager manager = new DBManager(getActivity());
            for (TeamItem teamItem : manager.listAllTeam()) {
                HashMap<String, String> stringHashMap = new HashMap<>();
                stringHashMap.put("team_name", teamItem.getTeamName());
                stringHashMap.put("team_id", String.valueOf(teamItem.getTeamId()));
                stringHashMap.put("leader", getString(R.string.leadText) + teamItem.getLeader());
                stringHashMap.put("qq", getString(R.string.qqText) + teamItem.getQq());
                retList.add(stringHashMap);
            }
        } else {
            Log.i("run", "日期不相等，从服务器中获取在线数据");
            //获取网络数据
            try {
                List<TeamItem> teamList = new ArrayList<>();
                Thread.sleep(1000);
                //从服务器获取数据
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("url", "android");
                String url = "/SendTeam";
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
                            String team_id = json.getString("team_id");
                            String team_name = json.getString("team_name");
                            String cp_name = json.getString("cp_name");
                            String leader = json.getString("leader");
                            String mates_num = json.getString("mates_num");
                            String qq = json.getString("qq");
                            String detail = json.getString("detail");

                            map.put("team_id", team_id);
                            map.put("team_name", team_name);
                            map.put("cp_name", cp_name);
                            map.put("leader", getString(R.string.leadText) + leader);
                            map.put("mates_num", mates_num);
                            map.put("qq", getString(R.string.qqText) + qq);
                            map.put("detail", detail);

                            retList.add(map);

                            TeamItem teamItem = new TeamItem(cp_name, team_name, leader, Integer.parseInt(mates_num), qq, detail);
                            teamList.add(teamItem);
                            Log.i(TAG, "run: 成功获取数据");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                //把数据写入数据库中
                DBManager manager = new DBManager(getActivity());
                manager.deleteAllTeam();
                Log.i("db", "删除所有队伍记录");
                manager.addAllTeam(teamList);
                Log.i("db", "添加新队伍记录集");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //更新记录日期
            SharedPreferences sp = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            edit.putString(DATE_SP_KEY, todayStr);
            edit.apply();
            Log.i("run", "更新队伍日期：" + todayStr);
        }
        Message msg = handler.obtainMessage(3);
        msg.obj = retList;
        handler.sendMessage(msg);
        Log.i("run():thread", "sendMessage.....");
    }

    @Override
    public void onClick(View v) {
        Intent addTeam = new Intent(getActivity(), InitiateTeamActivity.class);
        startActivityForResult(addTeam, 4);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        HashMap<String, String> map = (HashMap<String, String>) parent.getItemAtPosition(position);
        final int team_id = Integer.parseInt(map.get("team_id"));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示").setMessage("请确定是否删除当前数据").setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG, "onClick:对话框事件处理");
                listItems.remove(position);
                listItemAdapter.notifyDataSetChanged();
                DBManager manager = new DBManager(getActivity());
                manager.deleteTeam(team_id);

            }
        })
                .setNegativeButton("否", null);
        builder.create().show();
        Log.i(TAG, "onItemLongClick:size=" + listItems.size());
        return true;
    }
}
