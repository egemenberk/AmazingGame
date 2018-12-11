package com.ceng453.frontend;

public class BulletFactory {
    public static Bullet create( int bulletType, int damage ){
        Bullet bullet = null;
        switch (bulletType) {
            case Bullet.UserBullet:
                bullet = new Bullet(ApplicationConstants.UserBulletImage, ApplicationConstants.UserBulletWidth, ApplicationConstants.UserBulletHeight);
                bullet.setVelocityY( ApplicationConstants.UserBulletVelocity );
                bullet.initialize( 1, damage);
                break;
            case Bullet.AlienBullet:
                bullet = new Bullet(ApplicationConstants.AlienBulletImage, ApplicationConstants.AlienBulletWidth*damage, ApplicationConstants.AlienBulletHeight*damage);
                bullet.setVelocityY( ApplicationConstants.AlienBulletVelocity );
                bullet.initialize( 1, damage );
                break;

        }
        return bullet;
    }
}
