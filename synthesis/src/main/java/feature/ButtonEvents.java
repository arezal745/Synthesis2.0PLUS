package feature;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * @Author:DaOcean
 * @DATE:2023/4/17 17:27
 * 选择素材路径
 */
public class ButtonEvents {

    public static class selectDirectoryTrigger implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
           //在新窗口选择目标文件夹
            JFrame jfTop = new JFrame("选择文件夹");
            //设置窗口大小
            jfTop.setSize(800,900);
            //把窗口定位到屏幕中心
            jfTop.setLocationRelativeTo(null);
            //设置默认打开的路径
            String defaultDirectoryPath = System.getProperty("user.dir");
            //选择目录
            JFileChooser targetFilesChooser = new JFileChooser(defaultDirectoryPath);
            //设置只能选择目录而不是文件
            targetFilesChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            // 打开文件选择器
            int result = targetFilesChooser.showOpenDialog(null);
            // 如果用户选择了一个文件
            String filePath = "";
            if (result == JFileChooser.APPROVE_OPTION) {
                // 获取用户选择的文件路径
                File selectedFile = targetFilesChooser.getSelectedFile();
                filePath = selectedFile.getAbsolutePath();
                // 打印用户选择的文件路径
                System.out.println("Selected file: " + filePath);
            }
        }
    }




    public String selectDirectoryEvent(){

        String directoryPath ="";
        return directoryPath;
    }

}
