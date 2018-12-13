package main.com.ceng453.frontend;

import javafx.scene.image.Image;

public class HomingBullet extends Bullet {
    public HomingBullet(Image sprite, int width, int height) {
        super(sprite, width, height);
    }

    @Override
    public GameObject update(double elapsedTime, long currentCycleNumber) {
        return null;
    }
}
