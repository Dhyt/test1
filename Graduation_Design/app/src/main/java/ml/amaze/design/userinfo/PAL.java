package ml.amaze.design.userinfo;

/**
 *
 * @author hxj
 * @date 2017/6/20 0020
 */
public class PAL {
    /*
 中国成人活动水平分级
 活动水平       职业工作时间分配                工作内容举例                   personInfo.PAL

 轻              75%时间坐或站立        办公室工作、修理电器钟表、       1.55（男）    1.56（女）
                 25%时间站着活动          售货员、讲课等

中               25%时间坐或站立           学生日常活动、机动车驾驶    1.78        1.64
                 75%时间站着活动          电工安装、金工切割等


重              40%时间坐或站立            非机械化农业劳动、炼钢、    2.10        1.82
                60%时间特殊职业活动         舞蹈、体育、运动等

      */

    private double pal;
    private String example;

     PAL(int workLevel, String gender) {
        String man="男";
        switch(workLevel){
            case 1:
                if(man.equals(gender)){
                    this.pal=1.55;

                }else{
                    this.pal=1.56;
                }
                this.example ="办公室工作、修理电器钟表、售货员、讲课等";
                break;
            case 2:
                if(man.equals(gender)){
                    this.pal=1.78;

                }else{
                    this.pal=1.64;
                }
                this.example ="学生日常活动、机动车驾驶、电工安装、金工切割等";
                break;
            case 3:
                if(man.equals(gender)){
                    this.pal=2.10;

                }else{
                    this.pal=1.82;
                }
                this.example ="非机械化农业劳动、炼钢、舞蹈、体育、运动等";
                break;
            default:
                    break;
        }


    }

     double getPal() {
        return pal;
    }

    public String getExample() {
        return example;
    }
}
