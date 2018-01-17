package ml.amaze.design.dietplan;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ml.amaze.design.R;
import ml.amaze.design.bean.DaoSession;
import ml.amaze.design.bean.DietPlanBean;
import ml.amaze.design.bean.DietPlanBeanDao;
import ml.amaze.design.database.GreenDaoUtils;
import ml.amaze.design.utils.Utils;

/**
 *
 * @author hxj
 * @date 2017/12/28 0028
 */

public class DietPlanFragment extends Fragment {
    double demandEnergy;
    List<DietPlanBean> listAll;
    DaoSession daoSession;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        
        view=inflater.inflate(R.layout.dietplanpart_main, null);
        Bundle bundle = this.getArguments();
        demandEnergy = bundle.getDouble("DemandEnergy");
        Log.d("DietPlanActivity","DemandEnergy="+demandEnergy);
        initView();


        //今天日期
        String s = Utils.getFormatDate();
        daoSession = GreenDaoUtils.getDaoSessionInstance(getActivity());
        listAll = daoSession.getDietPlanBeanDao().queryBuilder().
                where(DietPlanBeanDao.Properties.Date.eq(s)).
                build().list();
        if(listAll.size()==0){
            Utils.showToast(getActivity(), "您还没有创建今天的膳食计划，请在 搜索菜品 中添加您想吃的食物");
        }
        return view;
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        TextView tvDemandenergy = (TextView) view.findViewById(R.id.dietplan_tv_demandEnergy);
        TextView tvBreakfast = (TextView) view.findViewById(R.id.dietplan_tv_breakfast_calory);
        TextView tvBreakfastProtein = (TextView) view.findViewById(R.id.dietplan_tv_breakfast_protein);
        TextView tvBreakfastFat = (TextView) view.findViewById(R.id.dietplan_tv_breakfast_fat);
        TextView tvBreakfastCarbohydrate = (TextView) view.findViewById(R.id.dietplan_tv_breakfast_carbohydrate);
        TextView tvLunch = (TextView) view.findViewById(R.id.dietplan_tv_lunch_calory);
        TextView tvLunchProtein = (TextView) view.findViewById(R.id.dietplan_tv_lunch_protein);
        TextView tvLunchFat = (TextView) view.findViewById(R.id.dietplan_tv_lunch_fat);
        TextView tvLunchCarbohydrate = (TextView) view.findViewById(R.id.dietplan_tv_lunch_carbohydrate);
        TextView tvSupper = (TextView) view.findViewById(R.id.dietplan_tv_supper_calory);
        TextView tvSupperProtein = (TextView) view.findViewById(R.id.dietplan_tv_supper_protein);
        TextView tvSupperFat = (TextView) view.findViewById(R.id.dietplan_tv_supper_fat);
        TextView tvSupperCarbohydrate = (TextView) view.findViewById(R.id.dietplan_tv_supper_carbohydrate);


        /*计算每顿饭的能量，
        早餐晚餐各30%，午餐40%，所以晚餐的能量计算可用早餐代替
         根据三大产能营养素需求量（碳水化合物60%，脂肪25%，蛋白质15%）
        和实际需求量（吸收率按碳水化合物98%，脂肪95%，蛋白质92%计算）
        1卡=1卡路里=4.184焦耳；
        1千卡=1大卡=1000卡=1000卡路里 =4184焦耳=4.184千焦。
        每1克的蛋白质或碳水化合物的热量为 4千卡，而每1克的脂肪热量为 9千卡
        */
        double breakfastDemandEnergy = demandEnergy * 0.3;
        double breakfastProtein = breakfastDemandEnergy * 0.15 / 0.92 / 4;
        double breakfastFat = breakfastDemandEnergy * 0.25 / 0.95 / 9;
        double breakfastCarbohydrate = breakfastDemandEnergy * 0.6 / 0.98 / 4;
        double lunchDemandEnergy = demandEnergy * 0.4;
        double lunchProtein = lunchDemandEnergy * 0.15 / 0.92 / 4;
        double lunchFat = lunchDemandEnergy * 0.25 / 0.95 / 9;
        double lunchCarbohydrate = lunchDemandEnergy * 0.6 / 0.98 / 4;

