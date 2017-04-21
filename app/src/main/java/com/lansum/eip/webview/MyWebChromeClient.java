package com.lansum.eip.webview;

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

public class MyWebChromeClient extends WebChromeClient {
    public static final int FILECHOOSER_RESULTCODE =1212;
    private String mCameraFilePath;
    private ValueCallback<Uri> mUploadMessage;
    Activity act;
    WebView webview;

    public MyWebChromeClient(Context act){

        this.act=(Activity) act;
//		webview=(WebView)act.findViewById(R.id.webView);
    }


    // 一个回调接口使用的主机应用程序通知当前页面的自定义视图已被撤职
    CustomViewCallback customViewCallback;

    //配置权限（同样在WebChromeClient中实现）
    @Override
    public void onGeolocationPermissionsShowPrompt(String origin,
                                                   GeolocationPermissions.Callback callback) {
        callback.invoke(origin, true, false);
        super.onGeolocationPermissionsShowPrompt(origin, callback);
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
