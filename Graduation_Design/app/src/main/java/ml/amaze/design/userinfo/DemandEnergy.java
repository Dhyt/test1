package ml.amaze.design.userinfo;

/**
 * @author hxj
 */
public class DemandEnergy {

    public static void main(String[] args) {

        User p = new User("hyt", "男", 22, 173.0f, 65.0f, 76.0f,
                2, 7);

        p.getBFR();
        p.getBMI();
        System.out.println("日需能量为" + Tools.calculateEnergy(p));
    }
}
