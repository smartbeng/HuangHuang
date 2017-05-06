package com.lansum.eip.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lansum.eip.R;
import com.lansum.eip.fragment.HomeFragment;
import com.lansum.eip.fragment.MainFragment;
import com.lansum.eip.fragment.ProjectFragment;
import com.lansum.eip.fragment.WorkFragment;
import com.lansum.eip.http.Constants;
import com.lansum.eip.util.ActivityCollector;
import com.lansum.eip.util.ToastStudio;
import com.lansum.eip.webview.WebViewController;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    protected MainActivity mainActivity;

    private FragmentStatePagerAdapter mAdapter; //获取fragment的数量
    private List<Fragment> mFragments;          //fragment页面集合
    private PagerAdapter myPageAdapter;         //viewpager适配器（给viewpager添加fragment界面布局）
    FragmentManager fm ;                        //fragment管理器
    FragmentTransaction transaction ;           //fragment事务提交对象
    private ImageView addImageView;             //加号请假按钮
    private long ExitTime;                      //按下back键的时间(再按一次退出的参数)
    Fragment mTab0;                             //主页fragment界面
    WorkFragment mTab1;                             //我的fragment界面
    ProjectFragment mTab2;
    Fragment mTab3;

    //ViewPager滑动
    @BindView(R.id.vp_container) ViewPager mViewPager;

    //主页底部图标加文字_整体布局
    @BindView(R.id.tab1_liner) TextView tab1LinearLayout;

    //我的底部图标加文字_整体布局
    @BindView(R.id.tab2_liner) TextView tab2LinearLayout;

    //工作面板底部图标加文字_整体布局
    @BindView(R.id.tab3_liner) TextView tab3LinearLayout;

    //项目管理底部图标加文字_整体布局
    @BindView(R.id.tab4_liner) TextView tab4LinearLayout;

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

        /**
         * 主页底部（圆角加号）请假点击事件
         */
        addImageView = (ImageView) findViewById(R.id.add_img);
        addImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewWebViewActivity.class);
                intent.putExtra("url", Constants.urlHostHigh + Constants.urlFaBiaoRiZhi);
                intent.putExtra("animation", R.anim.bottom_dialog_out);
                startActivity(intent);
                overridePendingTransition(R.anim.bottom_dialog_in, R.anim.bottom_dialog_out);

            }
        });
        ButterKnife.bind(this);
        initView();
        initEvent();
        setSelect(0);
    }

    private void initEvent() {
        tab1LinearLayout.setOnClickListener(this);
        tab2LinearLayout.setOnClickListener(this);
        tab3LinearLayout.setOnClickListener(this);
        tab4LinearLayout.setOnClickListener(this);
    }

    /**
     *开启事务并初始化页面数据
     *
     */
    private void initView() {
        fm = getSupportFragmentManager();
        transaction = fm.beginTransaction();  //开启事务
        mFragments = new ArrayList<Fragment>();
        mTab0 = new MainFragment();
        mTab1 = new WorkFragment();
        mTab2 = new ProjectFragment();
        mTab3 = new HomeFragment();
        mFragments.add(mTab0);  //将new出来的fragment布局添加至fragment集合中
        mFragments.add(mTab1);
        mFragments.add(mTab2);
        mFragments.add(mTab3);

        /**
         * 获取集合中fragment页面数量
         * 获取位置坐标（0和1）
         */
        mAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()){

            @Override
            public int getCount()
            {
                return mFragments.size();
            }

            @Override
            public Fragment getItem(int arg0)
            {
                return mFragments.get(arg0);
            }
        };

        myPageAdapter = new PagerAdapter(){

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object){
                container.removeView(mFragments.get(position).getView());
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position){
				/*View view = mViews.get(position);
				container.addView(view);*/

                Fragment fragment = mFragments.get(position);
                if(!fragment.isAdded()){ // 如果fragment还没有added
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.add(fragment, fragment.getClass().getSimpleName());
                    ft.commit();
                    /**
                     * 在用FragmentTransaction.commit()方法提交FragmentTransaction对象后
                     * 会在进程的主线程中，用异步的方式来执行。
                     * 如果想要立即执行这个等待中的操作，就要调用这个方法（只能在主线程中调用）。
                     * 要注意的是，所有的回调和相关的行为都会在这个调用中被执行完成，因此要仔细确认这个方法的调用位置。
                     */
                    fm.executePendingTransactions();
                }

                if(fragment.getView().getParent() == null){
                    container.addView(fragment.getView()); // 为viewpager增加布局
                }

                return fragment.getView();
                //return view;
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1){
                return arg0 == arg1;
            }

            @Override
            public int getCount(){
                return mFragments.size();
            }
        };

        //设置缓存数，我们这里不需要预加载
        mViewPager .setOffscreenPageLimit(0);
        mViewPager.setAdapter(myPageAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){

            @Override
            public void onPageSelected(int arg0){
                int currentItem = mViewPager.getCurrentItem();
                setTab(currentItem);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2){
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0){
                // TODO Auto-generated method stub

            }
        });
    }

    private void setTab(int i){
        resetImgs();    // 设置图片为亮色
        setBottom(i);   // 切换内容区域
    }

    /**
     * 切换图片至暗色，不选中
     */
    private void resetImgs(){
        tab1LinearLayout.setSelected(false);
        tab2LinearLayout.setSelected(false);
        tab3LinearLayout.setSelected(false);
        tab4LinearLayout.setSelected(false);
    }
    //选中
    private void setBottom(int position){
        switch (position) {
            case 0:
                tab1LinearLayout.setSelected(true);
                break;
            case 1:
                tab2LinearLayout.setSelected(true);
                break;
            case 2:
                tab3LinearLayout.setSelected(true);
                break;
            case 3:
                tab4LinearLayout.setSelected(true);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tab1_liner:
                setSelect(0);
                break;
            case R.id.tab2_liner:
                setSelect(1);
                //mTab1.LoadUrl(Constants.urlHostHigh+Constants.urlAttendance);
                break;
            case R.id.tab3_liner:
                setSelect(2);
                //mTab2.LoadUrl(Constants.urlHostHigh+Constants.urlGongZuoRiZhi);
                break;
            case R.id.tab4_liner:
                setSelect(3);
                break;
            default:
                break;
        }
    }

    private void setSelect(int i){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        switch (i){
            case 0:
                //mImgWeixin.setImageResource(R.drawable.tab_weixin_pressed);
                break;
            case 1:
                //transaction.show(mTab02);
                //mTab1.onResume();   //当切换至第一个fragment界面时第二个准备好交互状态
                //	mImgFrd.setImageResource(R.drawable.tab_find_frd_pressed);
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                break;
        }
        setTab(i);
        mViewPager.setCurrentItem(i);
    }

    /**
     * 隐藏Fragment
     * @param transaction
     */
    private void hideFragment(FragmentTransaction transaction){
        if (mTab0!= null){
            transaction.hide(mTab0);
        }
        if (mTab1 != null){
            transaction.hide(mTab1);
        }
        if (mTab2 != null){
            transaction.hide(mTab2);
        }
        if (mTab3 != null){
            transaction.hide(mTab3);
        }
    }

    //页面销毁时将Activity移出栈
    @Override
    protected void onDestroy() {
        ActivityCollector.removeActivity(mainActivity);
        super.onDestroy();
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
                ToastStudio.showToast(this, "再按一次退出");
                ExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
