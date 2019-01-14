package main.com.ceng453.game_objects;

import main.com.ceng453.ApplicationConstants;
import main.com.ceng453.frontend.main.Sound;

import java.util.LinkedList;
import java.util.List;


/*
 * Bullet factory class. That class will be used while creating bullets
 */
public class BulletFactory {

    private BulletFactory(){} // Factory pattern

    public static Bullet create(int bulletType, int damage ){
        Bullet bullet = null;
        switch (bulletType) {
            case Bullet.RegularUserBullet:
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
            case Bullet.ServerTickDrivenUserBullet:
                bullet = new ServerTickDrivenUserBullet(ApplicationConstants.UserBulletImage, ApplicationConstants.UserBulletWidth, ApplicationConstants.UserBulletHeight);
                bullet.setVelocityY( -ApplicationConstants.UserBulletVelocity );
                bullet.setHitpointsAndDamage( 1, damage);
                Sound.play(Sound.UserBulletSound);
                break;
            case Bullet.ServerTickDrivenRivalBullet:
                bullet = new ServerTickDrivenUserBullet(ApplicationConstants.UserBulletImage, ApplicationConstants.UserBulletWidth, ApplicationConstants.UserBulletHeight);
                bullet.setVelocityY( ApplicationConstants.UserBulletVelocity );
                bullet.setHitpointsAndDamage( 1, damage);
                Sound.play(Sound.UserBulletSound);
                break;

        }
        return bullet;
    }
}
