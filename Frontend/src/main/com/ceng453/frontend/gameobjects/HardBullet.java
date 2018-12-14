package main.com.ceng453.frontend.gameobjects;

import javafx.scene.image.Image;
import main.com.ceng453.frontend.main.ApplicationConstants;

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
        if( currentCycleNumber%10 == 0 )
        {
            Bullet newBullet = BulletFactory.create( Bullet.AlienBullet, 1 );
            newBullet.setVelocityX((currentCycleNumber%20)>=10?-100:100);
            newBullet.setPosition( getPositionX()+getWidth()/2, getPositionY()+getHeight()-10 );
            return newBullet;
        }
        return null;
    }
}
