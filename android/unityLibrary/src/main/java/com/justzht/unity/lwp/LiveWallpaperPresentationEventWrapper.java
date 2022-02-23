package com.justzht.unity.lwp;

import android.view.SurfaceHolder;
import android.view.SurfaceView;

public enum LiveWallpaperPresentationEventWrapper {
    INSTANCE;
    
    SurfaceHolder.Callback surfaceHolderCallback = new SurfaceHolder.Callback() {
        /* class com.justzht.unity.lwp.LiveWallpaperPresentationEventWrapper.SurfaceHolder$CallbackC00001 */

        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            LiveWallpaperUtils.logD("surfaceCreated for holder " + surfaceHolder);
            LiveWallpaperManager.getInstance().connectUnityDisplay(surfaceHolder);
        }

        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            LiveWallpaperUtils.logD("surfaceChanged for holder " + surfaceHolder);
            LiveWallpaperManager.getInstance().connectUnityDisplay(surfaceHolder);
        }

        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            LiveWallpaperUtils.logD("surfaceDestroyed for holder " + surfaceHolder);
            LiveWallpaperManager.getInstance().disconnectUnityDisplay(surfaceHolder);
        }
    };

    private LiveWallpaperPresentationEventWrapper() {
    }

    public static LiveWallpaperPresentationEventWrapper getInstance() {
        return INSTANCE;
    }

    public void setupSurfaceViewInActivityOnCreate(SurfaceView surfaceView) {
        surfaceView.getHolder().addCallback(this.surfaceHolderCallback);
    }

    public void setupSurfaceViewInActivityOnDestroy(SurfaceView surfaceView) {
        surfaceView.getHolder().removeCallback(this.surfaceHolderCallback);
    }

    public void setupSurfaceHolderInServiceOnCreate(SurfaceHolder surfaceHolder) {
        surfaceHolder.addCallback(this.surfaceHolderCallback);
    }

    public void setupSurfaceHolderInServiceOnDestroy(SurfaceHolder surfaceHolder) {
        surfaceHolder.removeCallback(this.surfaceHolderCallback);
    }
}
