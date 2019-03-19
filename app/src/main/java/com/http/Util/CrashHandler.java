package com.http.Util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CrashHandler implements Thread.UncaughtExceptionHandler{

        public static final String TAG = "CrashHandler";

        private Thread.UncaughtExceptionHandler mDefaultHandler;
        private static CrashHandler INSTANCE = new CrashHandler();
        private Context mContext;
        //文件路径
        private static final String PATH = Environment.getExternalStorageDirectory().getPath() +File.separator+ "crash";
        private static final String FILE_NAME = "crash";
        private static final String FILE_NAME_SUFEIX = ".txt";
        private CrashHandler() {
        }

        public static CrashHandler getInstance() {
            return INSTANCE;
        }

        public void init(Context context) {
            mContext = context;
            mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
            if (mDefaultHandler != null) {
                Thread.setDefaultUncaughtExceptionHandler(this);
            }
        }

        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            if (mDefaultHandler != null) {
                handleException(ex);
                mDefaultHandler.uncaughtException(thread, ex);
            }
        }

        private boolean handleException(Throwable ex) {
            if (ex == null) {
                return false;
            }
            try {
                //将文件写入sd卡
                writeToSDcard(ex);
                //写入后在这里可以进行上传操作
            } catch (IOException e) {
                e.printStackTrace();
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return true;
        }


        private void writeToSDcard(Throwable ex) throws IOException, PackageManager.NameNotFoundException {
            //如果没有SD卡，直接返回
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                return;
            }
            File filedir = new File(PATH);
            if (!filedir.exists()) {
                filedir.mkdirs();
            }
            long currenttime = System.currentTimeMillis();
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(currenttime));

            File exfile = new File(PATH +File.separator+FILE_NAME+time + FILE_NAME_SUFEIX);
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(exfile)));
            Log.e("错误日志文件路径",""+exfile.getAbsolutePath());
            pw.println(time);
            PackageManager pm = mContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            //当前版本号
            pw.println("App Version:" + pi.versionName + "_" + pi.versionCode);
            //当前系统
            pw.println("OS version:" + Build.VERSION.RELEASE + "_" + Build.VERSION.SDK_INT);
            //制造商
            pw.println("Vendor:" + Build.MANUFACTURER);
            //手机型号
            pw.println("Model:" + Build.MODEL);
            //CPU架构
            pw.println("CPU ABI:" + Build.CPU_ABI);

            ex.printStackTrace(pw);
            pw.close();
        }


}
