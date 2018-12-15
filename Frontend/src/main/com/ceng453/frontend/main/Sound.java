package main.com.ceng453.frontend.main;

import javafx.scene.media.AudioClip;
import java.io.File;


/*
 * Sound factory class for game effects
 */
public class Sound {

    public static final int EasyEnemyBulletSound = 0;
    public static final int MediumEnemyBulletSound = 1;
    public static final int HardEnemyBulletSound = 2;
    public static final int UserBulletSound = 3;
    public static final int ExplosionSound = 4;

    private Sound(){} // Factory pattenr

    // Create a sound in type 'type', then play it
    // These sound will be used for effects & sourd sounds, background sound is not created from here
    // Since it is attached to MediaView on our Scene
    public static void play( int type ){
        String musicFile = ApplicationConstants.ViyuwSound;
        switch (type){
            case Sound.EasyEnemyBulletSound:
                musicFile = ApplicationConstants.ViyuwSound;
                break;
            case Sound.MediumEnemyBulletSound:
                musicFile = ApplicationConstants.ViyuwSound;
                break;
            case Sound.HardEnemyBulletSound:
                musicFile = ApplicationConstants.Kapiska;
                break;
            case Sound.UserBulletSound:
                musicFile = ApplicationConstants.PitSound;
                break;
            case Sound.ExplosionSound:
                musicFile = ApplicationConstants.ViyuwSound;
                break;
        }

        // Create a clip & play that clip
        AudioClip clip = new AudioClip(new File(System.getProperty("user.dir") + "/assets/" + musicFile).toURI().toString());
        clip.setCycleCount(1);
        clip.play();
    }
}
