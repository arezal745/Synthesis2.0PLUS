package GUI;

import feature.NumberDocument;
import feature.CreateVideo;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * @author 阿枫
 * GUI
 */
public class mainFrameWindow {

    /**
     * 视频素材路径
     */
    String videoPath;
    /**
     * 选择视频素材路径
     */
    private JLabel selectPath;

    /**
     * 存储路径
     */
    String savePath;
    /**
     * 选择存储路径
     */
    private JLabel outputPath;

    /**
     * 选择背景音乐路径
     */
    String musicPath;
    private JLabel selectMusic;

    /**
     * 选择背景音乐路径
     */
    String dubPath;
    private JLabel selectDub;

    /**
     * 选择背景音乐路径
     */
    String dubTxtPath;
    private JLabel selectDubTxt;

    @SuppressWarnings("StatementWithEmptyBody")
    public mainFrameWindow(){
        //创建一个顶层容器-窗口
        JFrame jfTop = new JFrame("云剪2.0PLUS");
        //设置窗口大小
        jfTop.setSize(650,700);
        //把窗口定位到屏幕中心
        jfTop.setLocationRelativeTo(null);
        // 当点击窗口的关闭按钮时退出程序（没有这一句，程序不会退出）
        jfTop.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //设置窗口的主题样式跟随系统
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException |
                 InstantiationException e) {
            throw new RuntimeException(e);
        }
        //创建中间容器（面板容器）
        JPanel panelAddFile = new JPanel();
        panelAddFile.setLayout(new GridLayout(13,3,20,20));
        Border border = BorderFactory.createLineBorder(Color.BLACK);

        selectPath = new JLabel("选择素材所在的文件夹");
        //创建一个基本组件（按钮），并添加到 面板容器 中
        JButton targetFilesChooserBtn = new JButton("选择目标文件夹");
        //添加按钮点击事件
        targetFilesChooserBtn.addActionListener(new targetFilesChooserEvent());


        //添加一个输入框，设置合成模式
        String str="<html>请选择合成模式：</html>";
        JLabel mode = new JLabel(str);
        //创建一个单选按钮
        JRadioButton rdoMode0 = new JRadioButton("固定三镜头");
        JRadioButton rdoMode1 = new JRadioButton("自定义镜头数");
        //创建一个按钮组，把上面两个选项放入其中
        ButtonGroup rdoGroup = new ButtonGroup();
        rdoGroup.add(rdoMode0);
        rdoGroup.add(rdoMode1);
        //设置0为默认选项
        rdoMode0.setSelected(true);
        //创建中间容器（用来存放按钮组）
        JPanel panelButton = new JPanel();
        panelButton.setLayout(new GridLayout(1,2,5,5));
        //把按钮放入该容器
        panelButton.add(rdoMode0);
        panelButton.add(rdoMode1);
        //添加一个输入框，输入酒店名称
        JLabel name = new JLabel("请输入酒店或商单名称名称：");
        JTextArea hotelNameEnter = new JTextArea(2,20);
        hotelNameEnter.setBorder(border);
        //添加一个输入框，设置合成的数量
        JLabel number = new JLabel("请输入合成数量：");
        JTextArea targetNumberEnter = new JTextArea(2,20);
        targetNumberEnter.setBorder(border);
        //设置视频编号的初始值
        JLabel start = new JLabel("请输入初始编号：");
        JTextArea noNumber = new JTextArea(2,20);
        noNumber.setBorder(border);
        //设置只能输入数字
        targetNumberEnter.setDocument(new NumberDocument());
        noNumber.setDocument(new NumberDocument());

        //添加一个输入框，设置口播模式
        String str1="<html>请选择是否开启口播:</html>";
        JLabel mode1 = new JLabel(str1);
        //创建一个单选按钮
        JRadioButton rdoMode01 = new JRadioButton("NO");
        JRadioButton rdoMode11 = new JRadioButton("YES");
        //创建一个按钮组，把上面两个选项放入其中
        ButtonGroup rdoGroup1 = new ButtonGroup();
        rdoGroup1.add(rdoMode01);
        rdoGroup1.add(rdoMode11);
        //设置0为默认选项
        rdoMode11.setSelected(true);
        //创建中间容器（用来存放按钮组）
        JPanel panelButton1 = new JPanel();
        panelButton1.setLayout(new GridLayout(1,2,5,5));
        //把按钮放入该容器
        panelButton1.add(rdoMode01);
        panelButton1.add(rdoMode11);

