package com.hicorp.segment.utils;

import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @Author: wqs
 * @Date: Created in 15:13 2021/5/31
 * @Description:
 * @ChineseDescription:
 * @Modified_By:
 */
public class FileOption {
    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            ClassPathResource classPathResource = new ClassPathResource(fileName);
            InputStream inputStream =classPathResource.getInputStream();
            Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            int ch;
            StringBuilder sb = new StringBuilder();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
