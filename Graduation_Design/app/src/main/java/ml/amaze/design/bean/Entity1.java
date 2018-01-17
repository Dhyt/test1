package ml.amaze.design.bean;

import java.util.List;

/**
 * 模糊查询结果
 * 第一种请求的实体
 * 根据名字直接搜索
 * 获得名字机器搜索代码
 * @author hxj
 */
public class Entity1 {

    /**
     * page : 1
     * total_pages : 23
     * foods : [{"id":688,"code":"zhudapai","name":"猪大排","thumb_image_url":"http://s.boohee.cn/house/new_food/mid/6fa94a1a505346cfa74ae7db891f5ff7.jpg","is_liquid":false,"health_light":2,"weight":"100","calory":"264"},{"id":1667,"code":"jiangpaigu","name":"酱排骨","thumb_image_url":"http://s.boohee.cn/house/new_food/mid/6a4d6bb295c84306aefaa9a1b88b6314.jpg","is_liquid":false,"health_light":3,"weight":"100","calory":"366"},{"id":693,"code":"zhuxiaopai","name":"猪小排","thumb_image_url":"http://s.boohee.cn/house/new_food/mid/6edbf8df1e0c4ffb875b637053f0c30b.jpg","is_liquid":false,"health_light":2,"weight":"100","calory":"278"},{"id":4226,"code":"qingdunpaigu","name":"清炖排骨","thumb_image_url":"http://s.boohee.cn/house/upload_food/2008/6/13/user_mid_72047_1213345416.jpg","is_liquid":false,"health_light":2,"weight":"100","calory":"185"},{"id":16967,"code":"laohuangguapaigutang","name":"老黄瓜排骨汤","thumb_image_url":"http://s.boohee.cn/house/food_mid/mid_photo_20115181523116967.jpg","is_liquid":false,"health_light":2,"weight":"100","calory":"51"},{"id":6225,"code":"menpaigu","name":"焖排骨","thumb_image_url":"http://s.boohee.cn/house/upload_food/2016/10/20/mid_photo_url_478488b37d5e4b7086ced0b0ae22cab3.jpg","is_liquid":false,"health_light":1,"weight":"100","calory":"186"},{"id":3955,"code":"paigubao","name":"排骨煲","thumb_image_url":"http://s.boohee.cn/house/upload_food/2009/2/6/user_207105_1233897249.jpg","is_liquid":false,"health_light":2,"weight":"100","calory":"223"},{"id":4891,"code":"fenzhengpaigu","name":"粉蒸排骨","thumb_image_url":"http://s.boohee.cn/house/menu_mid/1169192301447_mid.jpg","is_liquid":false,"health_light":3,"weight":"100","calory":"298"},{"id":2707,"code":"suanxiangpaigu","name":"蒜香排骨","thumb_image_url":"http://s.boohee.cn/house/upload_food/2016/7/11/mid_photo_url_7b1380474dc14fb390656edae99e260a.jpg","is_liquid":false,"health_light":2,"weight":"100","calory":"234"},{"id":3733,"code":"jianpaigu","name":"煎排骨","thumb_image_url":"http://s.boohee.cn/house/upload_food/2016/10/12/mid_photo_url_1f47364525f842508a4c0820e579cc26.jpg","is_liquid":false,"health_light":2,"weight":"100","calory":"146"},{"id":3731,"code":"jiangzhipaigu","name":"酱汁排骨","thumb_image_url":"http://s.boohee.cn/house/upload_food/2008/9/26/user_mid_138345_1222434190.jpg","is_liquid":false,"health_light":3,"weight":"100","calory":"334"},{"id":3138,"code":"jiaoyanpaigu","name":"椒盐排骨","thumb_image_url":"http://s.boohee.cn/house/upload_food/2008/10/23/user_146292_1224739932.jpg","is_liquid":false,"health_light":3,"weight":"100","calory":"275"},{"id":2542,"code":"douchizhengpaigu","name":"豆豉蒸排骨","thumb_image_url":"http://s.boohee.cn/house/upload_food/2016/7/11/mid_photo_url_8fb6a206d3744e4b891285fe7cafd107.jpg","is_liquid":false,"health_light":2,"weight":"100","calory":"261"},{"id":19488,"code":"paiguniangao","name":"排骨年糕","thumb_image_url":"http://s.boohee.cn/house/upload_food/2009/5/2/129336_1241218704mid.jpg","is_liquid":false,"health_light":2,"weight":"100","calory":"173"},{"id":17344,"code":"dongguapaigutanghishaofaer","name":"冬瓜排骨汤","thumb_image_url":"http://s.boohee.cn/house/food_mid/mid_photo_20153414485317344.jpg","is_liquid":false,"health_light":2,"weight":"100","calory":"51"},{"id":7616,"code":"fenpaiguou","name":"粉排骨藕","thumb_image_url":"http://s.boohee.cn/house/food_mid/mid_photo_20128211039537616.jpg","is_liquid":false,"health_light":2,"weight":"100","calory":"224"},{"id":18117,"code":"tudoudunpaigu","name":"土豆炖排骨","thumb_image_url":"http://s.boohee.cn/house/upload_food/2016/9/2/mid_photo_url_568613fdc11b4d44851cb79105a71b25.jpg","is_liquid":false,"health_light":2,"weight":"100","calory":"118"},{"id":18443,"code":"paigudunluobotang","name":"排骨炖萝卜汤","thumb_image_url":"http://s.boohee.cn/house/upload_food/2009/1/17/96504_1232206637mid.jpg","is_liquid":false,"health_light":2,"weight":"100","calory":"84"},{"id":5836,"code":"youzhapaigu","name":"油炸排骨","thumb_image_url":"http://s.boohee.cn/house/upload_food/2016/10/28/mid_photo_url_6c3ca7fac64f469eaf50b5a760afee4f.jpg","is_liquid":false,"health_light":2,"weight":"100","calory":"268"},{"id":15160,"code":"paiguji","name":"排骨鸡","thumb_image_url":"http://s.boohee.cn/house/upload_food/2016/10/21/mid_photo_url_def98d7722434c6ba62176934d384c5e.jpg","is_liquid":false,"health_light":1,"weight":"100","calory":"146"},{"id":3730,"code":"jiazhijiangpaigu","name":"家制酱排骨","thumb_image_url":"http://s.boohee.cn/house/menu_mid/1169116731619_mid.jpg","is_liquid":false,"health_light":2,"weight":"100","calory":"247"},{"id":11054,"code":"mizhipaigu","name":"蜜汁排骨","thumb_image_url":"http://s.boohee.cn/house/upload_food/2008/6/13/user_mid_25708_1213360100.jpg","is_liquid":false,"health_light":1,"weight":"100","calory":"260"},{"id":5838,"code":"xiangsupaigu","name":"香酥排骨","thumb_image_url":"http://s.boohee.cn/house/menu_mid/1170406979820_mid.jpg","is_liquid":false,"health_light":3,"weight":"100","calory":"286"},{"id":8583,"code":"ouweipaigutang","name":"藕煨排骨汤","thumb_image_url":"http://s.boohee.cn/house/upload_food/2008/12/9/user_mid_36179_1228791035.jpg","is_liquid":false,"health_light":2,"weight":"100","calory":"62"},{"id":3334,"code":"suanlapaigu","name":"酸辣排骨","thumb_image_url":"http://s.boohee.cn/house/upload_food/2008/7/31/user_mid_113345_1217495697.jpg","is_liquid":false,"health_light":2,"weight":"100","calory":"199"},{"id":4761,"code":"paiguweioutang","name":"排骨煨藕汤","thumb_image_url":"http://s.boohee.cn/house/food_mid/mid_photo_2015213112154761.jpg","is_liquid":false,"health_light":2,"weight":"100","calory":"92"},{"id":16857,"code":"heyenuomizhengpaigu","name":"荷叶糯米蒸排骨","thumb_image_url":"http://s.boohee.cn/house/food_mid/mid_photo_20153414485116857.jpg","is_liquid":false,"health_light":2,"weight":"100","calory":"274"},{"id":4908,"code":"douyapaigutang","name":"豆芽排骨汤","thumb_image_url":"http://s.boohee.cn/house/upload_food/2016/9/22/mid_photo_url_337bae09aa5447f095c95154d26bc774.jpg","is_liquid":false,"health_light":2,"weight":"100","calory":"90"},{"id":2250,"code":"jiangpaigu2","name":"自制酱排骨","thumb_image_url":"http://s.boohee.cn/house/upload_food/2008/10/29/user_mid_153062_1225259482.jpg","is_liquid":false,"health_light":2,"weight":"100","calory":"251"},{"id":17268,"code":"bailuobodunzhupaigu","name":"白萝卜排骨汤","thumb_image_url":"http://s.boohee.cn/house/upload_food/2016/8/29/mid_photo_url_a0abfc247e2d4f378bdf532bbddd2981.jpg","is_liquid":false,"health_light":1,"weight":"100","calory":"68"}]
     */

