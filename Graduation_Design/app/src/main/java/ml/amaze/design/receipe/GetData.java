package ml.amaze.design.receipe;


/**
 * @author hxj
 */
public class GetData {

    private JsonApi jsonApi =new JsonApi();

    /**
     * 获取模糊查询结果
     * @param name
     * @return
     * @throws Exception
     */
    public String getEntity1(String name) throws Exception {

        //Entity1 entity1=new Gson().fromJson(json, Entity1.class);
        return jsonApi.getRequest1(name);
    }

    /**
     * 获取精确查询结果
     * @param code
     * @return
     * @throws Exception
     */
    public String getEntity2(String code) throws Exception {
        return jsonApi.getRequest2(code);
    }

    /**
     * 获取菜谱
     * @param code
     * @return
     * @throws Exception
     */
     String getrecipeOwn(String code) throws Exception {
        return jsonApi.getrequest3Own(code);
    }

     String getrecipeDouguo(String code) throws Exception {

        return jsonApi.getrequest3Douguo(code);
    }

}
