package feature;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author 86184
 * 下载url链接MP3文件
 */
public class URLFileSaver implements Runnable {

    private static final int BUFFER_SIZE = 4096;
    private String destUrl;
    private String fileName;

    public URLFileSaver(String destUrl,String path,String fileName)
    {
        this.destUrl = destUrl;
        //获取文件名
//        int i = destUrl.lastIndexOf("/");
        this.fileName = path + fileName+".mp3";
    }

    @Override
    public void run() {
        try {
            saveToFile(destUrl,fileName);

            System.out.println("文件："+destUrl+"下载完成，保存为"+fileName);
        } catch (IOException e) {
            System.out.println("文件下载失败，信息："+e.getMessage());
        }
    }

    // 将网络文件保存为本地文件的方法
    // @param destUrl
    // @param fileName
    // @throws IOException

    public void saveToFile(String destUrl, String fileName) throws IOException {
        FileOutputStream fos;
        BufferedInputStream bis;
        HttpURLConnection httpconn;
        URL url;
        byte[] buf = new byte[BUFFER_SIZE];
        int size = 0;

        // 建立链接
        url = new URL(destUrl);
        httpconn = (HttpURLConnection) url.openConnection();
        // 连接指定的资源
        httpconn.connect();
        // 获取网络输入流
        bis = new BufferedInputStream(httpconn.getInputStream());
        // 建立文件
        fos = new FileOutputStream(fileName);

        System.out.println("正在获取链接[" + destUrl + "]的内容\n将其保存为文件[" + fileName
                + "]");

        // 保存文件
        while ((size = bis.read(buf)) != -1) {
            fos.write(buf, 0, size);
        }
        fos.close();
        bis.close();
        httpconn.disconnect();
    }

    public static void DownURLFile(String url,String path,String fileName){
        Thread th = new Thread(new URLFileSaver(url,path,fileName));
        th.start();
    }

}
