package main.com.ceng453.game_objects;

import javafx.scene.image.Image;
import main.com.ceng453.ApplicationConstants;

import java.util.LinkedList;
import java.util.List;

public class HardBullet extends Bullet {

    private static final int bulletGenerationInterval = 10;
    private static final int generatedBulletXVelocity = 100;

    public HardBullet(Image alienBulletImage, int width, int height) {
        super(alienBulletImage, width, height);
    }

    @Override
    public List<GameObject> update(double elapsedTime, long currentCycleNumber) {

        // This bullet will be swinging while approaching to the user
        // This calculation allows this animation
        setVelocityX( 70*Math.sin( Math.toRadians((currentCycleNumber%30)*12.0) ) );

        // Set x = v*t
        setPositionX( getPositionX() + getVelocityX()*elapsedTime );
        setPositionY( getPositionY() + getVelocityY()*elapsedTime );

        //If bullet leaves screen, destroy it
        if( getPositionY()+getHeight() < 0 || getPositionY() > ApplicationConstants.ScreenHeight )
            setCleared();
        if( currentCycleNumber%bulletGenerationInterval == 0 )
        {
            Bullet newBullet = BulletFactory.create( Bullet.AlienBullet, ApplicationConstants.EasyAlienShipDMG );
            newBullet.setVelocityX(( // Make the new bullet to move in X too
                    currentCycleNumber%(2*bulletGenerationInterval))>=bulletGenerationInterval?
                    -generatedBulletXVelocity:
                    generatedBulletXVelocity);
            newBullet.setPosition( getPositionX()+getWidth()/2.0, getPositionY()+getHeight()-10 );
            List<GameObject> new_bullets = new LinkedList<>();
            new_bullets.add(newBullet);
            return new_bullets;
        }
        return null;
    }
}
