package me.twmm.twdroid.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by TangWei on 12/10/14.
 */
public class CacheUtil {

    public static int COMMON_CACHE_SIZE = 10 * 1024 * 1024; // 10M

    /**
     * Get disk cache directory
     *
     * @param ctx
     * @param uniqueName Unique name for caching directory, use it separate different types of cached files. eg: bitmap,strings,css,files etc.
     * @return cache directory
     */
    public static File getDiskCacheDir(Context ctx, String uniqueName) {

        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            cachePath = ctx.getExternalCacheDir().getPath();
        } else {
            cachePath = ctx.getCacheDir().getPath();
        }

        return new File(cachePath, uniqueName);
    }
}
