package ml.amaze.design.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;

import java.text.SimpleDateFormat;
import java.util.Date;

import ml.amaze.design.utils.Utils;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author hxj
 * @date 2017/12/18 0018
 * 营养汇总，将每天摄入的能量，蛋白质，脂肪，碳水化合物存入数据库
 */

@Entity
public class NutritionSummaryBean {
    @Id
    private Long id;
    @Property(nameInDb = "date")
    @Unique
    private String date;

    @Property(nameInDb = "calory")
    private String calory;
    @Property(nameInDb = "protein")
    private String protein;
    @Property(nameInDb = "fat")
    private String fat;
    @Property(nameInDb = "carbohydrate")
    private String carbohydrate;
    @Property(nameInDb = "fiber_dietary")
    private String fiber_dietary;
    @Property(nameInDb = "vitamin_a")
    private String vitamin_a;
    @Property(nameInDb = "vitamin_c")
    private String vitamin_c;
    @Property(nameInDb = "vitamin_e")
    private String vitamin_e;
    @Property(nameInDb = "carotene")
    private String carotene;
    @Property(nameInDb = "thiamine")
    private String thiamine;
    @Property(nameInDb = "lactoflavin")
    private String lactoflavin;
    @Property(nameInDb = "niacin")
    private String niacin;
    @Property(nameInDb = "cholesterol")
    private String cholesterol;
    @Property(nameInDb = "magnesium")
    private String magnesium;
    @Property(nameInDb = "calcium")
    private String calcium;
    @Property(nameInDb = "iron")
    private String iron;
    @Property(nameInDb = "zinc")
    private String zinc;
    @Property(nameInDb = "copper")
    private String copper;
    @Property(nameInDb = "manganese")
    private String manganese;
    @Property(nameInDb = "kalium")
    private String kalium;
    @Property(nameInDb = "phosphor")
    private String phosphor;
    @Property(nameInDb = "natrium")
    private String natrium;
    @Property(nameInDb = "selenium")
    private String selenium;

    public NutritionSummaryBean() {
        this.id = null;
        this.date = getFormatDate();
        this.calory = "0";
        this.protein = "0";
        this.fat = "0";
        this.carbohydrate = "0";
        this.fiber_dietary = "0";
        this.vitamin_a = "0";
        this.vitamin_c = "0";
        this.vitamin_e = "0";
        this.carotene = "0";
        this.thiamine = "0";
        this.lactoflavin = "0";
        this.niacin = "0";
        this.cholesterol = "0";
        this.magnesium = "0";
        this.calcium = "0";
        this.iron = "0";
        this.zinc = "0";
        this.copper = "0";
        this.manganese = "0";
        this.kalium = "0";
        this.phosphor = "0";
        this.natrium = "0";
        this.selenium = "0";
    }

