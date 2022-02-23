package com.justzht.unity.lwp;

import android.annotation.TargetApi;
import android.os.Build;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

public class LiveWallpaperPresentationService extends WallpaperService {
    public WallpaperService.Engine onCreateEngine() {
        LiveWallpaperUtils.logD("onCreateEngine");
        return new LiveWallpaperPresentationServiceEngine();
    }

    public class LiveWallpaperPresentationServiceEngine extends WallpaperService.Engine {
        LiveWallpaperPresentationServiceEngine() {
            super();
            LiveWallpaperUtils.logD("LiveWallpaperPresentationServiceEngine");
            setTouchEventsEnabled(true);
            setOffsetNotificationsEnabled(true);
        }

        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            LiveWallpaperUtils.logD("onCreate");
            LiveWallpaperPresentationEventWrapper.getInstance().setupSurfaceHolderInServiceOnCreate(surfaceHolder);
        }

        public void onDestroy() {
            super.onDestroy();
            LiveWallpaperPresentationEventWrapper.getInstance().setupSurfaceHolderInServiceOnDestroy(getSurfaceHolder());
            LiveWallpaperUtils.logD("onDestroy");
        }
    }
}
