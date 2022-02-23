package com.justzht.unity.lwp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.unity3d.player.IUnityPlayerLifecycleEvents;
import com.unity3d.player.UnityPlayer;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

public enum LiveWallpaperManager implements IUnityPlayerLifecycleEvents {
    INSTANCE;
    
    private WeakReference<Context> applicationContextWeakReference = new WeakReference<>(null);
    public Class defaultActivity = LiveWallpaperPresentationActivity.class;
    public Class defaultService = LiveWallpaperPresentationService.class;
    private volatile HashMap<Integer, WeakReference<SurfaceHolder>> holdersOfUnity = new HashMap<>();
    public volatile boolean unityInstancePaused = true;
    public UnityPlayer unityPlayer;

    private LiveWallpaperManager() {
    }

    public static LiveWallpaperManager getInstance() {
        return INSTANCE;
    }

    public Context getContext() {
        return this.applicationContextWeakReference.get();
    }

    public boolean hasContext() {
        return getContext() != null;
    }

    public void Init(Context applicationContext) {
        LiveWallpaperUtils.logD("Init -> " + applicationContext);
        this.applicationContextWeakReference = new WeakReference<>(applicationContext);
        LoadUnity(applicationContext);
    }

    public void DeInit(Context applicationContext) {
        UnloadUnity();
        this.unityPlayer.quit();
    }

    public void LoadUnity(Context context) {
        LiveWallpaperUtils.logD("LiveWallpaperManager.LoadUnity");
        LiveWallpaperUtils.logD("context.getPackageName() -> " + context.getPackageName());
        LiveWallpaperUtils.logD("getResources.getIdentifier() -> " + context.getResources().getIdentifier("game_view_content_description", "string", context.getPackageName()));
        this.unityPlayer = new UnityPlayer(context, this);
        this.unityPlayer.requestFocus();
    }

    public void UnloadUnity() {
        LiveWallpaperUtils.logD("LiveWallpaperManager.UnloadUnity");
        this.unityPlayer.unload();
    }

    public synchronized void connectUnityDisplay(SurfaceHolder holder) {
        LiveWallpaperUtils.logD("connectUnityDisplay -> " + holder);
        cleanUpHolders(holder);
        int nextIndex = this.holdersOfUnity.size();
        WeakReference<SurfaceHolder> reference = new WeakReference<>(holder);
        LiveWallpaperUtils.logD(String.format("holdersOfUnity.put(%s, %s)", Integer.valueOf(nextIndex), holder));
        this.holdersOfUnity.put(Integer.valueOf(nextIndex), reference);
        updateLifeStageUsingBackingCount();
    }

    public synchronized void disconnectUnityDisplay(SurfaceHolder holder) {
        LiveWallpaperUtils.logD("disconnectUnityDisplay -> " + holder);
        cleanUpHolders(holder);
        updateLifeStageUsingBackingCount();
    }