        //添加一个输入框，设置合成模式
        String str2="<html>请选择是否添加背景音乐：</html>";
        JLabel mode2 = new JLabel(str2);
        //创建一个单选按钮
        JRadioButton rdoMode02 = new JRadioButton("NO");
        JRadioButton rdoMode12 = new JRadioButton("YES");
        //创建一个按钮组，把上面两个选项放入其中
        ButtonGroup rdoGroup2 = new ButtonGroup();
        rdoGroup2.add(rdoMode02);
        rdoGroup2.add(rdoMode12);
        //设置0为默认选项
        rdoMode12.setSelected(true);
        //创建中间容器（用来存放按钮组）
        JPanel panelButton2 = new JPanel();
        panelButton2.setLayout(new GridLayout(1,2,5,5));
        //把按钮放入该容器
        panelButton2.add(rdoMode02);
        panelButton2.add(rdoMode12);

        //添加一个输入框，设置合成模式
        String str3="<html>请选择是否开启水印：</html>";
        JLabel mode3 = new JLabel(str3);
        //创建一个单选按钮
        JRadioButton rdoMode03 = new JRadioButton("NO");
        JRadioButton rdoMode13 = new JRadioButton("YES");
        //创建一个按钮组，把上面两个选项放入其中
        ButtonGroup rdoGroup3 = new ButtonGroup();
        rdoGroup3.add(rdoMode03);
        rdoGroup3.add(rdoMode13);
        //设置0为默认选项
        rdoMode13.setSelected(true);
        //创建中间容器（用来存放按钮组）
        JPanel panelButton3 = new JPanel();
        panelButton3.setLayout(new GridLayout(1,2,5,5));
        //把按钮放入该容器
        panelButton3.add(rdoMode03);
        panelButton3.add(rdoMode13);


        selectDub = new JLabel("选择口播音频文件夹");
        //创建一个基本组件（按钮），并添加到 面板容器 中
        JButton targetDubsChooserBtn = new JButton("选择目标文件夹");
        //添加按钮点击事件
        targetDubsChooserBtn.addActionListener(new targetDubsChooserEvent());

        selectDubTxt = new JLabel("选择口播文本文件夹");
        //创建一个基本组件（按钮），并添加到 面板容器 中
        JButton targetDubTxtChooserBtn = new JButton("选择目标文件夹");
        //添加按钮点击事件
        targetDubTxtChooserBtn.addActionListener(new targetDubTxtChooserEvent());


        //创建一个基本组件（按钮），并添加到 面板容器 中
        JButton commitBtn = new JButton("确认");



        //添加按钮点击事件
        commitBtn.addActionListener(e -> {
            int modeSelected = 0;
            int modeSelected1=0;
            int modeSelected2=0;
            int modeSelected3=0;
            if (rdoMode0.isSelected() || !rdoMode1.isSelected()){
            }else if (!rdoMode0.isSelected() || rdoMode1.isSelected()) {
                modeSelected = 1;
            }
            if (rdoMode01.isSelected() || !rdoMode11.isSelected()){
            }else if (!rdoMode01.isSelected() || rdoMode11.isSelected()) {
                modeSelected1 = 1;
            }
            if (rdoMode02.isSelected() || !rdoMode12.isSelected()){
            }else if (!rdoMode02.isSelected() || rdoMode12.isSelected()) {
                modeSelected2 = 1;
            }
            if (rdoMode03.isSelected() || !rdoMode13.isSelected()){
            }else if (!rdoMode03.isSelected() || rdoMode13.isSelected()) {
                modeSelected3 = 1;
            }
            commitAll(
                    videoPath,
                    modeSelected,
                    hotelNameEnter.getText(),
                    Integer.parseInt(targetNumberEnter.getText()),
                    Integer.parseInt(noNumber.getText()),
                    savePath,
                    modeSelected1,
                    modeSelected2,
                    modeSelected3
            );
        });

