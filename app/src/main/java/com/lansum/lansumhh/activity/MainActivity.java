package com.lansum.lansumhh.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.lansum.lansumhh.R;
import com.lansum.lansumhh.fragment.HomeFragment;
import com.lansum.lansumhh.fragment.MainFragment;
import com.lansum.lansumhh.fragment.QingJiaFragment;
import com.lansum.lansumhh.http.Constants;
import com.lansum.lansumhh.util.ToastStudio;
import com.litesuits.common.data.DataKeeper;
import com.litesuits.common.utils.NotificationUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    //头部导航栏
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;

    //底部导航的空间容器
    @BindView(R.id.tab_bottom_container)
    TabLayout mTablayout;

    //ViewPager滑动
    @BindView(R.id.vp_container)
    ViewPager mViewPager;

    //Menu菜单
    private Menu mMenu;

    //按下back键的时间
    private long ExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        /*DataKeeper dataKeeper = new DataKeeper(this,"HH");
        dataKeeper.put("Id",12);
        NotificationUtil.notification(this,"");*/

        //设置toolbar为原来的actionBar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);   //去除标题栏
            actionBar.setHomeAsUpIndicator(R.drawable.navbar_message);
        }
        initView();
    }

    private void initView() {
        //创建menu对象
        mMenu = new MenuBuilder(this);
        //将menu文件添加到menu对象
        getMenuInflater().inflate(R.menu.tab_bottom,mMenu);
        //将适配器与viewpager进行关联
        mViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
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
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - ExitTime > 2000) {
                ToastStudio.showToast(this,"再按一次退出");
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
            switch (position){
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

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_head, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.drawable.navbar_message:
                ToastStudio.showToast(this,"通知呀");
                break;
            case R.id.nation:
                ToastStudio.showToast(this,"开门呀");
                break;
            default:
        }
        return true;
    }


}
