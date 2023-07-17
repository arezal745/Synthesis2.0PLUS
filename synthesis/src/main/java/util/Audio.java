package util;

import feature.CreateVideo;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 阿枫
 * 背景音乐与AI口播
 */
public class Audio {
    /**
     * 获取视频时长
     */
    public static String getVideoTime(String videoPath, String ffmpegPath) {
        List<String> commands = new ArrayList<>();
        commands.add(ffmpegPath);
        commands.add("-i");
        commands.add(videoPath);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            final Process p = builder.start();

            //从输入流中读取视频信息
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            //从视频信息中解析时长
            String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb/s";
            Pattern pattern = Pattern.compile(regexDuration);
            Matcher m = pattern.matcher(sb.toString());
            if (m.find()) {
                return m.group(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }


//    ffmpeg -i video.mp4 -i audio.wav -c:v copy -c:a aac -strict experimental output.mp4
//    ffmpeg -i out.mp4 -i out.aac -vcodec copy -acodec copy new.mp4

    /**
     * @param ffmpegPath 剪辑程序
     * @param outputVideo 视频路径+文件名
     * @param music  背景音乐路径+文件名
     * @param outputPath 最终存储路径
     * @param name 酒店名
     * @param n 视频序列号
     * @param dubList 文案文件名
     * @param aiMode 文案文件名
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SneakyThrows
    public static void addAudioMp3(String ffmpegPath, String outputVideo, String music, String outputPath, String name, String n, ArrayList<String> dubList, int aiMode){
        try {
            System.out.println("添加背景音乐:");
            String dubName=null;
            if(dubList!=null){
                Random random=new Random();
                dubName=dubList.get(random.nextInt(dubList.size()));
            }

            String dubPath=CreateVideo.dubPath+"\\"+dubName;
            //获取视频时长
            String time=getVideoTime(outputVideo,ffmpegPath);
            //背景音乐路径
            String musicpath=outputPath+"\\music.mp3";
            //口播音频路径
            String dubPlusPath;
            //音频截取命令行
            String format = "%s -i %s -ss 00:00:00 -t %s %s";
            String command=String.format(format,ffmpegPath,music,time,outputPath+"\\music.mp3");
            CreateVideo.execCommand(command);

            if(aiMode==1){
                //合成口播音频
                dubPlusPath=addDubMp3(ffmpegPath,musicpath,dubPath);
                //合成字幕
                outputVideo=addDubTxt(ffmpegPath,outputVideo,dubName);
            }
            else {
                dubPlusPath=outputPath+"\\music.mp3";
            }

            //合成音频命令行
            String format1 = "%s -i %s -i %s -vcodec copy -acodec copy %s";
            String command1 = String.format(format1,ffmpegPath,outputVideo,dubPlusPath,outputPath+"\\"+name+n+".mp4");
            if(CreateVideo.execCommand(command1)>0){
                //删除无用文件
                File file=new File(dubPlusPath);
                file.delete();
                file=new File(outputVideo);
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("合并失败------是不是选错文件夹了？？？");
        }
    }

    /**
     * 合成口播
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static String addDubMp3(String ffmpegPath, String musicpath, String dubPath){
        System.out.println("合成口播音频:");
        String format1="%s -i %s -filter:a \"volume=2.0\" %s";
        String dubOutPath=CreateVideo.outPutPath+"\\dubs.mp3";
        String command1=String.format(format1,ffmpegPath,dubPath,dubOutPath);
        if(CreateVideo.execCommand(command1)>0){

        }
        String format = "%s -i %s -i %s -filter_complex amix=inputs=2:duration=longest:dropout_transition=2 -f mp3 %s";
        String com=CreateVideo.outPutPath+"\\dub.mp3";
        String command=String.format(format,ffmpegPath,musicpath,dubOutPath,com);
        if(CreateVideo.execCommand(command)>0) {
            //删除无用文件
            File file = new File(musicpath);
            file.delete();
            file=new File(dubOutPath);
            file.delete();
            return com;
        }
        else
        {
            System.out.println("合成口播失败");
            return "";
        }
    }

    /**
     * 添加文案
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SneakyThrows
    public static String addDubTxt(String ffmpegPath,String outputVideo, String dubName){
        try {
            System.out.println("合成文案字幕:");
            //获取口播音频对应文字
            Path path = Paths.get(CreateVideo.dubTxtPath+"\\"+dubName.split("\\.")[0]+".txt");
            byte[] data = Files.readAllBytes(path);
            String result = new String(data, StandardCharsets.UTF_8);
            String[] txt=result.split("，");

            //合成文案命令行
            String format = "%s -i %s -vf drawtext=fontcolor=white:fontsize=%s:fontfile=C\\\\:/Windows/Fonts/msyh.ttc:text='%s':x=%s:y=%s:enable='between(t,%s,%s)' -y %s";
            String command;
            int min;
            int max=0;
            File file;
            String[] fps=countPosition(ffmpegPath,outputVideo);

            for(int i=0;i<txt.length;i++)
            {
                //计算每次插入字幕的时间
                min=max;
//            min=max+1;
                if (i == txt.length - 1) {
                    if (txt[i].charAt(txt[i].length() - 1) == '。') {
                        txt[i] = txt[i].substring(0, txt[i].length() - 1);
                    }
                }
                max+=count(txt[i].length());
                //字母位置
                int y=Integer.parseInt(fps[1])-(Integer.parseInt(fps[1])/4);
                int fontSize=Integer.parseInt(fps[0])/24;
                int x= (Integer.parseInt(fps[0])-(txt[i].length()*fontSize))/2;
                command = String.format(format,ffmpegPath,outputVideo,fontSize,txt[i],x,y,min,max,CreateVideo.outPutPath+"\\"+"口播"+i+".mp4");
                if(CreateVideo.execCommand(command)>0){
                    file=new File(outputVideo);
                    file.delete();
                }
                outputVideo=CreateVideo.outPutPath+"\\"+"口播"+i+".mp4";
            }
            return CreateVideo.outPutPath+"\\"+"口播"+(txt.length-1)+".mp4";
        } catch (Exception e) {
        e.printStackTrace();
        System.out.println("合并失败------是不是选错文件夹了？？？");
        return "0";
    }

    }

    public static int count(int number){
        if(number<=6){
            return 1;
        }else if(number<=11){
            return 2;
        }else if(number<=16){
            return 3;
        }else if(number<=21){
            return 4;
        }else if(number<=26){
            return 5;
        } else if(number<=32){
            return 6;
        }else {
            return 7;
        }
    }

    public static String[] countPosition(String ffmpeg, String path){
        //拼接cmd命令语句
        StringBuilder buffer = new StringBuilder();
        buffer.append(ffmpeg);
        //注意要保留单词之间有空格
        buffer.append(" -i ");
        buffer.append(path);
        String[] xy = new String[2];
//        执行命令语句并返回执行结果
        try {
            Process process = Runtime.getRuntime().exec(buffer.toString());
            InputStream in = process.getErrorStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line ;
            while((line=br.readLine())!=null) {
                if(line.trim().startsWith("Duration:")){
                    //根据字符匹配进行切割
                    System.out.println("视频时间 = "  + line.trim().substring(0,line.trim().indexOf(",")));
                }
//                分辨率
                if(line.contains("fps")){
                    System.out.println(line);
                    String definition = line.split(",")[2];
                    definition = definition.trim().split(" ")[0];
                    if("bt709".equals(definition))
                    {
                        definition=line.substring(98,107);
                    }
                    System.out.println("分辨率=" + definition);

                    xy=definition.split("x");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return xy;
    }
}
