package com.demo.hello.seamates;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment implements Runnable {

    Handler handler;
    //    private ArrayList<HashMap<String, String>> listItems;//存放文字、图片信息
    private List<HashMap<String, String>> listItems;//存放文字、图片信息
    private SimpleAdapter listItemAdapter;//适配器
    private static final String TAG = "FirstFragment";
    ListView list;


    public FirstFragment() {
        // Required empty public constructor
    }
    @SuppressLint("HandlerLeak")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listItems = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put("ItemTitle", "Rate: " + i);//标题文字
            map.put("ItemDetail", "" + i);//详情讲述
            listItems.add(map);
        }
        listItemAdapter = new SimpleAdapter(getActivity(),listItems, R.layout.competition_info,
                new String[]{"ItemTitle", "ItemDetail"},
                new int[]{R.id.itemTitle, R.id.itemDetail});
        Thread t = new Thread(this);
        t.start();

//        initListView();
//
//        this.setListAdapter(listItemAdapter);
//        MyAdapter myAdapter = new MyAdapter(this, R.layout.list_item, listItems);
//        this.setListAdapter(myAdapter);
//
//
//        handler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                if (msg.what == 9) {
////                    List<HashMap<String, String>> listItems = (List<HashMap<String, String>>) msg.obj;
//                    listItems = (List<HashMap<String, String>>) msg.obj;
//                    listItemAdapter = new SimpleAdapter(MyList2Activity.this, listItems,//listItems数据源
//                            R.layout.list_item,//litItem的xml布局实现
//                            new String[]{"ItemTitle", "ItemDetail"},
//                            new int[]{R.id.itemTitle, R.id.itemDetail}
//                    );
//                    setListAdapter(listItemAdapter);
//                }
//                super.handleMessage(msg);
//            }
//        };
//        getListView().setOnItemClickListener(this);
//        getListView().setOnItemLongClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View info = inflater.inflate(R.layout.fragment_first, container, false);
        list = info.findViewById(R.id.lv);
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
        list.setAdapter(listItemAdapter);
        return info;
//        return inflater.inflate(R.layout.fragment_first, container, false);
    }



    public void run() {
        Log.i("thread", "run.....");
        List<HashMap<String, String>> retList = new ArrayList<HashMap<String, String>>();

        Message msg = handler.obtainMessage(9);
        msg.obj = retList;
        handler.sendMessage(msg);
        Log.i("run():thread", "sendMessage.....");
    }


}
