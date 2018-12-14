package main.com.ceng453.frontend.main;

import javafx.scene.media.AudioClip;
import java.io.File;

public class Sound {

    public static final int EasyEnemyBulletSound = 0;
    public static final int MediumEnemyBulletSound = 1;
    public static final int HardEnemyBulletSound = 2;
    public static final int UserBulletSound = 3;
    public static final int ExplosionSound = 4;

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

        AudioClip clip = new AudioClip(new File(System.getProperty("user.dir") + "/assets/" + musicFile).toURI().toString());
        clip.setCycleCount(1);
        clip.play();
    }
}
