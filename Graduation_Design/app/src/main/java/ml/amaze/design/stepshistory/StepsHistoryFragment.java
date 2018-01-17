package ml.amaze.design.stepshistory;

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

import ml.amaze.design.bean.DaoSession;
import ml.amaze.design.bean.SameAsFood;
import ml.amaze.design.bean.StepsBean;
import ml.amaze.design.bean.StepsBeanDao;
import ml.amaze.design.database.GreenDaoUtils;
import ml.amaze.design.R;
import ml.amaze.design.userinfo.Tools;
import ml.amaze.design.userinfo.User;

/**
 *
 * @author hxj
 * @date 2017/12/17 0017
 */

public class StepsHistoryFragment extends Fragment {
    List<StepsBean> list;
    User user;
    SameAsFood food;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.steps_history_mian, null);

        ListView stepsHistoryLv = (ListView) view.findViewById(R.id.steps_history_lv);
        DaoSession daoSession = GreenDaoUtils.getDaoSessionInstance(getActivity());
        StepsBeanDao stepsBeanDao = daoSession.getStepsBeanDao();

        //创建相当于某食物对象
        food = new SameAsFood();


        //拿到原始数据
        list = stepsBeanDao.queryBuilder().build().list();

        user = User.getUserInstance(getActivity());
        stepsHistoryLv.setAdapter(new MyAdapter());
        return view;
    }


    public class MyAdapter extends BaseAdapter {

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

        @SuppressLint("DefaultLocale")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(getActivity(), R.layout.steps_history_items, null);
            } else {
                view = convertView;
            }
            TextView tvDate = (TextView) view.findViewById(R.id.steps_history_items_date);
            TextView tvSteps = (TextView) view.findViewById(R.id.steps_history_items_steps);
            TextView tvEnergy = (TextView) view.findViewById(R.id.steps_history_items_exercise_energy);
            TextView tvSameAs = (TextView) view.findViewById(R.id.steps_history_items_same_as);
            TextView tvSameAsFood = (TextView) view.findViewById(R.id.steps_history_items_same_as_food);
            StepsBean stepsBean = list.get(position);
            String date = stepsBean.getDate();
            String week = stepsBean.getWeek();
            int steps = stepsBean.getSteps();
            double energy = Tools.calculateExerciseEnery(user,steps);


            tvDate.setText(String.format("%s(%s)", date, week));
            tvSteps.setText(String.format("%d步", steps));
            tvEnergy.setText(String.format("%skcal", energy));

            SameAsFood food = StepsHistoryFragment.this.food.getFood((int) energy);
            if(food!=null){
                tvSameAsFood.setText(String.format("%s%s", food.getCount(), food.getName()));
            }else {
                tvSameAs.setText("");
            }


            return view;
        }
    }

    @Override
    public void onStop() {
//
        super.onStop();
    }

    @Override
    public void onResume() {


        super.onResume();
    }
    //使用图线方式太丑，决定还是使用表格
    /*
    SplineChart03View splineChart03View;

    private void init(List<StepsBean> list) {

        //设置横轴
        LinkedList<String> labels = initLabelsByDate(list);
        //设置纵轴
        LinkedList<SplineData> charData = initDataSourceByDate(list);
        splineChart03View = new SplineChart03View(getActivity(),labels,charData);


        //设置显示页面
        FrameLayout content = (FrameLayout)view.findViewById(R.id.steps_history_fl);

        //缩放控件放置在FrameLayout的上层，用于放大缩小图表
        FrameLayout.LayoutParams frameParm = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        frameParm.gravity = Gravity.BOTTOM | Gravity.RIGHT;


        //图表显示范围在占屏幕大小的90%的区域内
        DisplayMetrics dm =view. getResources().getDisplayMetrics();
        int scrWidth = (int) (dm.widthPixels * 0.9);
        int scrHeight = (int) (dm.heightPixels * 0.9);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                scrWidth, scrHeight);

        //居中显示
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        //图表view放入布局中，也可直接将图表view放入Activity对应的xml文件中
        final RelativeLayout chartLayout = new RelativeLayout(getActivity());

        chartLayout.addView(splineChart03View, layoutParams);

        //增加控件
        ((ViewGroup) content).addView(chartLayout);
        //((ViewGroup) content).addView(mZoomControls);
        //setContentView(content);
        //放大监听
        //  mZoomControls.setOnZoomInClickListener(new OnZoomInClickListenerImpl());
        //缩小监听
        //  mZoomControls.setOnZoomOutClickListener(new OnZoomOutClickListenerImpl());


        //splineChart03View = (SplineChart03View)findViewById(R.id.halfcircle_view);


    }


    //设置纵轴数据（list为原始数据） 按周显示步数算法太恶心，没细算，决定把近十天数据按日期显示出来，只画一条线
    private LinkedList<SplineData> initDataSourceByDate(List<StepsBean> list) {
        int days=10;
//三周的数据集
        List<PointD> linePoint1 = new ArrayList<>();//上上周
       String lineKey="";
        int len=list.size();
        if(len>days-1){//至少有十五天的数据
            for (int i = 0; i < 10; i++) {
                linePoint1.add(new PointD(i * 10d, list.get(len-1-days+1+i).getSteps()));//上上周
            }
            lineKey="最近"+days+"天的步数";
        } else if(len<days) {//不到十五天的数据
            for (int i = 0; i < len; i++) {
                linePoint1.add(new PointD(i * 10d, list.get(i).getSteps()));
            }
            lineKey=len+"天的步数";
        }

        SplineData dataSeries1 = new SplineData(lineKey, linePoint1,
                Color.rgb(54, 141, 238));


        //设置线宽
        dataSeries1.getLinePaint().setStrokeWidth(2);
        dataSeries1.setLabelVisible(true);

        LinkedList<SplineData> chartData =new LinkedList<>();

        //设定数据源
        chartData.add(dataSeries1);
        return chartData;
    }

    //设置横轴数据
     private LinkedList<String> initLabelsByDate(List<StepsBean> list) {

        int days=10;
        LinkedList<String> labels = new LinkedList<>();

        int len=list.size();
        if(len>days-1){//至少有10天的数据

            //只放开头和结尾两个日期
            labels.add(list.get(len-days).getDate());
            labels.add(list.get(len-1).getDate());

        } else if(len<days) {//不到10天的数据
            //只放开头和结尾两个日期
            labels.add(list.get(0).getDate());
            labels.add(list.get(len-1).getDate());

        }

        return labels;

    }

 */
  /*
    按周显示步数算法太恶心，没细算，决定把近十五天数据按日期显示出来

    //设置纵轴数据（list为原始数据）（以今天为最后一天向前推七天：如周三, 周四, 周五, 周六, 周日, 周一, 周二）
    private LinkedList<SplineData> initDataSourceFromThisDay(List<StepsBean> list) {

//三周的数据集
        List<PointD> linePoint1 = new ArrayList<>();//上上周
        List<PointD> linePoint2 = new ArrayList<>();//上周
        List<PointD> linePoint3 = new ArrayList<>();//本周
        int len=list.size();
        if(len>20){//至少有三周数据
            for (int i = 0; i < 7; i++) {
                linePoint1.add(new PointD(i * 10d, list.get(len-21+i).getSteps()));
                linePoint2.add(new PointD(i * 10d, list.get(len -14+i).getSteps()));
                linePoint3.add(new PointD(i * 10d, list.get(len -7+i).getSteps()));
            }
        }
        else if(len>13&&len<21){//只有2周到3周数据
            for (int i = 0; i < 7; i++) {
                linePoint1.add(new PointD(i * 10d, list.get(len -14+i).getSteps()));
                linePoint2.add(new PointD(i * 10d, list.get(len -7+i).getSteps()));

            }
            for(int i = 0; i < len-14; i++){
                linePoint3.add(new PointD(i * 10d, list.get(len -14+i).getSteps()));
            }
        } else if(len>6&&len<14) {//只有1周到2周数据
            for (int i = 0; i < 7; i++) {
                linePoint1.add(new PointD(i * 10d, list.get(i).getSteps()));
            }
            for(int i = 0; i < list.size()-7; i++){
                linePoint2.add(new PointD(i * 10d, list.get(i + 7).getSteps()));

            }
        }else if(len>6&&len<14) {//只有0周到1周数据
            for(int i = 0; i < list.size(); i++){
                linePoint1.add(new PointD(i * 10d, list.get(i).getSteps()));
            }
        }

        SplineData dataSeries1 = new SplineData("the week befor last", linePoint1,
                Color.rgb(54, 141, 238));

        SplineData dataSeries2 = new SplineData("last week", linePoint2,
                Color.rgb(255, 165, 132));

        SplineData dataSeries3 = new SplineData("this week", linePoint3,
                Color.rgb(84, 206, 231));

        //设置线宽
        dataSeries1.getLinePaint().setStrokeWidth(2);
        dataSeries1.setLabelVisible(true);
        dataSeries2.setDotStyle(XEnum.DotStyle.RING);
        dataSeries3.getDotPaint().setColor(Color.rgb(75, 166, 51));
        dataSeries3.getPlotLine().getPlotDot().setRingInnerColor(Color.rgb(123, 89, 168));

        LinkedList<SplineData> chartData =new LinkedList<>();

        //设定数据源
        chartData.add(dataSeries1);
        chartData.add(dataSeries2);
        chartData.add(dataSeries3);
        return chartData;
    }

    //设置横轴数据（list中已经处理好，只有21个(三周)数据）（以今天为最后一天向前推七天：如周三, 周四, 周五, 周六, 周日, 周一, 周二）
    private LinkedList<String> initLabelsFromThisDay(List<StepsBean> list) {
        LinkedList<String> labels = new LinkedList<>();
        int len=list.size();
        if(len>6){//如果至少有七条数据
            for (int i = 0; i < 7; i++) {
                labels.add(list.get(len-7+i).getWeek());
            }
        }else {//如果没有七条数据
            for (int i = 0; i < len; i++) {
                labels.add(list.get(i).getWeek());
            }

        }
        for (String s:labels){
            System.out.println(s);
        }

        return labels;

    }

    //设置纵轴数据（list为原始数据）（以周天为最后一天向前推七天，周一, 周二，周三, 周四, 周五, 周六, 周日,）
    private LinkedList<SplineData> initDataSourceFromSunDay(List<StepsBean> list) {


//三周的数据集
        List<PointD> linePoint1 = new ArrayList<>();//上上周
        List<PointD> linePoint2 = new ArrayList<>();//上周
        List<PointD> linePoint3 = new ArrayList<>();//本周
        int len=list.size();
        String week = list.get(len - 1).getWeek();

            int j=0;
            boolean flag=true;
            while (flag){
                if(list.get(len-1-j).getWeek().equals("周日")){
                    flag=false;
                    break;
                }
                j++;
            }//循环结束后倒数J+1个是周日


        Long weekId = list.get(len - 1).getId();
        if(len>14){//至少有三周数据
            for (int i = 0; i < 7; i++) {
                linePoint1.add(new PointD(i * 10d, list.get(len-j-14+i).getSteps()));//上上周
                linePoint2.add(new PointD(i * 10d, list.get(len-j-7+i).getSteps()));//上周

            }
            for (int i = 0; i < j; i++) {
                linePoint3.add(new PointD(i * 10d, list.get(len -j+i).getSteps()));//这周
            }
        }
        else if(len>13&&len<21){//只有2周到3周数据
            for (int i = 0; i < 7; i++) {
                linePoint1.add(new PointD(i * 10d, list.get(len -14+i).getSteps()));
                linePoint2.add(new PointD(i * 10d, list.get(len -7+i).getSteps()));

            }
            for(int i = 0; i < len-14; i++){
                linePoint3.add(new PointD(i * 10d, list.get(len -14+i).getSteps()));
            }
        } else if(len>6&&len<14) {//只有1周到2周数据
            for (int i = 0; i < 7; i++) {
                linePoint1.add(new PointD(i * 10d, list.get(i).getSteps()));
            }
            for(int i = 0; i < list.size()-7; i++){
                linePoint2.add(new PointD(i * 10d, list.get(i + 7).getSteps()));

            }
        }else if(len>6&&len<14) {//只有0周到1周数据
            for(int i = 0; i < list.size(); i++){
                linePoint1.add(new PointD(i * 10d, list.get(i).getSteps()));
            }
        }

        SplineData dataSeries1 = new SplineData("the week befor last", linePoint1,
                Color.rgb(54, 141, 238));

        SplineData dataSeries2 = new SplineData("last week", linePoint2,
                Color.rgb(255, 165, 132));

        SplineData dataSeries3 = new SplineData("this week", linePoint3,
                Color.rgb(84, 206, 231));

        //设置线宽
        dataSeries1.getLinePaint().setStrokeWidth(2);
        dataSeries1.setLabelVisible(true);
        dataSeries2.setDotStyle(XEnum.DotStyle.RING);
        dataSeries3.getDotPaint().setColor(Color.rgb(75, 166, 51));
        dataSeries3.getPlotLine().getPlotDot().setRingInnerColor(Color.rgb(123, 89, 168));

        LinkedList<SplineData> chartData =new LinkedList<>();

        //设定数据源
        chartData.add(dataSeries1);
        chartData.add(dataSeries2);
        chartData.add(dataSeries3);
        return chartData;
    }

    //设置横轴数据（list中已经处理好，只有21个(三周)数据）（以周天为最后一天向前推七天，周一, 周二，周三, 周四, 周五, 周六, 周日,）
    private LinkedList<String> initLabelsFromFromSunDay() {
        LinkedList<String> labels = new LinkedList<>();
            labels.add("周一");
            labels.add("周二");
            labels.add("周三");
            labels.add("周四");
            labels.add("周五");
            labels.add("周六");
            labels.add("周日");
        return labels;

    }

*/

}
