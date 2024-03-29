package main.com.ceng453.game.objects;

import main.com.ceng453.ApplicationConstants;
import main.com.ceng453.client.main.Sound;


/*
 * Bullet factory class. That class will be used while creating bullets
 */
class BulletFactory {

    private BulletFactory(){} // Factory pattern

    public static Bullet create(int bulletType, int damage ){
        Bullet bullet = null;
        switch (bulletType) {
            case Bullet.RegularUserBullet:
                bullet = new Bullet(ApplicationConstants.USER_BULLET_IMAGE, ApplicationConstants.USER_BULLET_WIDTH, ApplicationConstants.USER_BULLET_HEIGHT);
                bullet.setVelocityY( ApplicationConstants.USER_BULLET_VELOCITY);
                bullet.setHitpointsAndDamage( 1, damage);
                Sound.play(Sound.UserBulletSound);
                break;
            case Bullet.AlienBullet:
                bullet = new Bullet(ApplicationConstants.ALIEN_BULLET_IMAGE, ApplicationConstants.ALIEN_BULLET_WIDTH *damage, ApplicationConstants.ALIEN_BULLET_HEIGHT *damage);
                bullet.setVelocityY( ApplicationConstants.ALIEN_BULLET_VELOCITY);
                bullet.setHitpointsAndDamage( 1, damage );
                Sound.play(Sound.EasyEnemyBulletSound);
                break;
            case Bullet.HardAlienBullet:
                bullet = new HardBullet(ApplicationConstants.ALIEN_BULLET_IMAGE, ApplicationConstants.ALIEN_BULLET_WIDTH *damage, ApplicationConstants.ALIEN_BULLET_HEIGHT *damage);
                bullet.setVelocityY( ApplicationConstants.ALIEN_BULLET_VELOCITY);
                bullet.setHitpointsAndDamage( 4, damage );
                Sound.play(Sound.HardEnemyBulletSound);
                break;
            case Bullet.ServerTickDrivenUserBullet:
                bullet = new ServerTickDrivenUserBullet(ApplicationConstants.USER_BULLET_IMAGE, ApplicationConstants.USER_BULLET_WIDTH, ApplicationConstants.USER_BULLET_HEIGHT);
                bullet.setVelocityY( ApplicationConstants.USER_BULLET_VELOCITY);
                bullet.setHitpointsAndDamage( 1, damage);
                Sound.play(Sound.UserBulletSound);
                break;
            case Bullet.ServerTickDrivenRivalBullet:
                bullet = new ServerTickDrivenUserBullet(ApplicationConstants.USER_BULLET_IMAGE, ApplicationConstants.USER_BULLET_WIDTH, ApplicationConstants.USER_BULLET_HEIGHT);
                bullet.setVelocityY( -ApplicationConstants.USER_BULLET_VELOCITY);
                bullet.setHitpointsAndDamage( 1, damage);
                Sound.play(Sound.UserBulletSound);
                break;

        }
        return bullet;
    }
}
