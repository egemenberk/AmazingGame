package main.com.ceng453.frontend.gameobjects;

import javafx.scene.image.Image;
import main.com.ceng453.frontend.main.ApplicationConstants;

public class EasyEnemyShip extends GameObject {

    private static final int directionChangeCycle = 50;
    private static final double SpeedX = 30.0;

    public EasyEnemyShip(Image sprite, int width, int height) {
        super(sprite, width, height);
        setBounty(ApplicationConstants.EasyAlienShipBounty);
    }


    public GameObject update(double elapsedTime, long currentCycleNumber) {
        // In each directionChangeCycle, ship will change its direction
        if( currentCycleNumber%(directionChangeCycle*2) >= directionChangeCycle )
            setVelocityX(SpeedX);
        else
            setVelocityX(-SpeedX);

        // Set position using x = v*t
        setPositionX( getVelocityX() * elapsedTime + getPositionX());
        setPositionY( getVelocityY() * elapsedTime + getPositionY());

        // Randomly shoot
        if( ApplicationConstants.numberGenerator.nextDouble() > 1.0 -  ApplicationConstants.EasyAlienShootPercentage )
            return shoot();

        return null;
    }

    // Shoot method, create a bullet from ship's position, than return it to the game level
    private GameObject shoot(){
        Bullet bullet = BulletFactory.create(Bullet.AlienBullet, getDamage());
        bullet.setPosition( getPositionX()+getWidth()/2.0, getPositionY() + getHeight() );
        return bullet;
    }
}
