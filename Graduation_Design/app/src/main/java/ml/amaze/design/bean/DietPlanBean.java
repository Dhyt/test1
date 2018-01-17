package ml.amaze.design.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.util.ArrayList;
import java.util.List;

import ml.amaze.design.utils.Utils;

/**
 * @author hxj
 * @date 2017/12/14 0014
 */
@Entity
public class DietPlanBean {
    @Id
    private Long id;
    @Property(nameInDb = "name")
    private String name;
    @Property(nameInDb = "code")
    private String code;
    @Property(nameInDb = "weight")
    private String weight;
    @Property(nameInDb = "health_light")
    private String health_light;
    @Property(nameInDb = "whichMeal")
    private int whichMeal;
    @Property(nameInDb = "count")
    private String count;
    @Property(nameInDb = "date")
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



    public DietPlanBean(Long id, Entity2 entity2, int whichMeal, String count) {


        this.id = id;
        this.name = trim(entity2.getName());
        this.code = entity2.getCode();
        this.health_light = entity2.getHealth_light() + "";
        this.whichMeal = whichMeal;
        this.date = Utils.getFormatDate();
        this.count = count;
        this.weight = entity2.getWeight();
        //系数
        double a = Double.parseDouble(count) / Double.parseDouble(weight);


        List<String> list=new ArrayList<>();
        list.add(entity2.getCalory());
        list.add(entity2.getProtein());
        list.add(entity2.getFat());
        list.add(entity2.getCarbohydrate());
        list.add(entity2.getFiber_dietary());
        list.add(entity2.getIngredient().getVitamin_a());
        list.add(entity2.getIngredient().getVitamin_c());
        list.add(entity2.getIngredient().getVitamin_e());
        list.add(entity2.getIngredient().getCarotene());
        list.add(entity2.getIngredient().getThiamine());
        list.add(entity2.getIngredient().getLactoflavin());
        list.add(entity2.getIngredient().getNiacin());
        list.add(entity2.getIngredient().getCholesterol());
        list.add(entity2.getIngredient().getMagnesium());
        list.add(entity2.getIngredient().getCalcium());
        list.add(entity2.getIngredient().getIron());
        list.add(entity2.getIngredient().getZinc());
        list.add(entity2.getIngredient().getCopper());
        list.add(entity2.getIngredient().getManganese());
        list.add(entity2.getIngredient().getKalium());
        list.add(entity2.getIngredient().getPhosphor());
        list.add(entity2.getIngredient().getNatrium());
        list.add(entity2.getIngredient().getSelenium());

        for(int i=0;i<23;i++){
            if(list.get(i).isEmpty()){
                list.add(i,"0");
            }
        }
        this.calory             = Utils.setDot(Double.parseDouble(list.get(0)                                          ) * a, 1) + "";
        this.protein            = Utils.setDot(Double.parseDouble(list.get(1)                                          ) * a, 1) + "";
        this.fat                = Utils.setDot(Double.parseDouble(list.get(2)                                      ) * a, 1) + "";
        this.carbohydrate       = Utils.setDot(Double.parseDouble(list.get(3)                                              ) * a, 1) + "";
        this.fiber_dietary      = Utils.setDot(Double.parseDouble(list.get(4)                                                  ) * a, 1) + "";
        this.vitamin_a          = Utils.setDot(Double.parseDouble(list.get(5)                                                              ) * a, 1) + "";
        this.vitamin_c          = Utils.setDot(Double.parseDouble(list.get(6)                                                                 ) * a, 1) + "";
        this.vitamin_e          = Utils.setDot(Double.parseDouble(list.get(7)                                                              ) * a, 1) + "";
        this.carotene           = Utils.setDot(Double.parseDouble(list.get(8)                                                          ) * a, 1) + "";
        this.thiamine           = Utils.setDot(Double.parseDouble(list.get(9)                                                          ) * a, 1) + "";
        this.lactoflavin        = Utils.setDot(Double.parseDouble(list.get(10)                                                              ) * a, 1) + "";
        this.niacin             = Utils.setDot(Double.parseDouble(list.get(11)                                                          ) * a, 1) + "";
        this.cholesterol        = Utils.setDot(Double.parseDouble(list.get(12)                                                              ) * a, 1) + "";
        this.magnesium          = Utils.setDot(Double.parseDouble(list.get(13)                                                              ) * a, 1) + "";
        this.calcium            = Utils.setDot(Double.parseDouble(list.get(14)                                                          ) * a, 1) + "";
        this.iron               = Utils.setDot(Double.parseDouble(list.get(15)                                                      ) * a, 1) + "";
        this.zinc               = Utils.setDot(Double.parseDouble(list.get(16)                                                      ) * a, 1) + "";
        this.copper             = Utils.setDot(Double.parseDouble(list.get(17)                                                          ) * a, 1) + "";
        this.manganese          = Utils.setDot(Double.parseDouble(list.get(18)                                                              ) * a, 1) + "";
        this.kalium             = Utils.setDot(Double.parseDouble(list.get(19)                                                          ) * a, 1) + "";
        this.phosphor           = Utils.setDot(Double.parseDouble(list.get(20)                                                          ) * a, 1) + "";
        this.natrium            = Utils.setDot(Double.parseDouble(list.get(21)                                                          ) * a, 1) + "";
        this.selenium           = Utils.setDot(Double.parseDouble(list.get(22)                                                          ) * a, 1) + "";
    }


    @Generated(hash = 1936171571)
    public DietPlanBean(Long id, String name, String code, String weight, String health_light, int whichMeal, String count, String date, String calory, String protein, String fat,
            String carbohydrate, String fiber_dietary, String vitamin_a, String vitamin_c, String vitamin_e, String carotene, String thiamine, String lactoflavin, String niacin,
            String cholesterol, String magnesium, String calcium, String iron, String zinc, String copper, String manganese, String kalium, String phosphor, String natrium, String selenium) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.weight = weight;
        this.health_light = health_light;
        this.whichMeal = whichMeal;
        this.count = count;
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


    @Generated(hash = 660789615)
    public DietPlanBean() {
    }


    private String trim(String str) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c != ' ') {
                sb.append(c);
            }
        }
        return sb.toString();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getWhichMeal() {
        return whichMeal;
    }

    public void setWhichMeal(int whichMeal) {
        this.whichMeal = whichMeal;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCalory() {
        return calory;
    }

    public void setCalory(String calory) {
        this.calory = calory;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHealth_light() {
        return health_light;
    }

    public void setHealth_light(String health_light) {
        this.health_light = health_light;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(String carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public String getDate() {
        return date;
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
