package ml.amaze.design.userinfo;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import ml.amaze.design.R;
import ml.amaze.design.register.RegisterFirstPageFragment;

/**
 *
 * @author hxj
 * @date 2017/12/13 0013
 */

public class UserInfoFragment extends Fragment {
    User user;
    View view;
    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        Log.d("UserInfoFragment", "开启UserInforagment");
        //读取bundle中的数据
        Bundle bundle = this.getArguments();
        user = (User) bundle.get("user");
        //初始化view，通过打气筒转换为view
        view = inflater.inflate(R.layout.userinfopart_userinfo, null);

        initView();
        return view;
    }

    @SuppressLint("SetTextI18n")
    private void initView(){
        ImageView picture = (ImageView) view.findViewById(R.id.userInfo_iv_picture);
        TextView tvName = (TextView) view.findViewById(R.id.userInfo_tv_name);
        TextView tvAge = (TextView) view.findViewById(R.id.userInfo_tv_age);
        TextView tvHeight = (TextView) view.findViewById(R.id.userInfo_tv_height);
        TextView tvWeight = (TextView) view.findViewById(R.id.userInfo_tv_weight);
        TextView tvWaistline = (TextView) view.findViewById(R.id.userInfo_tv_waistline);
        TextView tvWorklevel = (TextView) view.findViewById(R.id.userInfo_tv_worklevel);
        TextView tvSleeptime = (TextView) view.findViewById(R.id.userInfo_tv_sleepTime);
        TextView tvBm = (TextView) view.findViewById(R.id.userInfo_tv_BM);
        TextView tvBmr = (TextView) view.findViewById(R.id.userInfo_tv_BMR);
        TextView tvBfr = (TextView) view.findViewById(R.id.userInfo_tv_BFR);
        TextView tvBmi = (TextView) view.findViewById(R.id.userInfo_tv_BMI);
        TextView tvDemandenergy = (TextView) view.findViewById(R.id.userInfo_tv_demandEnergy);
        //重新注册按钮
        Button btnReregister = (Button) view.findViewById(R.id.userInfo_btn_reRegister);
        btnReregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFirstPageFragment registerFirstPageFragment = new RegisterFirstPageFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.main_main, registerFirstPageFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        tvName.        setText("姓名:"+user.getName());
        tvAge.         setText("年龄:"+user.getAge());
        tvHeight.      setText("身高:"+user.getHeight()+"cm");
        tvWeight.      setText("体重:"+user.getWeight()+"kg");
        tvWaistline.   setText("腰围:"+user.getWaistline()+"cm");
        tvWorklevel.   setText("体力活动等级:"+user.getWorkLevel());
        tvSleeptime.   setText("休息时间:"+user.getSleepTime()+"h");
        tvBm.          setText("基础代谢:BM--"+user.getBM()+"kcal");
        tvBmr.         setText("基础代谢率:BMR--"+user.getBMR()+"");
        tvBfr.         setText("体脂率:BFR--"+user.getBFR()+"--"+user.getBFRLevel()+"");
        tvBmi.         setText("体质指数:BMI--"+user.getBMI()+"--"+user.getBMILevel()+"");
        tvDemandenergy.setText("日需能量:DemandEnergy--"+user.getDemandEnergy()+"kcal");
        String man="男";
        if(man.equals(user.getGender())){
            picture.setImageResource(R.drawable.boys);
        }else {
        picture.setImageResource(R.drawable.girl);

        }
    }

    private boolean readUser() {
        //保证用户注册
        SharedPreferences config = getActivity().getSharedPreferences("config", 0);
        String json = config.getString("user", null);

        if (json == null) {
            return false;
        } else {
            user = new Gson().fromJson(json, User.class);
            return true;
        }

    }

    @Override
    public void onResume() {


        super.onResume();
    }
}
