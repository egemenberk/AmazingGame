package com.ceng453.frontend;

import javafx.scene.image.Image;

public class MediumEnemyShip extends GameObject {

    public MediumEnemyShip(Image sprite, int width, int height) {
        super(sprite, width, height);
        setBounty(ApplicationConstants.AlienShip2Bounty);
    }

    @Override
    public GameObject update(double elapsedTime, long currentCycleNumber) {
        double rad = (currentCycleNumber%70) * 2 * Math.PI / 70.0;

        setVelocityY( Math.cos( rad )*140 );
        setVelocityX( Math.sin( rad )*380 );

        /*if( currentCycleNumber%140 > 70 )
            setVelocityX( getVelocityX() + 100 );
        else
            setVelocityX( getVelocityX() - 100 );*/

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