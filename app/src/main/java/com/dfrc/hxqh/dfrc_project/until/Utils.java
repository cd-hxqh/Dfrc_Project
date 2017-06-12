package com.dfrc.hxqh.dfrc_project.until;

import android.content.Context;
import android.os.Environment;

import com.dfrc.hxqh.dfrc_project.constants.Constants;

import java.io.File;

/**
 * Created by apple on 15/9/24.
 * 系统工具类
 */
public class Utils {

    private static final String TAG = "Utils";

    // 首先判断sdcard是否插入
    public static boolean isSdCard() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * @param @param  context
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     * @Title: getFilePath
     * @Description: TODO获取文件路径
     */
    public static String getFilePath(Context context) {
        boolean isSdCard = isSdCard();
        String path = null;
        if (isSdCard) {
            path = Constants.PATH_DB + context.getPackageName() + File.separator;
        } else {
            path = Constants.NOT_SDCARD_PATH_DB + context.getPackageName() + File.separator;
        }
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String DB_NAME = path + Constants.TB_NAME;

        return DB_NAME;
    }


    /**
     * 获取文件夹的大小*
     */
    public static double getDirSize(File file) {

        //判断文件是否存在
        if (file.exists()) {
            //如果是目录则递归计算其内容的总大小
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                double size = 0;
                for (File f : children)
                    size += getDirSize(f);
                return size;
            } else {//如果是文件则直接返回其大小,以“兆”为单位
                double size = (double) file.length() / 1024 / 1024;
                return size;
            }
        } else {
            return 0.0;
        }
    }

    /**
     * 删除单个文件
     *
     * @param fileName 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
