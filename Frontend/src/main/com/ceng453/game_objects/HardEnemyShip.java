package main.com.ceng453.game_objects;

import javafx.scene.image.Image;
import main.com.ceng453.ApplicationConstants;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

// This is the boss of our game
public class HardEnemyShip extends GameObject implements Serializable {

    private static final int changeDirectionCycle = 250;
    private static final int linearXVelocity = 100;
    private static final int circleCompilationCycle = 70;
    private static final int cyclicMovementElipsX = 140;
    private static final int cyclicMovementElipsY = 380;

    public HardEnemyShip(Image sprite, int width, int height) {
        super(sprite, width, height);
        setBounty(ApplicationConstants.HARD_ALIEN_SHIP_BOUNTY);
    }

    @Override
    public List<GameObject> update(double elapsedTime, long currentCycleNumber) {
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
        if( ApplicationConstants.NUMBER_GENERATOR.nextDouble() > (1.0 -  ApplicationConstants.HARD_ALIEN_SHOOT_PERCENTAGE) )
            return shoot();

        return null;
    }

    private List<GameObject> shoot(){
        List<GameObject> bullets = new LinkedList<>();
        Bullet bullet = BulletFactory.create(Bullet.HardAlienBullet, getDamage());
        bullet.setPosition( getPositionX()+getWidth()/2.0, getPositionY() + getHeight()-70 );
        bullets.add(bullet);
        return bullets;
    }
}
