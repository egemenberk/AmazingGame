package main.com.ceng453.frontend.gameobjects;

import javafx.scene.image.Image;
import main.com.ceng453.frontend.main.ApplicationConstants;

public class HardEnemyShip extends GameObject {

    public HardEnemyShip(Image sprite, int width, int height) {
        super(sprite, width, height);
        setBounty(ApplicationConstants.AlienShip3Bounty);
    }

    @Override
    public GameObject update(double elapsedTime, long currentCycleNumber) {
        double rad = (currentCycleNumber%70) * 2 * Math.PI / 70.0;

        setVelocityY( -Math.cos( rad )*140 );
        setVelocityX( -Math.sin( rad )*380 );

        if( currentCycleNumber%500 > 250 )
            setVelocityX( getVelocityX() + 100 );
        else
            setVelocityX( getVelocityX() - 100 );

        setPositionX( getVelocityX() * elapsedTime + getPositionX());
        setPositionY( getVelocityY() * elapsedTime + getPositionY());

        if( ApplicationConstants.numberGenerator.nextDouble() > (1.0 -  ApplicationConstants.HardAlienShootPercentage) )
            return shoot();

        return null;
    }

    private GameObject shoot(){
        Bullet bullet = BulletFactory.create(Bullet.HardAlienBullet, getDamage());
        bullet.setPosition( getPositionX()+getWidth()/2.0, getPositionY() + getHeight()-70 );
        return bullet;
    }
}