        outputPath = new JLabel("选择视频的输出的文件夹");
        //创建一个基本组件（按钮），并添加到 面板容器 中
        JButton outputFilesChooserBtn = new JButton("选择目标文件夹");
        //添加按钮点击事件
        outputFilesChooserBtn.addActionListener(new outputFilesChooserEvent());


        selectMusic = new JLabel("选择背景音乐文件夹");
        //创建一个基本组件（按钮），并添加到 面板容器 中
        JButton selectMusicChooserBtn = new JButton("选择目标文件夹");
        //添加按钮点击事件
        selectMusicChooserBtn.addActionListener(new selectMusicChooserEvent());



        //设置字体和按钮的大小
        selectPath.setFont(new Font("宋体", Font.PLAIN, 20));
        mode.setFont(new Font("宋体", Font.PLAIN, 20));
        name.setFont(new Font("宋体", Font.PLAIN, 20));
        number.setFont(new Font("宋体", Font.PLAIN, 20));
        start.setFont(new Font("宋体", Font.PLAIN, 20));
        targetFilesChooserBtn.setFont(new Font("宋体", Font.PLAIN, 20));
        commitBtn.setFont(new Font("宋体", Font.PLAIN, 20));
        rdoMode0.setFont(new Font("宋体",Font.PLAIN,20));
        rdoMode1.setFont(new Font("宋体",Font.PLAIN,20));
        selectMusic.setFont(new Font("宋体",Font.PLAIN,20));
        outputPath.setFont(new Font("宋体",Font.PLAIN,20));
        selectDub.setFont(new Font("宋体",Font.PLAIN,20));
        selectDubTxt.setFont(new Font("宋体",Font.PLAIN,20));
        mode1.setFont(new Font("宋体", Font.PLAIN, 20));
        rdoMode01.setFont(new Font("宋体",Font.PLAIN,20));
        rdoMode11.setFont(new Font("宋体",Font.PLAIN,20));
        mode2.setFont(new Font("宋体", Font.PLAIN, 20));
        rdoMode02.setFont(new Font("宋体",Font.PLAIN,20));
        rdoMode12.setFont(new Font("宋体",Font.PLAIN,20));
        mode3.setFont(new Font("宋体", Font.PLAIN, 20));
        rdoMode03.setFont(new Font("宋体",Font.PLAIN,20));
        rdoMode13.setFont(new Font("宋体",Font.PLAIN,20));


        //把按钮加到面板容器中
        panelAddFile.add(selectPath);
        panelAddFile.add(targetFilesChooserBtn);
        panelAddFile.add(mode);
        panelAddFile.add(panelButton);
        panelAddFile.add(mode3);
        panelAddFile.add(panelButton3);
        panelAddFile.add(name);
        panelAddFile.add(hotelNameEnter);
        panelAddFile.add(number);
        panelAddFile.add(targetNumberEnter);
        panelAddFile.add(start);
        panelAddFile.add(noNumber);
        panelAddFile.add(outputPath);
        panelAddFile.add(outputFilesChooserBtn);
        panelAddFile.add(mode2);
        panelAddFile.add(panelButton2);
        panelAddFile.add(selectMusic);
        panelAddFile.add(selectMusicChooserBtn);
        panelAddFile.add(mode1);
        panelAddFile.add(panelButton1);
        panelAddFile.add(selectDub);
        panelAddFile.add(targetDubsChooserBtn);
        panelAddFile.add(selectDubTxt);
        panelAddFile.add(targetDubTxtChooserBtn);
        panelAddFile.add(commitBtn);
        //把 面板容器 作为窗口的内容面板 设置到 窗口
        jfTop.setContentPane(panelAddFile);


