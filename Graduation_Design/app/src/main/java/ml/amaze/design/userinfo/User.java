package ml.amaze.design.userinfo;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 *
 * @author hxj
 * @date 2017/6/19 0019
 *
 * @name 姓名                                editText
 * @gender 性别                                checkbox
 * @age 年龄                                singlePicker
 * @height 身高(cm)                            TapeView
 * @weight 体重(Kg)                            TapeView
 * @waistline 腰围(cm)                            TapeView
 * @workLevel 体力活动等级  1:轻  2中 3重            checkbox
 * @sleepTime 休息时间                            TapeView
 * 以下属性通过计算得来，故只有get方法
 * @pal 中国成人活动水平分级
 * @BM 基础代谢
 * @BMR 基础代谢率（BMR）
 * @BFR 体脂率
 * @BFRLevel 体脂率等级
 * @BMI 体质指数
 * @BMILevel 体质指数等级
 * @demandEnergy 日需能量
 * @
 * @
 *
 */
public class User implements Serializable {

    private int id;
    private String name;
    private String gender;
    private int age;
    private double height;
    private double weight;
    private double waistline;
    private int workLevel;
    private double sleepTime;
    //一下属性通过计算得来，故只有get方法

    private PAL pal = null;
    private double BM;
    private double BMR;
    private double BFR;
    private String BFRLevel;
    private double BMI;
    private String BMILevel;
    private double demandEnergy;

    public static User user=null;

    public User() {
    }

    //获取user单例

    public static User getUserInstance(Context context){
        if(user==null){
            //如果user对象在内存中不存在
            if(isExistInDB(context)){
                //若user在文件中存在
                return user;
            }else {
                //若user在文件中不存在
                return null;
            }
        }else {
            //如果user对象在内存中存在
            return user;
        }


    }

    //判断是否在数据库中存在

    private static boolean isExistInDB(Context context){

        //保证用户注册
        SharedPreferences config = context.getSharedPreferences("config", 0);
        String json = config.getString("user", null);
        Log.d("setting", "读取的json为：" + json);
        if (json == null) {
            //若不存在则返回false
            return false;
        } else {//若存在，则创建个user
            user = new Gson().fromJson(json, User.class);
            return true;
        }
    }

     User(String name, String gender, int age, double height, double weight, double waistline, int workLevel, double sleepTime) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.waistline = waistline;
        this.workLevel = workLevel;
        this.sleepTime = sleepTime;
    }






    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

     double getBM() {
        return Tools.calculateBM(this);
    }

     double getBMR() {
        return Tools.calculateBMR(this);
    }

     double getBFR() {
        return Tools.calculateBFR(this);
    }

     double getBMI() {
        return Tools.calculateBMI(this);
    }

    public double getDemandEnergy() {
        return Tools.calculateEnergy(this);
    }

     String getBMILevel() {
        return Tools.calculateBMILevel(this);
    }

     String getBFRLevel() {
        return Tools.calculateBFRLevel(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

     String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

     double getWaistline() {
        return waistline;
    }

    public void setWaistline(double waistline) {
        this.waistline = waistline;
    }

     int getWorkLevel() {
        return workLevel;
    }

    public void setWorkLevel(int workLevel) {
        this.workLevel = workLevel;
    }

     PAL getPal() {
        return new PAL(workLevel, gender);
    }


     double getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(double sleepTime) {
        this.sleepTime = sleepTime;
    }


}
