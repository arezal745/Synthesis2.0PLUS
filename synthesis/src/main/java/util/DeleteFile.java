package util;

import java.io.File;

/**
 * @Author:DaOcean
 * @DATE:2023/4/4 14:51
 * 清空素材文件夹中素材
 */
public class DeleteFile {

    static int flag = 1;//用来判断文件是否删除成功
//    public static void main(String[] args) {
//        String baseLocation = System.getProperty("user.dir");
//        File file = new File(baseLocation);
//        deleteFile(file);
//        if (flag == 1){
//            System.out.println("文件删除成功！");
//        }
//    }


    public static void deleteFile(File file) {
        //判断文件不为null或文件目录存在
        if (file == null || !file.exists()) {
            flag = 0;
            System.out.println("文件删除失败,请检查文件路径是否正确");
            return;
        }
        //取得这个目录下的所有子文件对象
        File[] files = file.listFiles();
        //遍历该目录下的文件对象
        for (File f: files) {
            //打印文件名
            String name = file.getName();
            System.out.println(name);
            //判断子目录是否存在子目录,如果是文件则删除
            if (f.isDirectory()) {
                deleteFile(f);
            } else {
                System.out.println(f.getName()+" was deleted!");
                f.delete();
            }
        }
        //删除空文件夹  for循环已经把上一层节点的目录清空。
        file.delete();

    }
}
