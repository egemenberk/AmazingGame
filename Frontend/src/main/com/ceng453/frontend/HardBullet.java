package main.com.ceng453.frontend;

import javafx.scene.image.Image;

public class HardBullet extends Bullet {
    public HardBullet(Image alienBulletImage, int width, int height) {
        super(alienBulletImage, width, height);
    }

    @Override
    public GameObject update(double elapsedTime, long currentCycleNumber) {

        setVelocityX( 70*Math.sin( Math.toRadians((currentCycleNumber%30)*12.0) ) );

        setPositionX( getPositionX() + getVelocityX()*elapsedTime );
        setPositionY( getPositionY() + getVelocityY()*elapsedTime );
        if( getPositionY()+getHeight() < 0 || getPositionY() > ApplicationConstants.ScreenHeight )
            setCleared(true);
        return null;
    }
}
