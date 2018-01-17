package ml.amaze.design.sidebar;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ml.amaze.design.bean.NutritionDiseaseBean;
import ml.amaze.design.R;
import ml.amaze.design.utils.Utils;

/**
 *
 * @author hxj
 * @date 2017/12/21 0021
 */

public class DiseaseFragment extends Fragment {
        List<NutritionDiseaseBean> list;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view;
        view=inflater.inflate(R.layout.diseasepart_main,null);

        list = NutritionDiseaseBean. getDiseaseInstance();

        ListView lvDisease = (ListView) view.findViewById(R.id.disease_main_lv);

        lvDisease.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("DiseaseFragment","开启营养病详细");
                DiseaseDetailsFragment diseaseDetailsFragment = new DiseaseDetailsFragment();
                Bundle bundle = new Bundle();

                bundle.putSerializable("DiseaseBean",list.get(position));
                Utils.startFragmentOnMain(getActivity(),diseaseDetailsFragment,bundle);
            }
        });
        lvDisease.setAdapter(new MyAdapter());
        return view;
    }

    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
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
            if(convertView==null){
                view=View.inflate(getActivity(),R.layout.diseasepart_item_main_disease_name,null);
            }else {
                view=convertView;
            }

            NutritionDiseaseBean diseaseBean = list.get(position);

            TextView tvName = (TextView) view.findViewById(R.id.disease_item_mian_name);
            TextView tvIntroduce = (TextView) view.findViewById(R.id.disease_item_mian_introduce);

            tvName.setText(diseaseBean.getName());
            tvIntroduce.setText(diseaseBean.getIntroduce());
            return view;
        }
    }
}
