package ml.amaze.design.receipe;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.qqtheme.framework.picker.DoublePicker;
import ml.amaze.design.R;
import ml.amaze.design.bean.DaoSession;
import ml.amaze.design.bean.DietPlanBean;
import ml.amaze.design.bean.DietPlanBeanDao;
import ml.amaze.design.bean.Entity2;
import ml.amaze.design.bean.NutritionSummaryBean;
import ml.amaze.design.bean.NutritionSummaryBeanDao;
import ml.amaze.design.database.GreenDaoUtils;
import ml.amaze.design.receipe.fragment.NutrientsFragment;
import ml.amaze.design.receipe.fragment.RecipeFragment;
import ml.amaze.design.utils.Utils;

import static android.widget.RelativeLayout.BELOW;
import static android.widget.RelativeLayout.CENTER_HORIZONTAL;

/**
 *
 * @author hxj
 * @date 2017/8/14 0014
 */

public class DetailsActivity extends AppCompatActivity {

    private Entity2 entity2;
    private boolean flag;
    private Map<String, Object> map;
    private String code;
    int backStackEntryCount;
    @SuppressLint({"WrongViewCast", "SetTextI18n"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipepart_details);
        //添加返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //fragment返回栈的个数
        backStackEntryCount = getFragmentManager().getBackStackEntryCount();
        //write by hyt
        flag = false;
        map = new HashMap<>();
        //get code
        Intent i = getIntent();
        code = i.getStringExtra("code");
        getJosnByCodeOnNet();
        System.out.println("code:" + code);

        while (true) {
            if (map.get("Entity2_json") != null) {
                entity2 = (Entity2) map.get("Entity2_json");
                System.out.println(entity2.toString());
                //get info
                String name = entity2.getName();
                String appraise1 = entity2.getAppraise();
                int healthLight1 = entity2.getHealth_light();
                String weight1 = entity2.getWeight();
                String path = entity2.getLarge_image_url();
                String calory = entity2.getCalory();
                //get wiget
                TextView tittle = (TextView) findViewById(R.id.detail_layout_tv_tittle);
                TextView appraise = (TextView) findViewById(R.id.detail_layout_tv_appraise);
                TextView healthLight = (TextView) findViewById(R.id.detail_layout_tv_health_light);
                TextView weight = (TextView) findViewById(R.id.detail_layout_tv_weight);
                ImageView image = (ImageView) findViewById(R.id.detail_layout_iv_image);

                //设置添加到膳食计划按钮
                TextView add = (TextView) findViewById(R.id.detail_layout_tv_add);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addDietPlan();

                    }
                });


                //set info
                tittle.setText(name);
                appraise.setText(appraise1);
                healthLight.setText("健康等级" + healthLight1);
                weight.setText(calory + "千卡/" + weight1 + "克");
                //set image
                Glide.with(this).load(path).override(650, 480).fitCenter().into(image);

