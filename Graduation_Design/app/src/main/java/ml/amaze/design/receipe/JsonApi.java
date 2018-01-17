package ml.amaze.design.receipe;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author hxj
 * @date 2017/6/27 0027
 * @SRC 请求接口地址
 */
public class JsonApi {
    private static final String DEF_CHATSET = "UTF-8";
    private static final int DEF_CONN_TIMEOUT = 30000;
    private static final int DEF_READ_TIMEOUT = 30000;

    private static final String USER_AGENT =  "Android/Volley";
    private static final String SRC= "http://amazer.ml/testServlet";



    /**
     *  1.模糊查询菜谱
     * @param name
     * @return
     */
     String getRequest1(String name){
        //请求参数
        Map params = new HashMap(2);
        //需要查询的名字
        params.put("method","getRequest1");
        //应用APPKEY(应用详细页查询)
        params.put("arg",name);

        return getJson(SRC,params);
    }


    /**
     * 2.精确查找菜谱
     * @param code
     * @return
     */
     String  getRequest2(String code){
//请求参数
        Map params = new HashMap(2);
        //需要查询的名字
        params.put("method","getRequest2");
        //应用APPKEY(应用详细页查询)
        params.put("arg",code);

        return getJson(SRC,params);
    }

    /**
     * 3.获得菜谱
     * @param code
     * @return
     */
     String getrequest3Own(String code){
        //请求参数
        Map params = new HashMap(2);
        //需要查询的名字
        params.put("method","getRequest3_own");
        //应用APPKEY(应用详细页查询)
        params.put("arg",code);
        return getJson(SRC,params);
    }


     String getrequest3Douguo(String code){
        //请求参数
        Map params = new HashMap(2);
        //需要查询的名字
        params.put("method","getRequest3_douguo");
        //应用APPKEY(应用详细页查询)
        params.put("arg",code);

        return getJson(SRC,params);
    }

    public String getYiYan(){
        //https://sslapi.hitokoto.cn/?encode=text
        //https://api.i-meto.com/hitokoto?length=32
        //String yiYanApi="https://api.lwl12.com/hitokoto/main/get";
        String yiYanApi="https://api.i-meto.com/hitokoto?length=32";
        return getJson(yiYanApi,null);
    }

    public  String getPic(){
        System.out.println("-------------");
        String requestURL="http://lorempixel.com/index.php?generator=1&x=480&y=800&cat=nature";
        //不知道咋用String picApi2="https://picsum.photos/200/300/?random";

        //requestURL返回数据  <img src="output/nature-h-c-480-640-6.jpg" />
        String response=getJson(requestURL,null);
        //http://lorempixel.com/output/nature-h-c-480-800-1.jpg
        return "http://lorempixel.com/"+response.substring(response.indexOf("\"")+1,response.lastIndexOf("\""));
    }


	//http://record.boohee.com/api/v2/eatings/hot?page=1&app_device=Android&user_key=058fbee5-8a04-4fcd-b94c-b84c677c10f5&token=DEKmsNDyVsAzuSvS7bnx



    private String getJson(String url, Map params){

        String result="";
        try {
            result =net(url, params, "GET");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return  网络请求字符串
     * @throws Exception
     */
    private String net(String strUrl, Map params, String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        String methodGet="GET";
        String methodPost="POST";
        try {

            StringBuffer sb = new StringBuffer();
            if(params!=null){
                if(method==null ||methodGet .equals(method)){
                    strUrl = strUrl+"?"+urlencode(params);
                }
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if(method==null || methodGet.equals(method)){
                conn.setRequestMethod(methodGet);
            }else{
                conn.setRequestMethod(methodPost);
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", USER_AGENT);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params!= null && methodPost.equals(method)) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead ;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }


    /**
     * 将map型转为请求参数型
     * @param data
     * @return
     */
    private String urlencode(Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
