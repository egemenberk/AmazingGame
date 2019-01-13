package main.com.ceng453.game_objects;

import javafx.scene.image.Image;
import main.com.ceng453.ApplicationConstants;

public class BossAlien extends GameObject {

    private static final int changeDirectionCycle = 250;
    private static final int linearXVelocity = 100;
    private static final int circleCompilationCycle = 70;
    private static final int cyclicMovementElipsX = 140;
    private static final int cyclicMovementElipsY = 380;
    private static final int shootingIntervalCycle = 200;
    private static final int OffsetX = ApplicationConstants.ScreenWidth/2 - ApplicationConstants.BossAlienShipWidth/2;
    private static final int OffsetY = ApplicationConstants.ScreenHeight/2 - ApplicationConstants.BossAlienShipHeight/2;

    protected BossAlien(Image sprite, int width, int height) {
        super(sprite, width, height);
        setBounty(ApplicationConstants.BossAlienShipBounty);
    }

    @Override
    public GameObject update(double __, long currentCycleNumber) {
        // Move in cycles
        double rad = (currentCycleNumber% circleCompilationCycle) * 2 * Math.PI / circleCompilationCycle;

        setPositionX( OffsetX );
        setPositionY( OffsetY);

        setVelocityX( -Math.cos( rad )*cyclicMovementElipsX  );
        setVelocityY( -Math.sin( rad )*cyclicMovementElipsY  );

        // Additionally, move to left and right
        //setPositionX( getPositionX() + linearXVelocity*((currentCycleNumber%changeDirectionCycle)-changeDirectionCycle/2.0) );

        // Randomly shoot
        if( currentCycleNumber % shootingIntervalCycle == 0 )
            return shoot();

        return null;
    }

    private GameObject shoot(){
        Bullet bullet = BulletFactory.create(Bullet.AlienBullet, getDamage());
        bullet.setPosition( getPositionX()+getWidth()/2.0, getPositionY() + getHeight()-70 );
        return bullet;
    }
}