    private synchronized void cleanUpHolders() {
        cleanUpHolders(null);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v7, resolved type: java.util.HashMap<java.lang.Integer, java.lang.ref.WeakReference<android.view.SurfaceHolder>> */
    /* JADX WARN: Multi-variable type inference failed */
    /*  JADX ERROR: MOVE_RESULT instruction can be used only in fallback mode
        jadx.core.utils.exceptions.CodegenException: MOVE_RESULT instruction can be used only in fallback mode
        	at jadx.core.codegen.InsnGen.fallbackOnlyInsn(InsnGen.java:604)
        	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:542)
        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:230)
        	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:119)
        	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:103)
        	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:806)
        	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:746)
        	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:367)
        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:230)
        	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:119)
        	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:103)
        	at jadx.core.codegen.InsnGen.addArgDot(InsnGen.java:87)
        	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:715)
        	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:367)
        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:230)
        	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:119)
        	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:103)
        	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:290)
        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:249)
        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:217)
        	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:110)
        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:56)
        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:93)
        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:59)
        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:93)
        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:59)
        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:93)
        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:59)
        	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:244)
        	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:237)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:342)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:295)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:264)
        	at java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
        	at java.util.ArrayList.forEach(ArrayList.java:1259)
        	at java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:390)
        	at java.util.stream.Sink$ChainedReference.end(Sink.java:258)
        */
    private synchronized void cleanUpHolders(android.view.SurfaceHolder r6) {
        /*
            r5 = this;
            monitor-enter(r5)
            java.util.HashMap<java.lang.Integer, java.lang.ref.WeakReference<android.view.SurfaceHolder>> r2 = r5.holdersOfUnity     // Catch:{ all -> 0x003b }
            java.util.Collection r2 = r2.values()     // Catch:{ all -> 0x003b }
            java.util.stream.Stream r2 = r2.stream()     // Catch:{ all -> 0x003b }
            r3 = move-result     // Catch:{ all -> 0x003b }
            java.util.stream.Stream r2 = r2.filter(r3)     // Catch:{ all -> 0x003b }
            java.util.stream.Collector r3 = java.util.stream.Collectors.toList()     // Catch:{ all -> 0x003b }
            java.lang.Object r1 = r2.collect(r3)     // Catch:{ all -> 0x003b }
            java.util.List r1 = (java.util.List) r1     // Catch:{ all -> 0x003b }
            java.util.HashMap<java.lang.Integer, java.lang.ref.WeakReference<android.view.SurfaceHolder>> r2 = r5.holdersOfUnity     // Catch:{ all -> 0x003b }
            r2.clear()     // Catch:{ all -> 0x003b }
            r0 = 0
        L_0x0023:
            int r2 = r1.size()     // Catch:{ all -> 0x003b }
            if (r0 >= r2) goto L_0x0039
            java.util.HashMap<java.lang.Integer, java.lang.ref.WeakReference<android.view.SurfaceHolder>> r2 = r5.holdersOfUnity     // Catch:{ all -> 0x003b }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r0)     // Catch:{ all -> 0x003b }
            java.lang.Object r4 = r1.get(r0)     // Catch:{ all -> 0x003b }
            r2.put(r3, r4)     // Catch:{ all -> 0x003b }
            int r0 = r0 + 1
            goto L_0x0023
        L_0x0039:
            monitor-exit(r5)
            return
        L_0x003b:
            r2 = move-exception
            monitor-exit(r5)
            throw r2
        */
//        throw new UnsupportedOperationException("Method not decompiled: com.justzht.unity.lwp.LiveWallpaperManager.cleanUpHolders(android.view.SurfaceHolder):void");
    }

    private static /* synthetic */ boolean lambda$cleanUpHolders$0(SurfaceHolder holderToRemove, WeakReference ref) {
        return (ref == null || ref.get() == holderToRemove) ? false : true;
    }

    private synchronized void updateLifeStageUsingBackingCount() {
        WeakReference<SurfaceHolder> surfaceHolderReference;
        Surface surface = null;
        boolean hasHolder = true;
        synchronized (this) {
            cleanUpHolders();
            int surfaceHolderCount = this.holdersOfUnity.size();
            if (surfaceHolderCount <= 0) {
                hasHolder = false;
            }
            Integer newestSurfaceHolderKey = Integer.valueOf(Math.max(surfaceHolderCount - 1, 0));
            if (this.holdersOfUnity.containsKey(newestSurfaceHolderKey)) {
                surfaceHolderReference = this.holdersOfUnity.get(newestSurfaceHolderKey);
            } else {
                surfaceHolderReference = null;
            }
            SurfaceHolder surfaceHolder = surfaceHolderReference == null ? null : surfaceHolderReference.get();
            if (surfaceHolder != null) {
                surface = surfaceHolder.getSurface();
            }
            if (hasHolder) {
                LiveWallpaperUtils.logD(String.format("calling unityPlayer.displayChanged(%s, %s)", 0, surface));
                this.unityPlayer.displayChanged(0, surface);
            }
            LiveWallpaperUtils.logD("unityLifeCycleRunnable run");
            if (hasHolder && this.unityInstancePaused) {
                this.unityInstancePaused = false;
                LiveWallpaperUtils.logD("calling unityPlayer.resume");
                this.unityPlayer.resume();
                this.unityPlayer.windowFocusChanged(true);
            } else if (!hasHolder && !this.unityInstancePaused) {
                this.unityInstancePaused = true;
                LiveWallpaperUtils.logD("calling unityPlayer.pause");
                this.unityPlayer.pause();
            }
        }
    }

    public void onUnityPlayerUnloaded() {
        LiveWallpaperUtils.logD("onUnityPlayerUnloaded");
    }

    public void onUnityPlayerQuitted() {
        LiveWallpaperUtils.logD("onUnityPlayerQuitted");
    }

    public void openLiveWallpaperPreview(String serviceClassName) {
        try {
            openLiveWallpaperPreview(Class.forName(serviceClassName));
        } catch (ClassNotFoundException e) {
            LiveWallpaperUtils.logE(e.toString());
            e.printStackTrace();
        }
    }

    public void openLiveWallpaperPreview(Class serviceClass) {
        Context context = getContext();
        try {
            Intent intent = new Intent("android.service.wallpaper.CHANGE_LIVE_WALLPAPER");
            intent.addFlags(1342177280);
            intent.putExtra("android.service.wallpaper.extra.LIVE_WALLPAPER_COMPONENT", new ComponentName(context, serviceClass));
            context.startActivity(intent);
        } catch (Exception e) {
            LiveWallpaperUtils.logE(e.toString());
            try {
                new Intent("android.intent.action.SET_WALLPAPER").addFlags(1342177280);
            } catch (Exception e2) {
                LiveWallpaperUtils.logE(e2.toString());
            }
        }
    }

    public void openLiveWallpaperPreview() {
        openLiveWallpaperPreview(this.defaultService);
    }

    public void launchUnityActivity() {
        launchUnityActivity(this.defaultActivity);
    }

    public void launchUnityActivity(Class activityClass) {
        Intent intent = new Intent(getContext(), activityClass);
//        intent.addFlags(268435456);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        getContext().startActivity(intent);
    }

    public void launchUnityActivity(String activityName) {
        try {
            launchUnityActivity(Class.forName(activityName));
        } catch (ClassNotFoundException e) {
            LiveWallpaperUtils.logE(e.toString());
            e.printStackTrace();
        }
    }

    public void launchUnityService() {
        launchUnityService(this.defaultService);
    }

    public void launchUnityService(Class serviceClass) {
        getContext().startService(new Intent(getContext(), serviceClass));
    }
}
