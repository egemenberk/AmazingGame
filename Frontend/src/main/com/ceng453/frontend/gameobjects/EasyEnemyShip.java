package main.com.ceng453.frontend.gameobjects;

import javafx.scene.image.Image;
import main.com.ceng453.frontend.main.ApplicationConstants;

public class EasyEnemyShip extends GameObject {

    public EasyEnemyShip(Image sprite, int width, int height) {
        super(sprite, width, height);
        setBounty(ApplicationConstants.AlienShip1Bounty);
    }

    @Override
    public GameObject update(double elapsedTime, long currentCycleNumber) {
        if( currentCycleNumber%100 >= 50 )
            setVelocityX(30.0);
        else
            setVelocityX(-30.0);

        setPositionX( getVelocityX() * elapsedTime + getPositionX());
        setPositionY( getVelocityY() * elapsedTime + getPositionY());

        if( ApplicationConstants.numberGenerator.nextDouble() > 1.0 -  ApplicationConstants.EasyAlienShootPercentage )
            return shoot();

        return null;
    }

    private GameObject shoot(){
        Bullet bullet = BulletFactory.create(Bullet.AlienBullet, getDamage());
        bullet.setPosition( getPositionX()+getWidth()/2.0, getPositionY() + getHeight() );
        return bullet;
    }
}