        //显示窗口，前面创建的信息都在内存中，通过 jf.setVisible(true) 把内存中的信息显示在屏幕上。
        jfTop.setVisible(true);

    }

    public Boolean commitAll(String videoPath,int modeNumber,String hotelName,int targetNumber,int noNumber,String outputPath,int mode,int mode2,int mode3){
        new CreateVideo(videoPath,modeNumber,hotelName,targetNumber,noNumber,outputPath,musicPath,dubPath,dubTxtPath,mode,mode2,mode3);
        return true;
    }


    class targetFilesChooserEvent implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            videoPath = targetFilesChooserFrame();

        }
    }

    class targetDubsChooserEvent implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            dubPath = targetDubsChooserEvent();

        }
    }

    class targetDubTxtChooserEvent implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            dubTxtPath = targetDubTxtChooserEvent();

        }
    }

    class outputFilesChooserEvent implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            savePath = outputFilesChooserFrame();

        }
    }

    class selectMusicChooserEvent implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            musicPath = selectMusicChooserFrame();

        }
    }

    public String targetFilesChooserFrame() {
        //弹出新窗口以选择目标目录并返回该目录的绝对路径

        //创建一个顶层容器-窗口
        JFrame jfTop = new JFrame("选择素材路径");
        //设置窗口大小
        jfTop.setSize(600,600);
        //把窗口定位到屏幕中心
        jfTop.setLocationRelativeTo(null);
        //设置默认打开的路径
//        String defaultDirectoryPath = System.getProperty("user.dir")+"\\src\\main\\resources\\video";
        String defaultDirectoryPath = "D:\\阿里云盘";
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
        selectPath.setText(filePath);
        selectPath.setFont(new Font("宋体", Font.PLAIN, 15));

        return filePath;
    }

    /**
     * 合成视频保存路径选择
     */
    public String outputFilesChooserFrame() {
        //弹出新窗口以选择目标目录并返回该目录的绝对路径

        //创建一个顶层容器-窗口
        JFrame jfTop = new JFrame("选择保存路径");
        //设置窗口大小
        jfTop.setSize(600,600);
        //把窗口定位到屏幕中心
        jfTop.setLocationRelativeTo(null);
        //设置默认打开的路径
        String defaultDirectoryPath ="D:\\阿里云盘";
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
        outputPath.setText(filePath);
        outputPath.setFont(new Font("宋体", Font.PLAIN, 15));

        return filePath;
    }

    /**
     * 合成视频保存路径选择
     */
    public String selectMusicChooserFrame() {
        //弹出新窗口以选择目标目录并返回该目录的绝对路径

        //创建一个顶层容器-窗口
        JFrame jfTop = new JFrame("选择音频路径");
        //设置窗口大小
        jfTop.setSize(600,600);
        //把窗口定位到屏幕中心
        jfTop.setLocationRelativeTo(null);
        //设置默认打开的路径
        String defaultDirectoryPath ="D:\\阿里云盘";
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
        selectMusic.setText(filePath);
        selectMusic.setFont(new Font("宋体", Font.PLAIN, 15));

        return filePath;
    }

    /**
     * 合成视频保存路径选择
     */
    public String targetDubsChooserEvent() {
        //弹出新窗口以选择目标目录并返回该目录的绝对路径

        //创建一个顶层容器-窗口
        JFrame jfTop = new JFrame("选择口播路径");
        //设置窗口大小
        jfTop.setSize(600,600);
        //把窗口定位到屏幕中心
        jfTop.setLocationRelativeTo(null);
        //设置默认打开的路径
        String defaultDirectoryPath ="D:\\阿里云盘";
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
        selectDub.setText(filePath);
        selectDub.setFont(new Font("宋体", Font.PLAIN, 15));
        return filePath;
    }

    /**
     * 合成视频保存路径选择
     */
    public String targetDubTxtChooserEvent() {
        //弹出新窗口以选择目标目录并返回该目录的绝对路径

        //创建一个顶层容器-窗口
        JFrame jfTop = new JFrame("选择音频路径");
        //设置窗口大小
        jfTop.setSize(600,600);
        //把窗口定位到屏幕中心
        jfTop.setLocationRelativeTo(null);
        //设置默认打开的路径
        String defaultDirectoryPath ="D:\\阿里云盘";
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
        selectDubTxt.setText(filePath);
        selectDubTxt.setFont(new Font("宋体", Font.PLAIN, 15));

        return filePath;
    }
}



