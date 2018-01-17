package ml.amaze.design;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import ml.amaze.design.utils.ImprovedTimer;

/**
 * 欢迎页面
 *
 * @author hxj
 * @date 2017/12/23 0023
 */

public class WelcomeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //页面设置为无标题
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.welcome);


        ImageView imageView = (ImageView) findViewById(R.id.main_iv);
       //File file = new File(getCacheDir().getAbsolutePath() + "/" + "welcome.jpg");
        //Glide.with(getApplicationContext()).load(file).error(R.drawable.welcome).skipMemoryCache( true ).diskCacheStrategy(DiskCacheStrategy.NONE ).centerCrop().into(imageView);

        //加载本地图片
        Glide.with(getApplicationContext()).load(R.drawable.welcome).centerCrop().into(imageView);
        Log.d("WelcomeActivity", "图片加载完成");

        Log.d("WelcomeActivity", "欢迎页面开启");
        //设置一个计时器，在此页面上停留3秒然后跳转到主页面,
        new ImprovedTimer().schedule(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this,
                        MainActivity.class);
                startActivity(intent);
                Log.d("WelcomeActivity", "跳转mainactivity");
                finish();
            }
        }, 2000);



    }



}
