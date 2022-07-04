package pages;

import java.io.File;

public class main {
    public static void main(String[] args) {
        //D:\Новая папка (3)\hw-servlets-final\tamplates


        File file = new File("tamplates/");
        System.out.println(file.getAbsolutePath());
    }
}
