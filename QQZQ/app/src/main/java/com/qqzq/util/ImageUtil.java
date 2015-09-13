package com.qqzq.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by jie.xiao on 15/9/13.
 */
public class ImageUtil {
    /**
     * 获取SDCard的目录路径功能
     *
     * @return
     */
    private static String getSDCardPath() {
        File sdcardDir = null;
        // 判断SDCard是否存在
        boolean sdcardExist = Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
        if (sdcardExist) {
            sdcardDir = Environment.getExternalStorageDirectory();
        }
        return sdcardDir.toString();
    }

    /**
     * 将bitmap图片保存到sd卡上，返回路径
     *
     * @throws IOException
     */
    public static String saveBitmapToSD(Activity act, String destPath, Bitmap bm) {
        // 图片存储路径
        String SavePath = getSDCardPath() + destPath;
        // 保存Bitmap
        File path = new File(SavePath);
        // 文件
        String filepath = SavePath + "/logo.png";
        System.out.println("图片路径:" + filepath);
        try {
            File file = new File(filepath);
            if (!path.exists()) {
                path.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = null;
            fos = new FileOutputStream(file);
            if (null != fos) {
                bm.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.flush();
                fos.close();
                Toast.makeText(act, "截屏文件已保存至SDCard/ScreenImages/目录下",
                        Toast.LENGTH_LONG).show();
                return filepath;
            }
        } catch (IOException e) {
            Log.e(ImageUtil.class.getSimpleName(), "", e);
        }
        return null;
    }
}
