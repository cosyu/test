package com.example.utils;


import java.io.File;
import java.nio.charset.StandardCharsets;


public class FileUtils {

    public static void  appendStringToFile(String filePath ,String content) throws Exception{

        File file = new File(filePath);
        org.apache.commons.io.FileUtils.writeStringToFile(file,content, StandardCharsets.UTF_8,true);

    }

    public static void writeStringToNewFile(String filePath ,String content) throws Exception{

        File file = new File(filePath);
        if(file.exists()){
            file.delete();
        }
        appendStringToFile(filePath,content);

    }

}
