package com.blueeyescloud.ext.devicemaster.utils;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

public class YamlUtils {
    public static void get(String fileName, String key){
        Yaml yaml = new Yaml();
        File file = new File(fileName);
        try {
            Map map = yaml.load(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
