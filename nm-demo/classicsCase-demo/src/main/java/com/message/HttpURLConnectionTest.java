package com.message;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class HttpURLConnectionTest {
    public static final String GET_URL = "http://restapi.amap.com/v3/geocode/geo?key=389880a06e3f893ea46036f030c94700&s=rsv3&city=35"
            + "&address=平凉市庄浪县朱店镇柳李村三社";
    public static final String POST_URL = "http://restapi.amap.com/v3/geocode/geo?key=389880a06e3f893ea46036f030c94700&s=rsv3&city=35"
            + "&address=平凉市庄浪县朱店镇柳李村三社";

    /***
     * 根据经度和位置获取详细信息，
     * <p>
     *  参考：https://lbs.amap.com/console/show/picker，我是直接拿这个测试服的，以为在高德的官网没有找到相关文档。
     *  返回值：jsonp_991766_({"status":"1","regeocode":{"addressComponent":{"city":"文山壮族苗族自治州","province":"云南省","adcode":"532627","district":"广南县","towncode":"532627107000","streetNumber":{"number":[],"direction":[],"distance":[],"street":[]},"country":"中国","township":"坝美镇","businessAreas":[[]],"building":{"name":[],"type":[]},"neighborhood":{"name":[],"type":[]},"citycode":"0876"},"formatted_address":"云南省文山壮族苗族自治州广南县坝美镇龙暮"},"info":"OK","infocode":"10000"})
     * </p>
     *
     */
    public static final String LOCATION = "https://restapi.amap.com/v3/geocode/regeo?key=8325164e247e15eea68b59e89200988b&s=rsv3&location=105.003731,24.269511&radius=2800&callback=jsonp_991766_&platform=JS&logversion=2.0&sdkversion=1.3&appname=https%3A%2F%2Flbs.amap.com%2Fconsole%2Fshow%2Fpicker&csid=9EC80466-6B19-48CB-8E2E-8C21BB2D24A1";

    /***
     * 将上面这个地址中的 &callback=jsonp_991766_ 参数去掉，返回的就是一个JSON格式了。
     * 返回值：{"status":"1","regeocode":{"addressComponent":{"city":"文山壮族苗族自治州","province":"云南省","adcode":"532627","district":"广南县","towncode":"532627107000","streetNumber":{"number":[],"direction":[],"distance":[],"street":[]},"country":"中国","township":"坝美镇","businessAreas":[[]],"building":{"name":[],"type":[]},"neighborhood":{"name":[],"type":[]},"citycode":"0876"},"formatted_address":"云南省文山壮族苗族自治州广南县坝美镇龙暮"},"info":"OK","infocode":"10000"}
     */
    public static final String LOCATIONNEW = "https://restapi.amap.com/v3/geocode/regeo?key=8325164e247e15eea68b59e89200988b&s=rsv3&location=105.003731,24.269511&radius=2800&platform=JS&logversion=2.0&sdkversion=1.3&appname=https%3A%2F%2Flbs.amap.com%2Fconsole%2Fshow%2Fpicker&csid=9EC80466-6B19-48CB-8E2E-8C21BB2D24A1";


    /**
     * 接口调用 GET
     */
    public static void httpURLConectionGET() {
        try {
            URL url = new URL(GET_URL);    // 把字符串转换为URL请求地址
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开连接
            connection.connect();// 连接会话
            // 获取输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {// 循环读取流
                sb.append(line);
            }
            br.close();// 关闭流
            connection.disconnect();// 断开连接
            System.out.println("Get=="+sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("失败!");
        }
    }

    /**
     * 接口调用  POST
     */
    public static void httpURLConnectionPOST () {
        try {
            URL url = new URL(POST_URL);

            // 将url 以 open方法返回的urlConnection  连接强转为HttpURLConnection连接  (标识一个url所引用的远程对象连接)
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 此时cnnection只是为一个连接对象,待连接中

            // 设置连接输出流为true,默认false (post 请求是以流的方式隐式的传递参数)
            connection.setDoOutput(true);

            // 设置连接输入流为true
            connection.setDoInput(true);

            // 设置请求方式为post
            connection.setRequestMethod("POST");

            // post请求缓存设为false
            connection.setUseCaches(false);

            // 设置该HttpURLConnection实例是否自动执行重定向
            connection.setInstanceFollowRedirects(true);

            // 设置请求头里面的各个属性 (以下为设置内容的类型,设置为经过urlEncoded编码过的from参数)
            // application/x-javascript text/xml->xml数据
            //application/x-javascript->json对象
            //application/x-www-form-urlencoded->表单数据
            //这是重点^_^
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // 建立连接 (请求未开始,直到connection.getInputStream()方法调用时才发起,以上各个参数设置需在此方法之前进行)
            connection.connect();

            // 创建输入输出流,用于往连接里面输出携带的参数,(输出内容为?后面的内容)
            DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());
            String parm = "storeId=" + URLEncoder.encode("32", "utf-8"); //URLEncoder.encode()方法  为字符串进行编码

            // 将参数输出到连接
            dataout.writeBytes(parm);

            // 输出完成后刷新并关闭流
            dataout.flush();
            dataout.close(); // 重要且易忽略步骤 (关闭流,切记!)

            System.out.println(connection.getResponseCode());

            // 连接发起请求,处理服务器响应  (从连接获取到输入流并包装为bufferedReader)
            BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder(); // 用来存储响应数据

            // 循环读取流,若不到结尾处
            while ((line = bf.readLine()) != null) {
                sb.append(bf.readLine());
            }
            bf.close();    // 重要且易忽略步骤 (关闭流,切记!)
            connection.disconnect(); // 销毁连接
            System.out.println("Post=="+sb.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据经度和维度获取相关信息
     * 经度和维度已经写在地址里面了。
     */
    public static void httpURLLocationGET() {
        try {
            URL url = new URL(LOCATIONNEW);    // 把字符串转换为URL请求地址
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开连接
            connection.connect();// 连接会话
            // 获取输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {// 循环读取流
                sb.append(line);
            }
            br.close();// 关闭流
            connection.disconnect();// 断开连接
            System.out.println(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("失败!");
        }
    }

    public static void main(String[] args) {
//         httpURLConectionGET();
        httpURLConectionGET();
        // post这个测试无效
        httpURLConnectionPOST();

        httpURLLocationGET();
    }
}


