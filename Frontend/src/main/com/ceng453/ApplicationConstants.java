package main.com.ceng453;

import javafx.scene.image.Image;
import main.com.ceng453.client.main.StaticHelpers;

import java.util.Objects;
import java.util.Random;

public final class ApplicationConstants {
    public static final String REST_SERVER_ADDRESS = "http://localhost:8080";
    public static final String GAME_SERVER_IP ="127.0.0.1";
    public static final int GAME_SERVER_PORT = 9999;

    public static final int USER_BULLET_WIDTH = 20;
    public static final int USER_BULLET_HEIGHT = 35;
    public static final int ALIEN_BULLET_WIDTH = 20;
    public static final int ALIEN_BULLET_HEIGHT = 30;

    public static final double EASY_ALIEN_SHOOT_PERCENTAGE = 0.002;
    public static final double MEDIUM_ALIEN_SHOOT_PERCENTAGE = 0.005;
    public static final double HARD_ALIEN_SHOOT_PERCENTAGE = 0.008;

    public static final int SCREEN_WIDTH = 600;
    public static final int SCREEN_HEIGHT = 768;
    public static final double TEXT_DRAW_RECT_HEIGHT = 22;

    public static final int BOSS_ALIEN_SHIP_WIDTH = 200;
    public static final int BOSS_ALIEN_SHIP_HEIGHT = 200;

    public static final int EASY_ALIEN_SHIP_BOUNTY = 10;
    public static final int MEDIUM_ALIEN_SHIP_BOUNTY = 45;
    public static final int HARD_ALIEN_SHIP_BOUNTY = 180;
    public static final int BOSS_ALIEN_SHIP_BOUNTY = 480;

    public static final int EASY_ALIEN_SHIP_HP = 2;
    public static final int EASY_ALIEN_SHIP_DMG = 1;
    public static final int MEDIUM_ALIEN_SHIP_HP = 6;
    public static final int MEDIUM_ALIEN_SHIP_DMG = 2;
    public static final int HARD_ALIEN_SHIP_HP = 16;
    public static final int HARD_ALIEN_SHIP_DMG = 3;
    public static final int BOSS_ALIEN_SHIP_HP = 78;

    public static final int USER_SHIP_HEALTH = 6;
    public static final int USER_SHIP_DAMAGE = 2;

    public static final double USER_BULLET_VELOCITY = -270;
    public static final double ALIEN_BULLET_VELOCITY = 100;
    public static final double HEALTH_BAR_HEIGHT = 10;
    public static final double HEALTH_BAR_WIDTH_COEFFICIENT = 0.7;
    public static final long TICK_MS = 15;

    public static final String WAIT_JPG = "wait.jpg";
    public static final String ALIEN_BOSS_PNG = "alien_boss.png";
    private static final String USER_BULLET_PNG = "user_bullet.png";
    private static final String ALIEN_BULLET_PNG = "alien_bullet.png";
    private static final String BACKGROUND_JPG = "background.jpg";
    private static final String USER_SHIP_PNG = "user_ship.png";
    private static final String ALIEN_SHIP_PNG = "alien_ship.png";
    private static final String ALIEN_2_PNG = "alien2.png";
    private static final String ALIEN_3_PNG = "alien3.png";
    private static final String EXPLOSION_PNG = "explosion.png";
    private static final String GAME_OVER_PNG = "game_over.png";

    public static final String CIYUW_WAV = "ciyuw.wav";
    public static final String PIT_WAV = "pit.wav";
    public static final String DATDIRIDAT_WAV = "datdiridat.wav";
    private static final String JUST_WOW_JPG = "just_wow.jpg";
    public static final String KAPUSKA_WAV = "kapuska.wav";

    public static final String GAME_MUSIC_FILENAME = "NecroDancer_OST_Portabellohead.mp3";

    public static final Image USER_BULLET_IMAGE = new Image(Objects.requireNonNull(StaticHelpers.getResourceFromAssets(USER_BULLET_PNG)));
    public static final Image BACK_GROUND_IMAGE = new Image(Objects.requireNonNull(StaticHelpers.getResourceFromAssets(BACKGROUND_JPG)));
    public static final Image USER_SHIP_IMAGE = new Image(Objects.requireNonNull(StaticHelpers.getResourceFromAssets(USER_SHIP_PNG)));
    public static final Image EASY_ALIEN_SHIP_IMAGE = new Image(Objects.requireNonNull(StaticHelpers.getResourceFromAssets(ALIEN_SHIP_PNG)));
    public static final Image EXPLOSION_IMAGE = new Image(Objects.requireNonNull(StaticHelpers.getResourceFromAssets(EXPLOSION_PNG)));
    public static final Image ALIEN_BULLET_IMAGE = new Image(Objects.requireNonNull(StaticHelpers.getResourceFromAssets(ALIEN_BULLET_PNG)));
    public static final Image MEDIUM_ALIEN_SHIP_IMAGE = new Image(Objects.requireNonNull(StaticHelpers.getResourceFromAssets(ALIEN_2_PNG)));
    public static final Image HARD_ALIEN_SHIP_IMAGE = new Image(Objects.requireNonNull(StaticHelpers.getResourceFromAssets(ALIEN_3_PNG)));
    public static final Image GAME_OVER_IMAGE = new Image(Objects.requireNonNull(StaticHelpers.getResourceFromAssets(GAME_OVER_PNG)));
    public static final Image JUST_WOW_IMAGE = new Image(Objects.requireNonNull(StaticHelpers.getResourceFromAssets(JUST_WOW_JPG)));
    public static final Image BOSS_ALIEN_SHIP_IMAGE = new Image(Objects.requireNonNull(StaticHelpers.getResourceFromAssets(ALIEN_BOSS_PNG)));
    public static final Image WAIT_IMAGE = new Image(Objects.requireNonNull(StaticHelpers.getResourceFromAssets(WAIT_JPG)));

    public static final Random NUMBER_GENERATOR = new Random();

    public static final String JSON_KEY_X = "x";
    public static final String JSON_KEY_Y = "y";
    public static final String JSON_KEY_USER_HP = "hp";
    public static final String JSON_KEY_TICK = "tick";
    public static final String JSON_KEY_ALIEN_HP = "alien_hp";
    public static final String JSON_KEY_WINNER_FLAG = "winner";
    public static final String JSON_KEY_HAS_RIVAL_WON = "has_rival_destroyed_boss";
    public static final String JSON_KEY_HAS_SHOT_THIS_TURN = "has_shot";

}