        //设置标签

        tvDemandenergy.setText("日需能量" + Utils.setDot(demandEnergy,2) + "kcal");
        tvBreakfast.setText(Utils.setDot(breakfastDemandEnergy,2) + "kcal");
        tvBreakfastProtein.setText("蛋白质：" + Utils.setDot(breakfastProtein,1) + "g");
        tvBreakfastFat.setText("脂肪：" + Utils.setDot(breakfastFat,1) + "g");
        tvBreakfastCarbohydrate.setText("碳水化合物：" + Utils.setDot(breakfastCarbohydrate,1) + "g");
        tvLunch.setText(Utils.setDot(lunchDemandEnergy,2) + "kcal");
        tvLunchProtein.setText("蛋白质：" + Utils.setDot(lunchProtein,1) + "g");
        tvLunchFat.setText("脂肪：" + Utils.setDot(lunchFat,1) + "g");
        tvLunchCarbohydrate.setText("碳水化合物：" + Utils.setDot(lunchCarbohydrate,1) + "g");
        tvSupper.setText(Utils.setDot(breakfastDemandEnergy,2) + "kcal");
        tvSupperProtein.setText("蛋白质：" + Utils.setDot(breakfastProtein,1) + "g");
        tvSupperFat.setText("脂肪：" + Utils.setDot(breakfastFat,1) + "g");
        tvSupperCarbohydrate.setText("碳水化合物：" + Utils.setDot(breakfastCarbohydrate,1) + "g");

        //设置四个按钮
        TextView breakfastClick     =(TextView) view.findViewById(R.id.dietplan_tv_breakfast_click);
        TextView lunchClick         =(TextView) view.findViewById(R.id.dietplan_tv_lunch_click);
        TextView supperClick            =(TextView) view.findViewById(R.id.dietplan_tv_supper_click);
        TextView summary            =(TextView) view.findViewById(R.id. dietplan_tv_summary);
        breakfastClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                breakfast();
            }
        });
                lunchClick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lunch();
                    }
                });
        supperClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supper();
            }
        });
                summary.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        summary();
                    }
                });
    }


    /**
     * 四个按钮的点击事件
     *
     */
    public void breakfast() {
        DietPlanFragmentBreakfast breakfast = new DietPlanFragmentBreakfast();
        Bundle bundle = new Bundle();
        bundle.putDouble("breakfastdemandEnergy", Utils.setDot(demandEnergy * 0.3,2));
        Log.d("dietPlanFragment", "开启饮食计划Fragment");

        startFragmentOnMain(breakfast, bundle);
    }

    public void lunch() {
        DietPlanFragmentLunch lunch = new DietPlanFragmentLunch();
        Bundle bundle = new Bundle();
        bundle.putDouble("lunchdemandEnergy", Utils.setDot(demandEnergy * 0.4,2));

        Log.d("dietPlanFragment", "开启饮食计划lunchFragment");
        startFragmentOnMain(lunch, bundle);
    }

    public void supper() {

        DietPlanFragmentSupper supper = new DietPlanFragmentSupper();
        Bundle bundle = new Bundle();
        bundle.putDouble("supperdemandEnergy", Utils.setDot(demandEnergy * 0.3,2));

        Log.d("dietPlanFragment", "开启饮食计划supperFragment");
        startFragmentOnMain(supper, bundle);
    }

    public void summary() {

        DietPlanFragmentSummary dietPlanFragmentSummary=new DietPlanFragmentSummary();
        Bundle bundle = new Bundle();
        bundle.putDouble("demandEnergy", Utils.setDot(demandEnergy,2));
        startFragmentOnMain(dietPlanFragmentSummary, bundle);


    }


    private <T extends Fragment> void startFragmentOnMain(T c, Bundle bundle) {
        Log.d("startFragmentOnMain", "开始");
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (bundle != null) {
            c.setArguments(bundle);
        }
        transaction.replace(R.id.dietplan_ll_mian, c);
        transaction.addToBackStack(null);
        transaction.commit();
        Log.d("startFragmentOnMain", "结束");
    }



}
