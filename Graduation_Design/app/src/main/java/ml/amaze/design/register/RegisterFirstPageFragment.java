package ml.amaze.design.register;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.util.ConvertUtils;
import cn.qqtheme.framework.widget.WheelView;
import ml.amaze.design.R;
import ml.amaze.design.selfview.TapeView;
import ml.amaze.design.userinfo.User;
import ml.amaze.design.utils.Utils;

/**
 *
 * @author hxj
 * @date 2017/12/10 0010
 */

public class RegisterFirstPageFragment extends Fragment {
    View view;
    Button btnAge;
    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        user=new User();
        view = inflater.inflate(R.layout.registerpart_fragment_firstpage, null);

//        initViews();
//        initDatas();

        Log.d("Regist1stPageFragment", "开启firstPage");

        btnAge = (Button) view.findViewById(R.id.register_firstpage_btn_age);

        btnAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionPicker(15, 50);
            }
        });

        TextView example = (TextView) view.findViewById(R.id.register_firstpage_tv_example);
        //点击显示活动等级提示
        example.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str1="1级：办公室工作、修理电器钟表、售货员、讲课等\n";
                String str2="2级：学生日常活动、机动车驾驶、电工安装、金工切割等\n";
                        String str3="3级：非机械化农业劳动、炼钢、舞蹈、体育、运动等";
                new AlertDialog.Builder(getActivity())
                        .setTitle("体力活动等级介绍")
                        .setMessage(str1+str2+str3)
                        .setPositiveButton("确定", null)
                        .show();
            }
        });

        //点击开启下一页设置信息
        View nextPage = view.findViewById(R.id.register_firstpage_btn_nextpage);
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //收集信息
                if(collectInfo()){
                    RegisterSecondPageFragment registerSecondPageFragment =new RegisterSecondPageFragment();

                    /*往bundle中添加数据*/
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", user);
                    registerSecondPageFragment.setArguments(bundle);

                    Log.d("openSecond", "开启secondPage");
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.main_main, registerSecondPageFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

        return view;
    }

    User user;


    private boolean  collectInfo(){
        //姓名
        EditText etName=(EditText) view.findViewById(R.id.register_firstpage_et_name);
        //性别
        RadioGroup rgGender = (RadioGroup) view.findViewById(R.id.register_firstpage_rg_gender);
        RadioButton rbGender = (RadioButton) view.findViewById(rgGender.getCheckedRadioButtonId());
        //活动等级
        RadioGroup rgWorklevel = (RadioGroup) view.findViewById(R.id.register_firstpage_rg_work_level);
        RadioButton rbWorklevel = (RadioButton) view.findViewById(rgWorklevel.getCheckedRadioButtonId());
        //休息时长
        TapeView tapeviewSleepTime=(TapeView) view.findViewById(R.id.register_firstpage_tapeview);

        try {
            String name = etName.getText().toString();
            String gender = rbGender.getText().toString();
            String workLevel = rbWorklevel.getText().toString();
            float sleepTime=tapeviewSleepTime.getValue();
            if(name.trim().isEmpty()||user.getAge()==0){
                Utils.showToast(getActivity(),"请输入完整信息");
                return false;
            }
            user.setName(name);
            user.setGender(gender);
            user.setWorkLevel(Integer.parseInt(workLevel));
            user.setSleepTime(sleepTime);
            System.out.println(user);
            Utils.showToast(getActivity(),user.toString());
            return true;
        }catch (NullPointerException e){
            e.printStackTrace();
            Utils.showToast(getActivity(),"请输入完整信息");
            return false;
        }
    }

    /**
     *  选择年龄
     * @param start
     * @param end
     */
    public void onOptionPicker(int start, int end) {
        OptionPicker picker;
        int length = end - start + 1;
        String[] items = new String[length];

        for (int i = 0; i < length; i++) {
            items[i] = (start + i)+"";
        }

        picker = new OptionPicker(getActivity(), items);
        picker.setCanceledOnTouchOutside(false);
        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setShadowColor(Color.BLUE, 40);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(true);
//        picker.setHeight(800);
//        picker.setWidth(600);
        picker.setTextSize(15);

        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {

                Utils.showToast(getActivity(),"index=" + index + ", item=" + item);

                btnAge.setText(item);
                user.setAge(Integer.parseInt(item));
            }
        });
        picker.show();
    }


    public void onYearMonthDayPicker(View view) {
        final DatePicker picker = new DatePicker(getActivity());
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTopPadding(ConvertUtils.toPx(getActivity(), 10));
        picker.setRangeEnd(2111, 1, 11);
        picker.setRangeStart(2016, 8, 29);
        picker.setSelectedItem(2050, 10, 14);
        picker.setResetWhileWheel(false);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                Utils.showToast(getActivity(),year + "-" + month + "-" + day);
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();

    }

    @Override
    public void onResume() {

        super.onResume();
    }

   
}
