package com.justzht.unity.lwp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class LiveWallpaperInitProvider extends ContentProvider {
    private UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    public static final String AUTHORITIES = "com.straw.course";
    public static final String COURSE_PATH = "init";
    private static final int PROVIDE_COURSE = 1;
    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITIES);
    public static final Uri COURSE_URI = Uri.withAppendedPath(BASE_URI, COURSE_PATH);


    public boolean onCreate() {
        if (getContext() == null) {
            LiveWallpaperUtils.logE("InitProvider.onCreate -> getContext == null");
            return false;
        }
        mUriMatcher.addURI(AUTHORITIES,
                COURSE_PATH, PROVIDE_COURSE);

        LiveWallpaperUtils.logD(String.format("Create With Context %s, defaultActivity class %s, defaultService class %s", getContext(), getDefaultActivity(), getDefaultService()));
        LiveWallpaperManager.getInstance().defaultActivity = getDefaultActivity();
        LiveWallpaperManager.getInstance().defaultService = getDefaultService();
        LiveWallpaperManager.getInstance().Init(getContext());
        return true;
    }

    /* access modifiers changed from: protected */
    public Class getDefaultActivity() {
        return LiveWallpaperPresentationActivity.class;
    }

    /* access modifiers changed from: protected */
    public Class getDefaultService() {
        return LiveWallpaperPresentationService.class;
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
