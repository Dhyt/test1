package ml.amaze.design.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import ml.amaze.design.R;
import ml.amaze.design.receipe.JsonApi;

/**
 *
 * @author hxj
 * @date 2017/12/18 0018
 */

public class Utils {

    /**
     * 设置任意位小数点
     * @param num
     * @param dot
     * @return
     */
    public static double setDot(double num,int dot) {
        BigDecimal b = new BigDecimal(num);

        return b.setScale(dot, BigDecimal.ROUND_HALF_UP).doubleValue();

    }

    public static String getFormatDate() {
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }


    /**
     * 在主界面开启fragment
     * @param activity
     * @param c
     * @param bundle
     * @param <T>
     */
    public static  <T extends Fragment> void startFragmentOnMain(Activity activity, T c, Bundle bundle) {
        Log.d("startFragmentOnMain", "开始");
        FragmentTransaction transaction =activity.getFragmentManager().beginTransaction();
        if (bundle != null) {
            c.setArguments(bundle);
        }
        transaction.replace(R.id.main_main, c);
        transaction.addToBackStack(null);
        transaction.commit();
        Log.d("startFragmentOnMain", "结束");
    }

    /**
     * 获取当前周几
     * @return
     */
    public  static String getFormatWeek() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String week = sdf.format(date);
        return week;
    }

    /**
     * 缓存欢迎页面
     * 每次调用此方法，则会缓存下次打开是的图片
     */
    public  static void cacheBit(final Activity activity) {
        //子线程缓存具体图片

        new Thread() {
            @Override
            public void run() {
                String path = new JsonApi().getPic();
                Log.d("cache","path="+path);
                File file;
                try {
                    file = new File(activity.getCacheDir().getAbsolutePath() + "/" + "welcome.jpg");
                    if (file.exists()) {
                        //若文件存在，则删掉
                        Log.d("cache","文件存在，删掉");
                        if(file.delete()){
                            Log.d("cache","删除成功");

                        }
                    }
                    if(!file.exists()){
                        //若图片不存在，则开始缓存
                        Log.d("filePath", file.getAbsolutePath());
                        URL url = new URL(path);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.setConnectTimeout(5000);
                        int code = conn.getResponseCode();
                        if (code == 200) {
                            InputStream in = conn.getInputStream();
                            FileOutputStream fos = new FileOutputStream(file);
                            int len;
                            byte[] buf = new byte[1024];
                            while ((len = in.read(buf)) != -1) {
                                fos.write(buf, 0, len);
                            }
                            fos.close();
                            in.close();
                            Log.d("cache", "缓存图片");
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


                super.run();
            }
        }.start();


    }

    /**
     * 弹出toast
     */
    public static void showToast(Context context, String text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }
}
