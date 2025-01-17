package com.yiwo.friendscometogether.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * Android N 适配工具类
 */
public class NougatUntil {

    public static String providerName = "com.yiwo.friendscometogether.fileprovider";


    /**
     * 7.0文件分享
     *
     * @param context    上下文
     * @param file       文件
     */
    public static Intent formatFileProviderShareIntent(Context context, File file) {
        Intent intent = new Intent();
        Uri uri = null;
        if(Build.VERSION.SDK_INT > 23) {
            uri = FileProvider.getUriForFile(context, providerName, file);
        } else {
            uri = Uri.fromFile(file);
        }
        // 表示文件类型
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("*/*");
        return intent;
    }

    /**
     * 将普通uri转化成适应7.0的content://形式  针对图片格式
     *
     * @param intent  intent
     * @return
     */
    public static Intent formatFileProviderPicIntent(Intent intent) {
        if(Build.VERSION.SDK_INT > 23) {
//            List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
//            for (ResolveInfo resolveInfo : resInfoList) {
//                String packageName = resolveInfo.activityInfo.packageName;
//                context.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            }
            // 表示图片类型
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        return intent;
    }

    /**
     * 将普通uri转化成适应7.0的content://形式
     *
     * @return
     */
    public static Uri formatFileProviderUri(Context context, File file) {
        Uri uri = null;
        if(Build.VERSION.SDK_INT > 23) {
            uri = FileProvider.getUriForFile(context, providerName, file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }
}

