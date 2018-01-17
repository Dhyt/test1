package ml.amaze.design.pedometer.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ml.amaze.design.R;
import ml.amaze.design.bean.DaoSession;
import ml.amaze.design.bean.StepsBean;
import ml.amaze.design.bean.StepsBeanDao;
import ml.amaze.design.database.GreenDaoUtils;
import ml.amaze.design.utils.Utils;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 *
 * @author hxj
 * @date 2017/12/4 0004
 * 开启计步服务，创建悬浮窗，记录步数并按日期插入数据库，
 */

public class StepService extends Service {



    public static Boolean flag = false;
    private SensorManager sensorManager;
    private StepDetector stepDetector;
    //今天日期

    private static String thisDay = Utils.getFormatDate();
//用于获取list

    private StepsBeanDao stepsBeanDao;
    //获取list

    List<StepsBean> list;
    //list的大小，也是插入下有一条数据的角标

    long index;




    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("StepService", "StepService已开启");

        //设置计步器开始步数
        new Thread(new Runnable() {
            @Override
            public void run() {
                startStepDetector();
            }
        }).start();



        DaoSession daoSession = GreenDaoUtils.getDaoSessionInstance(getApplicationContext());
        stepsBeanDao = daoSession.getStepsBeanDao();


        list = stepsBeanDao.queryBuilder().build().list();



        //重新创建StepsService时
        int steps;
        if (list.size() == 0) {
            //如果没有数据库，则初始化步数为0，插入新数据
            steps = 0;
            index=0;
        } else {
            if(list.get(list.size()-1).getDate().equals(Utils.getFormatDate())){
                //若存在数据库，且最后一天数据日期为今天，则更新
                index = list.size()-1;
                steps = list.get((int) index).getSteps();
            }else {
                //若存在数据库，且最后一天数据日期不为今天，则插入新数据
                index=list.size();
                steps=0;
            }
        }

        Log.v("StepService读取步数为：", steps + "");
        StepDetector.CURRENT_SETP = steps;



        try {
            if (!isAdded) {
                //如果悬浮窗不存在，则创建悬浮窗
                createFloatView();
            }
            //执行悬浮窗更新子线程
            updateUI();
        } catch (RuntimeException e) {
            Toast.makeText(this, "请在 设置->应用->此app下开启悬浮窗", Toast.LENGTH_LONG).show();
            Log.d("RuntimeException", "请在 设置->应用->此app下开启悬浮窗");
        }
        //执行判断日期和保存步数子线程
        updateDate();
        saveSteps();

    }

    private void startStepDetector() {
        flag = true;
        stepDetector = new StepDetector(this);
        //获取传感器管理器的实例
        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        //获得传感器的类型，这里获得的类型是加速度传感器
        //此方法用来注册，只有注册过才会生效，参数：SensorEventListener的实例，Sensor的实例，更新速率
        Sensor sensor = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(stepDetector, sensor,
                SensorManager.SENSOR_DELAY_FASTEST);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        flag = false;
        if (stepDetector != null) {
            sensorManager.unregisterListener(stepDetector);
        }

        GreenDaoUtils.close();
    }






    /**
     * 创建悬浮窗
     */
   // 是否已增加悬浮窗

    private static boolean isAdded = false;
    private static WindowManager wm;
    private static WindowManager.LayoutParams params;
    private TextView tvFloatview;

    @SuppressLint("ClickableViewAccessibility")
    private void createFloatView() {
        tvFloatview = new TextView(getApplicationContext());
        tvFloatview.setTextSize(10);
        tvFloatview.setTextColor(getResources().getColor(R.color.color_f3452a));
        //tvFloatview.setBackgroundColor(getResources().getColor(R.color.color_b2000000));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            tvFloatview.setBackground(getResources().getDrawable(R.drawable.textview_border));
        }
        tvFloatview.setPadding(5, 5, 5, 5);

        //tvFloatview.setText("悬浮窗");

        wm = (WindowManager) getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        params = new WindowManager.LayoutParams();

        // 设置图片格式，效果为背景透明
        params.format = PixelFormat.RGBA_8888;
        // 设置window type
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;


       /*
        * 如果设置为params.type = WindowManager.LayoutParams.TYPE_PHONE;
        * 那么优先级会降低一些, 即拉下通知栏不可见
        */

        // 设置Window flag
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
       /*
        * 下面的flags属性的效果形同“锁定”。
        * 悬浮窗不可触摸，不接受任何事件,同时不影响后面的事件响应。
       wmParams.flags=LayoutParams.FLAG_NOT_TOUCH_MODAL
                              | LayoutParams.FLAG_NOT_FOCUSABLE
                              | LayoutParams.FLAG_NOT_TOUCHABLE;
        */

        // 设置悬浮窗的长得宽
        params.width = WRAP_CONTENT;
        params.height = WRAP_CONTENT;

        // 设置悬浮窗的Touch监听
        tvFloatview.setOnTouchListener(new View.OnTouchListener() {
            int lastX, lastY;
            int paramX, paramY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        paramX = params.x;
                        paramY = params.y;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int dx = (int) event.getRawX() - lastX;
                        int dy = (int) event.getRawY() - lastY;
                        params.x = paramX + dx;
                        params.y = paramY + dy;
                        // 更新悬浮窗位置
                        wm.updateViewLayout(tvFloatview, params);
                        break;
                        default:
                }
                return true;
            }
        });
        wm.addView(tvFloatview, params);

        isAdded = true;
    }

    /**
     * 通过handler更新悬浮窗UI
     */
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            //更新UI
            Log.v("StepService:", "step=" + StepDetector.CURRENT_SETP);
            tvFloatview.setText(StepDetector.CURRENT_SETP + "");

            super.handleMessage(msg);
        }
    };


    /*
    三个子线程

     */
    /**
     * 每隔1.5秒更新悬浮窗UI
     */
    private void updateUI() {
        Log.d("StepService:updateUI", "开始执行updateUI子线程");
        new Thread() {
            @Override
            public void run() {
                Message s;
                while (isAdded) {
                    try {
                        Thread.sleep(1500);
                        s = new Message();
                        s.arg1 = 0;
                        handler.sendMessage(s);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                super.run();
            }
        }.start();
    }

    /**
     * 每隔十秒更新数据库步数
     */
    private void saveSteps() {
        Log.d("StepService:saveSteps", "开始执行saveSteps子线程");

        new Thread() {
            @Override
            public void run() {
                StepsBean stepsBean;

                while (true) {
                    try {
                        //十秒
                        Thread.sleep(10000);
                        stepsBean = new StepsBean(index, StepDetector.CURRENT_SETP);
                        stepsBeanDao.insertOrReplace(stepsBean);

                        //stepsBeanDao.updateInTx(stepsBean);
                        Log.v("StepService更新数据库", stepsBean.toString());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    super.run();
                }
            }
        }.start();
    }

    /**
     * 每隔半小时检查一次日期
     */
    private void updateDate() {
        Log.d("StepService:updateDate", "开始执行updateDate子线程");
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        //半小时
                        Thread.sleep(1800000);
                        if (!thisDay.equals(Utils.getFormatDate())) {
                            //说明过了一天
                            thisDay = Utils.getFormatDate();

                            Log.d("StepService:", "过了一天，今天的日期为：" + thisDay);
                            index++;
                            StepDetector.CURRENT_SETP = 0;
                        }
                        super.run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }



















    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
