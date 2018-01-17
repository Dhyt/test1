package ml.amaze.design.bean;


import java.util.List;

/**
 * @author hxj
 */
public class Entity3_own {


    /**
     * type : own
     * data : {"ext":" 1. 将羊排骨洗净剁段，萝卜洗净切块，香菜洗净切段；大葱洗净切段；姜洗净切块；\r\n2. 锅内添水烧开，放入羊排骨和萝卜同煮，熟后捞出备用。\r\n3. 炒锅注油烧热，下葱姜爆锅，放入辣椒酱、红枣、酱油、白糖、胡椒粉及适量清水，烧开后放入羊排骨，用慢火烧至熟烂，用水淀粉勾芡，撒上香菜段，翻匀出锅即可。 ","materials":{"major_materials":[{"name":"羊肉(肥瘦)","weight":750,"desc":""}],"minor_materials":[{"name":"白萝卜","weight":100,"desc":""},{"name":"红枣(干)","weight":8,"desc":""}],"seasoning":[{"name":"花生油","weight":25,"desc":""},{"name":"豌豆淀粉","weight":10,"desc":""},{"name":"大葱","weight":5,"desc":""},{"name":"酱油","weight":4,"desc":""},{"name":"姜","weight":3,"desc":""},{"name":"精盐","weight":3,"desc":""},{"name":"料酒","weight":3,"desc":""},{"name":"胡椒粉","weight":2,"desc":""},{"name":"辣椒粉","weight":2,"desc":""},{"name":"味精","weight":2,"desc":""}]}}
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
         * ext :  1. 将羊排骨洗净剁段，萝卜洗净切块，香菜洗净切段；大葱洗净切段；姜洗净切块；
         2. 锅内添水烧开，放入羊排骨和萝卜同煮，熟后捞出备用。
         3. 炒锅注油烧热，下葱姜爆锅，放入辣椒酱、红枣、酱油、白糖、胡椒粉及适量清水，烧开后放入羊排骨，用慢火烧至熟烂，用水淀粉勾芡，撒上香菜段，翻匀出锅即可。
         * materials : {"major_materials":[{"name":"羊肉(肥瘦)","weight":750,"desc":""}],"
         * minor_materials":[{"name":"白萝卜","weight":100,"desc":""},{"name":"红枣(干)","weight":8,"desc":""}],
         * "seasoning":[{"name":"花生油","weight":25,"desc":""},{"name":"豌豆淀粉","weight":10,"desc":""},{"name":"大葱","weight":5,"desc":""},{"name":"酱油","weight":4,"desc":""},{"name":"姜","weight":3,"desc":""},{"name":"精盐","weight":3,"desc":""},{"name":"料酒","weight":3,"desc":""},{"name":"胡椒粉","weight":2,"desc":""},{"name":"辣椒粉","weight":2,"desc":""},{"name":"味精","weight":2,"desc":""}]}
         *
         * own菜谱有两种，一种是 materials : {"major_materials":[{"name":"羊肉(肥瘦)","weigh。。。。
         * 另一种是"materials":{"raw":[{"name":"猪肉(肥瘦)","wei。。。。
         */

        private String ext;
        private MaterialsBean materials;

        public String getExt() {
            return ext;
        }

        public void setExt(String ext) {
            this.ext = ext;
        }

        public MaterialsBean getMaterials() {
            return materials;
        }

        public void setMaterials(MaterialsBean materials) {
            this.materials = materials;
        }

        public static class MaterialsBean {
            private List<MajorMaterialsBean> major_materials;
            private List<MajorMaterialsBean> minor_materials;
            private List<MajorMaterialsBean> seasoning;
            private List<MajorMaterialsBean> raw;

            public List<MajorMaterialsBean> getRaw() {
                return raw;
            }

            public void setRaw(List<MajorMaterialsBean> raw) {
                this.raw = raw;
            }

            public List<MajorMaterialsBean> getMajor_materials() {
                return major_materials;
            }

            public void setMajor_materials(List<MajorMaterialsBean> major_materials) {
                this.major_materials = major_materials;
            }

            public List<MajorMaterialsBean> getMinor_materials() {
                return minor_materials;
            }

            public void setMinor_materials(List<MajorMaterialsBean> minor_materials) {
                this.minor_materials = minor_materials;
            }

            public List<MajorMaterialsBean> getSeasoning() {
                return seasoning;
            }

            public void setSeasoning(List<MajorMaterialsBean> seasoning) {
                this.seasoning = seasoning;
            }

            public static class MajorMaterialsBean {
                /**
                 * name : 羊肉(肥瘦)
                 * weight : 750
                 * desc :
                 */

                private String name;
                private double weight;
                private String desc;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public double getWeight() {
                    return weight;
                }

                public void setWeight(int weight) {
                    this.weight = weight;
                }

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }
            }


        }
    }
}
