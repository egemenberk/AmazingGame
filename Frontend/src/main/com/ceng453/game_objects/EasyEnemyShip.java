package main.com.ceng453.game_objects;

import javafx.scene.image.Image;
import main.com.ceng453.ApplicationConstants;

import java.util.LinkedList;
import java.util.List;

public class EasyEnemyShip extends GameObject {

    private static final int directionChangeCycle = 50;
    private static final double SpeedX = 30.0;

    public EasyEnemyShip(Image sprite, int width, int height) {
        super(sprite, width, height);
        setBounty(ApplicationConstants.EASY_ALIEN_SHIP_BOUNTY);
    }


    public List<GameObject> update(double elapsedTime, long currentCycleNumber) {
        // In each directionChangeCycle, ship will change its direction
        if( currentCycleNumber%(directionChangeCycle*2) >= directionChangeCycle )
            setVelocityX(SpeedX);
        else
            setVelocityX(-SpeedX);

        // Set position using x = v*t
        setPositionX( getVelocityX() * elapsedTime + getPositionX());
        setPositionY( getVelocityY() * elapsedTime + getPositionY());

        // Randomly shoot
        if( ApplicationConstants.NUMBER_GENERATOR.nextDouble() > 1.0 -  ApplicationConstants.EASY_ALIEN_SHOOT_PERCENTAGE)
            return shoot();

        return null;
    }

    // Shoot method, create a bullet from ship's position, than return it to the game level
    private List<GameObject> shoot(){
        List<GameObject> bullets = new LinkedList<>();
        Bullet bullet = BulletFactory.create(Bullet.AlienBullet, getDamage());
        bullet.setPosition( getPositionX()+getWidth()/2.0, getPositionY() + getHeight() );
        bullets.add(bullet);
        return bullets;
    }
}
