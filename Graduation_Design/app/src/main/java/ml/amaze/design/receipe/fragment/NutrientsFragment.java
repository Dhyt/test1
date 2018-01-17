package ml.amaze.design.receipe.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Field;

import ml.amaze.design.R;
import ml.amaze.design.bean.Entity2;

/**
 *
 * @author hxj
 * @date 2017/11/28 0028
 * 查看营养素
 * @ingredient 中英文营养素名字对照
 */

public class NutrientsFragment extends Fragment {





    Field[] fieldsIngredient;
    Entity2.IngredientBean ingredient;

    private String[] names = {"calory", "protein", "fat", "carbohydrate", "fiber_dietary", "vitamin_a", "vitamin_c", "vitamin_e", "carotene", "thiamine", "lactoflavin", "niacin", "cholesterol", "magnesium", "calcium", "iron", "zinc", "copper", "manganese", "kalium", "phosphor", "natrium", "selenium"};

    private String[] namezhcn = {"热量", "蛋白质", "脂肪", "碳水化合物", "膳食纤维", "维生素A", "维生素C", "维生素E", "胡萝卜素", "维生素B1", "维生素B2", "烟酸", "胆固醇", "镁", "钙", "铁", "锌", "铜", "锰", "钾", "磷", "钠", "硒"};

