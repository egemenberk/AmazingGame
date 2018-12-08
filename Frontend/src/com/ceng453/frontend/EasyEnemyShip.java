package com.ceng453.frontend;

import javafx.scene.image.Image;

public class EasyEnemyShip extends GameObject {

    public EasyEnemyShip(Image sprite, int width, int height) {
        super(sprite, width, height);
    }

    @Override
    public GameObject update(double elapsedTime, long currentCycleNumber) {
        if( currentCycleNumber%100 >= 50 )
            setVelocityX(30.0);
        else
            setVelocityX(-30.0);

        setPositionX( getVelocityX() * elapsedTime + getPositionX());
        setPositionY( getVelocityY() * elapsedTime + getPositionY());

        return null;
    }
}
