package main.com.ceng453.frontend.gameobjects;

import javafx.scene.image.Image;
import main.com.ceng453.frontend.main.ApplicationConstants;

public class MediumEnemyShip extends GameObject {

    private static final int circleCompilationCycle = 70;
    private static final int cyclicMovementElipsX = 140;
    private static final int cyclicMovementElipsY = 350;

    public MediumEnemyShip(Image sprite, int width, int height) {
        super(sprite, width, height);
        setBounty(ApplicationConstants.MediumAlienShipBounty);
    }

    @Override
    public GameObject update(double elapsedTime, long currentCycleNumber) {
        // Move in elipses
        double rad = (currentCycleNumber%circleCompilationCycle) * 2 * Math.PI / circleCompilationCycle;

        setVelocityY( Math.cos( rad )*cyclicMovementElipsX );
        setVelocityX( Math.sin( rad )*cyclicMovementElipsY );

        // Calculate x with x=v*t
        setPositionX( getVelocityX() * elapsedTime + getPositionX());
        setPositionY( getVelocityY() * elapsedTime + getPositionY());

        // Randomly shoot
        if( ApplicationConstants.numberGenerator.nextDouble() > 1.0 -  ApplicationConstants.MediumAlienShootPercentage )
            return shoot();

        return null;
    }

    private GameObject shoot(){
        Bullet bullet = BulletFactory.create(Bullet.AlienBullet, getDamage());
        bullet.setPosition( getPositionX()+getWidth()/2.0, getPositionY() + getHeight() );
        return bullet;
    }
}