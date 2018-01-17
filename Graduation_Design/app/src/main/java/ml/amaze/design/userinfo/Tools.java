package ml.amaze.design.userinfo;

import ml.amaze.design.utils.Utils;

/**
 *
 * @author hxj
 * @date 2017/6/19 0019
 * 计算各种人体信息
 */
public class Tools {

    /**
     * 计算体脂率BFR
     */
     static double calculateBFR(User user){

        /*
        女性的身体脂肪公式
        参数a = 腰围-公分(腰部的周长) x 0.74
        参数b = (总体重-公斤 x 0.082) + 34.89
        身体脂肪总重量-公斤 = a - b
        身体脂肪百分比= (身体脂肪总重量÷ 体重) x 100%

        男性的身体脂肪公式
        参数a = 腰围-公分 x 0.74
        参数b =  (体重-公斤 x 0.082) + 44.74
        身体脂肪总重量-公斤= a - b
        体脂率(身体脂肪百分比) = (身体脂肪总重量÷ 体重) x 100%

         */
        String gender=user.getGender();
        double weight=user.getWeight();
        double waistline=user.getWaistline();

        double argB;
        double argA=waistline*0.74;
        System.out.println("argA:"+argA);

        String man="男";
        if(man.equals(gender)){
            argB=weight*0.082+44.74;
            System.out.println("argB:"+argB);
        }else{
            argB=weight*0.082+34.89;
            System.out.println("argB:"+argB);
        }

        double BFR=(argA-argB)/weight;
        System.out.println("BFR为："+BFR);
        return Utils.setDot(BFR,2);
    }

    /**
     * 计算体脂率BFR等级
     * @param user
     * @return
     */
     static String calculateBFRLevel(User user){
        /*
            BMI :           偏瘦        健硕       健康       超重      肥胖

            男：          2-4%            6-13%    14-17%     18-25%      25%
            女：       10-12%            14-20%    21-24%     25-31%      32%



        */

        double bfr =user.getBFR();
        if("男".equals(user.getGender())){
            if(bfr<0.05){
                return "偏瘦";
            }else if(bfr<0.13){
                return "健硕 ";
            }else if(bfr<0.17){
                return "健康";
            }else if(bfr<0.25){
                return "超重";
            }else {
                return "肥胖";
            }
        }else {
            if(bfr<0.13){
                return "偏瘦";
            }else if(bfr<0.20){
                return "健硕 ";
            }else if(bfr<0.24){
                return "健康";
            }else if(bfr<0.32){
                return "超重";
            }else {
                return "肥胖";
            }
        }


    }

    /**
     * 计算体质指数（BMI）
     * @param user
     * @return
     */
     static double calculateBMI(User user){
        /*
            体质指数（BMI）=体重（kg）÷身高^2（m）
            EX：70kg÷（1.75×1.75）=22.86
        */
        double weight=user.getWeight();
        double height=user.getHeight();

        double BMI =weight*10000/(height*height);
        System.out.println("BMI为："+BMI);
        return Utils.setDot(BMI,2);
    }

    /**
     * 计算体质指数等级（BMI）
     * @param user
     * @return
     */
     static String calculateBMILevel(User user){
        /*
            BMI :           18.5         23.9        27.9
                    体重过低     体重正常       超重           肥胖
        */

        double BMI =user.getBMI();
        if(BMI<18.5){
            return "体重过低";
        }else if(BMI<23.9){
            return "体重正常 ";
        }else if(BMI<27.9){
            return "超重";
        }else {
            return "肥胖";
        }

    }

    /**
     * 计算基础代谢（BM）
     * @param user
     * @return
     */
     static double calculateBM(User user){
         /*
        1.	基础代谢（BM）消耗能量的计算方法：
        B.	Harris和Benedict提出了下列公式，可根据年龄、身高和体重直接计算基础代谢能量消耗：
            男：BM（kcal）= 66 + 13.7×体重（kg）+ 5.0×身高（cm）- 6.8×年龄（y）
            女：BM（kcal）= 655 + 9.5×体重（kg）+ 1.8×身高（cm）- 4.7×年龄（y）
         */
        String gender = user.getGender();
        double weight = user.getWeight();
        double height = user.getHeight();
        int age = user.getAge();
        double BM;
        String man="男";
        if(man.equals(gender)){
            BM=66+13.7*weight+5.0*height-6.8*age;
        }else{
            BM=655+9.5*weight+1.8*height-4.7*age;
        }
        System.out.println("BM为"+BM);
        return Utils.setDot(BM,2);
    }

    /**
     * 计算基础代谢率（BMR）
     * @param user
     * @return
     */
     static double calculateBMR(User user){

        /*
        2.	BMR（ kcal/(kg.h)） = BM ÷ 体重（kg）÷24 (h)
         */
        double weight = user.getWeight();
        double bm = calculateBM(user);
        double bmr=bm/(weight*24);
        System.out.println("BMR为"+bmr);
        return Utils.setDot(bmr,2);
    }

