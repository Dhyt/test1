package ml.amaze.design.receipe.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.List;

import ml.amaze.design.R;
import ml.amaze.design.bean.Entity3_douguo;
import ml.amaze.design.bean.Entity3_own;
import ml.amaze.design.receipe.MTextView;

/**
 * @author hxj
 * @date 2017/12/2 0002
 */

public class RecipeFragment extends Fragment {




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view;
        Bundle bundle = getArguments();
        String recipeJson = (String) bundle.get("Recipe_Json");
        String type = (String) bundle.get("type");

        String own = "own";
        Log.d("RecipeFragment", "recipeJson：" + recipeJson);
        Log.d("RecipeFragment", "recipetype：" + type);
        if (own.equals(type)) {
            Log.d("type", own);
            Entity3_own entity3Own = new Gson().fromJson(recipeJson, Entity3_own.class);
            Log.d("entity3Own", entity3Own.toString());
            view = initviewOwn(entity3Own, inflater);
        } else {
            Log.d("type", "douguo");
            Entity3_douguo.DataBean dataBean = new Gson().fromJson(recipeJson, Entity3_douguo.DataBean.class);
            view = initviewDouguo(dataBean, inflater);
        }

        return view;
    }


    private View initviewOwn(Entity3_own entity3Own, LayoutInflater inflater) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.recipepart_fragment_recipe_own, null);
        System.out.println("______entity3_own.getType()_____" + entity3Own.getType());
        Entity3_own.DataBean data = entity3Own.getData();
        System.out.println("______data.getExt()_____" + data.getExt());
        String ext = data.getExt();
        Log.d("ext", ext);
        System.out.println("______ext_____" + ext);


        //主要材料
        List<Entity3_own.DataBean.MaterialsBean.MajorMaterialsBean> majorMaterials = data.getMaterials().getMajor_materials();
        ListView majorLv = (ListView) view.findViewById(R.id.recipe_fragment_own_lv_major_materials);
        //own菜谱有两种，一种是 materials : {"major_materials":[{"name":"羊肉(肥瘦)","weigh。。。。
        //另一种是"materials":{"raw":[{"name":"猪肉(肥瘦)","wei。。。。
        if (majorMaterials != null) {
            //辅料
            List<Entity3_own.DataBean.MaterialsBean.MajorMaterialsBean> minorMaterials = data.getMaterials().getMinor_materials();
            //调料
            List<Entity3_own.DataBean.MaterialsBean.MajorMaterialsBean> seasoning = data.getMaterials().getSeasoning();
            ListView minorLv = (ListView) view.findViewById(R.id.recipe_fragment_own_lv_minor_materials);
            ListView seasoningLv = (ListView) view.findViewById(R.id.recipe_fragment_own_lv_seasoning);

            TextView steps = (TextView) view.findViewById(R.id.recipe_fragment_own_tv_steps);
            if (ext.trim().isEmpty()) {
                steps.setText("暂无信息");
            } else {
                steps.setText(ext);
            }


            majorLv.setAdapter(new MyAdapterOwn(majorMaterials));
            //实现多个listView共存
            setListViewHeightBasedOnChildren(majorLv);

            minorLv.setAdapter(new MyAdapterOwn(minorMaterials));
            //实现多个listView共存
            setListViewHeightBasedOnChildren(minorLv);


            seasoningLv.setAdapter(new MyAdapterOwn(seasoning));
            //实现多个listView共存
            setListViewHeightBasedOnChildren(seasoningLv);


        } else {
            List<Entity3_own.DataBean.MaterialsBean.MajorMaterialsBean> raw = data.getMaterials().getRaw();
            majorLv.setAdapter(new MyAdapterOwn(raw));
            TextView minorMaterials = (TextView) view.findViewById(R.id.recipe_fragment_own_tv_minor_materials);
            System.out.println("tvName.setText(\"辅料\\n无\"");
            minorMaterials.setText("辅料\n无");
            System.out.println("tvName.setText(\"调料\\n无\");");
            TextView seasoning = (TextView) view.findViewById(R.id.recipe_fragment_own_tv_seasoning);
            seasoning.setText("调料\n无");
            setListViewHeightBasedOnChildren(majorLv);
        }

        return view;
    }

    private class MyAdapterOwn extends BaseAdapter {

        List<Entity3_own.DataBean.MaterialsBean.MajorMaterialsBean> list;

        private MyAdapterOwn(List<Entity3_own.DataBean.MaterialsBean.MajorMaterialsBean> list) {
            this.list = list;
        }

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

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(getActivity(), R.layout.recipepart_items_recipe_condiments_own, null);
            } else {
                view = convertView;
            }
            Entity3_own.DataBean.MaterialsBean.MajorMaterialsBean bean = list.get(position);
            String name = bean.getName();
            double weight = bean.getWeight();

            TextView tvName = (TextView) view.findViewById(R.id.recipe_item_own_tv_name);
            TextView tvWeight = (TextView) view.findViewById(R.id.recipe_item_own_tv_weight);
            tvName.setText(name);
            tvWeight.setText(weight + "克");


            return view;
        }
    }


    private View initviewDouguo(Entity3_douguo.DataBean data, LayoutInflater inflater) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.recipepart_fragment_recipe_douguo, null);

        List<Entity3_douguo.DataBean.CondimentsBean> condiments = data.getCondiments();
        List<Entity3_douguo.DataBean.recipeStepsBean> steps = data.getSteps();
        String tips = data.getTips();

        //小贴士
        TextView tipsTv = (TextView) view.findViewById(R.id.recipe_fragment_douguo_tv_tips);
        tipsTv.setText(tips);


        ListView condimentsLv = (ListView) view.findViewById(R.id.recipe_fragment_douguo_lv_condiments);
        ListView stepsLv = (ListView) view.findViewById(R.id.recipe_fragment_douguo_lv_steps);

        condimentsLv.setAdapter(new MyAdapterDouguo(condiments, "condiments"));
        stepsLv.setAdapter(new MyAdapterDouguo(steps, "steps"));

        setListViewHeightBasedOnChildren(condimentsLv);
        setListViewHeightBasedOnChildren(stepsLv);
        return view;
    }

    private class MyAdapterDouguo extends BaseAdapter {

        List list;
        String arg;

        private MyAdapterDouguo(List list, String arg) {

            this.list = list;
            this.arg = arg;
        }

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
            String condiments = "condiments";
            if (convertView == null) {
                if (condiments.equals(arg)) {
                    view = View.inflate(getActivity(), R.layout.recipepart_items_recipe_condiments_douguo, null);
                } else {
                    view = View.inflate(getActivity(), R.layout.recipepart_items_recipe_steps_douguo, null);

                }
            } else {
                view = convertView;
            }

            String text1;
            String text2;
            if (arg.equals(condiments)) {
                Entity3_douguo.DataBean.CondimentsBean bean = (Entity3_douguo.DataBean.CondimentsBean) list.get(position);
                text1 = bean.getName();
                text2 = bean.getAmount();
                TextView tvName = (TextView) view.findViewById(R.id.recipe_item_douguo_condiments_tv_name);
                TextView tvWeight = (TextView) view.findViewById(R.id.recipe_item_douguo_condiments_tv_amount);
                tvName.setText(text1);
                tvWeight.setText(text2);
            } else {
                Entity3_douguo.DataBean.recipeStepsBean bean = (Entity3_douguo.DataBean.recipeStepsBean) list.get(position);
                text1 = "第" + bean.getPosition() + "步：";
                text2 = bean.getDesc();
                String url = (String) bean.getImage_url();
                if (url != null) {
                    ImageView iv = (ImageView) view.findViewById(R.id.recipe_item_douguo_steps_iv);
                    Glide.with(getActivity()).load(url).override(250, 250).fitCenter().into(iv);
                }
                MTextView tvName = (MTextView) view.findViewById(R.id.recipe_item_douguo_steps_tv_index);
                MTextView tvWeight = (MTextView) view.findViewById(R.id.recipe_item_douguo_steps_tv_steps);
                tvName.setText(text1);
                tvWeight.setText(text2);
            }
            return view;
        }
    }


    //实现多个listview

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
            Log.d("totalHeight", totalHeight + "");
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);

    }


}

