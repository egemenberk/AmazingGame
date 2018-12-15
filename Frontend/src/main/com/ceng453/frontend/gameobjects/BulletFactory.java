package main.com.ceng453.frontend.gameobjects;

import main.com.ceng453.frontend.main.ApplicationConstants;
import main.com.ceng453.frontend.main.Sound;

public class BulletFactory {
    public static Bullet create( int bulletType, int damage ){
        Bullet bullet = null;
        switch (bulletType) {
            case Bullet.UserBullet:
                bullet = new Bullet(ApplicationConstants.UserBulletImage, ApplicationConstants.UserBulletWidth, ApplicationConstants.UserBulletHeight);
                bullet.setVelocityY( ApplicationConstants.UserBulletVelocity );
                bullet.setHitpointsAndDamage( 1, damage);
                Sound.play(Sound.UserBulletSound);
                break;
            case Bullet.AlienBullet:
                bullet = new Bullet(ApplicationConstants.AlienBulletImage, ApplicationConstants.AlienBulletWidth*damage, ApplicationConstants.AlienBulletHeight*damage);
                bullet.setVelocityY( ApplicationConstants.AlienBulletVelocity );
                bullet.setHitpointsAndDamage( 1, damage );
                Sound.play(Sound.EasyEnemyBulletSound);
                break;
            case Bullet.HardAlienBullet:
                bullet = new HardBullet(ApplicationConstants.AlienBulletImage, ApplicationConstants.AlienBulletWidth*damage, ApplicationConstants.AlienBulletHeight*damage);
                bullet.setVelocityY( ApplicationConstants.AlienBulletVelocity );
                bullet.setHitpointsAndDamage( 4, damage );
                Sound.play(Sound.HardEnemyBulletSound);
                break;

        }
        return bullet;
    }
}