    public NutritionSummaryBean(NutritionSummaryBean nutritionSummaryBean, DietPlanBean dietPlanBean) {
       this.id=nutritionSummaryBean.getId();

        this.calory = Utils.setDot(Double.parseDouble(nutritionSummaryBean.getCalory()) + Double.parseDouble(dietPlanBean.getCalory()), 1) + "";
        this.protein = Utils.setDot(Double.parseDouble(nutritionSummaryBean.getProtein()) + Double.parseDouble(dietPlanBean.getProtein()), 1) + "";
        this.fat = Utils.setDot(Double.parseDouble(nutritionSummaryBean.getFat()) + Double.parseDouble(dietPlanBean.getFat()), 1) + "";
        this.carbohydrate = Utils.setDot(Double.parseDouble(nutritionSummaryBean.getCarbohydrate()) + Double.parseDouble(dietPlanBean.getCarbohydrate()), 1) + "";
        this.fiber_dietary = Utils.setDot(Double.parseDouble(nutritionSummaryBean.getFiber_dietary()) + Double.parseDouble(dietPlanBean.getFiber_dietary()), 1) + "";
        this.vitamin_a = Utils.setDot(Double.parseDouble(nutritionSummaryBean.getVitamin_a()) + Double.parseDouble(dietPlanBean.getVitamin_a()), 1) + "";
        this.vitamin_c = Utils.setDot(Double.parseDouble(nutritionSummaryBean.getVitamin_c()) + Double.parseDouble(dietPlanBean.getVitamin_c()), 1) + "";
        this.vitamin_e = Utils.setDot(Double.parseDouble(nutritionSummaryBean.getVitamin_e()) + Double.parseDouble(dietPlanBean.getVitamin_e()), 1) + "";
        this.carotene = Utils.setDot(Double.parseDouble(nutritionSummaryBean.getCarotene()) + Double.parseDouble(dietPlanBean.getCarotene()), 1) + "";
        this.thiamine = Utils.setDot(Double.parseDouble(nutritionSummaryBean.getThiamine()) + Double.parseDouble(dietPlanBean.getThiamine()), 1) + "";
        this.lactoflavin = Utils.setDot(Double.parseDouble(nutritionSummaryBean.getLactoflavin()) + Double.parseDouble(dietPlanBean.getLactoflavin()), 1) + "";
        this.niacin = Utils.setDot(Double.parseDouble(nutritionSummaryBean.getNiacin()) + Double.parseDouble(dietPlanBean.getNiacin()), 1) + "";
        this.cholesterol = Utils.setDot(Double.parseDouble(nutritionSummaryBean.getCholesterol()) + Double.parseDouble(dietPlanBean.getCholesterol()), 1) + "";
        this.magnesium = Utils.setDot(Double.parseDouble(nutritionSummaryBean.getMagnesium()) + Double.parseDouble(dietPlanBean.getMagnesium()), 1) + "";
        this.calcium = Utils.setDot(Double.parseDouble(nutritionSummaryBean.getCalcium()) + Double.parseDouble(dietPlanBean.getCalcium()), 1) + "";
        this.iron = Utils.setDot(Double.parseDouble(nutritionSummaryBean.getIron()) + Double.parseDouble(dietPlanBean.getIron()), 1) + "";
        this.zinc = Utils.setDot(Double.parseDouble(nutritionSummaryBean.getZinc()) + Double.parseDouble(dietPlanBean.getZinc()), 1) + "";
        this.copper = Utils.setDot(Double.parseDouble(nutritionSummaryBean.getCopper()) + Double.parseDouble(dietPlanBean.getCopper()), 1) + "";
        this.manganese = Utils.setDot(Double.parseDouble(nutritionSummaryBean.getManganese()) + Double.parseDouble(dietPlanBean.getManganese()), 1) + "";
        this.kalium = Utils.setDot(Double.parseDouble(nutritionSummaryBean.getKalium()) + Double.parseDouble(dietPlanBean.getKalium()), 1) + "";
        this.phosphor = Utils.setDot(Double.parseDouble(nutritionSummaryBean.getPhosphor()) + Double.parseDouble(dietPlanBean.getPhosphor()), 1) + "";
        this.natrium = Utils.setDot(Double.parseDouble(nutritionSummaryBean.getNatrium()) + Double.parseDouble(dietPlanBean.getNatrium()), 1) + "";
        this.selenium = Utils.setDot(Double.parseDouble(nutritionSummaryBean.getSelenium()) + Double.parseDouble(dietPlanBean.getSelenium()), 1) + "";


        this.date = getFormatDate();
    }


    public NutritionSummaryBean(Long id, String calory, String protein, String fat, String carbohydrate) {
        this.id = id;
        this.calory = calory;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrate = carbohydrate;
        this.date = getFormatDate();
    }