    /**
     * 计算日需能量(kcal)
     * @param user
     * @return
     */
     static double calculateEnergy(User user){

        /*
        成人每日需要的热量 
        男性 ： 9250- 10090 千焦耳 女性： 7980 - 8820 千焦耳
         */
        double weight = user.getWeight();
        double PAL = user.getPal().getPal();
        double sleepTime = user.getSleepTime();
        /*
        1.	基础代谢（BM）消耗能量的计算方法：
         */
        double BM = calculateBM(user);
        /*
        2.	BMR（ kcal/(kg.h)） = BM ÷ 体重（kg）÷24 (h)
         */
        double BMR=calculateBMR(user);
        System.out.println("BMR为"+BMR);
        /*
        3.	夜间基础代谢消耗能量= BMR（ kcal/(kg.h)）× 体重（kg）× 夜间睡觉时间(h)
                  或= BMR（ kcal/(m2.h)）×体表面积（m2）× 夜间睡觉时间(h)
         */

        double bmrNight=BMR*weight*sleepTime;
        System.out.println("夜间基础代谢消耗能量"+bmrNight);

        /*
        4.	白天人体力活动需要能量 = BMR × 体力活动水平（personInfo.PAL）× 体力活动时间 × 体重或者体表面积
         */
//        double workTime;
//        if(age>15&&age<50){
//            workTime=8;
//        }else{
//            workTime=3;
//        }
//
//        double dayEnergyRequirments=BMR*(PAL*workTime+1.55*(24-sleepTime-workTime))*weight;
        double dayEnergyRequirments =BMR*(PAL*(24-sleepTime))*weight;
        System.out.println("白天人体力活动需要能量"+dayEnergyRequirments);
        /*
        5.	食物热效应 ≈ BM × 10%
         */
        double foodHeat=BM*0.1;

        System.out.println("5、\t食物热效应 "+foodHeat);
        //日需能量
        double demandEnergy=bmrNight+dayEnergyRequirments+foodHeat;
        System.out.println("日需能量为："+demandEnergy);
        return Utils.setDot(demandEnergy,2);
    }

    /**
     *通过步数，体重计算行走能量
     * @param user
     * @return
     */
    public static double calculateExerciseEnery(User user,int steps){
        //方法一
        //http://blog.sina.com.cn/s/blog_6277eedf0100jhe2.html，Actigraph加速度计能耗预测方程效度研究.caj
        //千卡/分钟=（净代谢当量Net METs×3.5×体重公斤）/200（毫升/公斤/分钟=升/分钟×1000÷体重公斤→
        // 升/分钟=毫升/公斤/分钟（净代谢当量Net METs×3.5）×体重公斤÷1000；
        // 因为每消耗1升的氧气，大约消耗5千卡的热量，
        // 由此可以推出：千卡/分钟=净代谢当量Net METs×3.5×体重公斤÷1000×5）
    /*
                能耗计算方法      漫走           快走        漫跑        中速跑
                实际能耗   3.14士038         4.63士0.54   7.85士ltO4       9t96士ltsl
     */

        //方法二
        //步行和日常体力活动能量消耗的推算.caj
        // 男性：步行能量消耗（kcal）=0.53*身高（cm）+0.58*体重（kg）+0.37*步频（步/min）+1.51*时间（min）-145.03
        // 女性：步行能量消耗（kcal）=0.003*身高（cm）+0.45*体重（kg）+0.16*步频（步/min）+0.39*时间（min）-12.93


        //方法三
    /*
    (1) 已知体重、时间和速度
    跑步热量（kcal）＝体重（kg）×运动时间（小时）×指数K 指数K＝30÷速度（分钟400米）
    例如：某人体重60公斤，长跑1小时，速度是3分钟400米或8公里小时，
    那么他跑步过程中消耗的热量＝60×1×303=600kcal(千卡)
     此种计算含盖了运动后由于基础代谢率提高所消耗的一部分热量，也就是运动后体温升高所产生的一部分热量。
    （2）已知体重、距离
    跑步热量（kcal）＝体重（kg）×距离（公里）×1.036
    例如：体重60公斤的人，长跑8公里，那么消耗的热量＝60×8×1.036＝497.28 kcal(千卡)
    （3）已知体重、速度和时间
    跑步热量（kcal）＝体重（kg）×运动时间（分钟）×指数K
    一小时8公里 K＝0.1355 一小时12公里 K＝0.1797 一小时15公里 K＝0.1875
    体重60公斤的人，长跑1小时，速度为8公里小时，那么消耗的热量＝60×60×0.1355＝487.8kcal(千卡)

     */
        //使用方法三（2）
        //默认步长为75cm

        double weight = user.getWeight();
        return Utils.setDot(weight*0.00075*steps,0);
    }


}
