package ml.amaze.design;


import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import ml.amaze.design.dietplan.DietPlanFragment;
import ml.amaze.design.pedometer.fragment.PedometerFragment;
import ml.amaze.design.pedometer.service.StepService;
import ml.amaze.design.receipe.JsonApi;
import ml.amaze.design.receipe.fragment.FoodFragment;
import ml.amaze.design.register.RegisterFirstPageFragment;
import ml.amaze.design.sidebar.DiseaseFragment;
import ml.amaze.design.sidebar.NutrientsIntroduceFragment;
import ml.amaze.design.sidebar.SuggestEatFragment;
import ml.amaze.design.stepshistory.StepsHistoryFragment;
import ml.amaze.design.userinfo.User;
import ml.amaze.design.userinfo.UserInfoFragment;
import ml.amaze.design.utils.Utils;


/**
 * @author hxj
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setToolBarTitle();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //开启计步服务
        Intent intent = new Intent(this, StepService.class);
        startService(intent);
        Log.d("StepService", "开启计步服务");


    }

    /**
     * 添加搜索框
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_item_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;

    }

    /**
     * 点击搜索执行的方法
     * @param intent
     */
    @Override
    public void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        //获得搜索框里值
        String foodName=intent.getStringExtra(SearchManager.QUERY);
        System.out.println(foodName);

        FoodFragment foodFragment = new FoodFragment();
        Bundle bundle=new Bundle();
        bundle.putString("foodName",foodName);
        Utils.startFragmentOnMain(this, foodFragment, bundle);
    }







    //设置一言

    String tittle;

    private void setToolBarTitle() {
        new Thread() {
            @Override
            public void run() {
                //一言长度
                int strLen = 29;
                final TextView toolbarTv = (TextView) findViewById(R.id.toolbar_tv);
                JsonApi jsonApi = new JsonApi();

                tittle = jsonApi.getYiYan();
                try {
                    if (tittle.length() > strLen) {
                        setToolBarTitle();
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    Log.d("setToolBarTitleError", "网络异常");
                    tittle = "网络异常";
                }


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        toolbarTv.setText(tittle);
                    }
                });

            }
        }.start();

    }

    //返回键

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home) {
            Log.d("++++", "home is clicked");
            return true;
        } else if (id == R.id.menu_item_search) {
            Log.d("++++", "search is clicked");
            onSearchRequested();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //功能栏

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.


        int id = item.getItemId();

        if (id == R.id.disease) {

            Log.d("DiseaseFragment", "开启常见营养病fragment");
            DiseaseFragment diseaseFragment = new DiseaseFragment();
            Utils.startFragmentOnMain(this, diseaseFragment, null);

        } else if (id == R.id.nutrients_introduce) {

            Log.d("nutrientsIntroduce", "开启营养素介绍fragment");
            NutrientsIntroduceFragment nutrientsIntroduceFragment = new NutrientsIntroduceFragment();
            Utils.startFragmentOnMain(this, nutrientsIntroduceFragment, null);

        } else if (id == R.id.suggest_eat) {
            Log.d("SuggestEatFragment", "开启推荐摄入量fragment");

            SuggestEatFragment suggestEatFragment=new SuggestEatFragment();
            Utils.startFragmentOnMain(this, suggestEatFragment, null);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //监听按键

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //点击了返回键
            int count = getFragmentManager().getBackStackEntryCount();

            System.out.println("DietPlanctivity当前回退栈个数：" + count);
            //若arg2=1弹出回退栈中所有的fragment,若arg2=0,弹出一个fragment
            this.getFragmentManager().popBackStackImmediate(null, 1);
        }

        return super.onKeyDown(keyCode, event);
    }



    //底部四个按钮

    /**
     * 设置选中更改背景色
     */
    int searchFood = 0;
    int dietPlan = 1;
    int pedometer = 2;
    int stepsHistory = 3;
    int userInfo = 4;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            //LinearLayout llSearchfood = (LinearLayout) findViewById(R.id.main_ll_searchfood);
            LinearLayout llDietplan = (LinearLayout) findViewById(R.id.main_ll_dietplan);
            LinearLayout llPedometer = (LinearLayout) findViewById(R.id.main_ll_pedometer);
            LinearLayout llStepshistory = (LinearLayout) findViewById(R.id.main_ll_stepshistory);
            LinearLayout llUserinfo = (LinearLayout) findViewById(R.id.main_ll_userinfo);
            /*if (msg.arg1 == searchFood) {
                //llSearchfood.setBackgroundColor(getResources().getColor(R.color.tab_selected_bg));
                llDietplan.setBackgroundColor(getResources().getColor(R.color.background));
                llPedometer.setBackgroundColor(getResources().getColor(R.color.background));
                llStepshistory.setBackgroundColor(getResources().getColor(R.color.background));
                llUserinfo.setBackgroundColor(getResources().getColor(R.color.background));
            } else */
                if (msg.arg1 == dietPlan) {
                //llSearchfood.setBackgroundColor(getResources().getColor(R.color.background));
                llDietplan.setBackgroundColor(getResources().getColor(R.color.tab_selected_bg));
                llPedometer.setBackgroundColor(getResources().getColor(R.color.background));
                llStepshistory.setBackgroundColor(getResources().getColor(R.color.background));
                llUserinfo.setBackgroundColor(getResources().getColor(R.color.background));
            } else if (msg.arg1 == pedometer) {
                //llSearchfood.setBackgroundColor(getResources().getColor(R.color.background));
                llDietplan.setBackgroundColor(getResources().getColor(R.color.background));
                llPedometer.setBackgroundColor(getResources().getColor(R.color.tab_selected_bg));
                llStepshistory.setBackgroundColor(getResources().getColor(R.color.background));
                llUserinfo.setBackgroundColor(getResources().getColor(R.color.background));

            } else if (msg.arg1 == stepsHistory) {
                //llSearchfood.setBackgroundColor(getResources().getColor(R.color.background));
                llDietplan.setBackgroundColor(getResources().getColor(R.color.background));
                llPedometer.setBackgroundColor(getResources().getColor(R.color.background));
                llStepshistory.setBackgroundColor(getResources().getColor(R.color.tab_selected_bg));
                llUserinfo.setBackgroundColor(getResources().getColor(R.color.background));

            } else if (msg.arg1 == userInfo) {
                //llSearchfood.setBackgroundColor(getResources().getColor(R.color.background));
                llDietplan.setBackgroundColor(getResources().getColor(R.color.background));
                llPedometer.setBackgroundColor(getResources().getColor(R.color.background));
                llStepshistory.setBackgroundColor(getResources().getColor(R.color.background));
                llUserinfo.setBackgroundColor(getResources().getColor(R.color.tab_selected_bg));
            }
            super.handleMessage(msg);
        }
    };

    /*
    public void searchFood(View view) {
        Message message = Message.obtain();
        message.arg1 = searchFood;
        handler.sendMessage(message);
        Log.d("cookMeal", "开启做饭activity");
        //使用fragment代替activity
        //开启新activity
//        Intent i = new Intent(this, FoodActivity.class);
//        startActivity(i);
        FoodFragment foodFragment = new FoodFragment();


        Utils.startFragmentOnMain(this, foodFragment, null);
    }
*/

    public void dietPlan(View view) {
        Message message = Message.obtain();
        message.arg1 = dietPlan;
        handler.sendMessage(message);
        //保证用户注册
        User user = User.getUserInstance(this);
        if (user == null) {
            Utils.showToast(this, "当前功能不可用，请先在 我的信息 注册您的个人信息,这并不会上传您的任何信息");
        } else {
            double demandEnergy = user.getDemandEnergy();
            //把所需能量传入fragment
            Log.d("MainActivity", "开启饮食计划Activity");
            //使用fragment代替activity
//            Intent i = new Intent(this, DietPlanActivity.class);
//            i.putExtra("DemandEnergy", demandEnergy);
//            startActivity(i);

            view.setBackgroundColor(getResources().getColor(R.color.tab_selected_bg));
            Bundle bundle = new Bundle();
            bundle.putDouble("DemandEnergy", demandEnergy);
            DietPlanFragment DietPlanFragment = new DietPlanFragment();
            Utils.startFragmentOnMain(this, DietPlanFragment, bundle);
        }
    }

    public void pedometer(View view) {
        Message message = Message.obtain();
        message.arg1 = pedometer;
        handler.sendMessage(message);
        Log.d("mainActivity", "开启计步Fragment");
        //开启新activity
        PedometerFragment pedometerFragment = new PedometerFragment();
        view.setBackgroundColor(getResources().getColor(R.color.tab_selected_bg));
        Utils.startFragmentOnMain(this, pedometerFragment, null);
    }

    public void stepsHistory(View view) {
        Message message = Message.obtain();
        message.arg1 = stepsHistory;
        handler.sendMessage(message);
        //保证用户注册
        User user = User.getUserInstance(this);
        if (user == null) {
            Utils.showToast(this, "当前功能不可用，请先在 我的信息 注册您的个人信息,这并不会上传您的任何信息");
        } else {
            //开启我的运动 fragment页面
            Log.d("mainActivity", "开启StepsHistoryFragment)");
            StepsHistoryFragment stepsHistoryFragment = new StepsHistoryFragment();
            Utils.startFragmentOnMain(this, stepsHistoryFragment, null);

        }
        view.setBackgroundColor(getResources().getColor(R.color.tab_selected_bg));
    }

    public void userInfo(View view) {
        Message message = Message.obtain();
        message.arg1 = userInfo;
        handler.sendMessage(message);
        //保证用户注册
        view.setBackgroundColor(getResources().getColor(R.color.tab_selected_bg));
        User user = User.getUserInstance(this);
        if (user == null) {
            //开启注册页面
            Log.d("setting", "开启RegisterActivity");
            RegisterFirstPageFragment registerFirstPageFragment = new RegisterFirstPageFragment();
            Utils.startFragmentOnMain(this, registerFirstPageFragment, null);

        } else {
            Log.d("setting", "开启UserInfoFragment");
            //开启用户信息页面
            UserInfoFragment userInfoFragment = new UserInfoFragment();
            //传递user对象
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", user);
            Utils.startFragmentOnMain(this, userInfoFragment, bundle);
        }
    }


}


