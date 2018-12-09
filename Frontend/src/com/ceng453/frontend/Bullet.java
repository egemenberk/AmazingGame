package com.ceng453.frontend;

import javafx.scene.image.Image;

public class Bullet extends GameObject {

    public static final int UserBullet = 0;
    public static final int AlienBullet = 1;


    public Bullet(Image sprite, int width, int height) {
        super(sprite, width, height);
    }

    @Override
    public GameObject update(double elapsedTime, long currentCycleNumber) {
        setPositionY( getPositionY() + getVelocityY()*elapsedTime );
        return null;
    }
}
