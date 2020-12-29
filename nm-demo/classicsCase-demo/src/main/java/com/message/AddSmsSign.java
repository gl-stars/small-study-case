//package com.message;
//
//import java.rmi.ServerException;
//
///**
// *  短信测试
// *  <p>阿里大鱼
// *  <a href="https://api.aliyun.com/?spm=a2c4g.11186623.2.18.933960e2fDlpTC#/?product=Dysmsapi&version=2017-05-25&api=AddSmsSign&params={"RegionId":"cn-hangzhou","SignName":"赤练科技有限公司","SignSource":"吼吼科技","Remark":"这时一个短信验证码","SignFileList":[{"FileSuffix":"asdf","FileContents":"asdfasdf"},{"FileSuffix":"","FileContents":""}]}&tab=DEMO&lang=JAVA"></a></p>
// * @author: gl_stars
// * @data: 2020年 09月 29日 17:14
// **/
//public class AddSmsSign {
//    public static void main(String[] args) {
//        DefaultProfile profile = DefaultProfile.getProfile("https://dysmsapi.aliyuncs.com", "LTAI4G7GWCGrg4KCAwZnJX1t", "cRw5vaD0ByqsEjD4EvGDWfLdIHHpvH");
//        IAcsClient client = new DefaultAcsClient(profile);
//
//        CommonRequest request = new CommonRequest();
//        request.setSysMethod(MethodType.POST);
//        request.setSysDomain("dysmsapi.aliyuncs.com");
//        request.setSysVersion("2017-05-25");
//        request.setSysAction("AddSmsSign");
//        request.putQueryParameter("RegionId", "cn-hangzhou");
//        request.putQueryParameter("SignName", "赤练科技有限公司");
//        request.putQueryParameter("SignSource", "吼吼科技");
//        request.putQueryParameter("Remark", "这时一个短信验证码");
//        request.putQueryParameter("SignFileList.1.FileSuffix", "asdf");
//        request.putQueryParameter("SignFileList.1.FileContents", "asdfasdf");
//        try {
//            CommonResponse response = client.getCommonResponse(request);
//            System.out.println(response.getData());
//        } catch (ServerException e) {
//            e.printStackTrace();
//        } catch (ClientException e) {
//            e.printStackTrace();
//        }
//    }
//}
