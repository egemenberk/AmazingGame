package main.com.ceng453;

import javafx.scene.image.Image;
import main.com.ceng453.frontend.main.StaticHelpers;

import java.util.Objects;
import java.util.Random;

public final class ApplicationConstants {
    public static final String RestServerBaseAddress = "http://localhost:8080";
    public static final String GameServerIP ="192.168.1.25";
    public static final int GameServerPort = 9999;

    public static final int UserBulletWidth = 20;
    public static final int UserBulletHeight = 35;
    public static final int AlienBulletWidth = 20;
    public static final int AlienBulletHeight = 30;

    public static final double EasyAlienShootPercentage = 0.002;
    public static final double MediumAlienShootPercentage = 0.005;
    public static final double HardAlienShootPercentage = 0.008;

    public static final int ScreenWidth = 600;
    public static final int ScreenHeight = 768;
    public static final double TextDrawRectHeight = 22;

    public static final int EasyAlienShipBounty = 10;
    public static final int MediumAlienShipBounty = 45;
    public static final int HardAlienShipBounty = 180;

    public static final int EasyAlienShipHP = 2;
    public static final int EasyAlienShipDMG = 1;
    public static final int MediumAlienShipHP = 6;
    public static final int MediumAlienShipDMG = 2;
    public static final int HardAlienShipHP = 16;
    public static final int HardAlienShipDMG = 3;

    public static final int UserShipHealth = 6;
    public static final int UserShipDamage = 2;

    public static final double UserBulletVelocity = -270;
    public static final double AlienBulletVelocity = 100;
    public static final double HealtBarHeight = 10;
    public static final double HealtBarWidthCoefficent = 0.7;

    private static final String UserBulletImagename = "user_bullet.png";
    private static final String AlienBulletImagename = "alien_bullet.png";
    private static final String BackgroundImagename = "background.jpg";
    private static final String UserShipImagename = "user_ship.png";
    private static final String EasyAlienShipImagename = "alien_ship.png";
    private static final String MediumAlienShipImagename = "alien2.png";
    private static final String HardAlienShipImagename = "alien3.png";
    private static final String ExplosionImagename = "explosion.png";
    private static final String GameOverImagename = "game_over.png";

    public static final String ViyuwSound = "ciyuw.wav";
    public static final String PitSound = "pit.wav";
    public static final String Dattiridatdat = "datdiridat.wav";
    private static final String JustWowImagename = "just_wow.jpg";
    public static final String Kapiska = "kapuska.wav";

    public static final String GameMusicFilename = "NecroDancer_OST_Portabellohead.mp3";

    public static final Image UserBulletImage = new Image(Objects.requireNonNull(StaticHelpers.getResourceFromAssets(UserBulletImagename)));
    public static final Image BackGroundImage = new Image(Objects.requireNonNull(StaticHelpers.getResourceFromAssets(BackgroundImagename)));
    public static final Image UserShipImage = new Image(Objects.requireNonNull(StaticHelpers.getResourceFromAssets(UserShipImagename)));
    public static final Image EasyAlienShipImage = new Image(Objects.requireNonNull(StaticHelpers.getResourceFromAssets(EasyAlienShipImagename)));
    public static final Image ExplosionImage = new Image(Objects.requireNonNull(StaticHelpers.getResourceFromAssets(ExplosionImagename)));
    public static final Image AlienBulletImage = new Image(Objects.requireNonNull(StaticHelpers.getResourceFromAssets(AlienBulletImagename)));
    public static final Image MediumAlienShipImage = new Image(Objects.requireNonNull(StaticHelpers.getResourceFromAssets(MediumAlienShipImagename)));
    public static final Image HardAlienShipImage = new Image(Objects.requireNonNull(StaticHelpers.getResourceFromAssets(HardAlienShipImagename)));
    public static final Image GameOverImage = new Image(Objects.requireNonNull(StaticHelpers.getResourceFromAssets(GameOverImagename)));
    public static final Image JustWowImage = new Image(Objects.requireNonNull(StaticHelpers.getResourceFromAssets(JustWowImagename)));

    public static final Random numberGenerator = new Random();

}