    private String[] lights=getLights();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.recipepart_fragment_nutrient, null);

        Bundle bundle = this.getArguments();
        Entity2 entity2 = (Entity2) bundle.get("entity2");



        //通过反射获得属性
        System.out.println("entity2......" + entity2.getCalory());
        ingredient = entity2.getIngredient();


        fieldsIngredient = ingredient.getClass().getDeclaredFields();


        ListView listView = (ListView) view.findViewById(R.id.nutrient_fragment_lv);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    new AlertDialog.Builder(getActivity())
                            .setTitle(namezhcn[position]+"营养素介绍")
                            .setMessage(lights[position-1])
                            .setPositiveButton("确定", null)
                            .show();
                }
            }
        });
        listView.setAdapter(new MyAdapter());
        return view;
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @SuppressLint("NewApi")
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(getActivity(), R.layout.recipepart_items_nutrient, null);
            } else {
                view = convertView;
            }

            for (int i = 0; i < fieldsIngredient.length; i++) {
                Field f = fieldsIngredient[i];
                //设置些属性是可以访问的
                f.setAccessible(true);

                if (names[position].equals(f.getName())) {
                    try {
                        //得到此属性的值
                        String val = (String) f.get(ingredient);

                        TextView content = (TextView) view.findViewById(R.id.nutrient_item_tv_content);
                        TextView contentNum = (TextView) view.findViewById(R.id.nutrient_item_tv_contentNum);

                        String name= namezhcn[position];
                        System.out.println(name+ "----------" + val);
                        content.setText(namezhcn[position]);

                        if("热量".equals(name)){
                            if(val.isEmpty()){
                                contentNum.setText("0.0千卡");
                            }else {
                                contentNum.setText(val+"千卡");
                            }

                        }else if("蛋白质".equals(name) || "脂肪".equals(name) || "碳水化合物".equals(name) || "膳食纤维".equals(name)){
                            if(val.isEmpty()){
                                contentNum.setText("0.0克");
                            }else {
                                contentNum.setText(val+"克");
                            }
                        }else if("维生素A".equals(name) || "胡萝卜素".equals(name) || "硒".equals(name)){
                            if(val.isEmpty()) {
                                contentNum.setText("0.0微克");
                            }else {
                                contentNum.setText(val+"微克");
                            }
                        }else {
                            if(val.isEmpty()) {
                                contentNum.setText("0.0毫克");
                            }else {
                                contentNum.setText(val + "毫克");
                            }
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
            return view;
        }
    }

    /**
     * 获取营养素介绍
     * @return
     */
    public String[] getLights() {

        String protein =
                "功能:构成和修复组织，生理调节，供给能量。 \n" +
                        "缺乏症:生长发迟育,缓体重减轻,容易疲劳,贫血,易克休,对传染病抵抗力降低,创伤骨折不易愈合,病后恢复健康迟缓,营养性水肿。\n" +
                        "食物来源：牛奶，大豆，肉类等";

        String fat =
                "功能:提供能量，构成机体组成，提供必需脂肪酸，合成激素，帮助脂溶性维生素的吸收。\n" +
                        "缺乏症:生长发育迟缓,体重减轻,容易疲劳,贫血,易克休,对染病抵传抗降力。低伤、创折不易愈合。病骨恢后复健康迟。营缓性养水肿。\n" +
                        "食物来源：动物的脂肪组织和植物种子";
        String carbohydrate =
                "功能:提供能量，构成机体组成,节约蛋白质，抗生酮作用和解毒作用\n" +
                        "缺乏症:生长发育迟缓。体重减轻容易疲劳。\n" +
                        "主要食物来源:米面，杂粮，薯类，果实，蜂蜜，糖果，甜味水果，动物肝脏等";
        String fiberDietary =
                "功能:促进钙质吸收，增强肠道功能，降低血脂血糖，预防糖尿病，预防心血管疾病\n" +
                        "缺乏症:便秘、肥胖症、动脉硬化、心脑血管疾病、糖尿病等\n" +
                        "主要食物来源:蔬菜，水果，粗粮等";
        String vitaminA =
                "功能:构成视觉细胞内感光物质的成分，提高免疫力，具有抑制肿瘤的作用\n" +
                        "缺乏症:夜盲症，干眼病，皮肤类疾病\n" +
                        "主要食物来源:动物肝脏，鱼肝油，全奶，蛋黄，菠菜，芹菜叶，胡萝卜等";
        String vitaminC =
                "功能:清除自由基，促进胶原蛋白的形成，降胆固醇，预防心血管疾病，阻断亚硝胺的形成，对铅，砷苯等具有解毒作用\n" +
                        "缺乏症:坏血病，引起铁吸收障碍，造成缺铁性贫血\n" +
                        "主要食物来源:新鲜蔬菜和水果，";
        String vitaminE =
                "功能:清除自由基，预防衰老，保护神经系统，促进生殖系统发育。\n" +
                        "缺乏症:不孕不育，促使机体衰老\n" +
                        "主要食物来源:植物油，麦胚，见过，种子，豆类谷类";
        String carotene =//胡萝卜素
                "功能:构成视觉细胞内感光物质的成分，防止皮肤干燥，促进生长发育，维护生殖功能，可以转化为维生素A\n" +
                        "缺乏症:暗适应能力下降，夜盲，干眼病，生殖发育受阻，食欲下降，头发干枯，记忆力减退\n" +
                        "主要食物来源:枸杞子，螺旋藻，西兰花，胡萝卜，芒果，沙棘果等";
        String thiamine =//vb1
                "功能:作为氧化脱羧酶和转酮醇酶的辅酶参与能量代谢，促进食欲\n" +
                        "缺乏症:脚气病，临床表现首先出现体弱及疲倦，然后出现头痛、失眠、眩晕、食欲不佳及其他胃肠症状和心动过速\n" +
                        "主要食物来源:谷类是主要来源，胚芽、杂粮、豆类、坚果、动物内脏、蛋类、瘦肉中含量也较多。";
        String lactoflavin =//vb2
                "功能:以FAD和FMN两种形式参与氧化还原反应\n" +
                        "缺乏症:视力模糊、视疲劳、角膜充血、暗适应能力下降，溢脂性皮炎，口角炎、唇炎和舌炎。\n" +
                        "主要食物来源:动物肝，肾，心，蛋黄，菠菜，油菜及豆类等";
        String niacin =//烟酸，vb3
                "功能:参与糖酵解，脂肪代谢及蛋白质代谢，与DNA复制，修复和细胞分化有关\n" +
                        "缺乏症:赖皮病，典型症状是皮炎、腹泻和痴呆\n" +
                        "主要食物来源:动物肝脏，种子，豆类";
        String cholesterol =//胆固醇
                "功能:形成胆酸，参与细胞膜的构成，参与合成激素\n" +
                        "缺乏症:引起营养平衡失调，导致贫血和其它疾病的发生。\n" +
                        "主要食物来源:：蛋黄、动物脑、动物肝肾、墨斗鱼（乌贼）、蟹黄、蟹膏等";
        String magnesium =//镁
                "功能:胞细内液中要重的阳离子。激活体内多种酶。维持核酸结构的稳定性。抑制神经兴奋性。参与合成体内的蛋白质。　\n" +
                        "缺乏症:情绪不安、易激动、手足抽搐、反射亢进等\n" +
                        "主要食物来源:荞麦，大麦，燕麦，黄豆等";
        String calcium =
                "功能:构成骨骼和牙齿，维持神经肌肉的应激性，参与神经脉冲传导，作为生物膜的组成，激活酶类\n" +
                        "缺乏症:手足抽搐，佝偻病，骨质疏松等\n" +
                        "主要食物来源:奶及奶制品，小鱼小虾和坚果类食品，豆制品";
        String iron =
                "功能:（1）血红蛋白、肌红蛋白的成分（2）维持正常的造血功能（3）参与体内氧的运输（4）组织呼吸过程。\n" +
                        "缺乏症:缺铁性贫血，表现为皮肤粗糙，粘膜苍白，易疲劳，头晕，记忆减退等。\n" +
                        "主要食物来源:动物内脏，动物血制品，红肉，红枣，桂圆等";
        String zinc =
                "功能:含锌金属酶的组成成分，参与影响DNA和蛋白质的，促进免疫系统，有利于维生素A的正常代谢\n" +
                        "缺乏症:厌食、易患口腔溃疡,受损伤口不易愈合，生长发育不良，免疫力下降\n" +
                        "主要食物来源:贝壳类产品，红肉和动物内脏，干果类，燕麦，花生等";
        String copper =
                "功能:含铜金属酶的组成成分，促进结缔组织的形成和骨骼肌的正常发育，维持中枢神经系统的健康　\n" +
                        "缺乏症:骨骼结构疏松易碎，发育停止；心脏、主动脉和大血管中弹性蛋白含量降低，组织张力降低；脑组织萎缩，\n" +
                        "主要食物来源:牡蛎，贝类，坚果类，动物肝肾，豆类等";
        String manganese =
                "功能:含锰金属酶的组成成分，参与骨骼和结缔组织形成和能量代谢\n" +
                        "缺乏症:生长发育迟缓，生殖机能受阻\n" +
                        "主要食物来源:茶叶，坚果，全谷类和豆类等";
        String kalium =
                "功能:细胞内液主要中的阳离子，维持体内渗透压的平衡，增强肌肉兴奋性，参与蛋白质，糖类代谢　　\n" +
                        "缺乏症:低钾血症\n" +
                        "主要食物来源:蔬菜，水果等";
        String phosphor =
                "功能:构成骨骼和牙齿，参与能量代谢，是DNA，RNA和细胞膜的组成成分\n" +
                        "缺乏症:引起骨骼,牙齿发育不正常,骨质疏松,软骨病,食欲不振等症状. \n" +
                        "主要食物来源:瘦肉，蛋，奶，动物肝脏，海带，紫菜，花生，坚果，粗粮";
        String natrium =
                "功能:细外液中胞主要的阳离子。维持体内的水平衡、渗透压及、酸碱平衡，增强肌肉兴奋性。\n" +
                        "缺乏症:低钠血症、电解质紊乱、酸碱失衡、体位性低血压\n" +
                        "主要食物来源:食盐，酱油，味精，咸菜等";
        String selenium =
                "功能:构成谷胱甘肽过氧化酶，阻断自由基的攻击，延缓衰老，提高免疫力\n" +
                        "缺乏症:克山病\n" +
                        "主要食物来源:动物肝脏，肉类，大蒜，圆葱等";


        return new String[]{protein, fat, carbohydrate, fiberDietary, vitaminA, vitaminC, vitaminE, carotene, thiamine, lactoflavin, niacin, cholesterol, magnesium, calcium, iron, zinc, copper, manganese, kalium, phosphor, natrium, selenium};
    }

}
