package main.com.ceng453.game_objects;

import javafx.scene.image.Image;
import main.com.ceng453.ApplicationConstants;

import java.util.ArrayList;
import java.util.List;

public class BossAlien extends GameObject {

    private static final int circleCompilationCycle = 70;
    private static final int cyclicMovementElipsX = 140;
    private static final int cyclicMovementElipsY = 380;
    private static final int shootingIntervalCycle = 200;
    private static final int OffsetX = ApplicationConstants.SCREEN_WIDTH /2 - ApplicationConstants.BOSS_ALIEN_SHIP_WIDTH /2;
    private static final int OffsetY = ApplicationConstants.SCREEN_HEIGHT /2 - ApplicationConstants.BOSS_ALIEN_SHIP_HEIGHT /2;

    protected BossAlien(Image sprite, int width, int height) {
        super(sprite, width, height);
        setBounty(ApplicationConstants.BOSS_ALIEN_SHIP_BOUNTY);
    }

    @Override
    public List<GameObject> update(double __, long currentCycleNumber) {
        // Move in cycles
        double rad = (currentCycleNumber% circleCompilationCycle) * 2 * Math.PI / circleCompilationCycle;

        setPositionX( OffsetX );
        setPositionY( OffsetY);

        setVelocityX( -Math.cos( rad )*cyclicMovementElipsX  );
        setVelocityY( -Math.sin( rad )*cyclicMovementElipsY  );

        // Additionally, move to left and right
        //setPositionX( getPositionX() + linearXVelocity*((currentCycleNumber%changeDirectionCycle)-changeDirectionCycle/2.0) );

        // Randomly shoot
        if( currentCycleNumber % shootingIntervalCycle == shootingIntervalCycle-1 )
            return shoot();

        return null;
    }

    private List<GameObject> shoot(){
        List<GameObject> bullets = new ArrayList<>();

        // One to the top
        bullets.add(BulletFactory.create(Bullet.AlienBullet, getDamage()));
        bullets.get(bullets.size()-1).setPosition(
                getPositionX()+getWidth()/2.0 - bullets.get(bullets.size()-1).getWidth()/2.0,
                getPositionY() + getHeight()/2.0 - ApplicationConstants.ALIEN_BULLET_VELOCITY /2.0);
        bullets.get(bullets.size()-1).setVelocityY(-ApplicationConstants.ALIEN_BULLET_VELOCITY);

        // One for the bottom
        bullets.add(BulletFactory.create(Bullet.AlienBullet, getDamage()));
        bullets.get(bullets.size()-1).setPosition(
                getPositionX()+getWidth()/2.0 - bullets.get(bullets.size()-1).getWidth()/2.0,
                getPositionY() + getHeight()/2.0 - bullets.get(bullets.size()-1).getHeight() + ApplicationConstants.ALIEN_BULLET_VELOCITY /2.0);
        bullets.get(bullets.size()-1).setVelocityY(ApplicationConstants.ALIEN_BULLET_VELOCITY);
        return bullets;
    }
}
