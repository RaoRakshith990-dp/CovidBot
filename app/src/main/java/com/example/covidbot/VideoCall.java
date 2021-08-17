package com.example.covidbot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.covidbot.initsdk.InitAuthSDKCallback;
import com.example.covidbot.initsdk.InitAuthSDKHelper;
import com.example.covidbot.initsdk.UserLoginCallback;

import us.zoom.sdk.JoinMeetingOptions;
import us.zoom.sdk.JoinMeetingParams;
import us.zoom.sdk.MeetingServiceListener;
import us.zoom.sdk.MeetingStatus;
import us.zoom.sdk.ZoomApiError;
import us.zoom.sdk.ZoomError;
import us.zoom.sdk.ZoomSDK;

public class VideoCall extends AppCompatActivity implements InitAuthSDKCallback, MeetingServiceListener, UserLoginCallback.ZoomDemoAuthenticationListener, View.OnClickListener {
    ZoomSDK zoomSDK;
    EditText ed1,ed2;
    Button btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_call);
        ed1 = findViewById(R.id.edt1);
        ed2 = findViewById(R.id.edt2);
        btn1 = findViewById(R.id.joinmeeting);
        zoomSDK = ZoomSDK.getInstance();
        InitAuthSDKHelper.getInstance().initSDK(this,this);
        if(zoomSDK.isInitialized()){
            ZoomSDK.getInstance().getMeetingService().addListener(this);
            ZoomSDK.getInstance().getMeetingSettingsHelper().enable720p(true);
        }
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!zoomSDK.isInitialized()){
                    Toast.makeText(VideoCall.this, "Init SDK FIrst ", Toast.LENGTH_SHORT).show();
                    InitAuthSDKHelper.getInstance().initSDK(VideoCall.this, VideoCall.this);
                    return;
                }
                if(ZoomSDK.getInstance().getMeetingSettingsHelper().isCustomizedMeetingUIEnabled()){
                    ZoomSDK.getInstance().getSmsService().enableZoomAuthRealNameMeetingUIShown(false);
                }else {
                    ZoomSDK.getInstance().getSmsService().enableZoomAuthRealNameMeetingUIShown(true);
                }
                String number = ed1.getText().toString();
                String name = ed2.getText().toString();
                JoinMeetingParams params = new JoinMeetingParams();
                params.meetingNo = number;
                params.displayName = name;
                JoinMeetingOptions options = new JoinMeetingOptions();
                options.no_audio  = true;
                ZoomSDK.getInstance().getMeetingService().joinMeetingWithParams(VideoCall.this,params,options);
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onZoomSDKLoginResult(long result) {

    }

    @Override
    public void onZoomSDKLogoutResult(long result) {

    }

    @Override
    public void onZoomIdentityExpired() {

    }

    @Override
    public void onMeetingStatusChanged(MeetingStatus meetingStatus, int i, int i1) {

    }

    @Override
    public void onZoomSDKInitializeResult(int i, int i1) {
        if(i!= ZoomError.ZOOM_ERROR_SUCCESS){
            Toast.makeText(this, "Failed to initialize zoom:"+i+"internal"+i1, Toast.LENGTH_SHORT).show();
        }else {
            ZoomSDK.getInstance().getMeetingSettingsHelper().enable720p(true);
            ZoomSDK.getInstance().getMeetingSettingsHelper().enableShowMyMeetingElapseTime(true);
            ZoomSDK.getInstance().getMeetingSettingsHelper().setVideoOnWhenMyShare(true);
            ZoomSDK.getInstance().getMeetingService().addListener(this);
            Toast.makeText(this, "Initaialized successfully", Toast.LENGTH_SHORT).show();
            if(zoomSDK.tryAutoLoginZoom() == ZoomApiError.ZOOM_API_ERROR_SUCCESS){
                UserLoginCallback.getInstance().addListener(this);
            }else {

            }
        }
    }

    @Override
    public void onZoomAuthIdentityExpired() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UserLoginCallback.getInstance().removeListener(this);
        if(null != ZoomSDK.getInstance().getMeetingService()){
            ZoomSDK.getInstance().getMeetingService().removeListener(this);
        }
        InitAuthSDKHelper.getInstance().reset();
    }
}