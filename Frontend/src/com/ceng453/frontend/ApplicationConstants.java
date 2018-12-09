package com.ceng453.frontend;

import javafx.scene.image.Image;

import java.util.Random;

public final class ApplicationConstants {
    public static final int UserBulletWidth = 20;
    public static final int UserBulletHeight = 35;
    public static final int AlienBulletWidth = 20;
    public static final int AlienBulletHeight = 30;
    public static final double UserBulletVelocity = -270;
    public static final double EasyAlienShootPercentage = 0.001;
    public static final double AlienBulletVelocity = 200;
    static final int ScreenWidth = 600;
    static final int ScreenHeight = 1000;

    static final String UserBulletImagename = "user_bullet.png";
    static final String AlienBulletImagename = "alien_bullet.png";
    static final String BackgroundImagename = "background.jpg";
    static final String UserShipImagename = "user_ship.png";
    static final String AlienShipImagename = "alien_ship.png";
    static final String ExplosionImagename = "explosion.png";

    public static final Image UserBulletImage = new Image( StaticHelpers.getResourceFromAssets(UserBulletImagename) );
    public static final Image BackGroundImage = new Image( StaticHelpers.getResourceFromAssets(BackgroundImagename) );
    public static final Image UserShipImage = new Image( StaticHelpers.getResourceFromAssets(UserShipImagename) );
    public static final Image AlienShipImage = new Image( StaticHelpers.getResourceFromAssets(AlienShipImagename) );
    public static final Image ExplosionImage = new Image( StaticHelpers.getResourceFromAssets(ExplosionImagename) );
    public static final Image AlienBulletImage = new Image( StaticHelpers.getResourceFromAssets(AlienBulletImagename) );

    public static final Random numberGenerator = new Random();

}