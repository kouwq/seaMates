package com.demo.hello.seamates;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private MyPageAdapter pageAdapter;
    private RadioGroup radioGroup;
    private RadioButton rbtHome, rbtFunc, rbtSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {
        /**
         * RadioGroup部分
         */
        radioGroup = findViewById(R.id.bottomGroup);
        rbtHome = findViewById(R.id.radioHome);
        rbtFunc = findViewById(R.id.radioFunc);
        rbtSetting = findViewById(R.id.radioSetting);
        //RadioGroup选中状态改变监听
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    //setCurrentItem第二个参数控制页面切换动画 true:打开/false:关闭
                    case R.id.radioHome:
                        viewPager.setCurrentItem(0, true);
                        break;
                    case R.id.radioFunc:
                        viewPager.setCurrentItem(1, true);
                        break;
                    case R.id.radioSetting:
                        viewPager.setCurrentItem(2, true);
                        break;
                }
            }
        });

        /**
         * ViewPager部分
         */
        viewPager = findViewById(R.id.viewpager);

        CompetitionFragment competitionFragment = new CompetitionFragment();
        TeamFragment teamFragment = new TeamFragment();
        UserFragment userFragment = new UserFragment();
        List<Fragment> Fragments = new ArrayList<Fragment>();
        Fragments.add(competitionFragment);
        Fragments.add(teamFragment);
        Fragments.add(userFragment);
        //ViewPager设置适配器
        pageAdapter = new MyPageAdapter(getSupportFragmentManager(), Fragments);
        viewPager.setAdapter(pageAdapter);
        //ViewPager显示第一个Fragment
        viewPager.setCurrentItem(0);
        //ViewPager页面切换监听
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.inf, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (viewPager.getCurrentItem()) {
            case 0:
                menu.findItem(R.id.menu_set).setVisible(false);
                menu.findItem(R.id.action_search).setVisible(true);
                break;
            case 1:
                menu.findItem(R.id.menu_set).setVisible(false);
                menu.findItem(R.id.action_search).setVisible(true);
                break;
            case 2:
                menu.findItem(R.id.menu_set).setVisible(true);
                menu.findItem(R.id.action_search).setVisible(false);
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_set) {
            Toast.makeText(this, "set.png", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_search) {
            Toast.makeText(this, "其他", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }


    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                radioGroup.check(R.id.radioHome);
                break;
            case 1:
                radioGroup.check(R.id.radioFunc);
                break;
            case 2:
                radioGroup.check(R.id.radioSetting);
                break;

        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
