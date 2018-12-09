package com.ceng453.frontend;

import javafx.scene.shape.Rectangle;

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

    public static boolean intersects(GameObject o1, GameObject o2) {
        Rectangle o1Rect = new Rectangle( o1.getPositionX(),o1.getPositionY(), o1.getWidth(), o1.getHeight() );
        if( o1Rect.intersects( o2.getPositionX(), o2.getPositionY(), o2.getWidth(), o2.getHeight() ) )
            return true;
        return false;
    }
}