    @Generated(hash = 1729413596)
    public NutritionSummaryBean(Long id, String date, String calory, String protein, String fat, String carbohydrate, String fiber_dietary, String vitamin_a,
            String vitamin_c, String vitamin_e, String carotene, String thiamine, String lactoflavin, String niacin, String cholesterol, String magnesium, String calcium,
            String iron, String zinc, String copper, String manganese, String kalium, String phosphor, String natrium, String selenium) {
        this.id = id;
        this.date = date;
        this.calory = calory;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrate = carbohydrate;
        this.fiber_dietary = fiber_dietary;
        this.vitamin_a = vitamin_a;
        this.vitamin_c = vitamin_c;
        this.vitamin_e = vitamin_e;
        this.carotene = carotene;
        this.thiamine = thiamine;
        this.lactoflavin = lactoflavin;
        this.niacin = niacin;
        this.cholesterol = cholesterol;
        this.magnesium = magnesium;
        this.calcium = calcium;
        this.iron = iron;
        this.zinc = zinc;
        this.copper = copper;
        this.manganese = manganese;
        this.kalium = kalium;
        this.phosphor = phosphor;
        this.natrium = natrium;
        this.selenium = selenium;
    }


    private String getFormatDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String s = sdf.format(date);
        return s;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCalory() {
        return this.calory;
    }

    public void setCalory(String calory) {
        this.calory = calory;
    }

    public String getProtein() {
        return this.protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getFat() {
        return this.fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getCarbohydrate() {
        return this.carbohydrate;
    }

    public void setCarbohydrate(String carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFiber_dietary() {
        return fiber_dietary;
    }

    public void setFiber_dietary(String fiber_dietary) {
        this.fiber_dietary = fiber_dietary;
    }

    public String getVitamin_a() {
        return vitamin_a;
    }

    public void setVitamin_a(String vitamin_a) {
        this.vitamin_a = vitamin_a;
    }

    public String getVitamin_c() {
        return vitamin_c;
    }

    public void setVitamin_c(String vitamin_c) {
        this.vitamin_c = vitamin_c;
    }

    public String getVitamin_e() {
        return vitamin_e;
    }

    public void setVitamin_e(String vitamin_e) {
        this.vitamin_e = vitamin_e;
    }

    public String getCarotene() {
        return carotene;
    }

    public void setCarotene(String carotene) {
        this.carotene = carotene;
    }

    public String getThiamine() {
        return thiamine;
    }

    public void setThiamine(String thiamine) {
        this.thiamine = thiamine;
    }

    public String getLactoflavin() {
        return lactoflavin;
    }

    public void setLactoflavin(String lactoflavin) {
        this.lactoflavin = lactoflavin;
    }

    public String getNiacin() {
        return niacin;
    }

    public void setNiacin(String niacin) {
        this.niacin = niacin;
    }

    public String getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(String cholesterol) {
        this.cholesterol = cholesterol;
    }

    public String getMagnesium() {
        return magnesium;
    }

    public void setMagnesium(String magnesium) {
        this.magnesium = magnesium;
    }

    public String getCalcium() {
        return calcium;
    }

    public void setCalcium(String calcium) {
        this.calcium = calcium;
    }

    public String getIron() {
        return iron;
    }

    public void setIron(String iron) {
        this.iron = iron;
    }

    public String getZinc() {
        return zinc;
    }

    public void setZinc(String zinc) {
        this.zinc = zinc;
    }

    public String getCopper() {
        return copper;
    }

    public void setCopper(String copper) {
        this.copper = copper;
    }

    public String getManganese() {
        return manganese;
    }

    public void setManganese(String manganese) {
        this.manganese = manganese;
    }

    public String getKalium() {
        return kalium;
    }

    public void setKalium(String kalium) {
        this.kalium = kalium;
    }

    public String getPhosphor() {
        return phosphor;
    }

    public void setPhosphor(String phosphor) {
        this.phosphor = phosphor;
    }

    public String getNatrium() {
        return natrium;
    }

    public void setNatrium(String natrium) {
        this.natrium = natrium;
    }

    public String getSelenium() {
        return selenium;
    }

    public void setSelenium(String selenium) {
        this.selenium = selenium;
    }

}
