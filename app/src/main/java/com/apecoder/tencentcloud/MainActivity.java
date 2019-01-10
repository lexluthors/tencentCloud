package com.apecoder.tencentcloud;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.rtmp.TXLiveBase;
import com.tencent.rtmp.TXLivePushConfig;

import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {
    TXLivePushConfig  mLivePushConfig;
    public static String STEAM_URL ="rtmp://38782.livepush.myqcloud.com/live/012f6b5679?bizid=38782&txSecret=490e9009f2e48fc94366e64eb7a08970&txTime=5C2A3CFE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String sdkver = TXLiveBase.getSDKVersionStr();
        Log.d("liteavsdk", "liteav sdk version is : " + sdkver);

//        TXLivePusher mLivePusher = new TXLivePusher(MainActivity.this);
//        mLivePushConfig = new TXLivePushConfig();
//        mLivePusher.setConfig(mLivePushConfig);
//
//        mLivePusher.startPusher(STEAM_URL);
//
//        TXCloudVideoView mCaptureView = (TXCloudVideoView) findViewById(R.id.video_view);
//        mLivePusher.startCameraPreview(mCaptureView);
        findViewById(R.id.open_live).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开摄像头
                getCameraPermission();
            }
        });
        getPermission();
    }

    private void getPermission(){
        RxPermissions rxPermission = new RxPermissions(MainActivity.this);
        rxPermission.requestEach(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                        }
                    }
                });
    }
    private void getCameraPermission(){
        RxPermissions rxPermission = new RxPermissions(MainActivity.this);
        rxPermission.requestEach(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            switch (permission.name) {
                                case Manifest.permission.CAMERA:
                                    //同意开启摄像头
                                    Intent intent = new Intent(MainActivity.this,LiveActivity.class);
                                    startActivity(intent);
                                    break;
                            }
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                        }
                    }
                });
    }
}
