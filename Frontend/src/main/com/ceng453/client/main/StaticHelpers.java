package main.com.ceng453.client.main;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import main.com.ceng453.game.objects.GameObject;

public class StaticHelpers {

    // Helper function to read the images from a file
    public static Image getImageFromAssets(String filename) {
        return new Image(Main.class.getResourceAsStream("/assets/"+filename));
    }

    // Helper function that checks if two objects collide into each other
    public static boolean intersects(GameObject o1, GameObject o2) {
        Rectangle o1Rect = new Rectangle( o1.getPositionX(),o1.getPositionY(), o1.getWidth(), o1.getHeight() );
        return o1Rect.intersects(o2.getPositionX(), o2.getPositionY(), o2.getWidth(), o2.getHeight());
    }
}
