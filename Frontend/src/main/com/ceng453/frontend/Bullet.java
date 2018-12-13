package main.com.ceng453.frontend;

import javafx.scene.image.Image;

public class Bullet extends GameObject {

    public static final int UserBullet = 0;
    public static final int AlienBullet = 1;
    public static final int HardAlienBullet = 2;
    protected static final int HomingMissile = 3;


    public Bullet(Image sprite, int width, int height) {
        super(sprite, width, height);
        setVelocityX(0);
    }

    @Override
    public GameObject update(double elapsedTime, long currentCycleNumber) {
        setPositionY( getPositionY() + getVelocityY()*elapsedTime );
        setPositionX( getPositionX() + getVelocityX()*elapsedTime );
        if( getPositionY()+getHeight() < 0 || getPositionY() > ApplicationConstants.ScreenHeight )
            setCleared(true);
        return null;
    }
}
