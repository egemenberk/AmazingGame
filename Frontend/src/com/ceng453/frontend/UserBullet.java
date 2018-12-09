package com.ceng453.frontend;

import javafx.scene.image.Image;

import java.io.FileNotFoundException;

public class UserBullet extends GameObject {
    public UserBullet(double posX, double posY)
    {
        super(ApplicationConstants.UserBulletImage, ApplicationConstants.UserBulletWidth, ApplicationConstants.UserBulletHeight);
        this.setVelocityY( -260 );
        this.setPosition(posX, posY);
    }

    public UserBullet(Image sprite, int width, int height) {
        super(sprite, width, height);
        this.setVelocityY( -60 );
    }

    @Override
    public GameObject update(double elapsedTime, long currentCycleNumber) {
        setPositionY( getPositionY() + getVelocityY()*elapsedTime );
        return null;
    }
}
