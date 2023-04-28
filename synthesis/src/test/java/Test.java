import feature.CreateVideo;
import lombok.SneakyThrows;
import util.Audio;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * @Author:DaOcean
 * @DATE:2023/4/4 17:30
 */
public class Test {

//    ffmpeg.exe  -i  audio提取版.mp3 -ss 00:00:00  -t  00:01:00  audio截取版.mp3
private static final String ffmpegPath = System.getProperty("user.dir") + "\\ffmpeg\\bin\\ffmpeg.exe";
    @SneakyThrows
    public static void main(String[] args){
//        Audio.addAudioMp3("D:\\data bank\\github\\Repository\\synthesis\\ffmpeg\\bin\\ffmpeg.exe","D:\\阿里云盘\\outVideo1\\测试1.mp4","D:\\阿里云盘\\背景音乐\\美食BGM\\美食音乐(19).mp3","D:\\阿里云盘\\outVideo1");
//        String time=Audio.getVideoTime("D:\\阿里云盘\\配音文件.mp3","D:\\data bank\\github\\Repository\\synthesis\\ffmpeg\\bin\\ffmpeg.exe");
//        String time1=Audio.getVideoTime("D:\\阿里云盘\\配音文件1.mp3","D:\\data bank\\github\\Repository\\synthesis\\ffmpeg\\bin\\ffmpeg.exe");
//        String time3=Audio.getVideoTime("D:\\阿里云盘\\配音文件无符号.mp3","D:\\data bank\\github\\Repository\\synthesis\\ffmpeg\\bin\\ffmpeg.exe");
//        String time4=Audio.getVideoTime("D:\\阿里云盘\\配音文件1无符号.mp3","D:\\data bank\\github\\Repository\\synthesis\\ffmpeg\\bin\\ffmpeg.exe");
//
//        System.out.println("有符号5字符:"+time);
//        System.out.println("有符号10字符:"+time1);
//        System.out.println("无符号5字符:"+time3);
//        System.out.println("无符号10字符:"+time4);
//        String format = "%s -i %s -i %s -filter_complex amix=inputs=2:duration=first:dropout_transition=2 -f mp3 %s";
//        String music="D:\\阿里云盘\\outVideo1\\music.mp3";
//        String dub="D:\\阿里云盘\\outVideo1\\配音文件.mp3";
//        String com="D:\\阿里云盘\\outVideo1\\带配音.mp3";
//        String command=String.format(format,ffmpegPath,music,dub,com);
//        CreateVideo.execCommand(command);

        Path path = Paths.get("D:\\阿里云盘\\口播文案\\配音(1).txt");
        byte[] data = Files.readAllBytes(path);
        String result = new String(data, "utf-8");
        String[] txt=result.split("，");
        for (String s : txt) {
            System.out.println(s);
        }

    }

}
