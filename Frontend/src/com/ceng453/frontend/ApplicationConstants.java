package com.ceng453.frontend;

import javafx.scene.image.Image;

public final class ApplicationConstants {
    public static final int UserBulletWidth = 15;
    public static final int UserBulletHeight = 30;
    static final int ScreenWidth = 600;
    static final int ScreenHeight = 1000;

    static final String UserBulletImagename = "user_bullet.png";
    static final String AlienBulletImagename = "alien_bullet.png";
    static final String BackgroundImagename = "background.jpg";
    static final String UserShipImagename = "user_ship.png";
    static final String AlienShipImagename = "alien_ship.png";

    public static final Image UserBulletImage = new Image( StaticHelpers.getResourceFromAssets(UserBulletImagename) );
    public static final Image BackGroundImage = new Image( StaticHelpers.getResourceFromAssets(BackgroundImagename) );
    public static final Image UserShipImage = new Image( StaticHelpers.getResourceFromAssets(UserShipImagename) );
    public static final Image AlienShipImage = new Image( StaticHelpers.getResourceFromAssets(AlienShipImagename) );
}
