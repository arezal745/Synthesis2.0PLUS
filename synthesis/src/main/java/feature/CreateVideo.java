package feature;

import util.Audio;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author 阿枫
 * 合成视频
 */
public class CreateVideo {

    /**
     * ffmpeg软件路径
     */
    private static final String FFMPEG_PATH = System.getProperty("user.dir") + "\\ffmpeg\\bin\\ffmpeg.exe";
    /**
     * 时间格式
     */
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 视频路径
     */
    private static  String videoPath;
    /**
     * 合成数量
     */
    int n;
    /**
     * 酒店名字
     */
    private static String name;
    /**
     * 音频文件
     */
    private static ArrayList<String> musicList;
    /**
     * 视频存储路径
     */
    public static String outPutPath;
    /**
     * 口播文件路径
     */
    private static ArrayList<String> dubList;
    public static String dubPath;
    /**
     * 口播文案路径
     */
    public static String dubTxtPath;

    public static int aiMode;

    public static int musicMode;

    public static int waterMarkMode;

    static Random random = new Random();

    public CreateVideo(String videoP, int modeNumber, String hotelName, int targetNumber, int noNumber, String outputPath,String musicPath,String dubPath,String dubTxtPath,int mode1,int mode2,int mode3) {

        System.out.println(musicPath);
        videoPath = videoP;
        name = hotelName;
        n = targetNumber;
        musicList=getFilePath(musicPath);
        outPutPath=outputPath;
        waterMarkMode=mode3;
        if(dubPath!=null){
            dubList=getFileName(dubPath);
        }else {
            dubList=null;
        }
        CreateVideo.dubPath =dubPath;
        CreateVideo.dubTxtPath=dubTxtPath;
        aiMode=mode1;
        musicMode=mode2;
        if(modeNumber == 0){
            // ./video是0随机合成模式
            ArrayList<String> dirs = getFilePath(videoPath);
            System.out.println("当前素材数量："+dirs.size());
            System.out.println("极限不重复数量："+max(dirs.size()));
            System.out.println("请输入合成数量："+n);
            System.out.println("请输入酒店名称："+name);
            for (int i = 0; i < n; i++) {
                int[] arr = unSet(dirs.size());
                List<Object> list = new ArrayList<>();
                list.add(dirs.get(arr[0]));
                list.add(dirs.get(arr[1]));
                list.add(dirs.get(arr[2]));
                System.out.println("源头序列号："+(i+1));
                m4(i+1, list, noNumber, outputPath);
            }
        }else if (modeNumber == 1){
            //这里是模式1的
            System.out.println("素材路径："+videoPath);
            Map<String,ArrayList<String>> listMap=new HashMap<>();
            File rootDirs = new File(videoPath);
            if(rootDirs.isDirectory()) {
                String[] list = rootDirs.list();
                for(int i = 0; i< Objects.requireNonNull(list).length; i++) {
                    File dirs = new File(videoPath+"\\"+list[i]);
                    if(dirs.isDirectory()) {
                        System.out.println("文件夹："+list[i]);
                        listMap.put("dirs"+(i+1),getFilePath(videoPath+"\\"+list[i]));
                    }else {
                        System.out.println("文件："+list[i]);
                    }
                }
            }
            int max=1;
            for(int i=0;i<listMap.size();i++)
            {
                System.out.println("当前v"+(i+1)+"文件夹素材数量："+listMap.get("dirs"+(i+1)).size());
                //计算最大不重复视频合成数量
                max*=listMap.get("dirs"+(i+1)).size();
            }
            System.out.println("极限不重复数量："+max);
            System.out.println("请输入合成数量："+n);
            System.out.println("请输入酒店名称："+name);
            name = hotelName;
            for (int i = 0; i < targetNumber; i++) {
                List<Object> list = new ArrayList<>();
                //获取每个文件夹一个随机素材
                for(int j=0;j<listMap.size();j++)
                {
                    ArrayList<String> arrayList=listMap.get("dirs"+(j+1));
                    int a=random.nextInt(arrayList.size());
                    list.add(arrayList.get(a));
                }
                System.out.println("源头序列号："+(i+1));
                m4(i+1, list, noNumber, outputPath);
            }
        }else{
            System.out.println("请正确输入");
        }



    }

    public static int max(int n){
        return n*(n-1)*(n-2)/6;
    }

    /**
     * 获取随机排列
     */
    public static int[] unSet(int n) {
        int[] arr = {0, 0, 0};

        arr[0] = random.nextInt(n);
        do {
            arr[1] = random.nextInt(n);
        } while (arr[1] == arr[0]);
        do {
            arr[2] = random.nextInt(n);
        } while (arr[2] == arr[0] || arr[2] == arr[1]);
        System.out.println("随机素材序列号"+arr[0]+" "+arr[1]+" "+arr[2]);
        return arr;
    }

