package ml.amaze.design.receipe.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import ml.amaze.design.R;
import ml.amaze.design.bean.Entity1;
import ml.amaze.design.receipe.DetailsActivity;
import ml.amaze.design.receipe.GetData;

/**
 * Created by hxj on 2017/12/28 0028.
 */

public class FoodFragment extends Fragment {

        //线程间通信

        @SuppressLint("HandlerLeak")
        private Handler handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                entity1 = (Entity1) msg.obj;
                foods = entity1.getFoods();
                //设置点击监听
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        System.out.println("第" + position + "个被点击！");
                        //获得被选中的食物code并通过暴露的方法传入下一fragment
                        code = foods.get(position).getCode();
                        Intent i = new Intent(getActivity(), DetailsActivity.class);
                        i.putExtra("code", code);
                        startActivity(i);
                    }
                });
                lv.setAdapter(new MyAdapter());
            }
        };

        ListView lv;
        List<Entity1.FoodsBean> foods;
        ImageView searchItemIv;
        Entity1 entity1;
        String code;
        private EditText et;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view;
        view = inflater.inflate(R.layout.recipepart_food, null);


/*
            et = (EditText)view. findViewById(R.id.food_et_foodName);
            //把edittext的回车变成搜索键
            et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                /**
                 * 参数说明
                 * @param v 被监听的对象
                 * @param actionId  动作标识符,如果值等于EditorInfo.IME_NULL，则回车键被按下。
                 * @param event    如果由输入键触发，这是事件；否则，这是空的(比如非输入键触发是空的)。
                 * @return 返回你的动作

                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        Log.i("FoodAvtivity", "搜索操作执行");
                        String foodName = et.getText().toString();
                        System.out.println(foodName);
                        //从网络搜索食物
                        searchByNet(foodName);
                    }
                    return false;
                }
            });
*/
        Bundle bundle = getArguments();
        String foodName=bundle.getString("foodName");
        searchByNet(foodName);
            lv = (ListView) view.findViewById(R.id.food_lv_search);
            // ListView滑动时触发的事件
            lv.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    switch (scrollState) {
                        case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                            // 当ListView处于滑动状态时，停止加载图片，保证操作界面流畅
                            Glide.with(getActivity()).pauseRequests();
                            break;
                        case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                            // 当ListView处于静止状态时，继续加载图片
                            Glide.with(getActivity()).resumeRequests();
                            break;
                        default:
                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });
        return view;

        }

        private class MyAdapter extends BaseAdapter {
            @Override
            public int getCount() {
                return foods.size();
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
                    view = View.inflate(getActivity(), R.layout.recipepart_items_entity1, null);

                } else {
                    view = convertView;
                }
                //get widget

                searchItemIv = (ImageView) view.findViewById(R.id.search_item_iv);
                TextView searchItemTvTittle = (TextView) view.findViewById(R.id.search_item_tv_tittle);
                TextView searchItemTvContent = (TextView) view.findViewById(R.id.search_item_tv_content);
                TextView searchItemTvCalory = (TextView) view.findViewById(R.id.search_item_tv_calory);

                //get info
                Entity1.FoodsBean bean = foods.get(position);
                String path = bean.getThumb_image_url();
                String tittle = bean.getName();
                String content = bean.getHealth_light() + "";
                String calory = bean.getCalory();
                //set info
                searchItemTvTittle.setText(tittle);
                searchItemTvContent.setText(content);
                searchItemTvCalory.setText(calory);

                //set pic
                //Glide.with(getApplicationContext()).load(path).into(searchItemIv);
                Glide.with(getActivity()).load(path).override(100, 100).fitCenter().into(searchItemIv);

            /*使用Glide包代替缓存至本地加载图片的方法
            File file = new File(getCacheDir().getAbsolutePath() + "/" + "mid"+ path.substring(path.lastIndexOf("/")+1));
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            Log.d("bitmapPath", file.getAbsolutePath());
            searchItemIv.setImageBitmap(bitmap);
*/
                return view;
            }
        }


        private void searchByNet(final String name) {


            try {

                new Thread() {
                    @Override
                    public void run() {
                        GetData getData = new GetData();

                        try {
                            Message msg = Message.obtain();
                            //return a json
                            String json = getData.getEntity1(name);
                            entity1 = new Gson().fromJson(json, Entity1.class);
                            Log.d("jsonEntity1", json);
                            msg.obj = entity1;
                            //缓存图片
                            //cacheBit(entity1);
                            //发送消息
                            handler.sendMessage(msg);
                            Log.d("sendMessage", "send");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 缓存图片至本地
         *
         * @param entity1
         */
        public void cacheBit(Entity1 entity1) {
            //缓存具体图片
            final List<Entity1.FoodsBean> list = entity1.getFoods();
            String path;
            File file;
            for (Entity1.FoodsBean food : list) {
                try {
                    path = food.getThumb_image_url();
                    file = new File(getActivity().getCacheDir().getAbsolutePath() + "/" + "mid" + path.substring(path.lastIndexOf("/") + 1));


                    if (!file.exists()) {
                        //若图片不存在，则开始缓存
                        Log.d("filePath", file.getAbsolutePath());
                        URL url = new URL(path);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.setConnectTimeout(5000);
                        int code = conn.getResponseCode();
                        if (code == 200) {
                            InputStream in = conn.getInputStream();
                            FileOutputStream fos = new FileOutputStream(file);
                            int len;
                            byte[] buf = new byte[1024];
                            while ((len = in.read(buf)) != -1) {
                                fos.write(buf, 0, len);
                            }
                            fos.close();
                            in.close();
                            Log.d("cache", "缓存图片");
                        }
                    } else {
                        Log.d("cache", "图片已存在");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
}
