package util;

import lombok.SneakyThrows;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

/**
 * @author 阿枫
 * @create 2023/6/15
 */
public class EditTestFile {
    @SneakyThrows
    public static void main(String[] args) {
        Scanner input=new Scanner(System.in);
        Path path = Paths.get("D:\\阿里云盘\\口播文案\\all.txt");
        List<String> lines = Files.readAllLines(path);
        System.out.print("请输入酒店名称:");
        String hotelName=input.next();
        File file = new File("D:\\阿里云盘\\口播文案\\"+hotelName);
        if(!file.exists()) {
            file.mkdirs();
            System.out.println("文件夹创建成功！");
        } else {
            System.out.println("文件夹已存在！");
        }
        for(int i=0;i<lines.size();i++)
        {
            file=new File("D:\\阿里云盘\\口播文案\\"+hotelName+"\\口播文件"+(i+1)+".txt");
            file.createNewFile();
            //true表示追加内容
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("D:\\阿里云盘\\口播文案\\"+hotelName+"\\口播文件"+(i+1)+".txt", false));
            bufferedWriter.write(lines.get(i));
            bufferedWriter.close();
        }
    }
}
