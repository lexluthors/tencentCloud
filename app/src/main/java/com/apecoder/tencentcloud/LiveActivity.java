package com.apecoder.tencentcloud;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.tencent.liteav.demo.common.view.BeautySettingPannel;
import com.tencent.rtmp.ITXLivePushListener;
import com.tencent.rtmp.TXLiveBase;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;

public class LiveActivity extends AppCompatActivity implements ITXLivePushListener, BeautySettingPannel.IOnBeautyParamsChangeListener{
    TXLivePushConfig  mLivePushConfig;
//    public static String STEAM_URL ="rtmp://38782.livepush.myqcloud.com/live/329d422f4a?bizid=38782&txSecret=cca0e5d696ea9ddd8cd0b80b1bfa5748&txTime=5C531B7E";
    public static String STEAM_URL ="rtmp://plugin_push.youhuiquanqiu.com/live/826054362?txSecret=ccc5e2196eee5c92f4a076c021d360d4&txTime=5C36E2F9";
    TXLivePusher mLivePusher;

    BeautySettingPannel  mBeautyPannelView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
        String sdkver = TXLiveBase.getSDKVersionStr();
        Log.d("liteavsdk", "liteav sdk version is : " + sdkver);

        mLivePusher = new TXLivePusher(LiveActivity.this);
        mLivePushConfig = new TXLivePushConfig();
        mLivePusher.setConfig(mLivePushConfig);

        mLivePusher.startPusher(STEAM_URL);

        TXCloudVideoView mCaptureView = (TXCloudVideoView) findViewById(R.id.video_view);
        mLivePusher.startCameraPreview(mCaptureView);

        //美颜p图部分
        mBeautyPannelView = (BeautySettingPannel) findViewById(R.id.layoutFaceBeauty);
        mBeautyPannelView.setBeautyParamsChangeListener(this);

        Button mBtnFaceBeauty = (Button)findViewById(R.id.btnFaceBeauty);
        mBtnFaceBeauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBeautyPannelView.setVisibility(mBeautyPannelView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopRtmpPublish();
    }

    //结束推流，注意做好清理工作
    public void stopRtmpPublish() {
        mLivePusher.stopCameraPreview(true); //停止摄像头预览
        mLivePusher.stopPusher();            //停止推流
        mLivePusher.setPushListener(null);   //解绑 listener
    }


    private int              mBeautyStyle = TXLiveConstants.BEAUTY_STYLE_SMOOTH;
    private int              mBeautyLevel = 5;
    private int              mWhiteningLevel = 3;
    private int              mRuddyLevel = 2;

    @Override
    public void onBeautyParamsChange(BeautySettingPannel.BeautyParams params, int key) {
        switch (key) {
            case BeautySettingPannel.BEAUTYPARAM_EXPOSURE:
                if (mLivePusher != null) {
                    mLivePusher.setExposureCompensation(params.mExposure);
                }
                break;
            case BeautySettingPannel.BEAUTYPARAM_BEAUTY:
                mBeautyStyle = params.mBeautyStyle;
                mBeautyLevel = params.mBeautyLevel;
                if (mLivePusher != null) {
                    mLivePusher.setBeautyFilter(mBeautyStyle, mBeautyLevel, mWhiteningLevel, mRuddyLevel);
                }
                break;
            case BeautySettingPannel.BEAUTYPARAM_WHITE:
                mWhiteningLevel = params.mWhiteLevel;
                if (mLivePusher != null) {
                    mLivePusher.setBeautyFilter(mBeautyStyle, mBeautyLevel, mWhiteningLevel, mRuddyLevel);
                }
                break;
            case BeautySettingPannel.BEAUTYPARAM_BIG_EYE:
                if (mLivePusher != null) {
                    mLivePusher.setEyeScaleLevel(params.mBigEyeLevel);
                }
                break;
            case BeautySettingPannel.BEAUTYPARAM_FACE_LIFT:
                if (mLivePusher != null) {
                    mLivePusher.setFaceSlimLevel(params.mFaceSlimLevel);
                }
                break;
            case BeautySettingPannel.BEAUTYPARAM_FILTER:
                if (mLivePusher != null) {
                    mLivePusher.setFilter(params.mFilterBmp);
                }
                break;
            case BeautySettingPannel.BEAUTYPARAM_GREEN:
                if (mLivePusher != null) {
                    mLivePusher.setGreenScreenFile(params.mGreenFile);
                }
                break;
            case BeautySettingPannel.BEAUTYPARAM_MOTION_TMPL:
                if (mLivePusher != null) {
                    mLivePusher.setMotionTmpl(params.mMotionTmplPath);
                }
                break;
            case BeautySettingPannel.BEAUTYPARAM_RUDDY:
                mRuddyLevel = params.mRuddyLevel;
                if (mLivePusher != null) {
                    mLivePusher.setBeautyFilter(mBeautyStyle, mBeautyLevel, mWhiteningLevel, mRuddyLevel);
                }
                break;
//            case BeautySettingPannel.BEAUTYPARAM_BEAUTY_STYLE:
//                mBeautyStyle = params.mBeautyStyle;
//                if (mLivePusher != null) {
//                    mLivePusher.setBeautyFilter(mBeautyStyle, mBeautyLevel, mWhiteningLevel, mRuddyLevel);
//                }
//                break;
            case BeautySettingPannel.BEAUTYPARAM_FACEV:
                if (mLivePusher != null) {
                    mLivePusher.setFaceVLevel(params.mFaceVLevel);
                }
                break;
            case BeautySettingPannel.BEAUTYPARAM_FACESHORT:
                if (mLivePusher != null) {
                    mLivePusher.setFaceShortLevel(params.mFaceShortLevel);
                }
                break;
            case BeautySettingPannel.BEAUTYPARAM_CHINSLIME:
                if (mLivePusher != null) {
                    mLivePusher.setChinLevel(params.mChinSlimLevel);
                }
                break;
            case BeautySettingPannel.BEAUTYPARAM_NOSESCALE:
                if (mLivePusher != null) {
                    mLivePusher.setNoseSlimLevel(params.mNoseScaleLevel);
                }
                break;
            case BeautySettingPannel.BEAUTYPARAM_FILTER_MIX_LEVEL:
                if (mLivePusher != null) {
                    mLivePusher.setSpecialRatio(params.mFilterMixLevel/10.f);
                }
                break;
//            case BeautySettingPannel.BEAUTYPARAM_CAPTURE_MODE:
//                if (mLivePusher != null) {
//                    boolean bEnable = ( 0 == params.mCaptureMode ? false : true);
//                    mLivePusher.enableHighResolutionCapture(bEnable);
//                }
//                break;
//            case BeautySettingPannel.BEAUTYPARAM_SHARPEN:
//                if (mLivePusher != null) {
//                    mLivePusher.setSharpenLevel(params.mSharpenLevel);
//                }
//                break;
        }
    }

