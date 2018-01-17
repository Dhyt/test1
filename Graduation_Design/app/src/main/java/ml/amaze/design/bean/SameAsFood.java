package ml.amaze.design.bean;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author hxj
 * @date 2017/12/20 0020
 * 多少能量相当于什么食物
 */

public class SameAsFood {

    /*
    奇异果 1 个 30
        苹果 (中) 1 个 55
        瘦火腿	2 片 (60克)	70
        鸡蛋 1 只 80
        煎蛋 1 只 136
    一瓶可口可乐大概是200大卡
    白饭 1碗 (135g) 200
    烤猪扒(连肥)	1 件 (90克)	300
  方便面 1 包 (100g) 470

     */

    private String name;
    private String count;
    private int calory;


    public static List<SameAsFood> list = null;

    public List<SameAsFood> getList() {
        if (list == null) {
            list = new LinkedList<>();

            list.add(new SameAsFood("奇异果", "1个", 30));
            list.add(new SameAsFood("苹果(中)", "1个", 55));
            list.add(new SameAsFood("瘦火腿", "2片(60克)", 70));
            list.add(new SameAsFood("鸡蛋", "1个", 80));
            list.add(new SameAsFood("煎蛋", "1个", 136));
            list.add(new SameAsFood("可乐", "一瓶", 200));
            list.add(new SameAsFood("方便面", "1包", 470));
            return list;
        }else {
            return list;
        }

    }

    public void listClose() {
        list=null;
    }

    public SameAsFood getFood(int energy) {
        getList();
        int index=-1;
        for (SameAsFood s:list){
            if(energy>s.getCalory()-10){
                index++;
            }
        }
        if(index!=-1){
            return list.get(index);
        }else {
            return null;
        }
    }

    public SameAsFood() {
    }

    public SameAsFood(String name, String count, int calory) {
        this.name = name;
        this.count = count;
        this.calory = calory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public int getCalory() {
        return calory;
    }

    public void setCalory(int calory) {
        this.calory = calory;
    }
}
