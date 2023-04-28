package util;


import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 阿枫
 * @create 2023/4/14
 */
@Slf4j
public class HttpUtil {
    /**
     * 以post方式调用第三方接口,以form-data 形式  发送 MultipartFile 文件数据
     *
     * @param url              post请求url
     * @param otherFormDataMap 表单中的其他参数
     * @param connectTimeout   连接超时时间
     * @return 返回 接口返回结果
     */
    public static String doPostFormDataFile(String url,Map<String, String> otherFormDataMap, int connectTimeout) throws IOException {
//        File toFile = transferToFile(multipartFile);
        Map<String, Object> data = new HashMap<>();
//        data.put(fileParamName, toFile);
        data.putAll(otherFormDataMap);
        long t1 = System.currentTimeMillis();
        log.info("doPostFormDataFile -- 开始接口调用，otherFormDataMap：{}，fileName：{}", JSON.toJSONString(otherFormDataMap));
        String response = HttpRequest.post(url)
                .form(data)
                .contentType("multipart/form-data")
                .setConnectionTimeout(connectTimeout)
                .execute()
                .body();
        long t2 = System.currentTimeMillis();
        log.info("doPostFormDataFile -- 调用接口结束，耗时：{}，otherFormDataMap：{}，fileName：{}，响应结果：{}", t2 - t1, JSON.toJSONString(otherFormDataMap), response);
        return response;
    }


//    public static void main(String[] args) {
//        Map<String,String> map=new HashMap<>();
//        map.put("language","中文（普通话，简体）");
//        map.put("voice","zh-CN-XiaomoNeural");
//        map.put("text","你好。");
//        map.put("role","0");
//        map.put("style","0");
//        map.put("styledegree","1");
//        map.put("rate","0");
//        map.put("pitch","0");
//        map.put("kbitrate","audio-16khz-32kbitrate-mono-mp3");
//        map.put("silence","");
//        map.put("user_id","0");
//        try {
//            HttpUtil.doPostFormDataFile("https://www.text-to-speech.cn/getSpeek.php",map,5000);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}