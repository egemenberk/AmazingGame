package main.com.ceng453.game_objects;

import javafx.scene.image.Image;
import main.com.ceng453.frontend.main.ApplicationConstants;

// This is the boss of our game
public class HardEnemyShip extends GameObject {

    private static final int changeDirectionCycle = 250;
    private static final int linearXVelocity = 100;
    private static final int circleCompilationCycle = 70;
    private static final int cyclicMovementElipsX = 140;
    private static final int cyclicMovementElipsY = 380;

    public HardEnemyShip(Image sprite, int width, int height) {
        super(sprite, width, height);
        setBounty(ApplicationConstants.HardAlienShipBounty);
    }

    @Override
    public GameObject update(double elapsedTime, long currentCycleNumber) {
        // Move in cycles
        double rad = (currentCycleNumber% circleCompilationCycle) * 2 * Math.PI / circleCompilationCycle;

        setVelocityY( -Math.cos( rad )*cyclicMovementElipsX );
        setVelocityX( -Math.sin( rad )*cyclicMovementElipsY );

        // Additionally, move to left and right
        if( currentCycleNumber%(2*changeDirectionCycle) > changeDirectionCycle )
            setVelocityX( getVelocityX() + linearXVelocity );
        else
            setVelocityX( getVelocityX() - linearXVelocity );

        // Calculate x by x=v*t
        setPositionX( getVelocityX() * elapsedTime + getPositionX());
        setPositionY( getVelocityY() * elapsedTime + getPositionY());

        // Randomly shoot
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
