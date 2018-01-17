package ml.amaze.design.dietplan;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ml.amaze.design.R;
import ml.amaze.design.bean.DaoSession;
import ml.amaze.design.bean.NutritionSummaryBean;
import ml.amaze.design.bean.NutritionSummaryBeanDao;
import ml.amaze.design.database.GreenDaoUtils;
import ml.amaze.design.utils.Utils;

/**
 * @author hxj
 * @date 2017/12/30 0030
 */

public class DietPlanFragmentSummary extends Fragment {
    private String[] namezhcn = {"热量", "蛋白质", "脂肪", "碳水化合物", "维生素A", "维生素B1", "维生素B2", "维生素C", "维生素E", "钙", "磷", "铁", "锌", "硒"};
    //以14-50岁推荐量为标准

    private Double[] suggest;
    private Double [] eat;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.dietplanpart_summary, null);

        Bundle bundle = getArguments();
        Double demandEnergy = bundle.getDouble("demandEnergy");

        double suggestProtein = Utils.setDot(demandEnergy * 0.15 / 0.92 / 4, 1);
        double suggestFat = Utils.setDot(demandEnergy * 0.25 / 0.95 / 9, 1);
        double suggestCarbohydrate = Utils.setDot(demandEnergy * 0.6 / 0.98 / 4, 1);
        //{demandEnergy+"kcal", protein+"g",fat+"g", carbohydrate+"g", "750ug", "1.4mg", "1.3mg", "100mg", "14mg", "900mg", "700mg", "17.5mg", "13mg", "50ug"};
        suggest = new Double[]{demandEnergy , suggestProtein , suggestFat , suggestCarbohydrate , 750d, 1.4, 1.3, 100d, 14d, 900d, 700d, 17.5, 13d, 50d};

        //从数据库获取能量汇总
        String s = Utils.getFormatDate();
        DaoSession daoSession = GreenDaoUtils.getDaoSessionInstance(getActivity());
        List<NutritionSummaryBean> list = daoSession.getNutritionSummaryBeanDao().queryBuilder().
                where(NutritionSummaryBeanDao.Properties.Date.eq(s)).build().list();
            //只有一个

            eat=new Double[14];
        if (list.size() > 0) {
            NutritionSummaryBean nutritionSummaryBean = list.get(0);
            eat[0]     = Double.parseDouble(nutritionSummaryBean.getCalory());
            eat[1]     = Double.parseDouble(nutritionSummaryBean.getProtein());
            eat[2]     = Double.parseDouble(nutritionSummaryBean.getFat());
            eat[3]     = Double.parseDouble(nutritionSummaryBean.getCarbohydrate());
            eat[4]     = Double.parseDouble(nutritionSummaryBean.getVitamin_a());
            eat[5]     = Double.parseDouble(nutritionSummaryBean.getThiamine());
            eat[6]     = Double.parseDouble(nutritionSummaryBean.getLactoflavin());
            eat[7]     = Double.parseDouble(nutritionSummaryBean.getVitamin_c());
            eat[8]     = Double.parseDouble(nutritionSummaryBean.getVitamin_e());
            eat[9]     = Double.parseDouble(nutritionSummaryBean.getCalcium());
            eat[10]    = Double.parseDouble(nutritionSummaryBean.getPhosphor());
            eat[11]    = Double.parseDouble(nutritionSummaryBean.getIron());
            eat[12]    = Double.parseDouble(nutritionSummaryBean.getZinc());
            eat[13]    = Double.parseDouble(nutritionSummaryBean.getSelenium());
        } else {
            Utils.showToast(getActivity(), "您还没有创建今天的膳食计划，请在 搜索菜品 中添加您想吃的食物");
            eat[0]     = 0d;
            eat[1]     = 0d;
            eat[2]     = 0d;
            eat[3]     = 0d;
            eat[4]     = 0d;
            eat[5]     = 0d;
            eat[6]     = 0d;
            eat[7]     = 0d;
            eat[8]     = 0d;
            eat[9]     = 0d;
            eat[10]    = 0d;
            eat[11]    = 0d;
            eat[12]    = 0d;
            eat[13]    = 0d;
        }


        ListView summaryLv = (ListView) view.findViewById(R.id.dietplan_summary_lv);

        summaryLv.setAdapter(new MyAdapter());


        return view;
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 14;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(getActivity(), R.layout.dietplanpart_item_summary, null);
            } else {
                view = convertView;
            }
            TextView tvNutrients = (TextView) view.findViewById(R.id.dietplan_summary_tv_nutrients);
            TextView tvSuggest = (TextView) view.findViewById(R.id.dietplan_summary_tv_suggest);
            TextView tvEat = (TextView) view.findViewById(R.id.dietplan_summary_tv_eat);

            String name=namezhcn[position];
            tvNutrients.setText(name);
            if("热量".equals(name)){
                tvSuggest.setText(suggest[position]+"千卡");
                tvEat.setText(eat[position]+"千卡");
            }else if("蛋白质".equals(name) || "脂肪".equals(name) || "碳水化合物".equals(name) ){
                tvSuggest.setText(suggest[position]+"克");
                tvEat.setText(eat[position]+"克");

            }else if("维生素A".equals(name) || "硒".equals(name)){
                tvSuggest.setText(suggest[position]+"微克");
                tvEat.setText(eat[position]+"微克");
            }else {
                tvSuggest.setText(suggest[position]+"毫克");
                tvEat.setText(eat[position]+"毫克");

            }
            if(eat[position]>suggest[position]*1.2){
                //超出120%
                tvEat.setTextColor(getResources().getColor(R.color.red));
            }else if (eat[position]<suggest[position]*1.2&&eat[position]>suggest[position]*0.8){
                tvEat.setTextColor(getResources().getColor(R.color.green));

            }else if (eat[position]<suggest[position]*0.8){
                tvEat.setTextColor(getResources().getColor(R.color.yellow));

            }

            return view;
        }
    }
}
