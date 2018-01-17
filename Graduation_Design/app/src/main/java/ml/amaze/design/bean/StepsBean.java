package ml.amaze.design.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;

import ml.amaze.design.utils.Utils;

/**
 *
 * @author hxj
 * @date 2017/12/15 0015
 */
@Entity
public class StepsBean {
    @Id
    private Long id;

    @Property(nameInDb = "date")
    @Unique private String date;
    @Property(nameInDb = "week")
    private String week;
    @Property(nameInDb = "steps")
    private int steps;

    public StepsBean(int steps) {
        this.id = null;
        this.date = Utils.getFormatDate() ;
        this.week=Utils.getFormatWeek();
        this.steps = steps;

    }

    public StepsBean(Long id, int steps) {
        this.id = id;
        this.date = Utils.getFormatDate();
        this.week=Utils.getFormatWeek();
        this.steps = steps;
    }

    @Generated(hash = 695882324)
    public StepsBean(Long id, String date, String week, int steps) {
        this.id = id;
        this.date = date;
        this.week = week;
        this.steps = steps;
    }

    @Generated(hash = 1068342147)
    public StepsBean() {
    }

    @Override
    public String toString() {
        return "StepsBean{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", week='" + week + '\'' +
                ", steps=" + steps +
                '}';
    }





    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSteps() {
        return this.steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public String getWeek() {
        return this.week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
