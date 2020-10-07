package com.blueeyescloud.baselib.net;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Utils {
    //按升序排序
    public static String getParamStringByAsec(Map<String, Object> params){
        StringBuffer stringBuffer = new StringBuffer();
        List<String> keyList = new ArrayList<>(params.keySet());
        Collections.sort(keyList);
        for(int i = 0; i < keyList.size(); i++){
            String key = keyList.get(i);
            if(i == keyList.size() - 1){
                stringBuffer.append(key).append("=").append(params.get(key));
            }else{
                stringBuffer.append(key).append("=").append(params.get(key)).append("&");
            }
        }
        return stringBuffer.toString();
    }





//    public static void main(String[] args) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("resourceId", "234");
//        params.put("attribute", "asgadg");
//        params.put("name", "namagag");
//        String sortedParams = getParamStringByAsec(params);
//
//        System.out.println("sortedParams = " + sortedParams);
//
//        String certId = Build.CPU_ABI;
//        String userName = Build.CPU_ABI;
//
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(certId)
//                .append("@")
//                .append(System.currentTimeMillis())
//                .append(":")
//                .append(userName);
//
//        String preEncodeAuthString = stringBuilder.toString();
//
//        String auth = Base64.encode(preEncodeAuthString);
//
//        stringBuilder.append(sortedParams);
//        String signature = SignUtil.sha1WithRsaSign(stringBuilder.toString(), privateKey);
//    }
}
