package com.lansum.lansumhh.webview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.webkit.GeolocationPermissions;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import java.io.File;

/**
 * Created by MaiBenBen on 2017/4/14.
 */

public class MyWebViewClient extends WebChromeClient {
    public static final int FILECHOOSER_RESULTCODE =1212;
    private String mCameraFilePath;
    private ValueCallback<Uri> mUploadMessage;
    Activity act;
    WebView webview;

    public MyWebViewClient(Context act){

        this.act=(Activity) act;
//		webview=(WebView)act.findViewById(R.id.webView);
    }


    // 一个回调接口使用的主机应用程序通知当前页面的自定义视图已被撤职
    CustomViewCallback customViewCallback;
    // 进入全屏的时候
    @Override
    public void onShowCustomView(View view, CustomViewCallback callback) {
        // 赋值给callback
        customViewCallback = callback;
        // 设置webView隐藏
        webview.setVisibility(View.GONE);
        // 声明video，把之后的视频放到这里面去
//        FrameLayout video = (FrameLayout) act.findViewById(R.id.video_view);
        // 将video放到当前视图中
//        video.addView(view);
//        video.setVisibility(View.VISIBLE);
        // 横屏显示
        act.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // 设置全屏
        setFullScreen();
    }

    //配置权限（同样在WebChromeClient中实现）
    @Override
    public void onGeolocationPermissionsShowPrompt(String origin,
                                                   GeolocationPermissions.Callback callback) {
        callback.invoke(origin, true, false);
        super.onGeolocationPermissionsShowPrompt(origin, callback);
    }
    // 退出全屏的时候
    @Override
    public void onHideCustomView() {
        if (customViewCallback != null) {
            // 隐藏掉
            customViewCallback.onCustomViewHidden();
        }
        // 用户当前的首选方向
        act.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
        // 退出全屏
        quitFullScreen();

        // 设置WebView可见
        webview.setVisibility(View.VISIBLE);

    }

    /**
     * 设置全屏
     */
    private void setFullScreen() {
        // 设置全屏的相关属性，获取当前的屏幕状态，然后设置全屏
        act. getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 全屏下的状态码：1098974464
        // 窗口下的状态吗：1098973440
    }

    /**
     * 退出全屏
     */
    private void quitFullScreen() {
        // 声明当前屏幕状态的参数并获取
        final WindowManager.LayoutParams attrs = act.getWindow().getAttributes();
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        act.getWindow().setAttributes(attrs);
        act.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
    }

    // For Android 3.0+
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        myopenFileChooser(uploadMsg, acceptType);
    }
    // For Android < 3.0
    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        myopenFileChooser(uploadMsg, "");
    }


    // For Android  > 4.1.1
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        myopenFileChooser(uploadMsg, acceptType);
    }

    //5.1.1

    public void myopenFileChooser(ValueCallback<Uri> uploadMsg,
                                  String acceptType) {
        mUploadMessage = uploadMsg;

        act.startActivityForResult(
                Intent.createChooser(createDefaultOpenableIntent(), "完成操作需要使用"),
                FILECHOOSER_RESULTCODE);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (null == mUploadMessage) {
            return;
        }
        if(requestCode==FILECHOOSER_RESULTCODE) {
            // if (null == mUploadMessage) return;
            Uri result = data == null || resultCode !=act. RESULT_OK ? null
                    : data.getData();
            if (result == null && data == null && resultCode == Activity.RESULT_OK) {
                File cameraFile = new File(mCameraFilePath);
                if (cameraFile.exists()) {
                    result = Uri.fromFile(cameraFile);
                    // Broadcast to the media scanner that we have a new photo
                    // so it will be added into the gallery for the user.
                    act. sendBroadcast(
                            new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, result));
                }
            }
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;

        }else{
            return;
        }

    }

    private Intent createDefaultOpenableIntent() {
        // Create and return a chooser with the default OPENABLE
        // actions including the camera, camcorder and sound
        // recorder where available.
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");

        Intent chooser = createChooserIntent(
                createCameraIntent()
                // createCamcorderIntent(),
                //createSoundRecorderIntent()
        );
        chooser.putExtra(Intent.EXTRA_INTENT, i);
        return chooser;
    }

    private Intent createChooserIntent(Intent... intents) {
        Intent chooser = new Intent(Intent.ACTION_CHOOSER);
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents);
        chooser.putExtra(Intent.EXTRA_TITLE, "File Chooser");
        return chooser;
    }

    private Intent createCameraIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File externalDataDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM);
        File cameraDataDir = new File(externalDataDir.getAbsolutePath() +
                File.separator + "browser-photos");
        cameraDataDir.mkdirs();
        mCameraFilePath = cameraDataDir.getAbsolutePath() + File.separator +
                System.currentTimeMillis() + ".jpg";
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mCameraFilePath)));
        return cameraIntent;
    }

    private Intent createCamcorderIntent() {
        return new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
    }

    private Intent createSoundRecorderIntent() {
        return new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
    }
}
