package com.example.academicadministration.utils;

import com.cloopen.rest.sdk.BodyType;
import com.cloopen.rest.sdk.CCPRestSmsSDK;

import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class SendMessage {

    public static String message(String phone){
        //返回产生的验证码
        StringBuilder code= new StringBuilder();
        //生产环境请求地址：app.cloopen.com
        String serverIp = "app.cloopen.com";
        //请求端口
        String serverPort = "8883";
        //主账号,登陆云通讯网站后,可在控制台首页看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN
        String accountSId = "2c94811c87c2d4870187e77bd3c20668";
        String accountToken = "1d0758657d344193bf3bc76ca734046a";
        //请使用管理控制台中已创建应用的APPID
        String appId = "2c94811c87c2d4870187e77bd506066f";
        CCPRestSmsSDK sdk = new CCPRestSmsSDK();
        sdk.init(serverIp, serverPort);
        sdk.setAccount(accountSId, accountToken);
        sdk.setAppId(appId);
        sdk.setBodyType(BodyType.Type_JSON);
        //手机号码
        String to = phone;
        String templateId= "1";//使用的模板id
        //生成四位随机数
        String s[]={"0","1","2","3","4","5","6","7","8","9"};
        Random random=new Random();
        for(int i=0;i<4;i++){
            code.append(s[random.nextInt(10000)%10]);
        }
        System.out.println(code);
        String[] datas = {String.valueOf(code),"2"};//格式:你的验证码是{code}，请于{2}分钟内正确输入
        //HashMap<String, Object> result = sdk.sendTemplateSMS(to,templateId,datas);
        HashMap<String, Object> result = sdk.sendTemplateSMS(to,templateId,datas);
        if("000000".equals(result.get("statusCode"))){
            //正常返回输出data包体信息（map）
            HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for(String key:keySet){
                Object object = data.get(key);
                System.out.println(key +" = "+object);
            }
        }else{
            //异常返回输出错误码和错误信息
            System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
        }
        return String.valueOf(code);
    }
}
