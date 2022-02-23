package com.justzht.unity.lwp;

import android.app.Activity;
import android.database.Cursor;
import android.icu.number.NumberFormatter;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import com.unity3d.player.UnityWallpaperActivity;

public class LiveWallpaperPresentationActivity extends Activity {
    protected SurfaceView surfaceView;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.surfaceView = new SurfaceView(this);
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.addView(surfaceView);
        frameLayout.addView(LiveWallpaperManager.getInstance().unityPlayer);
//        setContentView(this.surfaceView);
        LiveWallpaperUtils.logD("onCreate");
        setContentView(frameLayout);
        LiveWallpaperPresentationEventWrapper.getInstance().setupSurfaceViewInActivityOnCreate(this.surfaceView);
//        Cursor cursor = getContentResolver().query(LiveWallpaperInitProvider.COURSE_URI, null, null, null, null);
    }



    @Override
    protected void onStart() {
        super.onStart();
        LiveWallpaperManager.getInstance().unityPlayer.resume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        LiveWallpaperManager.getInstance().unityPlayer.pause();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        LiveWallpaperPresentationEventWrapper.getInstance().setupSurfaceViewInActivityOnDestroy(this.surfaceView);
    }
}
