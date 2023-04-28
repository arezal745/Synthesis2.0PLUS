package feature;


import java.io.File;
import java.text.SimpleDateFormat;

import static feature.CreateVideo.execCommand;

/**
 * @Author:DaOcean
 * @DATE:2023/4/14 14:45
 */
public class EditVideo {

    private static final String ffmpegPath = System.getProperty("user.dir") + "\\ffmpeg\\bin\\ffmpeg.exe";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //合成视频去原声
    public void tBEo (String video1, String video2, String outputDir) {
        //把素材转换成ts格式
        String format1 = "%s -i %s -c copy -bsf:v h264_mp4toannexb -f mpegts %s";
        String command1 = String.format(format1, ffmpegPath, video1, outputDir + "input1.ts");
        String command2 = String.format(format1, ffmpegPath, video2, outputDir + "input2.ts");
        //ts格式文件合成一个MP4文件，去掉原声
        String format2 = "%s -i \"concat:%s|%s\" -c copy -an -bsf:a aac_adtstoasc -movflags +faststart %s";
        String command3 = String.format(format2, ffmpegPath, outputDir + "input1.ts", outputDir + "input2.ts", outputDir);

        if (execCommand(command1)>0&&execCommand(command2)>0&&execCommand(command3)>0) {
            File file1 = new File(outputDir + "input1.ts");
            File file2 = new File(outputDir + "input2.ts");
            file1.delete();
            file2.delete();
        }
    }


}