    @Override
    public void onPushEvent(int event, Bundle param) {
//        Log.e("NotifyCode","LivePublisherActivity :" + event);
        String msg = param.getString(TXLiveConstants.EVT_DESCRIPTION);
        String pushEventLog = "receive event: " + event + ", " + msg;
//        if (mLivePusher != null) {
//            mLivePusher.onLogRecord("[event:" + event + "]" + msg + "\n");
//        }
        //错误还是要明确的报一下
//        if (event < 0) {
//            Toast.makeText(getApplicationContext(), param.getString(TXLiveConstants.EVT_DESCRIPTION), Toast.LENGTH_SHORT).show();
//            if(event == TXLiveConstants.PUSH_ERR_OPEN_CAMERA_FAIL || event == TXLiveConstants.PUSH_ERR_OPEN_MIC_FAIL){
//                stopPublishRtmp();
//            }
//        }
//
//        if (event == TXLiveConstants.PUSH_ERR_NET_DISCONNECT || event == TXLiveConstants.PUSH_ERR_INVALID_ADDRESS) {
//            stopPublishRtmp();
//        }
//        else if (event == TXLiveConstants.PUSH_WARNING_HW_ACCELERATION_FAIL) {
//            Toast.makeText(getApplicationContext(), param.getString(TXLiveConstants.EVT_DESCRIPTION), Toast.LENGTH_SHORT).show();
//            mLivePushConfig.setHardwareAcceleration(TXLiveConstants.ENCODE_VIDEO_SOFTWARE);
//            mBtnHWEncode.setBackgroundResource(R.drawable.quick2);
//            mLivePusher.setConfig(mLivePushConfig);
//            mHWVideoEncode = false;
//        }
//        else if (event == TXLiveConstants.PUSH_ERR_SCREEN_CAPTURE_UNSURPORT) {
//            stopPublishRtmp();
//        }
//        else if (event == TXLiveConstants.PUSH_ERR_SCREEN_CAPTURE_START_FAILED) {
//            stopPublishRtmp();
//        } else if (event == TXLiveConstants.PUSH_EVT_CHANGE_RESOLUTION) {
//        } else if (event == TXLiveConstants.PUSH_EVT_CHANGE_BITRATE) {
//        } else if (event == TXLiveConstants.PUSH_WARNING_NET_BUSY) {
//            ++mNetBusyCount;
//            showNetBusyTips();
//        } else if (event == TXLiveConstants.PUSH_EVT_START_VIDEO_ENCODER) {
//            int encType = param.getInt(TXLiveConstants.EVT_PARAM1);
//            mHWVideoEncode = (encType == 1);
//            mBtnHWEncode.getBackground().setAlpha(mHWVideoEncode ? 255 : 100);
//        }
    }

    @Override
    public void onNetStatus(Bundle status) {
//        if (mLivePusher != null){
//            mLivePusher.onLogRecord("[net state]:\n"+str+"\n");
//        }
    }
}