    private int page;
    private int total_pages;
    private List<FoodsBean> foods;


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<FoodsBean> getFoods() {
        return foods;
    }

    public void setFoods(List<FoodsBean> foods) {
        this.foods = foods;
    }

    public static class FoodsBean {
        /**
         * id : 688
         * code : zhudapai
         * name : 猪大排
         * thumb_image_url : http://s.boohee.cn/house/new_food/mid/6fa94a1a505346cfa74ae7db891f5ff7.jpg
         * is_liquid : false
         * health_light : 2
         * weight : 100
         * calory : 264
         */

        private int id;
        private String code;
        private String name;
        private String thumb_image_url;
        private boolean is_liquid;
        private int health_light;
        private String weight;
        private String calory;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getThumb_image_url() {
            return thumb_image_url;
        }

        public void setThumb_image_url(String thumb_image_url) {
            this.thumb_image_url = thumb_image_url;
        }

        public boolean isIs_liquid() {
            return is_liquid;
        }

        public void setIs_liquid(boolean is_liquid) {
            this.is_liquid = is_liquid;
        }

        public int getHealth_light() {
            return health_light;
        }

        public void setHealth_light(int health_light) {
            this.health_light = health_light;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getCalory() {
            return calory;
        }

        public void setCalory(String calory) {
            this.calory = calory;
        }
    }
}
