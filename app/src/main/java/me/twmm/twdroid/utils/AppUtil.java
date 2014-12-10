package me.twmm.twdroid.utils;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by TangWei on 12/10/14.
 */
public class AppUtil {

    /**
     * Get application's version code
     *
     * @param ctx Context
     * @return Version code,default to 1.
     */
    public int getAppVersion(Context ctx){

        try {
            return  ctx.getPackageManager().getPackageInfo(ctx.getPackageName(),0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return 1;
    }
}
