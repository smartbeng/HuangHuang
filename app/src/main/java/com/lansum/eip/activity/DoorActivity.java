package com.lansum.eip.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lansum.eip.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DoorActivity extends AppCompatActivity {

    @BindView(R.id.door_toolbar)
    Toolbar doorToolBar;

    @BindView(R.id.open_door_gif)
    ImageView openDoorGif;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door);
        ButterKnife.bind(this);
        // 设置 toolbar 显示的标题
        //doorToolBar.setTitle(fileModels.get(currPosition).getFileName());
        doorToolBar.setTitle("开门");
        // 添加返回的箭头
        doorToolBar.setNavigationIcon(R.drawable.fanhui);
        // 让toolbar继承actionBar的属性
        setSupportActionBar(doorToolBar);


        /**
         * 设置点击返回箭头的事件  一定要放在 setSupportActionBar 之后设置
         */
        doorToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 点击箭头 关闭当前的activity
                finish();
            }
        });
        String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493208220&di=9601923c400f390bb7f49e44ce8ef416&imgtype=jpg&er=1&src=http%3A%2F%2Fimage.tianjimedia.com%2FuploadImages%2F2013%2F324%2F2V0Y78O558E8.jpg";
        Glide.with(DoorActivity.this).load(R.drawable.opendoor).into(openDoorGif);
    }

    /**
     * 加载开门Gif图片
     * @param view
     */
    public void loadGif(View view){

    }
}