    /**
     * 获取所有素材路径+文件名
     */
    public static ArrayList<String> getFilePath(String path) {
        ArrayList<String> filePathList = new ArrayList<>();
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File value : Objects.requireNonNull(files)) {
                filePathList.add(value.getPath());
            }
        } else {
            System.out.println("这不是一个文件夹");
        }
        return filePathList;
    }

    /**
     * 获取所有素材文件名
     */
    public static ArrayList<String> getFileName(String path) {
        ArrayList<String> filePathList = new ArrayList<>();
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < Objects.requireNonNull(files).length; i++) {
                filePathList.add(files[i].getName());
            }
        } else {
            System.out.println("这不是一个文件夹");
        }
        return filePathList;
    }

    /**
     * 封装参数调用合成视频
     */
    @SuppressWarnings("rawtypes")
    private static void m4(int i, List list, int number, String outputPath) {
        i+=number-1;
        String outputDir = outputPath + "\\";
        String output = outputPath + "\\video" + i + ".mp4";
        System.out.println("OutPutFileLocation====>"+output);
        mergeVideo(list, outputDir, output);
    }

    /**
     * 合成视频，去原声加水印
     */
    @SuppressWarnings({"ResultOfMethodCallIgnored", "StatementWithEmptyBody"})
    public static String mergeVideo(List<String> list, String outputDir, String outputFile) {
        System.out.println("去原声，生成ts文件:");
        try {
            //把素材转换成ts格式
            String format1 = "%s -i %s -c copy -bsf:v h264_mp4toannexb -f mpegts %s";
            StringBuilder format3= new StringBuilder(FFMPEG_PATH + " -i \"concat:");
            HashMap<String,String> hashMap=new HashMap<>();
            for(int i=0;i<list.size();i++)
            {
                hashMap.put("command"+(i+1),String.format(format1, FFMPEG_PATH, list.get(i), outputDir + "input"+(i+1)+".ts"));
                format3.append(outputDir).append("input").append(i + 1).append(".ts").append("|");
            }
            format3 = new StringBuilder(format3.substring(0, format3.length() - 1));
            //ts格式文件合成一个MP4文件，去掉原声
            format3.append("\" -c copy -an -bsf:a aac_adtstoasc -movflags +faststart ").append(outputFile);

            for (int i = 0; i < hashMap.size(); i++) {
                if(execCommand(hashMap.get("command"+(i+1)))>0){

                }
                else {
                    return "0";
                }
            }
              if (execCommand(format3.toString()) > 0) {

            //在视频前一秒加水印
                String format4 = "%s -i %s -vf drawtext=fontcolor=white:fontsize=30:fontfile=C\\\\:/Windows/Fonts/msyh.ttc:text='%s':x=10:y=10:enable='lte(t\\,1)' -y %s";
                String n = (outputFile.split("video")[1]).split("\\.")[0];
                String command6 = String.format(format4,FFMPEG_PATH,outputFile,name+n,outputDir + name+"music"+ n + ".mp4");
                  if (waterMarkMode == 1) {
                      System.out.println("添加水印:");
                  }
                if(waterMarkMode==1&&execCommand(command6)>0){
                    File file = new File(outputFile);
                    //noinspection ResultOfMethodCallIgnored
                    file.delete();
                    for (int i = 0; i < hashMap.size(); i++) {
                    File file1 = new File(outputDir + "input"+(i+1)+".ts");
                        //noinspection ResultOfMethodCallIgnored
                        file1.delete();
                    }
                    if(musicMode==1){
                        Audio.addAudioMp3(FFMPEG_PATH,outputDir + name +"music"+ n + ".mp4",musicList.get(random.nextInt(musicList.size())),outPutPath,name,n,dubList,aiMode);
                    }
                }else{
                    for (int i = 0; i < hashMap.size(); i++) {
                        File file1 = new File(outputDir + "input"+(i+1)+".ts");
                        file1.delete();
                    }
                    Audio.addAudioMp3(FFMPEG_PATH,outputFile,musicList.get(random.nextInt(musicList.size())),outPutPath,name,n,dubList,aiMode);
                }
                  System.out.println("完成------阿枫真棒！！！");
                  return "1";
            } else {
                return "0";
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("合并失败------是不是选错文件夹了？？？" + outputFile);
            return "0";
        }
    }

    /**
     * 调用命令行
     */
    public static Integer execCommand(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            //获取进程的标准输入流
            final InputStream is1 = process.getInputStream();
            //获取进程的错误流
            final InputStream is2 = process.getErrorStream();
            //启动两个线程，一个线程负责读标准输出流，另一个负责读标准错误流
            readInputStream(is1);
            readInputStream(is2);
            process.waitFor();
            process.destroy();
            System.out.println("-----操作成功" + command + " " + SIMPLE_DATE_FORMAT.format(new Date()));
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("-----操作失败" + command);
            return -1;
        }
    }

    /**
     * 读写磁盘
     */
    @SuppressWarnings("StatementWithEmptyBody")
    private static void readInputStream(InputStream inputStream) {
        new Thread(() -> {
            BufferedReader br1 = new BufferedReader(new InputStreamReader(inputStream));
            try {
                while (br1.readLine() != null) {
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
