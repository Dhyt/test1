package ml.amaze.design.dietplan;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import ml.amaze.design.R;
import ml.amaze.design.bean.DaoSession;
import ml.amaze.design.bean.DietPlanBean;
import ml.amaze.design.bean.DietPlanBeanDao;
import ml.amaze.design.bean.NutritionSummaryBean;
import ml.amaze.design.bean.NutritionSummaryBeanDao;
import ml.amaze.design.database.GreenDaoUtils;
import ml.amaze.design.utils.Utils;

/**
 *
 * @author hxj
 * @date 2017/12/17 0017
 */

public class DietPlanActivity extends AppCompatActivity {
    double demandEnergy;
        List<DietPlanBean> listAll;
    DaoSession daoSession;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dietplanpart_main);

        Log.d("DietPlanActivity","DietPlanActivity开启");
        Intent i = getIntent();
        demandEnergy = i.getDoubleExtra("DemandEnergy", 0);
        Log.d("DietPlanActivity","DemandEnergy="+demandEnergy);
        initView();


        //今天日期
        String s = Utils.getFormatDate();
        daoSession = GreenDaoUtils.getDaoSessionInstance(this);
        listAll = daoSession.getDietPlanBeanDao().queryBuilder().
                where(DietPlanBeanDao.Properties.Date.eq(s)).
                build().list();
        if(listAll.size()==0){
            Utils.showToast(this, "您还没有创建今天的膳食计划，请在 搜索菜品 中添加您想吃的食物");
        }
    }


    @SuppressLint("SetTextI18n")
    private void initView() {
        TextView tvDemandenergy = (TextView) findViewById(R.id.dietplan_tv_demandEnergy);
        TextView tvBreakfast = (TextView) findViewById(R.id.dietplan_tv_breakfast_calory);
        TextView tvBreakfastProtein = (TextView) findViewById(R.id.dietplan_tv_breakfast_protein);
        TextView tvBreakfastFat = (TextView) findViewById(R.id.dietplan_tv_breakfast_fat);
        TextView tvBreakfastCarbohydrate = (TextView) findViewById(R.id.dietplan_tv_breakfast_carbohydrate);
        TextView tvLunch = (TextView) findViewById(R.id.dietplan_tv_lunch_calory);
        TextView tvLunchProtein = (TextView) findViewById(R.id.dietplan_tv_lunch_protein);
        TextView tvLunchFat = (TextView) findViewById(R.id.dietplan_tv_lunch_fat);
        TextView tvLunchCarbohydrate = (TextView) findViewById(R.id.dietplan_tv_lunch_carbohydrate);
        TextView tvSupper = (TextView) findViewById(R.id.dietplan_tv_supper_calory);
        TextView tvSupperProtein = (TextView) findViewById(R.id.dietplan_tv_supper_protein);
        TextView tvSupperFat = (TextView) findViewById(R.id.dietplan_tv_supper_fat);
        TextView tvSupperCarbohydrate = (TextView) findViewById(R.id.dietplan_tv_supper_carbohydrate);


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
    }


    /**
     * 四个按钮的点击事件
     * @param view
     */
    public void breakfast(View view) {
        DietPlanFragmentBreakfast breakfast = new DietPlanFragmentBreakfast();
        Bundle bundle = new Bundle();
        bundle.putDouble("breakfastdemandEnergy", Utils.setDot(demandEnergy * 0.3,2));
        Log.d("dietPlanFragment", "开启饮食计划Fragment");

        startFragmentOnMain(breakfast, bundle);
    }

    public void lunch(View view) {
        DietPlanFragmentLunch lunch = new DietPlanFragmentLunch();
        Bundle bundle = new Bundle();
        bundle.putDouble("lunchdemandEnergy", Utils.setDot(demandEnergy * 0.4,2));

        Log.d("dietPlanFragment", "开启饮食计划lunchFragment");
        startFragmentOnMain(lunch, bundle);
    }

    public void supper(View view) {

        DietPlanFragmentSupper supper = new DietPlanFragmentSupper();
        Bundle bundle = new Bundle();
        bundle.putDouble("supperdemandEnergy", Utils.setDot(demandEnergy * 0.3,2));

        Log.d("dietPlanFragment", "开启饮食计划supperFragment");
        startFragmentOnMain(supper, bundle);
    }

    public void summary(View view) {

        //从数据库获取能量汇总

        String s=Utils.getFormatDate();
        List<NutritionSummaryBean> list = daoSession.getNutritionSummaryBeanDao().queryBuilder().
                where(NutritionSummaryBeanDao.Properties.Date.eq(s)).build().list();
        if(list.size()>0){
            //只有一个
            NutritionSummaryBean nutritionSummaryBean = list.get(0);
            String str1="能量:"+      nutritionSummaryBean.getCalory()    +"kcal\n";
            String str2="蛋白质:"+     nutritionSummaryBean.getProtein()    +"克\n";
            String str3="脂肪:"+      nutritionSummaryBean.getFat() +"克\n";
            String str4="碳水化合物"+    nutritionSummaryBean.getCarbohydrate() +"克";
            new AlertDialog.Builder(this)
                    .setTitle("您今日的营养素汇总")
                    .setMessage(str1+str2+str3+str4)
                    .setPositiveButton("确定", null)
                    .show();
        }else {
            Utils.showToast(this,"您还没有创建今天的膳食计划，请在 搜索菜品 中添加您想吃的食物");
        }

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








    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //点击了返回键
            int count = getFragmentManager().getBackStackEntryCount();

            System.out.println("DietPlanctivity当前回退栈个数：" + count);
        //若arg2=1弹出回退栈中所有的fragment,若arg2=0,弹出一个fragment
        this.getFragmentManager().popBackStackImmediate(null,1);
        }
        return super.onKeyDown(keyCode, event);
    }


}
