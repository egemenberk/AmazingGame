package main.com.ceng453.client.main;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import main.com.ceng453.ApplicationConstants;

import java.io.File;


/*
 * Sound factory class for game effects
 */
public class Sound {

    public static final int EasyEnemyBulletSound = 0;
    private static final int MediumEnemyBulletSound = 1;
    public static final int HardEnemyBulletSound = 2;
    public static final int UserBulletSound = 3;
    private static final int ExplosionSound = 4;

    private Sound(){} // Factory pattern

    // Create a sound in type 'type', then play it
    // These sound will be used for effects & sound sounds, background sound is not created from here
    // Since it is attached to MediaView on our Scene
    public static void play( int type ){
        String musicFile = null;
        switch (type){
            case Sound.EasyEnemyBulletSound:
                musicFile = ApplicationConstants.CIYUW_WAV;
                break;
            case Sound.MediumEnemyBulletSound:
                musicFile = ApplicationConstants.CIYUW_WAV;
                break;
            case Sound.HardEnemyBulletSound:
                musicFile = ApplicationConstants.KAPUSKA_WAV;
                break;
            case Sound.UserBulletSound:
                musicFile = ApplicationConstants.PIT_WAV;
                break;
            case Sound.ExplosionSound:
                musicFile = ApplicationConstants.CIYUW_WAV;
                break;
        }

        // Create a clip & play that clip
        AudioClip clip = new AudioClip(Main.class.getResource("/assets/"+musicFile).toString());
        clip.setCycleCount(1);
        clip.play();
    }
}
