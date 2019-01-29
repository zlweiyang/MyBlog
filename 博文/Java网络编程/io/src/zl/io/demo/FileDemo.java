package zl.io.demo;

import java.io.File;

/**
 * @author zlCalma
 * @date 2019/1/15 15:13.
 */
public class FileDemo {

    public static void listAllFIle(File dir){
        if(dir == null || !dir.exists()){
            return;
        }
        if(dir.isFile()){
            System.out.println(dir.getName());
            return;
        }
        for(File file:dir.listFiles()){
            listAllFIle(file);
        }
    }

    public static void main(String[] args) {
        File file = new File("D:\\");
        listAllFIle(file);
    }
}
