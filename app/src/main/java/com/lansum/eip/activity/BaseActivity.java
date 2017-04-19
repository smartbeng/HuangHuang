package com.lansum.eip.activity;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.umeng.message.PushAgent;

import static anet.channel.util.Utils.context;

/**
 * Created by MaiBenBen on 2017/4/12.
 */

public class BaseActivity extends AppCompatActivity {

    public static Activity baseActivity;

    public PushAgent mPushAgent;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {

        baseActivity = this;
        //友盟统计应用启动数据
        PushAgent.getInstance(context).onAppStart();
        //声明 RxPermissions 库引用
       /* RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        // I can control the camera now
                        Toast.makeText(BaseActivity.this, "你同意了", Toast.LENGTH_SHORT).show();
                    } else {
                        // Oups permission denied
                        Toast.makeText(BaseActivity.this, "您拒绝了", Toast.LENGTH_SHORT).show();
                    }
                });*/

        super.onCreate(savedInstanceState, persistentState);
    }

}



