package ml.amaze.design.pedometer.activity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.os.Build;

/**
 *
 * @author hxj
 * @date 2017/12/3 0003
 */

public class Pedometer implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mStepCount;
    private Sensor mStepDetector;
    private TriggerEventListener mTriggerEventListener;

    //步行总数

    private float mCount;

    //步行探测器

    private float mDetector;
    private Context context;
    private static final int SENSOR_TYPE_D =Sensor.TYPE_STEP_DETECTOR;
    private static final int SENSOR_TYPE_C =Sensor.TYPE_STEP_COUNTER;

    public Pedometer() {

    }

    public Pedometer(Context context) {
        this.context = context;
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mStepCount = mSensorManager.getDefaultSensor(SENSOR_TYPE_C);
        mStepDetector = mSensorManager.getDefaultSensor(SENSOR_TYPE_D);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            mTriggerEventListener = new TriggerEventListener() {
                @Override
                public void onTrigger(TriggerEvent event) {

                }
            };
        }

    }



    public void register(){

        register(mStepCount, SensorManager.SENSOR_DELAY_FASTEST);
        register(mStepDetector, SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void unRegister(){
        mSensorManager.unregisterListener(this);
    }

    private void register(Sensor sensor,int rateUs) {
        mSensorManager.registerListener(this, sensor, rateUs);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType()== SENSOR_TYPE_C) {
            setStepCount(event.values[0]);
        }
        if (event.sensor.getType()== SENSOR_TYPE_D) {
            if (event.values[0]==1.0) {
                mDetector++;
            }
        }
    }

    public float getStepCount() {
        return mCount;
    }

    private void setStepCount(float count) {
        this.mCount = count;
    }

    public float getmDetector() {
        return mDetector;
    }

}