package com.vsm.splashadskitdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.AudioFocusType;
import com.huawei.hms.ads.HwAds;
import com.huawei.hms.ads.splash.SplashView;

public class MainActivity extends AppCompatActivity {

    private static final String AD_ID = "testq6zq98hecj";
    private boolean hasPaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize the HUAWEI Ads SDK.
        HwAds.init(this);
        // Load and display a splash ad.
        loadAd();
    }



    /**
     * When the ad display is complete, the app home screen is displayed.
     */
    private void jump() {
        if (!hasPaused) {
            hasPaused = true;
            startActivity(new Intent(MainActivity.this, MainApplicationActivity.class));
            finish();
        }
    }
    /**
     * Set this parameter to true when exiting the app to ensure that the app home screen is not displayed.
     */
    @Override
    protected void onStop() {
        // Remove the timeout message from the message queue.
        //timeoutHandler.removeMessages(MSG_AD_TIMEOUT);
        hasPaused = true;
        super.onStop();
    }
    /**
     * Call this method when returning to the splash ad screen from another screen to access the app home screen.
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        hasPaused = false;
        jump();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void loadAd() {
        int orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        AdParam adParam = new AdParam.Builder().build();
        SplashView.SplashAdLoadListener splashAdLoadListener = new SplashView.SplashAdLoadListener() {
            @Override
            public void onAdLoaded() {
                // Called when an ad is loaded successfully.
            }
            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Called when an ad fails to be loaded. The app home screen is then displayed.
                jump();
            }
            @Override
            public void onAdDismissed() {
                // Called when the display of an ad is complete. The app home screen is then displayed.
                jump();
            }
        };
        // Obtain SplashView.
        SplashView splashView = findViewById(R.id.splash_ad_view);
        // Set the default slogan.
        splashView.setSloganResId(R.mipmap.ic_launcher);
        // Set the audio focus type for a video splash ad.
        splashView.setAudioFocusType(AudioFocusType.NOT_GAIN_AUDIO_FOCUS_WHEN_MUTE);
        // Load the ad. AD_ID indicates the ad slot ID.
        splashView.load(AD_ID, orientation, adParam, splashAdLoadListener);
    }
}