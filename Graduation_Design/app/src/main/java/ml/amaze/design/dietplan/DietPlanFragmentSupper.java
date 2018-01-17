package ml.amaze.design.dietplan;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
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
 * @date 2017/12/13 0013
 */

public class DietPlanFragmentSupper extends Fragment {
    View view;
    double supperDemandEnergy;
    List<DietPlanBean> listSupper;

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.dietplanpart_breakfast, null);

        Bundle bundle = this.getArguments();
        supperDemandEnergy = bundle.getDouble("supperdemandEnergy");

        Log.d("DietPlanFragmentSupper", "supperdemandEnergy=" + supperDemandEnergy);


        //今天日期
        String s = Utils.getFormatDate();

        DaoSession daoSession = GreenDaoUtils.getDaoSessionInstance(getActivity());
        List<DietPlanBean> listAll = daoSession.getDietPlanBeanDao().queryBuilder().
                where(DietPlanBeanDao.Properties.Date.eq(s)).
                build().list();
        System.out.println(listAll.size());
        System.out.println(listSupper);
        if (listAll.size()==0) {
            //如果没有创建过膳食计划
            Toast.makeText(getActivity(), "您还没有创建今天的膳食计划，请在 搜索菜品 中添加您想吃的食物", Toast.LENGTH_SHORT).show();
        } else {//如果创建过
            listSupper = new ArrayList<>();
            for (DietPlanBean d : listAll) {
                //把中饭选出来
                if(d.getWhichMeal()==2) {
                    listSupper.add(d);
                }
            }
            if(listSupper.size()!=0){
                ListView lvBreakfast = (ListView) view.findViewById(R.id.dietplan_breakfast_lv_breakfast);
                lvBreakfast.setAdapter(new MyAdapter());
                initView();
            }else {
                Toast.makeText(getActivity(), "您还没有创建今天晚上的膳食计划，请在 搜索菜品 中添加您想吃的食物", Toast.LENGTH_LONG).show();

            }

        }

        return view;
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listSupper.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(getActivity(), R.layout.dietplanpart_items_breakfast, null);
            } else {
                view = convertView;
            }

            TextView tvName = (TextView) view.findViewById(R.id.dietplan_item_tv_name);
            TextView tvCount = (TextView) view.findViewById(R.id.dietplan_item_tv_count);
            TextView tvCalory = (TextView) view.findViewById(R.id.dietplan_item_tv_calory);
            TextView tvProtein = (TextView) view.findViewById(R.id.dietplan_item_tv_protein);
            TextView tvFat = (TextView) view.findViewById(R.id.dietplan_item_tv_fat);
            TextView tvCarbohydrate = (TextView) view.findViewById(R.id.dietplan_item_tv_carbohydrate);

            DietPlanBean dietPlanBean = listSupper.get(position);

            tvName.setText(dietPlanBean.getName());
            tvCount.setText(String.format("%sg", dietPlanBean.getCount()));

            tvCalory.setText(String.format("%skcal", dietPlanBean.getCalory()));
            tvProtein.setText(String.format("蛋白质:%sg", dietPlanBean.getProtein()));
            tvFat.setText(String.format("脂肪:%sg", dietPlanBean.getFat()));
            tvCarbohydrate.setText(String.format("碳水化合物:%sg", dietPlanBean.getCarbohydrate()));


            return view;
        }
    }


    @SuppressLint("SetTextI18n")
    private void initView() {
        //头标签
        TextView tvSupper =                    (TextView) view.findViewById(R.id.dietplan_breakfast_tv_demandEnergy);
        TextView tvSupperProtein =            (TextView) view.findViewById(R.id.dietplan_breakfast_tv_breakfast_protein);
        TextView tvSupperFat =                (TextView) view.findViewById(R.id.dietplan_breakfast_tv_breakfast_fat);
        TextView tvSupperCarbohydrate =       (TextView) view.findViewById(R.id.dietplan_breakfast_tv_breakfast_carbohydrate);

        /*计算每顿饭的能量，
        早餐晚餐各30%，午餐40%，所以晚餐的能量计算可用早餐代替
         根据三大产能营养素需求量（碳水化合物60%，脂肪25%，蛋白质15%）
        和实际需求量（吸收率按碳水化合物98%，脂肪95%，蛋白质92%计算）
        1卡=1卡路里=4.184焦耳；
        1千卡=1大卡=1000卡=1000卡路里 =4184焦耳=4.184千焦。
        每1克的蛋白质或碳水化合物的热量为 4千卡，而每1克的脂肪热量为 9千卡
        */
        double breakfastProtein = supperDemandEnergy * 0.15 / 0.92 / 4;
        double breakfastFat = supperDemandEnergy * 0.25 / 0.95 / 9;
        double breakfastCarbohydrate = supperDemandEnergy * 0.6 / 0.98 / 4;


        //设置头标签
        tvSupper.setText(String.format("晚餐需要的能量:%skcal", Utils.setDot(supperDemandEnergy,2)));
        tvSupperProtein.setText(String.format("蛋白质:%sg", Utils.setDot(breakfastProtein,1)));
        tvSupperFat.setText(String.format("脂肪:%sg", Utils.setDot(breakfastFat,1)));
        tvSupperCarbohydrate.setText(String.format("碳水化合物:%sg", Utils.setDot(breakfastCarbohydrate,1)));

        //尾标签
        TextView tvCalorySummary = (TextView) view.findViewById(R.id.dietplan_tv_calory_summary);
        TextView tvProteinSummary = (TextView) view.findViewById(R.id.dietplan_tv_protein_summary);
        TextView tvFatSummary = (TextView) view.findViewById(R.id.dietplan_tv_fat_summary);
        TextView tvCarbohydrateSummary = (TextView) view.findViewById(R.id.dietplan_tv_carbohydrate_summary);
        TextView tvSummary = (TextView) view.findViewById(R.id.dietplan_tv__summary);

        double calorySum            =0;
        double proteinSum           =0;
        double fatSum               =0;
        double carbohydrateSum      =0;

        for (DietPlanBean d:listSupper){
            calorySum+=Double.parseDouble(d.getCalory());
            proteinSum+=Double.parseDouble(d.getProtein());
            fatSum+=Double.parseDouble(d.getFat());
            carbohydrateSum+=Double.parseDouble(d.getCarbohydrate());
        }
        tvSummary.setText("汇·\n总·");
 
        tvCalorySummary.setText(Utils.setDot(calorySum,2)+"\nkcal");
        tvProteinSummary.setText("蛋白质:\n"+Utils.setDot(proteinSum,2)     +"g");
        tvFatSummary.setText("脂肪:\n"+Utils.setDot(fatSum,2)         +"g");
        tvCarbohydrateSummary.setText("碳水化合物:\n"+Utils.setDot(carbohydrateSum,2)+"g");

    }

   


   
}
