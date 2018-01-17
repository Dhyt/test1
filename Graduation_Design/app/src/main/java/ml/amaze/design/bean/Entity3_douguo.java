package ml.amaze.design.bean;

import java.util.List;

/**
 * @author hxj
 */
public class Entity3_douguo {

    /**
     * type : douguo
     * data : {"id":385,"code":"hongshaodapai","name":"红烧大排","image_url":"http://s.boohee.cn/house/food_big/big_photo201281514502016556.jpg","tips":"1、大排入油锅过一下油就可以了。目的是让淀粉裹住大排。\n2、煸香葱结时火要适中。\n3、焖煮时要翻动，一是怕粘锅，二是要让它受热均匀。\n4、裹上淀粉的大排，烧出来肉质较嫩滑。\n","condiments":[{"name":"大排","amount":"三块"},{"name":"葱","amount":"十根"},{"name":"酱油","amount":null},{"name":"盐","amount":null},{"name":"酒","amount":null},{"name":"糖","amount":null},{"name":"味精","amount":null},{"name":"淀粉","amount":null}],"details":[{"position":1,"desc":"大排洗净，沥干，用木榔头把它敲扁。\n","image_url":null},{"position":2,"desc":"葱去黄叶，洗净，打成三四个葱结\n","image_url":null},{"position":3,"desc":"锅上火加热，加入油（略多一些），加热，把大排蘸上干淀粉逐一下油锅过一下油，时间不要长，只要淀粉结起就可以盛出了。\n","image_url":null},{"position":4,"desc":"倒去大部分油，余油热，加入葱结煸香，加入大排，加酱油、适量盐，加水（约三分之二）煮开，转小火焖煮约十分钟，注意当中要适当翻动，以免粘锅。加糖，味精调味，收汁盛出即可。\n","image_url":null}]}
     */

    private String type;
    private DataBean data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 385
         * code : hongshaodapai
         * name : 红烧大排
         * image_url : http://s.boohee.cn/house/food_big/big_photo201281514502016556.jpg
         * tips : 1、大排入油锅过一下油就可以了。目的是让淀粉裹住大排。
         2、煸香葱结时火要适中。
         3、焖煮时要翻动，一是怕粘锅，二是要让它受热均匀。
         4、裹上淀粉的大排，烧出来肉质较嫩滑。

         * condiments : [{"name":"大排","amount":"三块"},{"name":"葱","amount":"十根"},{"name":"酱油","amount":null},{"name":"盐","amount":null},{"name":"酒","amount":null},{"name":"糖","amount":null},{"name":"味精","amount":null},{"name":"淀粉","amount":null}]
         * details : [{"position":1,"desc":"大排洗净，沥干，用木榔头把它敲扁。\n","image_url":null},{"position":2,"desc":"葱去黄叶，洗净，打成三四个葱结\n","image_url":null},{"position":3,"desc":"锅上火加热，加入油（略多一些），加热，把大排蘸上干淀粉逐一下油锅过一下油，时间不要长，只要淀粉结起就可以盛出了。\n","image_url":null},{"position":4,"desc":"倒去大部分油，余油热，加入葱结煸香，加入大排，加酱油、适量盐，加水（约三分之二）煮开，转小火焖煮约十分钟，注意当中要适当翻动，以免粘锅。加糖，味精调味，收汁盛出即可。\n","image_url":null}]
         */

        private int id;
        private String code;
        private String name;
        private String image_url;
        private String tips;
        private List<CondimentsBean> condiments;
        private List<recipeStepsBean> steps;

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

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }

        public List<CondimentsBean> getCondiments() {
            return condiments;
        }

        public void setCondiments(List<CondimentsBean> condiments) {
            this.condiments = condiments;
        }

        public List<recipeStepsBean> getSteps() {
            return steps;
        }

        public void setSteps(List<recipeStepsBean> steps) {
            this.steps = steps;
        }

        public static class CondimentsBean {
            /**
             * name : 大排
             * amount : 三块
             */

            private String name;
            private String amount;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }
        }

        public static class recipeStepsBean {
            /**
             * position : 1
             * desc : 大排洗净，沥干，用木榔头把它敲扁。

             * image_url : null
             */

            private int position;
            private String desc;
            private Object image_url;

            public int getPosition() {
                return position;
            }

            public void setPosition(int position) {
                this.position = position;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public Object getImage_url() {
                return image_url;
            }

            public void setImage_url(Object image_url) {
                this.image_url = image_url;
            }
        }
    }
}
