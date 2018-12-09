package com.ceng453.frontend;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class StaticHelpers {

    public static FileInputStream getResourceFromAssets(String filename) {
        try {
            return new FileInputStream(System.getProperty("user.dir") + "/assets/" + filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