                //setOnClickListener查看营养素按钮，添加点击事件
                TextView tvNutrient = (TextView) findViewById(R.id.detail_layout_tv_nutrient);
                tvNutrient.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NutrientsFragment nutrientsFragment = new NutrientsFragment();
                        /*往bundle中添加数据*/
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("entity2", entity2);
                        nutrientsFragment.setArguments(bundle);

                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.detail_layout_rl, nutrientsFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });
                //添加 查看菜谱按钮
                if (entity2.getRecipe_type() != null) {
                    addTextView();
                }
                break;
            }

        }


    }


    /**
     * 设置返回键
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            //对用户按home icon的处理
            case android.R.id.home:
                if(backStackEntryCount<getFragmentManager().getBackStackEntryCount()){
                getFragmentManager().popBackStack();

                }else {
                    finish();
                }

                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 添加至膳食计划
     */
    private void addDietPlan() {
        int maxValue=31;
        final List<String> str1 = new ArrayList<>();
        str1.add("早");
        str1.add("午");
        str1.add("晚");
        final List<String> str2 = new ArrayList<>();
        for (int i = 1; i < maxValue; i++) {
            str2.add(i + "0");
        }
        DoublePicker doublePicker = new DoublePicker(this, str1, str2);
        doublePicker.setFirstLabel(null, "餐");
        doublePicker.setSecondLabel(null, "克");
        doublePicker.setShadowColor(Color.BLUE, 20);
        doublePicker.setSelectedIndex(1, 11);
        doublePicker.setTitleText("添加到膳食计划");
        doublePicker.setOnPickListener(new DoublePicker.OnPickListener() {
            @Override
            public void onPicked(int i, int i1) {
                //i:0,1,2 i1:10,20,30,...300
                //开启子线程保存膳食计划,并保存至能量汇总
                readAndSaveDietPlan(str2, i, i1);

            }
        });


        doublePicker.show();
    }
    /**
     * 保存至数据库
     * @param str2  所选菜谱的数量  10，20，30.。。。
     * @param i     whichMeal 0,1,2
     * @param i1    多少克
     * @return
     */
    private boolean readAndSaveDietPlan(final List<String> str2, final int i, final int i1) {
        Toast.makeText(getApplicationContext(), i + ":" + str2.get(i1), Toast.LENGTH_SHORT).show();
        new Thread() {
            @Override
            public void run() {

                //使用greenDao框架保存数据
                Log.d("DetailActivity子线程", "开启子线程保存膳食计划和能量汇总");
                DietPlanBean dietPlanBean = new DietPlanBean(null, entity2, i, str2.get(i1));
                DaoSession daoSession = GreenDaoUtils.getDaoSessionInstance(getApplicationContext());
                //保存膳食计划 DietPlanBean
                DietPlanBeanDao dietPlanBeanDao = daoSession.getDietPlanBeanDao();
                dietPlanBeanDao.insert(dietPlanBean);
                Log.d("DetailActivity子线程", "readAndSaveDietPlan-dietPlanBeanDao保存成功");


                //保存至能量汇总NutritionSummaryBean
                NutritionSummaryBeanDao nutritionSummaryBeanDao = daoSession.getNutritionSummaryBeanDao();
                String s=Utils.getFormatDate();
                //选出今天的能量汇总
                List<NutritionSummaryBean> list = nutritionSummaryBeanDao.queryBuilder().
                        where(NutritionSummaryBeanDao.Properties.Date.eq(s)).build().list();
                    NutritionSummaryBean nutritionSummaryBean;
                if (list.size()==0){
                    //创建一个新nutritionSummaryBean
                    nutritionSummaryBean = new NutritionSummaryBean();
                }else {
                    //只有一个
                nutritionSummaryBean = list.get(0);
                }
                nutritionSummaryBeanDao.insertOrReplace(new NutritionSummaryBean(nutritionSummaryBean,dietPlanBean));
                Log.d("DetailActivity子线程", "readAndSaveDietPlan-NutritionSummaryBean保存成功");
                super.run();
            }
        }.start();


        return true;
    }






    /**
     * get entity2's json by code
     */
    public void getJosnByCodeOnNet() {

        //在子线程中访问网络，获取entity2的json
        new Thread() {
            @Override
            public void run() {
                try {
                    GetData getData = new GetData();
                    //return a json
                    String entity2Json = getData.getEntity2(code);
                    entity2 = new Gson().fromJson(entity2Json, Entity2.class);
                    Log.d("detailsActivity", "jsonEntity2-"+entity2Json);
                    map.put("Entity2_json", entity2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void getRecipeByTypeOnNet() {

        //在子线程中访问网络，获取entity2的json
        new Thread() {
            @Override
            public void run() {
                try {
                    GetData getData = new GetData();
                    String recipeType = entity2.getRecipe_type();
                    String recipeJson;
                    String own="own";
                    if (own.equals(recipeType)) {
                        //return a json
                        recipeJson = getData.getrecipeOwn(code);
                        map.put("type", own);
                    } else {
                        recipeJson = getData.getrecipeDouguo(code);
                        map.put("type", "douguo");
                    }
                    Log.d("jsonEntity3", recipeJson);
                    map.put("Recipe_Json", recipeJson);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            System.out.println("在detail界面按下了返回");
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 添加TextView
     */
    public void addTextView() {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.detail_layout_rl);

        TextView tv = new TextView(this);

        tv.setText("查看菜谱");
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("addTextView", "按钮被点击");
                getRecipeByTypeOnNet();
                while (true) {
                    if (map.get("Recipe_Json") != null) {
                        RecipeFragment recipeFragment = new RecipeFragment();
                        /*往bundle中添加数据*/
                        Bundle bundle = new Bundle();
                        String recipeJson = (String) map.get("Recipe_Json");
                        String type = (String) map.get("type");
                        bundle.putString("Recipe_Json", recipeJson);
                        bundle.putString("type", type);
                        recipeFragment.setArguments(bundle);
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.detail_layout_rl_main, recipeFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        break;
                    }
                }
            }
        });
        tv.setTextSize(20);

        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.addRule(BELOW, R.id.detail_layout_tv_nutrient);
        lp2.addRule(CENTER_HORIZONTAL);
        // btn2 位于 btn1 的下方          
        relativeLayout.addView(tv, lp2);


    }
}
