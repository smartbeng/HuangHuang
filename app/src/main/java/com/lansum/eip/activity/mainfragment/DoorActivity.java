package com.lansum.eip.activity.mainfragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.lansum.eip.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.litesuits.android.async.AsyncExecutor.handler;

public class DoorActivity extends AppCompatActivity {

    @BindView(R.id.door_toolbar)
    Toolbar doorToolBar;

    @BindView(R.id.open_door_gif)
    ImageView openDoorGif;

    @BindView(R.id.yaoshi_401)
    ImageView openDoor401;

    @BindView(R.id.yaoshi_408)
    ImageView openDoor408;

    @BindView(R.id.yaoshi_3)
    ImageView openDoor3;

    @BindView(R.id.yaoshi_4)
    ImageView openDoor4;

    //handler发送消息成功的状态码
    private static final int MESSAGE_SUCCESS = 4424;

    //handler发送消息所携带的参数（持续时间）
    private int duration;

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
        //加载静止状态下的Gif
        loadGif0(openDoorGif);
    }

    /**
     * 钥匙image点击事件
     * @param v
     */
    @OnClick({R.id.yaoshi_4,R.id.yaoshi_401,R.id.yaoshi_408,R.id.yaoshi_3})
    void onClick(View v){
        switch (v.getId()){
            case R.id.yaoshi_4:
                //播放一次Gif动图 以下同是
                loadGif(openDoorGif);
                break;
            case R.id.yaoshi_401:
                loadGif(openDoorGif);
                break;
            case R.id.yaoshi_408:
                loadGif(openDoorGif);
                break;
            case R.id.yaoshi_3:
                loadGif(openDoorGif);
                break;
        }
    }

    /**
     * 加载开门Gif动图(只播放一次)
     * @param view
     */
    public void loadGif(View view){
        //Glide.with(DoorActivity.this).load(R.drawable.opendoor).into(openDoorGif);
        Glide.with(this)
                .load(R.drawable.opendoor)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<Integer, GlideDrawable>() {

                    @Override
                    public boolean onException(Exception arg0, Integer arg1,
                                               Target<GlideDrawable> arg2, boolean arg3) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource,
                                                   Integer model, Target<GlideDrawable> target,
                                                   boolean isFromMemoryCache, boolean isFirstResource) {
                        // 计算动画时长
                        GifDrawable drawable = (GifDrawable) resource;
                        GifDecoder decoder = drawable.getDecoder();
                        for (int i = 0; i < drawable.getFrameCount(); i++) {
                            duration += decoder.getDelay(i);
                        }
                        //发送延时消息，通知动画结束
                        handler.sendEmptyMessageDelayed(MESSAGE_SUCCESS,
                                duration);
                        return false;
                    }
                }) //仅仅加载一次gif动画
                .into(new GlideDrawableImageViewTarget(openDoorGif, 1));
    }

    /**
     * /**
     * 加载开门静止状态下的Gif图片
     * @param view
     */
    public void loadGif0(View view){
        //Glide.with(DoorActivity.this).load(R.drawable.opendoor).into(openDoorGif);
        Glide.with(this)
                .load(R.drawable.opendoor)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<Integer, GlideDrawable>() {

                    @Override
                    public boolean onException(Exception arg0, Integer arg1,
                                               Target<GlideDrawable> arg2, boolean arg3) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource,
                                                   Integer model, Target<GlideDrawable> target,
                                                   boolean isFromMemoryCache, boolean isFirstResource) {
                        // 计算动画时长
                        GifDrawable drawable = (GifDrawable) resource;
                        GifDecoder decoder = drawable.getDecoder();
                        for (int i = 0; i < drawable.getFrameCount(); i++) {
                            duration += decoder.getDelay(i);
                        }
                        //发送延时消息，通知动画结束
                        handler.sendEmptyMessageDelayed(MESSAGE_SUCCESS,
                                duration);
                        return false;
                    }
                }) //仅仅加载一次gif动画
                .into(new GlideDrawableImageViewTarget(openDoorGif, 0));
    }
}
