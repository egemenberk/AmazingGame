package com.ceng453.frontend;

import javafx.scene.image.Image;

public class UserShip extends GameObject {

    public UserShip(Image sprite, int width, int height) {
        super(sprite, width, height);
    }

    @Override
    public GameObject update(double elapsedTime, long currentCycleNumber) {
        setPositionX( getVelocityX()*elapsedTime + getPositionX() );
        setPositionY( getVelocityY()*elapsedTime + getPositionY() );
        return null;
    }

    public GameObject shoot(){
        Bullet bullet = BulletFactory.create(Bullet.UserBullet, getDamage());
        bullet.setPosition(getPositionX() + getWidth() / 2.0, getPositionY());
        return bullet;
    }
}