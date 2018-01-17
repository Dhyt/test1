package ml.amaze.design.pedometer.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ml.amaze.design.pedometer.service.StepDetector;
import ml.amaze.design.R;

/**
 *
 * @author hxj
 * @date 2017/12/18 0018
 */

public class PedometerFragment extends Fragment {
    TextView textView;
        View view;
    boolean flag=true;
    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {

            //服务开启后StepDetector.CURRENT_SETP即为步数
            int steps=StepDetector.CURRENT_SETP;
            if(steps<3000){
                textView.setText("您今天才走了"+steps+"步，记得锻炼身体哦!");
            }else if(steps>2999&&steps<6000){
                textView.setText("您今天已经走了"+steps+"步，多多运动才能保持健康!");
            }else if(steps>6000){
                textView.setText("您今天已经走了"+steps+"步，很棒哦!");
            }

            Log.d("handler","接受一条数据");
            super.handleMessage(msg);
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.pedometerpart_pedometer,null);

        textView = (TextView) view.findViewById(R.id.pedometer_tv_steps);
        updateUI();
        return view;
    }
    private void updateUI(){
        new Thread() {
            @Override
            public void run() {
                //设置敏感度  1-10 1最敏感
                StepDetector.SENSITIVITY=8;

                while (flag){
                    try {
                        Thread.sleep(1000);
                        //在主线程更新ui
                        Message message = new Message();
                        handler.sendMessage(message);
                        Log.d("handler","传送一条数据");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    public void onResume() {
        System.out.println("onResume");
//        if(getFragmentManager().getBackStackEntryCount()>0){
//
//            getFragmentManager().popBackStack();
//        }
        flag=true;

        super.onResume();
    }

    @Override
    public void onStop() {
        flag=false;
        System.out.println("onStop");
        super.onStop();
    }
}
