package ml.amaze.design.sidebar;

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

import ml.amaze.design.R;
import ml.amaze.design.userinfo.User;
import ml.amaze.design.utils.Utils;

/**
 * @author hxj
 * @date 2017/12/31 0031
 */

public class SuggestEatFragment extends Fragment {
    private String[] namezhcn = {"热量", "蛋白质", "脂肪", "碳水化合物", "维生素A", "维生素B1", "维生素B2", "维生素C", "维生素E", "钙", "磷", "铁", "锌", "硒"};
    private Double[] suggest;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.sidebar_suggest_eat, null);

        User user = User.getUserInstance(getActivity());
        if (user == null) {
            Utils.showToast(getActivity(), "热量、蛋白质、脂肪、碳水化合物的推荐量不可用，请先在 我的信息 注册您的个人信息,这并不会上传您的任何信息");
            suggest = new Double[]{0d, 0d, 0d, 0d, 750d, 1.4, 1.3, 100d, 14d, 900d, 700d, 17.5, 13d, 50d};
        } else {
            double demandEnergy = user.getDemandEnergy();
            double suggestProtein = Utils.setDot(demandEnergy * 0.15 / 0.92 / 4, 1);
            double suggestFat = Utils.setDot(demandEnergy * 0.25 / 0.95 / 9, 1);
            double suggestCarbohydrate = Utils.setDot(demandEnergy * 0.6 / 0.98 / 4, 1);
            //{demandEnergy+"kcal", protein+"g",fat+"g", carbohydrate+"g", "750ug", "1.4mg", "1.3mg", "100mg", "14mg", "900mg", "700mg", "17.5mg", "13mg", "50ug"};
            suggest = new Double[]{demandEnergy, suggestProtein, suggestFat, suggestCarbohydrate, 750d, 1.4, 1.3, 100d, 14d, 900d, 700d, 17.5, 13d, 50d};
        }


        ListView lv = (ListView) view.findViewById(R.id.sidebar_suggest_eat_lv);

        lv.setAdapter(new Myadapter());


        return view;

    }

    private class Myadapter extends BaseAdapter {

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
                view = View.inflate(getActivity(), R.layout.sidebar_suggest_eat_items, null);
            } else {
                view = convertView;
            }

            TextView tvName = (TextView) view.findViewById(R.id.sidebar_suggest_eat_items_tv_name);
            TextView amount = (TextView) view.findViewById(R.id.sidebar_suggest_eat_items_tv_amount);
            String name = namezhcn[position];
            tvName.setText(name);
            if ("热量".equals(name)) {
                amount.setText(suggest[position] + "千卡");

            } else if ("蛋白质".equals(name) || "脂肪".equals(name) || "碳水化合物".equals(name)) {
                amount.setText(suggest[position] + "克");

            } else if ("维生素A".equals(name) || "硒".equals(name)) {
                amount.setText(suggest[position] + "微克");
            } else {
                amount.setText(suggest[position] + "毫克");
            }
            return view;
        }
    }
}
