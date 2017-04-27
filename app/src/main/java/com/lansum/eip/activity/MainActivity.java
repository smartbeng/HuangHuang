package com.lansum.eip.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TabLayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.lansum.eip.BaseActivity;
import com.lansum.eip.R;
import com.lansum.eip.fragment.HomeFragment;
import com.lansum.eip.fragment.MainFragment;
import com.lansum.eip.fragment.QingJiaFragment;
import com.lansum.eip.http.Constants;
import com.lansum.eip.util.ActivityCollector;
import com.lansum.eip.util.StatusBarUtil;
import com.lansum.eip.util.ToastStudio;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    protected MainActivity mainActivity;

    //底部导航的空间容器
    @BindView(R.id.tab_bottom_container)
    TabLayout mTablayout;

    //ViewPager滑动
    @BindView(R.id.vp_container)
    ViewPager mViewPager;

    //加号请假按钮
    private ImageView mImageView;

    //Menu菜单
    private Menu mMenu;

    //按下back键的时间
    private long ExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 隐藏状态栏
         */
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * 将 menu 文件添加到 TabLayout 中
     * 将 TabLayout 与 ViewPager 关联
     * 实现点击和滑动页面的功能
     */
    private void initView() {
        //创建menu对象
        mMenu = new MenuBuilder(this);
        //将menu文件添加到menu对象
        getMenuInflater().inflate(R.menu.tab_bottom, mMenu);
        //将适配器与viewpager进行关联
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        //将TabLayout和ViewPager进行关联
        mTablayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < mMenu.size(); i++) {
                //循环获取每一个Tab对象
                TabLayout.Tab tab = mTablayout.getTabAt(i);
                //获取每一个menu中存储的数据
                MenuItem item = mMenu.getItem(i);
                //设置item显示的文本
                tab.setText(item.getTitle());
                tab.setIcon(item.getIcon());
        }


    }

    /**
     * 再按一次退出
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - ExitTime > 2000) {
                ToastStudio.showToast(this, "再按一次退出");
                ExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 滑动适配器
     */
    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new MainFragment();
                case 1:
                    return new QingJiaFragment();

                case 2:
                    return new HomeFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return mMenu.size();
        }
    }

    @Override
    protected void onDestroy() {
        ActivityCollector.removeActivity(mainActivity);
        super.onDestroy();
    }
}
